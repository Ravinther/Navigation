package com.google.android.gms.internal;

import android.text.TextUtils;
import com.sygic.aura.C1090R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import loquendo.tts.engine.TTSConst;

public final class zzls {
    private static final Pattern zzage;
    private static final Pattern zzagf;

    static {
        zzage = Pattern.compile("\\\\.");
        zzagf = Pattern.compile("[\\\\\"/\b\f\n\r\t]");
    }

    public static String zzcA(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = zzagf.matcher(str);
        StringBuffer stringBuffer = null;
        while (matcher.find()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            switch (matcher.group().charAt(0)) {
                case TTSConst.TTSEVT_TAG /*8*/:
                    matcher.appendReplacement(stringBuffer, "\\\\b");
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    matcher.appendReplacement(stringBuffer, "\\\\t");
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    matcher.appendReplacement(stringBuffer, "\\\\n");
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    matcher.appendReplacement(stringBuffer, "\\\\f");
                    break;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    matcher.appendReplacement(stringBuffer, "\\\\r");
                    break;
                case C1090R.styleable.Theme_actionModePasteDrawable /*34*/:
                    matcher.appendReplacement(stringBuffer, "\\\\\\\"");
                    break;
                case C1090R.styleable.Theme_spinnerDropDownItemStyle /*47*/:
                    matcher.appendReplacement(stringBuffer, "\\\\/");
                    break;
                case C1090R.styleable.Theme_alertDialogTheme /*92*/:
                    matcher.appendReplacement(stringBuffer, "\\\\\\\\");
                    break;
                default:
                    break;
            }
        }
        if (stringBuffer == null) {
            return str;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
