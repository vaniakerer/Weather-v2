package com.example.ivan.weatherapp.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.business.main.exeption.CannotConvertAddressExeption;
import com.example.ivan.weatherapp.presentation.base.BasePresenter;

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

    public MainPresenter(WeatherInteractor weatherInteractor, AddressInterceptor addressInterceptor) {
        this.weatherInteractor = weatherInteractor;
        this.addressInterceptor = addressInterceptor;
    }

    public void loadWeather() {
        getViewState().showProgress();
        Disposable disposable = weatherInteractor.getWeather()
                .doOnError(Throwable::printStackTrace)
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

    public void onChangeCityNameClick() {
        Disposable disposable = weatherInteractor
                .getSavedCityName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> getViewState().showChangeCityNameDialog(s),
                        throwable -> getViewState().showChangeCityNameDialog());

        unsubscribeOnDestroy(disposable);
    }

    public void setCityName(String cityName) {
        Disposable disposable = weatherInteractor.clearDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> saveCity(cityName));
        unsubscribeOnDestroy(disposable);
    }

    private void saveCity(String cityName) {
        Disposable disposable = weatherInteractor
                .saveCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> loadWeather());
        unsubscribeOnDestroy(disposable);
    }
}
