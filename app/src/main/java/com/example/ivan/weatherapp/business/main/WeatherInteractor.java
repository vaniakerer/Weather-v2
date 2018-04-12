package com.example.ivan.weatherapp.business.main;

import com.example.ivan.weatherapp.entity.db.DbWeather;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.example.ivan.weatherapp.data.repository.StorageRepository;
import com.example.ivan.weatherapp.data.repository.WeatherRepository;


import io.reactivex.Observable;

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
                .flatMap(this::mapSuccessResponse)
                .onErrorResumeNext(throwable -> {
                    return getSavedWeather(cityName);
                });
    }

    private Observable<WeatherResponse> getWeatherByCityNameFromNetwork(String cityName) {
        return weatherRepository.getWeather(cityName);
    }

    private Observable<Weather> getSavedWeather(String cityName) {
        return storageRepository.getSavedWeather()
                .flatMap(dbWeather -> Observable.just(Weather.fromDbWeather(dbWeather)));
    }

    private void saveWeather(WeatherResponse weather) {
        storageRepository.saveWeather(DbWeather.fromWeatherResponse(weather))
                .subscribe();
    }

    private Observable<Weather> mapSuccessResponse(WeatherResponse response) {
        if (response != null)
            return Observable.just(Weather.fromWeatherResponse(response));
        else
            return Observable.empty();
    }
}
