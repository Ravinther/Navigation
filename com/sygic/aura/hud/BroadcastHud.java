package com.sygic.aura.hud;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BroadcastHud implements HudInterface {
    public static String INTENT_ACTION_UPDATE;
    protected Context mContext;
    protected Bundle mData;

    static {
        INTENT_ACTION_UPDATE = "com.sygic.intent.ACTION_HUD_UPDATE";
    }

    public BroadcastHud(Context context) {
        this.mData = new Bundle();
        this.mContext = context;
    }

    public void send() {
        Intent intent = new Intent(INTENT_ACTION_UPDATE);
        intent.putExtras(this.mData);
        this.mContext.sendBroadcast(intent);
    }

    public void updateValue(String key, int value) {
        this.mData.putInt(key, value);
    }

    public void updateValue(String key, String value) {
        this.mData.putString(key, value);
    }

    public void updateValue(String key, int[] value) {
        this.mData.putIntArray(key, value);
    }

    public void updateValue(String key, long[] value) {
        this.mData.putLongArray(key, value);
    }
}
