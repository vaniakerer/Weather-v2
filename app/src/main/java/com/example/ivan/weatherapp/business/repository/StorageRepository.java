package com.example.ivan.weatherapp.business.repository;

import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.data.database.model.DbWeather;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Created by ivan
 */

public interface StorageRepository {
    Flowable<RealmResults<DbCity>> getSavedCityName();

    Flowable saveNewCityName(DbCity city);

    Flowable<DbWeather> getSavedWeather();

    Flowable<DbWeather> saveWeather(DbWeather weather);

    Flowable clearDb();

    Flowable<Boolean> clearWeather();

    Flowable<Boolean> clearCity();
}
