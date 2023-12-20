package com.longtv.zappy;

import android.app.Application;

public class App extends Application {


    private static App sAppInstance;

    private static synchronized void setAppInstance(App instance) {
        sAppInstance = instance;
    }

    public static App getInstance() {
        return sAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAppInstance(this);
    }
}
