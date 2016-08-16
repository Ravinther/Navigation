package com.sygic.aura.feature.common;

/* compiled from: TextWatcherDummy */
class Keys {
    private int m_nCount;

    Keys() {
        this.m_nCount = 0;
    }

    public void add() {
        this.m_nCount++;
    }

    public void del() {
        if (this.m_nCount > 0) {
            this.m_nCount--;
        }
    }

    public int count() {
        return this.m_nCount;
    }

    public void reset() {
        this.m_nCount = 0;
    }
}
