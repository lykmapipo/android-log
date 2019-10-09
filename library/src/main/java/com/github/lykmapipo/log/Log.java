package com.github.lykmapipo.log;


import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArraySet;

import com.crashlytics.android.Crashlytics;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
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

    private Log() {
        throw new AssertionError("No instances.");
    }

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
     * Set user identifier for {@link Crashlytics}
     *
     * @param identifier valid unique user identifier
     * @author lally elias<lallyelias87@gmail.com>
     * @since 0.1.0
     */
    public static void setUserIdentifier(@NonNull String identifier) {
        Crashlytics.setUserIdentifier(identifier);
    }

    /**
     * Set user email for {@link Crashlytics}
     *
     * @param email valid unique user email
     * @author lally elias<lallyelias87@gmail.com>
     * @since 0.1.0
     */
    public static void setUserEmail(@NonNull String email) {
        Crashlytics.setUserEmail(email);
    }

    /**
     * Set user name for {@link Crashlytics}
     *
     * @param name valid user name
     * @author lally elias<lallyelias87@gmail.com>
     * @since 0.1.0
     */
    public static void setUserName(@NonNull String name) {
        Crashlytics.setUserName(name);
    }

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
