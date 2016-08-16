package com.sygic.aura.settings.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import com.sygic.aura.settings.data.LangEntry;
import com.sygic.aura.settings.data.SettingsManager;
import java.util.Locale;

public class LanguagesAdapter extends SettingsAdapter<LangEntry> {

    public class ViewHolder extends com.sygic.aura.settings.model.SettingsAdapter$com.sygic.aura.settings.model.SettingsAdapter.ViewHolder {
        protected ViewHolder(View rootView) {
            super(rootView);
        }

        @TargetApi(17)
        protected void update(LangEntry entry) {
            super.update(entry);
            int flagIcon = LanguagesAdapter.isoCountryCodeToFlagDrawable(LanguagesAdapter.this.getContext(), entry.getISO());
            if (VERSION.SDK_INT < 17) {
                this.title.setCompoundDrawablesWithIntrinsicBounds(flagIcon, 0, 0, 0);
            } else {
                this.title.setCompoundDrawablesRelativeWithIntrinsicBounds(flagIcon, 0, 0, 0);
            }
        }
    }

    public LanguagesAdapter(Context context) {
        super(context);
    }

    public LangEntry[] loadItems() {
        return SettingsManager.nativeGetLangFiles();
    }

    public CharSequence getItemName(LangEntry item) {
        return item.getLanguage();
    }

    public void setData(LangEntry[] entries) {
        clear();
        if (entries != null) {
            addAll(entries);
        }
    }

    public int getItemIndexByFileName(String fileName) {
        int count = getCount();
        for (int i = 0; i < count; i++) {
            if (((LangEntry) getItem(i)).getFileName().equals(fileName)) {
                return i;
            }
        }
        return -1;
    }

    protected com.sygic.aura.settings.model.SettingsAdapter$com.sygic.aura.settings.model.SettingsAdapter.ViewHolder createViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    private static int isoCountryCodeToFlagDrawable(Context context, String isoCountryCode) {
        return context.getResources().getIdentifier("flg_".concat(isoCountryCode.toLowerCase(Locale.US)), "drawable", context.getPackageName());
    }
}
