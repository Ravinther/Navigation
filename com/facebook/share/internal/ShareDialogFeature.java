package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum ShareDialogFeature implements DialogFeature {
    SHARE_DIALOG(20130618),
    PHOTOS(20140204),
    VIDEO(20141028);
    
    private int minVersion;

    private ShareDialogFeature(int minVersion) {
        this.minVersion = minVersion;
    }

    public String getAction() {
        return "com.facebook.platform.action.request.FEED_DIALOG";
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
