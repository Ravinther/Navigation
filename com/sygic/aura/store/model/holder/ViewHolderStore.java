package com.sygic.aura.store.model.holder;

import android.view.View;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderStore extends StoreHolder {
    public ViewHolderStore(View view) {
        super(view);
    }

    public void updateContent(StoreEntry item) {
        this.mTitle.setText(item.getTitle());
        loadIcon(item, true);
    }

    public EViewType getType() {
        return EViewType.TYPE_NONE;
    }
}
