package com.google.android.vending.expansion.downloader;

import java.io.File;

public class Constants {
    public static final String EXP_PATH;

    static {
        EXP_PATH = File.separator + "Android" + File.separator + "obb" + File.separator;
    }
}
