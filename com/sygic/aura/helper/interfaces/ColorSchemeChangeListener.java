package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.settings.data.SettingsManager.EColorScheme;

public interface ColorSchemeChangeListener extends CoreEventListener {
    void onColorSchemeChanged(EColorScheme eColorScheme);
}
