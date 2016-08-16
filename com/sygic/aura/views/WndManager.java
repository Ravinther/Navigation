package com.sygic.aura.views;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.helper.SygicHelper;

public class WndManager {

    /* renamed from: com.sygic.aura.views.WndManager.1 */
    static class C17921 implements VoidCallback {
        C17921() {
        }

        public void getMethod() {
            WndManager.ResetAutoCloseTimer();
        }
    }

    /* renamed from: com.sygic.aura.views.WndManager.2 */
    static class C17932 implements VoidCallback {
        C17932() {
        }

        public void getMethod() {
            WndManager.ClearMapSelection();
        }
    }

    /* renamed from: com.sygic.aura.views.WndManager.3 */
    static class C17943 implements Callback<Boolean> {
        final /* synthetic */ int val$command;
        final /* synthetic */ String val$data;
        final /* synthetic */ String val$extra1;
        final /* synthetic */ String val$extra2;

        C17943(int i, String str, String str2, String str3) {
            this.val$command = i;
            this.val$data = str;
            this.val$extra1 = str2;
            this.val$extra2 = str3;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(WndManager.InvokeCommand(this.val$command, this.val$data, this.val$extra1, this.val$extra2));
        }
    }

    /* renamed from: com.sygic.aura.views.WndManager.4 */
    static class C17954 implements Callback<String> {
        C17954() {
        }

        public String getMethod() {
            return WndManager.GetLogParams();
        }
    }

    public enum EToastMsg {
        TMNone(0),
        TMRupiImportSuccess(1),
        TMRupiImportFailure(2);
        
        final int nValue;

        private EToastMsg(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    private static native void ClearMapSelection();

    private static native String GetLogParams();

    private static native boolean InvokeCommand(int i, String str, String str2, String str3);

    private static native void ResetAutoCloseTimer();

    public static void nativeResetAutoCloseTimer() {
        if (!SygicProject.IS_PROTOTYPE && SygicHelper.getCoreHandler() != null) {
            ObjectHandler.postAndWait(new C17921());
        }
    }

    public static void nativeClearMapSelection() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C17932());
        }
    }

    public static boolean nativeInvokeCommand(int command, String data, String extra1, String extra2) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C17943(command, data, extra1, extra2)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static String nativeGetLogParams() {
        if (SygicProject.IS_PROTOTYPE) {
            return "";
        }
        return (String) new ObjectHandler(new C17954()).execute().get(null);
    }

    public static boolean shouldShowCountdown(int tick) {
        return tick > 0 && tick < 10;
    }
}
