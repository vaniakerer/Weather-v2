package com.example.ivan.weatherapp.api;

import android.support.annotation.NonNull;

import com.example.ivan.weatherapp.WeatherApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ivan Kerer
 */

public class ApiFactory {

    private static OkHttpClient sClient;
    private static Retrofit sBeroRetrofit;
    private static Retrofit sGoogleRetrofit;
    public static int cacheSize = 10 * 1024 * 1024; // 10 MiB

    private static DarkSkyApi sService;


    public static DarkSkyApi getUserService() {
        DarkSkyApi service = sService;

        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    sBeroRetrofit = buildBeroRetrofit();
                    service = sService = sBeroRetrofit.create(DarkSkyApi.class);
                }
            }
        }
        return service;
    }

    @NonNull
    private static Retrofit buildBeroRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://35.225.171.121:8080")
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Cache cache = new Cache(WeatherApp.get().getCacheDir(), cacheSize);

        return new OkHttpClient.Builder()
                .followRedirects(true)
                .addInterceptor(getInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    private static Interceptor getInterceptor() {
        Interceptor dateIntrceptor = null;

        dateIntrceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .build();


                return chain.proceed(request);
            }
        };
        return dateIntrceptor;
    }
}
