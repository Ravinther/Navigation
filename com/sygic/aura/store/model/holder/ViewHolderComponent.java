package com.sygic.aura.store.model.holder;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.ViewAnimator;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderComponent extends StoreHolder {
    private final Drawable mDrawableAvailable;
    private final Drawable mDrawableDownloading;
    private final Drawable mDrawableInstalled;
    private final Animation mInAnimation;
    private final Animation mOutAnimation;
    protected final ProgressBar progressBar;
    protected final ViewAnimator switcher;

    public ViewHolderComponent(View view, Drawable installed, Drawable downloading, Drawable available, Animation inAnimation, Animation outAnimation) {
        super(view);
        this.switcher = (ViewAnimator) view.findViewById(2131624408);
        this.progressBar = (ProgressBar) this.switcher.getChildAt(1);
        this.mDrawableInstalled = installed;
        this.mDrawableDownloading = downloading;
        this.mDrawableAvailable = available;
        this.mInAnimation = inAnimation;
        this.mOutAnimation = outAnimation;
    }

    public ProgressBar getProgressView() {
        return this.progressBar;
    }

    public void showProgressAnim(boolean show) {
        this.switcher.setInAnimation(this.mInAnimation);
        this.switcher.setOutAnimation(this.mOutAnimation);
        showProgress(show);
        this.switcher.setInAnimation(null);
        this.switcher.setOutAnimation(null);
    }

    public void showProgress(boolean show) {
        int nextChild = show ? 1 : 0;
        if (this.switcher.getDisplayedChild() != nextChild) {
            if (show) {
                this.progressBar.setIndeterminate(true);
            }
            this.switcher.setDisplayedChild(nextChild);
        }
    }

    public void setActionState(boolean isInstalled, boolean isDownloading) {
        setActionState(isInstalled, isDownloading, false);
    }

    public void setActionState(boolean isInstalled, boolean isDownloading, boolean isUpdateAvailable) {
        Drawable drawable = isInstalled ? this.mDrawableInstalled : isDownloading ? this.mDrawableDownloading : isUpdateAvailable ? this.mDrawableInstalled : this.mDrawableAvailable;
        this.mStatusIcon.setImageDrawable(drawable);
    }

    private void setSummary(String summaryText) {
        int visibility;
        if (TextUtils.isEmpty(summaryText)) {
            visibility = 8;
        } else {
            visibility = 0;
            this.mDescription.setText(summaryText);
        }
        this.switcher.setVisibility(visibility);
    }

    public void updateContent(StoreEntry item) {
        this.mTitle.setText(item.getTitle());
        setSummary(item.getSummary());
        loadIcon(item, false);
    }

    public EViewType getType() {
        return EViewType.TYPE_COMPONENT;
    }
}
