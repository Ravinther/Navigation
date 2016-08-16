package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.design.C0001R;
import android.view.View;

abstract class FloatingActionButtonImpl {
    static final int[] EMPTY_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final int[] PRESSED_ENABLED_STATE_SET;
    final ShadowViewDelegate mShadowViewDelegate;
    final View mView;

    abstract void hide();

    abstract void jumpDrawableToCurrentState();

    abstract void onDrawableStateChanged(int[] iArr);

    abstract void setBackgroundDrawable(Drawable drawable, ColorStateList colorStateList, Mode mode, int i, int i2);

    abstract void setBackgroundTintList(ColorStateList colorStateList);

    abstract void setBackgroundTintMode(Mode mode);

    abstract void setElevation(float f);

    abstract void setPressedTranslationZ(float f);

    abstract void setRippleColor(int i);

    abstract void show();

    static {
        PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
        FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
        EMPTY_STATE_SET = new int[0];
    }

    FloatingActionButtonImpl(View view, ShadowViewDelegate shadowViewDelegate) {
        this.mView = view;
        this.mShadowViewDelegate = shadowViewDelegate;
    }

    Drawable createBorderDrawable(int borderWidth, ColorStateList backgroundTint) {
        Resources resources = this.mView.getResources();
        CircularBorderDrawable borderDrawable = newCircularDrawable();
        borderDrawable.setGradientColors(resources.getColor(C0001R.color.fab_stroke_top_outer_color), resources.getColor(C0001R.color.fab_stroke_top_inner_color), resources.getColor(C0001R.color.fab_stroke_end_inner_color), resources.getColor(C0001R.color.fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth((float) borderWidth);
        borderDrawable.setTintColor(backgroundTint.getDefaultColor());
        return borderDrawable;
    }

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }
}
