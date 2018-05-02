package com.example.ivan.weatherapp.business.repository;

import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;

import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public interface WeatherRepositoty {
    Flowable<WeatherResponse> getWeather(String cityName);
}
