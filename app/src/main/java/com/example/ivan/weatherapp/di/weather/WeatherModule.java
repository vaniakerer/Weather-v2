package com.example.ivan.weatherapp.di.weather;

import android.location.Geocoder;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.business.repository.AddressRepository;
import com.example.ivan.weatherapp.business.repository.StorageRepository;
import com.example.ivan.weatherapp.business.repository.WeatherRepositoty;
import com.example.ivan.weatherapp.data.database.CityDataProvider;
import com.example.ivan.weatherapp.data.database.WeatherDataProvider;
import com.example.ivan.weatherapp.data.database.provider.RealmProvider;
import com.example.ivan.weatherapp.data.repository.AddressRepositoryImpl;
import com.example.ivan.weatherapp.data.repository.StorageRepositoryImpl;
import com.example.ivan.weatherapp.data.repository.WeatherRepositoryImpl;
import com.example.ivan.weatherapp.presentation.service.sync_adapter.SyncAdapterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan
 */

@Module
public class WeatherModule {

    @Provides
    @WeatherScope
    public WeatherInteractor getWeatherInteractor(WeatherRepositoty repository, StorageRepository storageRepository, AddressRepository addressRepository) {
        return new WeatherInteractor(repository, storageRepository, addressRepository);
    }

    @Provides
    @WeatherScope
    public WeatherRepositoty getWetherRepository(DarkSkyApi api) {
        return new WeatherRepositoryImpl(api);
    }

    @Provides
    @WeatherScope
    public AddressRepository getAddressRepository(Geocoder geocoder) {
        return new AddressRepositoryImpl(geocoder);
    }

    @Provides
    @WeatherScope
    public StorageRepository provideStorageRepository(CityDataProvider cityDataProvider, WeatherDataProvider weatherDataProvider) {
        return new StorageRepositoryImpl(cityDataProvider, weatherDataProvider);
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
