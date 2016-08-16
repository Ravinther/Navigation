package com.sygic.aura.network;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;

public class ComponentManager {

    /* renamed from: com.sygic.aura.network.ComponentManager.1 */
    static class C14001 implements Callback<Boolean> {
        C14001() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(ComponentManager.InvokeManageMaps());
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.2 */
    static class C14012 implements Callback<Boolean> {
        final /* synthetic */ String val$listId;

        C14012(String str) {
            this.val$listId = str;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(ComponentManager.RequestList(this.val$listId));
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.3 */
    static class C14023 implements Callback<Boolean> {
        final /* synthetic */ String val$id;
        final /* synthetic */ int val$position;

        C14023(String str, int i) {
            this.val$id = str;
            this.val$position = i;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(ComponentManager.RequestInstall(this.val$id, this.val$position));
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.4 */
    static class C14034 implements Callback<Boolean> {
        final /* synthetic */ Object[][] val$toInstall;
        final /* synthetic */ Object[][] val$toUninstall;

        C14034(Object[][] objArr, Object[][] objArr2) {
            this.val$toInstall = objArr;
            this.val$toUninstall = objArr2;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(ComponentManager.RequestBatchInstall(this.val$toInstall, this.val$toUninstall));
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.5 */
    static class C14045 implements Callback<Boolean> {
        final /* synthetic */ String val$id;
        final /* synthetic */ int val$position;

        C14045(String str, int i) {
            this.val$id = str;
            this.val$position = i;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(ComponentManager.RequestUninstall(this.val$id, this.val$position));
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.6 */
    static class C14056 implements Callback<Long> {
        final /* synthetic */ String val$id;
        final /* synthetic */ int val$position;

        C14056(String str, int i) {
            this.val$id = str;
            this.val$position = i;
        }

        public Long getMethod() {
            return Long.valueOf(ComponentManager.GetComponentSize(this.val$id, this.val$position));
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.7 */
    static class C14067 implements Callback<long[]> {
        final /* synthetic */ String val$id;

        C14067(String str) {
            this.val$id = str;
        }

        public long[] getMethod() {
            return ComponentManager.GetDownloadItemStatus(this.val$id);
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.8 */
    static class C14078 implements Callback<Integer> {
        C14078() {
        }

        public Integer getMethod() {
            return Integer.valueOf(ComponentManager.GetInstalledMapCount());
        }
    }

    /* renamed from: com.sygic.aura.network.ComponentManager.9 */
    static class C14089 implements Callback<String[]> {
        C14089() {
        }

        public String[] getMethod() {
            return ComponentManager.GetInstalledMaps();
        }
    }

    private static native long GetComponentSize(String str, int i);

    private static native long[] GetDownloadItemStatus(String str);

    private static native int GetInstalledMapCount();

    private static native String[] GetInstalledMaps();

    private static native boolean InvokeManageMaps();

    private static native boolean RequestBatchInstall(Object[][] objArr, Object[][] objArr2);

    private static native boolean RequestInstall(String str, int i);

    private static native boolean RequestList(String str);

    private static native boolean RequestUninstall(String str, int i);

    public static boolean nativeInvokeManageMaps() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C14001()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRequestList(String listId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C14012(listId)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeInstall(String id, int position) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C14023(id, position)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeInstall(Object[][] toInstall, Object[][] toUninstall) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C14034(toInstall, toUninstall)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeUninstall(String id, int position) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C14045(id, position)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static long nativeGetComponentSize(String id, int position) {
        if (SygicProject.IS_PROTOTYPE) {
            return 250;
        }
        return ((Long) new ObjectHandler(new C14056(id, position)).execute().get(Long.valueOf(0))).longValue();
    }

    public static long[] nativeGetDownloadItemStatus(String id) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (long[]) new ObjectHandler(new C14067(id)).execute().get(new long[0]);
    }

    public static int nativeGetInstalledMapCount() {
        if (SygicProject.IS_PROTOTYPE) {
            return 5;
        }
        return ((Integer) new ObjectHandler(new C14078()).execute().get(Integer.valueOf(0))).intValue();
    }

    public static String[] nativeGetInstalledMaps() {
        if (SygicProject.IS_PROTOTYPE) {
            return new String[0];
        }
        return (String[]) new ObjectHandler(new C14089()).execute().get(new String[0]);
    }
}
