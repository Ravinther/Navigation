package com.bosch.myspin.serversdk.service.client;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardExtension;
import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardManager;
import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardRegister;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/* renamed from: com.bosch.myspin.serversdk.service.client.e */
public class C0188e implements KeyboardManager {
    private static final LogComponent f154a;
    private RelativeLayout f155b;
    private C0215x f156c;
    private KeyboardExtension f157d;
    private int f158e;
    private int f159f;
    private EditText f160g;
    private Set<KeyboardExtension> f161h;
    private ArrayList<KeyboardExtension> f162i;
    private Activity f163j;
    private WindowManager f164k;
    private int f165l;
    private boolean f166m;

    static {
        f154a = LogComponent.Keyboard;
    }

    public C0188e() {
        this.f158e = 671;
        this.f159f = 424;
        this.f161h = new HashSet();
        this.f162i = new ArrayList();
        this.f165l = -1;
        this.f156c = C0215x.m320a();
        KeyboardRegister.getInstance().registerKeyboardManager(this);
    }

    public void m190a(Activity activity) {
        Logger.logDebug(f154a, "KeyboardHandler/addKeyboardInternal");
        if (activity != null) {
            this.f163j = activity;
            ViewGroup viewGroup = (ViewGroup) this.f163j.findViewById(16908290).getRootView();
            if (viewGroup != null) {
                synchronized (this) {
                    m178a(viewGroup);
                }
                return;
            }
            Logger.logWarning(f154a, "KeyboardHandler/Adding keyboard failed. RootView is null!");
        }
    }

