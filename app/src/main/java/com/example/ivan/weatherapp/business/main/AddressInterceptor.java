package com.example.ivan.weatherapp.business.main;

import com.example.ivan.weatherapp.data.repository.AddressRepository;

import io.reactivex.Observable;


/**
 * Created by ivan
 */

public class AddressInterceptor {
    private AddressRepository addressRepository;

    public AddressInterceptor(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Observable<String> getLocationFromCityName(String cityName) {
        return addressRepository.getLocationsByCityName(cityName);
    }
}
