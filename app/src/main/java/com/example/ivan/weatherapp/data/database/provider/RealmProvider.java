package com.example.ivan.weatherapp.data.database.provider;

import android.content.Context;

import com.example.ivan.weatherapp.data.database.migration.DbMigration;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ivan
 */

public class RealmProvider {


    @Inject
    public RealmProvider(Context application) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("weather.realm")
               // .migration(new DbMigration())
                .schemaVersion(4)
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }


    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }

}
