package com.example.ivan.weatherapp.entity.db;


import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;

/**
 * Created by ivan
 */

public class DbWeather {
    private double temperature;
    private double windSpeed;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public static DbWeather fromWeatherResponse(WeatherResponse weatherResponse) {
        DbWeather dbWeather = new DbWeather();
        dbWeather.setTemperature(weatherResponse.getDailyDTO().getData().get(0).getTemperatureMin());
        dbWeather.setWindSpeed(weatherResponse.getDailyDTO().getData().get(0).getWindSpeed());

        return dbWeather;
    }
}
