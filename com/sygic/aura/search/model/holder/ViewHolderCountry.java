package com.sygic.aura.search.model.holder;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import com.sygic.aura.search.model.data.CountrySearchItem;
import com.sygic.aura.search.model.data.SearchItem;
import java.util.Locale;

public class ViewHolderCountry extends ViewHolder {
    public ViewHolderCountry(View view) {
        super(view);
    }

    public void updateContent(SearchItem item, int position) {
        CountrySearchItem searchItem = (CountrySearchItem) item;
        if (searchItem != null) {
            String strIso = searchItem.getIso();
            if (!TextUtils.isEmpty(strIso)) {
                Resources resources = this.mContext.getResources();
                int iconId = resources.getIdentifier("flg_" + strIso.toLowerCase(Locale.ENGLISH), "drawable", this.mContext.getPackageName());
                this.mIconView.setImageDrawable(iconId == 0 ? null : resources.getDrawable(iconId));
            }
            this.mTextView.setText(searchItem.getCountryName());
            this.mIconView.setVisibility(0);
            this.mExtTextView.setVisibility(8);
        }
    }
}
