package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.nio.ByteBuffer;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class FreeSpaceBox extends AbstractBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    byte[] data;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("FreeSpaceBox.java", FreeSpaceBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setData", "com.coremedia.iso.boxes.FreeSpaceBox", "[B", PoiFragment.ARG_DATA, "", "void"), 42);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getData", "com.coremedia.iso.boxes.FreeSpaceBox", "", "", "", "[B"), 46);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "toString", "com.coremedia.iso.boxes.FreeSpaceBox", "", "", "", "java.lang.String"), 61);
    }

    protected long getContentSize() {
        return (long) this.data.length;
    }

    public FreeSpaceBox() {
        super("skip");
    }

    public void _parseDetails(ByteBuffer content) {
        this.data = new byte[content.remaining()];
        content.get(this.data);
    }

    protected void getContent(ByteBuffer byteBuffer) {
        byteBuffer.put(this.data);
    }

    public String toString() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_2, this, this));
        return "FreeSpaceBox[size=" + this.data.length + ";type=" + getType() + "]";
    }
}
