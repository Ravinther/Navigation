package com.flurry.sdk;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;
import loquendo.tts.engine.TTSConst;

public class la {
    public static boolean m1240a() {
        return ((KeyguardManager) jc.m946a().m957c().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    @SuppressLint({"NewApi"})
    public static Point m1242b() {
        Display defaultDisplay = ((WindowManager) jc.m946a().m957c().getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else if (VERSION.SDK_INT >= 14) {
            try {
                Method method = Display.class.getMethod("getRawHeight", new Class[0]);
                point.x = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                point.y = ((Integer) method.invoke(defaultDisplay, new Object[0])).intValue();
            } catch (Throwable th) {
                defaultDisplay.getSize(point);
            }
        } else if (VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(point);
        } else {
            point.x = defaultDisplay.getWidth();
            point.y = defaultDisplay.getHeight();
        }
        return point;
    }

    public static DisplayMetrics m1243c() {
        Display defaultDisplay = ((WindowManager) jc.m946a().m957c().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

    @SuppressLint({"NewApi"})
    public static DisplayMetrics m1245d() {
        Display defaultDisplay = ((WindowManager) jc.m946a().m957c().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics;
        if (VERSION.SDK_INT >= 17) {
            displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            return displayMetrics;
        } else if (VERSION.SDK_INT < 14) {
            return m1243c();
        } else {
            try {
                displayMetrics = new DisplayMetrics();
                Display.class.getMethod("getRealMetrics", new Class[0]).invoke(defaultDisplay, new Object[]{displayMetrics});
                return displayMetrics;
            } catch (Exception e) {
                return m1243c();
            }
        }
    }

    public static float m1246e() {
        return m1245d().density;
    }

    public static int m1239a(int i) {
        return Math.round(((float) i) / m1245d().density);
    }

    public static int m1241b(int i) {
        return Math.round(m1245d().density * ((float) i));
    }

    public static int m1247f() {
        return m1242b().x;
    }

    public static int m1248g() {
        return m1242b().y;
    }

    public static int m1249h() {
        return m1239a(m1247f());
    }

    public static int m1250i() {
        return m1239a(m1248g());
    }

    public static int m1251j() {
        Point b = m1242b();
        if (b.x == b.y) {
            return 3;
        }
        if (b.x < b.y) {
            return 1;
        }
        return 2;
    }

    public static Pair<Integer, Integer> m1252k() {
        return Pair.create(Integer.valueOf(m1249h()), Integer.valueOf(m1250i()));
    }

    public static Pair<Integer, Integer> m1244c(int i) {
        int h = m1249h();
        int i2 = m1250i();
        switch (i) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                return Pair.create(Integer.valueOf(i2), Integer.valueOf(h));
            default:
                return Pair.create(Integer.valueOf(h), Integer.valueOf(i2));
        }
    }
}
