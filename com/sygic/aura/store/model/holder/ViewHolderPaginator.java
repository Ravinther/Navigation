package com.sygic.aura.store.model.holder;

import android.view.View;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderPaginator extends StoreHolder {
    public ViewHolderPaginator(View view) {
        super(view);
    }

    public void updateContent(StoreEntry item) {
    }

    public EViewType getType() {
        return EViewType.TYPE_PAGINATOR;
    }
}
