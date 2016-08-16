package com.sygic.aura.poi.detail.badge;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

public class TextPoiBadge extends PoiBadge {
    public TextPoiBadge(String title, String url, int rating, int ratingMax, int logoRes) {
        super(title, url, rating, ratingMax, logoRes);
    }

    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(2130903075, container, false);
    }

    protected void onViewCreated(View view, ViewGroup container) {
        if (this.mLogoRes != 0) {
            ((SImageView) view.findViewById(2131624120)).setFontDrawableResource(this.mLogoRes);
        }
        if (!TextUtils.isEmpty(this.mTitle)) {
            ((STextView) view.findViewById(C1799R.id.title)).setCoreText(this.mTitle);
        }
        if (this.mRating >= 0 && this.mRatingMax >= 0) {
            ((STextView) view.findViewById(2131624124)).setCoreText(this.mRating + "/" + this.mRatingMax);
        }
    }
}
