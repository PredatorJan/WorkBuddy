package de.jan.fx;

import de.jan.core.Counter;
import de.jan.nativebridge.NativeInput;
import de.jan.storage.Storage;
import de.jan.storage.exceptions.StorageException;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private Label counter;

    @FXML
    private Button exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IntegerProperty countProperty = Counter.getInstance().getCountProperty();
        counter.textProperty().bind(countProperty.asString());

        exit.setOnAction(event -> {
            NativeInput.stopKeyHook();
            try {
                Storage.getInstance().saveData();
                Storage.getInstance().saveConfig();
            } catch (StorageException e) {
                throw new RuntimeException(e);
            }

            Platform.exit();
            System.exit(0);
        });
    }
}
