package com.crashlytics.android.ndk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ProcMapEntryParser {
    private static final Pattern MAP_REGEX;

    static {
        MAP_REGEX = Pattern.compile("\\s*(\\p{XDigit}+)-\\s*(\\p{XDigit}+)\\s+(.{4})\\s+\\p{XDigit}+\\s+.+\\s+\\d+\\s+(.+)");
    }

    public static ProcMapEntry parse(String mapEntry) {
        Matcher m = MAP_REGEX.matcher(mapEntry);
        if (!m.matches()) {
            return null;
        }
        long address = Long.valueOf(m.group(1), 16).longValue();
        long size = Long.valueOf(m.group(2), 16).longValue() - address;
        String perms = m.group(3);
        String path = m.group(4);
        if (isRelevant(address, size, perms, path)) {
            return new ProcMapEntry(address, size, path);
        }
        return null;
    }

    private static boolean isRelevant(long address, long size, String perms, String path) {
        return (perms.indexOf(120) == -1 || path.indexOf(47) == -1) ? false : true;
    }
}
