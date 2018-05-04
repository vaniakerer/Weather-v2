package com.example.ivan.weatherapp;

import android.app.Application;
import android.util.Log;

import com.example.ivan.weatherapp.di.app.AppComponent;
import com.example.ivan.weatherapp.di.app.ContextModule;
import com.example.ivan.weatherapp.di.app.DaggerAppComponent;
import com.example.ivan.weatherapp.di.weather.WeatherComponent;
import com.example.ivan.weatherapp.di.weather.WeatherModule;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by ivan
 */

public class WeatherApp extends Application {
    private static WeatherApp sInstance;
    private static AppComponent sAppComponent;
    private static WeatherComponent sWeatherComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (sInstance == null)
            sInstance = this;

        initRealm();
        initStetho();
        initAppComponent();
    }

    private void initRealm() {
        Realm.init(this);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    private void initAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public WeatherComponent plusWeatherComponent() {
        if (sWeatherComponent == null)
            sWeatherComponent = sAppComponent.plusWeatherComponent(new WeatherModule());

        return sWeatherComponent;
    }

    public void clearWeatherComponent() {
        sWeatherComponent = null;
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static WeatherApp get() {
        return sInstance;
    }
}
