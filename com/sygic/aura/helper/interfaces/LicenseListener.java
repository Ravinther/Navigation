package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface LicenseListener extends CoreEventListener {
    void onLicenseUpdated();
}
