package com.sygic.aura.analytics;

public interface AnalyticsInterface {

    public enum EventType {
        FRW,
        WEBVIEW,
        IN_APP_PURCHASE,
        CLICK,
        WIDGET_START_NAVI,
        WIDGET_LIFECYCLE_ACTION,
        WIDGET_TRAFFIC_REQUEST,
        CORE,
        UNLOCK,
        QUICK_MENU,
        QUICK_MENU_FLURRY,
        ACTIVATION,
        SCOUT_COMPUTE,
        EVENT_NAME_PURCHASED,
        EVENT_NAME_ADDED_TO_CART,
        EVENT_NAME_ADDED_TO_WISHLIST,
        WHATS_NEW,
        LIVE_SERVICES,
        NOTIFICATION,
        BOOKING,
        SEARCH,
        NOTIFICATION_CENTER,
        OFFER_PARKING,
        SETTINGS_CHANGE,
        SETTINGS_CATEGORY,
        SETTINGS_POI,
        STORE_PAYMENT
    }
}
