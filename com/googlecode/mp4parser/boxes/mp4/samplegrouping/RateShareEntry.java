package com.googlecode.mp4parser.boxes.mp4.samplegrouping;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.util.CastUtils;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class RateShareEntry extends GroupEntry {
    private short discardPriority;
    private List<Entry> entries;
    private int maximumBitrate;
    private int minimumBitrate;
    private short operationPointCut;
    private short targetRateShare;

    public static class Entry {
        int availableBitrate;
        short targetRateShare;

        public Entry(int availableBitrate, short targetRateShare) {
            this.availableBitrate = availableBitrate;
            this.targetRateShare = targetRateShare;
        }

        public String toString() {
            return "{availableBitrate=" + this.availableBitrate + ", targetRateShare=" + this.targetRateShare + '}';
        }

        public int getAvailableBitrate() {
            return this.availableBitrate;
        }

        public short getTargetRateShare() {
            return this.targetRateShare;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry entry = (Entry) o;
            if (this.availableBitrate != entry.availableBitrate) {
                return false;
            }
            if (this.targetRateShare != entry.targetRateShare) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.availableBitrate * 31) + this.targetRateShare;
        }
    }

    public RateShareEntry() {
        this.entries = new LinkedList();
    }

    public String getType() {
        return "rash";
    }

    public void parse(ByteBuffer byteBuffer) {
        this.operationPointCut = byteBuffer.getShort();
        if (this.operationPointCut != (short) 1) {
            int entriesLeft = this.operationPointCut;
            while (true) {
                int entriesLeft2 = entriesLeft - 1;
                if (entriesLeft <= 0) {
                    break;
                }
                this.entries.add(new Entry(CastUtils.l2i(IsoTypeReader.readUInt32(byteBuffer)), byteBuffer.getShort()));
                entriesLeft = entriesLeft2;
            }
        } else {
            this.targetRateShare = byteBuffer.getShort();
        }
        this.maximumBitrate = CastUtils.l2i(IsoTypeReader.readUInt32(byteBuffer));
        this.minimumBitrate = CastUtils.l2i(IsoTypeReader.readUInt32(byteBuffer));
        this.discardPriority = (short) IsoTypeReader.readUInt8(byteBuffer);
    }

    public ByteBuffer get() {
        ByteBuffer buf = ByteBuffer.allocate(this.operationPointCut == (short) 1 ? 13 : (this.operationPointCut * 6) + 11);
        buf.putShort(this.operationPointCut);
        if (this.operationPointCut == (short) 1) {
            buf.putShort(this.targetRateShare);
        } else {
            for (Entry entry : this.entries) {
                buf.putInt(entry.getAvailableBitrate());
                buf.putShort(entry.getTargetRateShare());
            }
        }
        buf.putInt(this.maximumBitrate);
        buf.putInt(this.minimumBitrate);
        IsoTypeWriter.writeUInt8(buf, this.discardPriority);
        buf.rewind();
        return buf;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateShareEntry that = (RateShareEntry) o;
        if (this.discardPriority != that.discardPriority) {
            return false;
        }
        if (this.maximumBitrate != that.maximumBitrate) {
            return false;
        }
        if (this.minimumBitrate != that.minimumBitrate) {
            return false;
        }
        if (this.operationPointCut != that.operationPointCut) {
            return false;
        }
        if (this.targetRateShare != that.targetRateShare) {
            return false;
        }
        if (this.entries != null) {
            if (this.entries.equals(that.entries)) {
                return true;
            }
        } else if (that.entries == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((this.operationPointCut * 31) + this.targetRateShare) * 31) + (this.entries != null ? this.entries.hashCode() : 0)) * 31) + this.maximumBitrate) * 31) + this.minimumBitrate) * 31) + this.discardPriority;
    }
}
