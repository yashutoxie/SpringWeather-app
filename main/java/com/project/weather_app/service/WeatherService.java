package com.project.weather_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.weather_app.domain.CityCoordinates;
import com.project.weather_app.domain.CityWeather;
import com.project.weather_app.domain.WeatherRequestDetails;
import com.project.weather_app.entity.WeatherResponse;
import com.project.weather_app.providers.GeoCodingProvider;
import com.project.weather_app.providers.WeatherProvider;
import com.project.weather_app.transformer.GeocodingCoordinatesTransformer;
import com.project.weather_app.transformer.OpenWeatherTransformer;

@Service
public class WeatherService {
	private GeoCodingProvider geoCodingProvider;
	private WeatherProvider weatherProvider;
	private GeocodingCoordinatesTransformer geocodingCoordinatesTransformer;
	private OpenWeatherTransformer openWeatherTransformer;

	@Autowired
	public WeatherService(final GeoCodingProvider geoCodingProvider,
			final GeocodingCoordinatesTransformer geocodingCoordinatesTransformer,
			final WeatherProvider weatherProvider, final OpenWeatherTransformer openWeatherTransformer) {
		this.geoCodingProvider = geoCodingProvider;
		this.geocodingCoordinatesTransformer = geocodingCoordinatesTransformer;
		this.weatherProvider = weatherProvider;
		this.openWeatherTransformer = openWeatherTransformer;
	}

	public WeatherResponse getWeather(final WeatherRequestDetails weatherRequestDetails) throws Exception {
		// get latitudes and longitudes.

		final CityCoordinates cityCoordinates = geocodingCoordinatesTransformer
				.transformToDomain(geoCodingProvider.getCoordinates(weatherRequestDetails));

		final CityWeather cityWeather = openWeatherTransformer
				.transformToDomain(weatherProvider.getWeather(cityCoordinates));

		return openWeatherTransformer.transformToEntity(cityWeather);
	}
}
