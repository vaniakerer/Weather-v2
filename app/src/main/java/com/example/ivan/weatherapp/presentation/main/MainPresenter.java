package com.example.ivan.weatherapp.presentation.main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.entity.ui.Weather;
import com.example.ivan.weatherapp.presentation.base.BasePresenter;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
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
                    Log.d("AFSasfasfasf", "loadWeather: " + error.getClass().getName());
                    getViewState().hideProgress();
                    getViewState().showError(error.getMessage());
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
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> getViewState().showChangeCityNameDialog(s),
                        throwable -> getViewState().showChangeCityNameDialog());

        unsubscribeOnDestroy(disposable);
    }

    public void setCityName(String cityName) {
        Flowable clearDbObservable = weatherInteractor.clearDb();
        Flowable saveCityObservable = weatherInteractor.saveCityName(cityName);

        //TODO в теорії запис нового міста може спрацювати раніше, ніж видалення..
        Disposable disposable = Flowable.combineLatest(clearDbObservable, saveCityObservable, (o, o2) -> o)
                .subscribe(s -> loadWeather(), throwable -> getViewState().showError(throwable.toString()));

        unsubscribeOnDestroy(disposable);
    }
}
