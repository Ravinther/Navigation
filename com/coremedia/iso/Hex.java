package com.coremedia.iso;

public class Hex {
    private static final char[] DIGITS;

    static {
        DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public static String encodeHex(byte[] data) {
        return encodeHex(data, 0);
    }

    public static String encodeHex(byte[] data, int group) {
        int l = data.length;
        char[] out = new char[((group > 0 ? l / group : 0) + (l << 1))];
        int i = 0;
        int j = 0;
        while (i < l) {
            int i2;
            if (group <= 0 || i % group != 0 || j <= 0) {
                i2 = j;
            } else {
                i2 = j + 1;
                out[j] = '-';
            }
            j = i2 + 1;
            out[i2] = DIGITS[(data[i] & 240) >>> 4];
            i2 = j + 1;
            out[j] = DIGITS[data[i] & 15];
            i++;
            j = i2;
        }
        return new String(out);
    }
}
