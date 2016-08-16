package com.coremedia.iso.boxes;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.List;

public interface Container {
    List<Box> getBoxes();

    <T extends Box> List<T> getBoxes(Class<T> cls);

    ByteBuffer getByteBuffer(long j, long j2) throws IOException;

    void writeContainer(WritableByteChannel writableByteChannel) throws IOException;
}
