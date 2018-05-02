package com.example.ivan.weatherapp.business.repository;

import com.example.ivan.weatherapp.business.main.exeption.CannotConvertAddressExeption;

import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public interface AddressRepository {
    Flowable<String> getLocationsByCityName(String citName) throws CannotConvertAddressExeption;
}
