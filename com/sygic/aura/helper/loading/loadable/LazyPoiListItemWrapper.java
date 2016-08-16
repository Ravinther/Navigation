package com.sygic.aura.helper.loading.loadable;

import android.view.View;
import android.widget.TextView;
import com.sygic.aura.helper.loading.LazyLoading.LazyLoadable;
import com.sygic.aura.poi.PoiManager;
import com.sygic.aura.search.model.data.PoiListItem;
import java.util.HashMap;

public class LazyPoiListItemWrapper implements LazyLoadable {
    private static HashMap<Long, String> sAddressCache;
    private final PoiListItem mItem;

    static {
        sAddressCache = new HashMap();
    }

    public static LazyPoiListItemWrapper of(PoiListItem sourceItem) {
        return new LazyPoiListItemWrapper(sourceItem);
    }

    private LazyPoiListItemWrapper(PoiListItem item) {
        this.mItem = item;
    }

    public Object execute() {
        return PoiManager.nativeGetPoiDescription(this.mItem.getLongPosition());
    }

    public long getIdentifier() {
        return (long) this.mItem.hashCode();
    }

    public <R extends View> void setResult(Object result, R receiver) {
        if ((result instanceof String) && (receiver instanceof TextView)) {
            sAddressCache.put(Long.valueOf(this.mItem.getPoiId()), (String) result);
            synchronized (this) {
                if (receiver.getTag() != null && getIdentifier() == ((Long) receiver.getTag()).longValue()) {
                    ((TextView) receiver).setText((String) result);
                    receiver.setVisibility(0);
                }
            }
        }
    }

    public static String getCachedAddress(long poiId) {
        return (String) sAddressCache.get(Long.valueOf(poiId));
    }

    public static void clearCache() {
        sAddressCache.clear();
    }
}
