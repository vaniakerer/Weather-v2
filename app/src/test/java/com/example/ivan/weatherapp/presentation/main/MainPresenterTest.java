package com.example.ivan.weatherapp.presentation.main;

import com.example.ivan.weatherapp.domain.main.WeatherInteractor;
import com.example.ivan.weatherapp.domain.model.Weather;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by ivan
 */
public class MainPresenterTest extends TestCase {

    private static final String TEST_SAVED_CITY_NAME = "Kyiv";

    @Mock
    WeatherInteractor weatherInteractor;
    @Mock
    MainView mainView;
    @Mock
    MainView$$State mainView$$State;

    MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(s -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

        when(weatherInteractor.getWeather())
                .thenReturn(Observable.just(new Weather(12, 23)))
                .thenReturn(Observable.empty());

        when(weatherInteractor.getSavedCityName()).thenReturn(Observable.just(TEST_SAVED_CITY_NAME));

        when(weatherInteractor.clearDb()).thenReturn(Observable.just(1));
        when(weatherInteractor.saveCityName(anyString())).thenReturn(Observable.just(1));

        mainPresenter = new MainPresenter(weatherInteractor);
        mainPresenter.attachView(mainView);
        mainPresenter.setViewState(mainView$$State);
    }

    @Test
    public void nothingHappensInitially() throws Exception {
        verifyNoMoreInteractions(mainView);
        verifyNoMoreInteractions(weatherInteractor);
    }

    @Test
    public void testSuccessLoadWeather() throws Exception {
        mainPresenter.loadWeather();
        verify(mainView$$State).showProgress();
        verify(weatherInteractor).getWeather();
        verify(mainView$$State).showTemperature("12.0");
        verify(mainView$$State).showWindSpeed("23.0");
        verify(mainView$$State).hideProgress();
    }

    @Test
    public void testOnChangeCityname() {
        mainPresenter.onChangeCityNameClick();
        verify(mainView$$State).showChangeCityNameDialog(TEST_SAVED_CITY_NAME);
        verify(mainView$$State, never()).showChangeCityNameDialog();
    }

    @Test
    public void testSaveCityName() throws Exception {
        mainPresenter.setCityName(TEST_SAVED_CITY_NAME);
        verify(weatherInteractor).clearDb();
        verify(weatherInteractor).saveCityName(TEST_SAVED_CITY_NAME);
        verify(weatherInteractor, times(2)).getWeather();
    }
}