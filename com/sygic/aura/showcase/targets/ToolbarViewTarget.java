package com.sygic.aura.showcase.targets;

import android.view.View;
import com.sygic.aura.views.font_specials.SToolbar;
import loquendo.tts.engine.TTSConst;

public class ToolbarViewTarget extends ToolbarTarget {

    /* renamed from: com.sygic.aura.showcase.targets.ToolbarViewTarget.1 */
    static /* synthetic */ class C16811 {
        static final /* synthetic */ int[] f1289x28743463;

        static {
            f1289x28743463 = new int[ToolbarViewType.values().length];
            try {
                f1289x28743463[ToolbarViewType.NAV.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1289x28743463[ToolbarViewType.TITLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1289x28743463[ToolbarViewType.OVERFLOW.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum ToolbarViewType {
        NAV,
        TITLE,
        OVERFLOW
    }

    public ToolbarViewTarget(SToolbar toolbar, ToolbarViewType type) {
        super(getToolbarView(toolbar, type));
    }

    private static View getToolbarView(SToolbar toolbar, ToolbarViewType toolbarViewType) {
        if (toolbar == null) {
            return null;
        }
        switch (C16811.f1289x28743463[toolbarViewType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return getNavButton(toolbar);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return getTitleView(toolbar);
            case TTSConst.TTSUNICODE /*3*/:
                return getOverflowButton(toolbar);
            default:
                return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.view.View getNavButton(android.support.v7.widget.Toolbar r4) {
        /*
        r2 = android.support.v7.widget.Toolbar.class;
        r3 = "mNavButtonView";
        r1 = r2.getDeclaredField(r3);	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
        r2 = 1;
        r1.setAccessible(r2);	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
        r2 = r1.get(r4);	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
        r2 = (android.view.View) r2;	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
    L_0x0013:
        return r2;
    L_0x0014:
        r0 = move-exception;
        com.crashlytics.android.Crashlytics.logException(r0);
    L_0x0018:
        r2 = 0;
        goto L_0x0013;
    L_0x001a:
        r0 = move-exception;
        com.crashlytics.android.Crashlytics.logException(r0);
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.showcase.targets.ToolbarViewTarget.getNavButton(android.support.v7.widget.Toolbar):android.view.View");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.view.View getTitleView(com.sygic.aura.views.font_specials.SToolbar r4) {
        /*
        r2 = android.support.v7.widget.Toolbar.class;
        r3 = "mTitleTextView";
        r1 = r2.getDeclaredField(r3);	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
        r2 = 1;
        r1.setAccessible(r2);	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
        r2 = r1.get(r4);	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
        r2 = (android.view.View) r2;	 Catch:{ NoSuchFieldException -> 0x0014, IllegalAccessException -> 0x001a }
    L_0x0013:
        return r2;
    L_0x0014:
        r0 = move-exception;
        com.crashlytics.android.Crashlytics.logException(r0);
    L_0x0018:
        r2 = 0;
        goto L_0x0013;
    L_0x001a:
        r0 = move-exception;
        com.crashlytics.android.Crashlytics.logException(r0);
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.showcase.targets.ToolbarViewTarget.getTitleView(com.sygic.aura.views.font_specials.SToolbar):android.view.View");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.view.View getOverflowButton(android.support.v7.widget.Toolbar r6) {
        /*
        r4 = android.support.v7.widget.Toolbar.class;
        r5 = "mOuterActionMenuPresenter";
        r2 = r4.getDeclaredField(r5);	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
        r4 = 1;
        r2.setAccessible(r4);	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
        r1 = r2.get(r6);	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
        r4 = r1.getClass();	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
        r5 = "mOverflowButton";
        r3 = r4.getDeclaredField(r5);	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
        r4 = 1;
        r3.setAccessible(r4);	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
        r4 = r3.get(r1);	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
        r4 = (android.view.View) r4;	 Catch:{ NoSuchFieldException -> 0x0027, IllegalAccessException -> 0x002d }
    L_0x0026:
        return r4;
    L_0x0027:
        r0 = move-exception;
        com.crashlytics.android.Crashlytics.logException(r0);
    L_0x002b:
        r4 = 0;
        goto L_0x0026;
    L_0x002d:
        r0 = move-exception;
        com.crashlytics.android.Crashlytics.logException(r0);
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.showcase.targets.ToolbarViewTarget.getOverflowButton(android.support.v7.widget.Toolbar):android.view.View");
    }
}
