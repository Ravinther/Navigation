package com.sygic.aura.store.model.holder;

import android.view.View;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderEdit extends StoreHolder {
    public ViewHolderEdit(View view) {
        super(view);
    }

    public void updateContent(StoreEntry item) {
    }

    public EViewType getType() {
        return EViewType.TYPE_EDIT;
    }
}
