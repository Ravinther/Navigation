package com.sygic.aura.poi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.network.ConnectionManager;
import com.sygic.aura.poi.CircleTransformation;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.OnlinePoiListItem;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

public abstract class OnlinePoiAdapter extends PoiAdapter {
    private final LayoutInflater mInflater;
    protected final Resources mRes;
    private final String mSelectedLanguage;

    public static abstract class ViewHolder {
        protected static final String EMPTY_STRING = "";
        private static CircleTransformation sTransformation;
        protected View mBottomPart;
        private STextView mDistanceTextView;
        private ImageView mImageView;
        private STextView mPriceLevelTextView;
        protected Resources mRes;
        protected String mSelectedLanguage;
        private STextView mTitleTextView;
        private STextView mVotesTextView;

        protected abstract int getBigBadgeLayoutRes();

        protected abstract int getImagePlaceHolder();

        protected abstract String getPriceLevelString();

        protected abstract int getSmallBadgeLayoutRes();

        static {
            sTransformation = new CircleTransformation();
        }

        public ViewHolder(View view, Resources res, String selectedLanguage) {
            this(res, selectedLanguage);
            initViews(view);
        }

        public ViewHolder(Resources res, String selectedLanguage) {
            this.mRes = res;
            if (TextUtils.isEmpty(selectedLanguage)) {
                selectedLanguage = SettingsManager.nativeGetSelectedLanguage();
            }
            this.mSelectedLanguage = selectedLanguage;
        }

        public ViewHolder(Resources res) {
            this(res, SettingsManager.nativeGetSelectedLanguage());
        }

        public void initViews(View view) {
            this.mImageView = (ImageView) view.findViewById(C1799R.id.image);
            this.mTitleTextView = (STextView) view.findViewById(2131624363);
            this.mDistanceTextView = (STextView) view.findViewById(2131624119);
            this.mBottomPart = view.findViewById(2131624364);
            this.mVotesTextView = (STextView) view.findViewById(2131624368);
            this.mPriceLevelTextView = (STextView) view.findViewById(2131624369);
        }

        public View inflateAndUpdateBigBadge(LayoutInflater inflater, ViewGroup container, OnlinePoiInfoEntry entry) {
            View view = inflater.inflate(getBigBadgeLayoutRes(), container, false);
            initViews(view);
            updateBigBadgeContent(entry);
            return view;
        }

        public View inflateAndUpdateSmallBadge(LayoutInflater inflater, ViewGroup container, OnlinePoiInfoEntry entry) {
            View view = inflater.inflate(getSmallBadgeLayoutRes(), container, false);
            initViews(view);
            updateSmallBadgeContent(entry);
            return view;
        }

        public boolean updateContent(OnlinePoiListItem item) {
            if (item.getGroupId() == -1) {
                showEmpty(item.getDisplayName());
                return false;
            }
            updateInternal(item.getDisplayName(), item.getPoiDistance(), item.getFormattedDistance(), item.getVotes(), item.getImageUrl(), item.getPriceLevel());
            return true;
        }

        public void updateBigBadgeContent(OnlinePoiInfoEntry entry) {
            updateInternal(entry.getType().toString(), 0, null, entry.getRatingCount(), entry.getPhotoLink(), entry.getPriceLevel());
        }

        public void updateSmallBadgeContent(OnlinePoiInfoEntry entry) {
            updateInternalBase(entry.getType().toString(), entry.getPhotoLink(), entry.getPriceLevel());
        }

        private void updateInternal(String name, int distance, String formattedDistance, int votes, String imageUrl, int priceLevel) {
            updateInternalBase(name, imageUrl, priceLevel);
            if (distance <= 0) {
                this.mDistanceTextView.setText(EMPTY_STRING);
            } else {
                this.mDistanceTextView.setText(formattedDistance);
            }
            this.mBottomPart.setVisibility(0);
            String votesString = ResourceManager.getQuantityString(this.mRes, 2131689472, votes, this.mSelectedLanguage);
            this.mVotesTextView.setText(String.format(votesString, new Object[]{Integer.valueOf(votes)}));
        }

        private void updateInternalBase(String name, String imageUrl, int priceLevel) {
            this.mTitleTextView.setText(name);
            if (TextUtils.isEmpty(imageUrl)) {
                this.mImageView.setImageResource(getImagePlaceHolder());
            } else {
                Picasso.with(this.mImageView.getContext()).load(imageUrl).placeholder(getImagePlaceHolder()).error(getImagePlaceHolder()).transform(sTransformation).fit().centerCrop().into(this.mImageView);
            }
            if (priceLevel <= 0) {
                this.mPriceLevelTextView.setText(EMPTY_STRING);
                this.mPriceLevelTextView.setVisibility(8);
                return;
            }
            this.mPriceLevelTextView.setVisibility(0);
            Spannable spannable = new SpannableString(getPriceLevelString());
            spannable.setSpan(new ForegroundColorSpan(this.mRes.getColor(2131558597)), 0, priceLevel, 33);
            this.mPriceLevelTextView.setText(spannable);
        }

        public void showEmpty(String emptyLabel) {
            this.mTitleTextView.setText(emptyLabel);
            this.mImageView.setImageResource(getImagePlaceHolder());
            this.mDistanceTextView.setText(EMPTY_STRING);
            this.mBottomPart.setVisibility(8);
        }
    }

    protected abstract ViewHolder createViewHolder(View view, Resources resources, String str);

    protected abstract int getViewResource();

    public OnlinePoiAdapter(Context context, LocationQuery locationQuery, long searchRef, String selectedLanguage, LoadingStateListener listener) {
        super(context, locationQuery, searchRef, listener);
        this.mInflater = LayoutInflater.from(context);
        this.mRes = context.getResources();
        this.mSelectedLanguage = selectedLanguage;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(getViewResource(), parent, false);
            holder = createViewHolder(convertView, this.mRes, this.mSelectedLanguage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.updateContent((OnlinePoiListItem) getItem(position));
        return convertView;
    }

    protected PoiListItem getEmptyItem() {
        return OnlinePoiListItem.getSpecialItemInstance(this.mNoItemsLabel);
    }

    protected void executePoiLoading() {
        if (ConnectionManager.nativeIsConnected()) {
            super.executePoiLoading();
        } else {
            this.mListener.onLoadingError();
        }
    }

    public long[] getShowOnMapData(int items) {
        long[] pois = new long[items];
        for (int i = 0; i < pois.length; i++) {
            pois[i] = ((PoiListItem) getItem(i)).getLongPosition().toNativeLong();
        }
        return pois;
    }
}
