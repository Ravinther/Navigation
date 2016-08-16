package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface WidgetListener extends CoreEventListener {
    void onAddHudPlugin();

    void onUpdateWidgets();
}
