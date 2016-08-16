package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.SignpostItem;
import java.util.ArrayList;

public interface SignpostChangeListener extends CoreEventListener {
    void onSignpostChange(ArrayList<SignpostItem> arrayList);
}
