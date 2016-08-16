package com.googlecode.mp4parser.boxes.apple;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.Utf8;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public abstract class Utf8AppleDataBox extends AppleDataBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    String value;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("Utf8AppleDataBox.java", Utf8AppleDataBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getValue", "com.googlecode.mp4parser.boxes.apple.Utf8AppleDataBox", "", "", "", "java.lang.String"), 21);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setValue", "com.googlecode.mp4parser.boxes.apple.Utf8AppleDataBox", "java.lang.String", "value", "", "void"), 30);
    }

    protected Utf8AppleDataBox(String type) {
        super(type, 1);
    }

    public byte[] writeData() {
        return Utf8.convert(this.value);
    }

    protected int getDataLength() {
        return this.value.getBytes(Charset.forName("UTF-8")).length;
    }

    protected void parseData(ByteBuffer data) {
        this.value = IsoTypeReader.readString(data, data.remaining());
    }
}
