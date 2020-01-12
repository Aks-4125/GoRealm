package com.aks4125.gorealm;

import android.app.Application;

import org.robolectric.TestLifecycleApplication;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

@Config(application = MyApp.class)
public class CustomTestApplication extends Application implements TestLifecycleApplication {
    /* required mock application class to run robolectric tests*/
    @Override
    public void beforeTest(Method method) {
        /* required */
    }

    @Override
    public void prepareTest(Object test) {
        /* required */
    }

    @Override
    public void afterTest(Method method) {
        /* required */
    }
}