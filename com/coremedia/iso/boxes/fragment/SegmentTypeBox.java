package com.coremedia.iso.boxes.fragment;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import com.sygic.aura.C1090R;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class SegmentTypeBox extends AbstractBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_3 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_4 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_5 = null;
    private List<String> compatibleBrands;
    private String majorBrand;
    private long minorVersion;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("SegmentTypeBox.java", SegmentTypeBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getMajorBrand", "com.coremedia.iso.boxes.fragment.SegmentTypeBox", "", "", "", "java.lang.String"), 85);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setMajorBrand", "com.coremedia.iso.boxes.fragment.SegmentTypeBox", "java.lang.String", "majorBrand", "", "void"), 94);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setMinorVersion", "com.coremedia.iso.boxes.fragment.SegmentTypeBox", "long", "minorVersion", "", "void"), C1090R.styleable.Theme_radioButtonStyle);
        ajc$tjp_3 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getMinorVersion", "com.coremedia.iso.boxes.fragment.SegmentTypeBox", "", "", "", "long"), 113);
        ajc$tjp_4 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getCompatibleBrands", "com.coremedia.iso.boxes.fragment.SegmentTypeBox", "", "", "", "java.util.List"), 122);
        ajc$tjp_5 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setCompatibleBrands", "com.coremedia.iso.boxes.fragment.SegmentTypeBox", "java.util.List", "compatibleBrands", "", "void"), 126);
    }

    public SegmentTypeBox() {
        super("styp");
        this.compatibleBrands = Collections.emptyList();
    }

    public SegmentTypeBox(String majorBrand, long minorVersion, List<String> compatibleBrands) {
        super("styp");
        this.compatibleBrands = Collections.emptyList();
        this.majorBrand = majorBrand;
        this.minorVersion = minorVersion;
        this.compatibleBrands = compatibleBrands;
    }

    protected long getContentSize() {
        return (long) ((this.compatibleBrands.size() * 4) + 8);
    }

    public void _parseDetails(ByteBuffer content) {
        this.majorBrand = IsoTypeReader.read4cc(content);
        this.minorVersion = IsoTypeReader.readUInt32(content);
        int compatibleBrandsCount = content.remaining() / 4;
        this.compatibleBrands = new LinkedList();
        for (int i = 0; i < compatibleBrandsCount; i++) {
            this.compatibleBrands.add(IsoTypeReader.read4cc(content));
        }
    }

    protected void getContent(ByteBuffer byteBuffer) {
        byteBuffer.put(IsoFile.fourCCtoBytes(this.majorBrand));
        IsoTypeWriter.writeUInt32(byteBuffer, this.minorVersion);
        for (String compatibleBrand : this.compatibleBrands) {
            byteBuffer.put(IsoFile.fourCCtoBytes(compatibleBrand));
        }
    }

    public String getMajorBrand() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_0, this, this));
        return this.majorBrand;
    }

    public long getMinorVersion() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_3, this, this));
        return this.minorVersion;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("SegmentTypeBox[");
        result.append("majorBrand=").append(getMajorBrand());
        result.append(";");
        result.append("minorVersion=").append(getMinorVersion());
        for (String compatibleBrand : this.compatibleBrands) {
            result.append(";");
            result.append("compatibleBrand=").append(compatibleBrand);
        }
        result.append("]");
        return result.toString();
    }
}