    private void m185e() {
        this.f155b.removeAllViews();
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, (int) (((double) this.f159f) * 0.76d));
        layoutParams.addRule(12);
        layoutParams.addRule(9);
        layoutParams.addRule(11);
        this.f157d = (KeyboardExtension) this.f162i.get(this.f165l);
        View createKeyboard = this.f157d.createKeyboard(this.f163j, this.f159f, this.f158e);
        if (this.f162i.size() == 1) {
            this.f157d.disableLanguageButton();
        } else {
            this.f157d.enableLanguageButton();
        }
        this.f155b.addView(createKeyboard, layoutParams);
    }

    public void m189a(int i, int i2) {
        this.f158e = i;
        this.f159f = i2;
    }

    public boolean m193a() {
        return this.f155b != null && this.f166m;
    }

    private void m186f() {
        Logger.logDebug(f154a, "KeyboardHandler/active keyboards: " + this.f162i.size() + ", show keyboard with index: " + this.f165l);
        if (!this.f166m) {
            m187g();
            m185e();
            this.f164k = (WindowManager) this.f163j.getSystemService("window");
            this.f157d.setEditText(this.f160g);
            LayoutParams layoutParams = new WindowManager.LayoutParams(99);
            layoutParams.width = this.f158e;
            layoutParams.height = this.f159f;
            layoutParams.flags = 1536;
            layoutParams.screenOrientation = 0;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.f164k.getDefaultDisplay().getMetrics(displayMetrics);
            layoutParams.x = -Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
            this.f164k.addView(this.f155b, layoutParams);
            this.f156c.m324a(this.f155b);
            this.f166m = true;
            if (this.f157d != null) {
                m180a(true);
                this.f157d.show();
            }
        }
    }

    public void m194b() {
        if (this.f166m) {
            Logger.logDebug(f154a, "KeyboardHandler/hide keyboard");
            m180a(false);
            this.f156c.m327c(this.f155b);
            this.f164k.removeView(this.f155b);
            this.f166m = false;
            if (this.f157d != null) {
                this.f157d.hide();
            }
        }
    }

    public void m196c() {
        if (this.f155b != null && this.f166m) {
            try {
                this.f156c.m327c(this.f155b);
                this.f164k.removeView(this.f155b);
            } finally {
                if (this.f155b != null) {
                    this.f155b.removeAllViews();
                }
                this.f155b = null;
                this.f157d = null;
                this.f166m = false;
            }
        }
    }

    public void m195b(Activity activity) {
        m196c();
    }

    public void m197c(Activity activity) {
    }

    public void m191a(View view, View view2) {
        m178a((ViewGroup) view);
    }

    private void m178a(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                if (childAt instanceof SearchView) {
                    ((SearchView) childAt).setOnSearchClickListener(new C0189f(this, childAt, m181b((ViewGroup) childAt)));
                }
                m178a((ViewGroup) childAt);
            } else if (childAt != null) {
                m177a(childAt);
            }
        }
    }

    private EditText m181b(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                return m181b((ViewGroup) childAt);
            }
            if (childAt != null && (childAt instanceof EditText)) {
                return (EditText) childAt;
            }
        }
        return null;
    }

    private void m177a(View view) {
        if (view instanceof EditText) {
            view.setOnTouchListener(new C0190g(this));
            view.setOnFocusChangeListener(new C0191h(this));
        }
    }

    private void m187g() {
        if (this.f165l < 0) {
            m188h();
        }
        this.f157d = (KeyboardExtension) this.f162i.get(this.f165l);
        if (this.f155b == null) {
            this.f155b = new RelativeLayout(this.f163j);
        }
    }

    public void addExternalKeyboard(KeyboardExtension keyboardExtension) {
        Logger.logDebug(f154a, "KeyboardHandler/addExternalKeyboard: " + keyboardExtension);
        this.f161h.add(keyboardExtension);
    }

    private void m188h() {
        int i = 0;
        this.f165l = 0;
        if (this.f163j != null) {
            Object language = Locale.getDefault().getLanguage();
            InputMethodSubtype currentInputMethodSubtype = ((InputMethodManager) this.f163j.getSystemService("input_method")).getCurrentInputMethodSubtype();
            if (currentInputMethodSubtype != null) {
                String locale = currentInputMethodSubtype.getLocale();
                int indexOf = locale.indexOf("_");
                if (indexOf > 0) {
                    language = locale.substring(0, indexOf);
                } else {
                    String str = locale;
                }
            }
            if (this.f157d == null || this.f157d.getSupportedKeyboardLocals() == null || !this.f157d.getSupportedKeyboardLocals().contains(language)) {
                while (i < this.f162i.size()) {
                    if (((KeyboardExtension) this.f162i.get(i)).getSupportedKeyboardLocals().contains(language)) {
                        Logger.logInfo(f154a, "KeyboardHandler/" + ((KeyboardExtension) this.f162i.get(i)).getId() + " selected as default keyboard");
                        this.f165l = i;
                        return;
                    }
                    i++;
                }
                if (this.f162i.size() == 0) {
                    this.f162i.add(C0187d.m175a("com.bosch.myspin.keyboard.en"));
                    return;
                }
                return;
            }
            Logger.logInfo(f154a, "KeyboardHandler/" + this.f157d.getId() + " selected as default keyboard");
        }
    }

    public void switchKeyboard() {
        Logger.logDebug(f154a, "switchKeyboard() mIndex: " + this.f165l + " registered Keyboards: " + this.f162i.size());
        if (this.f165l < 0) {
            m188h();
        }
        ((KeyboardExtension) this.f162i.get(this.f165l)).hide();
        this.f165l = (this.f165l + 1) % this.f162i.size();
        if (this.f163j != null && this.f160g != null) {
            this.f157d = (KeyboardExtension) this.f162i.get(this.f165l);
            m185e();
            this.f157d.setEditText(this.f160g);
            if (this.f157d.getType() == 1002 || this.f160g.getText().toString().isEmpty()) {
                this.f157d.setType(1002);
            } else {
                this.f157d.setType(1001);
            }
            this.f157d.show();
        }
    }

    public void onHideRequest() {
        m194b();
    }

    private void m180a(boolean z) {
        if (this.f163j != null) {
            Intent intent = new Intent("com.bosch.myspin.intent.event.KEYBOARD_VISIBILITY_CHANGED");
            intent.putExtra("com.bosch.myspin.EXTRA_KEYBOARD_VISIBILITY", z);
            this.f163j.getApplicationContext().sendBroadcast(intent);
        }
    }

    public void m192a(ArrayList<String> arrayList) {
        Logger.logDebug(f154a, "KeyboardHandler/setAllowedKeyboardLocals: " + arrayList);
        this.f162i.clear();
        this.f165l = -1;
        if (arrayList == null || arrayList.size() <= 0) {
            this.f162i.add(C0187d.m175a("com.bosch.myspin.keyboard.en"));
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            KeyboardExtension a = C0187d.m175a((String) it.next());
            if (a != null) {
                this.f162i.add(a);
            }
        }
        for (KeyboardExtension a2 : this.f161h) {
            if (arrayList.contains(a2.getId())) {
                this.f162i.add(a2);
            }
        }
    }
}
