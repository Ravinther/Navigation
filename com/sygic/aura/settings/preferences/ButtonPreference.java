package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.sygic.aura.settings.StyleablePreference;
import com.sygic.base.C1799R;

public abstract class ButtonPreference extends CoreTitlePreference implements StyleablePreference {

    /* renamed from: com.sygic.aura.settings.preferences.ButtonPreference.1 */
    class C16661 implements OnClickListener {
        C16661() {
        }

        public void onClick(View view) {
            ButtonPreference.this.onButtonClicked(view);
        }
    }

    protected abstract void onButtonClicked(View view);

    public ButtonPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ButtonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayoutResource(2130903276);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        Button resetButton = (Button) view.findViewById(C1799R.id.button);
        resetButton.setText(getTitle());
        resetButton.setOnClickListener(new C16661());
    }
}
