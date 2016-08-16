package com.googlecode.mp4parser.contentprotection;

import com.googlecode.mp4parser.boxes.piff.ProtectionSpecificHeader;
import java.nio.ByteBuffer;
import java.util.UUID;

public class GenericHeader extends ProtectionSpecificHeader {
    public static UUID PROTECTION_SYSTEM_ID;
    ByteBuffer data;

    static {
        PROTECTION_SYSTEM_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
        ProtectionSpecificHeader.uuidRegistry.put(PROTECTION_SYSTEM_ID, GenericHeader.class);
    }

    public void parse(ByteBuffer buffer) {
        this.data = buffer;
    }

    public ByteBuffer getData() {
        return this.data;
    }
}
