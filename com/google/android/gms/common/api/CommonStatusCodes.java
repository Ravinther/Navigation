package com.google.android.gms.common.api;

import com.sygic.aura.search.model.data.PoiListItem;
import loquendo.tts.engine.TTSConst;

public class CommonStatusCodes {
    public static String getStatusCodeString(int statusCode) {
        switch (statusCode) {
            case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                return "SUCCESS_CACHE";
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "SUCCESS";
            case TTSConst.TTSMULTILINE /*1*/:
                return "SERVICE_MISSING";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case TTSConst.TTSUNICODE /*3*/:
                return "SERVICE_DISABLED";
            case TTSConst.TTSXML /*4*/:
                return "SIGN_IN_REQUIRED";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "INVALID_ACCOUNT";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "RESOLUTION_REQUIRED";
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return "NETWORK_ERROR";
            case TTSConst.TTSEVT_TAG /*8*/:
                return "INTERNAL_ERROR";
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return "SERVICE_INVALID";
            case TTSConst.TTSEVT_RESUME /*10*/:
                return "DEVELOPER_ERROR";
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return "LICENSE_CHECK_FAILED";
            case TTSConst.TTSEVT_AUDIO /*13*/:
                return "ERROR_OPERATION_FAILED";
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                return "INTERRUPTED";
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                return "TIMEOUT";
            case TTSConst.TTSEVT_ERROR /*16*/:
                return "CANCELED";
            case 3000:
                return "AUTH_API_INVALID_CREDENTIALS";
            case 3001:
                return "AUTH_API_ACCESS_FORBIDDEN";
            case 3002:
                return "AUTH_API_CLIENT_ERROR";
            case 3003:
                return "AUTH_API_SERVER_ERROR";
            case 3004:
                return "AUTH_TOKEN_ERROR";
            case 3005:
                return "AUTH_URL_RESOLUTION";
            default:
                return "unknown status code: " + statusCode;
        }
    }
}
