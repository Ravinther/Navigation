package com.googlecode.mp4parser.boxes.mp4.objectdescriptors;

import com.coremedia.iso.Hex;
import com.coremedia.iso.IsoTypeReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Descriptor(tags = {4})
public class DecoderConfigDescriptor extends BaseDescriptor {
    private static Logger log;
    AudioSpecificConfig audioSpecificInfo;
    long avgBitRate;
    int bufferSizeDB;
    byte[] configDescriptorDeadBytes;
    DecoderSpecificInfo decoderSpecificInfo;
    long maxBitRate;
    int objectTypeIndication;
    List<ProfileLevelIndicationDescriptor> profileLevelIndicationDescriptors;
    int streamType;
    int upStream;

    public DecoderConfigDescriptor() {
        this.profileLevelIndicationDescriptors = new ArrayList();
    }

    static {
        log = Logger.getLogger(DecoderConfigDescriptor.class.getName());
    }

    public void parseDetail(ByteBuffer bb) throws IOException {
        BaseDescriptor descriptor;
        Object valueOf;
        this.objectTypeIndication = IsoTypeReader.readUInt8(bb);
        int data = IsoTypeReader.readUInt8(bb);
        this.streamType = data >>> 2;
        this.upStream = (data >> 1) & 1;
        this.bufferSizeDB = IsoTypeReader.readUInt24(bb);
        this.maxBitRate = IsoTypeReader.readUInt32(bb);
        this.avgBitRate = IsoTypeReader.readUInt32(bb);
        if (bb.remaining() > 2) {
            int begin = bb.position();
            descriptor = ObjectDescriptorFactory.createFrom(this.objectTypeIndication, bb);
            int read = bb.position() - begin;
            Logger logger = log;
            StringBuilder append = new StringBuilder().append(descriptor).append(" - DecoderConfigDescr1 read: ").append(read).append(", size: ");
            if (descriptor != null) {
                valueOf = Integer.valueOf(descriptor.getSize());
            } else {
                valueOf = null;
            }
            logger.finer(append.append(valueOf).toString());
            if (descriptor != null) {
                int size = descriptor.getSize();
                if (read < size) {
                    this.configDescriptorDeadBytes = new byte[(size - read)];
                    bb.get(this.configDescriptorDeadBytes);
                }
            }
            if (descriptor instanceof DecoderSpecificInfo) {
                this.decoderSpecificInfo = (DecoderSpecificInfo) descriptor;
            }
            if (descriptor instanceof AudioSpecificConfig) {
                this.audioSpecificInfo = (AudioSpecificConfig) descriptor;
            }
        }
        while (bb.remaining() > 2) {
            long begin2 = (long) bb.position();
            descriptor = ObjectDescriptorFactory.createFrom(this.objectTypeIndication, bb);
            long read2 = ((long) bb.position()) - begin2;
            logger = log;
            append = new StringBuilder().append(descriptor).append(" - DecoderConfigDescr2 read: ").append(read2).append(", size: ");
            if (descriptor != null) {
                valueOf = Integer.valueOf(descriptor.getSize());
            } else {
                valueOf = null;
            }
            logger.finer(append.append(valueOf).toString());
            if (descriptor instanceof ProfileLevelIndicationDescriptor) {
                this.profileLevelIndicationDescriptors.add((ProfileLevelIndicationDescriptor) descriptor);
            }
        }
    }

    public DecoderSpecificInfo getDecoderSpecificInfo() {
        return this.decoderSpecificInfo;
    }

    public AudioSpecificConfig getAudioSpecificInfo() {
        return this.audioSpecificInfo;
    }

    public List<ProfileLevelIndicationDescriptor> getProfileLevelIndicationDescriptors() {
        return this.profileLevelIndicationDescriptors;
    }

    public int getObjectTypeIndication() {
        return this.objectTypeIndication;
    }

    public int getStreamType() {
        return this.streamType;
    }

    public int getUpStream() {
        return this.upStream;
    }

    public int getBufferSizeDB() {
        return this.bufferSizeDB;
    }

    public long getMaxBitRate() {
        return this.maxBitRate;
    }

    public void setMaxBitRate(long maxBitRate) {
        this.maxBitRate = maxBitRate;
    }

    public long getAvgBitRate() {
        return this.avgBitRate;
    }

    public void setAvgBitRate(long avgBitRate) {
        this.avgBitRate = avgBitRate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DecoderConfigDescriptor");
        sb.append("{objectTypeIndication=").append(this.objectTypeIndication);
        sb.append(", streamType=").append(this.streamType);
        sb.append(", upStream=").append(this.upStream);
        sb.append(", bufferSizeDB=").append(this.bufferSizeDB);
        sb.append(", maxBitRate=").append(this.maxBitRate);
        sb.append(", avgBitRate=").append(this.avgBitRate);
        sb.append(", decoderSpecificInfo=").append(this.decoderSpecificInfo);
        sb.append(", audioSpecificInfo=").append(this.audioSpecificInfo);
        sb.append(", configDescriptorDeadBytes=").append(Hex.encodeHex(this.configDescriptorDeadBytes != null ? this.configDescriptorDeadBytes : new byte[0]));
        sb.append(", profileLevelIndicationDescriptors=").append(this.profileLevelIndicationDescriptors == null ? "null" : Arrays.asList(new List[]{this.profileLevelIndicationDescriptors}).toString());
        sb.append('}');
        return sb.toString();
    }
}
