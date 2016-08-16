package com.bosch.myspin.serversdk.uielements.keyboardinterface;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import java.util.List;

public interface KeyboardExtension {
    View createKeyboard(Context context, int i, int i2);

    void disableLanguageButton();

    void enableLanguageButton();

    String getId();

    List<String> getSupportedKeyboardLocals();

    int getType();

    void hide();

    void setEditText(EditText editText);

    void setType(int i);

    void show();
}
