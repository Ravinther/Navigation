package com.sygic.aura.settings.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.settings.data.LangEntry;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.model.LanguagesAdapter;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.font_specials.SToolbar;

public class LanguageSettingsFragment extends AbstractScreenFragment implements OnItemClickListener {
    private LanguagesAdapter mAdapter;
    private ListView mListView;
    private SmartProgressBar mSmartProgressBar;

    private class LoadLanguagesTask extends AsyncTask<Void, Void, LangEntry[]> {
        private LoadLanguagesTask() {
        }

        protected LangEntry[] doInBackground(Void... params) {
            return LanguageSettingsFragment.this.mAdapter.loadItems();
        }

        protected void onPreExecute() {
            LanguageSettingsFragment.this.mSmartProgressBar.startWithFadeIn();
        }

        protected void onPostExecute(LangEntry[] result) {
            LanguageSettingsFragment.this.mAdapter.setData(result);
            int selectedIndex = LanguageSettingsFragment.this.mAdapter.getItemIndexByFileName(SettingsManager.nativeGetSelectedLanguage());
            if (selectedIndex != -1) {
                LanguageSettingsFragment.this.mListView.setItemChecked(selectedIndex, true);
            }
            LanguageSettingsFragment.this.mSmartProgressBar.stopAndCrossfadeWith(LanguageSettingsFragment.this.mListView);
        }
    }

    public LanguageSettingsFragment() {
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165811);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903109, container, false);
        this.mListView = (ListView) view.findViewById(2131624221);
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        this.mAdapter = new LanguagesAdapter(getActivity());
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(this);
        AsyncTaskHelper.execute(new LoadLanguagesTask());
        return view;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        LangEntry langEntry = (LangEntry) this.mAdapter.getItem(position);
        String key = getArguments().getString(SettingsFragment.ARG_KEY);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(key, langEntry.getFileName()).putString(getArguments().getString("fragment_data"), langEntry.getLanguage()).apply();
        this.mToolbar.setTitle(2131165811);
    }
}
