package com.sygic.aura.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.favorites.fragment.FavoritesFragment;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.FavoritesItem;
import java.util.ArrayList;
import java.util.List;

public class HistorySection extends LinearLayout {
    private ArrayAdapter<? extends FavoritesItem> mAdapter;
    private View mMoreView;
    final List<HistorySectionItem> mSections;

    /* renamed from: com.sygic.aura.search.view.HistorySection.1 */
    class C15871 implements OnClickListener {
        final /* synthetic */ FavoritesFragmentResultCallback val$callback;

        C15871(FavoritesFragmentResultCallback favoritesFragmentResultCallback) {
            this.val$callback = favoritesFragmentResultCallback;
        }

        public void onClick(View v) {
            FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
            if (manager != null) {
                SygicAnalyticsLogger.getAnalyticsEvent(HistorySection.this.getContext(), EventType.SEARCH).setName("search").setValue("click", "more_from_history").logAndRecycle();
                Bundle bundle = new Bundle(1);
                bundle.putBoolean("route_filter", true);
                bundle.putInt("page_index", 1);
                manager.addFragment(FavoritesFragment.class, "fragment_favorites_tag", true, this.val$callback, bundle);
            }
            NaviNativeActivity.hideKeyboard(v.getWindowToken());
        }
    }

    public HistorySection(Context context) {
        super(context);
        this.mSections = new ArrayList();
    }

    public HistorySection(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSections = new ArrayList();
    }

    public HistorySection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSections = new ArrayList();
    }

    @TargetApi(21)
    public HistorySection(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mSections = new ArrayList();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof HistorySectionItem) {
                this.mSections.add((HistorySectionItem) childAt);
            } else if (2131624473 == childAt.getId()) {
                this.mMoreView = childAt;
            }
        }
    }

    public void showSections(FavoritesFragmentResultCallback callback, int count) {
        if (this.mAdapter != null) {
            boolean z;
            if (count > 0) {
                z = true;
            } else {
                z = false;
            }
            ResourceManager.makeControlVisible(this, z, true);
            int i = 0;
            while (i < count && i < this.mSections.size()) {
                HistorySectionItem historySectionItem = (HistorySectionItem) this.mSections.get(i);
                historySectionItem.setData((FavoritesItem) this.mAdapter.getItem(i), callback);
                historySectionItem.setVisibility(0);
                i++;
            }
            if (count > this.mSections.size()) {
                this.mMoreView.setVisibility(0);
                this.mMoreView.setOnClickListener(new C15871(callback));
            }
        }
    }

    public void setAdapter(ArrayAdapter<? extends FavoritesItem> listAdapter) {
        this.mAdapter = listAdapter;
    }
}
