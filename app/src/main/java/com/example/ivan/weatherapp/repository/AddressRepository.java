package com.example.ivan.weatherapp.repository;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ivan
 */

public class AddressRepository {

    private Geocoder geocoder;

    public AddressRepository(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public Observable<String> getLocationsByCityName(String citName) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(citName, 1);
            if (!addresses.isEmpty()) {
               return Observable.just(addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude());
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        return Observable.just("1,1");//default value
    }


}