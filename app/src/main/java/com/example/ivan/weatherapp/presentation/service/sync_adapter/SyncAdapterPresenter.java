package com.example.ivan.weatherapp.presentation.service.sync_adapter;

import android.util.Log;

import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.entity.ui.Weather;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by ivan
 */

public class SyncAdapterPresenter {
    private WeatherInteractor weatherInteractor;
    private SyncAdapterView view;
    private CompositeDisposable compositeDisposable;

    public SyncAdapterPresenter(SyncAdapterView view, WeatherInteractor weatherInteractor) {
        this.view = view;
        this.weatherInteractor = weatherInteractor;
    }

    public void loadWeather() {
        weatherInteractor
                .getWeather()
                .subscribe(new DisposableSubscriber<Weather>() {

                    @Override
                    public void onNext(Weather weather) {
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }
}
