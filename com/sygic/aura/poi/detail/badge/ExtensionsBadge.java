package com.sygic.aura.poi.detail.badge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

public class ExtensionsBadge extends PoiBadge {
    private String mIconPath;
    private String mRatingPath;
    private String mReviews;

    public ExtensionsBadge(String title, String url) {
        super(title, url, 0, 0, 0);
    }

    public ExtensionsBadge setIconPath(String iconPath) {
        this.mIconPath = iconPath;
        return this;
    }

    public ExtensionsBadge setRatingPath(String ratingPath) {
        this.mRatingPath = ratingPath;
        return this;
    }

    public ExtensionsBadge setReviews(String reviews) {
        this.mReviews = reviews;
        return this;
    }

    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(2130903073, container, false);
    }

    public void onViewCreated(View view, ViewGroup container) {
        Bitmap bitmap;
        if (!TextUtils.isEmpty(this.mTitle)) {
            ((STextView) view.findViewById(C1799R.id.title)).setCoreText(this.mTitle);
        }
        if (!TextUtils.isEmpty(this.mIconPath)) {
            bitmap = BitmapFactory.decodeFile(this.mIconPath);
            if (bitmap != null) {
                ((SImageView) view.findViewById(2131624120)).setImageBitmap(bitmap);
            }
        }
        if (!TextUtils.isEmpty(this.mRatingPath)) {
            bitmap = BitmapFactory.decodeFile(this.mRatingPath);
            if (bitmap != null) {
                ((SImageView) view.findViewById(2131624121)).setImageBitmap(bitmap);
            }
        }
        if (!TextUtils.isEmpty(this.mReviews)) {
            ((STextView) view.findViewById(2131624122)).setText(this.mReviews);
        }
    }
}
