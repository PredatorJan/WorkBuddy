package de.jan.storage.entities;

import de.jan.core.Counter;
import de.jan.storage.DataConstants;

import java.util.Properties;

public class Data {

    private int count;

    public Data(Properties data) {
        count = Integer.parseInt(data.getProperty(DataConstants.COUNT));
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(DataConstants.COUNT, String.valueOf(Counter.getInstance().getCount()));

        return properties;
    }

    public int getCount() {
        return count;
    }

    public Data setCount(int count) {
        this.count = count;
        return this;
    }
}
