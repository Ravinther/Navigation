package com.googlecode.mp4parser.boxes.mp4.samplegrouping;

import java.nio.ByteBuffer;

public class RollRecoveryEntry extends GroupEntry {
    private short rollDistance;

    public String getType() {
        return "roll";
    }

    public void parse(ByteBuffer byteBuffer) {
        this.rollDistance = byteBuffer.getShort();
    }

    public ByteBuffer get() {
        ByteBuffer content = ByteBuffer.allocate(2);
        content.putShort(this.rollDistance);
        content.rewind();
        return content;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.rollDistance != ((RollRecoveryEntry) o).rollDistance) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.rollDistance;
    }
}
