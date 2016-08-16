package com.sygic.aura.helper.imageMetadataExtractor.lang;

public class StringUtil {
    public static <T extends CharSequence> String join(T[] strings, String delimiter) {
        int i = 0;
        int capacity = 0;
        int delimLength = delimiter.length();
        for (T value : strings) {
            T value2;
            capacity += value2.length() + delimLength;
        }
        StringBuilder buffer = new StringBuilder(capacity);
        boolean first = true;
        int length = strings.length;
        while (i < length) {
            value2 = strings[i];
            if (first) {
                first = false;
            } else {
                buffer.append(delimiter);
            }
            buffer.append(value2);
            i++;
        }
        return buffer.toString();
    }
}
