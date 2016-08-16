package com.sygic.aura.search.model.data;

import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.OnlinePoiInfoEntry.EItemType;
import com.sygic.aura.poi.PoiDetailsInfo;
import java.util.Comparator;

public class PoiListItem extends ListItem implements Comparable<PoiListItem> {
    public static final int ITEM_SPECIAL_NEARBY_POI = -1;
    private static Comparator<? super ListItem> mComparator;
    private int mColor;
    private final int mDistance;
    private String mFormattedDistance;
    private final int mGroupId;
    private final long mPoiId;
    private final String mStrIconFile;

    /* renamed from: com.sygic.aura.search.model.data.PoiListItem.1 */
    static class C15781 implements Comparator<ListItem> {
        C15781() {
        }

        public int compare(ListItem lListItem, ListItem rListItem) {
            return ((PoiListItem) lListItem).compareTo((PoiListItem) rListItem);
        }
    }

    public static PoiListItem getSpecialItemInstance(String label) {
        return new PoiListItem(label, ITEM_SPECIAL_NEARBY_POI);
    }

    protected PoiListItem(String strName, int groupId) {
        super(strName);
        this.mColor = SygicHelper.getActivity().getResources().getColor(2131558648);
        this.mDistance = 0;
        this.mPoiId = 0;
        this.mGroupId = groupId;
        this.mStrIconFile = "";
    }

    public PoiListItem(String strName, String strExtName, MapSelection mapSel, long id, int distance, int groupId, int poiIcon, int poiColor, String strIconFile, String formattedDistance) {
        super(strName, strExtName, mapSel, poiIcon);
        this.mPoiId = id;
        this.mDistance = distance;
        this.mFormattedDistance = formattedDistance;
        this.mColor = poiColor;
        this.mStrIconFile = strIconFile;
        this.mGroupId = groupId;
        if (this.mColor == 0) {
            this.mColor = SygicHelper.getActivity().getResources().getColor(2131558648);
        }
    }

    public MapSelection getMapSel() {
        if (this.mMapSel == null) {
            this.mMapSel = getPoiMapSel(this.mPoiId);
        }
        return super.getMapSel();
    }

    public boolean isGroup() {
        return this.mGroupId >= 0;
    }

    public int getPoiDistance() {
        return this.mDistance;
    }

    public String getIcon() {
        return this.mIconType == 0 ? null : Character.toString((char) this.mIconType);
    }

    public int compareTo(PoiListItem another) {
        int distanceDiff = getPoiDistance() - another.getPoiDistance();
        if (distanceDiff == 0) {
            return equals(another) ? 0 : 1;
        } else {
            return distanceDiff;
        }
    }

    public int getColor() {
        return this.mColor;
    }

    public String getIconFile() {
        return this.mStrIconFile;
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    public long getPoiId() {
        return this.mPoiId;
    }

    public String getFormattedDistance() {
        return this.mFormattedDistance;
    }

    protected MapSelection getPoiMapSel(long poiId) {
        return PoiDetailsInfo.nativeGetPoiSelection(poiId);
    }

    public static Comparator<? super ListItem> getComparator() {
        if (mComparator == null) {
            mComparator = new C15781();
        }
        return mComparator;
    }

    public EItemType getProviderType() {
        return EItemType.None;
    }
}
