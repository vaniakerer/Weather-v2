package com.example.ivan.weatherapp.data.db.realm.provider;

import android.app.Application;
import android.content.Context;

import java.util.Calendar;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmCollection;
import io.realm.RealmConfiguration;

/**
 * Created by ivan
 */

public class RealmProvider {


    @Inject
    public RealmProvider(Context application) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("weather.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }


    public Realm getRealm(){
        return Realm.getDefaultInstance();
    }

}
