package com.example.ivan.weatherapp.repository;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;


import io.reactivex.Observable;

/**
 * Created by ivan
 */

public class WeatherRepository {
    private DarkSkyApi darkSkyApiService;

    public WeatherRepository(DarkSkyApi darkSkyApiService) {
        this.darkSkyApiService = darkSkyApiService;
    }

    public Observable<WeatherResponse> getWeather(String cityName) {
        return darkSkyApiService.getCityInfo(cityName);
    }

}
