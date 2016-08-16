package com.sygic.aura.helper;

import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.route.RouteManager.RouteComputeMode;
import com.sygic.aura.views.font_specials.SToggleButton;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SwitchViewHelper<T extends SToggleButton> implements OnClickListener {
    private View mChecked;
    private final SwitchViewListener mSwitchViewListener;
    private final Map<RouteComputeMode, T> mViewMap;

    public interface SwitchViewListener {
        void onSelectionChanged(RouteComputeMode routeComputeMode);
    }

    public SwitchViewHelper(SwitchViewListener switchViewListener) {
        this.mSwitchViewListener = switchViewListener;
        this.mViewMap = new HashMap();
    }

    public void add(RouteComputeMode mode, T view) {
        synchronized (this.mViewMap) {
            this.mViewMap.put(mode, view);
            view.setOnClickListener(this);
        }
    }

    public void setSelected(RouteComputeMode mode) {
        synchronized (this.mViewMap) {
            for (RouteComputeMode key : this.mViewMap.keySet()) {
                if (key == mode && !((SToggleButton) this.mViewMap.get(key)).equals(this.mChecked)) {
                    this.mSwitchViewListener.onSelectionChanged(key);
                }
                setItemChecked((SToggleButton) this.mViewMap.get(key), key == mode);
            }
        }
    }

    public void setEnabled(boolean enabled) {
        synchronized (this.mViewMap) {
            for (Entry<RouteComputeMode, T> entry : this.mViewMap.entrySet()) {
                ((View) entry.getValue()).setEnabled(enabled);
            }
        }
    }

    public void onClick(View v) {
        synchronized (this.mViewMap) {
            for (Entry<RouteComputeMode, T> entry : this.mViewMap.entrySet()) {
                SToggleButton view = (SToggleButton) entry.getValue();
                boolean clicked = view.equals(v);
                if (clicked && !view.equals(this.mChecked)) {
                    this.mSwitchViewListener.onSelectionChanged((RouteComputeMode) entry.getKey());
                }
                setItemChecked(view, clicked);
            }
        }
    }

    private void setItemChecked(T item, boolean isChecked) {
        item.setChecked(isChecked);
        if (isChecked) {
            this.mChecked = item;
        }
    }
}
