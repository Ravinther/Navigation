package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.imaging.PhotographicConversions;
import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.TagDescriptor;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import loquendo.tts.engine.TTSConst;

public class ExifSubIFDDescriptor extends TagDescriptor<ExifSubIFDDirectory> {
    private static final DecimalFormat SimpleDecimalFormatter;
    private final boolean _allowDecimalRepresentationOfRationals;

    static {
        SimpleDecimalFormatter = new DecimalFormat("0.#");
    }

    public ExifSubIFDDescriptor(ExifSubIFDDirectory directory) {
        super(directory);
        this._allowDecimalRepresentationOfRationals = true;
    }

    public String getDescription(int tagType) {
        switch (tagType) {
            case 254:
                return getNewSubfileTypeDescription();
            case 255:
                return getSubfileTypeDescription();
            case 258:
                return getBitsPerSampleDescription();
            case 262:
                return getPhotometricInterpretationDescription();
            case 263:
                return getThresholdingDescription();
            case 266:
                return getFillOrderDescription();
            case 277:
                return getSamplesPerPixelDescription();
            case 278:
                return getRowsPerStripDescription();
            case 279:
                return getStripByteCountsDescription();
            case 284:
                return getPlanarConfigurationDescription();
            case 530:
                return getYCbCrSubsamplingDescription();
            case 33434:
                return getExposureTimeDescription();
            case 33437:
                return getFNumberDescription();
            case 34850:
                return getExposureProgramDescription();
            case 34855:
                return getIsoEquivalentDescription();
            case 36864:
                return getExifVersionDescription();
            case 37121:
                return getComponentConfigurationDescription();
            case 37122:
                return getCompressedAverageBitsPerPixelDescription();
            case 37377:
                return getShutterSpeedDescription();
            case 37378:
                return getApertureValueDescription();
            case 37380:
                return getExposureBiasDescription();
            case 37381:
                return getMaxApertureValueDescription();
            case 37382:
                return getSubjectDistanceDescription();
            case 37383:
                return getMeteringModeDescription();
            case 37384:
                return getWhiteBalanceDescription();
            case 37385:
                return getFlashDescription();
            case 37386:
                return getFocalLengthDescription();
            case 37510:
                return getUserCommentDescription();
            case 40960:
                return getFlashPixVersionDescription();
            case 40961:
                return getColorSpaceDescription();
            case 40962:
                return getExifImageWidthDescription();
            case 40963:
                return getExifImageHeightDescription();
            case 41486:
                return getFocalPlaneXResolutionDescription();
            case 41487:
                return getFocalPlaneYResolutionDescription();
            case 41488:
                return getFocalPlaneResolutionUnitDescription();
            case 41495:
                return getSensingMethodDescription();
            case 41728:
                return getFileSourceDescription();
            case 41729:
                return getSceneTypeDescription();
            case 41985:
                return getCustomRenderedDescription();
            case 41986:
                return getExposureModeDescription();
            case 41987:
                return getWhiteBalanceModeDescription();
            case 41988:
                return getDigitalZoomRatioDescription();
            case 41989:
                return get35mmFilmEquivFocalLengthDescription();
            case 41990:
                return getSceneCaptureTypeDescription();
            case 41991:
                return getGainControlDescription();
            case 41992:
                return getContrastDescription();
            case 41993:
                return getSaturationDescription();
            case 41994:
                return getSharpnessDescription();
            case 41996:
                return getSubjectDistanceRangeDescription();
            default:
                return super.getDescription(tagType);
        }
    }

    public String getNewSubfileTypeDescription() {
        return getIndexedDescription(254, 1, "Full-resolution image", "Reduced-resolution image", "Single page of multi-page reduced-resolution image", "Transparency mask", "Transparency mask of reduced-resolution image", "Transparency mask of multi-page image", "Transparency mask of reduced-resolution multi-page image");
    }

    public String getSubfileTypeDescription() {
        return getIndexedDescription(255, 1, "Full-resolution image", "Reduced-resolution image", "Single page of multi-page image");
    }

    public String getThresholdingDescription() {
        return getIndexedDescription(263, 1, "No dithering or halftoning", "Ordered dither or halftone", "Randomized dither");
    }

