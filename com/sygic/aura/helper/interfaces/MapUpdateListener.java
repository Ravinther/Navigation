package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface MapUpdateListener extends CoreEventListener {
    void onMapUpdateCount(Integer num);
}
