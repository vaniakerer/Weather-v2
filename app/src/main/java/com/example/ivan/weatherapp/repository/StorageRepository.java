package com.example.ivan.weatherapp.repository;


import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.example.ivan.weatherapp.utils.Prefs;

import io.reactivex.Observable;

/**
 * Created by ivan
 */

public class StorageRepository {
    private Prefs prefs;

    public StorageRepository(Prefs prefs) {
        this.prefs = prefs;
    }

    public Observable<String> getSavedCityName() {
        return Observable.just(prefs.getSavedCityName());
    }

    public void saveNewCityName(String cityName) {
        prefs.saveNewCityName(cityName);
    }

    public Observable<WeatherResponse> getSavedWeather() {
        return Observable.just(prefs.getSavedWeather());
    }

    public void saveWeather(WeatherResponse weather) {
        prefs.saveWeather(weather);
    }

}
