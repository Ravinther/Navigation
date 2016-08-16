package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.IOException;

public abstract class RandomAccessReader {
    private boolean _isMotorolaByteOrder;

    protected abstract byte getByte(int i) throws IOException;

    public abstract byte[] getBytes(int i, int i2) throws IOException;

    public abstract long getLength() throws IOException;

    protected abstract boolean isValidIndex(int i, int i2) throws IOException;

    protected abstract void validateIndex(int i, int i2) throws IOException;

    public RandomAccessReader() {
        this._isMotorolaByteOrder = true;
    }

    public void setMotorolaByteOrder(boolean motorolaByteOrder) {
        this._isMotorolaByteOrder = motorolaByteOrder;
    }

    public short getUInt8(int index) throws IOException {
        validateIndex(index, 1);
        return (short) (getByte(index) & 255);
    }

    public byte getInt8(int index) throws IOException {
        validateIndex(index, 1);
        return getByte(index);
    }

    public int getUInt16(int index) throws IOException {
        validateIndex(index, 2);
        if (this._isMotorolaByteOrder) {
            return ((getByte(index) << 8) & 65280) | (getByte(index + 1) & 255);
        }
        return ((getByte(index + 1) << 8) & 65280) | (getByte(index) & 255);
    }

    public short getInt16(int index) throws IOException {
        validateIndex(index, 2);
        if (this._isMotorolaByteOrder) {
            return (short) (((((short) getByte(index)) << 8) & -256) | (((short) getByte(index + 1)) & 255));
        }
        return (short) (((((short) getByte(index + 1)) << 8) & -256) | (((short) getByte(index)) & 255));
    }

    public long getUInt32(int index) throws IOException {
        validateIndex(index, 4);
        if (this._isMotorolaByteOrder) {
            return ((((((long) getByte(index)) << 24) & 4278190080L) | ((((long) getByte(index + 1)) << 16) & 16711680)) | ((((long) getByte(index + 2)) << 8) & 65280)) | (((long) getByte(index + 3)) & 255);
        }
        return ((((((long) getByte(index + 3)) << 24) & 4278190080L) | ((((long) getByte(index + 2)) << 16) & 16711680)) | ((((long) getByte(index + 1)) << 8) & 65280)) | (((long) getByte(index)) & 255);
    }

    public int getInt32(int index) throws IOException {
        validateIndex(index, 4);
        if (this._isMotorolaByteOrder) {
            return ((((getByte(index) << 24) & -16777216) | ((getByte(index + 1) << 16) & 16711680)) | ((getByte(index + 2) << 8) & 65280)) | (getByte(index + 3) & 255);
        }
        return ((((getByte(index + 3) << 24) & -16777216) | ((getByte(index + 2) << 16) & 16711680)) | ((getByte(index + 1) << 8) & 65280)) | (getByte(index) & 255);
    }

    public long getInt64(int index) throws IOException {
        validateIndex(index, 8);
        if (this._isMotorolaByteOrder) {
            return ((((((((((long) getByte(index)) << 56) & -72057594037927936L) | ((((long) getByte(index + 1)) << 48) & 71776119061217280L)) | ((((long) getByte(index + 2)) << 40) & 280375465082880L)) | ((((long) getByte(index + 3)) << 32) & 1095216660480L)) | ((((long) getByte(index + 4)) << 24) & 4278190080L)) | ((((long) getByte(index + 5)) << 16) & 16711680)) | ((((long) getByte(index + 6)) << 8) & 65280)) | (((long) getByte(index + 7)) & 255);
        }
        return ((((((((((long) getByte(index + 7)) << 56) & -72057594037927936L) | ((((long) getByte(index + 6)) << 48) & 71776119061217280L)) | ((((long) getByte(index + 5)) << 40) & 280375465082880L)) | ((((long) getByte(index + 4)) << 32) & 1095216660480L)) | ((((long) getByte(index + 3)) << 24) & 4278190080L)) | ((((long) getByte(index + 2)) << 16) & 16711680)) | ((((long) getByte(index + 1)) << 8) & 65280)) | (((long) getByte(index)) & 255);
    }

    public float getFloat32(int index) throws IOException {
        return Float.intBitsToFloat(getInt32(index));
    }

    public double getDouble64(int index) throws IOException {
        return Double.longBitsToDouble(getInt64(index));
    }

    public String getString(int index, int bytesRequested) throws IOException {
        return new String(getBytes(index, bytesRequested));
    }

    public String getNullTerminatedString(int index, int maxLengthBytes) throws IOException {
        byte[] bytes = getBytes(index, maxLengthBytes);
        int length = 0;
        while (length < bytes.length && bytes[length] != null) {
            length++;
        }
        return new String(bytes, 0, length);
    }
}
