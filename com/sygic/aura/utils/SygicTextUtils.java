package com.sygic.aura.utils;

public class SygicTextUtils {
    public static String capitalizeEachWord(String source) {
        StringBuffer res = new StringBuffer();
        for (String str : source.split("\\s+")) {
            char[] stringArray = str.toCharArray();
            if (stringArray.length > 0) {
                stringArray[0] = Character.toUpperCase(stringArray[0]);
                res.append(new String(stringArray)).append(" ");
            }
        }
        return res.toString();
    }
}
