package com.sygic.aura.poi.detail.badge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class PoiBadge {
    protected final int mLogoRes;
    protected final int mRating;
    protected final int mRatingMax;
    protected final String mTitle;
    protected final String mUrl;

    protected abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    protected abstract void onViewCreated(View view, ViewGroup viewGroup);

    public PoiBadge(String title, String url, int rating, int ratingMax, int logoRes) {
        this.mTitle = title;
        this.mUrl = url;
        this.mRating = rating;
        this.mRatingMax = ratingMax;
        this.mLogoRes = logoRes;
    }

    public void injectView(LayoutInflater inflater, ViewGroup container) {
        View view = onCreateView(inflater, container);
        onViewCreated(view, container);
        container.addView(view);
    }

    public void performClick(Context context) {
        if (!TextUtils.isEmpty(this.mUrl)) {
            context.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(this.mUrl)));
        }
    }
}
