package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface AutoCloseListener extends CoreEventListener {
    public static final AutoCloseListener DUMMY_LISTENER;

    /* renamed from: com.sygic.aura.helper.interfaces.AutoCloseListener.1 */
    static class C12801 implements AutoCloseListener {
        C12801() {
        }

        public void onAutoClose(int nTick, boolean bInMove) {
        }
    }

    void onAutoClose(int i, boolean z);

    static {
        DUMMY_LISTENER = new C12801();
    }
}
