package com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff;

import loquendo.tts.engine.TTSConst;

public class TiffDataFormat {
    public static final TiffDataFormat DOUBLE;
    public static final TiffDataFormat INT16_S;
    public static final TiffDataFormat INT16_U;
    public static final TiffDataFormat INT32_S;
    public static final TiffDataFormat INT32_U;
    public static final TiffDataFormat INT8_S;
    public static final TiffDataFormat INT8_U;
    public static final TiffDataFormat RATIONAL_S;
    public static final TiffDataFormat RATIONAL_U;
    public static final TiffDataFormat SINGLE;
    public static final TiffDataFormat STRING;
    public static final TiffDataFormat UNDEFINED;
    private final int _componentSizeBytes;
    private final String _name;
    private final int _tiffFormatCode;

    static {
        INT8_U = new TiffDataFormat("BYTE", 1, 1);
        STRING = new TiffDataFormat("STRING", 2, 1);
        INT16_U = new TiffDataFormat("USHORT", 3, 2);
        INT32_U = new TiffDataFormat("ULONG", 4, 4);
        RATIONAL_U = new TiffDataFormat("URATIONAL", 5, 8);
        INT8_S = new TiffDataFormat("SBYTE", 6, 1);
        UNDEFINED = new TiffDataFormat("UNDEFINED", 7, 1);
        INT16_S = new TiffDataFormat("SSHORT", 8, 2);
        INT32_S = new TiffDataFormat("SLONG", 9, 4);
        RATIONAL_S = new TiffDataFormat("SRATIONAL", 10, 8);
        SINGLE = new TiffDataFormat("SINGLE", 11, 4);
        DOUBLE = new TiffDataFormat("DOUBLE", 12, 8);
    }

    public static TiffDataFormat fromTiffFormatCode(int tiffFormatCode) {
        switch (tiffFormatCode) {
            case TTSConst.TTSMULTILINE /*1*/:
                return INT8_U;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return STRING;
            case TTSConst.TTSUNICODE /*3*/:
                return INT16_U;
            case TTSConst.TTSXML /*4*/:
                return INT32_U;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return RATIONAL_U;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return INT8_S;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return UNDEFINED;
            case TTSConst.TTSEVT_TAG /*8*/:
                return INT16_S;
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return INT32_S;
            case TTSConst.TTSEVT_RESUME /*10*/:
                return RATIONAL_S;
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return SINGLE;
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                return DOUBLE;
            default:
                return null;
        }
    }

    private TiffDataFormat(String name, int tiffFormatCode, int componentSizeBytes) {
        this._name = name;
        this._tiffFormatCode = tiffFormatCode;
        this._componentSizeBytes = componentSizeBytes;
    }

    public int getComponentSizeBytes() {
        return this._componentSizeBytes;
    }

    public String toString() {
        return this._name;
    }
}
