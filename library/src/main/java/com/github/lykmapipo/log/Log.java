package com.github.lykmapipo.log;


import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArraySet;

import com.crashlytics.android.Crashlytics;
import com.github.lykmapipo.common.Common;
import com.github.lykmapipo.common.provider.Provider;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import timber.log.Timber;

/**
 * Handy wrapper for {@link timber.log.Timber} and
 * {@link com.crashlytics.android.Crashlytics}
 * to provide logging utility helpers
 *
 * @author lally elias<lallyelias87@gmail.com>
 * @version 0.1.0
 * @since 0.1.0
 */
public class Log {
    // log levels
    public static final int VERBOSE = android.util.Log.VERBOSE; // 2
    public static final int DEBUG = android.util.Log.DEBUG; // 3
    public static final int INFO = android.util.Log.INFO; // 4
    public static final int WARN = android.util.Log.WARN; // 5
    public static final int ERROR = android.util.Log.ERROR; // 6
    public static final int ASSERT = android.util.Log.ASSERT; // 7

    // common keys
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_TAG = "tag";
    private static final String KEY_MESSAGE = "message";

    // instances
    @VisibleForTesting
    static CrashlyticsTree crashlyticsTree;
    @VisibleForTesting
    static Timber.DebugTree debugTree;
    @VisibleForTesting
    static Provider appProvider;


    private Log() {
        throw new AssertionError("No instances.");
    }

