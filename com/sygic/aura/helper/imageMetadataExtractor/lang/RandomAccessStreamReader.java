package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RandomAccessStreamReader extends RandomAccessReader {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final int _chunkLength;
    private final ArrayList<byte[]> _chunks;
    private boolean _isStreamFinished;
    private final InputStream _stream;
    private int _streamLength;

    static {
        $assertionsDisabled = !RandomAccessStreamReader.class.desiredAssertionStatus();
    }

    public RandomAccessStreamReader(InputStream stream) {
        this(stream, 2048);
    }

    public RandomAccessStreamReader(InputStream stream, int chunkLength) {
        this._chunks = new ArrayList();
        if (stream == null) {
            throw new NullPointerException();
        } else if (chunkLength <= 0) {
            throw new IllegalArgumentException("chunkLength must be greater than zero");
        } else {
            this._chunkLength = chunkLength;
            this._stream = stream;
        }
    }

    public long getLength() throws IOException {
        isValidIndex(Integer.MAX_VALUE, 1);
        if ($assertionsDisabled || this._isStreamFinished) {
            return (long) this._streamLength;
        }
        throw new AssertionError();
    }

    protected void validateIndex(int index, int bytesRequested) throws IOException {
        if (index < 0) {
            throw new BufferBoundsException(String.format("Attempt to read from buffer using a negative index (%d)", new Object[]{Integer.valueOf(index)}));
        } else if (bytesRequested < 0) {
            throw new BufferBoundsException("Number of requested bytes must be zero or greater");
        } else if ((((long) index) + ((long) bytesRequested)) - 1 > 2147483647L) {
            throw new BufferBoundsException(String.format("Number of requested bytes summed with starting index exceed maximum range of signed 32 bit integers (requested index: %d, requested count: %d)", new Object[]{Integer.valueOf(index), Integer.valueOf(bytesRequested)}));
        } else if (!isValidIndex(index, bytesRequested)) {
            if ($assertionsDisabled || this._isStreamFinished) {
                throw new BufferBoundsException(index, bytesRequested, (long) this._streamLength);
            }
            throw new AssertionError();
        }
    }

    protected boolean isValidIndex(int index, int bytesRequested) throws IOException {
        if (index < 0 || bytesRequested < 0) {
            return false;
        }
        long endIndexLong = (((long) index) + ((long) bytesRequested)) - 1;
        if (endIndexLong > 2147483647L) {
            return false;
        }
        int endIndex = (int) endIndexLong;
        if (this._isStreamFinished) {
            return endIndex < this._streamLength;
        } else {
            int chunkIndex = endIndex / this._chunkLength;
            while (chunkIndex >= this._chunks.size()) {
                if ($assertionsDisabled || !this._isStreamFinished) {
                    byte[] chunk = new byte[this._chunkLength];
                    int totalBytesRead = 0;
                    while (!this._isStreamFinished && totalBytesRead != this._chunkLength) {
                        int bytesRead = this._stream.read(chunk, totalBytesRead, this._chunkLength - totalBytesRead);
                        if (bytesRead == -1) {
                            this._isStreamFinished = true;
                            this._streamLength = (this._chunks.size() * this._chunkLength) + totalBytesRead;
                            if (endIndex >= this._streamLength) {
                                this._chunks.add(chunk);
                                return false;
                            }
                        } else {
                            totalBytesRead += bytesRead;
                        }
                    }
                    this._chunks.add(chunk);
                } else {
                    throw new AssertionError();
                }
            }
            return true;
        }
    }

    protected byte getByte(int index) throws IOException {
        if ($assertionsDisabled || index >= 0) {
            return ((byte[]) this._chunks.get(index / this._chunkLength))[index % this._chunkLength];
        }
        throw new AssertionError();
    }

    public byte[] getBytes(int index, int count) throws IOException {
        validateIndex(index, count);
        byte[] bytes = new byte[count];
        int remaining = count;
        int fromIndex = index;
        int toIndex = 0;
        while (remaining != 0) {
            int fromChunkIndex = fromIndex / this._chunkLength;
            int fromInnerIndex = fromIndex % this._chunkLength;
            int length = Math.min(remaining, this._chunkLength - fromInnerIndex);
            System.arraycopy((byte[]) this._chunks.get(fromChunkIndex), fromInnerIndex, bytes, toIndex, length);
            remaining -= length;
            fromIndex += length;
            toIndex += length;
        }
        return bytes;
    }
}
