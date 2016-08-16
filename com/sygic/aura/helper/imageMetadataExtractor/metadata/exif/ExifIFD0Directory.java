package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import java.util.HashMap;

public class ExifIFD0Directory extends Directory {
    protected static final HashMap<Integer, String> _tagNameMap;

    static {
        _tagNameMap = new HashMap();
        _tagNameMap.put(Integer.valueOf(270), "Image Description");
        _tagNameMap.put(Integer.valueOf(271), "Make");
        _tagNameMap.put(Integer.valueOf(272), "Model");
        _tagNameMap.put(Integer.valueOf(274), "Orientation");
        _tagNameMap.put(Integer.valueOf(282), "X Resolution");
        _tagNameMap.put(Integer.valueOf(283), "Y Resolution");
        _tagNameMap.put(Integer.valueOf(296), "Resolution Unit");
        _tagNameMap.put(Integer.valueOf(305), "Software");
        _tagNameMap.put(Integer.valueOf(306), "Date/Time");
        _tagNameMap.put(Integer.valueOf(315), "Artist");
        _tagNameMap.put(Integer.valueOf(318), "White Point");
        _tagNameMap.put(Integer.valueOf(319), "Primary Chromaticities");
        _tagNameMap.put(Integer.valueOf(529), "YCbCr Coefficients");
        _tagNameMap.put(Integer.valueOf(531), "YCbCr Positioning");
        _tagNameMap.put(Integer.valueOf(532), "Reference Black/White");
        _tagNameMap.put(Integer.valueOf(33432), "Copyright");
        _tagNameMap.put(Integer.valueOf(34858), "Time Zone Offset");
        _tagNameMap.put(Integer.valueOf(40093), "Windows XP Author");
        _tagNameMap.put(Integer.valueOf(40092), "Windows XP Comment");
        _tagNameMap.put(Integer.valueOf(40094), "Windows XP Keywords");
        _tagNameMap.put(Integer.valueOf(40095), "Windows XP Subject");
        _tagNameMap.put(Integer.valueOf(40091), "Windows XP Title");
    }

    public ExifIFD0Directory() {
        setDescriptor(new ExifIFD0Descriptor(this));
    }

    public String getName() {
        return "Exif IFD0";
    }

    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
