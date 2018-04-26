package com.example.ivan.weatherapp.data.db.realm;

import com.example.ivan.weatherapp.data.db.realm.provider.RealmProvider;

import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Created by ivan
 */

public class WeatherDataProvider extends BaseDataProvider {

    public WeatherDataProvider(RealmProvider realmProvider) {
        super(realmProvider);
    }

    public DbWeather getSavedWeather() {
        RealmResults<DbWeather> savedWeathers = getRealm().where(DbWeather.class).findAll();
        return savedWeathers.first();
    }

    public DbWeather saveWeather(DbWeather dbWeather) {
        getRealm().beginTransaction();
        DbWeather returnedWeather = null;

        try {
            returnedWeather = getRealm().copyToRealm(dbWeather);
            getRealm().commitTransaction();
        } catch (Exception e) {
            getRealm().cancelTransaction();
        } finally {
            getRealm().close();
        }

        return returnedWeather;
    }

    public boolean deleteAll() {
        getRealm().beginTransaction();
        getRealm().where(DbWeather.class).findAll().deleteAllFromRealm();
        getRealm().commitTransaction();
        getRealm().close();

        return true;
    }


}
