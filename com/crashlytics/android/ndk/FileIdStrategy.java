package com.crashlytics.android.ndk;

import java.io.File;
import java.io.IOException;

interface FileIdStrategy {
    String getId(File file) throws IOException;
}
