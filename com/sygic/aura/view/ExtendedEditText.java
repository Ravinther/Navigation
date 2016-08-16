package com.sygic.aura.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import com.sygic.aura.SygicMain;
import com.sygic.aura.events.ActivityUserInteractionListener;

public class ExtendedEditText extends EditText {

    private class ExtendedInputConnection extends InputConnectionWrapper {
        public ExtendedInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (ExtendedEditText.this.length() < 1) {
                ExtendedEditText.this.dispatchKeyEvent(new KeyEvent(0, 67));
                ExtendedEditText.this.dispatchKeyEvent(new KeyEvent(1, 67));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    public ExtendedEditText(Context context) {
        super(context);
    }

    public ExtendedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (!isInEditMode()) {
            ActivityUserInteractionListener eventListener = SygicMain.getActivityUserInteractionListener();
            if (eventListener != null) {
                eventListener.onUserInteraction();
            }
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection target = super.onCreateInputConnection(outAttrs);
        return target == null ? null : new ExtendedInputConnection(target, true);
    }
}
