package com.example.ivan.weatherapp.presentation.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.ivan.weatherapp.R;
import com.example.ivan.weatherapp.WeatherApp;
import com.example.ivan.weatherapp.business.main.AddressInterceptor;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.presentation.base.view.BaseActivity;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements MainView {
    @InjectPresenter
    MainPresenter presenter;
    @Inject
    WeatherInteractor weatherInteractor;
    @Inject
    AddressInterceptor addressInterceptor;

    private ViewGroup weatherContainer;
    private TextView chageCity;
    private TextView temperatureTv;
    private TextView windSpeedTv;
    private TextView cityName;
    private ProgressBar progressBar;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(weatherInteractor, addressInterceptor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WeatherApp.get().plusWeatherComponent().injectMainActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        presenter.loadWeather();
    }


    protected void initViews() {
        weatherContainer = findViewById(R.id.weather_container);
        chageCity = findViewById(R.id.change_city);
        cityName = findViewById(R.id.city_name);
        temperatureTv = findViewById(R.id.current_temperature);
        windSpeedTv = findViewById(R.id.current_wind_speed);
        progressBar = findViewById(R.id.progress);

        chageCity.setOnClickListener(view -> presenter.onChangeCityNameClick());
    }

    @Override
    public void showProgress() {
        weatherContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        weatherContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showErrorMessage(message);
    }

    @Override
    public void showCityName(String cityName) {
        this.cityName.setText(cityName);
    }

    @Override
    public void showTemperature(String temperature) {
        this.temperatureTv.setText(getString(R.string.city_temperature, temperature));
    }

    @Override
    public void showWindSpeed(String windSpeed) {
        this.windSpeedTv.setText(getString(R.string.wind_speed, windSpeed));
    }

    @Override
    public void showWeather() {

    }

    @Override
    public void showInputCityNameState() {
        this.chageCity.setText(getString(R.string.input_city_name));
    }

    @Override
    public void showChangeCityNameDialog(String cityName) {
        new MaterialDialog.Builder(MainActivity.this)
                .title(R.string.city_name)
                .input(getString(R.string.input_city_name), cityName, false, (dialog, input) -> presenter.setCityName(input.toString()))
                .build()
                .show();
    }

    @Override
    public void showChangeCityNameDialog() {
        new MaterialDialog.Builder(MainActivity.this)
                .title(R.string.city_name)
                .input(getString(R.string.input_city_name), "", false, (dialog, input) -> presenter.setCityName(input.toString()))
                .build()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeatherApp.get().clearWeatherComponent();
    }
}
