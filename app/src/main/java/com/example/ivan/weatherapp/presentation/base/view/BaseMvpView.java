package com.example.ivan.weatherapp.presentation.base.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by ivan
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface BaseMvpView extends MvpView {
    void showError(String message);
}
