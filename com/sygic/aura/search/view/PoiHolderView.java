package com.sygic.aura.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.poi.PoiFragmentResultCallback;
import com.sygic.aura.poi.PoiManager;
import com.sygic.aura.poi.fragment.PoiFragment;
import com.sygic.aura.poi.nearbypoi.fragment.NearbyPoiCategoryFragment;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup;
import com.sygic.aura.poi.provider.PoiProvider;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.search.model.RouteWaypointsAdapter;
import com.sygic.aura.search.model.data.SearchLocationData;
import java.util.ArrayList;
import java.util.List;

public class PoiHolderView extends LinearLayout {
    public static final int[] POI_IDS;
    private RouteWaypointsAdapter mAdapter;
    private PoiFragmentResultCallback mCallback;
    private OnMoreClickListener mOnMoreClickedListener;
    private final List<ImageView> mShortcuts;

    public interface OnMoreClickListener {
        void onMoreClicked(SearchLocationData searchLocationData);
    }

    /* renamed from: com.sygic.aura.search.view.PoiHolderView.1 */
    class C15891 implements OnClickListener {
        C15891() {
        }

        public void onClick(View v) {
            NaviNativeActivity.hideKeyboard(v.getWindowToken());
            if (PoiHolderView.this.mOnMoreClickedListener != null) {
                PoiHolderView.this.mOnMoreClickedListener.onMoreClicked(PoiHolderView.this.mAdapter.getCurrentItem());
            }
        }
    }

    /* renamed from: com.sygic.aura.search.view.PoiHolderView.2 */
    class C15902 implements OnClickListener {
        final /* synthetic */ int val$groupId;
        final /* synthetic */ PoiProvider[] val$poiProviders;
        final /* synthetic */ String val$stringCode;
        final /* synthetic */ String val$title;

        C15902(String str, int i, PoiProvider[] poiProviderArr, String str2) {
            this.val$stringCode = str;
            this.val$groupId = i;
            this.val$poiProviders = poiProviderArr;
            this.val$title = str2;
        }

        public void onClick(View v) {
            NaviNativeActivity.hideKeyboard(v.getWindowToken());
            SygicAnalyticsLogger.getAnalyticsEvent(PoiHolderView.this.getContext(), EventType.SEARCH).setName("search").setValue("click", this.val$stringCode).logAndRecycle();
            FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, this.val$stringCode);
            bundle.putInt(PoiFragment.ARG_GROUP_ID, this.val$groupId);
            bundle.putParcelable(PoiFragment.ARG_DATA, PoiHolderView.this.mAdapter.getCurrentItem());
            bundle.putParcelableArray(PoiFragment.ARG_PROVIDERS, this.val$poiProviders);
            bundle.putInt(PoiFragment.ARG_DEFAULT, NearbyPoiCategoryFragment.getDefaultProviderIndex(NearbyPoiGroup.getDefaultProviderFromGroupType(this.val$groupId, PositionInfo.nativeGetLastValidPosition()), this.val$poiProviders));
            manager.addFragment(PoiFragment.class, this.val$title, true, PoiHolderView.this.mCallback, bundle);
        }
    }

    static {
        POI_IDS = new int[]{305, 302, 314, 301};
    }

    public PoiHolderView(Context context) {
        super(context);
        this.mShortcuts = new ArrayList(5);
    }

    public PoiHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mShortcuts = new ArrayList(5);
    }

    public PoiHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShortcuts = new ArrayList(5);
    }

    @TargetApi(21)
    public PoiHolderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mShortcuts = new ArrayList(5);
    }

    protected void onFinishInflate() {
        int i;
        super.onFinishInflate();
        for (i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt != null && (childAt instanceof ImageView)) {
                this.mShortcuts.add((ImageView) childAt);
            }
        }
        setMore();
        NearbyPoiGroup[] shortcuts = isInEditMode() ? new NearbyPoiGroup[]{NearbyPoiGroup.getDummyItem("Poi 1")} : PoiManager.nativeGetPoiGroupsByIds(POI_IDS);
        for (i = 0; i < shortcuts.length; i++) {
            NearbyPoiGroup item = shortcuts[i];
            setShortcut(i, item.getName(), item.getIconChar(), item.getType(), NearbyPoiGroup.providersFromGroupType(item.getType(), null), item.getStringCode());
        }
    }

    private void setMore() {
        FrameLayout moreView = (FrameLayout) findViewById(2131624475);
        Drawable drawable = ((ImageView) moreView.findViewById(2131624476)).getDrawable();
        if (drawable != null && (drawable instanceof FontDrawable)) {
            ((FontDrawable) drawable).setText(getContext().getString(2131166249));
        }
        moreView.setOnClickListener(new C15891());
    }

    public void setShortcut(int pos, String title, String icon, int groupId, PoiProvider[] poiProviders, String stringCode) {
        if (pos < this.mShortcuts.size()) {
            ImageView imageView = (ImageView) this.mShortcuts.get(pos);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null && (drawable instanceof FontDrawable)) {
                ((FontDrawable) drawable).setText(icon);
                imageView.setVisibility(0);
            }
            imageView.setOnClickListener(new C15902(stringCode, groupId, poiProviders, title));
        }
    }

    public void setData(RouteWaypointsAdapter waypointsAdapter, PoiFragmentResultCallback callback, OnMoreClickListener moreListener) {
        this.mAdapter = waypointsAdapter;
        this.mCallback = callback;
        this.mOnMoreClickedListener = moreListener;
    }
}
