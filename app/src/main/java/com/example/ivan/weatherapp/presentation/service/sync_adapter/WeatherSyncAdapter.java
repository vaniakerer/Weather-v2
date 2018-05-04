package com.example.ivan.weatherapp.presentation.service.sync_adapter;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ivan.weatherapp.WeatherApp;
import com.example.ivan.weatherapp.domain.main.WeatherInteractor;

import javax.inject.Inject;

/**
 * Created by ivan
 */

public class WeatherSyncAdapter extends AbstractThreadedSyncAdapter implements SyncAdapterView {

    @Inject
    WeatherInteractor weatherInteractor;

    private SyncAdapterPresenter presenter;
    private ContentResolver contentResolver;

    public WeatherSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        WeatherApp.get().plusWeatherComponent().injectWeatherSyncAdapter(this);
        contentResolver = context.getContentResolver();
    }

    public WeatherSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        WeatherApp.get().plusWeatherComponent().injectWeatherSyncAdapter(this);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.d("Sync", "Sync");
        presenter = new SyncAdapterPresenter(this, weatherInteractor);
        presenter.loadWeather();

        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(5000);
        }
    }
}
