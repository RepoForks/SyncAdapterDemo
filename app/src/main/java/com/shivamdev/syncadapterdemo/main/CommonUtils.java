package com.shivamdev.syncadapterdemo.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;

/**
 * Created by shivam on 14/7/16.
 */

public class CommonUtils {

    public static String getBatteryLevel(Context context) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent status = context.registerReceiver(null, intentFilter);
        int level = status.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        return String.valueOf(level);
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephonyManager.getDeviceId();
    }

}
