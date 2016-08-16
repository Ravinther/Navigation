package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

public final class zzrq {
    private final ByteBuffer zzbbZ;

    public static class zza extends IOException {
        zza(int i, int i2) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
        }
    }

    private zzrq(ByteBuffer byteBuffer) {
        this.zzbbZ = byteBuffer;
    }

    private zzrq(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static zzrq zzA(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public static int zzB(int i, int i2) {
        return zzlv(i) + zzls(i2);
    }

    public static int zzY(long j) {
        return zzab(j);
    }

    private static int zza(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        int i3 = i;
        while (i3 < length) {
            char charAt = charSequence.charAt(i3);
            if (charAt < '\u0800') {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if ('\ud800' <= charAt && charAt <= '\udfff') {
                    if (Character.codePointAt(charSequence, i3) < 65536) {
                        throw new IllegalArgumentException("Unpaired surrogate at index " + i3);
                    }
                    i3++;
                }
            }
            i3++;
        }
        return i2;
    }

    private static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int length = charSequence.length();
        int i3 = 0;
        int i4 = i + i2;
        while (i3 < length && i3 + i < i4) {
            char charAt = charSequence.charAt(i3);
            if (charAt >= '\u0080') {
                break;
            }
            bArr[i + i3] = (byte) charAt;
            i3++;
        }
        if (i3 == length) {
            return i + length;
        }
        int i5 = i + i3;
        while (i3 < length) {
            int i6;
            char charAt2 = charSequence.charAt(i3);
            if (charAt2 < '\u0080' && i5 < i4) {
                i6 = i5 + 1;
                bArr[i5] = (byte) charAt2;
            } else if (charAt2 < '\u0800' && i5 <= i4 - 2) {
                r6 = i5 + 1;
                bArr[i5] = (byte) ((charAt2 >>> 6) | 960);
                i6 = r6 + 1;
                bArr[r6] = (byte) ((charAt2 & 63) | 128);
            } else if ((charAt2 < '\ud800' || '\udfff' < charAt2) && i5 <= i4 - 3) {
                i6 = i5 + 1;
                bArr[i5] = (byte) ((charAt2 >>> 12) | 480);
                i5 = i6 + 1;
                bArr[i6] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i6 = i5 + 1;
                bArr[i5] = (byte) ((charAt2 & 63) | 128);
            } else if (i5 <= i4 - 4) {
                if (i3 + 1 != charSequence.length()) {
                    i3++;
                    charAt = charSequence.charAt(i3);
                    if (Character.isSurrogatePair(charAt2, charAt)) {
                        int toCodePoint = Character.toCodePoint(charAt2, charAt);
                        i6 = i5 + 1;
                        bArr[i5] = (byte) ((toCodePoint >>> 18) | 240);
                        i5 = i6 + 1;
                        bArr[i6] = (byte) (((toCodePoint >>> 12) & 63) | 128);
                        r6 = i5 + 1;
                        bArr[i5] = (byte) (((toCodePoint >>> 6) & 63) | 128);
                        i6 = r6 + 1;
                        bArr[r6] = (byte) ((toCodePoint & 63) | 128);
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + (i3 - 1));
            } else {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i5);
            }
            i3++;
            i5 = i6;
        }
        return i5;
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(zza(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (Throwable e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            zzb(charSequence, byteBuffer);
        }
    }

    public static int zzab(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public static int zzax(boolean z) {
        return 1;
    }

    public static int zzb(int i, zzrx com_google_android_gms_internal_zzrx) {
        return (zzlv(i) * 2) + zzd(com_google_android_gms_internal_zzrx);
    }

    public static zzrq zzb(byte[] bArr, int i, int i2) {
        return new zzrq(bArr, i, i2);
    }

    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < '\u0080') {
                byteBuffer.put((byte) charAt);
            } else if (charAt < '\u0800') {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & 63) | 128));
            } else if (charAt < '\ud800' || '\udfff' < charAt) {
                byteBuffer.put((byte) ((charAt >>> 12) | 480));
                byteBuffer.put((byte) (((charAt >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((charAt & 63) | 128));
            } else {
                if (i + 1 != charSequence.length()) {
                    i++;
                    char charAt2 = charSequence.charAt(i);
                    if (Character.isSurrogatePair(charAt, charAt2)) {
                        int toCodePoint = Character.toCodePoint(charAt, charAt2);
                        byteBuffer.put((byte) ((toCodePoint >>> 18) | 240));
                        byteBuffer.put((byte) (((toCodePoint >>> 12) & 63) | 128));
                        byteBuffer.put((byte) (((toCodePoint >>> 6) & 63) | 128));
                        byteBuffer.put((byte) ((toCodePoint & 63) | 128));
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + (i - 1));
            }
            i++;
        }
    }

    public static int zzc(int i, float f) {
        return zzlv(i) + zzj(f);
    }

    public static int zzc(int i, zzrx com_google_android_gms_internal_zzrx) {
        return zzlv(i) + zze(com_google_android_gms_internal_zzrx);
    }

    public static int zzc(int i, boolean z) {
        return zzlv(i) + zzax(z);
    }

    private static int zzc(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < '\u0080') {
            i++;
        }
        int i2 = i;
        i = length;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (charAt >= '\u0800') {
                i += zza(charSequence, i2);
                break;
            }
            i2++;
            i = ((127 - charAt) >>> 31) + i;
        }
        if (i >= length) {
            return i;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) i) + 4294967296L));
    }

    public static int zzd(int i, long j) {
        return zzlv(i) + zzY(j);
    }

    public static int zzd(zzrx com_google_android_gms_internal_zzrx) {
        return com_google_android_gms_internal_zzrx.zzDx();
    }

    public static int zze(zzrx com_google_android_gms_internal_zzrx) {
        int zzDx = com_google_android_gms_internal_zzrx.zzDx();
        return zzDx + zzlx(zzDx);
    }

    public static int zzfy(String str) {
        int zzc = zzc((CharSequence) str);
        return zzc + zzlx(zzc);
    }

    public static int zzj(float f) {
        return 4;
    }

    public static int zzl(int i, String str) {
        return zzlv(i) + zzfy(str);
    }

    public static int zzls(int i) {
        return i >= 0 ? zzlx(i) : 10;
    }

    public static int zzlv(int i) {
        return zzlx(zzsa.zzE(i, 0));
    }

    public static int zzlx(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public void zzD(int i, int i2) throws IOException {
        zzlw(zzsa.zzE(i, i2));
    }

    public void zzD(byte[] bArr) throws IOException {
        zzc(bArr, 0, bArr.length);
    }

    public int zzDi() {
        return this.zzbbZ.remaining();
    }

    public void zzW(long j) throws IOException {
        zzaa(j);
    }

    public void zza(int i, zzrx com_google_android_gms_internal_zzrx) throws IOException {
        zzD(i, 2);
        zzc(com_google_android_gms_internal_zzrx);
    }

    public void zzaa(long j) throws IOException {
        while ((-128 & j) != 0) {
            zzlu((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzlu((int) j);
    }

    public void zzaw(boolean z) throws IOException {
        zzlu(z ? 1 : 0);
    }

    public void zzb(byte b) throws IOException {
        if (this.zzbbZ.hasRemaining()) {
            this.zzbbZ.put(b);
            return;
        }
        throw new zza(this.zzbbZ.position(), this.zzbbZ.limit());
    }

    public void zzb(int i, float f) throws IOException {
        zzD(i, 5);
        zzi(f);
    }

    public void zzb(int i, long j) throws IOException {
        zzD(i, 0);
        zzW(j);
    }

    public void zzb(int i, String str) throws IOException {
        zzD(i, 2);
        zzfx(str);
    }

    public void zzb(int i, boolean z) throws IOException {
        zzD(i, 0);
        zzaw(z);
    }

    public void zzb(zzrx com_google_android_gms_internal_zzrx) throws IOException {
        com_google_android_gms_internal_zzrx.zza(this);
    }

    public void zzc(zzrx com_google_android_gms_internal_zzrx) throws IOException {
        zzlw(com_google_android_gms_internal_zzrx.zzDw());
        com_google_android_gms_internal_zzrx.zza(this);
    }

    public void zzc(byte[] bArr, int i, int i2) throws IOException {
        if (this.zzbbZ.remaining() >= i2) {
            this.zzbbZ.put(bArr, i, i2);
            return;
        }
        throw new zza(this.zzbbZ.position(), this.zzbbZ.limit());
    }

    public void zzfx(String str) throws IOException {
        try {
            int zzlx = zzlx(str.length());
            if (zzlx == zzlx(str.length() * 3)) {
                int position = this.zzbbZ.position();
                this.zzbbZ.position(position + zzlx);
                zza((CharSequence) str, this.zzbbZ);
                int position2 = this.zzbbZ.position();
                this.zzbbZ.position(position);
                zzlw((position2 - position) - zzlx);
                this.zzbbZ.position(position2);
                return;
            }
            zzlw(zzc((CharSequence) str));
            zza((CharSequence) str, this.zzbbZ);
        } catch (BufferOverflowException e) {
            throw new zza(this.zzbbZ.position(), this.zzbbZ.limit());
        }
    }

    public void zzi(float f) throws IOException {
        zzly(Float.floatToIntBits(f));
    }

    public void zzlq(int i) throws IOException {
        if (i >= 0) {
            zzlw(i);
        } else {
            zzaa((long) i);
        }
    }

    public void zzlu(int i) throws IOException {
        zzb((byte) i);
    }

    public void zzlw(int i) throws IOException {
        while ((i & -128) != 0) {
            zzlu((i & 127) | 128);
            i >>>= 7;
        }
        zzlu(i);
    }

    public void zzly(int i) throws IOException {
        zzlu(i & 255);
        zzlu((i >> 8) & 255);
        zzlu((i >> 16) & 255);
        zzlu((i >> 24) & 255);
    }

    public void zzz(int i, int i2) throws IOException {
        zzD(i, 0);
        zzlq(i2);
    }
}
