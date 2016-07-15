package com.shivamdev.syncadapterdemo.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by shivam on 14/7/16.
 */

public interface SyncApi {

    @POST("/Gail/api/GPSPing")
    Call<Void> syncGpsData(@Body GpsPingData pingData);

}
