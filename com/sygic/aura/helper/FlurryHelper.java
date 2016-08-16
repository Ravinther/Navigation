package com.sygic.aura.helper;

import com.sygic.aura.license.LicenseInfo;
import java.util.HashMap;

public abstract class FlurryHelper {
    public static void addDefaultParams(HashMap<String, Object> params) {
        params.put("speedcam_licence", "" + LicenseInfo.nativeHasSpeedcamLicense());
        params.put("traffic_licence", "" + LicenseInfo.nativeHasTrafficLicense());
    }
}
