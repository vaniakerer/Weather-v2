package com.example.ivan.weatherapp.di.app;

import com.example.ivan.weatherapp.api.DarkSkyApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ivan
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    public DarkSkyApi provideDarkSkyApi(Retrofit retrofit) {
        return retrofit.create(DarkSkyApi.class);
    }
}
