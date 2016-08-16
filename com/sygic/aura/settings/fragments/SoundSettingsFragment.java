package com.sygic.aura.settings.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.fragments.UserTTSDialogFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.ESoundSettingsType;
import com.sygic.aura.settings.data.SoundEntry;
import com.sygic.aura.settings.model.SoundsAdapter;
import com.sygic.aura.views.font_specials.SToolbar;

public class SoundSettingsFragment extends AbstractScreenFragment implements OnItemClickListener {
    private static final String NONE = "NONE";
    private SoundsAdapter mAdapter;
    private boolean mCustomNotSet;
    private UserTTSDialogFragment mCustomTTSDialog;
    private SoundEntry mCustomTTSEntry;
    private SoundEntry mDefaultTTSEntry;
    private ListView mList;
    private int mPos;
    private SharedPreferences mSharedPreferences;
    private String mTitle;
    ESoundSettingsType mWarnType;

    /* renamed from: com.sygic.aura.settings.fragments.SoundSettingsFragment.1 */
    class C16511 implements DialogFragmentClickListener {
        final /* synthetic */ int val$position;

        C16511(int i) {
            this.val$position = i;
        }

        public void onPositiveButtonClicked(Editable text) {
            String strMsg = text.toString();
            SoundSettingsFragment.this.setCustomTTSMsg(this.val$position, strMsg);
            SoundSettingsFragment.this.mCustomTTSDialog.setText(strMsg);
            SoundSettingsFragment.this.mPos = this.val$position;
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.SoundSettingsFragment.2 */
    class C16522 implements OnCancelListener {
        final /* synthetic */ AdapterView val$parent;

        C16522(AdapterView adapterView) {
            this.val$parent = adapterView;
        }

        public void onCancel(DialogInterface dialog) {
            ((ListView) this.val$parent).setItemChecked(SoundSettingsFragment.this.mPos, true);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.SoundSettingsFragment.3 */
    class C16533 implements TextWatcher {
        C16533() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            SoundSettingsFragment.this.mCustomTTSDialog.setButtonEnabled(-1, !TextUtils.isEmpty(s.toString()));
        }
    }

    public SoundSettingsFragment() {
        this.mWarnType = ESoundSettingsType.eNone;
        this.mCustomTTSEntry = null;
        this.mDefaultTTSEntry = null;
        this.mCustomNotSet = false;
        this.mPos = -1;
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(this.mTitle);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mTitle = bundle.getString(AbstractFragment.ARG_TITLE);
            String strType = bundle.getString("fragment_data");
            this.mWarnType = strType != null ? ESoundSettingsType.valueOf(strType) : ESoundSettingsType.eNone;
        }
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903110, container, false);
        this.mList = (ListView) view.findViewById(2131624221);
        this.mList.setChoiceMode(1);
        this.mAdapter = new SoundsAdapter(getActivity());
        this.mPos = loadSounds();
        this.mList.setAdapter(this.mAdapter);
        this.mList.setOnItemClickListener(this);
        this.mList.setItemChecked(this.mPos, true);
        return view;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = null;
        SoundEntry item = (SoundEntry) this.mAdapter.getItem(position);
        if (item.equals(this.mCustomTTSEntry)) {
            Context activity = getActivity();
            String name = (this.mCustomTTSEntry == null || this.mCustomNotSet) ? null : this.mCustomTTSEntry.getName();
            if (this.mDefaultTTSEntry != null) {
                str = this.mDefaultTTSEntry.getName();
            }
            this.mCustomTTSDialog = UserTTSDialogFragment.newInstance(activity, 2131166003, name, str, new C16511(position), new C16522(parent));
            this.mCustomTTSDialog.addTextChangedListener(new C16533());
            this.mCustomTTSDialog.showDialog();
            return;
        }
        saveSound(item);
    }

    private void saveSound(SoundEntry entry) {
        String key = getArguments().getString(SettingsFragment.ARG_KEY);
        String strName = entry.getName();
        if (entry.isTTS() && !entry.equals(this.mCustomTTSEntry)) {
            strName = ResourceManager.getCoreString(getActivity(), 2131165876);
        }
        this.mSharedPreferences.edit().putString(key, strName).commit();
        if (entry.isTTS()) {
            SettingsManager.nativePlayWarnSample(entry.getName(), true);
            if (!entry.equals(this.mCustomTTSEntry)) {
                strName = "";
            }
            SettingsManager.nativeSetTTSSound(strName, this.mWarnType);
        } else {
            SettingsManager.nativeSetSound(entry.getFolder(), this.mWarnType);
            SettingsManager.nativePlayWarnSample(entry.getSample(), false);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void setCustomTTSMsg(int position, String strMsg) {
        SoundEntry item = (SoundEntry) this.mAdapter.getItem(position);
        if (item != null) {
            item.setName(strMsg);
            saveSound(item);
        }
    }

    private int loadSounds() {
        SoundEntry[] sounds = this.mAdapter.loadItems();
        String strValue = SettingsManager.nativeGetSelectedSound(this.mWarnType);
        this.mAdapter.insert(new SoundEntry(ResourceManager.getCoreString(getActivity(), 2131165874), NONE, false), 0);
        int pos = -1;
        for (int i = 0; i != sounds.length; i++) {
            SoundEntry item = sounds[i];
            this.mAdapter.add(item);
            if (strValue.equals(item.getFolder())) {
                pos = i + 1;
            }
        }
        String strCustomTTS = SettingsManager.nativeGetCustomTTS(this.mWarnType);
        String strDefaultTTS = ResourceManager.getCoreString(getActivity(), 2131165876);
        if (strCustomTTS == null && strDefaultTTS == null) {
            return pos;
        }
        if (strDefaultTTS != null) {
            SoundEntry entry = new SoundEntry(strDefaultTTS, "", true);
            this.mDefaultTTSEntry = entry;
            this.mAdapter.insert(entry, 1);
            pos++;
        }
        if (strCustomTTS != null) {
            String strName = strCustomTTS;
            this.mCustomNotSet = TextUtils.isEmpty(strName);
            if (this.mCustomNotSet) {
                strName = ResourceManager.getCoreString(getActivity(), 2131165877);
            }
            entry = new SoundEntry(strName, "", true);
            this.mCustomTTSEntry = entry;
            this.mAdapter.insert(entry, 2);
            pos++;
        }
        boolean bDefaultTTS = SettingsManager.nativeIsDefaultTTSEnabled(this.mWarnType);
        boolean bTTSEnabled = SettingsManager.nativeIsTTSEnabled(this.mWarnType);
        if (bDefaultTTS) {
            return 1;
        }
        return bTTSEnabled ? 2 : pos;
    }
}
