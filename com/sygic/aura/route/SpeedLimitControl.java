package com.sygic.aura.route;

import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.sygic.aura.map.data.SpeedInfoItem;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.view.SpeedLimitSignShape;

public class SpeedLimitControl {
    private SpeedInfoItem mInfo;
    private final ViewSwitcher mSwitcher;

    public SpeedLimitControl(ViewSwitcher switcher) {
        this.mSwitcher = switcher;
    }

    public void updateSpeedLimit(SpeedInfoItem info) {
        int i = 0;
        if (!info.equals(this.mInfo)) {
            if (info.getSpeed() < 1 || info.getSpeed() > 180) {
                this.mSwitcher.setVisibility(8);
                return;
            }
            if (info.isAmerica()) {
                if (this.mSwitcher.getDisplayedChild() == 0) {
                    this.mSwitcher.showNext();
                }
            } else if (this.mSwitcher.getDisplayedChild() == 1) {
                this.mSwitcher.showPrevious();
            }
            this.mSwitcher.setVisibility(0);
            SpeedLimitSignShape view = (SpeedLimitSignShape) this.mSwitcher.getCurrentView();
            if (view != null) {
                view.getValue().setText(Integer.toString((int) ResourceManager.nativeFormatSpeed((double) info.getSpeed(), true)));
                TextView warn = view.getWarn();
                if (warn != null) {
                    if (!info.isExclamation()) {
                        i = 8;
                    }
                    warn.setVisibility(i);
                }
            }
            this.mInfo = info;
        }
    }

    public View getView() {
        return this.mSwitcher;
    }
}
