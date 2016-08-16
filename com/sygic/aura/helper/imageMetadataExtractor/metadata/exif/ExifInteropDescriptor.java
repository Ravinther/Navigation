package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.metadata.TagDescriptor;
import loquendo.tts.engine.TTSConst;

public class ExifInteropDescriptor extends TagDescriptor<ExifInteropDirectory> {
    public ExifInteropDescriptor(ExifInteropDirectory directory) {
        super(directory);
    }

    public String getDescription(int tagType) {
        switch (tagType) {
            case TTSConst.TTSMULTILINE /*1*/:
                return getInteropIndexDescription();
            case TTSConst.TTSPARAGRAPH /*2*/:
                return getInteropVersionDescription();
            default:
                return super.getDescription(tagType);
        }
    }

    public String getInteropVersionDescription() {
        return getVersionBytesDescription(2, 2);
    }

    public String getInteropIndexDescription() {
        String value = ((ExifInteropDirectory) this._directory).getString(1);
        if (value == null) {
            return null;
        }
        return "R98".equalsIgnoreCase(value.trim()) ? "Recommended Exif Interoperability Rules (ExifR98)" : "Unknown (" + value + ")";
    }
}
