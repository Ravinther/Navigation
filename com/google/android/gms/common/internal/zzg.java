package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.google.android.gms.C0568R;
import com.google.android.gms.internal.zzlp;
import com.sygic.aura.C1090R;
import loquendo.tts.engine.TTSConst;

public final class zzg {
    public static String zzb(Context context, int i, String str) {
        Resources resources = context.getResources();
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
                if (zzlp.zzb(resources)) {
                    return resources.getString(C0568R.string.common_google_play_services_install_text_tablet, new Object[]{str});
                }
                return resources.getString(C0568R.string.common_google_play_services_install_text_phone, new Object[]{str});
            case TTSConst.TTSPARAGRAPH /*2*/:
                return resources.getString(C0568R.string.common_google_play_services_update_text, new Object[]{str});
            case TTSConst.TTSUNICODE /*3*/:
                return resources.getString(C0568R.string.common_google_play_services_enable_text, new Object[]{str});
            case TTSConst.TTSEVT_TEXT /*5*/:
                return resources.getString(C0568R.string.common_google_play_services_invalid_account_text);
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return resources.getString(C0568R.string.common_google_play_services_network_error_text);
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return resources.getString(C0568R.string.common_google_play_services_unsupported_text, new Object[]{str});
            case TTSConst.TTSEVT_ERROR /*16*/:
                return resources.getString(C0568R.string.common_google_play_services_api_unavailable_text, new Object[]{str});
            case TTSConst.TTSEVT_JUMP /*17*/:
                return resources.getString(C0568R.string.common_google_play_services_sign_in_failed_text);
            case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                return resources.getString(C0568R.string.common_google_play_services_updating_text, new Object[]{str});
            case C1090R.styleable.Theme_dialogTheme /*42*/:
                return resources.getString(C0568R.string.common_android_wear_update_text, new Object[]{str});
            default:
                return resources.getString(C0568R.string.common_google_play_services_unknown_issue);
        }
    }

    public static final String zzg(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
                return resources.getString(C0568R.string.common_google_play_services_install_title);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return resources.getString(C0568R.string.common_google_play_services_update_title);
            case TTSConst.TTSUNICODE /*3*/:
                return resources.getString(C0568R.string.common_google_play_services_enable_title);
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return null;
            case TTSConst.TTSEVT_TEXT /*5*/:
                Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                return resources.getString(C0568R.string.common_google_play_services_invalid_account_title);
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                return resources.getString(C0568R.string.common_google_play_services_network_error_title);
            case TTSConst.TTSEVT_TAG /*8*/:
                Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                return null;
            case TTSConst.TTSEVT_PAUSE /*9*/:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return resources.getString(C0568R.string.common_google_play_services_unsupported_title);
            case TTSConst.TTSEVT_RESUME /*10*/:
                Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                return null;
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case TTSConst.TTSEVT_ERROR /*16*/:
                Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                return null;
            case TTSConst.TTSEVT_JUMP /*17*/:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return resources.getString(C0568R.string.common_google_play_services_sign_in_failed_title);
            case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                return resources.getString(C0568R.string.common_google_play_services_updating_title);
            case C1090R.styleable.Theme_dialogTheme /*42*/:
                return resources.getString(C0568R.string.common_android_wear_update_title);
            default:
                Log.e("GoogleApiAvailability", "Unexpected error code " + i);
                return null;
        }
    }

    public static String zzh(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
                return resources.getString(C0568R.string.common_google_play_services_install_button);
            case TTSConst.TTSPARAGRAPH /*2*/:
            case C1090R.styleable.Theme_dialogTheme /*42*/:
                return resources.getString(C0568R.string.common_google_play_services_update_button);
            case TTSConst.TTSUNICODE /*3*/:
                return resources.getString(C0568R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(17039370);
        }
    }
}
