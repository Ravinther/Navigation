package com.sygic.aura.search.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.GuiUtils;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.data.SearchBox;
import com.sygic.aura.search.model.GpsInputFragment;
import com.sygic.aura.search.model.data.QuickSearchItem.ItemType;
import com.sygic.aura.views.ManagedViewPager;
import com.sygic.aura.views.font_specials.SToolbar;

public class GpsCoordinatesFragment extends AbstractScreenFragment implements BackPressedListener {
    private ManagedViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;

    /* renamed from: com.sygic.aura.search.fragment.GpsCoordinatesFragment.1 */
    class C15591 implements OnMenuItemClickListener {
        C15591() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return GpsCoordinatesFragment.this.onOptionsItemSelected(menuItem);
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.GpsCoordinatesFragment.2 */
    class C15602 extends FragmentPagerAdapter {
        private final Fragment[] items;

        C15602(FragmentManager x0) {
            super(x0);
            this.items = new Fragment[]{GpsInputFragment.newInstance(0), GpsInputFragment.newInstance(1), GpsInputFragment.newInstance(2)};
        }

        public Fragment getItem(int pos) {
            return this.items[pos];
        }

        public int getCount() {
            return 3;
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.GpsCoordinatesFragment.3 */
    class C15613 extends SimpleOnPageChangeListener {
        final /* synthetic */ View val$view;

        C15613(View view) {
            this.val$view = view;
        }

        public void onPageSelected(int position) {
            NaviNativeActivity.hideKeyboard(this.val$view.getWindowToken());
        }
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165671);
        toolbar.inflateMenu(2131755013);
        toolbar.setOnMenuItemClickListener(new C15591());
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPagerAdapter = new C15602(getChildFragmentManager());
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903135, container, false);
        this.mPager = (ManagedViewPager) view.findViewById(2131624309);
        this.mPager.setAdapter(this.mPagerAdapter);
        this.mPager.setOffscreenPageLimit(3);
        this.mPager.addOnPageChangeListener(new C15613(view));
        GuiUtils.setupTabsWithViewPager((TabLayout) view.findViewById(2131624308), this.mPager, getActivity(), 2131165663, 2131165668, 2131165670);
        return view;
    }

    private long parseCoordinates(String strLat, String strLon) {
        strLat = strLat.replace('-', 'S').replace('+', 'N');
        strLon = strLon.replace('-', 'W').replace('+', 'E');
        if (strLat.indexOf(78) == -1 && strLat.indexOf(83) == -1) {
            strLat = 'N' + strLat;
        }
        if (strLon.indexOf(87) == -1 && strLon.indexOf(69) == -1) {
            strLon = 'E' + strLon;
        }
        return SearchBox.nativeGpsCoordsSearch(strLat, strLon);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2131624674) {
            return onActionDone();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onActionDone() {
        GpsInputFragment fragment = (GpsInputFragment) this.mPagerAdapter.getItem(this.mPager.getCurrentItem());
        MapSelection mapSel = new MapSelection(parseCoordinates(fragment.getLatitude(), fragment.getLongitude()), 0, 0);
        if (mapSel.getPosition().isValid()) {
            performHomeAction();
            if (this.mResultCallback != null) {
                ((QuickSearchFragmentResultCallback) this.mResultCallback).onQuickSearchFragmentResult(mapSel);
                this.mResultCallback = null;
            }
            return true;
        }
        SToast.makeText(getActivity(), 2131165667, 1).show();
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mLocationQuery.setQuickSearchItem(ItemType.ITEM_NONE, 0);
        if (this.mResultCallback != null) {
            ((QuickSearchFragmentResultCallback) this.mResultCallback).onQuickSearchFragmentResult((MapSelection) null);
        }
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
    }

    public boolean onBackPressed() {
        if (this.mPager.getCurrentItem() != 1) {
            return false;
        }
        this.mPager.setCurrentItem(0);
        return true;
    }
}
