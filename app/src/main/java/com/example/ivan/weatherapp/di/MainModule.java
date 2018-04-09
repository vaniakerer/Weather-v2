package com.example.ivan.weatherapp.di;

import android.app.Notification;
import android.content.Context;
import android.location.Geocoder;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.StorageInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.repository.AddressRepository;
import com.example.ivan.weatherapp.repository.StorageRepository;
import com.example.ivan.weatherapp.repository.WeatherRepository;
import com.example.ivan.weatherapp.utils.Prefs;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan
 */

@Module
public class MainModule {

    @Provides
    @Singleton
    public WeatherInteractor getWeatherInteractor(WeatherRepository repository, StorageRepository storageRepository) {
        return new WeatherInteractor(repository, storageRepository);
    }

    @Provides
    @Singleton
    public WeatherRepository getWetherRepository(DarkSkyApi api) {
        return new WeatherRepository(api);
    }

    @Provides
    @Singleton
    public AddressInterceptor getAddressInterceptor(AddressRepository addressRepository) {
        return new AddressInterceptor(addressRepository);
    }

    @Provides
    @Singleton
    public AddressRepository getAddressRepository(Geocoder geocoder) {
        return new AddressRepository(geocoder);
    }

    @Provides
    @Singleton
    public StorageInterceptor getStorageInterceptor(StorageRepository storageRepository) {
        return new StorageInterceptor(storageRepository);
    }

    @Provides
    @Singleton
    public StorageRepository provideStorageRepository(Prefs prefs) {
        return new StorageRepository(prefs);
    }


}
