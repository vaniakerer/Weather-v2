package com.example.ivan.weatherapp.data.repository;


import android.util.Log;

import com.example.ivan.weatherapp.data.db.realm.CityDataProvider;
import com.example.ivan.weatherapp.data.db.realm.DbCity;
import com.example.ivan.weatherapp.data.db.realm.DbWeather;
import com.example.ivan.weatherapp.data.db.realm.WeatherDataProvider;

import java.util.concurrent.Callable;

import io.reactivex.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.sqlite.rx.RxSQLite;

/**
 * Created by ivan
 */

public class StorageRepository {

    private CityDataProvider cityDataProvider;
    private WeatherDataProvider weatherDataProvider;


    public StorageRepository(CityDataProvider cityDataProvider, WeatherDataProvider weatherDataProvider) {
        this.cityDataProvider = cityDataProvider;
        this.weatherDataProvider = weatherDataProvider;
    }

    public Observable<RealmResults<DbCity>> getSavedCityName() {
        return Observable.create(e -> {
            RealmResults<DbCity> dbCity = cityDataProvider.getSavedCity();
            if (dbCity == null || dbCity.isEmpty())
                e.onError(new Throwable());
            else {
                e.onNext(dbCity);
                e.onComplete();
            }
        });
    }

    public Observable saveNewCityName(DbCity city) {
        return Observable.create(e -> {
            Log.d("TEst_Error", "saveNewCityName()");
            DbCity savedCity = cityDataProvider.insert(city);

            if (savedCity == null)
                e.onError(new Throwable("No city in Db"));
            else {
                e.onNext(savedCity);
            }
        });
    }

    public Observable<DbWeather> getSavedWeather() {
        return Observable.create(e -> {
            DbWeather dbWeather = weatherDataProvider.getSavedWeather();
            if (!e.isDisposed()) {
                e.onNext(dbWeather);
                e.onComplete();
            }
        });
    }

    public Observable<DbWeather> saveWeather(DbWeather weather) {
        return Observable.create(e -> {
            DbWeather savedWeather = weatherDataProvider.saveWeather(weather);
            if (!e.isDisposed()) {
                e.onNext(savedWeather);
                e.onComplete();
            }
        });
    }

    public Observable clearDb() {
        return Observable.combineLatest(clearCity(), clearWeather(), (i, i1) -> {
            Log.d("TEst_Error", i + " " + i1);
            return i && i1;
        });
    }

    public Observable<Boolean> clearWeather() {

        return Observable.create(e -> {
            boolean isDeleteSuccess = weatherDataProvider.deleteAll();
            if (!e.isDisposed()) {
                e.onNext(isDeleteSuccess);
            }
        });
    }

    private Observable<Boolean> clearCity() {
        return Observable.create(e -> {
            boolean isDeleted = cityDataProvider.deleteAll();
            e.onNext(isDeleted);
        });
    }

}
