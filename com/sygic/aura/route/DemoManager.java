package com.sygic.aura.route;

import com.sygic.aura.C1090R;
import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;

public class DemoManager {

    /* renamed from: com.sygic.aura.route.DemoManager.1 */
    static class C14801 implements VoidCallback {
        final /* synthetic */ int val$position;

        C14801(int i) {
            this.val$position = i;
        }

        public void getMethod() {
            DemoManager.DemonstrateStart(this.val$position);
        }
    }

    /* renamed from: com.sygic.aura.route.DemoManager.2 */
    static class C14812 implements VoidCallback {
        C14812() {
        }

        public void getMethod() {
            DemoManager.DemonstrateStop();
        }
    }

    /* renamed from: com.sygic.aura.route.DemoManager.3 */
    static class C14823 implements Callback<Boolean> {
        C14823() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(DemoManager.IsDemoRunning());
        }
    }

    /* renamed from: com.sygic.aura.route.DemoManager.4 */
    static class C14834 implements VoidCallback {
        final /* synthetic */ int val$speed;

        C14834(int i) {
            this.val$speed = i;
        }

        public void getMethod() {
            DemoManager.SetDemoSpeed(this.val$speed);
        }
    }

    /* renamed from: com.sygic.aura.route.DemoManager.5 */
    static class C14845 implements Callback<Integer> {
        C14845() {
        }

        public Integer getMethod() {
            return Integer.valueOf(DemoManager.GetDemoSpeed());
        }
    }

    /* renamed from: com.sygic.aura.route.DemoManager.6 */
    static class C14856 implements VoidCallback {
        final /* synthetic */ boolean val$paused;

        C14856(boolean z) {
            this.val$paused = z;
        }

        public void getMethod() {
            DemoManager.SetDemoPaused(this.val$paused);
        }
    }

    /* renamed from: com.sygic.aura.route.DemoManager.7 */
    static class C14867 implements Callback<Boolean> {
        C14867() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(DemoManager.IsDemoPaused());
        }
    }

    private static native void DemonstrateStart(int i);

    private static native void DemonstrateStop();

    private static native int GetDemoSpeed();

    private static native boolean IsDemoPaused();

    private static native boolean IsDemoRunning();

    private static native void SetDemoPaused(boolean z);

    private static native void SetDemoSpeed(int i);

    public static void nativeDemonstrateStart(int position) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C14801(position));
        }
    }

    public static void nativeDemonstrateStop() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C14812());
        }
    }

    public static boolean nativeIsDemoRunning() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C14823()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeSetDemoSpeed(int speed) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C14834(speed));
        }
    }

    public static int nativeGetDemoSpeed() {
        if (SygicProject.IS_PROTOTYPE) {
            return 23;
        }
        return ((Integer) new ObjectHandler(new C14845()).execute().get(Integer.valueOf(0))).intValue();
    }

    public static void nativeSetDemoPaused(boolean paused) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C14856(paused));
        }
    }

    public static void nativeSwapDemoPaused() {
        if (nativeIsDemoRunning()) {
            nativeSetDemoPaused(!nativeIsDemoPaused());
        }
    }

    public static boolean nativeIsDemoPaused() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C14867()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static int rotateSpeed() {
        int demoSpeed;
        switch (nativeGetDemoSpeed()) {
            case C1090R.styleable.Theme_checkboxStyle /*100*/:
                demoSpeed = 200;
                break;
            case 200:
                demoSpeed = 400;
                break;
            case 400:
                demoSpeed = 800;
                break;
            default:
                demoSpeed = 100;
                break;
        }
        nativeSetDemoSpeed(demoSpeed);
        return demoSpeed;
    }

    public static boolean isStopped() {
        return (nativeIsDemoRunning() || nativeIsDemoPaused()) ? false : true;
    }
}
