package com.sygic.aura.events.key;

import android.content.Context;
import android.view.KeyEvent;
import com.sygic.aura.C1090R;
import com.sygic.aura.SygicMain;

public class KeyEventServiceImpl implements KeyEventService {
    private boolean m_bWasDown;

    private KeyEventServiceImpl() {
        this.m_bWasDown = false;
    }

    public KeyEventServiceImpl(Context context) {
        this.m_bWasDown = false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == 0) {
            this.m_bWasDown = true;
            int mapKeyCode = mapKnobCodes(keyCode, event.isShiftPressed());
            int nKeyChar = event.getUnicodeChar();
            boolean bChar = false;
            if (mapKeyCode != keyCode) {
                nKeyChar = mapKeyCode;
            } else if (nKeyChar == 0 || keyCode == 66) {
                nKeyChar = keyCode;
            } else {
                bChar = true;
            }
            SygicMain.getInstance().KeyMessage(nKeyChar, 0, bChar);
            if (keyCode == 67) {
                SygicMain.getInstance().KeyMessage(nKeyChar, 1, bChar);
            }
            if (keyCode == 4 || keyCode == 84) {
                return true;
            }
        }
        return false;
    }

    private int mapKnobCodes(int keyCode, boolean shift) {
        if (keyCode == 23) {
            return 66;
        }
        if (keyCode == C1090R.styleable.Theme_editTextStyle || keyCode == C1090R.styleable.Theme_ratingBarStyle || (keyCode == 61 && !shift)) {
            return 34;
        }
        if (keyCode == C1090R.styleable.Theme_radioButtonStyle || keyCode == C1090R.styleable.Theme_spinnerStyle || (keyCode == 61 && shift)) {
            return 33;
        }
        return keyCode;
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getAction() == 1 && this.m_bWasDown) {
            this.m_bWasDown = false;
            if (keyCode == 67) {
                return true;
            }
            int mapKeyCode = mapKnobCodes(keyCode, event.isShiftPressed());
            int nKeyChar = event.getUnicodeChar();
            boolean bChar = false;
            if (mapKeyCode != keyCode) {
                nKeyChar = mapKeyCode;
            } else if (nKeyChar == 0 || keyCode == 66) {
                nKeyChar = keyCode;
            } else {
                bChar = true;
            }
            if (keyCode == 4 && SygicMain.getInstance().getFeature().getCommonFeature().isKeybVisible()) {
                SygicMain.getInstance().getFeature().getCommonFeature().setKeybVisible(false);
                return true;
            }
            SygicMain.getInstance().KeyMessage(nKeyChar, 1, bChar);
            if (keyCode == 4 || keyCode == 84) {
                return true;
            }
        }
        return false;
    }
}
