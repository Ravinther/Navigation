package com.sygic.aura.poi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.poi.adapter.OnlinePoiAdapter.ViewHolder;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.OnlinePoiListItem;

public class TripAdvisorPoiAdapter extends ImageRatingPoiAdapter {
    private static final String PRICE_LEVEL = "$$$$";
    public static int[] RATING_IMAGES;

    public static class TripAdvisorViewHolder extends ImageRatingViewHolder {
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

        public TripAdvisorViewHolder(Resources res, String selectedLanguage) {
            super(res, selectedLanguage);
        }

        public TripAdvisorViewHolder(View view, Resources res, String selectedLanguage) {
            super(view, res, selectedLanguage);
        }

        public TripAdvisorViewHolder(Resources res) {
            super(res);
        }

        public int getImagePlaceHolder() {
            return 2130838204;
        }

        protected int[] getRatingImages() {
            return TripAdvisorPoiAdapter.RATING_IMAGES;
        }
    }

    static {
        RATING_IMAGES = new int[]{2130838193, 2130838194, 2130838195, 2130838196, 2130838197, 2130838198, 2130838199, 2130838200, 2130838201, 2130838202, 2130838203};
    }

    public TripAdvisorPoiAdapter(Context context, LocationQuery locationQuery, long searchRef, String selectedLanguage, LoadingStateListener listener) {
        super(context, locationQuery, searchRef, selectedLanguage, listener);
    }

    protected ViewHolder createViewHolder(View convertView, Resources res, String selectedLanguage) {
        return new TripAdvisorViewHolder(convertView, res, selectedLanguage);
    }
}
