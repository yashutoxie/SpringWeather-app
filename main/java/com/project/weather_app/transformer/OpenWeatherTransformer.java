package com.project.weather_app.transformer;

import org.springframework.stereotype.Service;

import com.project.weather_app.domain.CityWeather;
import com.project.weather_app.entity.OpenWeatherResponseEntity;
import com.project.weather_app.entity.WeatherResponse;

@Service
public class OpenWeatherTransformer {
	
	public CityWeather transformToDomain(final OpenWeatherResponseEntity entity) {
		// Temperature is in Kelvin from OpenWeather API
		Double tempKelvin = entity.getMain().getTemperature();
		Double tempCelsius = tempKelvin - 273.15;
		Double tempFahrenheit = (tempCelsius * 9/5) + 32;

		return CityWeather.builder()
				.weather(entity.getWeather()[0].getMain())
				.details(entity.getWeather()[0].getDescription())
				.temperatureCelsius(Math.round(tempCelsius * 100.0) / 100.0) // Round to 2 decimal places
				.temperatureFahrenheit(Math.round(tempFahrenheit * 100.0) / 100.0) // Round to 2 decimal places
				.build();
	}
	
	public WeatherResponse transformToEntity(final CityWeather cityWeather) {
		return WeatherResponse.builder()
				.weather(cityWeather.getWeather())
				.details(cityWeather.getDetails())
				.temperatureCelsius(cityWeather.getTemperatureCelsius())
				.temperatureFahrenheit(cityWeather.getTemperatureFahrenheit())
				.build();
	}
}

