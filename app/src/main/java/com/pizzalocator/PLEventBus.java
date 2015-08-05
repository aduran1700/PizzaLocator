package com.pizzalocator;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

public class PLEventBus extends EventBus {
    private static volatile PLEventBus defaultInstance;
    private final ScheduledExecutorService mExecutorService;

    private PLEventBus() {
        super();
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public ScheduledFuture<Object> postDelayed(Object event, long delay) {
        return mExecutorService.schedule(new PostEventCallable(this, event), delay, TimeUnit.MILLISECONDS);
    }

    private class PostEventCallable implements Callable<Object> {
        private final PLEventBus mEventBus;
        private final Object mEvent;

        public PostEventCallable(PLEventBus eventBus, Object event) {
            mEventBus = eventBus;
            mEvent = event;
        }

        @Override
        public Object call() throws Exception {
            mEventBus.post(mEvent);
            return null;
        }
    }

    public static PLEventBus getInstance() {
        if (defaultInstance == null) {
            synchronized (PLEventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new PLEventBus();
                }
            }
        }
        return defaultInstance;
    }

    public static void postEvent(Object event) {
        getInstance().post(event);
    }

    public static void postStickyEvent(Object event) {
        getInstance().postSticky(event);
    }

    public static <T> T getSticky(Class<T> eventType) {
        return getInstance().getStickyEvent(eventType);
    }
}