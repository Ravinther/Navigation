package com.bosch.myspin.serversdk.voicecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.voicecontrol.C0254f.C0260a;
import com.sygic.aura.search.model.data.PoiListItem;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.voicecontrol.d */
class C0259d extends BroadcastReceiver {
    final /* synthetic */ C0256b f506a;

    C0259d(C0256b c0256b) {
        this.f506a = c0256b;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.media.ACTION_SCO_AUDIO_STATE_UPDATED") && intent.hasExtra("android.media.extra.SCO_AUDIO_STATE")) {
            switch (intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", 0)) {
                case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                    Logger.logDebug(C0256b.f485a, "MySpinVoiceControlManager/ACTION_SCO_AUDIO_STATE_UPDATED [SCO_AUDIO_STATE_ERROR]");
                    this.f506a.m463b(3);
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    Logger.logDebug(C0256b.f485a, "MySpinVoiceControlManager/ACTION_SCO_AUDIO_STATE_UPDATED [SCO_AUDIO_STATE_DISCONNECTED]");
                    if (this.f506a.f493i == C0260a.STATE_SCO_CONNECTED) {
                        this.f506a.m454a(C0260a.STATE_IDLE);
                    }
                case TTSConst.TTSMULTILINE /*1*/:
                    Logger.logDebug(C0256b.f485a, "MySpinVoiceControlManager/ACTION_SCO_AUDIO_STATE_UPDATED [SCO_AUDIO_STATE_CONNECTED]");
                    this.f506a.m454a(C0260a.STATE_SCO_CONNECTED);
                default:
                    Logger.logWarning(C0256b.f485a, "MySpinVoiceControlManager/ACTION_SCO_AUDIO_STATE_UPDATED [UNKNOWN]");
            }
        }
    }
}
