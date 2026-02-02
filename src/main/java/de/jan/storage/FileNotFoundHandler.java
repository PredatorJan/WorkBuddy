package de.jan.storage;

import de.jan.storage.exceptions.StorageException;

@FunctionalInterface
public interface FileNotFoundHandler {

    void handle() throws StorageException;
}
