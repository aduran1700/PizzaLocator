package com.pizzalocator;

import android.app.Application;


public class PLApplication extends Application {
    private static PLApplication sInstance;
    private PLEventBusRegistry mRegistry;

    public static PLApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();
        startEventProcessing();

    }

    private void startEventProcessing() {
        mRegistry = new PLEventBusRegistry(this);
        mRegistry.registerDefaultSubscribers();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sInstance = null;
        mRegistry.unregisterAllSubscribers();
        mRegistry = null;
    }
}