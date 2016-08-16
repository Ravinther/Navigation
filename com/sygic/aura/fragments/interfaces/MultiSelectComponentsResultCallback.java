package com.sygic.aura.fragments.interfaces;

public interface MultiSelectComponentsResultCallback extends ComponentsFragmentResultCallback {
    void setComponentsSelected(Object[][] objArr, Object[][] objArr2);

    void setTotalComponentsSize(long j);
}
