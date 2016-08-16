package com.sygic.aura.search.model.data;

import android.view.View;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.holder.ViewHolder;
import com.sygic.aura.search.model.holder.ViewHolderCountry;

public class CountrySearchItem extends SearchItem {
    public CountrySearchItem(String strIso, String strName) {
        super(strIso, strName);
    }

    public String getCountryName() {
        return getDisplayName();
    }

    public SearchType getSearchType() {
        return SearchType.COUNTRY;
    }

    public ViewHolder newViewHolderInstance(View view) {
        return new ViewHolderCountry(view);
    }

    public String toString() {
        return getIso().concat(": ").concat(getCountryName());
    }
}
