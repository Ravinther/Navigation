package com.bosch.myspin.serversdk.service.client;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.view.View;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.ArrayList;
import java.util.Iterator;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.service.client.w */
class C0212w {
    private static final LogComponent f285a;
    private int f286b;
    private long f287c;
    private long f288d;
    private C0215x f289e;
    private float f290f;
    private PointerCoords[] f291g;
    private PointerProperties[] f292h;
    private ArrayList<Integer> f293i;
    private C0211v f294j;

    static {
        f285a = LogComponent.TouchInjection;
    }

    C0212w(float f) {
        this.f290f = 1.0f;
        this.f293i = new ArrayList();
        this.f294j = new C0211v();
        this.f289e = C0215x.m320a();
        this.f290f = f;
        m313d();
    }

    void m318a(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i) {
        if (this.f289e.f298b.size() != 0) {
            if (iArr == null || iArr2 == null || iArr3 == null || iArr4 == null) {
                Logger.logWarning(f285a, "TouchHandler/Parameters must not be null!");
            } else if (iArr.length < i || iArr2.length < i || iArr3.length < i || iArr4.length < i) {
                Logger.logWarning(f285a, "TouchHandler/Parameters must not be emtpy!");
            } else if (i > 10) {
                Logger.logWarning(f285a, "TouchHandler/Number of touches exceeded!");
            } else {
                for (int i2 = 0; i2 < i; i2++) {
                    m305a(iArr4[i2], iArr[i2], iArr2[i2], iArr3[i2]);
                }
                m304a(this.f294j.m301b());
            }
        }
    }

