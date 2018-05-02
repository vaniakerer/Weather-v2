package com.example.ivan.weatherapp.presentation.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.ivan.weatherapp.R;
import com.example.ivan.weatherapp.WeatherApp;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;
import com.example.ivan.weatherapp.presentation.base.view.BaseActivity;
import com.example.ivan.weatherapp.utils.CustomAnimationUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements MainView {
    /*public static final String AUTHORITY = "com.example.ivan.weatherapp.provider";
    public static final String ACCOUNT_TYPE = "com.ivan.weather_app";
    public static final String ACCOUNT = "weatherapp_account";*/

    @InjectPresenter
    MainPresenter presenter;
    @Inject
    WeatherInteractor weatherInteractor;
    @Inject
    CustomAnimationUtils animationUtils;

    private ViewGroup weatherContainer;
    private TextView chageCity;
    private TextView temperatureTv;
    private TextView windSpeedTv;
    private TextView cityName;
    private ProgressBar progressBar;


    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.example.ivan.weatherapp.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.example.ivan.weatherapp";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(weatherInteractor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WeatherApp.get().plusWeatherComponent().injectMainActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        presenter.loadWeather();

     /*   Bundle b = new Bundle();
        // Disable sync backoff and ignore sync preferences. In other words...perform sync NOW!
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                CreateSyncAccount(this), // Sync account
                AUTHORITY,                 // Content authority
                b);*/

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
        animationUtils.animateInFromXml(temperatureTv);
        this.temperatureTv.setText(getString(R.string.city_temperature, temperature));
    }

    @Override
    public void showWindSpeed(String windSpeed) {
        animationUtils.animageInfl(windSpeedTv);
        this.windSpeedTv.setText(getString(R.string.wind_speed, windSpeed));
    }

    @Override
    public void showWeather() {

    }

    @Override
    public void showNoWeatherError() {
        temperatureTv.setText(getString(R.string.no_weather));
    }

    @Override
    public void showInputCityNameState() {
        this.chageCity.setText(getString(R.string.input_city_name));
    }

    @Override
    public void showChangeCityNameDialog(String cityName) {
        new MaterialDialog.Builder(MainActivity.this)
                .title(R.string.city_name)
                .input(getString(R.string.input_city_name), cityName, false, (dialog, input) -> {
                    presenter.setCityName(input.toString());
                })
                .build()
                .show();
    }

    @Override
    public void showChangeCityNameDialog() {
        new MaterialDialog.Builder(MainActivity.this)
                .title(R.string.city_name)
                .input(getString(R.string.input_city_name), "", false, (dialog, input) -> {
                    presenter.setCityName(input.toString());
                })
                .build()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeatherApp.get().clearWeatherComponent();
    }

    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }

        return newAccount;
    }
}
