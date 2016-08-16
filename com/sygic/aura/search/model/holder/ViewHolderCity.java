package com.sygic.aura.search.model.holder;

import android.view.View;
import com.sygic.aura.search.model.data.CityPostalSearchItem;
import com.sygic.aura.search.model.data.CityPostalSearchItem.IconType;
import com.sygic.aura.search.model.data.SearchItem;
import loquendo.tts.engine.TTSConst;

public class ViewHolderCity extends ViewHolder {

    /* renamed from: com.sygic.aura.search.model.holder.ViewHolderCity.1 */
    static /* synthetic */ class C15821 {
        static final /* synthetic */ int[] f1278xa624366e;

        static {
            f1278xa624366e = new int[IconType.values().length];
            try {
                f1278xa624366e[IconType.ICON_CITY_CAPITAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1278xa624366e[IconType.ICON_CITY_BIG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1278xa624366e[IconType.ICON_CITY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public ViewHolderCity(View view) {
        super(view);
    }

    public void updateContent(SearchItem item, int position) {
        CityPostalSearchItem searchItem = (CityPostalSearchItem) item;
        if (searchItem != null) {
            this.mTextView.setText(searchItem.getCityName());
            if (searchItem.hasExtName()) {
                this.mExtTextView.setText(searchItem.getExtName());
                this.mExtTextView.setVisibility(0);
            } else {
                this.mExtTextView.setVisibility(8);
            }
            switch (C15821.f1278xa624366e[searchItem.getIcon().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034279));
                    break;
                default:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034278));
                    break;
            }
            this.mIconView.setVisibility(0);
        }
    }
}
