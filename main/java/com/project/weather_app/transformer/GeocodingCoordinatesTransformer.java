package com.project.weather_app.transformer;

import org.springframework.stereotype.Service;

import com.project.weather_app.domain.CityCoordinates;
import com.project.weather_app.entity.GeoCodingCoordinatesEntity;

@Service
public class GeocodingCoordinatesTransformer {

    public CityCoordinates transformToDomain(final GeoCodingCoordinatesEntity entity) { 
        return CityCoordinates.builder()
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }

}