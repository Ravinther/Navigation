package com.flurry.android;

public enum FlurrySyndicationEventName {
    REBLOG("Reblog"),
    FAST_REBLOG("FastReblog"),
    SOURCE_LINK("SourceClick"),
    LIKE("Like");
    
    private String f537a;

    private FlurrySyndicationEventName(String str) {
        this.f537a = str;
    }

    public String toString() {
        return this.f537a;
    }
}
