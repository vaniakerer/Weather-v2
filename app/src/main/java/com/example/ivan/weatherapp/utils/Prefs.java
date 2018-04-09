package com.example.ivan.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.google.gson.Gson;

/**
 * Created by ivan
 */

public class Prefs {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String PREFERENCES_NAME = "WeatherPreferences";

    private static final String KEY_CITY_NAME = "key_city_name";

    private static final String KEY_WEATHER = "key_weather";

    public Prefs(@NonNull Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        editor = preferences.edit();
    }

    public void saveNewCityName(String cityName) {
        editor.putString(KEY_CITY_NAME, cityName);
        editor.commit();
    }

    public String getSavedCityName() {
        return preferences.getString(KEY_CITY_NAME, "");
    }

    public WeatherResponse getSavedWeather() {
        return new Gson().fromJson(preferences.getString(KEY_WEATHER, null), WeatherResponse.class);
    }

    public void saveWeather(WeatherResponse weather) {
        editor.putString(KEY_WEATHER, new Gson().toJson(weather));
        editor.commit();
    }

}
