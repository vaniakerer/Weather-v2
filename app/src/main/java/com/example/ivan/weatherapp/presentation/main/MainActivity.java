package com.example.ivan.weatherapp.presentation.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
                60 * 60);

        Observable.create(e -> {
            readFile();
            readJson();
            regex();

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }

        return newAccount;
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

    private void readJson() {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getAssets().open("json.json")));
            jsonReader.beginArray();
            long lines = 0;
            while (jsonReader.hasNext()) {
                lines++;
                Names a = new Gson().fromJson(jsonReader, Names.class);
                Log.d("testJson", lines + " " + a.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
       /* File sdcard = Environment.getExternalStorageDirectory();

        File file = new File(sdcard, "large.txt");
        InputStream inputStream = null;
        BufferedReader reader = null;*/
        BufferedReader reader = null;
        long timeBefore = System.currentTimeMillis();
        long lines = 0;
        try {
           // inputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(getAssets().open("large.txt"), 1024 * 8);
            reader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.d("test", " : " + lines + " " + line);
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Log.d("test", "Time: " + lines + " : " + (System.currentTimeMillis() - timeBefore) + " ");

    }

    private void regex() {

        Pattern emailPattern = Pattern.compile("(\\w+\\.\\w+)+@([\\w+\\.])+(\\w+\\.\\w{2,6})");
        Matcher emailMatcher = emailPattern.matcher("asfas.fs.asas@a.ww.as");
        Log.d("sasfasfMail", emailMatcher.matches() + "");
        emailMatcher = emailPattern.matcher("asfas.fs.asas@a.wws.assswss");
        Log.d("sasfasfMail", emailMatcher.matches() + "");
        emailMatcher = emailPattern.matcher("asfas.fs.asas@a.ww.aa");
        Log.d("sasfasfMail", emailMatcher.matches() + "");
        emailMatcher = emailPattern.matcher("asfas.fs.asas@.as");
        Log.d("sasfasfMail", emailMatcher.matches() + "");


        Pattern searchPattern = Pattern.compile("\\d{3}Searched\\.");
        Matcher searchMatcher = searchPattern.matcher("234Searched. 222.2Searched. 333Searched");
        while (searchMatcher.find())
            Log.d("sasfasfSearcj", searchMatcher.group());

    }
}
