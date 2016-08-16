package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.EOFException;
import java.io.IOException;

public class SequentialByteArrayReader extends SequentialReader {
    private final byte[] _bytes;
    private int _index;

    public SequentialByteArrayReader(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException();
        }
        this._bytes = bytes;
        this._index = 0;
    }

    protected byte getByte() throws IOException {
        if (this._index >= this._bytes.length) {
            throw new EOFException("End of data reached.");
        }
        byte[] bArr = this._bytes;
        int i = this._index;
        this._index = i + 1;
        return bArr[i];
    }

    public byte[] getBytes(int count) throws IOException {
        if (this._index + count > this._bytes.length) {
            throw new EOFException("End of data reached.");
        }
        byte[] bytes = new byte[count];
        System.arraycopy(this._bytes, this._index, bytes, 0, count);
        this._index += count;
        return bytes;
    }

    public void skip(long n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("n must be zero or greater.");
        } else if (((long) this._index) + n > ((long) this._bytes.length)) {
            throw new EOFException("End of data reached.");
        } else {
            this._index = (int) (((long) this._index) + n);
        }
    }

    public boolean trySkip(long n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("n must be zero or greater.");
        }
        this._index = (int) (((long) this._index) + n);
        if (this._index <= this._bytes.length) {
            return true;
        }
        this._index = this._bytes.length;
        return false;
    }
}
