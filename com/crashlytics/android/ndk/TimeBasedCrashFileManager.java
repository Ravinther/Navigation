package com.crashlytics.android.ndk;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import java.io.File;

class TimeBasedCrashFileManager implements CrashFileManager {
    private static final File[] EMPTY_FILES;
    private final File nativeCrashDirectory;
    private final CurrentTimeProvider timeProvider;

    static {
        EMPTY_FILES = new File[0];
    }

    public TimeBasedCrashFileManager(File nativeCrashDirectory) {
        this(nativeCrashDirectory, new SystemCurrentTimeProvider());
    }

    TimeBasedCrashFileManager(File nativeCrashDirectory, CurrentTimeProvider timeProvider) {
        this.nativeCrashDirectory = nativeCrashDirectory;
        this.timeProvider = timeProvider;
    }

    public File getNewCrashFile() {
        return new File(this.nativeCrashDirectory, this.timeProvider.getCurrentTimeMillis() + ".ndk.json");
    }

    public File getLastWrittenCrashFile() {
        return findLatestCrashFileByValue();
    }

    public void clearCrashFiles() {
        for (File f : getFiles()) {
            f.delete();
        }
    }

    private File findLatestCrashFileByValue() {
        File latestFile = null;
        File[] files = getFiles();
        long highest = 0;
        for (File f : files) {
            long value = Long.parseLong(stripExtension(f.getName()));
            if (value > highest) {
                highest = value;
                latestFile = f;
            }
        }
        return latestFile;
    }

    private String stripExtension(String fileName) {
        return fileName.substring(0, fileName.length() - ".ndk.json".length());
    }

    private File[] getFiles() {
        File[] files = this.nativeCrashDirectory.listFiles();
        return files == null ? EMPTY_FILES : files;
    }
}
