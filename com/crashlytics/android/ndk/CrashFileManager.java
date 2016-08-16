package com.crashlytics.android.ndk;

import java.io.File;

interface CrashFileManager {
    void clearCrashFiles();

    File getLastWrittenCrashFile();

    File getNewCrashFile();
}
