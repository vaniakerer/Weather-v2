package com.example.ivan.weatherapp.presentation.base.view;

/**
 * Created by ivan
 */

public interface BaseLoadingView extends BaseMvpView {
    void showProgress();

    void hideProgress();
}
