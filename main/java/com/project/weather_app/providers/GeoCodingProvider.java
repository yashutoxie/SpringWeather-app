package com.project.weather_app.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.weather_app.domain.WeatherRequestDetails;
import com.project.weather_app.entity.GeoCodingCoordinatesEntity;

@Service
public class GeoCodingProvider {
	@Value("${api.key}")
	private String apiKey;
	
	@Value("${geocoding.url}")
	private String geocodingurl;
	public GeoCodingCoordinatesEntity getCoordinates(final WeatherRequestDetails weatherRequestDetails) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<GeoCodingCoordinatesEntity[]> responseEntity;
		HttpEntity<String> requestEntity = new HttpEntity<>(null, null);
		
		@SuppressWarnings("deprecation")
		UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(geocodingurl)
		.queryParam("q", weatherRequestDetails.getCity())
		.queryParam("limit", "1")
		.queryParam("appid", apiKey).build();
		
		try {
			responseEntity = restTemplate
					.exchange(uriBuilder.toUriString(), HttpMethod.GET, requestEntity, GeoCodingCoordinatesEntity[].class);
		} catch (HttpStatusCodeException e) {
			throw new Exception(e.getMessage());
		}
		
		return responseEntity.getBody()[0];
	}
}
