package com.coremedia.iso;

import java.nio.ByteBuffer;
import loquendo.tts.engine.TTSConst;

public final class IsoTypeWriterVariable {
    public static void write(long v, ByteBuffer bb, int bytes) {
        switch (bytes) {
            case TTSConst.TTSMULTILINE /*1*/:
                IsoTypeWriter.writeUInt8(bb, (int) (255 & v));
            case TTSConst.TTSPARAGRAPH /*2*/:
                IsoTypeWriter.writeUInt16(bb, (int) (65535 & v));
            case TTSConst.TTSUNICODE /*3*/:
                IsoTypeWriter.writeUInt24(bb, (int) (16777215 & v));
            case TTSConst.TTSXML /*4*/:
                IsoTypeWriter.writeUInt32(bb, v);
            case TTSConst.TTSEVT_TAG /*8*/:
                IsoTypeWriter.writeUInt64(bb, v);
            default:
                throw new RuntimeException("I don't know how to read " + bytes + " bytes");
        }
    }
}
