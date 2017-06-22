package com.badeeb.waritex.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ahmed on 6/22/2017.
 */

public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
