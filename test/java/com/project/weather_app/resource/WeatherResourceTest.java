package com.project.weather_app.resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.project.weather_app.domain.WeatherRequestDetails;
import com.project.weather_app.entity.WeatherResponse;
import com.project.weather_app.service.WeatherService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(WeatherResource.class)
class WeatherResourceTest {
    
    public static final String CITY = "London";
    public static final String WEATHER = "Sunny";
    public static final String DETAILS = "Very sunny";
    public static final Double TEMP_CELSIUS = 22.5;
    public static final Double TEMP_FAHRENHEIT = 72.5;
    
    @SuppressWarnings("removal")
	@MockBean
    private WeatherService weatherService;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_should_return_weather_response_success() throws Exception {
        // Given
        final WeatherRequestDetails requestDetails = WeatherRequestDetails.builder()
                .city(CITY)
                .build();

        final WeatherResponse weatherResponse = WeatherResponse.builder()
                .weather(WEATHER)
                .details(DETAILS)
                .temperatureCelsius(TEMP_CELSIUS)
                .temperatureFahrenheit(TEMP_FAHRENHEIT)
                .build();

        when(weatherService.getWeather(requestDetails)).thenReturn(weatherResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/weather/{city}", CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.weather").value(WEATHER))
                .andExpect(jsonPath("$.details").value(DETAILS))
                .andExpect(jsonPath("$.temperatureCelsius").value(TEMP_CELSIUS))
                .andExpect(jsonPath("$.temperatureFahrenheit").value(TEMP_FAHRENHEIT));
    }

    @Test
    public void test_should_handle_service_exception() throws Exception {
        // Given
        final WeatherRequestDetails requestDetails = WeatherRequestDetails.builder()
                .city(CITY)
                .build();

        when(weatherService.getWeather(requestDetails))
                .thenThrow(new Exception("Weather service unavailable"));

        // When & Then
        mockMvc.perform(get("/api/v1/weather/{city}", CITY))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void test_should_handle_different_city_names() throws Exception {
        // Given
        String testCity = "New York";
        final WeatherRequestDetails requestDetails = WeatherRequestDetails.builder()
                .city(testCity)
                .build();

        final WeatherResponse weatherResponse = WeatherResponse.builder()
                .weather("Cloudy")
                .details("Overcast")
                .temperatureCelsius(18.0)
                .temperatureFahrenheit(64.4)
                .build();

        when(weatherService.getWeather(requestDetails)).thenReturn(weatherResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/weather/{city}", testCity))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weather").value("Cloudy"))
                .andExpect(jsonPath("$.details").value("Overcast"))
                .andExpect(jsonPath("$.temperatureCelsius").value(18.0))
                .andExpect(jsonPath("$.temperatureFahrenheit").value(64.4));
    }
}