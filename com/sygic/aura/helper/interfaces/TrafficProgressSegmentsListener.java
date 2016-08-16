package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import java.util.ArrayList;

public interface TrafficProgressSegmentsListener extends CoreEventListener {
    void onTrafficSegmentsComputed(ArrayList<Float> arrayList, ArrayList<Integer> arrayList2);
}
