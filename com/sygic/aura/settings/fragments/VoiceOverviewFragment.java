package com.sygic.aura.settings.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.ComponentsFragmentResultCallback;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.VoiceEntry;
import com.sygic.aura.settings.model.VoicesAdapter;
import com.sygic.aura.store.fragment.ComponentsFragment;
import com.sygic.aura.store.fragment.MarketPlaceFragment;
import com.sygic.aura.store.fragment.TTSVoiceComponentsFragment;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.base.C1799R;
import loquendo.tts.engine.TTSConst;

public class VoiceOverviewFragment extends AbstractScreenFragment implements OnItemClickListener, ComponentsFragmentResultCallback {
    private static final String TAG = "VoiceOverviewFragment";
    private VoicesAdapter mAdapter;
    private ListView mList;
    private boolean mOnTop;
    private SharedPreferences mSharedPreferences;
    private SmartProgressBar mSmartProgressBar;
    private String mTitle;

    /* renamed from: com.sygic.aura.settings.fragments.VoiceOverviewFragment.1 */
    class C16541 implements OnMenuItemClickListener {
        C16541() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return VoiceOverviewFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.VoiceOverviewFragment.2 */
    class C16552 extends AsyncTask<Void, VoiceEntry, Integer> {
        C16552() {
        }

        protected Integer doInBackground(Void... voids) {
            VoiceEntry[] voices = VoiceOverviewFragment.this.mAdapter.loadInstalledVoices();
            int pos = -1;
            if (voices != null) {
                VoiceEntry currentVoice = SettingsManager.nativeGetSelectedVoice();
                int i = 0;
                while (i < voices.length) {
                    if (pos < 0 && voices[i].equals(currentVoice)) {
                        pos = i;
                    }
                    publishProgress(new VoiceEntry[]{voices[i]});
                    i++;
                }
            }
            return Integer.valueOf(pos);
        }

        protected void onProgressUpdate(VoiceEntry... voices) {
            VoiceOverviewFragment.this.mAdapter.add(voices[0]);
        }

        protected void onPreExecute() {
            if (VoiceOverviewFragment.this.mList.getVisibility() == 0) {
                VoiceOverviewFragment.this.mList.setVisibility(4);
            }
            VoiceOverviewFragment.this.mAdapter.clear();
            VoiceOverviewFragment.this.mSmartProgressBar.startWithFadeIn();
        }

