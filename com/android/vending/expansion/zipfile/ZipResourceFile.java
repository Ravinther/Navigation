package com.android.vending.expansion.zipfile;

import android.content.res.AssetFileDescriptor;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipResourceFile {
    private HashMap<String, ZipEntryRO> mHashMap;
    ByteBuffer mLEByteBuffer;
    public HashMap<File, ZipFile> mZipFiles;

    public static final class ZipEntryRO {
        public long mCRC32;
        public long mCompressedLength;
        public final File mFile;
        public final String mFileName;
        public long mLocalHdrOffset;
        public int mMethod;
        public long mOffset;
        public long mUncompressedLength;
        public long mWhenModified;
        public final String mZipFileName;

        public ZipEntryRO(String zipFileName, File file, String fileName) {
            this.mOffset = -1;
            this.mFileName = fileName;
            this.mZipFileName = zipFileName;
            this.mFile = file;
        }

        public void setOffsetFromFile(RandomAccessFile f, ByteBuffer buf) throws IOException {
            long localHdrOffset = this.mLocalHdrOffset;
            try {
                f.seek(localHdrOffset);
                f.readFully(buf.array());
                if (buf.getInt(0) != 67324752) {
                    Log.w("zipro", "didn't find signature at start of lfh");
                    throw new IOException();
                }
                this.mOffset = ((30 + localHdrOffset) + ((long) (buf.getShort(26) & 65535))) + ((long) (buf.getShort(28) & 65535));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        public long getOffset() {
            return this.mOffset;
        }

        public boolean isUncompressed() {
            return this.mMethod == 0;
        }

        public AssetFileDescriptor getAssetFileDescriptor() {
            if (this.mMethod == 0) {
                try {
                    return new AssetFileDescriptor(ParcelFileDescriptor.open(this.mFile, 268435456), getOffset(), this.mUncompressedLength);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public File getZipFile() {
            return this.mFile;
        }
    }

    private static int swapEndian(int i) {
        return ((((i & 255) << 24) + ((65280 & i) << 8)) + ((16711680 & i) >>> 8)) + ((i >>> 24) & 255);
    }

    public ZipResourceFile(String zipFileName) throws IOException {
        this.mHashMap = new HashMap();
        this.mZipFiles = new HashMap();
        this.mLEByteBuffer = ByteBuffer.allocate(4);
        addPatchFile(zipFileName);
    }

    public ZipEntryRO[] getAllEntries() {
        Collection<ZipEntryRO> values = this.mHashMap.values();
        return (ZipEntryRO[]) values.toArray(new ZipEntryRO[values.size()]);
    }

    public InputStream getInputStream(String assetPath) throws IOException {
        ZipEntryRO entry = (ZipEntryRO) this.mHashMap.get(assetPath);
        if (entry != null) {
            if (entry.isUncompressed()) {
                return entry.getAssetFileDescriptor().createInputStream();
            }
            ZipFile zf = (ZipFile) this.mZipFiles.get(entry.getZipFile());
            if (zf == null) {
                zf = new ZipFile(entry.getZipFile(), 1);
                this.mZipFiles.put(entry.getZipFile(), zf);
            }
            ZipEntry zi = zf.getEntry(assetPath);
            if (zi != null) {
                return zf.getInputStream(zi);
            }
        }
        return null;
    }

    private static int read4LE(RandomAccessFile f) throws EOFException, IOException {
        return swapEndian(f.readInt());
    }

    void addPatchFile(String zipFileName) throws IOException {
        File file = new File(zipFileName);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        long fileLength = randomAccessFile.length();
        if (fileLength < 22) {
            throw new IOException();
        }
        long readAmount = 65557;
        if (65557 > fileLength) {
            readAmount = fileLength;
        }
        randomAccessFile.seek(0);
        int header = read4LE(randomAccessFile);
        if (header == 101010256) {
            Log.i("zipro", "Found Zip archive, but it looks empty");
            throw new IOException();
        } else if (header != 67324752) {
            Log.v("zipro", "Not a Zip archive");
            throw new IOException();
        } else {
            String str;
            randomAccessFile.seek(fileLength - readAmount);
            ByteBuffer bbuf = ByteBuffer.allocate((int) readAmount);
            byte[] buffer = bbuf.array();
            randomAccessFile.readFully(buffer);
            bbuf.order(ByteOrder.LITTLE_ENDIAN);
            int eocdIdx = buffer.length - 22;
            while (eocdIdx >= 0 && (buffer[eocdIdx] != 80 || bbuf.getInt(eocdIdx) != 101010256)) {
                eocdIdx--;
            }
            if (eocdIdx < 0) {
                str = " is not zip";
                Log.d("zipro", "Zip: EOCD not found, " + zipFileName + r33);
            }
            int numEntries = bbuf.getShort(eocdIdx + 8);
            long dirSize = ((long) bbuf.getInt(eocdIdx + 12)) & 4294967295L;
            long dirOffset = ((long) bbuf.getInt(eocdIdx + 16)) & 4294967295L;
            if (dirOffset + dirSize > fileLength) {
                str = ", size ";
                str = ", eocd ";
                str = ")";
                Log.w("zipro", "bad offsets (dir " + dirOffset + r33 + dirSize + r33 + eocdIdx + r33);
                throw new IOException();
            } else if (numEntries == 0) {
                Log.w("zipro", "empty archive?");
                throw new IOException();
            } else {
                MappedByteBuffer directoryMap = randomAccessFile.getChannel().map(MapMode.READ_ONLY, dirOffset, dirSize);
                directoryMap.order(ByteOrder.LITTLE_ENDIAN);
                byte[] tempBuf = new byte[65535];
                int currentOffset = 0;
                ByteBuffer buf = ByteBuffer.allocate(30);
                buf.order(ByteOrder.LITTLE_ENDIAN);
                for (int i = 0; i < numEntries; i++) {
                    if (directoryMap.getInt(currentOffset) != 33639248) {
                        str = ")";
                        Log.w("zipro", "Missed a central dir sig (at " + currentOffset + r33);
                        throw new IOException();
                    }
                    int fileNameLen = directoryMap.getShort(currentOffset + 28) & 65535;
                    int extraLen = directoryMap.getShort(currentOffset + 30) & 65535;
                    int commentLen = directoryMap.getShort(currentOffset + 32) & 65535;
                    directoryMap.position(currentOffset + 46);
                    directoryMap.get(tempBuf, 0, fileNameLen);
                    directoryMap.position(0);
                    str = new String(tempBuf, 0, fileNameLen);
                    ZipEntryRO zipEntryRO = new ZipEntryRO(zipFileName, file, str);
                    zipEntryRO.mMethod = directoryMap.getShort(currentOffset + 10) & 65535;
                    zipEntryRO.mWhenModified = ((long) directoryMap.getInt(currentOffset + 12)) & 4294967295L;
                    zipEntryRO.mCRC32 = directoryMap.getLong(currentOffset + 16) & 4294967295L;
                    zipEntryRO.mCompressedLength = directoryMap.getLong(currentOffset + 20) & 4294967295L;
                    zipEntryRO.mUncompressedLength = directoryMap.getLong(currentOffset + 24) & 4294967295L;
                    zipEntryRO.mLocalHdrOffset = ((long) directoryMap.getInt(currentOffset + 42)) & 4294967295L;
                    buf.clear();
                    zipEntryRO.setOffsetFromFile(randomAccessFile, buf);
                    this.mHashMap.put(str, zipEntryRO);
                    currentOffset += ((fileNameLen + 46) + extraLen) + commentLen;
                }
            }
        }
    }
}
