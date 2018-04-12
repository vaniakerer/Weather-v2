package com.example.ivan.weatherapp.di;

import com.example.ivan.weatherapp.presentation.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ivan
 */

@Singleton
@Component(modules = {ApiModule.class, RetrofitModule.class, MainModule.class, ContextModule.class, UtilsModule.class, SQLiteModule.class})
public interface AppComponent {
    void injectMainActivity(MainActivity mainActivity);
}
