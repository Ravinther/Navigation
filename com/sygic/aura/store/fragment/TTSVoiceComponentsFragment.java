package com.sygic.aura.store.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import com.sygic.aura.feature.tts.TTSDataRequestListener;
import com.sygic.aura.feature.tts.TtsAndroid;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.store.data.ComponentEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.SystemTTSEntry;
import com.sygic.aura.store.helper.TTSAsyncTask;
import com.sygic.aura.store.model.StoreAdapter;
import com.sygic.aura.store.model.TTSComponentAdapter;
import com.sygic.aura.views.animation.VerticalExpandingAnimator;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TTSVoiceComponentsFragment extends ComponentsFragment {
    private SparseArray<String> mMandatoryItems;
    private VoiceTTSReceiver mReceiver;
    private Queue<String> mSystemTTSQueue;

    /* renamed from: com.sygic.aura.store.fragment.TTSVoiceComponentsFragment.1 */
    class C17451 extends TTSAsyncTask {
        private boolean isDataReady;
        private StoreAdapter mAdapter;
        private int mPosition;
        final /* synthetic */ boolean val$isUpdateRequired;
        private Set<String> voiceSet;

        C17451(boolean z) {
            this.val$isUpdateRequired = z;
            this.isDataReady = false;
        }

        protected void onPreExecute() {
            this.mAdapter = TTSVoiceComponentsFragment.this.mAdapter;
            this.mAdapter.clear();
            this.mPosition = 0;
        }

        protected ArrayList<StoreEntry> doInBackground(StoreEntry... storeEntries) {
            ArrayList<StoreEntry> entries = new ArrayList();
            this.voiceSet = new HashSet();
            TtsAndroid androidTts = SygicHelper.getAndroidTts();
            if ("com.google.android.tts".equals(androidTts.getEngine())) {
                fireTTSCheckRequest(androidTts, this);
            } else {
                this.isDataReady = true;
            }
            for (StoreEntry entry : SystemTTSEntry.createFromVoiceEntries(SettingsManager.nativeGetInstalledVoices())) {
                if (!TextUtils.isEmpty(entry.getISO())) {
                    this.voiceSet.add(entry.getISO());
                }
            }
            synchronized (this) {
                if (!this.isDataReady) {
                    try {
                        wait(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (StoreEntry entry2 : SystemTTSEntry.createFromVoiceEntries(SettingsManager.nativeGetAvailableVoices())) {
                if (this.voiceSet.add(entry2.getISO())) {
                    addEntry(entries, entry2);
                }
            }
            for (StoreEntry entry22 : storeEntries) {
                if ((!this.val$isUpdateRequired || ((entry22 instanceof ComponentEntry) && ((ComponentEntry) entry22).isUpdateAvailable())) && (this.voiceSet == null || !this.voiceSet.contains(entry22.getISO()) || ((entry22 instanceof ComponentEntry) && ((ComponentEntry) entry22).isInstalled()))) {
                    addEntry(entries, entry22);
                    if (entry22 instanceof ComponentEntry) {
                        if (((ComponentEntry) entry22).isUpdateAvailable()) {
                            TTSVoiceComponentsFragment.this.mPendingItems.add(entry22.getId());
                        }
                        if (((ComponentEntry) entry22).isMandatory()) {
                            TTSVoiceComponentsFragment.this.mMandatoryItems.append(entry22.getIndex(), entry22.getId());
                        }
                    }
                }
            }
            return entries;
        }

        private void addEntry(ArrayList<StoreEntry> entries, StoreEntry entry) {
            int i = this.mPosition;
            this.mPosition = i + 1;
            entry.setPosition(i);
            entries.add(entry);
        }

        private void fireTTSCheckRequest(TtsAndroid tts, TTSDataRequestListener listener) {
            tts.checkTTSData(listener);
        }

        protected void onPostExecute(ArrayList<StoreEntry> entries) {
            TTSVoiceComponentsFragment.this.mSmartProgressBar.stopAndCrossfadeWith(TTSVoiceComponentsFragment.this.mList);
            if (this.val$isUpdateRequired) {
                VerticalExpandingAnimator.animateView(TTSVoiceComponentsFragment.this.mPending, true);
            }
            this.mAdapter.setNotifyOnChange(false);
            Iterator it = entries.iterator();
            while (it.hasNext()) {
                this.mAdapter.add((StoreEntry) it.next());
            }
            this.mAdapter.notifyDataSetChanged();
        }

        public void onDataReady() {
            synchronized (this) {
                this.isDataReady = true;
                notify();
            }
        }
    }

    private class VoiceTTSReceiver extends BroadcastReceiver {
        private VoiceTTSReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals("android.speech.tts.engine.TTS_DATA_INSTALLED")) {
                TTSVoiceComponentsFragment.this.onInstallFinished((String) TTSVoiceComponentsFragment.this.mSystemTTSQueue.poll());
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (VERSION.SDK_INT >= 9) {
            this.mSystemTTSQueue = new ArrayDeque();
        } else {
            this.mSystemTTSQueue = new LinkedList();
        }
        this.mMandatoryItems = new SparseArray();
        IntentFilter filter = new IntentFilter("android.speech.tts.engine.TTS_DATA_INSTALLED");
        FragmentActivity activity = getActivity();
        BroadcastReceiver voiceTTSReceiver = new VoiceTTSReceiver();
        this.mReceiver = voiceTTSReceiver;
        activity.registerReceiver(voiceTTSReceiver, filter);
    }

    public void onDestroy() {
        getActivity().unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    protected AsyncTask getProcessListTask(boolean isUpdateRequired) {
        return new C17451(isUpdateRequired);
    }

    protected void onInstallSuccess() {
        if (this.mCurrentDownload instanceof SystemTTSEntry) {
            if ("com.google.android.tts".equals(SygicHelper.getAndroidTts().getEngine())) {
                this.mSystemTTSQueue.add(this.mCurrentDownload.getId());
            } else {
                this.mChanged = true;
            }
        } else if (this.mMandatoryItems.size() > 0) {
            Object[][] installItems = (Object[][]) Array.newInstance(Object.class, new int[]{this.mMandatoryItems.size(), 2});
            for (int i = 0; i < this.mMandatoryItems.size(); i++) {
                int key = this.mMandatoryItems.keyAt(i);
                installItems[i] = new Object[]{this.mMandatoryItems.get(key), Integer.valueOf(key)};
            }
            ComponentManager.nativeInstall(installItems, new Object[0][]);
        }
    }

    protected StoreAdapter getAdapter(Context context) {
        return new TTSComponentAdapter(context);
    }
}