    public String getFillOrderDescription() {
        return getIndexedDescription(266, 1, "Normal", "Reversed");
    }

    public String getSubjectDistanceRangeDescription() {
        return getIndexedDescription(41996, "Unknown", "Macro", "Close view", "Distant view");
    }

    public String getSharpnessDescription() {
        return getIndexedDescription(41994, "None", "Low", "Hard");
    }

    public String getSaturationDescription() {
        return getIndexedDescription(41993, "None", "Low saturation", "High saturation");
    }

    public String getContrastDescription() {
        return getIndexedDescription(41992, "None", "Soft", "Hard");
    }

    public String getGainControlDescription() {
        return getIndexedDescription(41991, "None", "Low gain up", "Low gain down", "High gain up", "High gain down");
    }

    public String getSceneCaptureTypeDescription() {
        return getIndexedDescription(41990, "Standard", "Landscape", "Portrait", "Night scene");
    }

    public String get35mmFilmEquivFocalLengthDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(41989);
        if (value == null) {
            return null;
        }
        if (value.intValue() == 0) {
            return "Unknown";
        }
        return SimpleDecimalFormatter.format(value) + "mm";
    }

    public String getDigitalZoomRatioDescription() {
        Rational value = ((ExifSubIFDDirectory) this._directory).getRational(41988);
        if (value == null) {
            return null;
        }
        if (value.getNumerator() == 0) {
            return "Digital zoom not used.";
        }
        return SimpleDecimalFormatter.format(value.doubleValue());
    }

    public String getWhiteBalanceModeDescription() {
        return getIndexedDescription(41987, "Auto white balance", "Manual white balance");
    }

    public String getExposureModeDescription() {
        return getIndexedDescription(41986, "Auto exposure", "Manual exposure", "Auto bracket");
    }

    public String getCustomRenderedDescription() {
        return getIndexedDescription(41985, "Normal process", "Custom process");
    }

    public String getUserCommentDescription() {
        byte[] commentBytes = ((ExifSubIFDDirectory) this._directory).getByteArray(37510);
        if (commentBytes == null) {
            return null;
        }
        if (commentBytes.length == 0) {
            return "";
        }
        Map<String, String> encodingMap = new HashMap();
        encodingMap.put("ASCII", System.getProperty("file.encoding"));
        encodingMap.put("UNICODE", "UTF-16LE");
        encodingMap.put("JIS", "Shift-JIS");
        try {
            if (commentBytes.length >= 10) {
                String firstTenBytesString = new String(commentBytes, 0, 10);
                for (Entry<String, String> pair : encodingMap.entrySet()) {
                    String encodingName = (String) pair.getKey();
                    String charset = (String) pair.getValue();
                    if (firstTenBytesString.startsWith(encodingName)) {
                        for (int j = encodingName.length(); j < 10; j++) {
                            byte b = commentBytes[j];
                            if (b != null && b != 32) {
                                return new String(commentBytes, j, commentBytes.length - j, charset).trim();
                            }
                        }
                        return new String(commentBytes, 10, commentBytes.length - 10, charset).trim();
                    }
                }
            }
            return new String(commentBytes, System.getProperty("file.encoding")).trim();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String getIsoEquivalentDescription() {
        Integer isoEquiv = ((ExifSubIFDDirectory) this._directory).getInteger(34855);
        return isoEquiv != null ? Integer.toString(isoEquiv.intValue()) : null;
    }

    public String getExifVersionDescription() {
        return getVersionBytesDescription(36864, 2);
    }

    public String getFlashPixVersionDescription() {
        return getVersionBytesDescription(40960, 2);
    }

    public String getSceneTypeDescription() {
        return getIndexedDescription(41729, 1, "Directly photographed image");
    }

    public String getFileSourceDescription() {
        return getIndexedDescription(41728, 1, "Film Scanner", "Reflection Print Scanner", "Digital Still Camera (DSC)");
    }

    public String getExposureBiasDescription() {
        Rational value = ((ExifSubIFDDirectory) this._directory).getRational(37380);
        if (value == null) {
            return null;
        }
        return value.toSimpleString(true) + " EV";
    }

    public String getMaxApertureValueDescription() {
        Double aperture = ((ExifSubIFDDirectory) this._directory).getDoubleObject(37381);
        if (aperture == null) {
            return null;
        }
        return "F" + SimpleDecimalFormatter.format(PhotographicConversions.apertureToFStop(aperture.doubleValue()));
    }

    public String getApertureValueDescription() {
        Double aperture = ((ExifSubIFDDirectory) this._directory).getDoubleObject(37378);
        if (aperture == null) {
            return null;
        }
        return "F" + SimpleDecimalFormatter.format(PhotographicConversions.apertureToFStop(aperture.doubleValue()));
    }

    public String getExposureProgramDescription() {
        return getIndexedDescription(34850, 1, "Manual control", "Program normal", "Aperture priority", "Shutter priority", "Program creative (slow program)", "Program action (high-speed program)", "Portrait mode", "Landscape mode");
    }

    public String getYCbCrSubsamplingDescription() {
        int[] positions = ((ExifSubIFDDirectory) this._directory).getIntArray(530);
        if (positions == null) {
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
        String value = ((ExifSubIFDDirectory) this._directory).getString(277);
        return value == null ? null : value + " samples/pixel";
    }

    public String getRowsPerStripDescription() {
        String value = ((ExifSubIFDDirectory) this._directory).getString(278);
        return value == null ? null : value + " rows/strip";
    }

    public String getStripByteCountsDescription() {
        String value = ((ExifSubIFDDirectory) this._directory).getString(279);
        return value == null ? null : value + " bytes";
    }

    public String getPhotometricInterpretationDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(262);
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

    public String getBitsPerSampleDescription() {
        String value = ((ExifSubIFDDirectory) this._directory).getString(258);
        return value == null ? null : value + " bits/component/pixel";
    }

    public String getFocalPlaneXResolutionDescription() {
        Rational rational = ((ExifSubIFDDirectory) this._directory).getRational(41486);
        if (rational == null) {
            return null;
        }
        String str;
        String unit = getFocalPlaneResolutionUnitDescription();
        StringBuilder append = new StringBuilder().append(rational.getReciprocal().toSimpleString(true));
        if (unit == null) {
            str = "";
        } else {
            str = " " + unit.toLowerCase();
        }
        return append.append(str).toString();
    }

    public String getFocalPlaneYResolutionDescription() {
        Rational rational = ((ExifSubIFDDirectory) this._directory).getRational(41487);
        if (rational == null) {
            return null;
        }
        String str;
        String unit = getFocalPlaneResolutionUnitDescription();
        StringBuilder append = new StringBuilder().append(rational.getReciprocal().toSimpleString(true));
        if (unit == null) {
            str = "";
        } else {
            str = " " + unit.toLowerCase();
        }
        return append.append(str).toString();
    }

    public String getFocalPlaneResolutionUnitDescription() {
        return getIndexedDescription(41488, 1, "(No unit)", "Inches", "cm");
    }

    public String getExifImageWidthDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(40962);
        return value == null ? null : value + " pixels";
    }

    public String getExifImageHeightDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(40963);
        return value == null ? null : value + " pixels";
    }

    public String getColorSpaceDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(40961);
        if (value == null) {
            return null;
        }
        if (value.intValue() == 1) {
            return "sRGB";
        }
        if (value.intValue() == 65535) {
            return "Undefined";
        }
        return "Unknown (" + value + ")";
    }

    public String getFocalLengthDescription() {
        Rational value = ((ExifSubIFDDirectory) this._directory).getRational(37386);
        if (value == null) {
            return null;
        }
        return new DecimalFormat("0.0##").format(value.doubleValue()) + " mm";
    }

    public String getFlashDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(37385);
        if (value == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if ((value.intValue() & 1) != 0) {
            sb.append("Flash fired");
        } else {
            sb.append("Flash did not fire");
        }
        if ((value.intValue() & 4) != 0) {
            if ((value.intValue() & 2) != 0) {
                sb.append(", return detected");
            } else {
                sb.append(", return not detected");
            }
        }
        if ((value.intValue() & 16) != 0) {
            sb.append(", auto");
        }
        if ((value.intValue() & 64) != 0) {
            sb.append(", red-eye reduction");
        }
        return sb.toString();
    }

    public String getWhiteBalanceDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(37384);
        if (value == null) {
            return null;
        }
        switch (value.intValue()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "Unknown";
            case TTSConst.TTSMULTILINE /*1*/:
                return "Daylight";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "Florescent";
            case TTSConst.TTSUNICODE /*3*/:
                return "Tungsten";
            case TTSConst.TTSEVT_RESUME /*10*/:
                return "Flash";
            case TTSConst.TTSEVT_JUMP /*17*/:
                return "Standard light";
            case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                return "Standard light (B)";
            case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                return "Standard light (C)";
            case TTSConst.TTSEVT_STYLECHANGE /*20*/:
                return "D55";
            case TTSConst.TTSEVT_PERSONACHANGE /*21*/:
                return "D65";
            case TTSConst.TTSEVT_SAYASCHANGE /*22*/:
                return "D75";
            case 255:
                return "(Other)";
            default:
                return "Unknown (" + value + ")";
        }
    }

    public String getMeteringModeDescription() {
        Integer value = ((ExifSubIFDDirectory) this._directory).getInteger(37383);
        if (value == null) {
            return null;
        }
        switch (value.intValue()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "Unknown";
            case TTSConst.TTSMULTILINE /*1*/:
                return "Average";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "Center weighted average";
            case TTSConst.TTSUNICODE /*3*/:
                return "Spot";
            case TTSConst.TTSXML /*4*/:
                return "Multi-spot";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "Multi-segment";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "Partial";
            case 255:
                return "(Other)";
            default:
                return "";
        }
    }

    public String getSubjectDistanceDescription() {
        Rational value = ((ExifSubIFDDirectory) this._directory).getRational(37382);
        if (value == null) {
            return null;
        }
        return new DecimalFormat("0.0##").format(value.doubleValue()) + " metres";
    }

    public String getCompressedAverageBitsPerPixelDescription() {
        Rational value = ((ExifSubIFDDirectory) this._directory).getRational(37122);
        if (value == null) {
            return null;
        }
        String ratio = value.toSimpleString(true);
        return (value.isInteger() && value.intValue() == 1) ? ratio + " bit/pixel" : ratio + " bits/pixel";
    }

    public String getExposureTimeDescription() {
        String value = ((ExifSubIFDDirectory) this._directory).getString(33434);
        return value == null ? null : value + " sec";
    }

    public String getShutterSpeedDescription() {
        Float apexValue = ((ExifSubIFDDirectory) this._directory).getFloatObject(37377);
        if (apexValue == null) {
            return null;
        }
        if (apexValue.floatValue() <= 1.0f) {
            return (((float) Math.round(((double) ((float) (1.0d / Math.exp(((double) apexValue.floatValue()) * Math.log(2.0d))))) * 10.0d)) / 10.0f) + " sec";
        }
        return "1/" + ((int) Math.exp(((double) apexValue.floatValue()) * Math.log(2.0d))) + " sec";
    }

    public String getFNumberDescription() {
        Rational value = ((ExifSubIFDDirectory) this._directory).getRational(33437);
        if (value == null) {
            return null;
        }
        return "F" + SimpleDecimalFormatter.format(value.doubleValue());
    }

    public String getSensingMethodDescription() {
        return getIndexedDescription(41495, 1, "(Not defined)", "One-chip color area sensor", "Two-chip color area sensor", "Three-chip color area sensor", "Color sequential area sensor", null, "Trilinear sensor", "Color sequential linear sensor");
    }

    public String getComponentConfigurationDescription() {
        int[] components = ((ExifSubIFDDirectory) this._directory).getIntArray(37121);
        if (components == null) {
            return null;
        }
        String[] componentStrings = new String[]{"", "Y", "Cb", "Cr", "R", "G", "B"};
        StringBuilder componentConfig = new StringBuilder();
        for (int i = 0; i < Math.min(4, components.length); i++) {
            int j = components[i];
            if (j > 0 && j < componentStrings.length) {
                componentConfig.append(componentStrings[j]);
            }
        }
        return componentConfig.toString();
    }
}
