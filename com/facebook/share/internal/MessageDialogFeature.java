package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum MessageDialogFeature implements DialogFeature {
    MESSAGE_DIALOG(20140204),
    PHOTOS(20140324),
    VIDEO(20141218);
    
    private int minVersion;

    private MessageDialogFeature(int minVersion) {
        this.minVersion = minVersion;
    }

    public String getAction() {
        return "com.facebook.platform.action.request.MESSAGE_DIALOG";
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
