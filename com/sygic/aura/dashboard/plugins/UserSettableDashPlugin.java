package com.sygic.aura.dashboard.plugins;

import android.content.Context;
import android.os.Bundle;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.favorites.fragment.FavoritesFragment;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.search.model.data.FavoritesItem;

public abstract class UserSettableDashPlugin extends NavigableDashPlugin {

    /* renamed from: com.sygic.aura.dashboard.plugins.UserSettableDashPlugin.1 */
    class C11751 implements FavoritesFragmentResultCallback {
        final /* synthetic */ DashboardFragment val$dashboardFragment;

        C11751(DashboardFragment dashboardFragment) {
            this.val$dashboardFragment = dashboardFragment;
        }

        public void onFavoritesFragmentResult(FavoritesItem result) {
            if (result != null && result.getLongPosition().isValid()) {
                UserSettableDashPlugin.this.fillPluginData(this.val$dashboardFragment.getActivity(), result);
                long memoId = UserSettableDashPlugin.this.addMemo(this.val$dashboardFragment.getActivity());
                if (memoId >= 0) {
                    UserSettableDashPlugin.this.mWidgetItem.setMemoId(memoId);
                } else {
                    UserSettableDashPlugin.this.clear();
                }
                UserSettableDashPlugin.this.persist();
                this.val$dashboardFragment.onFavoritesFragmentResult(result);
            }
        }
    }

    protected abstract long addMemo(Context context);

    protected abstract int addTitle();

    protected UserSettableDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public boolean isSet() {
        return this.mWidgetItem.getMemoId() != -1;
    }

    public void performAction(DashboardFragment dashboardFragment) {
        if (isSet()) {
            navigate(dashboardFragment);
        } else {
            showFavorites(dashboardFragment);
        }
    }

    public void showFavorites(DashboardFragment dashboardFragment) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("action_title_mode_id", addTitle());
            bundle.putParcelable("exclusion_filter", dashboardFragment.getSortedPluginNamesByType());
            manager.addFragment(FavoritesFragment.class, "fragment_favorites_tag", true, new C11751(dashboardFragment), bundle, 17432576, 17432577);
        }
    }

    public boolean memoRemoved(Context context) {
        clear();
        return false;
    }
}
