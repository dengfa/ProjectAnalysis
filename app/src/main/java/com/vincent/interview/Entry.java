package com.vincent.interview;

public class Entry {
    /**
     * The value associated with this ThreadLocal.
     */
    Object value;

    public <T> void set(T k, Object v) {
        value = v;
    }
}