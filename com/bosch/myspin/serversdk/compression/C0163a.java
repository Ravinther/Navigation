package com.bosch.myspin.serversdk.compression;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.bosch.myspin.serversdk.service.sharedmemory.C0217b;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.compression.a */
public class C0163a {
    private static final LogComponent f74a;
    private int f75b;
    private int f76c;
    private int f77d;
    private ByteArrayOutputStream f78e;
    private C0162a f79f;

    /* renamed from: com.bosch.myspin.serversdk.compression.a.a */
    public interface C0162a {
        int m87a(Bitmap bitmap, long j, int i, int i2, int i3, int i4, int i5);
    }

    static {
        f74a = LogComponent.ScreenCapturing;
    }

    public C0163a(C0162a c0162a, int i, int i2, int i3, int i4, int i5) {
        m90a(i, i2);
        m93d(i3);
        m92c(i4);
        m91b(i5);
        if (c0162a == null) {
            throw new IllegalArgumentException("compressionHandler has not to be null");
        }
        this.f79f = c0162a;
        m95a(i3);
        this.f76c = i4;
        this.f77d = i5;
    }

    public void m95a(int i) {
        m93d(i);
        if (i == 1) {
            if (this.f78e == null) {
                this.f78e = new ByteArrayOutputStream();
            }
        } else if (this.f75b == 1) {
            if (this.f78e != null) {
                try {
                    this.f78e.close();
                } catch (Throwable e) {
                    Logger.logError(f74a, "BitmapCompressor/JPEG compression failed: ", e);
                }
            }
            this.f78e = null;
        }
        this.f75b = i;
    }

    public int m94a(Bitmap bitmap, C0217b c0217b) throws IOException {
        if (bitmap == null || c0217b == null) {
            throw new IllegalArgumentException("Argument frame or memoryFile has not to be null");
        }
        switch (this.f75b) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                return this.f79f.m87a(bitmap, c0217b.m331a(), c0217b.m332b(), 0, this.f75b, this.f76c, this.f77d);
            case TTSConst.TTSMULTILINE /*1*/:
                bitmap.compress(CompressFormat.JPEG, 80, this.f78e);
                int size = this.f78e.size();
                this.f78e.writeTo(c0217b.getOutputStream());
                this.f78e.reset();
                return size;
            default:
                return 0;
        }
    }

    private void m90a(int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("Arguments width or height have not to be lesser 1");
        }
    }

    private void m91b(int i) {
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
            default:
                throw new IllegalArgumentException("Supplied pixel endianess type not supported");
        }
    }

    private void m92c(int i) {
        switch (i) {
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
            case TTSConst.TTSXML /*4*/:
            default:
                throw new IllegalArgumentException("Supplied pixel format not supported");
        }
    }

    private void m93d(int i) {
        switch (i) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
            default:
                throw new IllegalArgumentException("Supplied compression type not supported");
        }
    }
}
