package com.example.ivan.weatherapp.business.main;

import android.util.Log;

import com.example.ivan.weatherapp.business.main.exeption.NoCityNameExeption;
import com.example.ivan.weatherapp.business.repository.AddressRepository;
import com.example.ivan.weatherapp.business.repository.StorageRepository;
import com.example.ivan.weatherapp.business.repository.WeatherRepositoty;
import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.data.database.model.DbWeather;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.example.ivan.weatherapp.utils.TextUtils;


import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public class WeatherInteractor {

    private WeatherRepositoty weatherRepository;
    private StorageRepository storageRepository;
    private AddressRepository addressRepository;

    public WeatherInteractor(WeatherRepositoty weatherRepository, StorageRepository storageRepository, AddressRepository addressRepository) {
        this.weatherRepository = weatherRepository;
        this.storageRepository = storageRepository;
        this.addressRepository = addressRepository;
    }

    private Flowable<String> getSavedCityLocation() {
        return storageRepository.getSavedCityName()
                .flatMap(dbCities -> Flowable.just(dbCities.get(0).getCityName()))
                .flatMap(s -> {
                    if (TextUtils.isEmpty(s))
                        throw new NoCityNameExeption();
                    else {
                        return addressRepository.getLocationsByCityName(s);
                    }
                });
    }

    public Flowable<String> getSavedCityName() {
        return storageRepository.getSavedCityName()
                .doOnNext(dbCities -> Log.d("Afasf", dbCities.toString()))
                .flatMap(dbCities -> Flowable.just(dbCities.get(0).getCityName()));
    }

    public Flowable<Weather> getWeather() {
        return getWeatherByCityNameFromNetwork()
                .flatMap(this::reSaveWeather)
                .doOnError(Throwable::printStackTrace)
                .flatMap(this::mapSuccessResponse)
                .onErrorResumeNext(getSavedWeather())
                .doOnError(Throwable::printStackTrace);
    }

    private Flowable<WeatherResponse> getWeatherByCityNameFromNetwork() {
        return getSavedCityLocation()
                .flatMap(s -> weatherRepository.getWeather(s))
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    throw new NoCityNameExeption();
                });
    }

    public Flowable<Weather> getSavedWeather() {
        return storageRepository.getSavedWeather()
                .flatMap(dbWeather -> Flowable.just(Weather.fromDbWeather(dbWeather)));
    }

    private Flowable<DbWeather> reSaveWeather(WeatherResponse weather) {
        return storageRepository
                .clearWeather().flatMap(integer -> storageRepository.saveWeather(DbWeather.fromWeatherResponse(weather)));
    }

    public Flowable saveCityName(String cityName) {
        return storageRepository.saveNewCityName(new DbCity(cityName));
    }

    public Flowable clearDb() {
        return storageRepository.clearDb();
    }

    private Flowable<Weather> mapSuccessResponse(DbWeather weather) {
        if (weather != null)
            return Flowable.just(Weather.fromDbWeather(weather));
        else
            return Flowable.empty();
    }


}