    /**
     * Initialize log internals
     *
     * @param provider valid application provider
     * @since 0.3.0
     */
    public static synchronized void of(@NonNull Provider provider) {
        // setup timber & its trees
        if (appProvider == null) {
            appProvider = provider;

            // setup CrashlyticsTree
            if (!appProvider.isDebug()) {
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
        }
    }

    /**
     * Clean up and reset {@link Log} internals
     */
    public static synchronized void dispose() {
        debugTree = null;
        crashlyticsTree = null;
        appProvider = null;
    }

    //
    // Shortcuts
    //

    /**
     * Log a verbose message with optional format args.
     */
    public static void v(@NonNls String message, Object... args) {
        Timber.v(message, args);
    }

    /**
     * Log a verbose exception and a message with optional format args.
     */
    public static void v(Throwable t, @NonNls String message, Object... args) {
        Timber.v(t, message, args);
    }

    /**
     * Log a verbose exception.
     */
    public static void v(Throwable t) {
        Timber.v(t);
    }

    /**
     * Log a debug message with optional format args.
     */
    public static void d(@NonNls String message, Object... args) {
        Timber.d(message, args);
    }

    /**
     * Log a debug exception and a message with optional format args.
     */
    public static void d(Throwable t, @NonNls String message, Object... args) {
        Timber.d(t, message, args);
    }

    /**
     * Log a debug exception.
     */
    public static void d(Throwable t) {
        Timber.d(t);
    }

    /**
     * Log an info message with optional format args.
     */
    public static void i(@NonNls String message, Object... args) {
        Timber.i(message, args);
    }

    /**
     * Log an info exception and a message with optional format args.
     */
    public static void i(Throwable t, @NonNls String message, Object... args) {
        Timber.i(t, message, args);
    }

    /**
     * Log an info exception.
     */
    public static void i(Throwable t) {
        Timber.i(t);
    }

    /**
     * Log a warning message with optional format args.
     */
    public static void w(@NonNls String message, Object... args) {
        Timber.w(message, args);
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    public static void w(Throwable t, @NonNls String message, Object... args) {
        Timber.w(t, message, args);
    }

    /**
     * Log a warning exception.
     */
    public static void w(Throwable t) {
        Timber.w(t);
    }

    /**
     * Log an error message with optional format args.
     */
    public static void e(@NonNls String message, Object... args) {
        Timber.e(message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void e(Throwable t, @NonNls String message, Object... args) {
        Timber.e(t, message, args);
    }

    /**
     * Log an error exception.
     */
    public static void e(Throwable t) {
        Timber.e(t);
    }

    /**
     * Log an assert message with optional format args.
     */
    public static void wtf(@NonNls String message, Object... args) {
        Timber.wtf(message, args);
    }

    /**
     * Log an assert exception and a message with optional format args.
     */
    public static void wtf(Throwable t, @NonNls String message, Object... args) {
        Timber.wtf(t, message, args);
    }

    /**
     * Log an assert exception.
     */
    public static void wtf(Throwable t) {
        Timber.wtf(t);
    }

    /**
     * Log at {@code priority} a message with optional format args.
     */
    public static void log(int priority, @NonNls String message, Object... args) {
        Timber.log(priority, message, args);
    }

    /**
     * Log at {@code priority} an exception and a message with optional format args.
     */
    public static void log(int priority, Throwable t, @NonNls String message, Object... args) {
        Timber.log(priority, t, message, args);
    }

    /**
     * Log at {@code priority} an exception.
     */
    public static void log(int priority, Throwable t) {
        Timber.log(priority, t);
    }


    //
    // Utils
    //

    /**
     * Provide default ignored log levels
     *
     * @return {@link Set} of default ignored log levels
     * @since 0.1.0
     */
    @VisibleForTesting
    static Set<Integer> defaultIgnoredLogLevels() {
        if (appProvider != null) {
            return appProvider.ignoredLogLevels();
        }
        return Common.Value.setOf(VERBOSE, INFO, DEBUG);
    }

    /**
     * Provide set of ignored log levels or defaults
     *
     * @param levels valid log level to ignore
     * @return {@link Set} of ignored log levels
     * @since 0.1.0
     */
    @VisibleForTesting
    static Set<Integer> ignoredLogLevels(Integer... levels) {
        Set<Integer> defaults = defaultIgnoredLogLevels();
        try {
            Set<Integer> provided = Common.Value.setOf(levels);
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
     * Set user identifier for {@link Crashlytics}
     *
     * @param identifier valid unique user identifier
     * @since 0.1.0
     */
    public static void setUserIdentifier(@NonNull String identifier) {
        Crashlytics.setUserIdentifier(identifier);
    }

    /**
     * Set user email for {@link Crashlytics}
     *
     * @param email valid unique user email
     * @since 0.1.0
     */
    public static void setUserEmail(@NonNull String email) {
        Crashlytics.setUserEmail(email);
    }

    /**
     * Set user name for {@link Crashlytics}
     *
     * @param name valid user name
     * @since 0.1.0
     */
    public static void setUserName(@NonNull String name) {
        Crashlytics.setUserName(name);
    }

    /**
     * Set user properties to {@link Crashlytics}
     *
     * @param key   valid property key
     * @param value valid property value
     * @since 0.1.0
     */
    public static void setUserProperty(@NonNull String key, @NonNull Object value) {
        set(key, value);
    }

    /**
     * Set custom properties to {@link Crashlytics}
     *
     * @param key   valid property key
     * @param value valid property value
     * @since 0.1.0
     */
    public static void set(@NonNull String key, @NonNull Object value) {
        // ensure valid key
        if (!Common.Strings.isEmpty(key)) {
            // set boolean property
            if (value instanceof Boolean) {
                Crashlytics.setBool(key, (Boolean) value);
            }
            // set double property
            if (value instanceof Double) {
                Crashlytics.setDouble(key, (Double) value);
            }
            // set float property
            if (value instanceof Float) {
                Crashlytics.setFloat(key, (Float) value);
            }
            // set int property
            if (value instanceof Integer) {
                Crashlytics.setInt(key, (Integer) value);
            }
            // set long property
            if (value instanceof Long) {
                Crashlytics.setLong(key, (Long) value);
            }
            // set string property
            if (value instanceof String) {
                Crashlytics.setString(key, (String) value);
            }
        }

    }

    /**
     * {@link Crashlytics} tree for {@link Timber}
     *
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
            Set<Integer> ignoredLogLevels = appProvider.ignoredLogLevels();
            if (ignoredLogLevels.contains(priority)) {
                return;
            }

            // add log custom keys
            Crashlytics.setInt(KEY_PRIORITY, priority);
            Crashlytics.setString(KEY_TAG, tag);
            Crashlytics.setString(KEY_MESSAGE, message);

            // log
            if (t == null) {
                Crashlytics.logException(new Exception(message));
            } else {
                Crashlytics.logException(t);
            }
        }
    }
}
