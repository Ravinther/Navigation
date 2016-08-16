package com.sygic.aura.store.model.holder;

import android.view.View;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.views.font_specials.STextView;

public class ViewHolderGroup extends StoreHolder {
    public ViewHolderGroup(View view) {
        super(view);
    }

    public void updateContent(StoreEntry item) {
        ((STextView) this.mTitle).setCoreText(item.getTitle());
    }

    public EViewType getType() {
        return EViewType.TYPE_GROUP;
    }
}
