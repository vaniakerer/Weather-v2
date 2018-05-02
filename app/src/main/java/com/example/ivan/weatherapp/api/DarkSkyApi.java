package com.example.ivan.weatherapp.api;

import com.example.ivan.weatherapp.entity.dto.weather.WeatherResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ivan
 */

public interface DarkSkyApi {
    @GET("6fe704e6b0c6a571be927f4b3b1adf1c/{coordinates}?exclude=minutely,hourly,alerts,flags&&lang=uk")
    Flowable<WeatherResponse> getCityInfo(@Path("coordinates") String coordinates);
}
