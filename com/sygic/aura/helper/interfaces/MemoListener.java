package com.sygic.aura.helper.interfaces;

import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface MemoListener extends CoreEventListener {
    void onAddHome(LongPosition longPosition, String str);

    void onAddParkingLocation(LongPosition longPosition, String str);

    void onAddWork(LongPosition longPosition, String str);

    void onMemoRemoved(Integer num);
}
