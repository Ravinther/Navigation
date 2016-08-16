package com.sygic.aura.feature.tts;

import android.media.AudioTrack;
import android.util.Log;
import com.sygic.aura.ProjectsConsts;
import loquendo.tts.engine.TTSAudioDestination;
import loquendo.tts.engine.TTSConst;
import loquendo.tts.engine.TTSEvent;
import loquendo.tts.engine.TTSException;
import loquendo.tts.engine.TTSReader;

public class AndroidAudioDestination extends TTSAudioDestination {
    private static int BUFSIZE;
    private EmptyBuffer dequeuer;
    private boolean ending;
    private boolean idle;
    private boolean paused;
    public int sampleRate;
    private boolean speaking;
    private boolean stopped;
    private AudioTrack track;

    class EmptyBuffer extends Thread {
        private Object dest;
        private boolean stillAlive;

        public EmptyBuffer(String threadName, Object dest) {
            super(threadName);
            this.stillAlive = true;
            this.dest = null;
            this.dest = dest;
        }

        public void killThis() {
            this.stillAlive = false;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r9 = this;
            r8 = 1;
            r7 = 0;
            r3 = 0;
            r1 = 0;
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.BUFSIZE;
            r0 = new byte[r6];
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.BUFSIZE;
            r4 = new byte[r6];
            java.util.Arrays.fill(r0, r7);
        L_0x0013:
            monitor-enter(r9);
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x0038 }
            r7 = 1;
            r6.idle = r7;	 Catch:{ Exception -> 0x0038 }
            r7 = r9.dest;	 Catch:{ Exception -> 0x0038 }
            monitor-enter(r7);	 Catch:{ Exception -> 0x0038 }
            r6 = r9.dest;	 Catch:{ all -> 0x0035 }
            r6.notify();	 Catch:{ all -> 0x0035 }
            monitor-exit(r7);	 Catch:{ all -> 0x0035 }
            r1 = 0;
            r9.wait();	 Catch:{ Exception -> 0x0038 }
        L_0x0027:
            monitor-exit(r9);	 Catch:{ all -> 0x003d }
            r6 = r9.stillAlive;
            if (r6 == 0) goto L_0x0034;
        L_0x002c:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;
            r6 = r6.ending;
            if (r6 == 0) goto L_0x0040;
        L_0x0034:
            return;
        L_0x0035:
            r6 = move-exception;
            monitor-exit(r7);	 Catch:{ all -> 0x0035 }
            throw r6;	 Catch:{ Exception -> 0x0038 }
        L_0x0038:
            r2 = move-exception;
            r2.printStackTrace();	 Catch:{ all -> 0x003d }
            goto L_0x0027;
        L_0x003d:
            r6 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x003d }
            throw r6;
        L_0x0040:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;
            r6 = r6.track;
            if (r6 != 0) goto L_0x0048;
        L_0x0048:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;
            r6 = r6.track;
            r6 = r6.getState();
            if (r6 != r8) goto L_0x00d2;
        L_0x0054:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00cd }
            r3 = r6.getBufferData(r0);	 Catch:{ Exception -> 0x00cd }
            r1 = r1 + r3;
            if (r3 <= 0) goto L_0x0067;
        L_0x005d:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00cd }
            r6 = r6.track;	 Catch:{ Exception -> 0x00cd }
            r7 = 0;
            r6.write(r0, r7, r3);	 Catch:{ Exception -> 0x00cd }
        L_0x0067:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00cd }
            r6 = r6.track;	 Catch:{ Exception -> 0x00cd }
            r6.play();	 Catch:{ Exception -> 0x00cd }
        L_0x0070:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.speaking;	 Catch:{ Exception -> 0x00b3 }
            if (r6 == 0) goto L_0x010e;
        L_0x0078:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.ending;	 Catch:{ Exception -> 0x00b3 }
            if (r6 != 0) goto L_0x0034;
        L_0x0080:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.paused;	 Catch:{ Exception -> 0x00b3 }
            if (r6 == 0) goto L_0x0096;
        L_0x0088:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.track;	 Catch:{ Exception -> 0x00b3 }
            r6.pause();	 Catch:{ Exception -> 0x00b3 }
            monitor-enter(r9);	 Catch:{ Exception -> 0x00b3 }
            r9.wait();	 Catch:{ Exception -> 0x00dd }
        L_0x0095:
            monitor-exit(r9);	 Catch:{ all -> 0x00e2 }
        L_0x0096:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r3 = r6.getBufferData(r0);	 Catch:{ Exception -> 0x00b3 }
            if (r3 != 0) goto L_0x00a5;
        L_0x009e:
            monitor-enter(r9);	 Catch:{ Exception -> 0x00b3 }
            r6 = 50;
            r9.wait(r6);	 Catch:{ InterruptedException -> 0x00e5 }
        L_0x00a4:
            monitor-exit(r9);	 Catch:{ all -> 0x00ea }
        L_0x00a5:
            r1 = r1 + r3;
            if (r3 <= 0) goto L_0x0070;
        L_0x00a8:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.track;	 Catch:{ Exception -> 0x00b3 }
            r7 = 0;
            r6.write(r0, r7, r3);	 Catch:{ Exception -> 0x00b3 }
            goto L_0x0070;
        L_0x00b3:
            r2 = move-exception;
            r2.printStackTrace();
        L_0x00b7:
            monitor-enter(r9);
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x015a }
            r7 = 1;
            r6.idle = r7;	 Catch:{ Exception -> 0x015a }
            r9.notifyAll();	 Catch:{ Exception -> 0x015a }
        L_0x00c1:
            monitor-exit(r9);	 Catch:{ all -> 0x0160 }
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;
            r6 = r6.track;
            r6.stop();
            goto L_0x0013;
        L_0x00cd:
            r2 = move-exception;
            r2.printStackTrace();
            goto L_0x0070;
        L_0x00d2:
            r6 = "LTTSAudioDest";
            r7 = "EmptyBuffer - AudioTrack init failed!!";
            android.util.Log.w(r6, r7);
            goto L_0x0013;
        L_0x00dd:
            r2 = move-exception;
            r2.printStackTrace();	 Catch:{ all -> 0x00e2 }
            goto L_0x0095;
        L_0x00e2:
            r6 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x00e2 }
            throw r6;	 Catch:{ Exception -> 0x00b3 }
        L_0x00e5:
            r2 = move-exception;
            r2.printStackTrace();	 Catch:{ all -> 0x00ea }
            goto L_0x00a4;
        L_0x00ea:
            r6 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x00ea }
            throw r6;	 Catch:{ Exception -> 0x00b3 }
        L_0x00ed:
            r1 = r1 + r3;
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.track;	 Catch:{ Exception -> 0x00b3 }
            r7 = 0;
            r6.write(r0, r7, r3);	 Catch:{ Exception -> 0x00b3 }
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.paused;	 Catch:{ Exception -> 0x00b3 }
            if (r6 == 0) goto L_0x010e;
        L_0x0100:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.track;	 Catch:{ Exception -> 0x00b3 }
            r6.pause();	 Catch:{ Exception -> 0x00b3 }
            monitor-enter(r9);	 Catch:{ Exception -> 0x00b3 }
            r9.wait();	 Catch:{ Exception -> 0x0120 }
        L_0x010d:
            monitor-exit(r9);	 Catch:{ all -> 0x0125 }
        L_0x010e:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r3 = r6.getBufferData(r0);	 Catch:{ Exception -> 0x00b3 }
            if (r3 <= 0) goto L_0x0128;
        L_0x0116:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.ending;	 Catch:{ Exception -> 0x00b3 }
            if (r6 == 0) goto L_0x00ed;
        L_0x011e:
            goto L_0x0034;
        L_0x0120:
            r2 = move-exception;
            r2.printStackTrace();	 Catch:{ all -> 0x0125 }
            goto L_0x010d;
        L_0x0125:
            r6 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x0125 }
            throw r6;	 Catch:{ Exception -> 0x00b3 }
        L_0x0128:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.BUFSIZE;	 Catch:{ Exception -> 0x00b3 }
            if (r1 >= r6) goto L_0x013e;
        L_0x012e:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.BUFSIZE;	 Catch:{ Exception -> 0x00b3 }
            r5 = r6 - r1;
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.track;	 Catch:{ Exception -> 0x00b3 }
            r7 = 0;
            r6.write(r4, r7, r5);	 Catch:{ Exception -> 0x00b3 }
        L_0x013e:
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6 = r6.track;	 Catch:{ Exception -> 0x00b3 }
            r6.flush();	 Catch:{ Exception -> 0x00b3 }
            r6 = com.sygic.aura.feature.tts.AndroidAudioDestination.this;	 Catch:{ Exception -> 0x00b3 }
            r6.cleanBuffer();	 Catch:{ Exception -> 0x00b3 }
            r7 = r9.dest;	 Catch:{ Exception -> 0x00b3 }
            monitor-enter(r7);	 Catch:{ Exception -> 0x00b3 }
            r6 = r9.dest;	 Catch:{ all -> 0x0157 }
            r6.notify();	 Catch:{ all -> 0x0157 }
            monitor-exit(r7);	 Catch:{ all -> 0x0157 }
            goto L_0x00b7;
        L_0x0157:
            r6 = move-exception;
            monitor-exit(r7);	 Catch:{ all -> 0x0157 }
            throw r6;	 Catch:{ Exception -> 0x00b3 }
        L_0x015a:
            r2 = move-exception;
            r2.printStackTrace();	 Catch:{ all -> 0x0160 }
            goto L_0x00c1;
        L_0x0160:
            r6 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x0160 }
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.tts.AndroidAudioDestination.EmptyBuffer.run():void");
        }
    }

    public void finalize() {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:22:0x002a in {9, 13, 17, 18, 19, 21, 23, 25} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.rerun(BlockProcessor.java:44)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:57)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r3 = this;
        r1 = r3.dequeuer;	 Catch:{ all -> 0x002d }
        if (r1 == 0) goto L_0x0018;	 Catch:{ all -> 0x002d }
    L_0x0004:
        r1 = r3.dequeuer;	 Catch:{ all -> 0x002d }
        r1 = r1.isAlive();	 Catch:{ all -> 0x002d }
        if (r1 == 0) goto L_0x0018;	 Catch:{ all -> 0x002d }
    L_0x000c:
        r1 = 1;	 Catch:{ all -> 0x002d }
        r3.ending = r1;	 Catch:{ all -> 0x002d }
        r2 = r3.dequeuer;	 Catch:{ all -> 0x002d }
        monitor-enter(r2);	 Catch:{ all -> 0x002d }
        r1 = r3.dequeuer;	 Catch:{ all -> 0x002d }
        r1.notify();	 Catch:{ all -> 0x002d }
        monitor-exit(r2);	 Catch:{ all -> 0x002d }
    L_0x0018:
        r1 = r3.track;	 Catch:{ all -> 0x002d }
        if (r1 == 0) goto L_0x0021;	 Catch:{ all -> 0x002d }
    L_0x001c:
        r1 = r3.track;	 Catch:{ all -> 0x002d }
        r1.stop();	 Catch:{ all -> 0x002d }
    L_0x0021:
        r1 = r3.track;	 Catch:{ all -> 0x002d }
        r1.release();	 Catch:{ all -> 0x002d }
        super.finalize();
    L_0x0029:
        return;
    L_0x002a:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x002d }
        throw r1;	 Catch:{ all -> 0x002d }
    L_0x002d:
        r1 = move-exception;
        super.finalize();	 Catch:{ Throwable -> 0x0037 }
    L_0x0031:
        throw r1;
    L_0x0032:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0029;
    L_0x0037:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.tts.AndroidAudioDestination.finalize():void");
    }

    static {
        BUFSIZE = 16000;
    }

    public AndroidAudioDestination(TTSReader hReader, String name) {
        super(hReader, name);
        this.speaking = false;
        this.idle = true;
        this.paused = false;
        this.stopped = false;
        this.ending = false;
        this.dequeuer = null;
        this.track = null;
        this.sampleRate = 16000;
        super.killThread();
        this.dequeuer = new EmptyBuffer("AD " + name, this);
        this.dequeuer.start();
    }

    public void killThread() {
        while (!this.idle) {
            synchronized (this.dequeuer) {
                try {
                    this.dequeuer.wait(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.dequeuer.killThis();
        synchronized (this.dequeuer) {
            this.dequeuer.notify();
        }
        this.dequeuer = null;
    }

    public void setAudio(int sampleRate, String coding, int nChannels) throws TTSException {
        this.sampleRate = sampleRate;
        int minBufSize = AudioTrack.getMinBufferSize(sampleRate, 2, 2);
        if (minBufSize > BUFSIZE) {
            BUFSIZE = minBufSize;
        }
        super.setAudio(sampleRate, coding, nChannels);
        this.track = new AudioTrack(ProjectsConsts.getInt(20), sampleRate, 2, 2, BUFSIZE, 1);
    }

    public boolean isSpeaking() {
        return !this.idle;
    }

    public void stop() {
        if (this.track != null) {
            try {
                if (this.track.getState() != 0) {
                    this.track.stop();
                    this.speaking = false;
                    this.stopped = true;
                }
                if (this.paused) {
                    this.paused = false;
                    synchronized (this.dequeuer) {
                        this.dequeuer.notify();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unlock() {
        this.stopped = false;
    }

    public void eventOccurred(TTSEvent evt) {
        switch (evt.getReason().intValue()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                if (this.paused) {
                    stop();
                    synchronized (this) {
                        try {
                            wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        break;
                    }
                }
                if (this.speaking) {
                    Log.w("LTTSAudioDest", "Event AUDIOSTART while speaking: conflict of LTTS events!");
                    return;
                }
                this.speaking = true;
                this.idle = false;
                synchronized (this.dequeuer) {
                    this.dequeuer.notify();
                    break;
                }
            case TTSConst.TTSMULTILINE /*1*/:
                this.speaking = false;
                this.stopped = false;
                synchronized (this) {
                    try {
                        wait(500);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        break;
                    }
                    break;
                }
            default:
        }
    }
}
