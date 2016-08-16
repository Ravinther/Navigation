package com.sygic.aura.search.model.data;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.plugins.FavoriteDashPlugin;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.PoiManager;
import com.sygic.aura.resources.FontDrawable;

public class BookmarksListItem extends FavoritesItem {
    private final int mPoiCategory;
    private String mPoiCategoryIcon;

    public BookmarksListItem(String strName, String strExtName, MapSelection mapSel, long memoId, int iconType, int poiCategory) {
        super(strName, strExtName, mapSel, memoId, iconType);
        this.mPoiCategoryIcon = null;
        this.mPoiCategory = poiCategory;
    }

    public String toString() {
        return getDisplayName();
    }

    protected int getIconResId() {
        ListItem[] items;
        if (this.mPoiCategory == 0) {
            items = null;
        } else {
            items = PoiManager.nativeGetPoiCategoriesByIds(new int[]{this.mPoiCategory});
        }
        if (items != null && items.length > 0) {
            this.mPoiCategoryIcon = ((PoiListItem) items[0]).getIcon();
        }
        return 2131034178;
    }

    public Drawable getIcon(Resources resources) {
        FontDrawable drawable = (FontDrawable) super.getIcon(resources);
        return TextUtils.isEmpty(this.mPoiCategoryIcon) ? drawable : new FontDrawable(drawable, this.mPoiCategoryIcon);
    }

    public String getExtName() {
        if (TextUtils.isEmpty(super.getExtName())) {
            return this.mPoiCategoryIcon;
        }
        return super.getExtName();
    }

    public DashboardPlugin createDashPlugin() {
        return FavoriteDashPlugin.newInstance(null);
    }
}
