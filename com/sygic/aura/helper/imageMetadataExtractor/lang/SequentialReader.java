package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class SequentialReader {
    private boolean _isMotorolaByteOrder;

    protected abstract byte getByte() throws IOException;

    public abstract byte[] getBytes(int i) throws IOException;

    public abstract void skip(long j) throws IOException;

    public abstract boolean trySkip(long j) throws IOException;

    public SequentialReader() {
        this._isMotorolaByteOrder = true;
    }

    public boolean isMotorolaByteOrder() {
        return this._isMotorolaByteOrder;
    }

    public short getUInt8() throws IOException {
        return (short) (getByte() & 255);
    }

    public byte getInt8() throws IOException {
        return getByte();
    }

    public int getUInt16() throws IOException {
        if (this._isMotorolaByteOrder) {
            return ((getByte() << 8) & 65280) | (getByte() & 255);
        }
        return (getByte() & 255) | ((getByte() << 8) & 65280);
    }

    public String getString(int bytesRequested) throws IOException {
        return new String(getBytes(bytesRequested));
    }

    public String getString(int bytesRequested, String charset) throws IOException {
        byte[] bytes = getBytes(bytesRequested);
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            return new String(bytes);
        }
    }
}
