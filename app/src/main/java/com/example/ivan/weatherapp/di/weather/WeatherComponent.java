package com.example.ivan.weatherapp.di.weather;

import com.example.ivan.weatherapp.presentation.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by ivan
 */

@Subcomponent(modules = WeatherModule.class)
@WeatherScope
public interface WeatherComponent {
    void injectMainActivity(MainActivity mainActivity);
}
