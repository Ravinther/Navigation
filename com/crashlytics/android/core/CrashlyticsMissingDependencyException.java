package com.crashlytics.android.core;

public class CrashlyticsMissingDependencyException extends RuntimeException {
    CrashlyticsMissingDependencyException(String message) {
        super(buildExceptionMessage(message));
    }

    private static String buildExceptionMessage(String message) {
        return "\n" + message + "\n";
    }
}
