package com.example.ivan.weatherapp.domain.model;

import com.example.ivan.weatherapp.data.database.model.DbWeather;

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
}
