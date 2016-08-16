package com.sygic.aura.helper.imageMetadataExtractor.metadata.iptc;

import com.sygic.aura.C1090R;
import com.sygic.aura.helper.imageMetadataExtractor.lang.StringUtil;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.TagDescriptor;
import loquendo.tts.engine.TTSConst;

public class IptcDescriptor extends TagDescriptor<IptcDirectory> {
    public IptcDescriptor(IptcDirectory directory) {
        super(directory);
    }

    public String getDescription(int tagType) {
        switch (tagType) {
            case 276:
                return getFileFormatDescription();
            case 537:
                return getKeywordsDescription();
            default:
                return super.getDescription(tagType);
        }
    }

    public String getFileFormatDescription() {
        Integer value = ((IptcDirectory) this._directory).getInteger(276);
        if (value == null) {
            return null;
        }
        switch (value.intValue()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "No ObjectData";
            case TTSConst.TTSMULTILINE /*1*/:
                return "IPTC-NAA Digital Newsphoto Parameter Record";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "IPTC7901 Recommended Message Format";
            case TTSConst.TTSUNICODE /*3*/:
                return "Tagged Image File Format (Adobe/Aldus Image data)";
            case TTSConst.TTSXML /*4*/:
                return "Illustrator (Adobe Graphics data)";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "AppleSingle (Apple Computer Inc)";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "NAA 89-3 (ANPA 1312)";
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return "MacBinary II";
            case TTSConst.TTSEVT_TAG /*8*/:
                return "IPTC Unstructured Character Oriented File Format (UCOFF)";
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return "United Press International ANPA 1312 variant";
            case TTSConst.TTSEVT_RESUME /*10*/:
                return "United Press International Down-Load Message";
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return "JPEG File Interchange (JFIF)";
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                return "Photo-CD Image-Pac (Eastman Kodak)";
            case TTSConst.TTSEVT_AUDIO /*13*/:
                return "Bit Mapped Graphics File [.BMP] (Microsoft)";
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                return "Digital Audio File [.WAV] (Microsoft & Creative Labs)";
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                return "Audio plus Moving Video [.AVI] (Microsoft)";
            case TTSConst.TTSEVT_ERROR /*16*/:
                return "PC DOS/Windows Executable Files [.COM][.EXE]";
            case TTSConst.TTSEVT_JUMP /*17*/:
                return "Compressed Binary File [.ZIP] (PKWare Inc)";
            case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                return "Audio Interchange File Format AIFF (Apple Computer Inc)";
            case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                return "RIFF Wave (Microsoft Corporation)";
            case TTSConst.TTSEVT_STYLECHANGE /*20*/:
                return "Freehand (Macromedia/Aldus)";
            case TTSConst.TTSEVT_PERSONACHANGE /*21*/:
                return "Hypertext Markup Language [.HTML] (The Internet Society)";
            case TTSConst.TTSEVT_SAYASCHANGE /*22*/:
                return "MPEG 2 Audio Layer 2 (Musicom), ISO/IEC";
            case C1090R.styleable.Theme_actionBarDivider /*23*/:
                return "MPEG 2 Audio Layer 3, ISO/IEC";
            case C1090R.styleable.Theme_actionBarItemBackground /*24*/:
                return "Portable Document File [.PDF] Adobe";
            case C1090R.styleable.Theme_actionMenuTextAppearance /*25*/:
                return "News Industry Text Format (NITF)";
            case C1090R.styleable.Theme_actionMenuTextColor /*26*/:
                return "Tape Archive [.TAR]";
            case C1090R.styleable.Theme_actionModeStyle /*27*/:
                return "Tidningarnas Telegrambyra NITF version (TTNITF DTD)";
            case C1090R.styleable.Theme_actionModeCloseButtonStyle /*28*/:
                return "Ritzaus Bureau NITF version (RBNITF DTD)";
            case C1090R.styleable.Theme_actionModeBackground /*29*/:
                return "Corel Draw [.CDR]";
            default:
                return String.format("Unknown (%d)", new Object[]{value});
        }
    }

    public String getKeywordsDescription() {
        String[] keywords = ((IptcDirectory) this._directory).getStringArray(537);
        if (keywords == null) {
            return null;
        }
        return StringUtil.join(keywords, ";");
    }
}
