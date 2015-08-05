package com.pizzalocator.base;

import android.app.Fragment;

import com.pizzalocator.PLEventBus;
import com.pizzalocator.PLEventBusRegistry;


public class BaseEventReportingFragment extends Fragment implements PLEventBusRegistry.EventBusSubscriber {

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