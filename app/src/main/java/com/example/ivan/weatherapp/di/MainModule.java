package com.example.ivan.weatherapp.di;

import android.location.Geocoder;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.data.repository.AddressRepository;
import com.example.ivan.weatherapp.data.repository.StorageRepository;
import com.example.ivan.weatherapp.data.repository.WeatherRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import ru.arturvasilov.sqlite.rx.RxSQLite;

/**
 * Created by ivan
 */

@Module
public class MainModule {

    @Provides
    @Singleton
    public WeatherInteractor getWeatherInteractor(WeatherRepository repository, StorageRepository storageRepository, AddressRepository addressRepository) {
        return new WeatherInteractor(repository, storageRepository, addressRepository);
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
    public StorageRepository provideStorageRepository(RxSQLite sqLite) {
        return new StorageRepository(sqLite);
    }


}
