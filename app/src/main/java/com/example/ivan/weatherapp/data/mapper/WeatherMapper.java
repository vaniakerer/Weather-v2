package com.example.ivan.weatherapp.data.mapper;

import com.example.ivan.weatherapp.data.database.model.DbWeather;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.domain.model.Weather;

import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public class WeatherMapper {

    public Flowable<Weather> mapWeatherResponse(WeatherResponse weatherResponse) {
        return Flowable.just(weatherResponse)
                .map(response -> {
                    double windSpeed = response.getDailyDTO().getData().get(0).getWindSpeed();
                    double temperature = response.getDailyDTO().getData().get(0).getTemperatureMin();
                    return new Weather(temperature, windSpeed);
                });
    }

    public Flowable<DbWeather> mapWeatherToDbWeather(Weather weather) {
        return Flowable.just(weather)
                .map(dbweather -> {
                    DbWeather dbWeather = new DbWeather();
                    dbWeather.setTemperature(dbweather.getTemperature());
                    dbWeather.setWindSpeed(dbweather.getSpeed());
                    dbWeather.setSummOfTemperatureAndWindSpeed(dbWeather.getTemperature() + dbWeather.getWindSpeed());
                    return dbWeather;
                });
    }

    public Weather mapDbWeatherToWeather(DbWeather dbWeather) {
        return new Weather(dbWeather.getTemperature(), dbWeather.getWindSpeed());
    }

}
