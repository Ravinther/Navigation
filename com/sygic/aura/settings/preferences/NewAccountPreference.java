package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.sygic.aura.SygicMain;
import com.sygic.base.C1799R;

public class NewAccountPreference extends SettingsFragmentPreference {

    /* renamed from: com.sygic.aura.settings.preferences.NewAccountPreference.1 */
    class C16711 implements OnClickListener {
        C16711() {
        }

        public void onClick(View view) {
            SygicMain.getInstance().getFeature().getSystemFeature().browserOpenUri("http://www.sygic.com/en/registration", null, null);
        }
    }

    public NewAccountPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public NewAccountPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewAccountPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayoutResource(2130903276);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        Button resetButton = (Button) view.findViewById(C1799R.id.button);
        resetButton.setBackgroundResource(2130837615);
        resetButton.setText(getTitle());
        resetButton.setOnClickListener(new C16711());
    }

    protected void onClick() {
        LoginDetailsPreference preference = (LoginDetailsPreference) findPreferenceInHierarchy(getContext().getString(2131165287));
        if (preference != null) {
            preference.persistName();
        }
        super.onClick();
    }
}
