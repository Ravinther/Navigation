package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReader;
import com.googlecode.mp4parser.AbstractBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import java.nio.ByteBuffer;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class OriginalFormatBox extends AbstractBox {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private String dataFormat;

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("OriginalFormatBox.java", OriginalFormatBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getDataFormat", "com.coremedia.iso.boxes.OriginalFormatBox", "", "", "", "java.lang.String"), 42);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setDataFormat", "com.coremedia.iso.boxes.OriginalFormatBox", "java.lang.String", "dataFormat", "", "void"), 47);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "toString", "com.coremedia.iso.boxes.OriginalFormatBox", "", "", "", "java.lang.String"), 67);
    }

    static {
        ajc$preClinit();
        $assertionsDisabled = !OriginalFormatBox.class.desiredAssertionStatus();
    }

    public OriginalFormatBox() {
        super("frma");
        this.dataFormat = "    ";
    }

    public String getDataFormat() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_0, this, this));
        return this.dataFormat;
    }

    protected long getContentSize() {
        return 4;
    }

    public void _parseDetails(ByteBuffer content) {
        this.dataFormat = IsoTypeReader.read4cc(content);
    }

    protected void getContent(ByteBuffer byteBuffer) {
        byteBuffer.put(IsoFile.fourCCtoBytes(this.dataFormat));
    }

    public String toString() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_2, this, this));
        return "OriginalFormatBox[dataFormat=" + getDataFormat() + "]";
    }
}
