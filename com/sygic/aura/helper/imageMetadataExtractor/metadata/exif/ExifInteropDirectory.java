package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import java.util.HashMap;

public class ExifInteropDirectory extends Directory {
    protected static final HashMap<Integer, String> _tagNameMap;

    static {
        _tagNameMap = new HashMap();
        _tagNameMap.put(Integer.valueOf(1), "Interoperability Index");
        _tagNameMap.put(Integer.valueOf(2), "Interoperability Version");
        _tagNameMap.put(Integer.valueOf(4096), "Related Image File Format");
        _tagNameMap.put(Integer.valueOf(4097), "Related Image Width");
        _tagNameMap.put(Integer.valueOf(4098), "Related Image Length");
    }

    public ExifInteropDirectory() {
        setDescriptor(new ExifInteropDescriptor(this));
    }

    public String getName() {
        return "Interoperability";
    }

    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
