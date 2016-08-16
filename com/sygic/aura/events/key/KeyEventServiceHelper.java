package com.sygic.aura.events.key;

import android.content.Context;

public abstract class KeyEventServiceHelper implements KeyEventService {
    public static KeyEventServiceImpl createInstance(Context context) {
        return new KeyEventServiceImpl(context);
    }
}
