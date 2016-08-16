package com.sygic.aura.store.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.store.data.ComponentEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.model.holder.StoreHolder;
import com.sygic.aura.store.model.holder.ViewHolderEdit;
import com.sygic.aura.store.model.holder.ViewHolderFolder;
import com.sygic.aura.store.model.holder.ViewHolderGroup;
import com.sygic.aura.store.model.holder.ViewHolderImage;
import com.sygic.aura.store.model.holder.ViewHolderPaginator;
import com.sygic.aura.store.model.holder.ViewHolderProduct;
import com.sygic.aura.store.model.holder.ViewHolderSelect;
import com.sygic.aura.store.model.holder.ViewHolderStore;
import com.sygic.aura.store.model.holder.ViewHolderText;
import com.sygic.base.C1799R;
import java.util.Collection;
import loquendo.tts.engine.TTSConst;

public abstract class StoreAdapter extends ArrayAdapter<StoreEntry> {
    protected final Drawable mIconDownload;
    protected final Drawable mIconDownloadFrw;
    protected final Drawable mIconDownloading;
    protected final Drawable mIconInstalled;
    protected final Drawable mIconInstalledFrw;
    protected final Animation mInAnimation;
    protected final LayoutInflater mInflater;
    protected final Animation mOutAnimation;

    /* renamed from: com.sygic.aura.store.model.StoreAdapter.1 */
    static /* synthetic */ class C17461 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType;

        static {
            $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType = new int[EViewType.values().length];
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_GROUP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_TEXT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_FOLDER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_PRODUCT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_EDIT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_PAGINATOR.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_SELECT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_IMAGE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public StoreAdapter(Context context, int resource) {
        super(context, resource, C1799R.id.title);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        Resources resources = context.getResources();
        this.mIconDownloading = FontDrawable.inflate(resources, 2131034169);
        this.mIconInstalled = FontDrawable.inflate(resources, 2131034198);
        this.mIconDownload = FontDrawable.inflate(resources, 2131034262);
        this.mIconInstalledFrw = FontDrawable.inflate(resources, 2131034199);
        this.mIconDownloadFrw = FontDrawable.inflate(resources, 2131034263);
        this.mInAnimation = AnimationUtils.loadAnimation(context, 2130968610);
        this.mOutAnimation = AnimationUtils.loadAnimation(context, 2130968614);
    }

    public int getViewTypeCount() {
        return EViewType.values().length;
    }

    public int getItemViewType(int position) {
        return ((StoreEntry) getItem(position)).getType().ordinal();
    }

    @SuppressLint({"ViewHolder"})
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        EViewType eViewType = EViewType.values()[viewType];
        if (eViewType.ordinal() != viewType) {
            throw new IllegalStateException("Enum value mismatch");
        }
        View view;
        StoreHolder holder;
        switch (C17461.$SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[eViewType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                view = this.mInflater.inflate(2130903167, parent, false);
                holder = new ViewHolderGroup(view);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                view = this.mInflater.inflate(2130903215, parent, false);
                holder = new ViewHolderText(view);
                break;
            case TTSConst.TTSUNICODE /*3*/:
                view = this.mInflater.inflate(2130903166, parent, false);
                holder = new ViewHolderFolder(view);
                break;
            case TTSConst.TTSXML /*4*/:
                view = this.mInflater.inflate(2130903211, parent, false);
                holder = new ViewHolderProduct(view, this.mIconInstalled);
                break;
            case TTSConst.TTSEVT_TEXT /*5*/:
                view = this.mInflater.inflate(2130903165, parent, false);
                holder = new ViewHolderEdit(view);
                break;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                view = this.mInflater.inflate(2130903165, parent, false);
                holder = new ViewHolderPaginator(view);
                break;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                view = this.mInflater.inflate(2130903204, parent, false);
                holder = new ViewHolderSelect(view);
                break;
            case TTSConst.TTSEVT_TAG /*8*/:
                view = this.mInflater.inflate(2130903176, parent, false);
                holder = new ViewHolderImage(view);
                break;
            default:
                view = super.getView(position, convertView, parent);
                holder = new ViewHolderStore(view);
                break;
        }
        view.setTag(holder);
        return view;
    }

    public boolean isEnabled(int position) {
        return getItemViewType(position) != EViewType.TYPE_TEXT.ordinal();
    }

    public ComponentEntry findItem(String id) {
        for (int i = 0; i < getCount(); i++) {
            StoreEntry item = (StoreEntry) getItem(i);
            if (item.getId().equals(id)) {
                return (ComponentEntry) item;
            }
        }
        return null;
    }

    public void retainAll(Collection<String> collection) {
        setNotifyOnChange(false);
        int i = 0;
        while (i < getCount()) {
            StoreEntry item = (StoreEntry) getItem(i);
            if (collection.contains(item.getId())) {
                item.setPosition(i);
            } else {
                remove(item);
                i--;
            }
            i++;
        }
        notifyDataSetChanged();
    }
}
