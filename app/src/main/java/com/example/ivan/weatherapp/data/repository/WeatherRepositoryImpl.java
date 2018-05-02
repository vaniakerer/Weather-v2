package com.example.ivan.weatherapp.data.repository;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.business.repository.WeatherRepositoty;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;


import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by ivan
 */

public class WeatherRepositoryImpl implements WeatherRepositoty {
    private DarkSkyApi darkSkyApiService;

    public WeatherRepositoryImpl(DarkSkyApi darkSkyApiService) {
        this.darkSkyApiService = darkSkyApiService;
    }

    @Override
    public Flowable<WeatherResponse> getWeather(String cityName) {
        return darkSkyApiService.getCityInfo(cityName);
    }

}
