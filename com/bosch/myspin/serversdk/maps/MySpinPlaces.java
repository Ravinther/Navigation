package com.bosch.myspin.serversdk.maps;

import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.ArrayList;

public class MySpinPlaces {
    private static final LogComponent f126a;
    private ArrayList<Object> f127b;

    static {
        f126a = LogComponent.Maps;
    }

    protected MySpinPlaces() {
        this.f127b = new ArrayList();
        MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinPlacesInit()");
    }
}
