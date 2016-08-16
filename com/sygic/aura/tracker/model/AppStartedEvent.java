package com.sygic.aura.tracker.model;

public class AppStartedEvent extends AppLifecycleEvent {
    protected String getJsonTypeName() {
        return "app_started";
    }
}
