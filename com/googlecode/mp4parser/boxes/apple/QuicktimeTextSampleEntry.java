package com.googlecode.mp4parser.boxes.apple;

import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.sampleentry.AbstractSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.List;

public class QuicktimeTextSampleEntry extends AbstractSampleEntry {
    int backgroundB;
    int backgroundG;
    int backgroundR;
    int dataReferenceIndex;
    long defaultTextBox;
    int displayFlags;
    short fontFace;
    String fontName;
    short fontNumber;
    int foregroundB;
    int foregroundG;
    int foregroundR;
    long reserved1;
    byte reserved2;
    short reserved3;
    int textJustification;

    public QuicktimeTextSampleEntry() {
        super("text");
        this.foregroundR = 65535;
        this.foregroundG = 65535;
        this.foregroundB = 65535;
        this.fontName = "";
    }

    public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        ByteBuffer content = ByteBuffer.allocate(CastUtils.l2i(contentSize));
        dataSource.read(content);
        content.position(6);
        this.dataReferenceIndex = IsoTypeReader.readUInt16(content);
        this.displayFlags = content.getInt();
        this.textJustification = content.getInt();
        this.backgroundR = IsoTypeReader.readUInt16(content);
        this.backgroundG = IsoTypeReader.readUInt16(content);
        this.backgroundB = IsoTypeReader.readUInt16(content);
        this.defaultTextBox = IsoTypeReader.readUInt64(content);
        this.reserved1 = IsoTypeReader.readUInt64(content);
        this.fontNumber = content.getShort();
        this.fontFace = content.getShort();
        this.reserved2 = content.get();
        this.reserved3 = content.getShort();
        this.foregroundR = IsoTypeReader.readUInt16(content);
        this.foregroundG = IsoTypeReader.readUInt16(content);
        this.foregroundB = IsoTypeReader.readUInt16(content);
        if (content.remaining() > 0) {
            byte[] myFontName = new byte[IsoTypeReader.readUInt8(content)];
            content.get(myFontName);
            this.fontName = new String(myFontName);
            return;
        }
        this.fontName = null;
    }

    public void setBoxes(List<Box> list) {
        throw new RuntimeException("QuicktimeTextSampleEntries may not have child boxes");
    }

    public void addBox(Box box) {
        throw new RuntimeException("QuicktimeTextSampleEntries may not have child boxes");
    }

    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        writableByteChannel.write(getHeader());
        ByteBuffer byteBuffer = ByteBuffer.allocate((this.fontName != null ? this.fontName.length() : 0) + 52);
        byteBuffer.position(6);
        IsoTypeWriter.writeUInt16(byteBuffer, this.dataReferenceIndex);
        byteBuffer.putInt(this.displayFlags);
        byteBuffer.putInt(this.textJustification);
        IsoTypeWriter.writeUInt16(byteBuffer, this.backgroundR);
        IsoTypeWriter.writeUInt16(byteBuffer, this.backgroundG);
        IsoTypeWriter.writeUInt16(byteBuffer, this.backgroundB);
        IsoTypeWriter.writeUInt64(byteBuffer, this.defaultTextBox);
        IsoTypeWriter.writeUInt64(byteBuffer, this.reserved1);
        byteBuffer.putShort(this.fontNumber);
        byteBuffer.putShort(this.fontFace);
        byteBuffer.put(this.reserved2);
        byteBuffer.putShort(this.reserved3);
        IsoTypeWriter.writeUInt16(byteBuffer, this.foregroundR);
        IsoTypeWriter.writeUInt16(byteBuffer, this.foregroundG);
        IsoTypeWriter.writeUInt16(byteBuffer, this.foregroundB);
        if (this.fontName != null) {
            IsoTypeWriter.writeUInt8(byteBuffer, this.fontName.length());
            byteBuffer.put(this.fontName.getBytes());
        }
        writableByteChannel.write((ByteBuffer) byteBuffer.rewind());
    }

    public long getSize() {
        long s = (52 + getContainerSize()) + ((long) (this.fontName != null ? this.fontName.length() : 0));
        int i = (this.largeBox || 8 + s >= 4294967296L) ? 16 : 8;
        return s + ((long) i);
    }
}
