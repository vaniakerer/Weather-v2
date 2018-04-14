package com.example.ivan.weatherapp.presentation.main;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ivan.weatherapp.presentation.base.view.BaseLoadingView;
import com.example.ivan.weatherapp.presentation.base.view.BaseMvpView;

/**
 * Created by ivan
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends BaseLoadingView {

    void showCityName(String cityName);

    void showTemperature(String temperature);

    void showWindSpeed(String windSpeed);

    void showWeather();

    void showInputCityNameState();

    void showChangeCityNameDialog(String cityName);

    void showChangeCityNameDialog();
}