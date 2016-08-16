package com.bosch.myspin.serversdk.service.client;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import com.bosch.myspin.serversdk.uielements.MySpinKeyboardView;
import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardExtension;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.bosch.myspin.serversdk.service.client.i */
public class C0192i implements KeyboardExtension {
    private static final LogComponent f172a;
    private final String f173b;
    private final String[] f174c;
    private WeakReference<MySpinKeyboardView> f175d;
    private int f176e;
    private int f177f;

    static {
        f172a = LogComponent.Keyboard;
    }

    C0192i(String str, String[] strArr) {
        if (str == null) {
            throw new IllegalArgumentException("keyboardId must be not null!");
        } else if (strArr == null) {
            throw new IllegalArgumentException("supportedLanguages must be not null!");
        } else {
            this.f173b = str;
            this.f174c = strArr;
        }
    }

    public View createKeyboard(Context context, int i, int i2) {
        if (this.f175d == null || this.f175d.get() == null || this.f177f != i || this.f176e != i2) {
            Logger.logDebug(f172a, "MySpinKeyboardFactory/createKeyboard(height:" + i + ", width:" + i2 + ")");
            this.f175d = new WeakReference(new MySpinKeyboardView(context, i, i2, m198a()));
        }
        this.f177f = i;
        this.f176e = i2;
        return (View) this.f175d.get();
    }

    private int m198a() {
        return Arrays.asList(MySpinKeyboardView.AVAILABLE_LANGUAGES).indexOf(this.f174c[0]);
    }

    public List<String> getSupportedKeyboardLocals() {
        return Arrays.asList(this.f174c);
    }

    public void hide() {
        if (this.f175d != null) {
            ((MySpinKeyboardView) this.f175d.get()).hide();
        }
    }

    public String getId() {
        return this.f173b;
    }

    public void enableLanguageButton() {
        ((MySpinKeyboardView) this.f175d.get()).enableLanguageButton(true);
    }

    public void disableLanguageButton() {
        ((MySpinKeyboardView) this.f175d.get()).enableLanguageButton(false);
    }

    public void setEditText(EditText editText) {
        if (this.f175d != null) {
            ((MySpinKeyboardView) this.f175d.get()).setEditText(editText);
        }
    }

    public void setType(int i) {
        if (this.f175d != null) {
            ((MySpinKeyboardView) this.f175d.get()).setType(i);
        }
    }

    public int getType() {
        if (this.f175d != null) {
            return ((MySpinKeyboardView) this.f175d.get()).getType();
        }
        return 0;
    }

    public void show() {
        if (this.f175d != null) {
            ((MySpinKeyboardView) this.f175d.get()).show();
        }
    }
}
