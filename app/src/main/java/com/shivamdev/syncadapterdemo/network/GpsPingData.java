package com.shivamdev.syncadapterdemo.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shivam on 14/7/16.
 */

public class GpsPingData {

    @SerializedName("EmployeeId")
    public String empId;

    @SerializedName("IMEI")
    public String imei;

    @SerializedName("CapturedDatetime")
    public String capturedDateTime;

    @SerializedName("GeoLocation")
    public String geoLocation;

    @SerializedName("BatteryPercentage")
    public String battery;

    @SerializedName("sessionId")
    public String sessionId;
}