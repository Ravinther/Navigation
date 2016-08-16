package com.bosch.myspin.serversdk.service.client;

import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.service.client.u */
public class C0210u {
    private float f277a;
    private float f278b;
    private int f279c;
    private int f280d;
    private long f281e;
    private PointerProperties f282f;

    public C0210u(int i, int i2, float f, float f2, long j) {
        this.f280d = i;
        this.f279c = i2;
        this.f277a = f;
        this.f278b = f2;
        this.f281e = j;
        m294e();
    }

    private void m294e() {
        this.f282f = new PointerProperties();
        this.f282f.toolType = 1;
        this.f282f.id = 0;
    }

    public PointerCoords m295a() {
        PointerCoords pointerCoords = new PointerCoords();
        pointerCoords.pressure = 0.5f;
        pointerCoords.size = 0.5f;
        pointerCoords.x = this.f277a;
        pointerCoords.y = this.f278b;
        return pointerCoords;
    }

    public int m296b() {
        return this.f279c;
    }

    public int m297c() {
        return this.f280d;
    }

    public String m298d() {
        switch (this.f279c) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "[ACTION_DOWN]";
            case TTSConst.TTSMULTILINE /*1*/:
                return "[ACTION_UP]";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "[ACTION_MOVE]";
            case TTSConst.TTSUNICODE /*3*/:
                return "[ACTION_CANCEL]";
            case TTSConst.TTSXML /*4*/:
                return "[ACTION_OUTSIDE]";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "[ACTION_POINTER_DOWN]";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "[ACTION_POINTER_UP]";
            default:
                return "[Untracked state!] " + this.f279c;
        }
    }
}
