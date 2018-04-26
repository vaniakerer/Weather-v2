package com.example.ivan.weatherapp.di.app;

import android.content.Context;
import android.location.Geocoder;

import com.example.ivan.weatherapp.api.ApiFactory;
import com.example.ivan.weatherapp.api.DarkSkyApi;
import com.example.ivan.weatherapp.di.weather.WeatherComponent;
import com.example.ivan.weatherapp.di.weather.WeatherModule;
import com.example.ivan.weatherapp.presentation.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import ru.arturvasilov.sqlite.rx.RxSQLite;

/**
 * Created by ivan
 */

@Singleton
@Component(modules = {ApiModule.class, RetrofitModule.class, ContextModule.class, UtilsModule.class})
public interface AppComponent {
    WeatherComponent plusWeatherComponent(WeatherModule weatherModule);
}
