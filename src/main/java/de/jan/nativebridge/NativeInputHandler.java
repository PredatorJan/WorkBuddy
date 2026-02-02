package de.jan.nativebridge;

import de.jan.core.Counter;
import javafx.application.Platform;

public class NativeInputHandler {

    public static void onKeyPressed(int keyCode) {
        Platform.runLater(() -> {
            Counter.getInstance().incrementCounter();
        });
    }
}
