package com.squareup.picasso;

import android.net.NetworkInfo;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import loquendo.tts.engine.TTSConst;

class PicassoExecutorService extends ThreadPoolExecutor {
    PicassoExecutorService() {
        super(3, 3, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new PicassoThreadFactory());
    }

    void adjustThreadCount(NetworkInfo info) {
        if (info == null || !info.isConnectedOrConnecting()) {
            setThreadCount(3);
            return;
        }
        switch (info.getType()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                switch (info.getSubtype()) {
                    case TTSConst.TTSMULTILINE /*1*/:
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        setThreadCount(1);
                    case TTSConst.TTSUNICODE /*3*/:
                    case TTSConst.TTSXML /*4*/:
                    case TTSConst.TTSEVT_TEXT /*5*/:
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                    case TTSConst.TTSEVT_NOTSENT /*12*/:
                        setThreadCount(2);
                    case TTSConst.TTSEVT_AUDIO /*13*/:
                    case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                        setThreadCount(3);
                    default:
                        setThreadCount(3);
                }
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSEVT_SENTENCE /*6*/:
            case TTSConst.TTSEVT_PAUSE /*9*/:
                setThreadCount(4);
            default:
                setThreadCount(3);
        }
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }
}
