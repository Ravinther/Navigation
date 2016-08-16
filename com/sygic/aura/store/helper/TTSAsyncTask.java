package com.sygic.aura.store.helper;

import android.os.AsyncTask;
import com.sygic.aura.feature.tts.TTSDataRequestListener;
import com.sygic.aura.store.data.StoreEntry;
import java.util.ArrayList;

public abstract class TTSAsyncTask extends AsyncTask<StoreEntry, StoreEntry, ArrayList<StoreEntry>> implements TTSDataRequestListener {
}
