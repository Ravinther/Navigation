package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.TagDescriptor;
import loquendo.tts.engine.TTSConst;

public class ExifThumbnailDescriptor extends TagDescriptor<ExifThumbnailDirectory> {
    private final boolean _allowDecimalRepresentationOfRationals;

    public ExifThumbnailDescriptor(ExifThumbnailDirectory directory) {
        super(directory);
        this._allowDecimalRepresentationOfRationals = true;
    }

    public String getDescription(int tagType) {
        switch (tagType) {
            case 256:
                return getThumbnailImageWidthDescription();
            case 257:
                return getThumbnailImageHeightDescription();
            case 258:
                return getBitsPerSampleDescription();
            case 259:
                return getCompressionDescription();
            case 262:
                return getPhotometricInterpretationDescription();
            case 274:
                return getOrientationDescription();
            case 277:
                return getSamplesPerPixelDescription();
            case 278:
                return getRowsPerStripDescription();
            case 279:
                return getStripByteCountsDescription();
            case 282:
                return getXResolutionDescription();
            case 283:
                return getYResolutionDescription();
            case 284:
                return getPlanarConfigurationDescription();
            case 296:
                return getResolutionDescription();
            case 513:
                return getThumbnailOffsetDescription();
            case 514:
                return getThumbnailLengthDescription();
            case 530:
                return getYCbCrSubsamplingDescription();
            case 531:
                return getYCbCrPositioningDescription();
            case 532:
                return getReferenceBlackWhiteDescription();
            default:
                return super.getDescription(tagType);
        }
    }

    public String getReferenceBlackWhiteDescription() {
        int[] ints = ((ExifThumbnailDirectory) this._directory).getIntArray(532);
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

    public String getYCbCrSubsamplingDescription() {
        int[] positions = ((ExifThumbnailDirectory) this._directory).getIntArray(530);
        if (positions == null || positions.length < 2) {
            return null;
        }
        if (positions[0] == 2 && positions[1] == 1) {
            return "YCbCr4:2:2";
        }
        if (positions[0] == 2 && positions[1] == 2) {
            return "YCbCr4:2:0";
        }
        return "(Unknown)";
    }

    public String getPlanarConfigurationDescription() {
        return getIndexedDescription(284, 1, "Chunky (contiguous for each subsampling pixel)", "Separate (Y-plane/Cb-plane/Cr-plane format)");
    }

    public String getSamplesPerPixelDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(277);
        return value == null ? null : value + " samples/pixel";
    }

    public String getRowsPerStripDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(278);
        return value == null ? null : value + " rows/strip";
    }

    public String getStripByteCountsDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(279);
        return value == null ? null : value + " bytes";
    }

    public String getPhotometricInterpretationDescription() {
        Integer value = ((ExifThumbnailDirectory) this._directory).getInteger(262);
        if (value == null) {
            return null;
        }
        switch (value.intValue()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "WhiteIsZero";
            case TTSConst.TTSMULTILINE /*1*/:
                return "BlackIsZero";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "RGB";
            case TTSConst.TTSUNICODE /*3*/:
                return "RGB Palette";
            case TTSConst.TTSXML /*4*/:
                return "Transparency Mask";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "CMYK";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "YCbCr";
            case TTSConst.TTSEVT_TAG /*8*/:
                return "CIELab";
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return "ICCLab";
            case TTSConst.TTSEVT_RESUME /*10*/:
                return "ITULab";
            case 32803:
                return "Color Filter Array";
            case 32844:
                return "Pixar LogL";
            case 32845:
                return "Pixar LogLuv";
            case 32892:
                return "Linear Raw";
            default:
                return "Unknown colour space";
        }
    }

    public String getCompressionDescription() {
        Integer value = ((ExifThumbnailDirectory) this._directory).getInteger(259);
        if (value == null) {
            return null;
        }
        switch (value.intValue()) {
            case TTSConst.TTSMULTILINE /*1*/:
                return "Uncompressed";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "CCITT 1D";
            case TTSConst.TTSUNICODE /*3*/:
                return "T4/Group 3 Fax";
            case TTSConst.TTSXML /*4*/:
                return "T6/Group 4 Fax";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "LZW";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "JPEG (old-style)";
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return "JPEG";
            case TTSConst.TTSEVT_TAG /*8*/:
                return "Adobe Deflate";
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return "JBIG B&W";
            case TTSConst.TTSEVT_RESUME /*10*/:
                return "JBIG Color";
            case 32661:
                return "JBIG";
            case 32676:
                return "SGILog";
            case 32677:
                return "SGILog24";
            case 32712:
                return "JPEG 2000";
            case 32713:
                return "Nikon NEF Compressed";
            case 32766:
                return "Next";
            case 32771:
                return "CCIRLEW";
            case 32773:
                return "PackBits";
            case 32809:
                return "Thunderscan";
            case 32895:
                return "IT8CTPAD";
            case 32896:
                return "IT8LW";
            case 32897:
                return "IT8MP";
            case 32898:
                return "IT8BL";
            case 32908:
                return "PixarFilm";
            case 32909:
                return "PixarLog";
            case 32946:
                return "Deflate";
            case 32947:
                return "DCS";
            default:
                return "Unknown compression";
        }
    }

    public String getBitsPerSampleDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(258);
        return value == null ? null : value + " bits/component/pixel";
    }

    public String getThumbnailImageWidthDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(256);
        return value == null ? null : value + " pixels";
    }

    public String getThumbnailImageHeightDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(257);
        return value == null ? null : value + " pixels";
    }

    public String getThumbnailLengthDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(514);
        return value == null ? null : value + " bytes";
    }

    public String getThumbnailOffsetDescription() {
        String value = ((ExifThumbnailDirectory) this._directory).getString(513);
        return value == null ? null : value + " bytes";
    }

    public String getYResolutionDescription() {
        Rational value = ((ExifThumbnailDirectory) this._directory).getRational(283);
        if (value == null) {
            return null;
        }
        String str;
        String unit = getResolutionDescription();
        StringBuilder append = new StringBuilder().append(value.toSimpleString(true)).append(" dots per ");
        if (unit == null) {
            str = "unit";
        } else {
            str = unit.toLowerCase();
        }
        return append.append(str).toString();
    }

    public String getXResolutionDescription() {
        Rational value = ((ExifThumbnailDirectory) this._directory).getRational(282);
        if (value == null) {
            return null;
        }
        String str;
        String unit = getResolutionDescription();
        StringBuilder append = new StringBuilder().append(value.toSimpleString(true)).append(" dots per ");
        if (unit == null) {
            str = "unit";
        } else {
            str = unit.toLowerCase();
        }
        return append.append(str).toString();
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
}
