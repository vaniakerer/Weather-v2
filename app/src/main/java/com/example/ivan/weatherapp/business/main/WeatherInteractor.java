package com.example.ivan.weatherapp.business.main;

import android.util.Log;

import com.example.ivan.weatherapp.business.main.exeption.NoCityNameExeption;
import com.example.ivan.weatherapp.data.repository.AddressRepository;
import com.example.ivan.weatherapp.data.db.realm.DbCity;
import com.example.ivan.weatherapp.data.db.realm.DbWeather;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.example.ivan.weatherapp.data.repository.StorageRepository;
import com.example.ivan.weatherapp.data.repository.WeatherRepository;
import com.example.ivan.weatherapp.utils.TextUtils;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.realm.RealmResults;

/**
 * Created by ivan
 */

public class WeatherInteractor {

    private WeatherRepository weatherRepository;
    private StorageRepository storageRepository;
    private AddressRepository addressRepository;

    public WeatherInteractor(WeatherRepository weatherRepository, StorageRepository storageRepository, AddressRepository addressRepository) {
        this.weatherRepository = weatherRepository;
        this.storageRepository = storageRepository;
        this.addressRepository = addressRepository;
    }

    private Observable<String> getSavedCityLocation() {
        return storageRepository.getSavedCityName()
                .flatMap(dbCities -> Observable.just(dbCities.get(0).getCityName()))
                .flatMap(s -> {
                    if (TextUtils.isEmpty(s))
                        throw new NoCityNameExeption();
                    else {
                        return addressRepository.getLocationsByCityName(s);
                    }
                });
    }

    public Observable<String> getSavedCityName() {
        return storageRepository.getSavedCityName()
                .doOnNext(dbCities -> Log.d("Afasf", dbCities.toString()))
                .flatMap(dbCities -> Observable.just(dbCities.get(0).getCityName()));
    }


    public Observable<Weather> getWeather() {
        return getWeatherByCityNameFromNetwork()
                .doOnNext(response -> Log.d("Afasf", response.toString()))
                .flatMap(this::reSaveWeather)
                .doOnError(Throwable::printStackTrace)
                .flatMap(this::mapSuccessResponse)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(weather -> Log.d("Afasf", weather.toString()))
                .onErrorResumeNext(getSavedWeather())
                .doOnError(Throwable::printStackTrace);
    }

    private Observable<WeatherResponse> getWeatherByCityNameFromNetwork() {
        return getSavedCityLocation()
                .flatMap(s -> weatherRepository.getWeather(s))
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    throw new NoCityNameExeption();
                });
    }

    private Observable<Weather> getSavedWeather() {
        return storageRepository.getSavedWeather()
                .flatMap(dbWeather -> Observable.just(Weather.fromDbWeather(dbWeather)));
    }

    private Observable<DbWeather> reSaveWeather(WeatherResponse weather) {
        return storageRepository
                .clearWeather().flatMap(integer -> storageRepository.saveWeather(DbWeather.fromWeatherResponse(weather)));
    }

    public Observable saveCityName(String cityName) {
        return storageRepository.saveNewCityName(new DbCity(cityName));
    }

    public Observable clearDb() {
        return storageRepository.clearDb();
    }

    private Observable<Weather> mapSuccessResponse(DbWeather weather) {
        if (weather != null)
            return Observable.just(Weather.fromDbWeather(weather));
        else
            return Observable.empty();
    }


}
