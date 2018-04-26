package com.example.ivan.weatherapp.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.ivan.weatherapp.data.db.realm.DbWeather;

import org.sqlite.database.sqlite.SQLiteDatabase;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;

/**
 * Created by ivan
 */

public class WeatherTable extends BaseTable<DbWeather> {

    public static final Table<DbWeather> TABLE = new WeatherTable();

    public static final String TEMPETARURE = "temperature";
    public static final String WIND_SPEED = "wind_speed";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .realColumn(TEMPETARURE)
                .realColumn(WIND_SPEED)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull DbWeather dbWeather) {
        ContentValues values = new ContentValues();

        values.put(TEMPETARURE, dbWeather.getTemperature());
        values.put(WIND_SPEED, dbWeather.getWindSpeed());

        return values;
    }

    @NonNull
    @Override
    public DbWeather fromCursor(@NonNull Cursor cursor) {
        DbWeather dbWeather = new DbWeather();

        dbWeather.setTemperature(cursor.getDouble(cursor.getColumnIndex(TEMPETARURE)));
        dbWeather.setWindSpeed(cursor.getDouble(cursor.getColumnIndex(WIND_SPEED)));

        return dbWeather;
    }
}
