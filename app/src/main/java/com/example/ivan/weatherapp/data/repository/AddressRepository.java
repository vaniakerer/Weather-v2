package com.example.ivan.weatherapp.data.repository;

import android.location.Address;
import android.location.Geocoder;

import com.example.ivan.weatherapp.business.main.exeption.CannotConvertAddressExeption;

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

    public Observable<String> getLocationsByCityName(String citName) throws CannotConvertAddressExeption {

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(citName, 1);
            if (!addresses.isEmpty())
                return Observable.just(addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude());
        } catch (IOException e) {
            throw new CannotConvertAddressExeption();
        }
        throw new CannotConvertAddressExeption();
    }
}