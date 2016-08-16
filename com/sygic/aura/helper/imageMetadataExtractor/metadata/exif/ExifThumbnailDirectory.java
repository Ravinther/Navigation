package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import java.util.HashMap;

public class ExifThumbnailDirectory extends Directory {
    protected static final HashMap<Integer, String> _tagNameMap;
    private byte[] _thumbnailData;

    static {
        _tagNameMap = new HashMap();
        _tagNameMap.put(Integer.valueOf(256), "Thumbnail Image Width");
        _tagNameMap.put(Integer.valueOf(257), "Thumbnail Image Height");
        _tagNameMap.put(Integer.valueOf(258), "Bits Per Sample");
        _tagNameMap.put(Integer.valueOf(259), "Thumbnail Compression");
        _tagNameMap.put(Integer.valueOf(262), "Photometric Interpretation");
        _tagNameMap.put(Integer.valueOf(273), "Strip Offsets");
        _tagNameMap.put(Integer.valueOf(274), "Orientation");
        _tagNameMap.put(Integer.valueOf(277), "Samples Per Pixel");
        _tagNameMap.put(Integer.valueOf(278), "Rows Per Strip");
        _tagNameMap.put(Integer.valueOf(279), "Strip Byte Counts");
        _tagNameMap.put(Integer.valueOf(282), "X Resolution");
        _tagNameMap.put(Integer.valueOf(283), "Y Resolution");
        _tagNameMap.put(Integer.valueOf(284), "Planar Configuration");
        _tagNameMap.put(Integer.valueOf(296), "Resolution Unit");
        _tagNameMap.put(Integer.valueOf(513), "Thumbnail Offset");
        _tagNameMap.put(Integer.valueOf(514), "Thumbnail Length");
        _tagNameMap.put(Integer.valueOf(529), "YCbCr Coefficients");
        _tagNameMap.put(Integer.valueOf(530), "YCbCr Sub-Sampling");
        _tagNameMap.put(Integer.valueOf(531), "YCbCr Positioning");
        _tagNameMap.put(Integer.valueOf(532), "Reference Black/White");
    }

    public ExifThumbnailDirectory() {
        setDescriptor(new ExifThumbnailDescriptor(this));
    }

    public String getName() {
        return "Exif Thumbnail";
    }

    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }

    public void setThumbnailData(byte[] data) {
        this._thumbnailData = data;
    }
}
