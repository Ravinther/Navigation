package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.content.res.Resources;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sygic.aura.resources.Typefaces;
import com.sygic.aura.settings.data.SettingsManager;

public class TextInfo extends Preference {
    public TextInfo(Context context) {
        super(context);
    }

    public TextInfo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected View onCreateView(ViewGroup parent) {
        Resources res = getContext().getResources();
        int padding = (int) res.getDimension(2131231022);
        TextView textView = new TextView(getContext());
        textView.setBackgroundColor(res.getColor(2131558436));
        textView.setTextColor(res.getColor(2131558731));
        textView.setText(SettingsManager.nativeGetEulaText());
        textView.setTypeface(Typefaces.getFont(getContext(), 2131166098));
        textView.setPadding(padding, padding, padding, padding);
        return textView;
    }
}
