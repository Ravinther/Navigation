package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.poi.OnlinePoiInfoEntry;

public interface PoiDetailInfoListener extends CoreEventListener {
    void onUpdateOnlineInfo(OnlinePoiInfoEntry onlinePoiInfoEntry);
}
