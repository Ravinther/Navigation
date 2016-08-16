package com.googlecode.mp4parser.boxes.mp4.samplegrouping;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import com.googlecode.mp4parser.util.CastUtils;
import com.mp4parser.iso14496.part15.StepwiseTemporalLayerEntry;
import com.mp4parser.iso14496.part15.SyncSampleEntry;
import com.mp4parser.iso14496.part15.TemporalLayerSampleGroup;
import com.mp4parser.iso14496.part15.TemporalSubLayerSampleGroup;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class SampleGroupDescriptionBox extends AbstractFullBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_3 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_4 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_5 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_6 = null;
    private int defaultLength;
    private List<GroupEntry> groupEntries;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("SampleGroupDescriptionBox.java", SampleGroupDescriptionBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getDefaultLength", "com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox", "", "", "", "int"), 145);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setDefaultLength", "com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox", "int", "defaultLength", "", "void"), 149);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getGroupEntries", "com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox", "", "", "", "java.util.List"), 153);
        ajc$tjp_3 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setGroupEntries", "com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox", "java.util.List", "groupEntries", "", "void"), 157);
        ajc$tjp_4 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "equals", "com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox", "java.lang.Object", "o", "", "boolean"), 162);
        ajc$tjp_5 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "hashCode", "com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox", "", "", "", "int"), 183);
        ajc$tjp_6 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "toString", "com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox", "", "", "", "java.lang.String"), 191);
    }

    public SampleGroupDescriptionBox() {
        super("sgpd");
        this.groupEntries = new LinkedList();
        setVersion(1);
    }

    protected long getContentSize() {
        long size = 8;
        if (getVersion() == 1) {
            size = 8 + 4;
        }
        size += 4;
        for (GroupEntry groupEntry : this.groupEntries) {
            if (getVersion() == 1 && this.defaultLength == 0) {
                size += 4;
            }
            size += (long) groupEntry.size();
        }
        return size;
    }

    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        byteBuffer.put(IsoFile.fourCCtoBytes(((GroupEntry) this.groupEntries.get(0)).getType()));
        if (getVersion() == 1) {
            IsoTypeWriter.writeUInt32(byteBuffer, (long) this.defaultLength);
        }
        IsoTypeWriter.writeUInt32(byteBuffer, (long) this.groupEntries.size());
        for (GroupEntry entry : this.groupEntries) {
            if (getVersion() == 1 && this.defaultLength == 0) {
                IsoTypeWriter.writeUInt32(byteBuffer, (long) entry.get().limit());
            }
            byteBuffer.put(entry.get());
        }
    }

    protected void _parseDetails(ByteBuffer content) {
        parseVersionAndFlags(content);
        if (getVersion() != 1) {
            throw new RuntimeException("SampleGroupDescriptionBox are only supported in version 1");
        }
        String groupingType = IsoTypeReader.read4cc(content);
        if (getVersion() == 1) {
            this.defaultLength = CastUtils.l2i(IsoTypeReader.readUInt32(content));
        }
        long entryCount = IsoTypeReader.readUInt32(content);
        while (true) {
            long entryCount2 = entryCount - 1;
            if (entryCount > 0) {
                int length = this.defaultLength;
                if (getVersion() != 1) {
                    break;
                }
                if (this.defaultLength == 0) {
                    length = CastUtils.l2i(IsoTypeReader.readUInt32(content));
                }
                int finalPos = content.position() + length;
                ByteBuffer parseMe = content.slice();
                parseMe.limit(length);
                this.groupEntries.add(parseGroupEntry(parseMe, groupingType));
                content.position(finalPos);
                entryCount = entryCount2;
            } else {
                return;
            }
        }
        throw new RuntimeException("This should be implemented");
    }

    private GroupEntry parseGroupEntry(ByteBuffer content, String groupingType) {
        GroupEntry rollRecoveryEntry;
        if ("roll".equals(groupingType)) {
            rollRecoveryEntry = new RollRecoveryEntry();
        } else if ("rash".equals(groupingType)) {
            rollRecoveryEntry = new RateShareEntry();
        } else if ("seig".equals(groupingType)) {
            rollRecoveryEntry = new CencSampleEncryptionInformationGroupEntry();
        } else if ("rap ".equals(groupingType)) {
            rollRecoveryEntry = new VisualRandomAccessEntry();
        } else if ("tele".equals(groupingType)) {
            rollRecoveryEntry = new TemporalLevelEntry();
        } else if ("sync".equals(groupingType)) {
            rollRecoveryEntry = new SyncSampleEntry();
        } else if ("tscl".equals(groupingType)) {
            rollRecoveryEntry = new TemporalLayerSampleGroup();
        } else if ("tsas".equals(groupingType)) {
            rollRecoveryEntry = new TemporalSubLayerSampleGroup();
        } else if ("stsa".equals(groupingType)) {
            rollRecoveryEntry = new StepwiseTemporalLayerEntry();
        } else {
            rollRecoveryEntry = new UnknownEntry(groupingType);
        }
        rollRecoveryEntry.parse(content);
        return rollRecoveryEntry;
    }

    public List<GroupEntry> getGroupEntries() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_2, this, this));
        return this.groupEntries;
    }

    public void setGroupEntries(List<GroupEntry> groupEntries) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_3, this, this, groupEntries));
        this.groupEntries = groupEntries;
    }

    public boolean equals(Object o) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_4, this, this, o));
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SampleGroupDescriptionBox that = (SampleGroupDescriptionBox) o;
        if (this.defaultLength != that.defaultLength) {
            return false;
        }
        if (this.groupEntries != null) {
            if (this.groupEntries.equals(that.groupEntries)) {
                return true;
            }
        } else if (that.groupEntries == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_5, this, this));
        return ((this.defaultLength + 0) * 31) + (this.groupEntries != null ? this.groupEntries.hashCode() : 0);
    }

    public String toString() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_6, this, this));
        return "SampleGroupDescriptionBox{groupingType='" + (this.groupEntries.size() > 0 ? ((GroupEntry) this.groupEntries.get(0)).getType() : "????") + '\'' + ", defaultLength=" + this.defaultLength + ", groupEntries=" + this.groupEntries + '}';
    }
}
