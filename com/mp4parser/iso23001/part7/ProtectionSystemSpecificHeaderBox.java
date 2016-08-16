package com.mp4parser.iso23001.part7;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.UUIDConverter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class ProtectionSystemSpecificHeaderBox extends AbstractFullBox {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static byte[] OMA2_SYSTEM_ID;
    public static byte[] PLAYREADY_SYSTEM_ID;
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_3 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_4 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_5 = null;
    byte[] content;
    List<UUID> keyIds;
    byte[] systemId;

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("ProtectionSystemSpecificHeaderBox.java", ProtectionSystemSpecificHeaderBox.class);
        ajc$tjp_0 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getKeyIds", "com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox", "", "", "", "java.util.List"), 43);
        ajc$tjp_1 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setKeyIds", "com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox", "java.util.List", "keyIds", "", "void"), 47);
        ajc$tjp_2 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getSystemId", "com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox", "", "", "", "[B"), 54);
        ajc$tjp_3 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setSystemId", "com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox", "[B", "systemId", "", "void"), 58);
        ajc$tjp_4 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "getContent", "com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox", "", "", "", "[B"), 63);
        ajc$tjp_5 = factory.makeSJP("method-execution", factory.makeMethodSig("1", "setContent", "com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox", "[B", "content", "", "void"), 67);
    }

    static {
        ajc$preClinit();
        $assertionsDisabled = !ProtectionSystemSpecificHeaderBox.class.desiredAssertionStatus();
        OMA2_SYSTEM_ID = UUIDConverter.convert(UUID.fromString("A2B55680-6F43-11E0-9A3F-0002A5D5C51B"));
        PLAYREADY_SYSTEM_ID = UUIDConverter.convert(UUID.fromString("9A04F079-9840-4286-AB92-E65BE0885F95"));
    }

    public ProtectionSystemSpecificHeaderBox() {
        super("pssh");
        this.keyIds = new ArrayList();
    }

    protected long getContentSize() {
        long l = (long) (this.content.length + 24);
        if (getVersion() > 0) {
            return (l + 4) + ((long) (this.keyIds.size() * 16));
        }
        return l;
    }

    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        if ($assertionsDisabled || this.systemId.length == 16) {
            byteBuffer.put(this.systemId, 0, 16);
            if (getVersion() > 0) {
                IsoTypeWriter.writeUInt32(byteBuffer, (long) this.keyIds.size());
                for (UUID keyId : this.keyIds) {
                    byteBuffer.put(UUIDConverter.convert(keyId));
                }
            }
            IsoTypeWriter.writeUInt32(byteBuffer, (long) this.content.length);
            byteBuffer.put(this.content);
            return;
        }
        throw new AssertionError();
    }

    protected void _parseDetails(ByteBuffer content) {
        parseVersionAndFlags(content);
        this.systemId = new byte[16];
        content.get(this.systemId);
        if (getVersion() > 0) {
            int count = CastUtils.l2i(IsoTypeReader.readUInt32(content));
            while (true) {
                int count2 = count - 1;
                if (count <= 0) {
                    break;
                }
                byte[] k = new byte[16];
                content.get(k);
                this.keyIds.add(UUIDConverter.convert(k));
                count = count2;
            }
        }
        long length = IsoTypeReader.readUInt32(content);
        this.content = new byte[content.remaining()];
        content.get(this.content);
        if (!$assertionsDisabled && length != ((long) this.content.length)) {
            throw new AssertionError();
        }
    }
}
