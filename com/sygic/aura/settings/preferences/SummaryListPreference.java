package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.StyleablePreference;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.utils.SygicLangHelper;
import com.sygic.aura.utils.SygicTextUtils;

public class SummaryListPreference extends ListPreference implements StyleablePreference {
    private TextView mSummary;

    public SummaryListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        String title = ResourceManager.getCoreString(getTitle());
        if (SygicLangHelper.isEngLangString(SettingsManager.nativeGetSelectedLanguage())) {
            title = SygicTextUtils.capitalizeEachWord(title);
        }
        setTitle(title);
        setDialogTitle(title);
        CharSequence[] entries = getEntries();
        if (entries != null) {
            for (int i = 0; i < entries.length; i++) {
                entries[i] = ResourceManager.getCoreString(entries[i].toString());
            }
        }
    }

    public SummaryListPreference(Context context) {
        super(context);
    }

    protected void setEntry() {
        if (this.mSummary != null && getSummary() == null) {
            CharSequence entry = getEntry();
            this.mSummary.setVisibility(entry == null ? 8 : 0);
            this.mSummary.setText(entry);
        }
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        this.mSummary = (TextView) view.findViewById(16908304);
        setEntry();
    }

    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            setEntry();
        }
    }

    public void style(View view) {
    }
}
