package com.example.ivan.weatherapp.di.weather;

import android.location.Geocoder;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.data.mapper.CityMapper;
import com.example.ivan.weatherapp.data.mapper.WeatherMapper;
import com.example.ivan.weatherapp.domain.main.WeatherInteractor;
import com.example.ivan.weatherapp.domain.model.City;
import com.example.ivan.weatherapp.domain.repository.AddressRepository;
import com.example.ivan.weatherapp.domain.repository.WeatherRepositoty;
import com.example.ivan.weatherapp.data.database.CityDataProvider;
import com.example.ivan.weatherapp.data.database.WeatherDataProvider;
import com.example.ivan.weatherapp.data.database.provider.RealmProvider;
import com.example.ivan.weatherapp.data.repository.AddressRepositoryImpl;
import com.example.ivan.weatherapp.data.repository.WeatherRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan
 */

@Module
public class WeatherModule {

    @Provides
    @WeatherScope
    public WeatherInteractor getWeatherInteractor(WeatherRepositoty repository, AddressRepository addressRepository) {
        return new WeatherInteractor(repository, addressRepository);
    }

    @Provides
    @WeatherScope
    public WeatherRepositoty getWetherRepository(DarkSkyApi api, CityDataProvider cityDataProvider, WeatherDataProvider weatherDataProvider, WeatherMapper weatherMapper, CityMapper cityMapper) {
        return new WeatherRepositoryImpl.Builder()
                .withDarkSkyApiService(api)
                .withCityDataProvider(cityDataProvider)
                .withWeatherDataProvider(weatherDataProvider)
                .withWeatherMapper(weatherMapper)
                .withCityMapper(cityMapper)
                .build();
    }

    @Provides
    @WeatherScope
    public AddressRepository getAddressRepository(Geocoder geocoder) {
        return new AddressRepositoryImpl(geocoder);
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

    @Provides
    @WeatherScope
    public WeatherMapper provideWeatherMapper() {
        return new WeatherMapper();
    }

    @Provides
    @WeatherScope
    public CityMapper provideCityMapper() {
        return new CityMapper();
    }
}
