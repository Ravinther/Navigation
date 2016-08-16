package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.appcompat.C0101R;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class AppCompatRadioButton extends RadioButton {
    private static final int[] TINT_ATTRS;
    private Drawable mButtonDrawable;
    private TintManager mTintManager;

    static {
        TINT_ATTRS = new int[]{16843015};
    }

    public AppCompatRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, C0101R.attr.radioButtonStyle);
    }

    public AppCompatRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (TintManager.SHOULD_BE_USED) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, TINT_ATTRS, defStyleAttr, 0);
            setButtonDrawable(a.getDrawable(0));
            a.recycle();
            this.mTintManager = a.getTintManager();
        }
    }

    public void setButtonDrawable(Drawable buttonDrawable) {
        super.setButtonDrawable(buttonDrawable);
        this.mButtonDrawable = buttonDrawable;
    }

    public void setButtonDrawable(int resid) {
        if (this.mTintManager != null) {
            setButtonDrawable(this.mTintManager.getDrawable(resid));
        } else {
            super.setButtonDrawable(resid);
        }
    }

    public int getCompoundPaddingLeft() {
        int padding = super.getCompoundPaddingLeft();
        if (VERSION.SDK_INT >= 17 || this.mButtonDrawable == null) {
            return padding;
        }
        return padding + this.mButtonDrawable.getIntrinsicWidth();
    }
}
