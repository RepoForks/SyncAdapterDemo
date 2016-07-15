package com.shivamdev.syncadapterdemo.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by shivam on 14/7/16.
 */

public interface SyncApi {


    // change the endpoint to your
    @POST("/endpoint")
    Call<Void> syncGpsData(@Body GpsPingData pingData);

}
