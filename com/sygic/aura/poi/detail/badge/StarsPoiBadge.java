package com.sygic.aura.poi.detail.badge;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

public class StarsPoiBadge extends PoiBadge {
    private final int mStarRes;

    public StarsPoiBadge(String title, String url, int rating, int ratingMax, int logoRes, int starRes) {
        super(title, url, rating, ratingMax, logoRes);
        this.mStarRes = starRes;
    }

    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(2130903074, container, false);
    }

    public void onViewCreated(View view, ViewGroup container) {
        if (this.mLogoRes != 0) {
            ((SImageView) view.findViewById(2131624120)).setFontDrawableResource(this.mLogoRes);
        }
        if (!TextUtils.isEmpty(this.mTitle)) {
            ((STextView) view.findViewById(C1799R.id.title)).setCoreText(this.mTitle);
        }
        if (this.mRating >= 0 && this.mRatingMax >= 0) {
            ViewGroup starsContainer = (ViewGroup) view.findViewById(2131624123);
            Resources res = container.getResources();
            LayoutParams lp = new LayoutParams(-2, -2);
            int i = 0;
            while (i < this.mRatingMax) {
                SImageView imageView = new SImageView(container.getContext());
                imageView.setScaleType(ScaleType.FIT_CENTER);
                imageView.setAdjustViewBounds(true);
                FontDrawable icon = (FontDrawable) FontDrawable.inflate(res, this.mStarRes);
                icon.setColor(i < this.mRating ? res.getColor(2131558498) : res.getColor(2131558493));
                imageView.setImageDrawable(icon);
                starsContainer.addView(imageView, lp);
                i++;
            }
        }
    }
}
