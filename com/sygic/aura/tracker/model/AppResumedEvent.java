package com.sygic.aura.tracker.model;

public class AppResumedEvent extends AppLifecycleEvent {
    protected String getJsonTypeName() {
        return "app_resumed";
    }
}
