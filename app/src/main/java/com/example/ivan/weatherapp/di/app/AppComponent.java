package com.example.ivan.weatherapp.di.app;

import com.example.ivan.weatherapp.di.weather.WeatherComponent;
import com.example.ivan.weatherapp.di.weather.WeatherModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ivan
 */

@Singleton
@Component(modules = {ApiModule.class, RetrofitModule.class, ContextModule.class, UtilsModule.class})
public interface AppComponent {
    WeatherComponent plusWeatherComponent(WeatherModule weatherModule);
}
