package com.sygic.aura.map.data;

import com.sygic.aura.map.data.map_selection.MapSelection;

public class BubbleInfo extends BubbleBaseInfo {
    private final String mStrLabel;

    public BubbleInfo(String strLabel, int screenX, int screenY, long id, MapSelection sel) {
        super(screenX, screenY, id, sel);
        this.mStrLabel = strLabel;
    }

    public String getLabel() {
        return this.mStrLabel;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BubbleInfo that = (BubbleInfo) o;
        if (!super.equals(o)) {
            return false;
        }
        if (this.mStrLabel != null) {
            if (this.mStrLabel.equals(that.mStrLabel)) {
                return true;
            }
        } else if (that.mStrLabel == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.mStrLabel != null ? this.mStrLabel.hashCode() : 0) * 31) + super.hashCode();
    }
}