        protected void onPostExecute(Integer result) {
            boolean isCurrent = result.intValue() >= 0;
            VoiceOverviewFragment.this.mList.setItemChecked(result.intValue() + 1, isCurrent);
            if (isCurrent) {
                VoiceEntry item = (VoiceEntry) VoiceOverviewFragment.this.mAdapter.getItem(result.intValue());
                if (VoiceOverviewFragment.this.saveCurrentVoice(item.getLanguage(), item.hashCode(), item.isTTS())) {
                    SettingsManager.nativeSetVoice(item.getFolder(), item.getPersonName(), item.isTTS());
                }
            }
            VoiceOverviewFragment.this.mSmartProgressBar.stopAndCrossfadeWith(VoiceOverviewFragment.this.mList);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.VoiceOverviewFragment.3 */
    class C16563 implements OnClickListener {
        final /* synthetic */ boolean val$isTTS;
        final /* synthetic */ String val$listId;
        final /* synthetic */ String val$title;

        C16563(String str, String str2, boolean z) {
            this.val$title = str;
            this.val$listId = str2;
            this.val$isTTS = z;
        }

        public void onClick(View view) {
            VoiceOverviewFragment.this.mOnTop = false;
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, this.val$title);
            bundle.putString("map_list_id", this.val$listId);
            Fragments.add(VoiceOverviewFragment.this.getActivity(), this.val$isTTS ? TTSVoiceComponentsFragment.class : ComponentsFragment.class, this.val$title, bundle, true, VoiceOverviewFragment.this);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.VoiceOverviewFragment.4 */
    class C16574 implements OnClickListener {
        final /* synthetic */ String val$productId;
        final /* synthetic */ String val$title;

        C16574(String str, String str2) {
            this.val$title = str;
            this.val$productId = str2;
        }

        public void onClick(View view) {
            VoiceOverviewFragment.this.mOnTop = false;
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, this.val$title);
            bundle.putString("product_id", this.val$productId);
            bundle.putString("source", VoiceOverviewFragment.TAG);
            Fragments.add(VoiceOverviewFragment.this.getActivity(), MarketPlaceFragment.class, this.val$title, bundle, true, VoiceOverviewFragment.this);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.VoiceOverviewFragment.5 */
    static /* synthetic */ class C16585 {
        static final /* synthetic */ int[] f1286x5a2b1c05;

        static {
            f1286x5a2b1c05 = new int[MoreType.values().length];
            try {
                f1286x5a2b1c05[MoreType.STANDARD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1286x5a2b1c05[MoreType.TTS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1286x5a2b1c05[MoreType.FUNNY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private enum MoreType {
        STANDARD,
        TTS,
        FUNNY
    }

    public VoiceOverviewFragment() {
        this.mOnTop = true;
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(this.mTitle);
        toolbar.inflateMenu(2131755035);
        toolbar.setOnMenuItemClickListener(new C16541());
        if (VERSION.SDK_INT < 15) {
            MenuItem item = toolbar.getMenu().findItem(2131624701);
            if (item != null) {
                item.setEnabled(false);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624701) {
            return super.onOptionsItemSelected(item);
        }
        if (!SygicHelper.getAndroidTts().openConfiguration(getActivity())) {
            SToast.makeText(getActivity(), C1799R.string.anui_settings_voices_tts_no_engine, 0).show();
        }
        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mTitle = bundle.getString(AbstractFragment.ARG_TITLE);
        }
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    public void onResume() {
        super.onResume();
        if (this.mOnTop && this.mList != null && this.mAdapter != null) {
            loadInstalledVoices();
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void loadInstalledVoices() {
        AsyncTaskHelper.execute(new C16552());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903138, container, false);
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        View headerView = inflater.inflate(2130903279, this.mList, false);
        View footerView = inflater.inflate(2130903224, this.mList, false);
        ((STextView) headerView.findViewById(16908310)).setCoreText(2131165888);
        ((STextView) footerView.findViewById(2131624506).findViewById(16908310)).setCoreText(2131165887);
        setupMoreButton(footerView.findViewById(2131624507), MoreType.TTS);
        setupMoreButton(footerView.findViewById(2131624508), MoreType.STANDARD);
        setupMoreButton(footerView.findViewById(2131624509), MoreType.FUNNY);
        this.mAdapter = new VoicesAdapter(getActivity());
        this.mList = (ListView) view.findViewById(2131624221);
        this.mList.setChoiceMode(1);
        this.mList.addHeaderView(headerView, null, false);
        this.mList.addFooterView(footerView, null, false);
        this.mList.setAdapter(this.mAdapter);
        this.mList.setOnItemClickListener(this);
        return view;
    }

    private void setupMoreButton(View rootView, MoreType type) {
        int titleTextId;
        OnClickListener listener;
        STextView title = (STextView) rootView.findViewById(C1799R.id.title);
        TextView summary = (TextView) rootView.findViewById(2131624505);
        ImageView image = (ImageView) rootView.findViewById(2131624504);
        ImageView actionIcon = (ImageView) rootView.findViewById(2131624480);
        switch (C16585.f1286x5a2b1c05[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                titleTextId = 2131165889;
                listener = getDownloadListener(ResourceManager.getCoreString(getActivity(), 2131165889), "manage-voices-nontts", false);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                titleTextId = 2131165890;
                listener = getDownloadListener(ResourceManager.getCoreString(getActivity(), 2131165890), "manage-voices-tts", true);
                break;
            case TTSConst.TTSUNICODE /*3*/:
                titleTextId = 2131165886;
                listener = getStoreListener(ResourceManager.getCoreString(getActivity(), 2131165886), "funny-voices");
                break;
            default:
                return;
        }
        title.setCoreText(titleTextId);
        summary.setText(null);
        summary.setVisibility(8);
        image.setImageDrawable(null);
        actionIcon.setImageDrawable(null);
        rootView.setOnClickListener(listener);
    }

    private OnClickListener getDownloadListener(String title, String listId, boolean isTTS) {
        return new C16563(title, listId, isTTS);
    }

    private OnClickListener getStoreListener(String title, String productId) {
        return new C16574(title, productId);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VoiceEntry item = (VoiceEntry) parent.getItemAtPosition(position);
        if (saveCurrentVoice(item.getLanguage(), item.hashCode(), item.isTTS())) {
            SettingsManager.nativeSetVoice(item.getFolder(), item.getPersonName(), item.isTTS());
            ((ListView) parent).setItemChecked(position, true);
        }
        SettingsManager.nativePlaySample(item.getSample(), item.isTTS());
    }

    private boolean saveCurrentVoice(String voiceName, int hash, boolean isTTS) {
        Context context = getActivity();
        if (context == null) {
            return false;
        }
        String key = getArguments().getString(SettingsFragment.ARG_KEY);
        String hashKey = context.getString(2131166729);
        if (this.mSharedPreferences.getString(key, "").compareTo(voiceName) == 0 && hash == this.mSharedPreferences.getInt(hashKey, hash)) {
            return false;
        }
        Editor editor = this.mSharedPreferences.edit();
        editor.putBoolean(context.getString(2131166730), isTTS);
        editor.putString(key, voiceName);
        editor.putInt(hashKey, hash).commit();
        return true;
    }

    public void onComponentsFragmentResult(boolean changed) {
        if (changed) {
            loadInstalledVoices();
        }
        this.mOnTop = true;
    }

    public void onDashboardFragmentFinished() {
        loadInstalledVoices();
        this.mOnTop = true;
    }
}
