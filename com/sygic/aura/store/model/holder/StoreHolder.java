package com.sygic.aura.store.model.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.base.C1799R;

public abstract class StoreHolder {
    final Context mContext;
    final TextView mDescription;
    final ImageView mIcon;
    final ImageView mStatusIcon;
    final TextView mTitle;

    public abstract EViewType getType();

    public abstract void updateContent(StoreEntry storeEntry);

    public StoreHolder(View view) {
        this.mContext = view.getContext();
        this.mTitle = (TextView) view.findViewById(C1799R.id.title);
        this.mDescription = (TextView) view.findViewById(C1799R.id.description);
        this.mStatusIcon = (ImageView) view.findViewById(2131624399);
        this.mIcon = (ImageView) view.findViewById(C1799R.id.icon);
    }

    protected void loadIcon(StoreEntry item, boolean goneIcon) {
        if (TextUtils.isEmpty(item.getIconUrl())) {
            this.mIcon.setVisibility(goneIcon ? 8 : 4);
            return;
        }
        this.mIcon.setVisibility(0);
        Picasso.with(this.mContext).load(item.getIconUrl()).placeholder(2130838125).error(2130838125).into(this.mIcon);
    }
}
