package com.shivamdev.syncadapterdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shivamdev.syncadapterdemo.network.GpsPingData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivam on 14/7/16.
 */

public class DbHelper extends SQLiteOpenHelper{


    private static final String GPS_PING = "gpstable";
    private static final String DATABASE_NAME = "GailSis";
    private final static String DATABASE_PATH = "/data/data/com.knowledgeflex.iops/databases/";
    private static final int DATABASE_VERSION = 1;

    private static final String IMEI = "IMEI";
    private static final String TIME = "Time";
    private static final String LATTITUDE = "Lat";
    private static final String LONGTITUDE = "Long";
    private static final String BATTERY = "BatteryStatus";
    private static final String SYNC = "SyncStatus";
    private static final String ROWID = "rowid";
    private static final String GPS_ROUTE_ID = "RouteId";
    private static final String GPS_SESSION_ID = "SessionId";

    private SQLiteDatabase db;
    private Context mContext;

    private static DbHelper instance;

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + GPS_PING + "(" + IMEI + " TEXT," + TIME + " TEXT," +
                 LATTITUDE + " TEXT," + LONGTITUDE + " TEXT," +
        BATTERY + " TEXT," + SYNC + " TEXT," + GPS_ROUTE_ID + " TEXT," + GPS_SESSION_ID + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + DATABASE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    public void insertData(GpsPingData data) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMEI, data.imei);
        values.put(TIME, data.capturedDateTime);
        String[] latLng = data.geoLocation.split(",");
        values.put(LATTITUDE, latLng[0]);
        values.put(LONGTITUDE, latLng[1]);
        values.put(BATTERY, data.battery);
        values.put(SYNC, "0");
        values.put(GPS_ROUTE_ID, data.empId);
        values.put(GPS_SESSION_ID, data.sessionId);
        db.insert(GPS_PING, null, values);
        db.close();
    }

    public List<GpsPingData> getAllPingData() {
        List<GpsPingData> Candidatelist = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + GPS_PING;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            GpsPingData members = new GpsPingData();
            members.imei = (cursor.getString(0));
            members.capturedDateTime = (cursor.getString(1));
            members.geoLocation = (cursor.getString(2) + "," + cursor.getString(3));
            members.battery = (cursor.getString(4));
            members.sessionId = (cursor.getString(6));
            members.empId = (cursor.getString(7));
            Candidatelist.add(members);

        }
        return Candidatelist;
    }

    public void deleteUpdated(String formattedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GPS_PING, "Time = ? ", new String[] { formattedDate });
    }
}
