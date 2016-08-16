package com.sygic.aura.dashboard;

import com.sygic.aura.SygicProject;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.plugins.AddNewDashPlugin;
import com.sygic.aura.dashboard.plugins.BlackBoxDashPlugin;
import com.sygic.aura.dashboard.plugins.ContactDashPlugin;
import com.sygic.aura.dashboard.plugins.FavoriteDashPlugin;
import com.sygic.aura.dashboard.plugins.HomeDashPlugin;
import com.sygic.aura.dashboard.plugins.HudDashPlugin;
import com.sygic.aura.dashboard.plugins.NavigateToPhotoDashPlugin;
import com.sygic.aura.dashboard.plugins.ParkingPlugin;
import com.sygic.aura.dashboard.plugins.RecentDashPlugin;
import com.sygic.aura.dashboard.plugins.RouteDashPlugin;
import com.sygic.aura.dashboard.plugins.SosDashPlugin;
import com.sygic.aura.dashboard.plugins.TravelbookDashPlugin;
import com.sygic.aura.dashboard.plugins.WorkDashPlugin;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class WidgetManager {
    static final /* synthetic */ boolean $assertionsDisabled;

    /* renamed from: com.sygic.aura.dashboard.WidgetManager.1 */
    static class C11571 implements Callback<Boolean> {
        final /* synthetic */ WidgetItem val$item;

        C11571(WidgetItem widgetItem) {
            this.val$item = widgetItem;
        }

        public Boolean getMethod() {
            int ret = WidgetManager.AddWidgetItem(this.val$item.getName(), this.val$item.getType().getValue(), this.val$item.getSize().getValue(), this.val$item.getPositionX(), this.val$item.getPositionY(), this.val$item.getMemoId());
            if (ret <= 0) {
                return Boolean.valueOf(false);
            }
            this.val$item.setId(ret);
            return Boolean.valueOf(true);
        }
    }

    /* renamed from: com.sygic.aura.dashboard.WidgetManager.2 */
    static class C11582 implements Callback<Boolean> {
        final /* synthetic */ WidgetItem val$item;

        C11582(WidgetItem widgetItem) {
            this.val$item = widgetItem;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(WidgetManager.UpdateWidget(this.val$item.getItemId(), this.val$item.getName(), this.val$item.getType().getValue(), this.val$item.getSize().getValue(), this.val$item.getPositionX(), this.val$item.getPositionY(), this.val$item.getMemoId()));
        }
    }

    /* renamed from: com.sygic.aura.dashboard.WidgetManager.3 */
    static class C11593 implements Callback<WidgetItem[]> {
        C11593() {
        }

        public WidgetItem[] getMethod() {
            return WidgetManager.GetWidgets();
        }
    }

    /* renamed from: com.sygic.aura.dashboard.WidgetManager.4 */
    static class C11604 implements VoidCallback {
        final /* synthetic */ int val$widgetItemId;

        C11604(int i) {
            this.val$widgetItemId = i;
        }

        public void getMethod() {
            WidgetManager.RemoveWidget(this.val$widgetItemId);
        }
    }

    /* renamed from: com.sygic.aura.dashboard.WidgetManager.5 */
    static class C11615 implements Callback<Boolean> {
        C11615() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(WidgetManager.IsHudAllowed());
        }
    }

    /* renamed from: com.sygic.aura.dashboard.WidgetManager.6 */
    static class C11626 implements Callback<Boolean> {
        C11626() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(WidgetManager.IsBlackBoxAllowed());
        }
    }

    /* renamed from: com.sygic.aura.dashboard.WidgetManager.7 */
    static /* synthetic */ class C11637 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType;

        static {
            $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType = new int[EWidgetType.values().length];
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeHome.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeWork.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeContact.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeRecent.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeFavorite.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeFavoriteRoute.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeHUD.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeBlackBox.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeTravelBook.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeSOS.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeNavigateToPhoto.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeParking.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeNone.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    private static native int AddWidgetItem(String str, int i, int i2, int i3, int i4, long j);

    private static native String GetRouteItineraryPath(long j);

    private static native WidgetItem[] GetWidgets();

    private static native boolean IsBlackBoxAllowed();

    private static native boolean IsHudAllowed();

    private static native void RemoveWidget(int i);

    private static native boolean UpdateWidget(int i, String str, int i2, int i3, int i4, int i5, long j);

    static {
        $assertionsDisabled = !WidgetManager.class.desiredAssertionStatus();
    }

    public static boolean nativeAddWidgetItem(WidgetItem item) {
        if (!$assertionsDisabled && (item.getItemId() != 0 || item.getMemoId() == -1)) {
            throw new AssertionError();
        } else if (SygicProject.IS_PROTOTYPE) {
            return true;
        } else {
            return ((Boolean) new ObjectHandler(new C11571(item)).execute().get(Boolean.valueOf(false))).booleanValue();
        }
    }

    public static boolean nativeUpdateWidget(WidgetItem item) {
        if (!$assertionsDisabled && item.getItemId() == 0) {
            throw new AssertionError();
        } else if (SygicProject.IS_PROTOTYPE) {
            return true;
        } else {
            return ((Boolean) new ObjectHandler(new C11582(item)).execute().get(Boolean.valueOf(false))).booleanValue();
        }
    }

    public static WidgetItem[] nativeGetWidgets() {
        if (!SygicProject.IS_PROTOTYPE) {
            return (WidgetItem[]) new ObjectHandler(new C11593()).execute().get(new WidgetItem[0]);
        }
        return new WidgetItem[]{new WidgetItem()};
    }

    public static void nativeRemoveWidget(int widgetItemId) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C11604(widgetItemId));
        }
    }

    public static boolean nativeIsHudAllowed() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C11615()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsBlackBoxAllowed() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C11626()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void getDashPlugins(List<DashboardPlugin> plugins) {
        plugins.clear();
        List<WidgetItem> widgetItemList = Arrays.asList(nativeGetWidgets());
        Collections.sort(widgetItemList);
        for (WidgetItem wiItem : widgetItemList) {
            switch (C11637.$SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[wiItem.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    plugins.add(HomeDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    plugins.add(WorkDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    plugins.add(ContactDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSXML /*4*/:
                    plugins.add(RecentDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    plugins.add(FavoriteDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    plugins.add(RouteDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    plugins.add(HudDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    plugins.add(BlackBoxDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    plugins.add(TravelbookDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    plugins.add(SosDashPlugin.newInstance(wiItem));
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    if (!InCarConnection.isInCarConnected()) {
                        plugins.add(NavigateToPhotoDashPlugin.newInstance(wiItem));
                        break;
                    }
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    plugins.add(ParkingPlugin.newInstance(wiItem));
                    break;
                default:
                    break;
            }
        }
        plugins.add(AddNewDashPlugin.newInstance(null));
    }

    public static String nativeGetRouteItineraryPath(long memoId) {
        return GetRouteItineraryPath(memoId);
    }
}
