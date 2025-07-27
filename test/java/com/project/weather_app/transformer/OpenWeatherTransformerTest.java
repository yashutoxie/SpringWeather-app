package com.project.weather_app.transformer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.weather_app.domain.CityWeather;
import com.project.weather_app.entity.MainEntity;
import com.project.weather_app.entity.OpenWeatherResponseEntity;
import com.project.weather_app.entity.WeatherEntity;
import com.project.weather_app.entity.WeatherResponse;

class OpenWeatherTransformerTest {

	private OpenWeatherTransformer transformer;

	private static final String WEATHER_MAIN = "Rain";
	private static final String WEATHER_DESCRIPTION = "A lot of rain";
	private static final Double TEMP_KELVIN = 295.65; // 22.5°C
	private static final Double EXPECTED_CELSIUS = 22.5;
	private static final Double EXPECTED_FAHRENHEIT = 72.5;

	@BeforeEach
	public void setup() {
		transformer = new OpenWeatherTransformer();
	}

	@Test
	public void test_should_transform_open_weather_response_entity_to_domain() {
		// Given
		final WeatherEntity weather = WeatherEntity.builder().main(WEATHER_MAIN).description(WEATHER_DESCRIPTION)
				.build();
		final WeatherEntity[] weatherEntities = { weather };

		final MainEntity mainEntity = MainEntity.builder().temperature(TEMP_KELVIN).build();

		final OpenWeatherResponseEntity entity = OpenWeatherResponseEntity.builder().weather(weatherEntities)
				.main(mainEntity).build();

		// When
		final CityWeather cityWeather = transformer.transformToDomain(entity);

		// Then
		assertAll("Should return domain weather object with temperature conversion",
				() -> assertEquals(WEATHER_MAIN, cityWeather.getWeather()),
				() -> assertEquals(WEATHER_DESCRIPTION, cityWeather.getDetails()),
				() -> assertEquals(EXPECTED_CELSIUS, cityWeather.getTemperatureCelsius()),
				() -> assertEquals(EXPECTED_FAHRENHEIT, cityWeather.getTemperatureFahrenheit()));
	}

	@Test
	public void test_should_transform_city_weather_to_entity() {
		// Given
		final CityWeather cityWeather = CityWeather.builder().weather(WEATHER_MAIN).details(WEATHER_DESCRIPTION)
				.temperatureCelsius(EXPECTED_CELSIUS).temperatureFahrenheit(EXPECTED_FAHRENHEIT).build();

		// When
		final WeatherResponse weatherResponse = transformer.transformToEntity(cityWeather);

		// Then
		assertAll("Should return entity weather response object with all fields",
				() -> assertEquals(cityWeather.getWeather(), weatherResponse.getWeather()),
				() -> assertEquals(cityWeather.getDetails(), weatherResponse.getDetails()),
				() -> assertEquals(cityWeather.getTemperatureCelsius(), weatherResponse.getTemperatureCelsius()),
				() -> assertEquals(cityWeather.getTemperatureFahrenheit(), weatherResponse.getTemperatureFahrenheit()));
	}

	@Test
	public void test_should_handle_temperature_conversion_accurately() {
		// Given - Test with different temperature values
		final WeatherEntity weather = WeatherEntity.builder().main("Clear").description("Clear sky").build();
		final WeatherEntity[] weatherEntities = { weather };

		final MainEntity mainEntity = MainEntity.builder().temperature(273.15) // 0°C exactly
				.build();

		final OpenWeatherResponseEntity entity = OpenWeatherResponseEntity.builder().weather(weatherEntities)
				.main(mainEntity).build();

		// When
		final CityWeather cityWeather = transformer.transformToDomain(entity);

		// Then
		assertAll("Should convert temperature accurately", () -> assertEquals(0.0, cityWeather.getTemperatureCelsius()),
				() -> assertEquals(32.0, cityWeather.getTemperatureFahrenheit()));
	}

	@Test
	public void test_should_handle_negative_temperatures() {
		// Given - Test with sub-zero temperature
		final WeatherEntity weather = WeatherEntity.builder().main("Snow").description("Heavy snow").build();
		final WeatherEntity[] weatherEntities = { weather };

		final MainEntity mainEntity = MainEntity.builder().temperature(263.15) // -10°C
				.build();

		final OpenWeatherResponseEntity entity = OpenWeatherResponseEntity.builder().weather(weatherEntities)
				.main(mainEntity).build();

		// When
		final CityWeather cityWeather = transformer.transformToDomain(entity);

		// Then
		assertAll("Should handle negative temperatures correctly",
				() -> assertEquals(-10.0, cityWeather.getTemperatureCelsius()),
				() -> assertEquals(14.0, cityWeather.getTemperatureFahrenheit()));
	}

	@Test
	public void test_should_round_temperature_to_two_decimal_places() {
		// Given - Test with temperature that requires rounding
		final WeatherEntity weather = WeatherEntity.builder().main("Clouds").description("Partly cloudy").build();
		final WeatherEntity[] weatherEntities = { weather };

		final MainEntity mainEntity = MainEntity.builder().temperature(295.678) // Should result in 22.528°C and
																				// 72.5504°F
				.build();

		final OpenWeatherResponseEntity entity = OpenWeatherResponseEntity.builder().weather(weatherEntities)
				.main(mainEntity).build();

		// When
		final CityWeather cityWeather = transformer.transformToDomain(entity);

		// Then
		assertAll("Should round temperatures to 2 decimal places",
				() -> assertEquals(22.53, cityWeather.getTemperatureCelsius()),
				() -> assertEquals(72.55, cityWeather.getTemperatureFahrenheit()));
	}
}