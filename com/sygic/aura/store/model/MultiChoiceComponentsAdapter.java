package com.sygic.aura.store.model;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.store.data.ComponentEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.fragment.MultiSelectComponentsFragment;
import com.sygic.aura.store.model.holder.StoreHolder;
import com.sygic.aura.store.model.holder.ViewHolderComponent;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MultiChoiceComponentsAdapter extends StoreAdapter {
    private final MultiSelectComponentsFragment mCallback;
    private long mMandatoryComponentsSize;
    private int mMandatoryCount;
    private final SparseArray<String> mSelected;
    private long mSelectedComponentsSize;

    public MultiChoiceComponentsAdapter(Context context, MultiSelectComponentsFragment callback) {
        super(context, 2130903178);
        this.mMandatoryCount = 0;
        this.mMandatoryComponentsSize = 0;
        this.mSelectedComponentsSize = 0;
        this.mCallback = callback;
        this.mSelected = new SparseArray();
    }

    public int getCount() {
        return super.getCount();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        StoreEntry item = (StoreEntry) getItem(position);
        if (convertView == null) {
            view = super.getView(position, convertView, parent);
            if (item != null && item.getType().equals(EViewType.TYPE_COMPONENT)) {
                view.setTag(new ViewHolderComponent(view, this.mIconInstalledFrw, this.mIconDownloading, this.mIconDownloadFrw, this.mInAnimation, this.mOutAnimation));
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
                componentHolder.showProgress(false);
                if (this.mSelected.indexOfKey(entry.getIndex()) >= 0) {
                    z = true;
                } else {
                    z = false;
                }
                componentHolder.setActionState(z, false);
            }
        }
        return view;
    }

    public void add(StoreEntry object) {
        if (object.getType().equals(EViewType.TYPE_COMPONENT)) {
            this.mCallback.setProceedVisible(true);
        }
        if (object.getType().equals(EViewType.TYPE_COMPONENT) && ((ComponentEntry) object).isMandatory()) {
            this.mSelected.append(object.getIndex(), object.getId());
            this.mMandatoryComponentsSize += ((ComponentEntry) object).getComponentSize();
            this.mMandatoryCount++;
            this.mCallback.setAdditionalDataSize(this.mMandatoryComponentsSize);
            return;
        }
        super.add(object);
    }

    public void clear() {
        super.clear();
        this.mSelected.clear();
    }

    public boolean hasSelectedItems() {
        return this.mMandatoryCount < this.mSelected.size();
    }

    public boolean areAllItemsSelected() {
        return getCount() + this.mMandatoryCount == this.mSelected.size();
    }

    public void onItemClick(StoreEntry item) {
        if (item instanceof ComponentEntry) {
            boolean z;
            int key = item.getIndex();
            if (this.mSelected.indexOfKey(key) < 0) {
                this.mSelected.put(key, item.getId());
                this.mSelectedComponentsSize += ((ComponentEntry) item).getComponentSize();
            } else {
                this.mSelected.remove(key);
                this.mSelectedComponentsSize -= ((ComponentEntry) item).getComponentSize();
            }
            notifyDataSetChanged();
            MultiSelectComponentsFragment multiSelectComponentsFragment = this.mCallback;
            if (this.mSelected.size() > this.mMandatoryCount) {
                z = true;
            } else {
                z = false;
            }
            multiSelectComponentsFragment.setProceedEnabled(z);
        }
    }

    public Object[][] getSelectedItems() {
        Object[][] installItems = (Object[][]) Array.newInstance(Object.class, new int[]{this.mSelected.size(), 2});
        for (int i = 0; i < this.mSelected.size(); i++) {
            int key = this.mSelected.keyAt(i);
            installItems[i] = new Object[]{this.mSelected.get(key), Integer.valueOf(key)};
        }
        return installItems;
    }

    public Object[][] getAllItems() {
        int i;
        int count = getCount();
        Object[][] installItems = (Object[][]) Array.newInstance(Object.class, new int[]{count, 2});
        for (i = 0; i < count; i++) {
            int index = this.mSelected.indexOfKey(((StoreEntry) getItem(i)).getIndex());
            if (index >= 0) {
                this.mSelected.removeAt(index);
            }
            installItems[i] = new Object[]{item.getId(), Integer.valueOf(item.getIndex())};
        }
        if (this.mSelected.size() <= 0) {
            return installItems;
        }
        int newLength = installItems.length + this.mSelected.size();
        Object[][] objArr = (Object[][]) Arrays.copyOf(installItems, newLength);
        for (i = installItems.length; i < newLength; i++) {
            int key = this.mSelected.keyAt(i - installItems.length);
            objArr[i] = new Object[]{this.mSelected.get(key), Integer.valueOf(key)};
        }
        return objArr;
    }

    public long getTotalSize() {
        return this.mMandatoryComponentsSize + this.mSelectedComponentsSize;
    }

    public String[] getSelectedIdsCopy() {
        String[] ids = new String[this.mSelected.size()];
        for (int i = 0; i < this.mSelected.size(); i++) {
            ids[i] = (String) this.mSelected.valueAt(i);
        }
        return ids;
    }
}
