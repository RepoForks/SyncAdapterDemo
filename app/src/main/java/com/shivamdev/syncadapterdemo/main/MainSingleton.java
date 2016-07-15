package com.shivamdev.syncadapterdemo.main;

import com.shivamdev.syncadapterdemo.sync.SyncAdapterInitialization;

/**
 * Created by shivam on 14/7/16.
 */

public class MainSingleton {

    private static MainSingleton instance;
    private SyncAdapterInitialization syncAdapterInstance;

    private MainSingleton() {

    }

    public static MainSingleton getInstance() {
        if (instance == null) {
            instance = new MainSingleton();
        }
        return instance;
    }

    public SyncAdapterInitialization getSyncAdapterInstance() {
        if(this.syncAdapterInstance == null){
            return new SyncAdapterInitialization(MainApplication.getApplication());
        }
        return syncAdapterInstance;
    }
}