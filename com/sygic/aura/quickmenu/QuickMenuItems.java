package com.sygic.aura.quickmenu;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.route.DemoManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import loquendo.tts.engine.TTSConst;

public class QuickMenuItems {

    /* renamed from: com.sygic.aura.quickmenu.QuickMenuItems.1 */
    static class C14671 implements OnClickListener {
        final /* synthetic */ QuickMenuView val$quickMenu;
        final /* synthetic */ QuickMenuItemType val$type;

        C14671(QuickMenuView quickMenuView, QuickMenuItemType quickMenuItemType) {
            this.val$quickMenu = quickMenuView;
            this.val$type = quickMenuItemType;
        }

        public void onClick(View v) {
            this.val$quickMenu.onItemClick(v, this.val$type);
        }
    }

    /* renamed from: com.sygic.aura.quickmenu.QuickMenuItems.2 */
    static /* synthetic */ class C14682 {
        static final /* synthetic */ int[] f1267x2185c976;

        static {
            f1267x2185c976 = new int[QuickMenuItemType.values().length];
            try {
                f1267x2185c976[QuickMenuItemType.START_DEMO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1267x2185c976[QuickMenuItemType.POI_SHOW_NEARBY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1267x2185c976[QuickMenuItemType.CANCEL_ROUTE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1267x2185c976[QuickMenuItemType.PEDESTRIAN_BUTTON.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1267x2185c976[QuickMenuItemType.MUTE_BUTTON.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1267x2185c976[QuickMenuItemType.SKIP_WAYPOINT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1267x2185c976[QuickMenuItemType.EMPTY_ITEM.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public enum QuickMenuItemType {
        EMPTY_ITEM,
        START_DEMO,
        SKIP_WAYPOINT,
        POI_REST_AREA,
        POI_PETROL_STATION,
        POI_SHOW_NEARBY,
        CANCEL_ROUTE,
        MUTE_BUTTON,
        PEDESTRIAN_BUTTON
    }

    public static View createView(QuickMenuView quickMenu, LayoutInflater inflater, QuickMenuItemType type) {
        if (type == QuickMenuItemType.EMPTY_ITEM) {
            return new View(quickMenu.getContext());
        }
        View itemView = inflater.inflate(2130903264, quickMenu, false);
        updateView(itemView, type);
        itemView.setOnClickListener(new C14671(quickMenu, type));
        return itemView;
    }

    public static View findViewByType(ViewGroup layout, QuickMenuItemType type) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child != null && type.equals(child.getTag(2131624428))) {
                return child;
            }
        }
        return null;
    }

    public static void updateView(View view, QuickMenuItemType type) {
        view.setTag(2131624428, type);
        if (type.equals(QuickMenuItemType.POI_REST_AREA) || type.equals(QuickMenuItemType.POI_PETROL_STATION)) {
            view.setEnabled(false);
        }
        Resources res = view.getResources();
        boolean isEnabled = view.isEnabled();
        SImageView imageView = (SImageView) view.findViewById(2131624565);
        int icon = getIconResId(type);
        if (icon != 0) {
            FontDrawable drawable = (FontDrawable) FontDrawable.inflate(res, icon);
            drawable.setColor(isEnabled ? res.getColor(2131558666) : res.getColor(2131558667));
            imageView.setImageDrawable(drawable);
        }
        STextView titleTextView = (STextView) view.findViewById(2131624566);
        int title = getTitleResId(type);
        if (title != 0) {
            titleTextView.setTextColor(isEnabled ? res.getColor(2131558668) : res.getColor(2131558669));
            titleTextView.setCoreText(title);
        }
    }

    public static QuickMenuItemType getType(int index) {
        return QuickMenuItemType.values()[index];
    }

    private static int getTitleResId(QuickMenuItemType type) {
        switch (C14682.f1267x2185c976[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131165581;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131165584;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131165580;
            case TTSConst.TTSXML /*4*/:
                return RouteSummary.nativeIsPedestrian() ? 2131165582 : 2131165585;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return SettingsManager.nativeIsMuted() ? 2131165590 : 2131165583;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return 2131165588;
            default:
                return 0;
        }
    }

    private static int getIconResId(QuickMenuItemType type) {
        switch (C14682.f1267x2185c976[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return DemoManager.isStopped() ? 2131034254 : 2131034206;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131034257;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131034253;
            case TTSConst.TTSXML /*4*/:
                return RouteSummary.nativeIsPedestrian() ? 2131034255 : 2131034258;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return SettingsManager.nativeIsMuted() ? 2131034261 : 2131034256;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return 2131034260;
            default:
                return 0;
        }
    }
}
