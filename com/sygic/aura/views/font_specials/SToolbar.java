package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.animation.DecelerateInterpolator;
import com.sygic.aura.helper.GuiUtils;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.base.C1799R;

public class SToolbar extends Toolbar {
    private int mHeight;
    private DecelerateInterpolator mInterpolator;
    private OnInvalidatedMenuListener mMenuInvalidatedListener;

    public interface OnInvalidatedMenuListener {
        void onMenuInvalidated(Menu menu);
    }

    public SToolbar(Context context) {
        super(context);
        init();
    }

    public SToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mHeight = (int) getResources().getDimension(2131230759);
    }

    public void inflateMenu(int resId) {
        if (resId > 0) {
            super.inflateMenu(resId);
            sygicifyMenu(getMenu(), true);
        }
    }

    private void sygicifyMenu(Menu menu, boolean invalidateMenu) {
        if (menu != null) {
            for (int i = 0; i < menu.size(); i++) {
                MenuItemImpl menuItem = (MenuItemImpl) menu.getItem(i);
                boolean capitalize = menuItem.isActionButton() && menuItem.getIcon() == null;
                menuItem.setTitle(GuiUtils.makeCustomFontText(getContext(), ResourceManager.getCoreString(menuItem.getTitle()), capitalize ? 2131166099 : 2131166101, capitalize));
            }
            if (invalidateMenu) {
                invalidateMenu();
            }
        }
    }

    public void invalidateMenuStrings() {
        sygicifyMenu(getMenu(), false);
    }

    public void invalidateMenu() {
        if (this.mMenuInvalidatedListener != null) {
            this.mMenuInvalidatedListener.onMenuInvalidated(getMenu());
        }
    }

    public void setNavigationIconAsUp() {
        setNavigationIcon((int) C1799R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    public void setTitle(int titleRes) {
        super.setTitle(GuiUtils.makeSemiboldFontText(getContext(), ResourceManager.getCoreString(getContext(), titleRes), false));
    }

    public void setTitle(CharSequence title) {
        super.setTitle(GuiUtils.makeSemiboldFontText(getContext(), ResourceManager.getCoreString(title), false));
    }

    public void setSubtitle(int subtitleRes) {
        super.setSubtitle(GuiUtils.makeSemiboldFontText(getContext(), ResourceManager.getCoreString(getContext(), subtitleRes), true));
    }

    public void setSubtitle(CharSequence subtitle) {
        super.setSubtitle(GuiUtils.makeSemiboldFontText(getContext(), ResourceManager.getCoreString(subtitle), true));
    }

    public void setOnMenuInvalidateListener(OnInvalidatedMenuListener menuInvalidateListener) {
        this.mMenuInvalidatedListener = menuInvalidateListener;
    }

    public void show() {
        if (hasHoneycomb()) {
            ensureInterpolator();
            animate().translationY(0.0f).setDuration(225).setInterpolator(this.mInterpolator);
            return;
        }
        setVisibility(0);
    }

    public void hideImmediate() {
        setTranslationY((float) (-this.mHeight));
    }

    public void hide() {
        if (hasHoneycomb()) {
            ensureInterpolator();
            animate().translationY((float) (-this.mHeight)).setDuration(225).setInterpolator(this.mInterpolator);
            return;
        }
        setVisibility(8);
    }

    private boolean hasHoneycomb() {
        return VERSION.SDK_INT >= 12;
    }

    private void ensureInterpolator() {
        if (this.mInterpolator == null) {
            this.mInterpolator = new DecelerateInterpolator();
        }
    }
}
