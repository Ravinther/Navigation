package com.sygic.aura.route.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.map.bubble.BubbleManager.OnBubbleShownListener;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.views.WndManager;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.aura.views.font_specials.SToolbar.OnInvalidatedMenuListener;

public class SelectFromMapFragment extends AbstractScreenFragment implements OnBubbleShownListener {

    /* renamed from: com.sygic.aura.route.fragment.SelectFromMapFragment.1 */
    class C15351 implements OnInvalidatedMenuListener {
        C15351() {
        }

        public void onMenuInvalidated(Menu menu) {
            SelectFromMapFragment.this.onPrepareOptionsMenu(menu);
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.SelectFromMapFragment.2 */
    class C15362 implements OnMenuItemClickListener {
        C15362() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return SelectFromMapFragment.this.onOptionsItemSelected(menuItem);
        }
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165930);
        toolbar.setOnMenuInvalidateListener(new C15351());
        toolbar.inflateMenu(2131755031);
        toolbar.setOnMenuItemClickListener(new C15362());
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNavigationData();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BubbleManager.getInstance().useSelectBubble(true);
        BubbleManager.getInstance().registerOnBubbleShownListener(this);
        WndManager.nativeClearMapSelection();
        MapOverlayFragment.setMode(getActivity(), Mode.SELECT_START_FROM_MAP);
        MapSelection mapSel = this.mRouteNavigateData.getWaypointMapSel(this.mRouteNavigateData.getWaypointsCount() - 1);
        if (mapSel == null) {
            performHomeAction();
        }
        MapControlsManager.nativeShowPin(mapSel);
        return inflater.inflate(2130903130, container, false);
    }

    public void onDestroyView() {
        super.onDestroyView();
        BubbleManager.getInstance().useSelectBubble(false);
        BubbleManager.getInstance().unregisterOnBubbleShownListener(this);
        MapControlsManager.nativeShowPin(MapSelection.Empty);
    }

    public void onDestroy() {
        super.onDestroy();
        RouteSummary.nativeCancelRoute();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(2131624696);
        if (item != null) {
            item.setEnabled(BubbleManager.getInstance().isVisible());
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624696) {
            return super.onOptionsItemSelected(item);
        }
        FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
        if (mngr == null) {
            return true;
        }
        this.mRouteNavigateData.changeStart(BubbleManager.getInstance().getSelection());
        mngr.replaceFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
        return true;
    }

    public void onBubbleShown(boolean shown) {
        if (this.mToolbar != null) {
            this.mToolbar.invalidateMenu();
        }
    }
}
