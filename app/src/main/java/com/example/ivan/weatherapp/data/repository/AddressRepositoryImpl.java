package com.example.ivan.weatherapp.data.repository;

import android.location.Address;
import android.location.Geocoder;

import com.example.ivan.weatherapp.business.main.exeption.CannotConvertAddressExeption;
import com.example.ivan.weatherapp.business.repository.AddressRepository;

import java.io.IOException;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by ivan
 */

public class AddressRepositoryImpl implements AddressRepository {

    private Geocoder geocoder;

    public AddressRepositoryImpl(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Override
    public Flowable<String> getLocationsByCityName(String citName) throws CannotConvertAddressExeption {

        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(citName, 1);
            if (!addresses.isEmpty())
                return Flowable.just(addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude());
        } catch (IOException e) {
            throw new CannotConvertAddressExeption();
        }
        throw new CannotConvertAddressExeption();
    }
}