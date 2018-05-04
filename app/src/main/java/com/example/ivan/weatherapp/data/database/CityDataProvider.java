package com.example.ivan.weatherapp.data.database;

import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.data.database.provider.RealmProvider;
import com.example.ivan.weatherapp.data.exception.NoSavedCityException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ivan
 */

public class CityDataProvider extends BaseDataProvider {

    public CityDataProvider(RealmProvider realmProvider) {
        super(realmProvider);
    }

    public Flowable<RealmResults<DbCity>> getSavedCity() {
        return Flowable.create(e -> {
            RealmResults<DbCity> cities = getRealm().where(DbCity.class).findAll();
            if (cities != null && !cities.isEmpty()) {
                e.onNext(cities);
            } else
                e.onError(new NoSavedCityException());
        }, BackpressureStrategy.LATEST);
    }

    public DbCity insert(DbCity dbCity) {
        getRealm().beginTransaction();
        DbCity returnedDbCity = null;
        try {
            returnedDbCity = getRealm().copyToRealm(dbCity);
        } catch (Exception e) {
            getRealm().cancelTransaction();
        } finally {
            getRealm().commitTransaction();
            getRealm().close();
        }

        return returnedDbCity;
    }

    public void delete(DbCity dbCity) {
        getRealm().beginTransaction();
        try {
            dbCity.deleteFromRealm();
        } catch (Exception e) {
            getRealm().cancelTransaction();
        } finally {
            getRealm().commitTransaction();
            getRealm().close();
        }
    }

    public boolean deleteAll() {
        getRealm().beginTransaction();
        boolean isDeleted = getRealm().where(DbCity.class).findAll().deleteAllFromRealm();
        getRealm().commitTransaction();
        getRealm().close();

        return isDeleted;
    }
}
