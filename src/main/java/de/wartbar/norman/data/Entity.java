package de.wartbar.norman.data;

import java.util.*;

public class Entity {

    private static final Object syncObject = new Object();
    private static int uniqueIdCounter = 0;

    public static int uniqueId() {
        synchronized (syncObject) {
            uniqueIdCounter++;
            return uniqueIdCounter;
        }
    }

    public List<String> keys = new ArrayList<>();
    public String value = Constants.emptyString;
    public Date date = new Date();
    public int id = uniqueId();
}
