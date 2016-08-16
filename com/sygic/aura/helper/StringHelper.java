package com.sygic.aura.helper;

public class StringHelper {
    public static String cutVersionString(String version, int parts) {
        if (version == null || version.isEmpty() || parts <= 0) {
            return "";
        }
        int index = version.indexOf(46);
        if (index == -1) {
            return version;
        }
        for (int i = 1; i < parts; i++) {
            int tmpIndex = version.indexOf(46, index + 1);
            if (tmpIndex == -1) {
                return version;
            }
            index = tmpIndex;
        }
        return version.substring(0, index);
    }
}
