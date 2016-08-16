package com.sygic.aura.fragments.interfaces;

import java.util.Collection;

public interface UpdateMapFragmentResultCallback extends FragmentResultCallback {
    void onDismiss();

    void onIgnore();

    void onUpdateMaps(Collection<String> collection);
}
