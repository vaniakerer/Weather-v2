package com.example.ivan.weatherapp.bussiness.main;

import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.data.repository.AddressRepository;
import com.example.ivan.weatherapp.data.repository.StorageRepository;
import com.example.ivan.weatherapp.data.repository.WeatherRepository;
import com.example.ivan.weatherapp.entity.db.DbCity;
import com.example.ivan.weatherapp.entity.db.DbWeather;
import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by ivan
 */

@RunWith(MockitoJUnitRunner.class)
public class WeatherInteractorTest {

    private static final String TEST_CITY_NAME = "Kyiv";

    @Mock
    WeatherRepository weatherRepository;
    @Mock
    StorageRepository storageRepository;
    @Mock
    AddressRepository addressRepository;
    @Mock
    WeatherResponse weatherResponse;

    List<DbCity> savedWeather = new ArrayList<>();

    WeatherInteractor weatherInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(s -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

        savedWeather.add(new DbCity(TEST_CITY_NAME));

        when(weatherRepository.getWeather(anyString())).thenReturn(Observable.just(weatherResponse));

        when(storageRepository.getSavedWeather()).thenReturn(Observable.just(new DbWeather()));
        when(storageRepository.getSavedCityName()).thenReturn(Observable.just(savedWeather));
        when(storageRepository.clearWeather()).thenReturn(Observable.just(1));
        when(addressRepository.getLocationsByCityName(anyString())).thenReturn(Observable.just("1,1"));


        weatherInteractor = new WeatherInteractor(weatherRepository, storageRepository, addressRepository);

    }

    @Test
    public void nothingHappenedInitially() throws Exception {
        verifyZeroInteractions(storageRepository);
        verifyZeroInteractions(addressRepository);
    }

    @Test
    public void testGetWeather() throws Exception {
        TestObserver testObserver = weatherInteractor.getWeather().test();
        testObserver.awaitTerminalEvent();

        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        verify(storageRepository).getSavedCityName();
        verify(weatherRepository).getWeather(anyString());
        verify(storageRepository).clearWeather();
        verify(storageRepository, never()).clearDb();
    }

    @Test
    public void testGetCityName() throws Exception {
        TestObserver testObserver = weatherInteractor.getSavedCityName().test();
        testObserver.awaitTerminalEvent();

        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        verify(storageRepository).getSavedCityName();

        testObserver.assertValue(o -> o.equals(TEST_CITY_NAME));
    }
}
