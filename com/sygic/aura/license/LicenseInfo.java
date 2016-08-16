package com.sygic.aura.license;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.SygicHelper;

public class LicenseInfo {

    /* renamed from: com.sygic.aura.license.LicenseInfo.1 */
    static class C12901 implements Callback<Boolean> {
        C12901() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(LicenseInfo.IsTrial());
        }
    }

    /* renamed from: com.sygic.aura.license.LicenseInfo.2 */
    static class C12912 implements Callback<Boolean> {
        C12912() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(LicenseInfo.IsTrialExpired());
        }
    }

    /* renamed from: com.sygic.aura.license.LicenseInfo.3 */
    static class C12923 implements Callback<Integer> {
        C12923() {
        }

        public Integer getMethod() {
            return Integer.valueOf(LicenseInfo.GetTrialDays());
        }
    }

    /* renamed from: com.sygic.aura.license.LicenseInfo.4 */
    static class C12934 implements Callback<Boolean> {
        C12934() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(LicenseInfo.HasTrafficLicense());
        }
    }

    /* renamed from: com.sygic.aura.license.LicenseInfo.5 */
    static class C12945 implements Callback<Boolean> {
        C12945() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(LicenseInfo.HasSpeedcamLicense());
        }
    }

    private static native int GetTrialDays();

    private static native boolean HasSpeedcamLicense();

    private static native boolean HasTrafficLicense();

    private static native boolean IsTrial();

    private static native boolean IsTrialExpired();

    public static boolean isValid() {
        return SygicHelper.getCoreHandler() != null;
    }

    public static boolean nativeIsTrial() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C12901()).execute().get(Boolean.valueOf(true))).booleanValue();
    }

    public static boolean nativeIsTrialExpired() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C12912()).execute().get(Boolean.valueOf(true))).booleanValue();
    }

    public static int nativeGetTrialDays() {
        if (SygicProject.IS_PROTOTYPE) {
            return 3;
        }
        return ((Integer) new ObjectHandler(new C12923()).execute().get(Integer.valueOf(0))).intValue();
    }

    public static boolean nativeHasTrafficLicense() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C12934()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeHasSpeedcamLicense() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C12945()).execute().get(Boolean.valueOf(false))).booleanValue();
    }
}
