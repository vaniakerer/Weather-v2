package com.example.ivan.weatherapp;

import android.app.Application;

import com.example.ivan.weatherapp.di.AppComponent;
import com.example.ivan.weatherapp.di.ContextModule;
import com.example.ivan.weatherapp.di.DaggerAppComponent;

/**
 * Created by ivan
 */

public class WeatherApp extends Application {
    private static WeatherApp sInstance;
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (sInstance == null)
            sInstance = this;
        initAppComponent();
    }

    private void initAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static WeatherApp get() {
        return sInstance;
    }
}
