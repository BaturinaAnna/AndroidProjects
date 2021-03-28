package com.example.noticebook;

import java.util.HashMap;
import java.util.Map;

public enum Priority {
    HIGH(0),
    MEDIUM(1),
    LOW(2);

    private int value;
    private static Map map = new HashMap<>();

    private Priority(int value) {
        this.value = value;
    }

    static {
        for (Priority priority : Priority.values()) {
            map.put(priority.value, priority);
        }
    }

    public static Priority valueOf(int priority) {
        return (Priority) map.get(priority);
    }

    public int getValue() {
        return value;
    }
}
