package com.sygic.aura.poi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.poi.adapter.OnlinePoiAdapter.ViewHolder;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.OnlinePoiListItem;

public class YelpPoiAdapter extends ImageRatingPoiAdapter {
    public static int[] RATING_IMAGES;

    public static class YelpViewHolder extends ImageRatingViewHolder {
        public /* bridge */ /* synthetic */ String getPriceLevelString() {
            return super.getPriceLevelString();
        }

        public /* bridge */ /* synthetic */ void initViews(View view) {
            super.initViews(view);
        }

        public /* bridge */ /* synthetic */ void updateBigBadgeContent(OnlinePoiInfoEntry onlinePoiInfoEntry) {
            super.updateBigBadgeContent(onlinePoiInfoEntry);
        }

        public /* bridge */ /* synthetic */ boolean updateContent(OnlinePoiListItem onlinePoiListItem) {
            return super.updateContent(onlinePoiListItem);
        }

        public /* bridge */ /* synthetic */ void updateSmallBadgeContent(OnlinePoiInfoEntry onlinePoiInfoEntry) {
            super.updateSmallBadgeContent(onlinePoiInfoEntry);
        }

        public YelpViewHolder(Resources res, String selectedLanguage) {
            super(res, selectedLanguage);
        }

        public YelpViewHolder(View view, Resources res, String selectedLanguage) {
            super(view, res, selectedLanguage);
        }

        public YelpViewHolder(Resources res) {
            super(res);
        }

        public int getImagePlaceHolder() {
            return 2130838239;
        }

        protected int[] getRatingImages() {
            return YelpPoiAdapter.RATING_IMAGES;
        }
    }

    static {
        RATING_IMAGES = new int[]{2130838228, 2130838229, 2130838230, 2130838231, 2130838232, 2130838233, 2130838234, 2130838235, 2130838236, 2130838237, 2130838238};
    }

    public YelpPoiAdapter(Context context, LocationQuery locationQuery, long searchRef, String selectedLanguage, LoadingStateListener listener) {
        super(context, locationQuery, searchRef, selectedLanguage, listener);
    }

    protected ViewHolder createViewHolder(View convertView, Resources res, String selectedLanguage) {
        return new YelpViewHolder(convertView, res, selectedLanguage);
    }
}
