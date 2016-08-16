package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractBox;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.nio.ByteBuffer;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class ItemDataBox extends AbstractBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    ByteBuffer data;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("ItemDataBox.java", ItemDataBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getData", "com.coremedia.iso.boxes.ItemDataBox", "", "", "", "java.nio.ByteBuffer"), 19);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setData", "com.coremedia.iso.boxes.ItemDataBox", "java.nio.ByteBuffer", PoiFragment.ARG_DATA, "", "void"), 23);
    }

    public ItemDataBox() {
        super("idat");
        this.data = ByteBuffer.allocate(0);
    }

    protected long getContentSize() {
        return (long) this.data.limit();
    }

    public void _parseDetails(ByteBuffer content) {
        this.data = content.slice();
        content.position(content.position() + content.remaining());
    }

    protected void getContent(ByteBuffer byteBuffer) {
        byteBuffer.put(this.data);
    }
}
