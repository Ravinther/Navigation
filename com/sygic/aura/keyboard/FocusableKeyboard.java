package com.sygic.aura.keyboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import com.sygic.aura.C1090R;
import com.sygic.aura.keyboard.FocusableKeyboardView.OnKeyboardActionListener;
import com.sygic.aura.search.model.data.PoiListItem;
import java.util.ArrayList;
import java.util.Iterator;
import loquendo.tts.engine.TTSConst;

public class FocusableKeyboard implements OnKeyListener, OnKeyboardActionListener {
    private Activity mHostActivity;
    private Keyboard mKbQwerty;
    private final int mKbQwertyResId;
    private Keyboard mKbSymbols1;
    private final int mKbSymbols1ResId;
    private Keyboard mKbSymbols2;
    private final int mKbSymbols2ResId;
    private FocusableKeyboardView mKeyboardView;
    private ArrayList<OnKeyboardVisibilityListener> mVisibilityListeners;

    public interface OnKeyboardVisibilityListener {
        void onVisibilityChanged(boolean z);
    }

    /* renamed from: com.sygic.aura.keyboard.FocusableKeyboard.1 */
    class C12861 implements OnFocusChangeListener {
        C12861() {
        }

        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                FocusableKeyboard.this.show();
            } else {
                FocusableKeyboard.this.hide();
            }
        }
    }

    /* renamed from: com.sygic.aura.keyboard.FocusableKeyboard.2 */
    class C12872 implements OnClickListener {
        C12872() {
        }

        public void onClick(View view) {
            FocusableKeyboard.this.show();
        }
    }

    /* renamed from: com.sygic.aura.keyboard.FocusableKeyboard.3 */
    class C12883 implements OnTouchListener {
        C12883() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            EditText edittext = (EditText) view;
            int inType = edittext.getInputType();
            edittext.setInputType(0);
            edittext.onTouchEvent(event);
            edittext.setInputType(inType);
            return true;
        }
    }

    public FocusableKeyboard(Activity host, FocusableKeyboardView kbView) {
        this.mKbQwertyResId = 2131034338;
        this.mKbSymbols1ResId = 2131034339;
        this.mKbSymbols2ResId = 2131034340;
        this.mVisibilityListeners = new ArrayList();
        this.mHostActivity = host;
        this.mKeyboardView = kbView;
        this.mKbQwerty = new Keyboard(this.mHostActivity, 2131034338);
        init();
    }

    private void init() {
        if (this.mKeyboardView != null) {
            this.mKeyboardView.setKeyboard(this.mKbQwerty);
            this.mKeyboardView.setPreviewEnabled(true);
            this.mKeyboardView.setOnKeyboardActionListener(this);
            this.mKeyboardView.showKeyFocus();
        }
    }

    public void addKeyboardVisibilityListener(OnKeyboardVisibilityListener listener) {
        if (listener != null) {
            this.mVisibilityListeners.add(listener);
        }
    }

    public void removeKeyboardVisibilityListener(OnKeyboardVisibilityListener listener) {
        this.mVisibilityListeners.remove(listener);
    }

    public void setKeyboardView(FocusableKeyboardView view) {
        this.mKeyboardView = view;
        init();
    }

    public void show() {
        if (this.mKeyboardView != null) {
            this.mKeyboardView.setVisibility(0);
            this.mKeyboardView.setEnabled(true);
        }
        Iterator it = this.mVisibilityListeners.iterator();
        while (it.hasNext()) {
            ((OnKeyboardVisibilityListener) it.next()).onVisibilityChanged(true);
        }
    }

    public void hide() {
        if (this.mKeyboardView != null) {
            this.mKeyboardView.setVisibility(8);
            this.mKeyboardView.setEnabled(false);
        }
        Iterator it = this.mVisibilityListeners.iterator();
        while (it.hasNext()) {
            ((OnKeyboardVisibilityListener) it.next()).onVisibilityChanged(false);
        }
    }

    public void registerEditText(EditText editText) {
        editText.setOnFocusChangeListener(new C12861());
        editText.setOnKeyListener(this);
        editText.setOnClickListener(new C12872());
        editText.setInputType(editText.getInputType() | 524288);
        editText.setOnTouchListener(new C12883());
    }

    public void onPress(int primaryCode) {
    }

    public void onRelease(int primaryCode) {
    }

    public void onKey(int primaryCode, int[] keyCodes) {
        View focusCurrent = this.mHostActivity.getWindow().getCurrentFocus();
        this.mKeyboardView.focusSearch(66);
        if (focusCurrent instanceof EditText) {
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            switch (primaryCode) {
                case -5:
                    if (editable != null && start > 0) {
                        editable.delete(start - 1, start);
                    }
                case -3:
                    hide();
                case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                    this.mKeyboardView.setShifted(!this.mKeyboardView.isShifted());
                case 66001:
                case 66002:
                case 66003:
                    this.mKeyboardView.setKeyboard(this.mKbQwerty);
                case 66004:
                    if (this.mKbSymbols1 == null) {
                        this.mKbSymbols1 = new Keyboard(this.mKeyboardView.getContext(), 2131034339);
                    }
                    this.mKeyboardView.setKeyboard(this.mKbSymbols1);
                case 66005:
                    if (this.mKbSymbols2 == null) {
                        this.mKbSymbols2 = new Keyboard(this.mKeyboardView.getContext(), 2131034340);
                    }
                    this.mKeyboardView.setKeyboard(this.mKbSymbols2);
                default:
                    char code = (char) primaryCode;
                    if (this.mKeyboardView.isShifted() && Character.isLetter(code)) {
                        code = Character.toUpperCase(code);
                    }
                    editable.insert(start, Character.toString(code));
            }
        }
    }

    public void onText(CharSequence pText) {
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                show();
                return true;
            case TTSConst.TTSEVT_STYLECHANGE /*20*/:
                hide();
                return true;
            case C1090R.styleable.Theme_actionBarDivider /*23*/:
                if (event.getAction() == 0) {
                    this.mKeyboardView.pressKeyFocus();
                    return true;
                } else if (event.getAction() != 1) {
                    return true;
                } else {
                    this.mKeyboardView.releaseKeyFocus();
                    return true;
                }
            case C1090R.styleable.Theme_popupWindowStyle /*61*/:
                if (event.getAction() != 1) {
                    return true;
                }
                if ((event.getMetaState() & 1) == 1) {
                    this.mKeyboardView.rotateLeftKeyFocus();
                    return true;
                }
                this.mKeyboardView.rotateRightKeyFocus();
                return true;
            default:
                return false;
        }
    }
}
