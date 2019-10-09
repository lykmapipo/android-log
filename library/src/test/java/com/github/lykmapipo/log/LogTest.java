package com.github.lykmapipo.log;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

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

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
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
        Log.alreadyInitialized = false;
        Log.create(true);

        assertThat(Log.alreadyInitialized, is(equalTo(true)));
        assertThat(Log.allowCrashlytics, is(equalTo(false)));
        assertThat(Log.ignoredLogLevels, is(not(equalTo(null))));
        assertThat(Log.crashlyticsTree, is(equalTo(null)));
        assertThat(Log.debugTree, is(not(equalTo(null))));
    }

    @Test
    public void shouldInitializeLogWithCrashlyticsTree() {
        Log.alreadyInitialized = false;
        Log.create(false);

        assertThat(Log.alreadyInitialized, is(equalTo(true)));
        assertThat(Log.allowCrashlytics, is(equalTo(true)));
        assertThat(Log.ignoredLogLevels, is(not(equalTo(null))));
        assertThat(Log.debugTree, is(equalTo(null)));
        assertThat(Log.crashlyticsTree, is(not(equalTo(null))));
    }

    @After
    public void clean() {
        context = null;
    }
}
