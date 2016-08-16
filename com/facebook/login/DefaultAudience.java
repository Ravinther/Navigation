package com.facebook.login;

public enum DefaultAudience {
    NONE(null),
    ONLY_ME("only_me"),
    FRIENDS("friends"),
    EVERYONE("everyone");
    
    private final String nativeProtocolAudience;

    private DefaultAudience(String protocol) {
        this.nativeProtocolAudience = protocol;
    }

    public String getNativeProtocolAudience() {
        return this.nativeProtocolAudience;
    }
}
