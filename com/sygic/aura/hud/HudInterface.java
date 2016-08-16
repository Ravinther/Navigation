package com.sygic.aura.hud;

public interface HudInterface {
    void send();

    void updateValue(String str, int i);

    void updateValue(String str, String str2);

    void updateValue(String str, int[] iArr);

    void updateValue(String str, long[] jArr);
}
