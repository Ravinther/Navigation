package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.route.data.WarningItem;

public interface WarningChangeListener extends CoreEventListener {
    void onWarningChange();

    void onWarningChange(WarningItem warningItem);
}
