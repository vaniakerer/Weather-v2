package com.example.ivan.weatherapp.di.app;

import android.content.Context;
import android.location.Geocoder;

import com.example.ivan.weatherapp.utils.CustomAnimationUtils;

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
    public Geocoder provideGeocoder(Context context) {
        return new Geocoder(context, Locale.US);
    }

    @Provides
    @Singleton
    public CustomAnimationUtils provideCustomAnimationUtils(){
        return new CustomAnimationUtils();
    }
}
