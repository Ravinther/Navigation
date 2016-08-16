package com.sygic.aura.map.notification;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.map.notification.data.NotificationCenterItem;

public class NotificationManager {

    /* renamed from: com.sygic.aura.map.notification.NotificationManager.1 */
    static class C13331 implements Callback<NotificationCenterItem[]> {
        final /* synthetic */ int val$mask;

        C13331(int i) {
            this.val$mask = i;
        }

        public NotificationCenterItem[] getMethod() {
            return NotificationManager.GetNotifications(this.val$mask);
        }
    }

    /* renamed from: com.sygic.aura.map.notification.NotificationManager.2 */
    static class C13342 implements VoidCallback {
        final /* synthetic */ int[] val$notificationIds;

        C13342(int[] iArr) {
            this.val$notificationIds = iArr;
        }

        public void getMethod() {
            NotificationManager.SetDisplayedNotifications(this.val$notificationIds);
        }
    }

    /* renamed from: com.sygic.aura.map.notification.NotificationManager.3 */
    static class C13353 implements VoidCallback {
        final /* synthetic */ int val$id;

        C13353(int i) {
            this.val$id = i;
        }

        public void getMethod() {
            NotificationManager.PerformTap(this.val$id);
        }
    }

    /* renamed from: com.sygic.aura.map.notification.NotificationManager.4 */
    static class C13364 implements VoidCallback {
        C13364() {
        }

        public void getMethod() {
            NotificationManager.ShowParkingPlacesOnMap();
        }
    }

    private static native NotificationCenterItem[] GetNotifications(int i);

    private static native void PerformTap(int i);

    private static native void SetDisplayedNotifications(int[] iArr);

    private static native void ShowParkingPlacesOnMap();

    public static NotificationCenterItem[] nativeGetNotifications(int mask) {
        if (SygicProject.IS_PROTOTYPE) {
            return new NotificationCenterItem[0];
        }
        return (NotificationCenterItem[]) new ObjectHandler(new C13331(mask)).execute().get(new NotificationCenterItem[0]);
    }

    public static void nativeSetDisplayedNotifications(int[] notificationIds) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C13342(notificationIds));
        }
    }

    public static void nativePerformClick(int id) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C13353(id));
        }
    }

    public static void nativeShowParkingPlacesOnMap() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C13364());
        }
    }
}
