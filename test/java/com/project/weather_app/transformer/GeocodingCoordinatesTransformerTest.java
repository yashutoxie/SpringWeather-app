package com.project.weather_app.transformer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.weather_app.domain.CityCoordinates;
import com.project.weather_app.entity.GeoCodingCoordinatesEntity;
 
class GeocodingCoordinatesTransformerTest {
    
    private GeocodingCoordinatesTransformer transformer;
    
    @BeforeEach
    public void setup() {
        transformer = new GeocodingCoordinatesTransformer();
    }
    
    @Test
    public void test_should_transform_geocoding_coordinates_to_domain() {
        // Given
        final GeoCodingCoordinatesEntity entity = GeoCodingCoordinatesEntity.builder()
                .longitude("12.0")
                .latitude("-12.90")
                .build();
        
        // When
        final CityCoordinates cityCoordinates = transformer.transformToDomain(entity);
        
        // Then
        assertAll("Should return domain city coordinates",
                () -> assertEquals(entity.getLatitude(), cityCoordinates.getLatitude()),
                () -> assertEquals(entity.getLongitude(), cityCoordinates.getLongitude()));
    }
    
    @Test
    public void test_should_handle_null_coordinates() {
        // Given
        final GeoCodingCoordinatesEntity entity = GeoCodingCoordinatesEntity.builder()
                .longitude(null)
                .latitude(null)
                .build();
        
        // When
        final CityCoordinates cityCoordinates = transformer.transformToDomain(entity);
        
        // Then
        assertAll("Should handle null coordinates",
                () -> assertNull(cityCoordinates.getLatitude()),
                () -> assertNull(cityCoordinates.getLongitude()));
    }
    
    @Test
    public void test_should_handle_edge_case_coordinates() {
        // Given - Testing extreme coordinate values
        final GeoCodingCoordinatesEntity entity = GeoCodingCoordinatesEntity.builder()
                .longitude("180.0")
                .latitude("-90.0")
                .build();
        
        // When
        final CityCoordinates cityCoordinates = transformer.transformToDomain(entity);
        
        // Then
        assertAll("Should handle edge case coordinates",
                () -> assertEquals("180.0", cityCoordinates.getLongitude()),
                () -> assertEquals("-90.0", cityCoordinates.getLatitude()));
    }
}
