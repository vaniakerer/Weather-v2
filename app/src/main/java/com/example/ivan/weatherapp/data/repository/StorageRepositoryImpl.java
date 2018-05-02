package com.example.ivan.weatherapp.data.repository;

import com.example.ivan.weatherapp.business.repository.StorageRepository;
import com.example.ivan.weatherapp.data.database.CityDataProvider;
import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.data.database.model.DbWeather;
import com.example.ivan.weatherapp.data.database.WeatherDataProvider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import io.realm.RealmResults;

/**
 * Created by ivan
 */

public class StorageRepositoryImpl implements StorageRepository {

    private CityDataProvider cityDataProvider;
    private WeatherDataProvider weatherDataProvider;


    public StorageRepositoryImpl(CityDataProvider cityDataProvider, WeatherDataProvider weatherDataProvider) {
        this.cityDataProvider = cityDataProvider;
        this.weatherDataProvider = weatherDataProvider;
    }

    @Override
    public Flowable<RealmResults<DbCity>> getSavedCityName() {
        return cityDataProvider.getSavedCity();
    }

    @Override
    public Flowable saveNewCityName(DbCity city) {
        return Flowable.create(e -> {
            DbCity savedCity = cityDataProvider.insert(city);

            if (savedCity == null)
                e.onError(new Throwable("No city in Db"));
            else {
                e.onNext(savedCity);
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<DbWeather> getSavedWeather() {
        return Flowable.create(e -> {
            DbWeather dbWeather = weatherDataProvider.getSavedWeather();
            if (!e.isCancelled()) {
                e.onNext(dbWeather);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<DbWeather> saveWeather(DbWeather weather) {
        return Flowable.create(e -> {
            DbWeather savedWeather = weatherDataProvider.saveWeather(weather);
            if (!e.isCancelled()) {
                e.onNext(savedWeather);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable clearDb() {
        return Flowable.combineLatest(clearCity(), clearWeather(), (i, i1) -> i && i1);
    }

    @Override
    public Flowable<Boolean> clearWeather() {

        return Flowable.create(e -> {
            boolean isDeleteSuccess = weatherDataProvider.deleteAll();
            if (!e.isCancelled()) {
                e.onNext(isDeleteSuccess);
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<Boolean> clearCity() {
        return Flowable.create(e -> {
            boolean isDeleted = cityDataProvider.deleteAll();
            e.onNext(isDeleted);
        }, BackpressureStrategy.LATEST);
    }

}
