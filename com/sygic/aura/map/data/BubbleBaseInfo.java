package com.sygic.aura.map.data;

import com.sygic.aura.data.LongPosition;
import com.sygic.aura.data.ScreenPoint;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;

public class BubbleBaseInfo {
    private final long mId;
    private ScreenPoint mPoint;
    private final MapSelection mSel;

    public BubbleBaseInfo(int screenX, int screenY, long id, MapSelection sel) {
        this.mPoint = new ScreenPoint(screenX, screenY);
        this.mId = id;
        this.mSel = sel;
    }

    public int getKey() {
        return (this.mPoint.x << 16) | this.mPoint.y;
    }

    public int getX() {
        return this.mPoint.x;
    }

    public int getY() {
        return this.mPoint.y;
    }

    public LongPosition getLongPosition() {
        return this.mSel.getPosition();
    }

    public long getId() {
        return this.mId;
    }

    public MapSelection getSelection() {
        return this.mSel;
    }

    public void remapCoords() {
        ScreenPoint point = PositionInfo.nativeGeoToScreen(getLongPosition());
        if (point != null) {
            this.mPoint = point;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BubbleBaseInfo that = (BubbleBaseInfo) o;
        if (this.mId != that.mId) {
            return false;
        }
        if (this.mPoint == null ? that.mPoint != null : !this.mPoint.equals(that.mPoint)) {
            return false;
        }
        if (this.mSel != null) {
            if (this.mSel.equals(that.mSel)) {
                return true;
            }
        } else if (that.mSel == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int i2 = ((int) (this.mId ^ (this.mId >>> 32))) * 31;
        if (this.mPoint != null) {
            hashCode = this.mPoint.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.mSel != null) {
            i = this.mSel.hashCode();
        }
        return hashCode + i;
    }
}
