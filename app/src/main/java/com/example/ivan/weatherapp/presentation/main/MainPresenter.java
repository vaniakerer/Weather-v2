package com.example.ivan.weatherapp.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.StorageInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.presentation.base.BasePresenter;
import com.example.ivan.weatherapp.utils.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ivan
 */
@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    private WeatherInteractor weatherInteractor;
    private AddressInterceptor addressInterceptor;
    private StorageInterceptor storageInterceptor;

    public MainPresenter(WeatherInteractor weatherInteractor, AddressInterceptor addressInterceptor, StorageInterceptor storageInterceptor) {
        this.weatherInteractor = weatherInteractor;
        this.addressInterceptor = addressInterceptor;
        this.storageInterceptor = storageInterceptor;
    }

    public void init() {
        storageInterceptor.getSavedCityName()
                .subscribe(this::getCoordsForomAddress);
    }

    private void getCoordsForomAddress(String s) {
        if (TextUtils.isEmpty(s))
            getViewState().showInputCityNameState();
        else
            addressInterceptor.getLocationFromCityName(s)
                    .subscribe(this::loadWeatherFrom, error -> {
                        getViewState().showError(error.getMessage());
                    });
    }

    private void loadWeatherFrom(String coords) {
        getViewState().showProgress();
        weatherInteractor.getWeather(coords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weather -> {
                    getViewState().hideProgress();
                    getViewState().showTemperature(String.valueOf(weather.getTemperature()));
                    getViewState().showWindSpeed(String.valueOf(weather.getSpeed()));
                }, error -> {
                    getViewState().hideProgress();
                    getViewState().showError("Error");
                });
    }

    public void setCityName(String cityName) {
        storageInterceptor.saveCityName(cityName);
        loadWeatherFrom(cityName);
    }

}
