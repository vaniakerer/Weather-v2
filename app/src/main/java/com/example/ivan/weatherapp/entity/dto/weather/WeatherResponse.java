package com.example.ivan.weatherapp.entity.dto.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ivan
 */

public class WeatherResponse {
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("currently")
    @Expose
    private CurrentlyWeather currentlyDTO;
    @SerializedName("daily")
    @Expose
    private DailyWeather dailyDTO;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public Integer getOffset() {
        return offset;
    }

    public CurrentlyWeather getCurrentlyDTO() {
        return currentlyDTO;
    }

    public DailyWeather getDailyDTO() {
        return dailyDTO;
    }
}
