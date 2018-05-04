package com.example.ivan.weatherapp.data.mapper;

import com.example.ivan.weatherapp.data.database.model.DbCity;
import com.example.ivan.weatherapp.domain.model.City;
import com.example.ivan.weatherapp.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Created by ivan
 */

public class CityMapper {

    public Flowable<List<City>> mapDbCityToCity(RealmResults<DbCity> dbCities) {
        return Flowable.just(dbCities)
                .map(c -> {
                    List<City> cities = new ArrayList<>();
                    for (DbCity dbCity : c) {
                        City city = new City(dbCity.getCityName());
                        cities.add(city);
                    }
                    return cities;
                });
    }
}
