package com.example.ivan.weatherapp.di.weather;

import android.location.Geocoder;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.data.db.realm.CityDataProvider;
import com.example.ivan.weatherapp.data.db.realm.WeatherDataProvider;
import com.example.ivan.weatherapp.data.db.realm.provider.RealmProvider;
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
public class WeatherModule {

    @Provides
    @WeatherScope
    public WeatherInteractor getWeatherInteractor(WeatherRepository repository, StorageRepository storageRepository, AddressRepository addressRepository) {
        return new WeatherInteractor(repository, storageRepository, addressRepository);
    }

    @Provides
    @WeatherScope
    public WeatherRepository getWetherRepository(DarkSkyApi api) {
        return new WeatherRepository(api);
    }

    @Provides
    @WeatherScope
    public AddressInterceptor getAddressInterceptor(AddressRepository addressRepository) {
        return new AddressInterceptor(addressRepository);
    }

    @Provides
    @WeatherScope
    public AddressRepository getAddressRepository(Geocoder geocoder) {
        return new AddressRepository(geocoder);
    }

    @Provides
    @WeatherScope
    public StorageRepository provideStorageRepository(CityDataProvider cityDataProvider, WeatherDataProvider weatherDataProvider) {
        return new StorageRepository(cityDataProvider, weatherDataProvider);
    }

    @Provides
    @WeatherScope
    public CityDataProvider provideCityDataProvider(RealmProvider realmProvider) {
        return new CityDataProvider(realmProvider);
    }

    @Provides
    @WeatherScope
    public WeatherDataProvider provideWeatherDataProvider(RealmProvider realmProvider) {
        return new WeatherDataProvider(realmProvider);
    }

}
