/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shivamdev.syncadapterdemo.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.shivamdev.syncadapterdemo.db.DbHelper;
import com.shivamdev.syncadapterdemo.main.LogToast;
import com.shivamdev.syncadapterdemo.main.MainApplication;
import com.shivamdev.syncadapterdemo.network.GpsPingData;
import com.shivamdev.syncadapterdemo.network.RetrofitAdapter;
import com.shivamdev.syncadapterdemo.network.SyncApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = "SyncAdapter";

    private DbHelper helper;
    // Define a variable to contain a content resolver instance
    private ContentResolver mContentResolver;
    private Context mContext;


    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        this.mContext = context;
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }


    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        insertData();
        List<GpsPingData> data = DbHelper.getInstance(mContext).getAllPingData();
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                apiCallToSync(data.get(i));
            }
        }
    }

    private void insertData() {
        //checking the Internet connection
        String geo="12.9325101" + "," + "77.60420";
        String routeId="1234";
        String sessionId="3430";
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
        Date newDate = currentCalendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final String formattedDate = dateFormat.format(newDate);
        String battery = "53";
        String imei = "358953063684813";


        final GpsPingData pingData = new GpsPingData();

        pingData.battery = battery;
        pingData.imei = imei;
        pingData.empId = routeId;
        pingData.geoLocation = geo;
        pingData.capturedDateTime = formattedDate;
        pingData.sessionId = sessionId;
        int count = 0;
        do {
            try {
                Thread.sleep(30 * 1000);
                DbHelper.getInstance(MainApplication.getApplication()).insertData(pingData);
            } catch (InterruptedException e) {
                LogToast.log(TAG, "Exception : " + e);
            }
            count++;
        } while (count < 5);
    }

    private void apiCallToSync(final GpsPingData gpsData) {
        SyncApi syncApi = RetrofitAdapter.get().getRetrofit().create(SyncApi.class);

        syncApi.syncGpsData(gpsData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                LogToast.log(TAG, "Data synced successfully.");
                helper = DbHelper.getInstance(mContext);
                helper.deleteUpdated(gpsData.capturedDateTime);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LogToast.log(TAG, "Data synced failed.");
            }
        });
    }
}
