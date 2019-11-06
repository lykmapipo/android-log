android-log
=======================

[![](https://jitpack.io/v/lykmapipo/android-log.svg)](https://jitpack.io/#lykmapipo/android-log)

Handy wrapper for Timber and Firebase Crashlytics to provide logging utility helpers

## Installation
Add [https://jitpack.io](https://jitpack.io) to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
add `android-log` dependency into your project

```gradle
dependencies {
    implementation 'com.github.lykmapipo:android-log:v0.2.1'
}
```

## Usage

Initialize `android-log`

```java
public class SampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /* initialize {@link Log} internals */
        Log.of(new Provider() {
            @NonNull
            @Override
            public Context getApplicationContext() {
                return SampleApp.this;
            }

            @NonNull
            @Override
            public Boolean isDebug() {
                return BuildConfig.DEBUG;
            }
        });

    }
}
```

In your `android components` start logging

```js
Log.setUserIdentifier("user:123456789");
Log.v(tring tag, String msg, Throwable tr);
Log.d(String tag, String msg, Throwable tr);
Log.i(String tag, String msg, Throwable tr);
Log.w(String tag, String msg, Throwable tr);
Log.e(String tag, String msg, Throwable tr);
Log.wtf(String tag, String msg, Throwable tr);
```


## Test
```sh
./gradlew test
```

## Contribute
It will be nice, if you open an issue first so that we can know what is going on, then, fork this repo and push in your ideas.
Do not forget to add a bit of test(s) of what value you adding.

## License

(The MIT License)

Copyright (c) lykmapipo && Contributors

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
'Software'), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
