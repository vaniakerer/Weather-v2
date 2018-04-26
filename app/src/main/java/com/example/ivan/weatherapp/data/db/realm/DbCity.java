package com.example.ivan.weatherapp.data.db.realm;


import io.realm.RealmObject;

/**
 * Created by ivan
 */

public class DbCity extends RealmObject {
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