    private void m304a(int i) {
        int i2;
        int[] c = m311c();
        m306a(i, c);
        m309b(i);
        this.f288d += 10;
        Iterator it = this.f294j.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            int max;
            C0210u c0210u = (C0210u) it.next();
            i2 = -1;
            int i4;
            switch (c0210u.m296b()) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    m314e(this.f286b);
                    if (this.f286b != 1) {
                        max = (Math.max(c[i3], 1) << 8) | 5;
                        break;
                    }
                    this.f287c = SystemClock.uptimeMillis();
                    this.f288d = this.f287c;
                    max = 0;
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    if (this.f286b <= 1) {
                        max = 1;
                        break;
                    }
                    i4 = this.f286b - 1;
                    if (i4 >= 0 && i4 < 10) {
                        i2 = (Math.max(i4, 1) << 8) | 6;
                    }
                    max = i2;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    max = 2;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    if (this.f286b <= 1) {
                        max = 3;
                        break;
                    }
                    i4 = c[i3];
                    if (i4 >= 0 && i4 < 10) {
                        i2 = (Math.max(i4, 1) << 8) | 3;
                    }
                    max = i2;
                    break;
                default:
                    Logger.logWarning(f285a, "TouchHandler/Unknow touch type: " + c0210u.m296b());
                    max = -1;
                    break;
            }
            PointerCoords g = m316g(c[i3]);
            if (g != null) {
                g.x = c0210u.m295a().x * this.f290f;
                g.y = c0210u.m295a().y * this.f290f;
            }
            if (max > -1 && this.f286b > 0) {
                if (this.f286b > 10) {
                    this.f286b = 10;
                }
                MotionEvent obtain = MotionEvent.obtain(this.f287c, this.f288d, max, this.f286b, this.f292h, this.f291g, 0, 0, 1.0f, 1.0f, 0, 0, 0, 0);
                m307a(obtain);
                obtain.recycle();
            }
            if (c0210u.m296b() == 1 || c0210u.m296b() == 3) {
                this.f293i.remove(Integer.valueOf(c0210u.m297c()));
                m315f(this.f286b - 1);
            }
            i3++;
        }
        Iterator it2 = this.f294j.iterator();
        while (it2.hasNext()) {
            C0210u c0210u2 = (C0210u) it2.next();
            Object obj;
            if (c0210u2.m296b() != 1 && c0210u2.m296b() != 3) {
                obj = null;
                this.f294j.m299a();
                if (obj != null) {
                    m313d();
                }
            }
        }
        i2 = 1;
        this.f294j.m299a();
        if (obj != null) {
            m313d();
        }
    }

    private void m307a(MotionEvent motionEvent) {
        int b = m308b();
        View view = null;
        if (b >= 0) {
            view = (View) this.f289e.f298b.get(b);
        }
        if (view == null) {
            Logger.logWarning(f285a, "TouchHandler/TopMostView is null!");
            return;
        }
        try {
            if ("myspin:dialog".equals(view.getTag())) {
                view.dispatchTouchEvent(motionEvent);
                view = (View) this.f289e.f298b.get(b - 1);
                return;
            }
            while (!view.dispatchTouchEvent(motionEvent) && b > 0) {
                b--;
                view = (View) this.f289e.f298b.get(b);
            }
        } catch (IllegalArgumentException e) {
            Logger.logWarning(f285a, "TouchHandler/pointer index out ouf range");
        } catch (IndexOutOfBoundsException e2) {
            Logger.logWarning(f285a, "TouchHandler/pointer index out ouf bounds");
        }
    }

    private void m309b(int i) {
        C0210u c = this.f294j.m302c();
        if (c != null) {
            int b = m308b();
            View view = null;
            if (b >= 0) {
                view = (View) this.f289e.f298b.get(b);
            }
            if (c.m295a().y * this.f290f > ((float) view.getHeight()) * 1.25f && i == 1) {
                this.f291g[0].y = 0.0f;
                this.f291g[0].x = c.m295a().x * this.f290f;
                MotionEvent obtain = MotionEvent.obtain(this.f287c, SystemClock.uptimeMillis(), 1, 1, this.f292h, this.f291g, 0, 0, 1.0f, 1.0f, 0, 0, 0, 0);
                m307a(obtain);
                obtain.recycle();
            }
        }
    }

    private int m308b() {
        if (!(this.f289e == null || this.f289e.f298b == null || this.f289e.f298b.size() <= 0)) {
            for (int size = this.f289e.f298b.size() - 1; size >= 0; size--) {
                if (((View) this.f289e.f298b.get(size)).getVisibility() == 0) {
                    return size;
                }
            }
        }
        return -1;
    }

    private int[] m311c() {
        int[] iArr = new int[10];
        Iterator it = this.f294j.iterator();
        int i = 0;
        while (it.hasNext()) {
            C0210u c0210u = (C0210u) it.next();
            iArr[i] = this.f293i.indexOf(Integer.valueOf(c0210u.m297c()));
            if (iArr[i] == -1) {
                iArr[i] = Math.max(this.f286b, i);
                if (c0210u.m296b() == 0) {
                    this.f293i.add(Math.min(iArr[i], this.f293i.size()), Integer.valueOf(c0210u.m297c()));
                    iArr[i] = this.f293i.indexOf(Integer.valueOf(c0210u.m297c()));
                }
            }
            i++;
        }
        return iArr;
    }

    private void m306a(int i, int[] iArr) {
        if (this.f286b > 10) {
            this.f286b = 10;
        }
        if (i > iArr.length) {
            i = iArr.length;
        }
        for (int i2 = this.f286b - 1; i2 >= 0; i2--) {
            Object obj;
            int i3 = this.f292h[i2].id;
            for (int i4 = 0; i4 < i; i4++) {
                if (i3 == iArr[i4]) {
                    obj = 1;
                    break;
                }
            }
            obj = null;
            if (obj == null) {
                m310c(i2);
            }
        }
    }

    private void m310c(int i) {
        if (i < 10 && this.f286b <= 10) {
            if (i >= 0 && this.f286b > 1) {
                this.f292h[i].id = this.f292h[this.f286b - 1].id;
                this.f291g[i].x = this.f291g[this.f286b - 1].x;
                this.f291g[i].y = this.f291g[this.f286b - 1].y;
            }
            if (this.f286b > 0) {
                this.f286b--;
            }
        }
    }

    private int m312d(int i) {
        if (this.f286b > 10) {
            this.f286b = 10;
        }
        for (int i2 = 0; i2 < this.f286b; i2++) {
            if (this.f292h[i2].id == i) {
                return i2;
            }
        }
        return -1;
    }

    private void m314e(int i) {
        if (this.f286b < 10 && m312d(i) < 0) {
            this.f292h[this.f286b].id = i;
            this.f286b++;
        }
    }

    private void m315f(int i) {
        int d = m312d(i);
        if (d >= 0) {
            m310c(d);
        }
    }

    private PointerCoords m316g(int i) {
        int d = m312d(i);
        if (d < 0 || d >= 10) {
            return null;
        }
        return this.f291g[d];
    }

    private void m313d() {
        int i;
        this.f291g = new PointerCoords[10];
        for (i = 0; i < 10; i++) {
            PointerCoords pointerCoords = new PointerCoords();
            pointerCoords.pressure = 0.5f;
            pointerCoords.size = 0.5f;
            this.f291g[i] = pointerCoords;
        }
        this.f292h = new PointerProperties[10];
        for (i = 0; i < 10; i++) {
            PointerProperties pointerProperties = new PointerProperties();
            pointerProperties.toolType = 1;
            pointerProperties.id = 0;
            this.f292h[i] = pointerProperties;
        }
    }

    private void m305a(int i, int i2, int i3, int i4) {
        this.f294j.m300a(new C0210u(i, i2, (float) i3, (float) i4, SystemClock.uptimeMillis()));
    }

    public void m317a() {
        this.f294j.m303d();
    }
}
