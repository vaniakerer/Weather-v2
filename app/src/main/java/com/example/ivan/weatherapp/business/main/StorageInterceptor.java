package com.example.ivan.weatherapp.business.main;

import com.example.ivan.weatherapp.data.db.tables.CityTable;
import com.example.ivan.weatherapp.data.repository.StorageRepository;
import com.example.ivan.weatherapp.entity.db.DbCity;

import io.reactivex.Observable;
import ru.arturvasilov.sqlite.core.SQLite;


/**
 * Created by ivan
 */

public class StorageInterceptor {
    private StorageRepository storageRepository;

    public StorageInterceptor(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Observable<String> getSavedCityName() {
        return storageRepository.getSavedCityName()
                .flatMap(dbCities -> Observable.just(dbCities.get(0).getCityName()));
    }

    public Observable saveCityName(String cityName) {
      return   storageRepository.saveNewCityName(new DbCity(cityName));
    }

    public Observable clearDb() {
        return storageRepository.clearDb();
    }
}
