package com.sygic.aura.search.fragment;

import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.model.data.QuickSearchItem;

public interface QuickSearchFragmentResultCallback extends FragmentResultCallback {
    void onQuickSearchFragmentResult(MapSelection mapSelection);

    void onQuickSearchFragmentResult(QuickSearchItem quickSearchItem);
}
