package com.example.ivan.weatherapp.di.app;

import android.content.Context;
import android.location.Geocoder;

import com.example.ivan.weatherapp.utils.Prefs;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan
 */

@Module
public class UtilsModule {
    @Provides
    @Singleton
    public Prefs providePreferences(Context context) {
        return new Prefs(context);
    }

    @Provides
    @Singleton
    public Geocoder getGeocoder(Context context) {
        return new Geocoder(context, Locale.US);
    }
}
