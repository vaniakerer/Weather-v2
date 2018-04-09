package com.example.ivan.weatherapp.business.main;

import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.example.ivan.weatherapp.repository.StorageRepository;
import com.example.ivan.weatherapp.repository.WeatherRepository;


import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ivan
 */

public class WeatherInteractor {

    private WeatherRepository weatherRepository;
    private StorageRepository storageRepository;

    public WeatherInteractor(WeatherRepository weatherRepository, StorageRepository storageRepository) {
        this.weatherRepository = weatherRepository;
        this.storageRepository = storageRepository;
    }

    public Observable<Weather> getWeather(String cityName) {
        return getWeatherByCityNameFromNetwork(cityName)
                .doOnNext(this::saveWeather)
                .onErrorResumeNext(throwable -> {
                    return getSavedWeather(cityName);
                })
                .flatMap(this::mapSuccessResponse);
    }

    private Observable<WeatherResponse> getWeatherByCityNameFromNetwork(String cityName) {
        return weatherRepository.getWeather(cityName);
    }

    private Observable<WeatherResponse> getSavedWeather(String cityName) {
        return storageRepository.getSavedWeather();
    }

    private void saveWeather(WeatherResponse weather) {
        storageRepository.saveWeather(weather);
    }

    private Observable<Weather> mapSuccessResponse(WeatherResponse response) {
        if (response != null)
            return Observable.just(Weather.fromWeatherResponse(response));
        else
            return Observable.empty();
    }
}
