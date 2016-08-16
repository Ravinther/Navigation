package com.sygic.aura.poi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.WebViewFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.poi.adapter.OnlinePoiAdapter.ViewHolder;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.OnlinePoiListItem;
import com.sygic.aura.views.font_specials.SButton;
import com.sygic.aura.views.font_specials.STextView;

public class ViatorPoiAdapter extends ImageRatingPoiAdapter {
    public static int[] RATING_IMAGES;

    private class ViatorViewHolder extends ImageRatingViewHolder {
        private SButton mReserveButton;
        private STextView mReservePrice;
        private ViewGroup mReserveView;

        /* renamed from: com.sygic.aura.poi.adapter.ViatorPoiAdapter.ViatorViewHolder.1 */
        class C14371 implements OnClickListener {
            final /* synthetic */ String val$detailUri;

            C14371(String str) {
                this.val$detailUri = str;
            }

            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uri", this.val$detailUri);
                Fragments.add((Activity) ViatorPoiAdapter.this.getContext(), WebViewFragment.class, "fragment_webview", bundle);
                SygicAnalyticsLogger.getAnalyticsEvent(ViatorPoiAdapter.this.getContext(), EventType.SEARCH).setName("Attraction booking").setValue("click", "viator").logAndRecycle();
            }
        }

        public ViatorViewHolder(Resources res, String selectedLanguage) {
            super(res, selectedLanguage);
        }

        public ViatorViewHolder(View view, Resources res, String selectedLanguage) {
            super(view, res, selectedLanguage);
        }

        public ViatorViewHolder(Resources res) {
            super(res);
        }

        public void initViews(View view) {
            super.initViews(view);
            this.mReserveView = (ViewGroup) view.findViewById(2131624464);
            this.mReserveButton = (SButton) view.findViewById(2131624466);
            this.mReservePrice = (STextView) view.findViewById(2131624467);
        }

        public boolean updateContent(OnlinePoiListItem item) {
            if (!super.updateContent(item)) {
                return false;
            }
            updateInternal(item.getPrice(), item.getBookingUrl());
            return true;
        }

        public void updateBigBadgeContent(OnlinePoiInfoEntry entry) {
            super.updateBigBadgeContent(entry);
            updateInternal(entry.getPrice(), entry.getBookingUrl());
        }

        private void updateInternal(String price, String detailUri) {
            if (TextUtils.isEmpty(price)) {
                this.mReserveView.setVisibility(8);
                return;
            }
            this.mReserveView.setVisibility(0);
            this.mReservePrice.setText(price);
            this.mReserveButton.setOnClickListener(new C14371(detailUri));
        }

        public void showEmpty(String emptyLabel) {
            super.showEmpty(emptyLabel);
            this.mReserveView.setVisibility(8);
        }

        public int getImagePlaceHolder() {
            return 2130838218;
        }

        protected int[] getRatingImages() {
            return ViatorPoiAdapter.RATING_IMAGES;
        }
    }

    static {
        RATING_IMAGES = new int[]{2130838207, 2130838208, 2130838209, 2130838210, 2130838211, 2130838212, 2130838213, 2130838214, 2130838215, 2130838216, 2130838217};
    }

    public ViatorPoiAdapter(Context context, LocationQuery locationQuery, long searchRef, String selectedLanguage, LoadingStateListener listener) {
        super(context, locationQuery, searchRef, selectedLanguage, listener);
    }

    protected ViewHolder createViewHolder(View convertView, Resources res, String selectedLanguage) {
        return new ViatorViewHolder(convertView, res, selectedLanguage);
    }
}
