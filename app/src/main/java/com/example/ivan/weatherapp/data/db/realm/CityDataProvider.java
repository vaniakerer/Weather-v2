package com.example.ivan.weatherapp.data.db.realm;

import com.example.ivan.weatherapp.data.db.realm.provider.RealmProvider;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ivan
 */

public class CityDataProvider extends BaseDataProvider {

    public CityDataProvider(RealmProvider realmProvider) {
        super(realmProvider);
    }

    public RealmResults<DbCity> getSavedCity() {
        RealmResults<DbCity> dbCityes = getRealm().where(DbCity.class).findAll();
        return dbCityes;
    }

    public DbCity insert(DbCity dbCity) {
        getRealm().beginTransaction();
        DbCity returnedDbCity = null;

        try {
            returnedDbCity = getRealm().copyToRealm(dbCity);
            getRealm().commitTransaction();
        } catch (Exception e) {
            getRealm().cancelTransaction();
        } finally {
            getRealm().close();
        }

        return returnedDbCity;
    }

    public void delete(DbCity dbCity) {
        getRealm().beginTransaction();
        try {
            dbCity.deleteFromRealm();
        } catch (Exception e) {
            getRealm().commitTransaction();
        } finally {
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
