package com.example.ivan.weatherapp.data.db;

import android.support.annotation.NonNull;

import com.example.ivan.weatherapp.BuildConfig;
import com.example.ivan.weatherapp.data.db.tables.CityTable;
import com.example.ivan.weatherapp.data.db.tables.WeatherTable;

import ru.arturvasilov.sqlite.core.SQLiteConfig;
import ru.arturvasilov.sqlite.core.SQLiteContentProvider;
import ru.arturvasilov.sqlite.core.SQLiteSchema;

/**
 * Created by ivan
 */

public class SQLiteProvider extends SQLiteContentProvider {
    private static final String DATABASE_NAME = BuildConfig.APPLICATION_ID + ".weatherApp";
    private static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

    @Override
    protected void prepareConfig(@NonNull SQLiteConfig config) {
        config.setDatabaseName(DATABASE_NAME);
        config.setAuthority(CONTENT_AUTHORITY);
    }

    @Override
    protected void prepareSchema(@NonNull SQLiteSchema schema) {
        schema.register(CityTable.TABLE);
        schema.register(WeatherTable.TABLE);
    }
}
