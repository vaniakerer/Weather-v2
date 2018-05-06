package com.example.ivan.weatherapp.presentation.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.ivan.weatherapp.R;
import com.example.ivan.weatherapp.WeatherApp;
import com.example.ivan.weatherapp.domain.main.WeatherInteractor;
import com.example.ivan.weatherapp.presentation.base.view.BaseActivity;
import com.example.ivan.weatherapp.utils.CustomAnimationUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements MainView {

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

    private ViewGroup splashContainer;
    private ImageView splashImage;

    private ObjectAnimator heigthAnimator;
    private ObjectAnimator widthAnimator;

    private AnimatorSet animatorSet;

    public static final String AUTHORITY = "com.example.ivan.weatherapp.provider";
    public static final String ACCOUNT_TYPE = "com.example.ivan.weatherapp";
    public static final String ACCOUNT = "dummyaccount";
    private Account mAccount;

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

/*        Bundle b = new Bundle();
        // Disable sync backoff and ignore sync preferences. In other words...perform sync NOW!
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                CreateSyncAccount(this), // Sync account
                AUTHORITY,                 // Content authority
                b);*/

        mAccount = CreateSyncAccount(this);

        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                2);

      /*  ins();
        stream();*/
    }

    protected void initViews() {
        weatherContainer = findViewById(R.id.weather_container);
        chageCity = findViewById(R.id.change_city);
        cityName = findViewById(R.id.city_name);
        temperatureTv = findViewById(R.id.current_temperature);
        windSpeedTv = findViewById(R.id.current_wind_speed);
        progressBar = findViewById(R.id.progress);
        splashContainer = findViewById(R.id.splash_container);
        splashImage = findViewById(R.id.splash_image);

        chageCity.setOnClickListener(view -> presenter.onChangeCityNameClick());

        animageSplash();
    }

    private void animageSplash() {
        animationUtils.animateCloudIcon(splashContainer, splashImage);
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

    private void ins() {
        Pattern pattern = Pattern.compile("([\\w\\.])+\\w+@{1}([\\w\\.-]+).{1}\\w{2,5}");
        Matcher matcher = pattern.matcher("asdasda@as.qw.www");
        Log.d("asfasf", matcher.matches() + "");
        Matcher matcher1 = pattern.matcher("as.s.w@asf.wwwwww");

        while (matcher1.find())
            Log.d("asfasf", matcher1.group() + " ");


        Pattern ipPattern = Pattern.compile("(\\d{1,3}\\.{1}){3}(\\d{1,3}\\:){1}\\d{4}");
        Matcher matcher2 = ipPattern.matcher("192.129.2.2:5555");
        Log.d("asfasf--", matcher2.matches() + " ");
        while (matcher2.find())
            Log.d("asfasf--", matcher2.group());

    }

    private void stream() {
        try {
            InputStream fileInputStream = getAssets().open("json.json");
            JsonReader jsonReader = new JsonReader(new InputStreamReader(fileInputStream));
            Gson gson = new GsonBuilder().create();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                Use use = gson.fromJson(jsonReader, Use.class);
                Log.d("User", use.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Use {
        String name;
        int age;
        long birt;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getBirt() {
            return birt;
        }

        public void setBirt(long birt) {
            this.birt = birt;
        }

        @Override
        public String toString() {
            return "name : " + name;
        }
    }

}
