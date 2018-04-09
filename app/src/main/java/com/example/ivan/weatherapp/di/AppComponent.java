package com.example.ivan.weatherapp.di;

import com.example.ivan.weatherapp.presentation.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by ivan
 */

@Singleton
@Component(modules = {ApiModule.class, RetrofitModule.class, MainModule.class, ContextModule.class, UtilsModule.class})
public interface AppComponent {
    void injectMainActivity(MainActivity mainActivity);
}
