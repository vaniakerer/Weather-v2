package com.example.ivan.weatherapp.entity.ui;

import android.app.Notification;

import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;

/**
 * Created by ivan
 */

public class Weather {
    private double temperature;
    private double speed;

    public Weather(double temperature, double speed) {
        this.temperature = temperature;
        this.speed = speed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getSpeed() {
        return speed;
    }

    public static Weather fromWeatherResponse(WeatherResponse response) {
        return new Weather(response.getDailyDTO().getData().get(0).getTemperatureMin(), response.getDailyDTO().getData().get(0).getWindSpeed());
    }


}
