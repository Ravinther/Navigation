package com.sygic.aura.search.model.holder;

import android.text.TextUtils;
import android.view.View;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.CrossingSearchItem;
import com.sygic.aura.search.model.data.CrossingSearchItem.IconType;
import com.sygic.aura.search.model.data.SearchItem;
import loquendo.tts.engine.TTSConst;

public class ViewHolderCrossing extends ViewHolder {
    private String mStreetName;

    /* renamed from: com.sygic.aura.search.model.holder.ViewHolderCrossing.1 */
    static /* synthetic */ class C15831 {
        static final /* synthetic */ int[] f1279xb56c7362;

        static {
            f1279xb56c7362 = new int[IconType.values().length];
            try {
                f1279xb56c7362[IconType.ICON_NEARBY_POI.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1279xb56c7362[IconType.ICON_ADDR_POINT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1279xb56c7362[IconType.ICON_CROSSING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public ViewHolderCrossing(View view) {
        super(view);
    }

    public void updateContent(SearchItem item, int position) {
        CrossingSearchItem searchItem = (CrossingSearchItem) item;
        if (searchItem != null) {
            if (position != 0) {
                this.mTextView.setText(searchItem.getDisplayName());
            } else if (!TextUtils.isEmpty(this.mStreetName)) {
                searchItem.setExtName(ResourceManager.getCoreString(this.mContext, 2131165681).replace("%street%", this.mStreetName));
                this.mTextView.setText(ResourceManager.getCoreString(this.mContext, 2131165682));
            }
            if (searchItem.hasExtName()) {
                this.mExtTextView.setText(searchItem.getExtName());
                this.mExtTextView.setVisibility(0);
            } else {
                this.mExtTextView.setVisibility(8);
            }
            switch (C15831.f1279xb56c7362[searchItem.getIcon().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034283));
                    break;
                default:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034280));
                    break;
            }
            this.mIconView.setVisibility(0);
        }
    }
}
