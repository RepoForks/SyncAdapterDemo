package com.shivamdev.syncadapterdemo.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shivam on 14/7/16.
 */

public class RetrofitAdapter {

    private Retrofit retrofit;
    private static RetrofitAdapter adapter;



    private RetrofitAdapter() {
        retrofit = retrofit();
    }

    public static synchronized RetrofitAdapter get() {
        if (adapter == null) {
            adapter = new RetrofitAdapter();
        }
        return adapter;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getInstance().getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return client;
    }



}
