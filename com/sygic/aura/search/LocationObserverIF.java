package com.sygic.aura.search;

public interface LocationObserverIF {
    void notifyDataAdded(int i, boolean z);

    void notifyDataRemoved(int i, String str, boolean z);

    void onDataChanged();
}
