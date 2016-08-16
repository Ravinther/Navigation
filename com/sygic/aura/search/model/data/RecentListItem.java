package com.sygic.aura.search.model.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.plugins.RecentDashPlugin;
import com.sygic.aura.map.data.MemoItem.ENetItemType;
import com.sygic.aura.map.data.MemoItem.ESelType;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.PoiManager;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.time.TimeManager;
import java.util.Comparator;
import loquendo.tts.engine.TTSConst;

public class RecentListItem extends FavoritesItem {
    private static Comparator<? super ListItem> mComparator;
    private final long DAY;
    private final long NOW;
    private String mAddress;
    private final int mCreated;
    private final int mPoiCategory;
    private String mPoiCategoryIcon;
    private String mStrDate;

    /* renamed from: com.sygic.aura.search.model.data.RecentListItem.1 */
    static class C15791 implements Comparator<ListItem> {
        C15791() {
        }

        public int compare(ListItem lListItem, ListItem rListItem) {
            return ((RecentListItem) rListItem).getCreated() - ((RecentListItem) lListItem).getCreated();
        }
    }

    /* renamed from: com.sygic.aura.search.model.data.RecentListItem.2 */
    static /* synthetic */ class C15802 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType;
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType;

        static {
            $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType = new int[ENetItemType.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType[ENetItemType.typeFourSquare.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType[ENetItemType.typeYelp.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType[ENetItemType.typeTripAdvisor.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType[ENetItemType.typeViator.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType[ENetItemType.typeService.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType[ENetItemType.typeNone.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType = new int[ESelType.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selPoiTree.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selPoi.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selWork.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selHome.ordinal()] = 4;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selSearch.ordinal()] = 5;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selUnknown.ordinal()] = 6;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selCityGuide.ordinal()] = 7;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selRupi.ordinal()] = 8;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selRoad.ordinal()] = 9;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selVehicle.ordinal()] = 10;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selFlag.ordinal()] = 11;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selFriend.ordinal()] = 12;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selPhoto.ordinal()] = 13;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selMemo.ordinal()] = 14;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.selCityCenter.ordinal()] = 15;
            } catch (NoSuchFieldError e21) {
            }
        }
    }

    public RecentListItem(String strName, String strExtName, String strAddress, MapSelection mapSel, long memoId, int iconType, int poiCategory, int created, String strDate) {
        super(strName, strExtName, mapSel, memoId, iconType);
        this.mPoiCategoryIcon = null;
        this.DAY = 86400;
        this.NOW = TimeManager.nativeTimeGetCurrentTime();
        this.mAddress = strAddress;
        this.mCreated = created;
        this.mStrDate = strDate;
        this.mPoiCategory = poiCategory;
    }

    public String toString() {
        return getDisplayName();
    }

    public static Comparator<? super ListItem> getComparator() {
        if (mComparator == null) {
            mComparator = new C15791();
        }
        return mComparator;
    }

    public int getCreated() {
        return this.mCreated;
    }

    protected int getIconResId() {
        switch (C15802.$SwitchMap$com$sygic$aura$map$data$MemoItem$ESelType[ESelType.createFromInt(this.mIconType).ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                ListItem[] items;
                if (this.mPoiCategory == 0) {
                    items = null;
                } else {
                    items = PoiManager.nativeGetPoiCategoriesByIds(new int[]{this.mPoiCategory});
                }
                if (items != null && items.length > 0) {
                    this.mPoiCategoryIcon = ((PoiListItem) items[0]).getIcon();
                    break;
                }
            case TTSConst.TTSPARAGRAPH /*2*/:
                break;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131034188;
            case TTSConst.TTSXML /*4*/:
                return 2131034183;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return getSearchIconId(this.mPoiCategory);
            default:
                return 2131034184;
        }
        return 2131034283;
    }

    private int getSearchIconId(int itemType) {
        switch (C15802.$SwitchMap$com$sygic$aura$map$data$MemoItem$ENetItemType[ENetItemType.createFromInt(itemType).ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131034264;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131034267;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131034265;
            case TTSConst.TTSXML /*4*/:
                return 2131034266;
            default:
                return 0;
        }
    }

    public Drawable getIcon(Resources resources) {
        FontDrawable drawable = (FontDrawable) super.getIcon(resources);
        return TextUtils.isEmpty(this.mPoiCategoryIcon) ? drawable : new FontDrawable(drawable, this.mPoiCategoryIcon);
    }

    public DashboardPlugin createDashPlugin() {
        return RecentDashPlugin.newInstance(null);
    }

    public String getExtName() {
        if (TextUtils.isEmpty(super.getExtName())) {
            return this.mPoiCategoryIcon;
        }
        return super.getExtName();
    }

    public String getAddressSummary() {
        return this.mAddress;
    }

    public int getPoiCategory() {
        return this.mPoiCategory;
    }

    private long getTimeDiff() {
        return this.NOW - ((long) getCreated());
    }

    public String getDate(Context context) {
        long timeDiff = getTimeDiff();
        if (timeDiff >= 172800) {
            return this.mStrDate;
        }
        return ResourceManager.getCoreString(context, timeDiff < 86400 ? 2131165946 : 2131165947);
    }
}
