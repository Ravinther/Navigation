package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.appcompat.C0101R;
import android.support.v7.internal.widget.TintInfo;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import java.lang.reflect.Field;

public class AppCompatSpinner extends Spinner {
    private static final int[] TINT_ATTRS;
    private TintInfo mBackgroundTint;
    private TintInfo mInternalBackgroundTint;
    private TintManager mTintManager;

    static {
        TINT_ATTRS = new int[]{16842964, 16843126};
    }

    public AppCompatSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, C0101R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (TintManager.SHOULD_BE_USED) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, TINT_ATTRS, defStyleAttr, 0);
            if (a.hasValue(0)) {
                ColorStateList tint = a.getTintManager().getTintList(a.getResourceId(0, -1));
                if (tint != null) {
                    setInternalBackgroundTint(tint);
                }
            }
            if (a.hasValue(1)) {
                Drawable popupBackground = a.getDrawable(1);
                if (VERSION.SDK_INT >= 16) {
                    setPopupBackgroundDrawable(popupBackground);
                } else if (VERSION.SDK_INT >= 11) {
                    setPopupBackgroundDrawableV11(this, popupBackground);
                }
            }
            this.mTintManager = a.getTintManager();
            a.recycle();
        }
    }

    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        setInternalBackgroundTint(this.mTintManager != null ? this.mTintManager.getTintList(resId) : null);
    }

    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        setInternalBackgroundTint(null);
    }

    @TargetApi(11)
    private static void setPopupBackgroundDrawableV11(Spinner view, Drawable background) {
        try {
            Field popupField = Spinner.class.getDeclaredField("mPopup");
            popupField.setAccessible(true);
            Object popup = popupField.get(view);
            if (popup instanceof ListPopupWindow) {
                ((ListPopupWindow) popup).setBackgroundDrawable(background);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList tint) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = tint;
        this.mBackgroundTint.mHasTintList = true;
        applySupportBackgroundTint();
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.mBackgroundTint != null ? this.mBackgroundTint.mTintList : null;
    }

    public void setSupportBackgroundTintMode(Mode tintMode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintMode = tintMode;
        this.mBackgroundTint.mHasTintMode = true;
        applySupportBackgroundTint();
    }

    public Mode getSupportBackgroundTintMode() {
        return this.mBackgroundTint != null ? this.mBackgroundTint.mTintMode : null;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        applySupportBackgroundTint();
    }

    private void applySupportBackgroundTint() {
        if (getBackground() == null) {
            return;
        }
        if (this.mBackgroundTint != null) {
            TintManager.tintViewBackground(this, this.mBackgroundTint);
        } else if (this.mInternalBackgroundTint != null) {
            TintManager.tintViewBackground(this, this.mInternalBackgroundTint);
        }
    }

    private void setInternalBackgroundTint(ColorStateList tint) {
        if (tint != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            this.mInternalBackgroundTint.mTintList = tint;
            this.mInternalBackgroundTint.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        applySupportBackgroundTint();
    }
}
