package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.store.data.StoreEntry;
import java.util.ArrayList;

public interface StoreBaseListener extends CoreEventListener {
    void onConnectionChanged(Boolean bool);

    void onListLoaded(ArrayList<StoreEntry> arrayList, Boolean bool);

    void onStoreMessage(String str);
}
