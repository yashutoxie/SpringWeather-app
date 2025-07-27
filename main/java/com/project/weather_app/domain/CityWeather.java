package com.project.weather_app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CityWeather {
	private String weather;
	private String details;
	private Double temperatureCelsius;
	private Double temperatureFahrenheit;
}