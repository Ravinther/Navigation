package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class StreamReader extends SequentialReader {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final InputStream _stream;

    static {
        $assertionsDisabled = !StreamReader.class.desiredAssertionStatus();
    }

    public StreamReader(InputStream stream) {
        if (stream == null) {
            throw new NullPointerException();
        }
        this._stream = stream;
    }

    protected byte getByte() throws IOException {
        int value = this._stream.read();
        if (value != -1) {
            return (byte) value;
        }
        throw new EOFException("End of data reached.");
    }

    public byte[] getBytes(int count) throws IOException {
        byte[] bytes = new byte[count];
        int totalBytesRead = 0;
        while (totalBytesRead != count) {
            int bytesRead = this._stream.read(bytes, totalBytesRead, count - totalBytesRead);
            if (bytesRead == -1) {
                throw new EOFException("End of data reached.");
            }
            totalBytesRead += bytesRead;
            if (!$assertionsDisabled && totalBytesRead > count) {
                throw new AssertionError();
            }
        }
        return bytes;
    }

    public void skip(long n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("n must be zero or greater.");
        }
        if (skipInternal(n) != n) {
            throw new EOFException(String.format("Unable to skip. Requested %d bytes but skipped %d.", new Object[]{Long.valueOf(n), Long.valueOf(skipInternal(n))}));
        }
    }

    public boolean trySkip(long n) throws IOException {
        if (n >= 0) {
            return skipInternal(n) == n;
        } else {
            throw new IllegalArgumentException("n must be zero or greater.");
        }
    }

    private long skipInternal(long n) throws IOException {
        long skippedTotal = 0;
        while (skippedTotal != n) {
            long skipped = this._stream.skip(n - skippedTotal);
            if ($assertionsDisabled || skipped >= 0) {
                skippedTotal += skipped;
                if (skipped == 0) {
                    break;
                }
            }
            throw new AssertionError();
        }
        return skippedTotal;
    }
}
