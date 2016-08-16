package com.vividsolutions.jts.util;

public class Assert {
    public static void isTrue(boolean assertion) {
        isTrue(assertion, null);
    }

    public static void isTrue(boolean assertion, String message) {
        if (!assertion) {
            if (message == null) {
                throw new AssertionFailedException();
            }
            throw new AssertionFailedException(message);
        }
    }

    public static void shouldNeverReachHere() {
        shouldNeverReachHere(null);
    }

    public static void shouldNeverReachHere(String message) {
        throw new AssertionFailedException("Should never reach here" + (message != null ? ": " + message : ""));
    }
}
