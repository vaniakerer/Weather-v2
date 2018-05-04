package com.example.ivan.weatherapp.data.database.model;


import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.domain.model.Weather;

import io.realm.RealmObject;

/**
 * Created by ivan
 */

public class DbWeather extends RealmObject {
    private double temperature;
    private double windSpeed;
    private double summOfTemperatureAndWindSpeed;

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

    public void setSummOfTemperatureAndWindSpeed(double summOfTemperatureAndWindSpeed) {
        this.summOfTemperatureAndWindSpeed = summOfTemperatureAndWindSpeed;
    }
}
