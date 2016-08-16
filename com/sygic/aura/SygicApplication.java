package com.sygic.aura;

import android.app.Application;

public class SygicApplication extends Application {
    static {
        SygicProject.setConsts();
    }
}
