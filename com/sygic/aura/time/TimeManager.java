package com.sygic.aura.time;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;

public class TimeManager {

    /* renamed from: com.sygic.aura.time.TimeManager.4 */
    static class C17514 implements Callback<Long> {
        C17514() {
        }

        public Long getMethod() {
            return Long.valueOf(TimeManager.TimeGetCurrentTime());
        }
    }

    private static native long TimeGetCurrentTime();

    public static long nativeTimeGetCurrentTime() {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Long) new ObjectHandler(new C17514()).execute().get(Long.valueOf(0))).longValue();
    }
}
