package com.example.ivan.weatherapp.presentation.main;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.InjectViewState;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.StorageInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.presentation.base.BasePresenter;
import com.example.ivan.weatherapp.utils.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
        Disposable disposable = storageInterceptor.getSavedCityName()
                .doOnError(Throwable::printStackTrace)
                .subscribe(this::mapAddressAndLoadWeather, throwable -> getViewState().showInputCityNameState());

        unsubscribeOnDestroy(disposable);
    }

    private void mapAddressAndLoadWeather(String s) {
        if (TextUtils.isEmpty(s))
            getViewState().showInputCityNameState();
        else {
            Disposable disposable = addressInterceptor.getLocationFromCityName(s)
                    .subscribe(this::loadWeather, error -> {
                        getViewState().showError(error.getMessage());
                    });
            unsubscribeOnDestroy(disposable);
        }
    }

    private void loadWeather(String coords) {
        getViewState().showProgress();
        Disposable disposable = weatherInteractor.getWeather(coords)
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

        unsubscribeOnDestroy(disposable);
    }

    public void setCityName(String cityName) {
        Disposable disposable = storageInterceptor.clearDb()
                .subscribe(o -> saveCity(cityName));
        unsubscribeOnDestroy(disposable);
    }

    private void saveCity(String cityName) {
        Disposable disposable = storageInterceptor.saveCityName(cityName).subscribe(o -> mapAddressAndLoadWeather(cityName));
        unsubscribeOnDestroy(disposable);
    }
}
