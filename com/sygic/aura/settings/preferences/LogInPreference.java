package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public abstract class LogInPreference extends ButtonPreference {

    /* renamed from: com.sygic.aura.settings.preferences.LogInPreference.1 */
    class C16691 implements Runnable {
        C16691() {
        }

        public void run() {
            LogInPreference.this.login();
        }
    }

    protected abstract void login();

    public LogInPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LogInPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LogInPreference(Context context) {
        super(context);
    }

    protected void onButtonClicked(View buttonView) {
        DependencyStatePreference preference = (DependencyStatePreference) findPreferenceInHierarchy(getContext().getString(2131165286));
        if (preference != null) {
            preference.toggle();
            buttonView.post(new C16691());
        }
    }

    protected void styleButton(Button button) {
        String source = button.getText().toString();
        SpannableString text = new SpannableString(source);
        text.setSpan(new StyleSpan(1), source.lastIndexOf(" ") + 1, source.length(), 0);
        button.setText(text);
    }
}
