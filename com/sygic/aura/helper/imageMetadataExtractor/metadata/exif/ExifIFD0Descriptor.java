package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.TagDescriptor;
import java.io.UnsupportedEncodingException;

public class ExifIFD0Descriptor extends TagDescriptor<ExifIFD0Directory> {
    private final boolean _allowDecimalRepresentationOfRationals;

    public ExifIFD0Descriptor(ExifIFD0Directory directory) {
        super(directory);
        this._allowDecimalRepresentationOfRationals = true;
    }

    public String getDescription(int tagType) {
        switch (tagType) {
            case 274:
                return getOrientationDescription();
            case 282:
                return getXResolutionDescription();
            case 283:
                return getYResolutionDescription();
            case 296:
                return getResolutionDescription();
            case 531:
                return getYCbCrPositioningDescription();
            case 532:
                return getReferenceBlackWhiteDescription();
            case 40091:
                return getWindowsTitleDescription();
            case 40092:
                return getWindowsCommentDescription();
            case 40093:
                return getWindowsAuthorDescription();
            case 40094:
                return getWindowsKeywordsDescription();
            case 40095:
                return getWindowsSubjectDescription();
            default:
                return super.getDescription(tagType);
        }
    }

    public String getReferenceBlackWhiteDescription() {
        int[] ints = ((ExifIFD0Directory) this._directory).getIntArray(532);
        if (ints == null || ints.length < 6) {
            return null;
        }
        int blackR = ints[0];
        int whiteR = ints[1];
        int blackG = ints[2];
        int whiteG = ints[3];
        int blackB = ints[4];
        int whiteB = ints[5];
        return String.format("[%d,%d,%d] [%d,%d,%d]", new Object[]{Integer.valueOf(blackR), Integer.valueOf(blackG), Integer.valueOf(blackB), Integer.valueOf(whiteR), Integer.valueOf(whiteG), Integer.valueOf(whiteB)});
    }

    public String getYResolutionDescription() {
        Rational value = ((ExifIFD0Directory) this._directory).getRational(283);
        if (value == null) {
            return null;
        }
        String str;
        String unit = getResolutionDescription();
        String str2 = "%s dots per %s";
        Object[] objArr = new Object[2];
        objArr[0] = value.toSimpleString(true);
        if (unit == null) {
            str = "unit";
        } else {
            str = unit.toLowerCase();
        }
        objArr[1] = str;
        return String.format(str2, objArr);
    }

    public String getXResolutionDescription() {
        Rational value = ((ExifIFD0Directory) this._directory).getRational(282);
        if (value == null) {
            return null;
        }
        String str;
        String unit = getResolutionDescription();
        String str2 = "%s dots per %s";
        Object[] objArr = new Object[2];
        objArr[0] = value.toSimpleString(true);
        if (unit == null) {
            str = "unit";
        } else {
            str = unit.toLowerCase();
        }
        objArr[1] = str;
        return String.format(str2, objArr);
    }

    public String getYCbCrPositioningDescription() {
        return getIndexedDescription(531, 1, "Center of pixel array", "Datum point");
    }

    public String getOrientationDescription() {
        return getIndexedDescription(274, 1, "Top, left side (Horizontal / normal)", "Top, right side (Mirror horizontal)", "Bottom, right side (Rotate 180)", "Bottom, left side (Mirror vertical)", "Left side, top (Mirror horizontal and rotate 270 CW)", "Right side, top (Rotate 90 CW)", "Right side, bottom (Mirror horizontal and rotate 90 CW)", "Left side, bottom (Rotate 270 CW)");
    }

    public String getResolutionDescription() {
        return getIndexedDescription(296, 1, "(No unit)", "Inch", "cm");
    }

    private String getUnicodeDescription(int tag) {
        byte[] bytes = ((ExifIFD0Directory) this._directory).getByteArray(tag);
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, "UTF-16LE").trim();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String getWindowsAuthorDescription() {
        return getUnicodeDescription(40093);
    }

    public String getWindowsCommentDescription() {
        return getUnicodeDescription(40092);
    }

    public String getWindowsKeywordsDescription() {
        return getUnicodeDescription(40094);
    }

    public String getWindowsTitleDescription() {
        return getUnicodeDescription(40091);
    }

    public String getWindowsSubjectDescription() {
        return getUnicodeDescription(40095);
    }
}
