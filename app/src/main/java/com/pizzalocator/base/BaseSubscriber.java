package com.pizzalocator.base;


import com.pizzalocator.PLEventBus;

public abstract class BaseSubscriber implements BaseEventBusRegistry.EventBusSubscriber {

    private PLEventBus mEventBus;

    @Override
    public final Object register(PLEventBus eventBus) {
        mEventBus = eventBus;
        mEventBus.register(this);
        return this;
    }

    public final void unregister(PLEventBus eventBus) {
        eventBus.unregister(this);
        mEventBus = null;
    }

    protected void post(Object event) {
        if (mEventBus == null) {
            throw new NullPointerException("PluginController.register() was not called. Is the controller registered in the EventBusRegistry?");
        }
        mEventBus.post(event);
    }

    protected void postSticky(Object event) {
        if (mEventBus == null) {
            throw new NullPointerException("PluginController.register() was not called. Is the controller registered in the EventBusRegistry?");
        }
        mEventBus.postSticky(event);
    }

    protected <T> T removeStickyEvent(Class<T> eventType) {
        if (mEventBus == null) {
            throw new NullPointerException("PluginController.register() was not called. Is the controller registered in the EventBusRegistry?");
        }
        return mEventBus.removeStickyEvent(eventType);
    }

    protected boolean removeStickyEvent(Object event) {
        if (mEventBus == null) {
            throw new NullPointerException("PluginController.register() was not called. Is the controller registered in the EventBusRegistry?");
        }
        return mEventBus.removeStickyEvent(event);
    }

    protected <T> T getStickyEvent(Class<T> eventType) {
        if (mEventBus == null) {
            throw new NullPointerException("PluginController.register() was not called. Is the controller registered in the EventBusRegistry?");
        }
        return mEventBus.getStickyEvent(eventType);
    }
}