package com.pizzalocator;

import android.content.Context;

import com.pizzalocator.base.BaseEventBusRegistry;
import com.pizzalocator.subscribers.PizzaVenuesSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PLEventBusRegistry extends BaseEventBusRegistry {

    protected PLEventBusRegistry(Context applicationContext) {
        super(applicationContext);
    }

    @Override
    protected List<BaseEventBusRegistry.EventBusSubscriber> createDefaultSubscribers() {
        List<EventBusSubscriber> subscribers = new ArrayList<>();
        subscribers.addAll(Arrays.asList(
                new PizzaVenuesSubscriber()
        ));
        return subscribers;
    }
}