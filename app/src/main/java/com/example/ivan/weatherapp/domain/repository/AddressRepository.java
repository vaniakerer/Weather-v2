package com.example.ivan.weatherapp.domain.repository;

import com.example.ivan.weatherapp.domain.main.exeption.CannotConvertAddressExeption;

import io.reactivex.Flowable;

/**
 * Created by ivan
 */

public interface AddressRepository {
    Flowable<String> getLocationsByCityName(String citName) throws CannotConvertAddressExeption;
}
