package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.sygic.aura.C1090R;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.network.AccountManager;
import com.sygic.aura.resources.ResourceManager;

public class LoginDetailsPreference extends Preference implements OnEditorActionListener {
    private boolean mLoadName;
    private StringBuilder mName;
    private StringBuilder mPassword;
    private boolean mShowName;
    private boolean mShowPassword;
    private View mView;

    private class TextChangedWatcher implements TextWatcher {
        private final StringBuilder mStoreText;

        private TextChangedWatcher(StringBuilder storeText) {
            this.mStoreText = storeText;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            this.mStoreText.replace(0, this.mStoreText.length(), s.toString());
        }
    }

    public LoginDetailsPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPref(context, attrs);
    }

    public LoginDetailsPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPref(context, attrs);
    }

    public LoginDetailsPreference(Context context) {
        this(context, null);
    }

    private void initPref(Context context, AttributeSet attrs) {
        setLayoutResource(2130903066);
        this.mName = new StringBuilder();
        this.mPassword = new StringBuilder();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.LoginDetailsPreference, 0, 0);
            this.mShowName = a.getBoolean(0, true);
            this.mShowPassword = a.getBoolean(1, true);
            this.mLoadName = a.getBoolean(2, false);
            a.recycle();
        }
    }

    public String getName() {
        return this.mName.toString().trim();
    }

    public String getPassword() {
        return this.mPassword.toString();
    }

    public View getView(View convertView, ViewGroup parent) {
        if (this.mView == null) {
            this.mView = super.getView(convertView, parent);
        }
        return this.mView;
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        if (this.mLoadName) {
            this.mName.append(getPersistedString(""));
        }
        EditText name = (EditText) view.findViewById(2131624105);
        name.setHint(ResourceManager.getCoreString(name.getHint().toString()));
        if (this.mShowName) {
            if (TextUtils.isEmpty(this.mName)) {
                String strName = AccountManager.nativeGetSygicLogin();
                if (!TextUtils.isEmpty(strName)) {
                    this.mName.append(strName);
                }
            }
            name.setText(this.mName);
            name.addTextChangedListener(new TextChangedWatcher(this.mName, null));
        } else {
            name.setVisibility(8);
        }
        EditText password = (EditText) view.findViewById(2131624106);
        password.setHint(ResourceManager.getCoreString(password.getHint().toString()));
        if (this.mShowPassword) {
            String strPass = AccountManager.nativeGetSygicPassStars();
            if (!TextUtils.isEmpty(strPass)) {
                password.setHint(strPass);
            }
            password.setText(this.mPassword);
            password.addTextChangedListener(new TextChangedWatcher(this.mPassword, null));
            password.setOnEditorActionListener(this);
            return;
        }
        password.setVisibility(8);
    }

    public void persistName() {
        persistString(getName());
    }

    public void hideKeyboard() {
        NaviNativeActivity.hideKeyboard(this.mView.getWindowToken());
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == 6) {
            AccountManager.nativeSygicLogin(getName(), getPassword());
        }
        return false;
    }
}
