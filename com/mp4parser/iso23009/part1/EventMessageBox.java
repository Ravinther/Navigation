package com.mp4parser.iso23009.part1;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.Utf8;
import com.googlecode.mp4parser.AbstractFullBox;
import com.sygic.aura.C1090R;
import java.nio.ByteBuffer;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class EventMessageBox extends AbstractFullBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_10 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_11 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_12 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_13 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_3 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_4 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_5 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_6 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_7 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_8 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_9 = null;
    long eventDuration;
    long id;
    byte[] messageData;
    long presentationTimeDelta;
    String schemeIdUri;
    long timescale;
    String value;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("EventMessageBox.java", EventMessageBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getSchemeIdUri", "com.mp4parser.iso23009.part1.EventMessageBox", "", "", "", "java.lang.String"), 59);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setSchemeIdUri", "com.mp4parser.iso23009.part1.EventMessageBox", "java.lang.String", "schemeIdUri", "", "void"), 63);
        ajc$tjp_10 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getId", "com.mp4parser.iso23009.part1.EventMessageBox", "", "", "", "long"), 99);
        ajc$tjp_11 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setId", "com.mp4parser.iso23009.part1.EventMessageBox", "long", "id", "", "void"), C1090R.styleable.Theme_radioButtonStyle);
        ajc$tjp_12 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getMessageData", "com.mp4parser.iso23009.part1.EventMessageBox", "", "", "", "[B"), 107);
        ajc$tjp_13 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setMessageData", "com.mp4parser.iso23009.part1.EventMessageBox", "[B", "messageData", "", "void"), 111);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getValue", "com.mp4parser.iso23009.part1.EventMessageBox", "", "", "", "java.lang.String"), 67);
        ajc$tjp_3 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setValue", "com.mp4parser.iso23009.part1.EventMessageBox", "java.lang.String", "value", "", "void"), 71);
        ajc$tjp_4 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getTimescale", "com.mp4parser.iso23009.part1.EventMessageBox", "", "", "", "long"), 75);
        ajc$tjp_5 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setTimescale", "com.mp4parser.iso23009.part1.EventMessageBox", "long", "timescale", "", "void"), 79);
        ajc$tjp_6 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getPresentationTimeDelta", "com.mp4parser.iso23009.part1.EventMessageBox", "", "", "", "long"), 83);
        ajc$tjp_7 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setPresentationTimeDelta", "com.mp4parser.iso23009.part1.EventMessageBox", "long", "presentationTimeDelta", "", "void"), 87);
        ajc$tjp_8 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getEventDuration", "com.mp4parser.iso23009.part1.EventMessageBox", "", "", "", "long"), 91);
        ajc$tjp_9 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setEventDuration", "com.mp4parser.iso23009.part1.EventMessageBox", "long", "eventDuration", "", "void"), 95);
    }

    public EventMessageBox() {
        super("emsg");
    }

    protected void _parseDetails(ByteBuffer content) {
        parseVersionAndFlags(content);
        this.schemeIdUri = IsoTypeReader.readString(content);
        this.value = IsoTypeReader.readString(content);
        this.timescale = IsoTypeReader.readUInt32(content);
        this.presentationTimeDelta = IsoTypeReader.readUInt32(content);
        this.eventDuration = IsoTypeReader.readUInt32(content);
        this.id = IsoTypeReader.readUInt32(content);
        this.messageData = new byte[content.remaining()];
        content.get(this.messageData);
    }

    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeUtf8String(byteBuffer, this.schemeIdUri);
        IsoTypeWriter.writeUtf8String(byteBuffer, this.value);
        IsoTypeWriter.writeUInt32(byteBuffer, this.timescale);
        IsoTypeWriter.writeUInt32(byteBuffer, this.presentationTimeDelta);
        IsoTypeWriter.writeUInt32(byteBuffer, this.eventDuration);
        IsoTypeWriter.writeUInt32(byteBuffer, this.id);
        byteBuffer.put(this.messageData);
    }

    protected long getContentSize() {
        return (long) (((Utf8.utf8StringLengthInBytes(this.schemeIdUri) + 22) + Utf8.utf8StringLengthInBytes(this.value)) + this.messageData.length);
    }
}
