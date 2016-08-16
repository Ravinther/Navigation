package com.sygic.aura.store.model.holder;

import android.text.TextUtils;
import android.view.View;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.data.SelectEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderSelect extends StoreHolder {
    public ViewHolderSelect(View view) {
        super(view);
    }

    public void updateContent(StoreEntry item) {
        SelectEntry selectEntry = (SelectEntry) item;
        this.mTitle.setText(item.getTitle());
        String selectedTitle = selectEntry.getSelectedOption().getTitle();
        if (TextUtils.isEmpty(selectedTitle)) {
            ResourceManager.makeControlVisible(this.mDescription, false, true);
            return;
        }
        ResourceManager.makeControlVisible(this.mDescription, true);
        this.mDescription.setText(selectedTitle);
    }

    public EViewType getType() {
        return EViewType.TYPE_SELECT;
    }
}
