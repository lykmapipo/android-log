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
    public void shouldProvideDefaultIgnoredLogLevels() {
        Set<Integer> levels = Log.defaultIgnoredLogLevels();

        assertThat(levels, is(not(equalTo(null))));
        assertThat(levels.isEmpty(), is(not(equalTo(true))));
        assertThat(levels.contains(android.util.Log.VERBOSE), is(equalTo(true)));
        assertThat(levels.contains(android.util.Log.DEBUG), is(equalTo(true)));
        assertThat(levels.contains(android.util.Log.INFO), is(equalTo(true)));
    }

    @Test
    public void shouldProvideCrashlyticsTree() {
        Log.CrashlyticsTree tree = new Log.CrashlyticsTree();

        assertThat(tree, is(not(equalTo(null))));
        assertThat(tree, is(instanceOf(Log.CrashlyticsTree.class)));
        assertThat(tree, is(instanceOf(Timber.Tree.class)));
    }

    @After
    public void clean() {
        context = null;
    }
}
