package com.coremedia.iso;

import java.nio.ByteBuffer;
import loquendo.tts.engine.TTSConst;

public final class IsoTypeReaderVariable {
    public static long read(ByteBuffer bb, int bytes) {
        switch (bytes) {
            case TTSConst.TTSMULTILINE /*1*/:
                return (long) IsoTypeReader.readUInt8(bb);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return (long) IsoTypeReader.readUInt16(bb);
            case TTSConst.TTSUNICODE /*3*/:
                return (long) IsoTypeReader.readUInt24(bb);
            case TTSConst.TTSXML /*4*/:
                return IsoTypeReader.readUInt32(bb);
            case TTSConst.TTSEVT_TAG /*8*/:
                return IsoTypeReader.readUInt64(bb);
            default:
                throw new RuntimeException("I don't know how to read " + bytes + " bytes");
        }
    }
}
