package com.example.ivan.weatherapp.presentation.service.sync_adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ivan.weatherapp.WeatherApp;
import com.example.ivan.weatherapp.business.main.WeatherInteractor;

import javax.inject.Inject;

/**
 * Created by ivan
 */

public class WeatherSyncAdapter extends AbstractThreadedSyncAdapter implements SyncAdapterView{

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

        presenter = new SyncAdapterPresenter(this, weatherInteractor);
        presenter.loadWeather();
        Log.d("Sync", "Sync");

    }
}
