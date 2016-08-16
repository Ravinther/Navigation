package com.sygic.aura.search.model.holder;

import android.text.TextUtils;
import android.view.View;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.SearchItem;
import com.sygic.aura.search.model.data.StreetSearchItem;
import com.sygic.aura.search.model.data.StreetSearchItem.IconType;
import loquendo.tts.engine.TTSConst;

public class ViewHolderStreet extends ViewHolder {
    private String mCityName;

    /* renamed from: com.sygic.aura.search.model.holder.ViewHolderStreet.1 */
    static /* synthetic */ class C15861 {
        static final /* synthetic */ int[] f1283xc7c32761;

        static {
            f1283xc7c32761 = new int[IconType.values().length];
            try {
                f1283xc7c32761[IconType.ICON_NEARBY_POI.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1283xc7c32761[IconType.ICON_STREET_CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1283xc7c32761[IconType.ICON_STREET_CITY_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1283xc7c32761[IconType.ICON_STREET.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public ViewHolderStreet(View view) {
        super(view);
    }

    public void updateContent(SearchItem item, int position) {
        StreetSearchItem searchItem = (StreetSearchItem) item;
        if (searchItem != null) {
            if (position != 0 || TextUtils.isEmpty(this.mCityName)) {
                this.mTextView.setText(searchItem.getStreetName());
            } else {
                searchItem.setExtName(ResourceManager.getCoreString(this.mContext, 2131165680).replace("%city%", this.mCityName));
                this.mTextView.setText(ResourceManager.getCoreString(this.mContext, 2131165682));
            }
            if (searchItem.hasExtName()) {
                this.mExtTextView.setText(searchItem.getExtName());
                this.mExtTextView.setVisibility(0);
            } else {
                this.mExtTextView.setVisibility(8);
            }
            switch (C15861.f1283xc7c32761[searchItem.getIcon().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034283));
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034287));
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034286));
                    this.mExtTextView.setCoreText(2131165679);
                    this.mExtTextView.setVisibility(0);
                    break;
                default:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034285));
                    break;
            }
            this.mIconView.setVisibility(0);
        }
    }
}
