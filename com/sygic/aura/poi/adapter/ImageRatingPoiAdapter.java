package com.sygic.aura.poi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.poi.adapter.OnlinePoiAdapter.ViewHolder;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.OnlinePoiListItem;
import com.sygic.aura.views.font_specials.STextView;

public abstract class ImageRatingPoiAdapter extends OnlinePoiAdapter {

    protected static abstract class ImageRatingViewHolder extends ViewHolder {
        private static final String PRICE_LEVEL_STRING = "$$$$";
        private STextView mPriceLevelTextView;
        private ImageView mRatingContainer;

        protected abstract int getImagePlaceHolder();

        protected abstract int[] getRatingImages();

        public ImageRatingViewHolder(Resources res, String selectedLanguage) {
            super(res, selectedLanguage);
        }

        public ImageRatingViewHolder(View view, Resources res, String selectedLanguage) {
            super(view, res, selectedLanguage);
        }

        public ImageRatingViewHolder(Resources res) {
            super(res);
        }

        public void initViews(View view) {
            super.initViews(view);
            this.mRatingContainer = (ImageView) view.findViewById(2131624370);
            this.mPriceLevelTextView = (STextView) view.findViewById(2131624369);
        }

        public boolean updateContent(OnlinePoiListItem item) {
            if (!super.updateContent(item)) {
                return false;
            }
            updateInternal(item.getRating(), item.getPriceLevel());
            return true;
        }

        public void updateBigBadgeContent(OnlinePoiInfoEntry entry) {
            super.updateBigBadgeContent(entry);
            updateInternal(entry.getRating(), entry.getPriceLevel());
        }

        public void updateSmallBadgeContent(OnlinePoiInfoEntry entry) {
            super.updateSmallBadgeContent(entry);
            updateInternal(entry.getRating(), entry.getPriceLevel());
        }

        private void updateInternal(float rating, int priceLevel) {
            this.mRatingContainer.setVisibility(0);
            int roundedRating = Math.round(2.0f * rating);
            if (roundedRating < 0 || roundedRating >= getRatingImages().length) {
                this.mRatingContainer.setImageResource(getRatingImages()[0]);
            } else {
                this.mRatingContainer.setImageResource(getRatingImages()[roundedRating]);
            }
            if (priceLevel <= 0) {
                this.mPriceLevelTextView.setText("");
                this.mPriceLevelTextView.setVisibility(8);
                return;
            }
            this.mPriceLevelTextView.setVisibility(0);
            Spannable spannable = new SpannableString(PRICE_LEVEL_STRING);
            spannable.setSpan(new ForegroundColorSpan(this.mRes.getColor(2131558522)), 0, priceLevel, 33);
            this.mPriceLevelTextView.setText(spannable);
        }

        protected int getBigBadgeLayoutRes() {
            return 2130903152;
        }

        protected int getSmallBadgeLayoutRes() {
            return 2130903153;
        }

        public String getPriceLevelString() {
            return PRICE_LEVEL_STRING;
        }
    }

    protected abstract ViewHolder createViewHolder(View view, Resources resources, String str);

    public ImageRatingPoiAdapter(Context context, LocationQuery locationQuery, long searchRef, String selectedLanguage, LoadingStateListener listener) {
        super(context, locationQuery, searchRef, selectedLanguage, listener);
    }

    protected int getViewResource() {
        return 2130903152;
    }
}
