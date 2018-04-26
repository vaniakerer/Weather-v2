package com.example.ivan.weatherapp.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.ivan.weatherapp.data.db.realm.DbCity;

import org.sqlite.database.sqlite.SQLiteDatabase;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;

/**
 * Created by ivan
 */

public class CityTable extends BaseTable<DbCity> {
    public static final Table<DbCity> TABLE = new CityTable();

    public static final String CITY_NAME = "city_name";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .textColumn(CITY_NAME)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull DbCity dbCity) {
        ContentValues values = new ContentValues();
        values.put(CITY_NAME, dbCity.getCityName());

        return values;
    }

    @NonNull
    @Override
    public DbCity fromCursor(@NonNull Cursor cursor) {
        DbCity dbCity = new DbCity();

        dbCity.setCityName(cursor.getString(cursor.getColumnIndex(CITY_NAME)));

        return dbCity;
    }
}
