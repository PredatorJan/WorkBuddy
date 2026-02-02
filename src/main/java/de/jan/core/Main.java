package de.jan.core;

import de.jan.nativebridge.NativeInput;
import de.jan.storage.Storage;
import de.jan.storage.entities.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        NativeInput.startKeyHook();
        Storage.getInstance().loadData();
        Config config = Storage.getInstance().loadConfig();

        URL resource = getClass().getResource("fxml/scene.fxml");
        if (resource == null) {
            throw new IllegalArgumentException("Could not find scene in resources!");
        }

        Parent root = FXMLLoader.load(resource);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);

            config.setWindowPosX(stage.getX());
            config.setWindowPosY(stage.getY());
        });

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("fxml/styles.css").toExternalForm());

        stage.setTitle("WorkBuddy");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(120);
        stage.setHeight(100);
        stage.getIcons().add(new Image(getClass().getResource("images/WorkBuddyIcon.png").toExternalForm()));
        stage.setAlwaysOnTop(true);

        stage.setX(config.getWindowPosX());
        stage.setY(config.getWindowPosY());

        stage.show();
    }
}
