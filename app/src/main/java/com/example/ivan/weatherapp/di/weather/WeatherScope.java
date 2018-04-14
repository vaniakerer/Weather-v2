package com.example.ivan.weatherapp.di.weather;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by ivan
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface WeatherScope {
}
