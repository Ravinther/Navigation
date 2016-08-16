package com.sygic.aura.dashboard;

import android.content.Context;
import android.content.res.Resources;
import com.sygic.aura.dashboard.DashboardPluginAdapter.PluginViewHolder;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.ParcelableStringSparseArray;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.model.data.ContactListItem;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.ListItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public abstract class DashboardPlugin implements DashboardAction {
    protected final List<ListItem> mAddressSegments;
    protected final WidgetItem mWidgetItem;

    /* renamed from: com.sygic.aura.dashboard.DashboardPlugin.1 */
    static /* synthetic */ class C11521 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetSize;

        static {
            $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetSize = new int[EWidgetSize.values().length];
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetSize[EWidgetSize.widgetSizeHalfRow.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetSize[EWidgetSize.widgetSizeOneRow.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetSize[EWidgetSize.widgetSizeDoubleRow.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    protected abstract int getPluginImage();

    public abstract String getPluginLabel(Resources resources);

    protected DashboardPlugin(WidgetItem widgetItem) {
        this.mAddressSegments = new ArrayList();
        this.mWidgetItem = widgetItem;
        updateAddressSegments();
    }

    public void fillPluginData(MemoItem memoItem) {
        clear();
        setAddressSegments(MemoManager.nativeGetMapSel((long) ((int) memoItem.getId())));
        this.mWidgetItem.setMemoId((long) ((int) memoItem.getId()));
        this.mWidgetItem.setName(memoItem.getStrData());
    }

    public void fillPluginData(Context context, FavoritesItem selectedItem) {
        setAddressSegments((ListItem) selectedItem);
        this.mWidgetItem.setName(selectedItem.getDisplayName());
        this.mWidgetItem.setExtName(selectedItem.getExtName());
        updateWidgetItem(context, selectedItem);
    }

    protected void setAddressSegments(ListItem selectedItem) {
        setAddressSegments(selectedItem.getMapSel());
    }

    protected void setAddressSegments(MapSelection mapSel) {
        this.mAddressSegments.clear();
        ListItem[] addressSegments = PositionInfo.nativeGetPositionSearchEntries(mapSel);
        if (addressSegments != null) {
            Collections.addAll(this.mAddressSegments, addressSegments);
        }
    }

    protected void updateAddressSegments() {
        this.mAddressSegments.clear();
        ListItem[] addressSegments = PositionInfo.nativeGetPositionSearchEntries(MemoManager.nativeGetMapSel(this.mWidgetItem));
        if (addressSegments != null) {
            Collections.addAll(this.mAddressSegments, addressSegments);
        }
    }

    public void setWidgetRowAndColumn(int row, int column) {
        this.mWidgetItem.setPositionX(column);
        this.mWidgetItem.setPositionY(row);
    }

    public int getWidgetId() {
        return this.mWidgetItem.getItemId();
    }

    public int getPluginSpans() {
        switch (C11521.$SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetSize[this.mWidgetItem.getSize().ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 1;
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
                return 2;
            default:
                return 0;
        }
    }

    public boolean isEditable() {
        return true;
    }

    public void clear() {
        this.mWidgetItem.setMemoId(-1);
        this.mWidgetItem.setName("");
        this.mWidgetItem.setExtName("");
        this.mAddressSegments.clear();
    }

    protected LongPosition getLongPosition() {
        ListItem listItem = getLastAddressSegment();
        return listItem == null ? LongPosition.Invalid : listItem.getLongPosition();
    }

    private int getLastAddressSegmentIndex() {
        return Math.min(this.mAddressSegments.size() - 1, 4);
    }

    private ListItem getLastAddressSegment() {
        int index = getLastAddressSegmentIndex();
        if (index == -1) {
            return null;
        }
        return (ListItem) this.mAddressSegments.get(index);
    }

    public List<ListItem> getAddressSegments() {
        return this.mAddressSegments;
    }

    protected void updateWidgetItem(Context context, FavoritesItem listItem) {
        if (listItem == null) {
            clear();
            return;
        }
        if (listItem instanceof ContactListItem) {
            this.mWidgetItem.setMemoId(MemoManager.nativeAddFavorite(context, listItem.getLongPosition(), listItem.getDisplayName()));
        }
        if (this.mWidgetItem.getMemoId() == -1) {
            this.mWidgetItem.setMemoId((long) ((int) listItem.getMemoId()));
        }
    }

    public void updateView(Resources resources, PluginViewHolder pluginViewHolder) {
        pluginViewHolder.image.setFontDrawableResource(getPluginImage());
        pluginViewHolder.label.setText(getPluginLabel(resources));
        pluginViewHolder.showPluginAsLocked(isLocked());
    }

    public void performAction(DashboardFragment dashboardFragment) {
    }

    public boolean persist() {
        if (this.mWidgetItem.getItemId() == 0) {
            return WidgetManager.nativeAddWidgetItem(this.mWidgetItem);
        }
        return WidgetManager.nativeUpdateWidget(this.mWidgetItem);
    }

    public boolean delete(Context context) {
        WidgetManager.nativeRemoveWidget(this.mWidgetItem.getItemId());
        if (this.mWidgetItem.getType() == EWidgetType.widgetTypeContact) {
            MemoManager.nativeRemoveItem(context, this.mWidgetItem.getMemoId());
        }
        return true;
    }

    public void addToSparseArray(Resources resources, ParcelableStringSparseArray pluginNamesSparseArray) {
    }

    public boolean memoRemoved(Context context) {
        return delete(context);
    }

    protected boolean isLocked() {
        return false;
    }
}
