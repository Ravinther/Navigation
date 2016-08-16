package com.sygic.aura.poi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.poi.adapter.OnlinePoiAdapter.ViewHolder;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.OnlinePoiListItem;
import com.sygic.aura.views.font_specials.STextView;

public class FsqPoiAdapter extends OnlinePoiAdapter {

    public static class FsqViewHolder extends ViewHolder {
        public static final String MAX_RATING = "/10";
        public static final String PRICE_LEVEL_STRING = "\u20ac\u20ac\u20ac\u20ac";
        private View mRatingContainer;
        private STextView mRatingMaxTextView;
        private STextView mRatingTextView;

        public FsqViewHolder(Resources res, String selectedLanguage) {
            super(res, selectedLanguage);
        }

        public FsqViewHolder(View view, Resources res, String selectedLanguage) {
            super(view, res, selectedLanguage);
        }

        public FsqViewHolder(Resources res) {
            super(res);
        }

        public void initViews(View view) {
            super.initViews(view);
            this.mRatingContainer = view.findViewById(2131624365);
            this.mRatingTextView = (STextView) view.findViewById(2131624366);
            this.mRatingMaxTextView = (STextView) view.findViewById(2131624367);
        }

        public boolean updateContent(OnlinePoiListItem item) {
            if (!super.updateContent(item)) {
                return false;
            }
            updateInternal(item.getRating(), item.getRatingColor());
            return true;
        }

        public void updateBigBadgeContent(OnlinePoiInfoEntry entry) {
            super.updateBigBadgeContent(entry);
            updateInternal(entry.getRating(), (int) entry.getRatingColor());
        }

        public void updateSmallBadgeContent(OnlinePoiInfoEntry entry) {
            super.updateSmallBadgeContent(entry);
            updateInternal(entry.getRating(), (int) entry.getRatingColor());
        }

        private void updateInternal(float rating, int ratingColor) {
            if (((double) rating) != -1.0d) {
                this.mRatingContainer.setVisibility(0);
                this.mRatingTextView.setText(String.valueOf(rating));
                this.mRatingMaxTextView.setText(MAX_RATING);
                updateRatingBackgroundColor(ratingColor);
                return;
            }
            this.mRatingContainer.setVisibility(8);
        }

        private void updateRatingBackgroundColor(int ratingColor) {
            Drawable background = this.mRatingContainer.getBackground();
            if (background != null && (background instanceof GradientDrawable)) {
                ((GradientDrawable) background).setColor(ratingColor);
            }
        }

        protected int getBigBadgeLayoutRes() {
            return 2130903150;
        }

        protected int getSmallBadgeLayoutRes() {
            return 2130903151;
        }

        public String getPriceLevelString() {
            return PRICE_LEVEL_STRING;
        }

        public int getImagePlaceHolder() {
            return 2130838035;
        }
    }

    public FsqPoiAdapter(Context context, LocationQuery locationQuery, long searchRef, String selectedLanguage, LoadingStateListener listener) {
        super(context, locationQuery, searchRef, selectedLanguage, listener);
    }

    protected ViewHolder createViewHolder(View convertView, Resources mRes, String selectedLanguage) {
        return new FsqViewHolder(convertView, mRes, selectedLanguage);
    }

    protected int getViewResource() {
        return 2130903150;
    }
}
