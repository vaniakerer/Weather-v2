package com.example.ivan.weatherapp.data.database;

import com.example.ivan.weatherapp.data.database.provider.RealmProvider;

import io.realm.Realm;

/**
 * Created by ivan
 */

public class BaseDataProvider {
    private RealmProvider realmProvider;

    public BaseDataProvider(RealmProvider realmProvider) {
        this.realmProvider = realmProvider;
    }

    protected Realm getRealm() {
        return realmProvider.getRealm();
    }
}
