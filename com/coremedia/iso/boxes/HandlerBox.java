package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.Utf8;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class HandlerBox extends AbstractFullBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_3 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_4 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_5 = null;
    public static final Map<String, String> readableTypes;
    private long f526a;
    private long f527b;
    private long f528c;
    private String handlerType;
    private String name;
    private long shouldBeZeroButAppleWritesHereSomeValue;
    private boolean zeroTerm;

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("HandlerBox.java", HandlerBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getHandlerType", "com.coremedia.iso.boxes.HandlerBox", "", "", "", "java.lang.String"), 78);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setName", "com.coremedia.iso.boxes.HandlerBox", "java.lang.String", "name", "", "void"), 87);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setHandlerType", "com.coremedia.iso.boxes.HandlerBox", "java.lang.String", "handlerType", "", "void"), 91);
        ajc$tjp_3 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getName", "com.coremedia.iso.boxes.HandlerBox", "", "", "", "java.lang.String"), 95);
        ajc$tjp_4 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getHumanReadableTrackType", "com.coremedia.iso.boxes.HandlerBox", "", "", "", "java.lang.String"), 99);
        ajc$tjp_5 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "toString", "com.coremedia.iso.boxes.HandlerBox", "", "", "", "java.lang.String"), 149);
    }

    static {
        ajc$preClinit();
        HashMap hm = new HashMap();
        hm.put("odsm", "ObjectDescriptorStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hm.put("crsm", "ClockReferenceStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hm.put("sdsm", "SceneDescriptionStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hm.put("m7sm", "MPEG7Stream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hm.put("ocsm", "ObjectContentInfoStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hm.put("ipsm", "IPMP Stream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hm.put("mjsm", "MPEG-J Stream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hm.put("mdir", "Apple Meta Data iTunes Reader");
        hm.put("mp7b", "MPEG-7 binary XML");
        hm.put("mp7t", "MPEG-7 XML");
        hm.put("vide", "Video Track");
        hm.put("soun", "Sound Track");
        hm.put("hint", "Hint Track");
        hm.put("appl", "Apple specific");
        hm.put("meta", "Timed Metadata track - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        readableTypes = Collections.unmodifiableMap(hm);
    }

    public HandlerBox() {
        super("hdlr");
        this.name = null;
        this.zeroTerm = true;
    }

    public String getHandlerType() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_0, this, this));
        return this.handlerType;
    }

    public void setHandlerType(String handlerType) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_2, this, this, handlerType));
        this.handlerType = handlerType;
    }

    public String getName() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_3, this, this));
        return this.name;
    }

    protected long getContentSize() {
        if (this.zeroTerm) {
            return (long) (Utf8.utf8StringLengthInBytes(this.name) + 25);
        }
        return (long) (Utf8.utf8StringLengthInBytes(this.name) + 24);
    }

    public void _parseDetails(ByteBuffer content) {
        parseVersionAndFlags(content);
        this.shouldBeZeroButAppleWritesHereSomeValue = IsoTypeReader.readUInt32(content);
        this.handlerType = IsoTypeReader.read4cc(content);
        this.f526a = IsoTypeReader.readUInt32(content);
        this.f527b = IsoTypeReader.readUInt32(content);
        this.f528c = IsoTypeReader.readUInt32(content);
        if (content.remaining() > 0) {
            this.name = IsoTypeReader.readString(content, content.remaining());
            if (this.name.endsWith("\u0000")) {
                this.name = this.name.substring(0, this.name.length() - 1);
                this.zeroTerm = true;
                return;
            }
            this.zeroTerm = false;
            return;
        }
        this.zeroTerm = false;
    }

    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeUInt32(byteBuffer, this.shouldBeZeroButAppleWritesHereSomeValue);
        byteBuffer.put(IsoFile.fourCCtoBytes(this.handlerType));
        IsoTypeWriter.writeUInt32(byteBuffer, this.f526a);
        IsoTypeWriter.writeUInt32(byteBuffer, this.f527b);
        IsoTypeWriter.writeUInt32(byteBuffer, this.f528c);
        if (this.name != null) {
            byteBuffer.put(Utf8.convert(this.name));
        }
        if (this.zeroTerm) {
            byteBuffer.put((byte) 0);
        }
    }

    public String toString() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_5, this, this));
        return "HandlerBox[handlerType=" + getHandlerType() + ";name=" + getName() + "]";
    }
}
