package com.googlecode.mp4parser.authoring.samples;

import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.SampleToChunkBox.Entry;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class DefaultMp4SampleList extends AbstractList<Sample> {
    ByteBuffer[][] cache;
    int[] chunkNumsStartSampleNum;
    long[] chunkOffsets;
    long[] chunkSizes;
    int lastChunk;
    long[][] sampleOffsetsWithinChunks;
    SampleSizeBox ssb;
    Container topLevel;
    TrackBox trackBox;

    /* renamed from: com.googlecode.mp4parser.authoring.samples.DefaultMp4SampleList.1 */
    class C10411 implements Sample {
        private final /* synthetic */ ByteBuffer val$finalCorrectPartOfChunk;
        private final /* synthetic */ long val$finalOffsetWithInChunk;
        private final /* synthetic */ long val$sampleSize;

        C10411(long j, ByteBuffer byteBuffer, long j2) {
            this.val$sampleSize = j;
            this.val$finalCorrectPartOfChunk = byteBuffer;
            this.val$finalOffsetWithInChunk = j2;
        }

        public void writeTo(WritableByteChannel channel) throws IOException {
            channel.write(asByteBuffer());
        }

        public long getSize() {
            return this.val$sampleSize;
        }

        public ByteBuffer asByteBuffer() {
            return (ByteBuffer) ((ByteBuffer) this.val$finalCorrectPartOfChunk.position(CastUtils.l2i(this.val$finalOffsetWithInChunk))).slice().limit(CastUtils.l2i(this.val$sampleSize));
        }

        public String toString() {
            return "DefaultMp4Sample(size:" + this.val$sampleSize + ")";
        }
    }

    public DefaultMp4SampleList(long track, Container topLevel) {
        this.trackBox = null;
        this.cache = null;
        this.lastChunk = 0;
        this.topLevel = topLevel;
        Class cls = TrackBox.class;
        for (TrackBox tb : ((MovieBox) topLevel.getBoxes(MovieBox.class).get(0)).getBoxes(r23)) {
            if (tb.getTrackHeaderBox().getTrackId() == track) {
                this.trackBox = tb;
            }
        }
        if (this.trackBox == null) {
            throw new RuntimeException("This MP4 does not contain track " + track);
        }
        int s2cIndex;
        int currentChunkNo;
        this.chunkOffsets = this.trackBox.getSampleTableBox().getChunkOffsetBox().getChunkOffsets();
        this.chunkSizes = new long[this.chunkOffsets.length];
        this.cache = new ByteBuffer[this.chunkOffsets.length][];
        this.sampleOffsetsWithinChunks = new long[this.chunkOffsets.length][];
        this.ssb = this.trackBox.getSampleTableBox().getSampleSizeBox();
        List<Entry> s2chunkEntries = this.trackBox.getSampleTableBox().getSampleToChunkBox().getEntries();
        Entry[] entries = (Entry[]) s2chunkEntries.toArray(new Entry[s2chunkEntries.size()]);
        int s2cIndex2 = 0 + 1;
        Entry next = entries[0];
        int currentChunkNo2 = 0;
        int currentSamplePerChunk = 0;
        long nextFirstChunk = next.getFirstChunk();
        int nextSamplePerChunk = CastUtils.l2i(next.getSamplesPerChunk());
        int currentSampleNo = 1;
        int lastSampleNo = size();
        while (true) {
            currentChunkNo2++;
            if (((long) currentChunkNo2) == nextFirstChunk) {
                currentSamplePerChunk = nextSamplePerChunk;
                int length = entries.length;
                if (r0 > s2cIndex2) {
                    s2cIndex = s2cIndex2 + 1;
                    next = entries[s2cIndex2];
                    nextSamplePerChunk = CastUtils.l2i(next.getSamplesPerChunk());
                    nextFirstChunk = next.getFirstChunk();
                } else {
                    nextSamplePerChunk = -1;
                    nextFirstChunk = Long.MAX_VALUE;
                    s2cIndex = s2cIndex2;
                }
            } else {
                s2cIndex = s2cIndex2;
            }
            this.sampleOffsetsWithinChunks[currentChunkNo2 - 1] = new long[currentSamplePerChunk];
            currentSampleNo += currentSamplePerChunk;
            if (currentSampleNo > lastSampleNo) {
                break;
            }
            s2cIndex2 = s2cIndex;
        }
        this.chunkNumsStartSampleNum = new int[(currentChunkNo2 + 1)];
        s2cIndex2 = 0 + 1;
        next = entries[0];
        currentChunkNo2 = 0;
        currentSamplePerChunk = 0;
        nextFirstChunk = next.getFirstChunk();
        nextSamplePerChunk = CastUtils.l2i(next.getSamplesPerChunk());
        currentSampleNo = 1;
        s2cIndex = s2cIndex2;
        while (true) {
            currentChunkNo = currentChunkNo2 + 1;
            this.chunkNumsStartSampleNum[currentChunkNo2] = currentSampleNo;
            if (((long) currentChunkNo) == nextFirstChunk) {
                currentSamplePerChunk = nextSamplePerChunk;
                length = entries.length;
                if (r0 > s2cIndex) {
                    s2cIndex2 = s2cIndex + 1;
                    next = entries[s2cIndex];
                    nextSamplePerChunk = CastUtils.l2i(next.getSamplesPerChunk());
                    nextFirstChunk = next.getFirstChunk();
                    s2cIndex = s2cIndex2;
                } else {
                    nextSamplePerChunk = -1;
                    nextFirstChunk = Long.MAX_VALUE;
                }
            }
            currentSampleNo += currentSamplePerChunk;
            if (currentSampleNo > lastSampleNo) {
                break;
            }
            currentChunkNo2 = currentChunkNo;
        }
        this.chunkNumsStartSampleNum[currentChunkNo] = Integer.MAX_VALUE;
        currentChunkNo2 = 0;
        long sampleSum = 0;
        int i = 1;
        while (true) {
            if (((long) i) <= this.ssb.getSampleCount()) {
                if (i == this.chunkNumsStartSampleNum[currentChunkNo2]) {
                    currentChunkNo2++;
                    sampleSum = 0;
                }
                long[] jArr = this.chunkSizes;
                int i2 = currentChunkNo2 - 1;
                jArr[i2] = jArr[i2] + this.ssb.getSampleSizeAtIndex(i - 1);
                this.sampleOffsetsWithinChunks[currentChunkNo2 - 1][i - this.chunkNumsStartSampleNum[currentChunkNo2 - 1]] = sampleSum;
                sampleSum += this.ssb.getSampleSizeAtIndex(i - 1);
                i++;
            } else {
                return;
            }
        }
    }

    synchronized int getChunkForSample(int index) {
        int i;
        int sampleNum = index + 1;
        if (sampleNum >= this.chunkNumsStartSampleNum[this.lastChunk] && sampleNum < this.chunkNumsStartSampleNum[this.lastChunk + 1]) {
            i = this.lastChunk;
        } else if (sampleNum < this.chunkNumsStartSampleNum[this.lastChunk]) {
            this.lastChunk = 0;
            while (this.chunkNumsStartSampleNum[this.lastChunk + 1] <= sampleNum) {
                this.lastChunk++;
            }
            i = this.lastChunk;
        } else {
            this.lastChunk++;
            while (this.chunkNumsStartSampleNum[this.lastChunk + 1] <= sampleNum) {
                this.lastChunk++;
            }
            i = this.lastChunk;
        }
        return i;
    }

    public Sample get(int index) {
        if (((long) index) >= this.ssb.getSampleCount()) {
            throw new IndexOutOfBoundsException();
        }
        int chunkNumber = getChunkForSample(index);
        int chunkStartSample = this.chunkNumsStartSampleNum[chunkNumber] - 1;
        long chunkOffset = this.chunkOffsets[CastUtils.l2i((long) chunkNumber)];
        int sampleInChunk = index - chunkStartSample;
        long[] sampleOffsetsWithinChunk = this.sampleOffsetsWithinChunks[CastUtils.l2i((long) chunkNumber)];
        long offsetWithInChunk = sampleOffsetsWithinChunk[sampleInChunk];
        ByteBuffer[] chunkBuffers = this.cache[CastUtils.l2i((long) chunkNumber)];
        if (chunkBuffers == null) {
            List<ByteBuffer> _chunkBuffers = new ArrayList();
            long currentStart = 0;
            int i = 0;
            while (i < sampleOffsetsWithinChunk.length) {
                try {
                    if ((sampleOffsetsWithinChunk[i] + this.ssb.getSampleSizeAtIndex(i + chunkStartSample)) - currentStart > 268435456) {
                        _chunkBuffers.add(this.topLevel.getByteBuffer(chunkOffset + currentStart, sampleOffsetsWithinChunk[i] - currentStart));
                        currentStart = sampleOffsetsWithinChunk[i];
                    }
                    i++;
                } catch (IOException e) {
                    throw new IndexOutOfBoundsException(e.getMessage());
                }
            }
            _chunkBuffers.add(this.topLevel.getByteBuffer(chunkOffset + currentStart, ((-currentStart) + sampleOffsetsWithinChunk[sampleOffsetsWithinChunk.length - 1]) + this.ssb.getSampleSizeAtIndex((sampleOffsetsWithinChunk.length + chunkStartSample) - 1)));
            chunkBuffers = (ByteBuffer[]) _chunkBuffers.toArray(new ByteBuffer[_chunkBuffers.size()]);
            this.cache[CastUtils.l2i((long) chunkNumber)] = chunkBuffers;
        }
        ByteBuffer correctPartOfChunk = null;
        for (ByteBuffer chunkBuffer : chunkBuffers) {
            if (offsetWithInChunk < ((long) chunkBuffer.limit())) {
                correctPartOfChunk = chunkBuffer;
                break;
            }
            offsetWithInChunk -= (long) chunkBuffer.limit();
        }
        return new C10411(this.ssb.getSampleSizeAtIndex(index), correctPartOfChunk, offsetWithInChunk);
    }

    public int size() {
        return CastUtils.l2i(this.trackBox.getSampleTableBox().getSampleSizeBox().getSampleCount());
    }
}
