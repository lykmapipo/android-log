package com.github.lykmapipo.log;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import com.github.lykmapipo.common.provider.Provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Set;

import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(RobolectricTestRunner.class)
public class LogTest {
    private Context context;
    private Provider debugProvider;
    private Provider crashlyticsProvider;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        debugProvider = new Provider() {
            @NonNull
            @Override
            public Context getApplicationContext() {
                return context;
            }

            @NonNull
            @Override
            public Boolean isDebug() {
                return true;
            }
        };

        crashlyticsProvider = new Provider() {
            @NonNull
            @Override
            public Context getApplicationContext() {
                return context;
            }

            @NonNull
            @Override
            public Boolean isDebug() {
                return false;
            }
        };
    }

    @Test
    public void shouldProvideDefaultIgnoredLogLevels_01() {
        Set<Integer> levels = Log.defaultIgnoredLogLevels();

        assertThat(levels, is(not(equalTo(null))));
        assertThat(levels.isEmpty(), is(not(equalTo(true))));
        assertThat(levels.contains(Log.VERBOSE), is(equalTo(true)));
        assertThat(levels.contains(Log.DEBUG), is(equalTo(true)));
        assertThat(levels.contains(Log.INFO), is(equalTo(true)));
    }

    @Test
    public void shouldProvideDefaultIgnoredLogLevels_02() {
        Set<Integer> levels = Log.ignoredLogLevels();

        assertThat(levels, is(not(equalTo(null))));
        assertThat(levels.isEmpty(), is(not(equalTo(true))));
        assertThat(levels.contains(Log.VERBOSE), is(equalTo(true)));
        assertThat(levels.contains(Log.DEBUG), is(equalTo(true)));
        assertThat(levels.contains(Log.INFO), is(equalTo(true)));
    }

    @Test
    public void shouldProvideIgnoredLogLevels_01() {
        Set<Integer> levels = Log.ignoredLogLevels(Log.INFO, Log.ASSERT, Log.WARN, Log.ERROR);

        assertThat(levels, is(not(equalTo(null))));
        assertThat(levels.isEmpty(), is(not(equalTo(true))));
        assertThat(levels.contains(Log.VERBOSE), is(equalTo(false)));
        assertThat(levels.contains(Log.DEBUG), is(equalTo(false)));
        assertThat(levels.contains(Log.INFO), is(equalTo(true)));
        assertThat(levels.contains(Log.ASSERT), is(equalTo(true)));
        assertThat(levels.contains(Log.WARN), is(equalTo(true)));
        assertThat(levels.contains(Log.ERROR), is(equalTo(true)));
    }

    @Test
    public void shouldProvideCrashlyticsTree_01() {
        Log.CrashlyticsTree tree = new Log.CrashlyticsTree();

        assertThat(tree, is(not(equalTo(null))));
        assertThat(tree, is(instanceOf(Log.CrashlyticsTree.class)));
        assertThat(tree, is(instanceOf(Timber.Tree.class)));
    }

    @Test
    public void shouldInitializeLogWithDebugTree() {
        Log.of(debugProvider);

        assertThat(Log.appProvider, is(not(equalTo(null))));
        assertThat(Log.appProvider.isDebug(), is(equalTo(true)));
        assertThat(Log.crashlyticsTree, is(equalTo(null)));
        assertThat(Log.debugTree, is(not(equalTo(null))));
    }

    @Test
    public void shouldInitializeLogWithCrashlyticsTree() {
        Log.of(crashlyticsProvider);

        assertThat(Log.appProvider, is(not(equalTo(null))));
        assertThat(Log.appProvider.isDebug(), is(equalTo(false)));
        assertThat(Log.debugTree, is(equalTo(null)));
        assertThat(Log.crashlyticsTree, is(not(equalTo(null))));
    }

    @After
    public void clean() {
        crashlyticsProvider = null;
        debugProvider = null;
        context = null;
        Log.dispose();
    }
}
