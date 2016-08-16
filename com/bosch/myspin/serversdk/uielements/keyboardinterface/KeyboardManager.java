package com.bosch.myspin.serversdk.uielements.keyboardinterface;

public interface KeyboardManager {
    void addExternalKeyboard(KeyboardExtension keyboardExtension);

    void onHideRequest();

    void switchKeyboard();
}
