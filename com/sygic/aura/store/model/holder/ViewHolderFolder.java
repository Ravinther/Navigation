package com.sygic.aura.store.model.holder;

import android.text.TextUtils;
import android.view.View;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderFolder extends StoreHolder {
    public ViewHolderFolder(View view) {
        super(view);
    }

    public void updateContent(StoreEntry item) {
        this.mTitle.setText(item.getTitle());
        this.mDescription.setVisibility(8);
        this.mIcon.setVisibility(8);
        loadIcon(item, true);
        String subtitle = item.getSummary();
        if (!TextUtils.isEmpty(subtitle)) {
            this.mDescription.setVisibility(0);
            this.mDescription.setText(subtitle);
        }
    }

    public EViewType getType() {
        return EViewType.TYPE_FOLDER;
    }
}
