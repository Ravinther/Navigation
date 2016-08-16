package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum OpenGraphMessageDialogFeature implements DialogFeature {
    OG_MESSAGE_DIALOG(20140204);
    
    private int minVersion;

    private OpenGraphMessageDialogFeature(int minVersion) {
        this.minVersion = minVersion;
    }

    public String getAction() {
        return "com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG";
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
