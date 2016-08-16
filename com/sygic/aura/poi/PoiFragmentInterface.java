package com.sygic.aura.poi;

import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.views.font_specials.SToolbar;

public interface PoiFragmentInterface {
    void finish(PoiListItem poiListItem);

    String getSelectedLanguage();

    SToolbar getToolbar();

    void onQueryChanged(CharSequence charSequence);
}
