package de.jan.nativebridge;

public class NativeInput {
    static {
        System.loadLibrary("work_buddy_native");
    }

    public static native void startKeyHook();

    public static native void stopKeyHook();
}