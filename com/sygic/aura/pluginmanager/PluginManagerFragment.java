package com.sygic.aura.pluginmanager;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.sygic.aura.favorites.fragment.FavoritesFragment;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.pluginmanager.plugin.PluginWrapper;
import com.sygic.aura.pluginmanager.plugin.shortcut.ShortcutPlugin.ShortcutPluginCallback;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.views.font_specials.SToolbar;

public class PluginManagerFragment extends AbstractScreenFragment implements ShortcutPluginCallback {
    private PluginsAdapter mAdapter;
    private final FavoritesFragmentResultCallback mFavoritesCallback;
    private final OnItemClickListener mItemClickListener;

    public interface PluginManagerCallback extends FragmentResultCallback {
        void onPluginsChanged();

        void onShorcutAdded(FavoritesItem favoritesItem);
    }

    /* renamed from: com.sygic.aura.pluginmanager.PluginManagerFragment.1 */
    class C14171 implements OnItemClickListener {
        C14171() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ((PluginWrapper) PluginManagerFragment.this.mAdapter.getItem(position)).getPlugin().handleClick(PluginManagerFragment.this.getActivity());
        }
    }

    /* renamed from: com.sygic.aura.pluginmanager.PluginManagerFragment.2 */
    class C14182 implements FavoritesFragmentResultCallback {
        C14182() {
        }

        public void onFavoritesFragmentResult(FavoritesItem result) {
            if (result != null) {
                PluginManagerFragment.this.getCallback().onShorcutAdded(result);
                PluginManagerFragment.this.performHomeAction();
            }
        }
    }

    public PluginManagerFragment() {
        this.mItemClickListener = new C14171();
        this.mFavoritesCallback = new C14182();
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165538);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(2130903118, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mAdapter = new PluginsAdapter(getActivity(), PluginManager.getPluginWrappers(getResources(), this));
        ListView listView = (ListView) view.findViewById(2131624221);
        listView.setAdapter(this.mAdapter);
        listView.setOnItemClickListener(this.mItemClickListener);
    }

    public PluginManagerCallback getCallback() {
        return (PluginManagerCallback) this.mResultCallback;
    }

    public void onPluginChanged() {
        this.mAdapter.notifyDataSetChanged();
        getCallback().onPluginsChanged();
    }

    public void onGoToFavourites(int pageIndex) {
        Parcelable filter = getArguments().getParcelable("exclusion_filter");
        Bundle bundle = new Bundle();
        bundle.putInt("page_index", pageIndex);
        bundle.putParcelable("exclusion_filter", filter);
        bundle.putInt("action_title_mode_id", 2131165311);
        SygicHelper.getFragmentActivityWrapper().addFragment(FavoritesFragment.class, "fragment_favorites_tag", true, this.mFavoritesCallback, bundle);
    }
}
