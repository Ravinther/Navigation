package com.sygic.aura.store.model.holder;

import android.view.View;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.data.TextEntry;

public class ViewHolderText extends StoreHolder {
    public ViewHolderText(View view) {
        super(view);
    }

    public void updateContent(StoreEntry item) {
        this.mTitle.setText(((TextEntry) item).getText());
    }

    public EViewType getType() {
        return EViewType.TYPE_TEXT;
    }
}
