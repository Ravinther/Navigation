package com.googlecode.mp4parser.boxes.apple;

import com.sygic.aura.poi.fragment.PoiFragment;
import java.nio.ByteBuffer;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class AppleCoverBox extends AppleDataBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private byte[] data;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("AppleCoverBox.java", AppleCoverBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getCoverData", "com.googlecode.mp4parser.boxes.apple.AppleCoverBox", "", "", "", "[B"), 21);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setJpg", "com.googlecode.mp4parser.boxes.apple.AppleCoverBox", "[B", PoiFragment.ARG_DATA, "", "void"), 25);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setPng", "com.googlecode.mp4parser.boxes.apple.AppleCoverBox", "[B", PoiFragment.ARG_DATA, "", "void"), 29);
    }

    public AppleCoverBox() {
        super("covr", 1);
    }

    protected byte[] writeData() {
        return this.data;
    }

    protected void parseData(ByteBuffer data) {
        this.data = new byte[data.limit()];
        data.get(this.data);
    }

    protected int getDataLength() {
        return this.data.length;
    }
}
