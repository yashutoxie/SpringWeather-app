package com.project.weather_app.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.weather_app.domain.WeatherRequestDetails;
import com.project.weather_app.entity.WeatherResponse;
import com.project.weather_app.service.WeatherService;

@RestController
@RequestMapping("/api/v1")
public class WeatherResource {

	private WeatherService weatherService;

	public WeatherResource(final WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping("/weather/{city}")
	public @ResponseBody WeatherResponse weather(@PathVariable("city") String city) throws Exception {
		final WeatherRequestDetails weatherRequestDetails = WeatherRequestDetails.builder().city(city).build();

		return weatherService.getWeather(weatherRequestDetails);
	}

}
