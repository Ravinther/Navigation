package com.sygic.aura.route.data;

import android.text.Html;
import android.text.Spanned;
import com.sygic.aura.resources.ResourceManager.TrafficType;

public class InstructionsItem {
    private int[] mArrIconChars;
    private boolean mAvoidable;
    private final int mIndex;
    private final ERouteInstructionType mItemType;
    private final String mStrFromStartDistance;
    private final Spanned mStrText;
    private final TrafficType mTrafficType;

    public enum ERouteInstructionType {
        TYPE_NONE(0),
        TYPE_JUNCTION(1),
        START_END(2),
        TYPE_START(3),
        TYPE_END(4),
        TYPE_SUMMARY(5),
        TYPE_AVOID(6),
        TYPE_TRAFFIC(7),
        TYPE_SINGLE_AVOID(8),
        TYPE_SINGLE_TRAFFIC(9),
        TYPE_SINGLE_DYNA_CHANGE(10),
        TYPE_STREET(11),
        TYPE_STOP(12),
        TYPE_SINGLE_TRAFFIC_USER(13),
        TYPE_BOUNDARY(14),
        TYPE_WAYPOINT(15);
        
        private final int id;

        private ERouteInstructionType(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }

        static ERouteInstructionType createFromInt(int value) {
            for (ERouteInstructionType eItem : values()) {
                if (eItem.getValue() == value) {
                    return eItem;
                }
            }
            return TYPE_NONE;
        }
    }

    public InstructionsItem(int index, String strText, String strDistance, int nDistance, int m_eType, int nTrafficType, int[] arrIconChars, boolean isAvoidable) {
        this.mIndex = index;
        this.mStrText = Html.fromHtml(strText);
        this.mStrFromStartDistance = strDistance;
        this.mArrIconChars = arrIconChars;
        this.mItemType = ERouteInstructionType.createFromInt(m_eType);
        this.mTrafficType = TrafficType.createFromInt(nTrafficType);
        this.mAvoidable = isAvoidable;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public ERouteInstructionType getType() {
        return this.mItemType;
    }

    public TrafficType getTrafficType() {
        return this.mTrafficType;
    }

    public int[] getIconChars() {
        return this.mArrIconChars;
    }

    public Spanned getText() {
        return this.mStrText;
    }

    public String getDistance() {
        return this.mStrFromStartDistance;
    }

    public boolean isAvoidable() {
        return this.mAvoidable;
    }
}
