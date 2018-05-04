package com.example.ivan.weatherapp.domain.main;

import com.example.ivan.weatherapp.domain.main.exeption.NoCityNameExeption;
import com.example.ivan.weatherapp.domain.repository.AddressRepository;
import com.example.ivan.weatherapp.domain.repository.WeatherRepositoty;
import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.domain.model.Weather;
import com.example.ivan.weatherapp.utils.TextUtils;


import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public class WeatherInteractor {

    private WeatherRepositoty weatherRepository;
    private AddressRepository addressRepository;

    public WeatherInteractor(WeatherRepositoty weatherRepository, AddressRepository addressRepository) {
        this.weatherRepository = weatherRepository;
        this.addressRepository = addressRepository;
    }

    private Flowable<String> getSavedCityLocation() {
        return weatherRepository.getSavedCityName()
                .flatMap(cities -> Flowable.just(cities.get(0).getCityName()))
                .flatMap(s -> {
                    if (TextUtils.isEmpty(s))
                        throw new NoCityNameExeption();
                    else {
                        return addressRepository.getLocationsByCityName(s);
                    }
                });
    }

    public Flowable<String> getSavedCityName() {
        return weatherRepository.getSavedCityName()
                .flatMap(dbCities -> Flowable.just(dbCities.get(0).getCityName()));
    }

    public Flowable<Weather> getWeather() {
        return getWeatherByCityNameFromNetwork();
    }

    private Flowable<Weather> getWeatherByCityNameFromNetwork() {
        return getSavedCityLocation()
                .onErrorReturn(throwable -> "1,1")
                .flatMap(s -> weatherRepository.getWeather(s)
                        .flatMap(this::reSaveWeather)
                        .onErrorResumeNext(getSavedWeather()));
    }

    public Flowable<Weather> getSavedWeather() {
        return weatherRepository.getSavedWeather();
    }

    private Flowable<Weather> reSaveWeather(Weather weather) {
        return weatherRepository
                .clearWeather()
                .flatMap(integer -> weatherRepository.saveWeather(weather));
    }

    public Flowable saveCityName(String cityName) {
        return weatherRepository.saveNewCityName(new DbCity(cityName));
    }

    public Flowable clearDb() {
        return weatherRepository.clearDb();
    }
}
