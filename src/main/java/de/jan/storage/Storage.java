package de.jan.storage;

import de.jan.core.Counter;
import de.jan.storage.entities.Config;
import de.jan.storage.entities.Data;
import de.jan.storage.exceptions.StorageException;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Storage {

    private static Storage instance;

    private final String HOME_DIRECTORY_FILENAME = "\\WorkBuddy";
    private final String DATA_FILENAME = HOME_DIRECTORY_FILENAME + "\\workBuddyData.config";
    private final String CONFIG_FILENAME = HOME_DIRECTORY_FILENAME + "\\workBuddy.config";

    private Data data;
    private Config config;

    private Storage() {
        instance = this;
    }

    public static Storage getInstance() {
        if (instance == null) {
            return new Storage();
        }
        return instance;
    }

    public Config loadConfig() throws StorageException {
        Properties configProperties = loadPropertyFile(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + CONFIG_FILENAME, this::createDefaultConfigFile);

        if (configProperties.isEmpty()) {
            throw new StorageException("Config is not allowed to be empty!");
        }

        return config = new Config(configProperties);
    }

    public void saveConfig() throws StorageException {
        Properties configProperties = config.getProperties();

        try (FileOutputStream fos = new FileOutputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + CONFIG_FILENAME)) {
            configProperties.store(fos, null);
        } catch (IOException e) {
            throw new StorageException("Error while saving config file", e);
        }
    }

    public Data loadData() throws StorageException {
        Properties dataProperties = loadPropertyFile(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + DATA_FILENAME, this::createDefaultDataFile);

        if (dataProperties.isEmpty()) {
            throw new StorageException("Data is not allowed to be empty!");
        }

        data = new Data(dataProperties);
        Counter.getInstance().setCount(data.getCount());

        return data;
    }

    public void saveData() throws StorageException {
        Properties dataProperties = data.getProperties();

        try (FileOutputStream fos = new FileOutputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + DATA_FILENAME)) {
            dataProperties.store(fos, null);
        } catch (IOException e) {
            throw new StorageException("Error while saving data file", e);
        }
    }

    private Properties loadPropertyFile(String filePath, FileNotFoundHandler handler) throws StorageException {
        Properties properties = new Properties();

        File file = new File(filePath);
        if (!file.exists()) {
            handler.handle();
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new StorageException("Error while loading data file", e);
        }

        return properties;
    }

    private void createDefaultDataFile() throws StorageException {
        File dataFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + DATA_FILENAME);

        File homeDirectory = dataFile.getParentFile();
        homeDirectory.mkdirs();

        boolean newFileCreated = false;
        try {
            newFileCreated = dataFile.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Could not create new data file", e);
        }

        if (!newFileCreated) {
            return;
        }

        Properties defaultDataProperties = getDefaultDataProperties();
        try (FileOutputStream fos = new FileOutputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + DATA_FILENAME)) {
            defaultDataProperties.store(fos, null);
        } catch (IOException e) {
            throw new StorageException("Error while saving default data file", e);
        }
    }

    private Properties getDefaultDataProperties() {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(DataConstants.COUNT, String.valueOf(Counter.getInstance().getCount()));

        return defaultProperties;
    }

    private void createDefaultConfigFile() throws StorageException {
        File configFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + CONFIG_FILENAME);

        File homeDirectory = configFile.getParentFile();
        homeDirectory.mkdirs();

        boolean newFileCreated = false;
        try {
            newFileCreated = configFile.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Could not create new config file", e);
        }

        if (!newFileCreated) {
            return;
        }

        Properties defaultConfigProperties = getDefaultConfigProperties();
        try (FileOutputStream fos = new FileOutputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + CONFIG_FILENAME)) {
            defaultConfigProperties.store(fos, null);
        } catch (IOException e) {
            throw new StorageException("Error while saving default data file", e);
        }
    }

    private Properties getDefaultConfigProperties() {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(ConfigConstants.WINDOW_POS_X, "0.0");
        defaultProperties.setProperty(ConfigConstants.WINDOW_POS_Y, "0.0");

        return defaultProperties;
    }
}
