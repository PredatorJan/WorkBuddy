package de.jan.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Counter {

    private static Counter instance;

    private final IntegerProperty count = new SimpleIntegerProperty(0);

    private Counter() {
        instance = this;
    }

    public static Counter getInstance() {
        if (instance == null) {
            return new Counter();
        }
        return instance;
    }

    public void incrementCounter() {
        count.set(count.get() + 1);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public IntegerProperty getCountProperty() {
        return count;
    }
}
