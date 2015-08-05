package com.pizzalocator.base;

import android.support.v7.app.AppCompatActivity;

import com.pizzalocator.PLEventBus;
import com.pizzalocator.PLEventBusRegistry;


public class BaseEventReportingActivity extends AppCompatActivity implements PLEventBusRegistry.EventBusSubscriber {

    @Override
    public Object register(PLEventBus eventBus) {
        eventBus.registerSticky(this);
        return this;
    }

    @Override
    public void unregister(PLEventBus eventBus) {
        eventBus.unregister(this);
    }

}