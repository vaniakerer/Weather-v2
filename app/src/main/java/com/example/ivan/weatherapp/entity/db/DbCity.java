package com.example.ivan.weatherapp.entity.db;


/**
 * Created by ivan
 */

public class DbCity {
    private String cityName;

    public DbCity() {
    }


    public DbCity(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
