package com.flurry.sdk;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public class ji extends MessageDigest {
    private CRC32 f922a;

    public ji() {
        super("CRC");
        this.f922a = new CRC32();
    }

    protected void engineReset() {
        this.f922a.reset();
    }

    protected void engineUpdate(byte b) {
        this.f922a.update(b);
    }

    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.f922a.update(bArr, i, i2);
    }

    protected byte[] engineDigest() {
        long value = this.f922a.getValue();
        return new byte[]{(byte) ((int) ((-16777216 & value) >> 24)), (byte) ((int) ((16711680 & value) >> 16)), (byte) ((int) ((65280 & value) >> 8)), (byte) ((int) ((value & 255) >> 0))};
    }

    public byte[] m991a() {
        return engineDigest();
    }

    public int m992b() {
        return ByteBuffer.wrap(engineDigest()).getInt();
    }
}
