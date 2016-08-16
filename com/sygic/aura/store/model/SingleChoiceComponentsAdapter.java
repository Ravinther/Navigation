package com.sygic.aura.store.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.store.data.ComponentEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.model.holder.StoreHolder;
import com.sygic.aura.store.model.holder.ViewHolderComponent;

public class SingleChoiceComponentsAdapter extends StoreAdapter {
    public SingleChoiceComponentsAdapter(Context context) {
        super(context, 2130903178);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        StoreEntry item = (StoreEntry) getItem(position);
        if (convertView == null) {
            view = super.getView(position, convertView, parent);
            if (item != null && item.getType().equals(EViewType.TYPE_COMPONENT)) {
                view.setTag(new ViewHolderComponent(view, this.mIconInstalled, this.mIconDownloading, this.mIconDownload, this.mInAnimation, this.mOutAnimation));
            }
        } else {
            view = convertView;
        }
        if (item != null) {
            StoreHolder holder = (StoreHolder) view.getTag();
            holder.updateContent(item);
            if (holder.getType().equals(EViewType.TYPE_COMPONENT) && (item instanceof ComponentEntry)) {
                boolean z;
                ComponentEntry entry = (ComponentEntry) item;
                ViewHolderComponent componentHolder = (ViewHolderComponent) holder;
                if (entry.isInstalled() || !entry.isDownloading()) {
                    z = false;
                } else {
                    z = true;
                }
                componentHolder.showProgress(z);
                componentHolder.setActionState(entry.isInstalled(), entry.isDownloading(), entry.isUpdateAvailable());
                if (entry.isDownloading()) {
                    if (entry.getProgress() > 0) {
                        componentHolder.getProgressView().setIndeterminate(false);
                        componentHolder.getProgressView().setProgress(entry.getProgress());
                    } else {
                        componentHolder.getProgressView().setIndeterminate(true);
                    }
                }
            }
        }
        return view;
    }
}
