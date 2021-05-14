package de.wartbar.norman.data;

import java.util.*;

public class Entity {

    private static final Object syncObject = new Object();
    private static Long uniqueIdCounter = 0L;

    public static Long uniqueId() {
        synchronized (syncObject) {
            uniqueIdCounter++;
            return uniqueIdCounter;
        }
    }

    public List<String> keys = new ArrayList<>();
    public String value = Constants.emptyString;
    public Date date = new Date();
    public Long id = uniqueId();
}
