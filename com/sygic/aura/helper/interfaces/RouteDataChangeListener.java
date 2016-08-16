package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchItem;
import java.util.ArrayList;

public interface RouteDataChangeListener extends CoreEventListener {
    void onFinishChanged(ArrayList<SearchItem> arrayList);

    void onWaypointInserted(Integer num, ArrayList<ListItem> arrayList);

    void onWaypointRemoved(Integer num);
}
