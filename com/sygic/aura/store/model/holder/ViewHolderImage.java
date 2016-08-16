package com.sygic.aura.store.model.holder;

import android.view.View;
import android.widget.FrameLayout;
import com.squareup.picasso.Picasso;
import com.sygic.aura.store.data.ButtonEntry.EButtonAction;
import com.sygic.aura.store.data.ImageEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderImage extends StoreHolder {
    private final FrameLayout mImageContainer;

    public ViewHolderImage(View view) {
        super(view);
        this.mImageContainer = (FrameLayout) view.findViewById(2131624406);
    }

    public void updateContent(StoreEntry item) {
        ImageEntry imageEntry = (ImageEntry) item;
        this.mTitle.setVisibility(8);
        if (imageEntry.getAction() == EButtonAction.BtnNone) {
            this.mImageContainer.setForeground(null);
        } else {
            this.mImageContainer.setForeground(this.mContext.getResources().getDrawable(2130838060));
        }
        Picasso.with(this.mContext).load(imageEntry.getImageUrl()).into(this.mIcon);
    }

    public EViewType getType() {
        return EViewType.TYPE_IMAGE;
    }
}
