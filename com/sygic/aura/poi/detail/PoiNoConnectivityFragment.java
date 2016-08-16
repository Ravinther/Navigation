package com.sygic.aura.poi.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.EventReceivers.NetworkEventsReceiver;
import com.sygic.aura.helper.interfaces.ConnectionChangeListener;
import com.sygic.aura.views.font_specials.SToolbar;

public class PoiNoConnectivityFragment extends AbstractScreenFragment implements ConnectionChangeListener {

    /* renamed from: com.sygic.aura.poi.detail.PoiNoConnectivityFragment.1 */
    class C14521 implements OnClickListener {
        C14521() {
        }

        public void onClick(View v) {
            PoiNoConnectivityFragment.this.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
            PoiNoConnectivityFragment.this.performHomeAction();
        }
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165575);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkEventsReceiver.registerConnectionChangeListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903120, container, false);
        ((Button) view.findViewById(2131624273)).setOnClickListener(new C14521());
        return view;
    }

    public void onDestroy() {
        super.onDestroy();
        NetworkEventsReceiver.unregisterConnectionChangeListener(this);
    }

    public void onConnectionChanged(Boolean bConnection) {
        if (bConnection.booleanValue()) {
            performHomeAction();
        }
    }
}
