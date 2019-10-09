package com.github.lykmapipo.log;


import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArraySet;

import com.crashlytics.android.Crashlytics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

/**
 * Handy wrapper for {@link timber.log.Timber} and {@link com.crashlytics.android.Crashlytics}
 * to provide logging utility helpers
 *
 * @author lally elias<lallyelias87@gmail.com>
 * @version 0.1.0
 * @since 0.1.0
 */
public class Log {
    // constants
    public static final String TAG = Log.class.getSimpleName();
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_TAG = "tag";
    public static final String KEY_MESSAGE = "message";

    // copy of log levels
    public static final int VERBOSE = android.util.Log.VERBOSE; // 2
    public static final int DEBUG = android.util.Log.DEBUG; // 3
    public static final int INFO = android.util.Log.INFO; // 4
    public static final int WARN = android.util.Log.WARN; // 5
    public static final int ERROR = android.util.Log.ERROR; // 6
    public static final int ASSERT = android.util.Log.ASSERT; // 7

    // instances
    @VisibleForTesting
    static Boolean alreadyInitialized = false;
    @VisibleForTesting
    static Boolean allowCrashlytics = false;
    @VisibleForTesting
    static Set<Integer> ignoredLogLevels;
    @VisibleForTesting
    static Timber.DebugTree debugTree;
    @VisibleForTesting
    static CrashlyticsTree crashlyticsTree;

    /**
     * Initialize log internals
     *
     * @param disableCrashlytics whether to use crashlytics or debug tree.
     *                           if disable debug tree will be used otherwise
     *                           crashlytics will be used
     * @param ignoredLevels      log levels to ignore with crashlytics
     * @author lally elias<lallyelias87@gmail.com>
     * @since 0.1.0
     */
    public static synchronized void create(
            @NonNull Boolean disableCrashlytics,
            Integer... ignoredLevels
    ) {
        // setup timber & its trees
        if (!alreadyInitialized) {
            allowCrashlytics = !disableCrashlytics;
            ignoredLogLevels = ignoredLogLevels(ignoredLevels);

            // setup CrashlyticsTree
            if (allowCrashlytics) {
                if (debugTree != null) {
                    Timber.uproot(debugTree);
                    debugTree = null;
                }
                crashlyticsTree = new CrashlyticsTree();
                Timber.plant(crashlyticsTree);
            }

            // setup DebugTree
            else {
                if (crashlyticsTree != null) {
                    Timber.uproot(crashlyticsTree);
                    crashlyticsTree = null;
                }
                debugTree = new Timber.DebugTree();
                Timber.plant(debugTree);
            }

            alreadyInitialized = true;
        }
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

    /**
     * Log an assert exception.
     */
    public static synchronized void wtf(Throwable t) {
    }


    //
    // Utils
    //

    /**
     * Provide default ignored log levels
     *
     * @return {@link Set} of default ignored log levels
     * @author lally elias<lallyelias87@gmail.com>
     * @since 0.1.0
     */
    @VisibleForTesting
    static Set<Integer> defaultIgnoredLogLevels() {
        ArraySet<Integer> ignored = new ArraySet<Integer>();
        ignored.add(Log.VERBOSE);
        ignored.add(Log.DEBUG);
        ignored.add(Log.INFO);
        return ignored;
    }

    /**
     * Provide set of ignored log levels or defaults
     *
     * @param levels valid log level to ignore
     * @return {@link Set} of ignored log levels
     * @author lally elias<lallyelias87@gmail.com>
     * @since 0.1.0
     */
    @VisibleForTesting
    static Set<Integer> ignoredLogLevels(Integer... levels) {
        Set<Integer> defaults = defaultIgnoredLogLevels();
        try {
            List<Integer> provided = Arrays.asList(levels);
            ArraySet<Integer> ignored = new ArraySet<Integer>();
            ignored.addAll(provided);
            if (ignored.isEmpty()) {
                ignored.addAll(defaults);
            }
            return ignored;
        } catch (Exception e) {
            return defaults;
        }
    }

    //
    // Timber Trees
    //


    /**
     * {@link Crashlytics} tree for {@link Timber}
     *
     * @author lally elias<lallyelias87@gmail.com>
     * @version 0.1.0
     * @since 0.1.0
     */
    static class CrashlyticsTree extends Timber.Tree {

        @Override
        protected void log(
                int priority, @Nullable String tag,
                @NotNull String message, @Nullable Throwable t
        ) {
            // handle ignored levels
            if (ignoredLogLevels.contains(priority)) {
                return;
            }

            // add log custom keys
            Crashlytics.setInt(KEY_PRIORITY, priority);
            Crashlytics.setString(KEY_TAG, tag);
            Crashlytics.setString(KEY_MESSAGE, message);
            // TODO: pass additional custom properties

            // log
            if (t == null) {
                Crashlytics.logException(new Exception(message));
            } else {
                Crashlytics.logException(t);
            }
        }
    }
}
