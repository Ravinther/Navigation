package com.sygic.aura.feature.common;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.sygic.aura.SygicMain;

public class TextWatcherDummy implements TextWatcher, OnKeyListener, OnEditorActionListener {
    private boolean bReset;
    private Keys f1254k;
    private boolean mWasDeleted;
    private int nCount;

    public TextWatcherDummy() {
        this.bReset = false;
        this.nCount = 0;
        this.f1254k = new Keys();
        this.mWasDeleted = false;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!this.bReset) {
            if (start + count != s.length()) {
                this.bReset = true;
            }
            this.nCount += count;
            if (this.f1254k.count() == s.length() && !this.mWasDeleted) {
                SygicMain.getInstance().KeyMessage(67, 0, false);
                SygicMain.getInstance().KeyMessage(67, 1, false);
                this.f1254k.del();
                this.mWasDeleted = false;
            }
            int i;
            if (s.length() - this.f1254k.count() > 0) {
                for (i = s.length() - this.f1254k.count(); i > 0; i--) {
                    SygicMain.getInstance().KeyMessage(s.charAt(s.length() - i), 0, true);
                    SygicMain.getInstance().KeyMessage(s.charAt(s.length() - i), 1, true);
                    this.f1254k.add();
                }
                this.mWasDeleted = false;
            } else if (s.length() - this.f1254k.count() < 0 && !this.mWasDeleted) {
                for (i = this.f1254k.count() - s.length(); i > 0; i--) {
                    SygicMain.getInstance().KeyMessage(67, 0, false);
                    SygicMain.getInstance().KeyMessage(67, 1, false);
                    this.f1254k.del();
                }
                this.mWasDeleted = false;
            }
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
        if (this.bReset) {
            s.clear();
            this.bReset = false;
            this.nCount = 0;
            this.f1254k.reset();
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == 0 && event.getKeyCode() == 67) {
            SygicMain.getInstance().KeyMessage(67, 0, false);
            this.f1254k.del();
            this.mWasDeleted = true;
            return true;
        } else if (event.getAction() != 1 || event.getKeyCode() != 67) {
            return false;
        } else {
            SygicMain.getInstance().KeyMessage(67, 1, false);
            this.mWasDeleted = true;
            return true;
        }
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId != 6) {
            return false;
        }
        SygicMain.getInstance().KeyMessage(66, 0, false);
        SygicMain.getInstance().KeyMessage(66, 1, false);
        return true;
    }

    public void reset() {
        this.bReset = true;
    }
}
