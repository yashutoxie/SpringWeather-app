package com.project.weather_app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainEntity {

	@JsonProperty("temp")
	private Double temperature;

	@JsonProperty("feels_like")
	private Double feelsLike;

	@JsonProperty("temp_min")
	private Double tempMin;

	@JsonProperty("temp_max")
	private Double tempMax;

	@JsonProperty("pressure")
	private Integer pressure;

	@JsonProperty("humidity")
	private Integer humidity;
}