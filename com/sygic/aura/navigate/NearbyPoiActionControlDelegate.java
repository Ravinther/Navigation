package com.sygic.aura.navigate;

import android.os.Bundle;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.views.font_specials.SToolbar;

public class NearbyPoiActionControlDelegate extends ActionControlDelegate {
    private String mTitle;

    /* renamed from: com.sygic.aura.navigate.NearbyPoiActionControlDelegate.1 */
    class C13841 implements OnClickListener {
        C13841() {
        }

        public void onClick(View v) {
            NearbyPoiActionControlDelegate.this.mFragment.performHomeAction();
        }
    }

    /* renamed from: com.sygic.aura.navigate.NearbyPoiActionControlDelegate.2 */
    class C13852 implements OnMenuItemClickListener {
        C13852() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return onOptionsItemSelected(item);
        }

        private boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() != 2131624685) {
                return false;
            }
            NearbyPoiActionControlDelegate.this.mFragment.performHomeAction();
            return true;
        }
    }

    protected NearbyPoiActionControlDelegate(ActionControlFragment fragment) {
        super(fragment);
    }

    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        this.mTitle = arguments.getString(AbstractFragment.ARG_TITLE);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        if (toolbar != null) {
            toolbar.setNavigationIconAsUp();
            toolbar.setNavigationOnClickListener(new C13841());
            toolbar.setTitle(this.mTitle);
            toolbar.inflateMenu(2131755022);
            toolbar.setOnMenuItemClickListener(new C13852());
        }
    }

    public boolean cancel() {
        return false;
    }

    public void onViewCreated(View view) {
        onSetupToolbar((SToolbar) view.findViewById(2131624226));
    }

    public void onPoiSelectionChanged(MapSelection mapSel, String title, String icon) {
    }

    public int getLayoutRes() {
        return 2130903070;
    }
}
