package com.sygic.aura.navigate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.interfaces.ActionControlFragmentListener;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;

public class ActionControlFragment extends AbstractScreenFragment implements ActionControlFragmentListener, BackPressedListener {
    private ActionControlDelegate mDelegate;

    public enum Mode implements Parcelable {
        NEARBY_POI,
        POR,
        PARKING;
        
        public static final Creator<Mode> CREATOR;

        /* renamed from: com.sygic.aura.navigate.ActionControlFragment.Mode.1 */
        static class C13771 implements Creator<Mode> {
            C13771() {
            }

            public Mode createFromParcel(Parcel source) {
                return Mode.values()[source.readInt()];
            }

            public Mode[] newArray(int size) {
                return new Mode[size];
            }
        }

        static {
            CREATOR = new C13771();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNavigationData();
        Bundle args = getArguments();
        Mode mode = (Mode) getArguments().getParcelable("mode");
        if (mode == null) {
            mode = Mode.PARKING;
        }
        this.mDelegate = ActionControlDelegate.from(this, mode);
        this.mDelegate.onCreate(args);
        MapEventsReceiver.registerActionControlFragmentListener(this);
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        MapOverlayFragment.setMode(getActivity(), com.sygic.aura.map.fragment.MapOverlayFragment.Mode.ACTION_CONTROL);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(this.mDelegate.getLayoutRes(), container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mDelegate.onViewCreated(view);
    }

    public void onDestroy() {
        super.onDestroy();
        MapEventsReceiver.unregisterActionControlFragmentListener(this);
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
    }

    public boolean onBackPressed() {
        return this.mDelegate.cancel();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDelegate.onConfigurationChanged();
    }

    public void onPoiSelectionChanged(MapSelection mapSel, String title, String icon) {
        this.mDelegate.onPoiSelectionChanged(mapSel, title, icon);
    }
}
