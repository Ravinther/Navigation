package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.IOException;

public class ByteArrayReader extends RandomAccessReader {
    private final byte[] _buffer;

    public ByteArrayReader(byte[] buffer) {
        if (buffer == null) {
            throw new NullPointerException();
        }
        this._buffer = buffer;
    }

    public long getLength() {
        return (long) this._buffer.length;
    }

    protected byte getByte(int index) throws IOException {
        return this._buffer[index];
    }

    protected void validateIndex(int index, int bytesRequested) throws IOException {
        if (!isValidIndex(index, bytesRequested)) {
            throw new BufferBoundsException(index, bytesRequested, (long) this._buffer.length);
        }
    }

    protected boolean isValidIndex(int index, int bytesRequested) throws IOException {
        return bytesRequested >= 0 && index >= 0 && (((long) index) + ((long) bytesRequested)) - 1 < ((long) this._buffer.length);
    }

    public byte[] getBytes(int index, int count) throws IOException {
        validateIndex(index, count);
        byte[] bytes = new byte[count];
        System.arraycopy(this._buffer, index, bytes, 0, count);
        return bytes;
    }
}
