package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import com.googlecode.mp4parser.util.CastUtils;
import java.nio.ByteBuffer;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class ChunkOffset64BitBox extends ChunkOffsetBox {
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private long[] chunkOffsets;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("ChunkOffset64BitBox.java", ChunkOffset64BitBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getChunkOffsets", "com.coremedia.iso.boxes.ChunkOffset64BitBox", "", "", "", "[J"), 23);
    }

    public ChunkOffset64BitBox() {
        super("co64");
    }

    public long[] getChunkOffsets() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_0, this, this));
        return this.chunkOffsets;
    }

    protected long getContentSize() {
        return (long) ((this.chunkOffsets.length * 8) + 8);
    }

    public void _parseDetails(ByteBuffer content) {
        parseVersionAndFlags(content);
        int entryCount = CastUtils.l2i(IsoTypeReader.readUInt32(content));
        this.chunkOffsets = new long[entryCount];
        for (int i = 0; i < entryCount; i++) {
            this.chunkOffsets[i] = IsoTypeReader.readUInt64(content);
        }
    }

    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeUInt32(byteBuffer, (long) this.chunkOffsets.length);
        for (long chunkOffset : this.chunkOffsets) {
            IsoTypeWriter.writeUInt64(byteBuffer, chunkOffset);
        }
    }
}
