package com.example.ivan.weatherapp.presentation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.ivan.weatherapp.presentation.service.sync_adapter.WeatherSyncAdapter;

/**
 * Created by ivan
 */

public class WeatherSyncService extends Service {
    private static WeatherSyncAdapter sSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new WeatherSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}

