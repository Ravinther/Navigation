package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.design.C0001R;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import loquendo.tts.engine.TTSConst;

public class NavigationView extends ScrimInsetsFrameLayout {
    private static final int[] CHECKED_STATE_SET;
    private static final int[] DISABLED_STATE_SET;
    private OnNavigationItemSelectedListener mListener;
    private int mMaxWidth;
    private final MenuBuilder mMenu;
    private MenuInflater mMenuInflater;
    private final NavigationMenuPresenter mPresenter;

    /* renamed from: android.support.design.widget.NavigationView.1 */
    class C00121 implements Callback {
        C00121() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return NavigationView.this.mListener != null && NavigationView.this.mListener.onNavigationItemSelected(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
        }
    }

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(MenuItem menuItem);
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR;
        public Bundle menuState;

        /* renamed from: android.support.design.widget.NavigationView.SavedState.1 */
        static class C00131 implements Creator<SavedState> {
            C00131() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        public SavedState(Parcel in) {
            super(in);
            this.menuState = in.readBundle();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeBundle(this.menuState);
        }

        static {
            CREATOR = new C00131();
        }
    }

    static {
        CHECKED_STATE_SET = new int[]{16842912};
        DISABLED_STATE_SET = new int[]{-16842910};
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        ColorStateList itemIconTint;
        ColorStateList itemTextColor;
        super(context, attrs, defStyleAttr);
        this.mMenu = new MenuBuilder(context);
        TypedArray a = context.obtainStyledAttributes(attrs, C0001R.styleable.NavigationView, defStyleAttr, C0001R.style.Widget_Design_NavigationView);
        setBackgroundDrawable(a.getDrawable(C0001R.styleable.NavigationView_android_background));
        if (a.hasValue(C0001R.styleable.NavigationView_elevation)) {
            ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(C0001R.styleable.NavigationView_elevation, 0));
        }
        ViewCompat.setFitsSystemWindows(this, a.getBoolean(C0001R.styleable.NavigationView_android_fitsSystemWindows, false));
        this.mMaxWidth = a.getDimensionPixelSize(C0001R.styleable.NavigationView_android_maxWidth, 0);
        if (a.hasValue(C0001R.styleable.NavigationView_itemIconTint)) {
            itemIconTint = a.getColorStateList(C0001R.styleable.NavigationView_itemIconTint);
        } else {
            itemIconTint = createDefaultColorStateList(16842808);
        }
        if (a.hasValue(C0001R.styleable.NavigationView_itemTextColor)) {
            itemTextColor = a.getColorStateList(C0001R.styleable.NavigationView_itemTextColor);
        } else {
            itemTextColor = createDefaultColorStateList(16842806);
        }
        Drawable itemBackground = a.getDrawable(C0001R.styleable.NavigationView_itemBackground);
        if (a.hasValue(C0001R.styleable.NavigationView_menu)) {
            inflateMenu(a.getResourceId(C0001R.styleable.NavigationView_menu, 0));
        }
        this.mMenu.setCallback(new C00121());
        this.mPresenter = new NavigationMenuPresenter();
        this.mPresenter.setId(1);
        this.mPresenter.initForMenu(context, this.mMenu);
        this.mPresenter.setItemIconTintList(itemIconTint);
        this.mPresenter.setItemTextColor(itemTextColor);
        this.mPresenter.setItemBackground(itemBackground);
        this.mMenu.addMenuPresenter(this.mPresenter);
        addView((View) this.mPresenter.getMenuView(this));
        if (a.hasValue(C0001R.styleable.NavigationView_headerLayout)) {
            inflateHeaderView(a.getResourceId(C0001R.styleable.NavigationView_headerLayout, 0));
        }
        a.recycle();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        state.menuState = new Bundle();
        this.mMenu.savePresenterStates(state.menuState);
        return state;
    }

    protected void onRestoreInstanceState(Parcelable savedState) {
        SavedState state = (SavedState) savedState;
        super.onRestoreInstanceState(state.getSuperState());
        this.mMenu.restorePresenterStates(state.menuState);
    }

    public void setNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
        this.mListener = listener;
    }

    protected void onMeasure(int widthSpec, int heightSpec) {
        switch (MeasureSpec.getMode(widthSpec)) {
            case Integer.MIN_VALUE:
                widthSpec = MeasureSpec.makeMeasureSpec(Math.min(MeasureSpec.getSize(widthSpec), this.mMaxWidth), 1073741824);
                break;
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                widthSpec = MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
                break;
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    public void inflateMenu(int resId) {
        if (this.mPresenter != null) {
            this.mPresenter.setUpdateSuspended(true);
        }
        getMenuInflater().inflate(resId, this.mMenu);
        if (this.mPresenter != null) {
            this.mPresenter.setUpdateSuspended(false);
            this.mPresenter.updateMenuView(false);
        }
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public View inflateHeaderView(int res) {
        return this.mPresenter.inflateHeaderView(res);
    }

    public ColorStateList getItemIconTintList() {
        return this.mPresenter.getItemTintList();
    }

    public void setItemIconTintList(ColorStateList tint) {
        this.mPresenter.setItemIconTintList(tint);
    }

    public ColorStateList getItemTextColor() {
        return this.mPresenter.getItemTextColor();
    }

    public void setItemTextColor(ColorStateList textColor) {
        this.mPresenter.setItemTextColor(textColor);
    }

    public Drawable getItemBackground() {
        return this.mPresenter.getItemBackground();
    }

    public void setItemBackgroundResource(int resId) {
        setItemBackground(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setItemBackground(Drawable itemBackground) {
        this.mPresenter.setItemBackground(itemBackground);
    }

    private MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.mMenuInflater = new SupportMenuInflater(getContext());
        }
        return this.mMenuInflater;
    }

    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = getResources().getColorStateList(value.resourceId);
        if (!getContext().getTheme().resolveAttribute(C0001R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor});
    }
}
