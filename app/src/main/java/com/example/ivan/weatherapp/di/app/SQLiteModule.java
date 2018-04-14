package com.example.ivan.weatherapp.di.app;

import android.support.annotation.StringRes;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.rx.RxSQLite;

/**
 * Created by ivan
 */

@Module
public class SQLiteModule {

    @Provides
    @Singleton
    public RxSQLite provideSqlite() {
        return RxSQLite.get();
    }
}
