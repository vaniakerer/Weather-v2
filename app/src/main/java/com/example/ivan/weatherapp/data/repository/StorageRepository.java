package com.example.ivan.weatherapp.data.repository;


import android.net.Uri;

import com.example.ivan.weatherapp.data.db.tables.CityTable;
import com.example.ivan.weatherapp.data.db.tables.WeatherTable;
import com.example.ivan.weatherapp.entity.db.DbCity;
import com.example.ivan.weatherapp.entity.db.DbWeather;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.entity.ui.Weather;

import java.util.List;

import io.reactivex.Observable;

import io.reactivex.functions.BiFunction;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.rx.RxSQLite;

/**
 * Created by ivan
 */

public class StorageRepository {

    private RxSQLite sqLite;

    public StorageRepository(RxSQLite sqLite) {
        this.sqLite = sqLite;
    }

    public Observable<List<DbCity>> getSavedCityName() {
        return sqLite.query(CityTable.TABLE);
    }

    public Observable saveNewCityName(DbCity city) {
        return sqLite.insert(CityTable.TABLE, city);
    }

    public Observable<DbWeather> getSavedWeather() {
        return sqLite.querySingle(WeatherTable.TABLE);
    }

    public Observable<DbWeather> saveWeather(DbWeather weather) {
        return sqLite.insert(WeatherTable.TABLE, weather).flatMap(uri -> Observable.just(weather));
    }

    public Observable clearDb() {
        return Observable.combineLatest(clearCity(), clearWeather(), (integer, integer2) -> integer + integer2);
    }

    public Observable<Integer> clearWeather() {
        return sqLite.delete(WeatherTable.TABLE);
    }

    private Observable<Integer> clearCity() {
        return sqLite.delete(CityTable.TABLE);
    }

}
