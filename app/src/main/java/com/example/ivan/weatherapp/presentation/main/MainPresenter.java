package com.example.ivan.weatherapp.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.business.main.exeption.CannotConvertAddressExeption;
import com.example.ivan.weatherapp.business.main.exeption.NoCityNameExeption;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.example.ivan.weatherapp.presentation.base.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ivan
 */
@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    private WeatherInteractor weatherInteractor;

    public MainPresenter(WeatherInteractor weatherInteractor) {
        this.weatherInteractor = weatherInteractor;
    }

    public void loadWeather() {
        getViewState().showProgress();
        Disposable disposable = weatherInteractor.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weather -> {
                    getViewState().hideProgress();
                    handleSuccessWeatherLoad(weather);
                }, error -> {
                    getViewState().hideProgress();
                }, () -> {
                });


        unsubscribeOnDestroy(disposable);
    }

    private void handleSuccessWeatherLoad(Weather weather) {
        if (weather != null) {
            getViewState().showTemperature(String.valueOf(weather.getTemperature()));
            getViewState().showWindSpeed(String.valueOf(weather.getSpeed()));
        } else
            getViewState().showNoWeatherError();
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


        Observable clearDbObservable = weatherInteractor.clearDb();
        Observable saveCityObservable = weatherInteractor.saveCityName(cityName);

        Disposable disposable = Observable.concat(clearDbObservable, saveCityObservable)
                .subscribe(s -> loadWeather());

        unsubscribeOnDestroy(disposable);
    }
}
