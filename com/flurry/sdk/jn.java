package com.flurry.sdk;

import java.util.Comparator;

public class jn implements Comparator<Runnable> {
    private static final String f933a;

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return m1008a((Runnable) obj, (Runnable) obj2);
    }

    static {
        f933a = jn.class.getSimpleName();
    }

    public int m1008a(Runnable runnable, Runnable runnable2) {
        int a = m1007a(runnable);
        int a2 = m1007a(runnable2);
        if (a < a2) {
            return -1;
        }
        if (a > a2) {
            return 1;
        }
        return 0;
    }

    private int m1007a(Runnable runnable) {
        if (runnable == null) {
            return Integer.MAX_VALUE;
        }
        if (runnable instanceof jo) {
            int j;
            lf lfVar = (lf) ((jo) runnable).m1009a();
            if (lfVar != null) {
                j = lfVar.m1057j();
            } else {
                j = Integer.MAX_VALUE;
            }
            return j;
        } else if (runnable instanceof lf) {
            return ((lf) runnable).m1057j();
        } else {
            jq.m1016a(6, f933a, "Unknown runnable class: " + runnable.getClass().getName());
            return Integer.MAX_VALUE;
        }
    }
}
