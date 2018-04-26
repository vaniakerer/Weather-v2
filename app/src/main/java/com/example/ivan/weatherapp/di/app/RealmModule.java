package com.example.ivan.weatherapp.di.app;

import android.content.Context;

import com.example.ivan.weatherapp.data.db.realm.provider.RealmProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan
 */

@Module
public class RealmModule {
    @Provides
    @Singleton
    public RealmProvider provideRealmProvider(Context context){
        return new RealmProvider(context);
    }
}
