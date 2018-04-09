package com.example.ivan.weatherapp.business.main;

import com.example.ivan.weatherapp.repository.StorageRepository;

import io.reactivex.Observable;


/**
 * Created by ivan
 */

public class StorageInterceptor {
    private StorageRepository storageRepository;

    public StorageInterceptor(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Observable<String> getSavedCityName() {
        return storageRepository.getSavedCityName();
    }

    public void saveCityName(String cityName) {
        storageRepository.saveNewCityName(cityName);
    }
}
