package com.example.ivan.weatherapp.data.repository;

import android.support.annotation.NonNull;

import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.data.mapper.CityMapper;
import com.example.ivan.weatherapp.data.mapper.WeatherMapper;
import com.example.ivan.weatherapp.domain.model.City;
import com.example.ivan.weatherapp.domain.repository.WeatherRepositoty;
import com.example.ivan.weatherapp.data.database.CityDataProvider;
import com.example.ivan.weatherapp.data.database.WeatherDataProvider;
import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.data.database.model.DbWeather;
import com.example.ivan.weatherapp.domain.model.Weather;


import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public class WeatherRepositoryImpl implements WeatherRepositoty {
    private DarkSkyApi darkSkyApiService;
    private CityDataProvider cityDataProvider;
    private WeatherDataProvider weatherDataProvider;

    private WeatherMapper weatherMapper;
    private CityMapper cityMapper;

    private WeatherRepositoryImpl(Builder builder) {
        darkSkyApiService = builder.darkSkyApiService;
        cityDataProvider = builder.cityDataProvider;
        weatherDataProvider = builder.weatherDataProvider;
        weatherMapper = builder.weatherMapper;
        cityMapper = builder.cityMapper;
    }

    @Override
    public Flowable<Weather> getWeather(String cityName) {
        return darkSkyApiService.getCityInfo(cityName).flatMap(
                response -> weatherMapper.mapWeatherResponse(response)
        );
    }

    @Override
    public Flowable<List<City>> getSavedCityName() {
        return cityDataProvider.getSavedCity()
                .flatMap(cities -> cityMapper.mapDbCityToCity(cities));
    }

    @Override
    public Flowable saveNewCityName(DbCity city) {
        return Flowable.create(e -> {
            DbCity savedCity = cityDataProvider.insert(city);
            if (savedCity == null)
                e.onError(new Throwable("No city in Db"));
            else {
                e.onNext(savedCity);
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Weather> getSavedWeather() {
        return Flowable.create(e -> {
            DbWeather dbWeather = weatherDataProvider.getSavedWeather();
            if (!e.isCancelled()) {
                e.onNext(dbWeather);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .map(dbWeather -> weatherMapper.mapDbWeatherToWeather((DbWeather) dbWeather));
    }

    @Override
    public Flowable<Weather> saveWeather(Weather weather) {
        return weatherMapper.mapWeatherToDbWeather(weather)
                .flatMap(w -> Flowable.create(e -> {
                    DbWeather savedWeather = weatherDataProvider.saveWeather(w);
                    if (!e.isCancelled()) {
                        e.onNext(savedWeather);
                        e.onComplete();
                    }
                }, BackpressureStrategy.BUFFER))
                .map(dbWeather -> weatherMapper.mapDbWeatherToWeather((DbWeather) dbWeather));

    }

    @Override
    public Flowable clearDb() {
        return Flowable.combineLatest(clearCity(), clearWeather(), (i, i1) -> i && i1);
    }

    @Override
    public Flowable<Boolean> clearWeather() {
        return Flowable.create(e -> {
            boolean isDeleteSuccess = weatherDataProvider.deleteAll();
            if (!e.isCancelled()) {
                e.onNext(isDeleteSuccess);
                e.onComplete();
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<Boolean> clearCity() {
        return Flowable.create(e -> {
            boolean isDeleted = cityDataProvider.deleteAll();
            e.onNext(isDeleted);
            e.onComplete();
        }, BackpressureStrategy.LATEST);
    }


    public static final class Builder {
        private CityMapper cityMapper;
        private WeatherMapper weatherMapper;
        private WeatherDataProvider weatherDataProvider;
        private CityDataProvider cityDataProvider;
        private DarkSkyApi darkSkyApiService;

        public Builder() {
        }

        public Builder withCityMapper(CityMapper val) {
            cityMapper = val;
            return this;
        }

        public Builder withWeatherMapper(WeatherMapper val) {
            weatherMapper = val;
            return this;
        }

        public Builder withWeatherDataProvider(WeatherDataProvider val) {
            weatherDataProvider = val;
            return this;
        }

        public Builder withCityDataProvider(CityDataProvider val) {
            cityDataProvider = val;
            return this;
        }

        public Builder withDarkSkyApiService(DarkSkyApi val) {
            darkSkyApiService = val;
            return this;
        }

        @NonNull
        public WeatherRepositoryImpl build() {
            return new WeatherRepositoryImpl(this);
        }
    }
}
