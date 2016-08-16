package com.sygic.aura.dashboard.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.feature.automotive.InCarConnectionListener;
import com.sygic.aura.views.font_specials.SToolbar;

public class DashboardFragmentInCar extends DashboardFragment implements InCarConnectionListener {

    /* renamed from: com.sygic.aura.dashboard.fragment.DashboardFragmentInCar.1 */
    class C11691 implements OnClickListener {
        C11691() {
        }

        public void onClick(View v) {
            DashboardFragmentInCar.this.performHomeAction();
            DashboardFragmentInCar.this.mNavigationDrawer.toggleDrawer();
        }
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle(2131165368);
            toolbar.setNavigationOnClickListener(new C11691());
        }
    }

    public void onConnectionChanged(boolean connected) {
        DashboardFragment df = new DashboardFragment();
        df.setArguments(getArguments());
        getFragmentManager().beginTransaction().replace(2131624521, df, "fragment_dashboard_tag").commit();
    }
}
