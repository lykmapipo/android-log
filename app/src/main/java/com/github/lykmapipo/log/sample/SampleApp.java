package com.github.lykmapipo.log.sample;

import android.app.Application;

import com.github.lykmapipo.log.Log;


public class SampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /* initialize {@link Log} internal DebugTree */
        Log.create(BuildConfig.DEBUG, Log.VERBOSE, Log.DEBUG, Log.INFO);

        /* test: initialize {@link Log} internal CrashlyticsTree */
        // Log.create(!BuildConfig.DEBUG, Log.VERBOSE, Log.DEBUG, Log.INFO);

    }
}
