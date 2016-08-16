package com.sygic.aura.helper.EventReceivers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import com.sygic.aura.helper.CustomDialogFragment;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.settings.data.PoiSettingsManager;
import com.sygic.aura.views.WndManager.EToastMsg;
import java.util.Vector;
import loquendo.tts.engine.TTSConst;

public class WndEventsReceiver extends NativeMethodsHelper {
    private static final Vector<AutoCloseListener> sListeners;

    /* renamed from: com.sygic.aura.helper.EventReceivers.WndEventsReceiver.1 */
    static class C12671 implements Runnable {
        final /* synthetic */ boolean val$inMovement;
        final /* synthetic */ int val$tick;

        C12671(int i, boolean z) {
            this.val$tick = i;
            this.val$inMovement = z;
        }

        public void run() {
            if (!WndEventsReceiver.sListeners.isEmpty()) {
                ((AutoCloseListener) WndEventsReceiver.sListeners.lastElement()).onAutoClose(this.val$tick, this.val$inMovement);
            }
        }
    }

    /* renamed from: com.sygic.aura.helper.EventReceivers.WndEventsReceiver.2 */
    static class C12682 implements Runnable {
        final /* synthetic */ String val$messageId;

        C12682(String str) {
            this.val$messageId = str;
        }

        public void run() {
            Activity activity = SygicHelper.getActivity();
            int resId = activity.getResources().getIdentifier(this.val$messageId, "string", activity.getPackageName());
            if (resId != 0) {
                SToast.makeText(activity, resId, 1).show();
            } else {
                SToast.makeText(activity, this.val$messageId, 1).show();
            }
        }
    }

    /* renamed from: com.sygic.aura.helper.EventReceivers.WndEventsReceiver.3 */
    static class C12693 implements Runnable {
        final /* synthetic */ int val$msgId;

        C12693(int i) {
            this.val$msgId = i;
        }

        public void run() {
            SToast.makeText(SygicHelper.getActivity(), this.val$msgId, 1).show();
        }
    }

    /* renamed from: com.sygic.aura.helper.EventReceivers.WndEventsReceiver.4 */
    static class C12704 implements OnClickListener {
        C12704() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            SToast.makeText(SygicHelper.getActivity(), 2131165357, 0).show();
            PoiSettingsManager.nativeImportCustomPoi();
        }
    }

    /* renamed from: com.sygic.aura.helper.EventReceivers.WndEventsReceiver.5 */
    static class C12715 implements Runnable {
        final /* synthetic */ CustomDialogFragment val$dialog;

        C12715(CustomDialogFragment customDialogFragment) {
            this.val$dialog = customDialogFragment;
        }

        public void run() {
            this.val$dialog.showAllowingStateLoss("fragment_import_pois_tag");
        }
    }

    /* renamed from: com.sygic.aura.helper.EventReceivers.WndEventsReceiver.6 */
    static /* synthetic */ class C12726 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$views$WndManager$EToastMsg;

        static {
            $SwitchMap$com$sygic$aura$views$WndManager$EToastMsg = new int[EToastMsg.values().length];
            try {
                $SwitchMap$com$sygic$aura$views$WndManager$EToastMsg[EToastMsg.TMRupiImportSuccess.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$views$WndManager$EToastMsg[EToastMsg.TMRupiImportFailure.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$views$WndManager$EToastMsg[EToastMsg.TMNone.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static {
        sListeners = new Vector();
    }

    public static void registerAutoCloseListener(AutoCloseListener listener) {
        sListeners.add(listener);
    }

    public static void unregisterAutoCloseListener(AutoCloseListener listener) {
        sListeners.remove(listener);
    }

    public static void onAutoClose(int tick, boolean inMovement) {
        sHandler.post(new C12671(tick, inMovement));
    }

    public static void displayToast(String messageId) {
        sHandler.post(new C12682(messageId));
    }

    public static void displayToast(int id) {
        EToastMsg toastMsgType = EToastMsg.values()[id];
        if (toastMsgType.getValue() != id) {
            throw new RuntimeException("Invalid enum value");
        }
        int msgId;
        switch (C12726.$SwitchMap$com$sygic$aura$views$WndManager$EToastMsg[toastMsgType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                msgId = 2131165358;
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                msgId = 2131165356;
                break;
            default:
                Log.d("WND_EVENTS_RECEIVER", "None or unhandled enum value.");
                msgId = 0;
                break;
        }
        if (msgId != 0) {
            sHandler.post(new C12693(msgId));
        }
    }

    public static void onImportCustomPoi() {
        sHandler.post(new C12715(new Builder(SygicHelper.getActivity()).title(2131165360).body(2131165359).negativeButton(2131165354, null).positiveButton(2131165364, new C12704()).build()));
    }

    public static void onShowTrialOrExpiredOffline() {
    }
}
