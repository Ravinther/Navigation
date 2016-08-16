package com.bosch.myspin.serversdk.uielements.keyboardinterface;

import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.ArrayList;
import java.util.Iterator;

public class KeyboardRegister {
    private static final LogComponent f403a;
    private static KeyboardRegister f404b;
    private ArrayList<KeyboardExtension> f405c;
    private KeyboardManager f406d;

    static {
        f403a = LogComponent.Keyboard;
    }

    private KeyboardRegister() {
        this.f405c = new ArrayList();
    }

    public static KeyboardRegister getInstance() {
        if (f404b == null) {
            f404b = new KeyboardRegister();
        }
        return f404b;
    }

    public void registerKeyboardManager(KeyboardManager keyboardManager) {
        Logger.logDebug(f403a, "KeyboardRegister/registerKeyboardManager");
        this.f406d = keyboardManager;
        if (this.f406d != null) {
            Iterator it = this.f405c.iterator();
            while (it.hasNext()) {
                this.f406d.addExternalKeyboard((KeyboardExtension) it.next());
            }
            this.f405c.clear();
        }
    }

    public void onLanguageButtonClick() {
        if (this.f406d != null) {
            this.f406d.switchKeyboard();
        }
    }

    public void onHideRequest() {
        if (this.f406d != null) {
            this.f406d.onHideRequest();
        }
    }
}
