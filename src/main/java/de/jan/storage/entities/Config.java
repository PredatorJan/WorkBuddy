package de.jan.storage.entities;

import de.jan.storage.ConfigConstants;

import java.util.Properties;

public class Config {

    private double windowPosX = 0;
    private double windowPosY = 0;

    public Config(Properties config) {
        windowPosX = Double.parseDouble(config.getProperty(ConfigConstants.WINDOW_POS_X));
        windowPosY = Double.parseDouble(config.getProperty(ConfigConstants.WINDOW_POS_Y));
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConfigConstants.WINDOW_POS_X, String.valueOf(windowPosX));
        properties.setProperty(ConfigConstants.WINDOW_POS_Y, String.valueOf(windowPosY));

        return properties;
    }

    public double getWindowPosX() {
        return windowPosX;
    }

    public Config setWindowPosX(double windowPosX) {
        this.windowPosX = windowPosX;
        return this;
    }

    public double getWindowPosY() {
        return windowPosY;
    }

    public Config setWindowPosY(double windowPosY) {
        this.windowPosY = windowPosY;
        return this;
    }
}
