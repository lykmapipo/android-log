package com.github.lykmapipo.log;


import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Log
 * <p>
 * Handy wrapper for {@link timber.log.Timber} and {@link com.crashlytics.android.Crashlytics}
 * to provide logging utility helpers
 * </p>
 *
 * @author lally elias<lallyelias87@gmail.com>
 * @version 0.1.0
 * @since 0.1.0
 */
public class Log {
    public static final String TAG = Log.class.getSimpleName();
    // TODO: Timber instance
    // TODO: Crashlytics instance

    public static synchronized void create(@NonNull Context context, String... ignoredLevels) {
        // TODO: create timber tree
    }

    /**
     * Log a verbose exception.
     */
    public static synchronized void v(Throwable t) {

    }

    /**
     * Log a debug exception.
     */
    public static synchronized void d(Throwable t) {

    }

    /**
     * Log an info exception.
     */
    public static synchronized void i(Throwable t) {

    }

    /**
     * Log a warning exception.
     */
    public static synchronized void w(Throwable t) {
    }

    /**
     * Log an error exception.
     */
    public static synchronized void e(Throwable t) {

    }
}
