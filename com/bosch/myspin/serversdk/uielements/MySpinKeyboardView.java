package com.bosch.myspin.serversdk.uielements;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import com.bosch.myspin.serversdk.resource.bitmaps.C0177a;
import com.bosch.myspin.serversdk.uielements.C0227b.C0220a;
import com.bosch.myspin.serversdk.uielements.C0227b.C0221b;
import com.bosch.myspin.serversdk.uielements.C0227b.C0222c;
import com.bosch.myspin.serversdk.uielements.C0227b.C0223d;
import com.bosch.myspin.serversdk.uielements.C0227b.C0224e;
import com.bosch.myspin.serversdk.uielements.C0227b.C0225f;
import com.bosch.myspin.serversdk.uielements.C0227b.C0226g;
import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardRegister;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.HashMap;
import java.util.Locale;
import loquendo.tts.engine.TTSConst;

public class MySpinKeyboardView extends MySpinKeyboardBaseView {
    public static final String[] AVAILABLE_LANGUAGES;
    static final C0219a[] f353a;
    private static final LogComponent f354b;
    private int f355c;
    private String[] f356d;
    private String[] f357e;
    private String[] f358f;
    private String[] f359g;
    private String f360h;
    private String f361i;
    private String f362j;
    private String f363k;
    private String f364l;
    private String f365m;

    static {
        f354b = LogComponent.Keyboard;
        f353a = new C0219a[]{new C0221b(), new C0223d(), new C0225f(), new C0222c(), new C0220a(), new C0224e(), new C0226g()};
        AVAILABLE_LANGUAGES = new String[]{"en", "de", "ru", "fr", "nl", "pt", "es"};
    }

    public MySpinKeyboardView(Context context, int i, int i2, int i3) {
        super(context, i, i2);
        this.f355c = -1;
        this.f355c = i3;
        m349a();
        loadLayouts();
        languageUpdated();
        Logger.logDebug(f354b, "MySpinKeyboard/construct, current locale: " + f353a[this.f355c].getLocale().getLanguage());
    }

    private void m349a() {
        if (this.f355c < 0 || this.f355c >= f353a.length) {
            String locale;
            int i;
            this.f355c = 0;
            String language = Locale.getDefault().getLanguage();
            if (getContext() != null) {
                InputMethodSubtype currentInputMethodSubtype = ((InputMethodManager) getContext().getSystemService("input_method")).getCurrentInputMethodSubtype();
                if (currentInputMethodSubtype != null) {
                    locale = currentInputMethodSubtype.getLocale();
                    i = 1;
                    while (i < f353a.length) {
                        if (locale.startsWith(f353a[i].getLocale().getLanguage())) {
                            i++;
                        } else {
                            this.f355c = i;
                            return;
                        }
                    }
                }
            }
            locale = language;
            i = 1;
            while (i < f353a.length) {
                if (locale.startsWith(f353a[i].getLocale().getLanguage())) {
                    i++;
                } else {
                    this.f355c = i;
                    return;
                }
            }
        }
    }

    protected int getResourceId(String str) {
        return str.equals("*flyinpushed") ? 0 : 0;
    }

    protected void languageButtonPressed() {
        KeyboardRegister.getInstance().onLanguageButtonClick();
    }

    public void show() {
        setVisibility(0);
        setType(this.mType);
    }

    protected void loadLayouts() {
        m349a();
        C0219a c0219a = f353a[this.f355c];
        this.f356d = c0219a.getStringArrayKeyboardLayoutMain();
        this.f357e = c0219a.getStringArrayKeyboardLayoutShift();
        this.f358f = c0219a.getStringArrayKeyboardLayoutDigits();
        this.f359g = c0219a.getStringArrayKeyboardLayoutAlt();
        generateKeyboardLayout();
        invalidate();
    }

    protected void setButtonIcon(MySpinKeyboardButton mySpinKeyboardButton, int i) {
        super.setButtonIcon(mySpinKeyboardButton, i);
        String text = mySpinKeyboardButton.getText();
        if (text.equals("*previous")) {
            if (i == 1) {
                mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 13));
            } else {
                mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 20));
            }
        } else if (!text.equals("*next")) {
        } else {
            if (i == 1) {
                mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 14));
            } else {
                mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 21));
            }
        }
    }

    public String getImeText(int i) {
        getLabel("*enter");
        switch (i) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                return this.f362j;
            case TTSConst.TTSUNICODE /*3*/:
                return this.f365m;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return this.f364l;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return this.f361i;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return this.f363k;
            default:
                return this.f360h;
        }
    }

    protected String[] getLayout(int i) {
        switch (i) {
            case 1002:
            case 1003:
                return this.f357e;
            case 1004:
                return this.f358f;
            case 1005:
                return this.f359g;
            default:
                return this.f356d;
        }
    }

    protected boolean checkForSpecialKeys(MySpinKeyboardButton mySpinKeyboardButton, int i, int i2) {
        return false;
    }

    protected void loadSpecialLabels() {
    }

    protected void prepareDrawing() {
    }

    protected boolean checkForSpecialFunction(String str, int i, int i2) {
        return false;
    }

    protected boolean checkForSpecialDelete(int i, int i2) {
        return false;
    }

    protected String getLabel(String str) {
        m349a();
        HashMap specialKeysDictionary = f353a[this.f355c].getSpecialKeysDictionary();
        if (str.equals("*enter")) {
            this.f360h = (String) specialKeysDictionary.get("keyboard_ok");
            this.f361i = (String) specialKeysDictionary.get("keyboard_done");
            this.f362j = (String) specialKeysDictionary.get("keyboard_go");
            this.f363k = (String) specialKeysDictionary.get("keyboard_prev");
            this.f364l = (String) specialKeysDictionary.get("keyboard_next");
            this.f365m = (String) specialKeysDictionary.get("keyboard_search");
            return this.f360h;
        } else if (str.equals("*space")) {
            return (String) specialKeysDictionary.get("keyboard_space");
        } else {
            if (str.equals("*abc")) {
                return (String) specialKeysDictionary.get("keyboard_abc");
            }
            return "";
        }
    }
}
