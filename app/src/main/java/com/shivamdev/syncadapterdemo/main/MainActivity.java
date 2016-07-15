package com.shivamdev.syncadapterdemo.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shivamdev.syncadapterdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        MainApplication.getApplication().getSingleton().getSyncAdapterInstance().startForceSyncing(bundle);
    }
}