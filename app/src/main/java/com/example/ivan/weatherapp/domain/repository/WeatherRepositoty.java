package com.example.ivan.weatherapp.domain.repository;

import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.domain.model.City;
import com.example.ivan.weatherapp.domain.model.Weather;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public interface WeatherRepositoty {
    Flowable<Weather> getWeather(String cityName);

    Flowable<List<City>> getSavedCityName();

    Flowable saveNewCityName(DbCity city);

    Flowable<Weather> getSavedWeather();

    Flowable<Weather> saveWeather(Weather weather);

    Flowable clearDb();

    Flowable<Boolean> clearWeather();

    Flowable<Boolean> clearCity();
}
