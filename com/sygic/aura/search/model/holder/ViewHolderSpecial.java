package com.sygic.aura.search.model.holder;

import android.text.TextUtils;
import android.view.View;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.SearchItem;
import com.sygic.aura.search.model.data.SpecialSearchItem;
import com.sygic.aura.search.model.data.SpecialSearchItem.ItemType;
import loquendo.tts.engine.TTSConst;

public class ViewHolderSpecial extends ViewHolder {
    private String mName;

    /* renamed from: com.sygic.aura.search.model.holder.ViewHolderSpecial.1 */
    static /* synthetic */ class C15851 {
        static final /* synthetic */ int[] f1282xfef9d2f5;

        static {
            f1282xfef9d2f5 = new int[ItemType.values().length];
            try {
                f1282xfef9d2f5[ItemType.ITEM_NEARBY_POI_STREET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1282xfef9d2f5[ItemType.ITEM_NEARBY_POI_CROSSING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1282xfef9d2f5[ItemType.ITEM_HOUSE_NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public ViewHolderSpecial(View view) {
        super(view);
        this.mName = null;
    }

    public void updateContent(SearchItem item, int position) {
        SpecialSearchItem searchItem = (SpecialSearchItem) item;
        if (searchItem != null) {
            String strNear;
            switch (C15851.f1282xfef9d2f5[searchItem.getSpecialItemType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    if (!TextUtils.isEmpty(this.mName)) {
                        strNear = ResourceManager.getCoreString(this.mContext, 2131165680);
                        if (strNear != null) {
                            searchItem.setExtName(strNear.replace("%city%", this.mName));
                        }
                        searchItem.setDisplayName(ResourceManager.getCoreString(this.mContext, 2131165682));
                    }
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034283));
                    this.mIconView.setVisibility(0);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (!TextUtils.isEmpty(this.mName)) {
                        strNear = ResourceManager.getCoreString(this.mContext, 2131165681);
                        if (strNear != null) {
                            searchItem.setExtName(strNear.replace("%street%", this.mName));
                        }
                        searchItem.setDisplayName(ResourceManager.getCoreString(this.mContext, 2131165682));
                    }
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034283));
                    this.mIconView.setVisibility(0);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    if (!TextUtils.isEmpty(this.mName)) {
                        searchItem.setExtName(ResourceManager.getCoreString(this.mContext, 2131165674));
                        searchItem.setDisplayName(this.mName);
                    }
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034278));
                    this.mIconView.setVisibility(0);
                    break;
                default:
                    this.mIconView.setVisibility(8);
                    break;
            }
            this.mTextView.setText(searchItem.getDisplayName());
            if (searchItem.hasExtName()) {
                this.mExtTextView.setText(searchItem.getExtName());
                this.mExtTextView.setVisibility(0);
                return;
            }
            this.mExtTextView.setVisibility(8);
        }
    }

    public void setName(String name) {
        this.mName = name;
    }
}
