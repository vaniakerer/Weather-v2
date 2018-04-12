package com.example.ivan.weatherapp;

import android.app.Application;

import com.example.ivan.weatherapp.data.db.SQLiteProvider;
import com.example.ivan.weatherapp.di.AppComponent;
import com.example.ivan.weatherapp.di.ContextModule;
import com.example.ivan.weatherapp.di.DaggerAppComponent;
import com.facebook.stetho.Stetho;

import ru.arturvasilov.sqlite.core.SQLite;

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

        initSQLite();
        initStetho();
        initAppComponent();
    }

    private void initSQLite() {
        SQLite.initialize(this);
    }

    private void initStetho() {
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
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
