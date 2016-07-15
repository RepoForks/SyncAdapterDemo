package com.shivamdev.syncadapterdemo.main;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by shivam on 14/7/16.
 */

public class MainApplication extends Application {

    private static MainApplication application;


    public static MainApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }

    public MainSingleton getSingleton() {
        return MainSingleton.getInstance();
    }
}
