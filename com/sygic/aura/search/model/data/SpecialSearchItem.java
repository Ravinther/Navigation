package com.sygic.aura.search.model.data;

import android.view.View;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.holder.ViewHolder;
import com.sygic.aura.search.model.holder.ViewHolderSpecial;

public class SpecialSearchItem extends SearchItem {
    private final ItemType mItemType;

    public enum ItemType {
        ITEM_NONE,
        ITEM_NEARBY_POI_STREET,
        ITEM_NEARBY_POI_CROSSING,
        ITEM_QUICK_NOTHING,
        ITEM_GPS_COORDS,
        ITEM_HOUSE_NUMBER
    }

    public SpecialSearchItem(ItemType itemType) {
        super("", "");
        this.mItemType = itemType;
    }

    public SearchType getSearchType() {
        return SearchType.SPECIAL;
    }

    public ViewHolder newViewHolderInstance(View view) {
        return new ViewHolderSpecial(view);
    }

    public ItemType getSpecialItemType() {
        return this.mItemType;
    }

    public String toString() {
        String displayName = getDisplayName();
        StringBuilder sb = new StringBuilder();
        if (displayName != null) {
            sb.append(displayName);
            sb.append(", ");
        }
        sb.append(getExtName());
        return sb.toString();
    }

    public static boolean isSpecial(SearchItem item) {
        return item != null && SearchType.SPECIAL.equals(item.getSearchType());
    }

    public static boolean isSpecialType(SearchItem item, ItemType requiredType) {
        return isSpecial(item) && requiredType != null && requiredType.equals(((SpecialSearchItem) item).getSpecialItemType());
    }
}
