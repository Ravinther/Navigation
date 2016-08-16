package com.sygic.aura.settings.preferences;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.StyleablePreference;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.VoiceEntry;
import com.sygic.base.C1799R;
import loquendo.tts.engine.TTSConst;

public class BluetoothPreference extends DialogPreference implements StyleablePreference {
    private final VoiceEntry mCurrentVoice;
    private final int mDefaultHfpDelay;
    private final String mDelayText;
    private final String mKeyHfpDelay;
    private int mOriginalHfpDelay;
    private final String mPreviewButtonString;
    private RadioGroup mRadioGroup;
    private final String mRadioTitleBluetooth;
    private final String mRadioTitleBluetoothHfp;
    private final String mRadioTitlePhoneSpeaker;
    private SeekBar mSeekBarHfpDelay;
    private TextView mSeekBarText;
    private TextView mSeekBarTitle;
    private String mValue;
    private boolean mValueSet;

    /* renamed from: com.sygic.aura.settings.preferences.BluetoothPreference.1 */
    class C16641 implements OnSeekBarChangeListener {
        C16641() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            BluetoothPreference.this.mSeekBarText.setText(String.format(BluetoothPreference.this.mDelayText, new Object[]{Integer.valueOf(progress * 100)}));
            BluetoothPreference.this.persistInt(BluetoothPreference.this.mKeyHfpDelay, progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    /* renamed from: com.sygic.aura.settings.preferences.BluetoothPreference.2 */
    class C16652 implements OnClickListener {
        C16652() {
        }

        public void onClick(View v) {
            if (BluetoothPreference.this.mCurrentVoice != null) {
                SettingsManager.nativePlaySample(BluetoothPreference.this.mCurrentVoice.getSample(), BluetoothPreference.this.mCurrentVoice.isTTS());
            }
        }
    }

    protected class BluetoothRadioListener implements OnCheckedChangeListener {
        private final SeekBar mHfpDelaySeekBar;
        private final TextView mHfpDelayText;
        private final TextView mHfpDelayTitle;

        public BluetoothRadioListener(SeekBar hfpDelaySeekBar, TextView hfpDelayText, TextView hfpDelayTitle) {
            this.mHfpDelaySeekBar = hfpDelaySeekBar;
            this.mHfpDelayText = hfpDelayText;
            this.mHfpDelayTitle = hfpDelayTitle;
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == 2131624126 || checkedId == 2131624127) {
                this.mHfpDelaySeekBar.setEnabled(false);
                this.mHfpDelayText.setEnabled(false);
                this.mHfpDelayTitle.setEnabled(false);
            } else if (checkedId == 2131624128) {
                this.mHfpDelaySeekBar.setEnabled(true);
                this.mHfpDelayText.setEnabled(true);
                this.mHfpDelayTitle.setEnabled(true);
            }
            BluetoothPreference.this.persistString(BluetoothPreference.this.getValue(checkedId));
        }
    }

    public BluetoothPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(2130903076);
        this.mPreviewButtonString = ResourceManager.getCoreString(context, 2131165848);
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.BluetoothPreference);
        this.mDefaultHfpDelay = a.getInt(0, 0);
        a.recycle();
        this.mRadioTitlePhoneSpeaker = ResourceManager.getCoreString(context, 2131165845);
        this.mRadioTitleBluetoothHfp = ResourceManager.getCoreString(context, 2131165847);
        this.mRadioTitleBluetooth = ResourceManager.getCoreString(context, 2131165846);
        this.mKeyHfpDelay = context.getString(C1799R.string.settings_sound_bluetooth_hfp_delay);
        this.mDelayText = ResourceManager.getCoreString(context, 2131165844);
        this.mCurrentVoice = SettingsManager.nativeGetSelectedVoice();
        String title = ResourceManager.getCoreString(String.valueOf(getTitle()));
        setTitle(title);
        setDialogTitle(title);
    }

    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        this.mSeekBarHfpDelay = (SeekBar) view.findViewById(2131624130);
        this.mSeekBarText = (TextView) view.findViewById(2131624131);
        this.mSeekBarTitle = (TextView) view.findViewById(2131624129);
        this.mSeekBarHfpDelay.setOnSeekBarChangeListener(new C16641());
        this.mOriginalHfpDelay = getPersistedInt(this.mKeyHfpDelay, this.mDefaultHfpDelay);
        this.mSeekBarHfpDelay.setProgress(this.mOriginalHfpDelay);
        this.mRadioGroup = (RadioGroup) view.findViewById(2131624125);
        this.mRadioGroup.setOnCheckedChangeListener(new BluetoothRadioListener(this.mSeekBarHfpDelay, this.mSeekBarText, this.mSeekBarTitle));
        RadioButton radioPhoneSpeaker = (RadioButton) view.findViewById(2131624126);
        radioPhoneSpeaker.setText(this.mRadioTitlePhoneSpeaker);
        RadioButton radioBluetoothHfp = (RadioButton) view.findViewById(2131624128);
        radioBluetoothHfp.setText(this.mRadioTitleBluetoothHfp);
        RadioButton radioBluetooth = (RadioButton) view.findViewById(2131624127);
        radioBluetooth.setText(this.mRadioTitleBluetooth);
        String str = this.mValue;
        boolean z = true;
        switch (str.hashCode()) {
            case C1090R.styleable.Theme_homeAsUpIndicator /*48*/:
                if (str.equals("0")) {
                    z = false;
                    break;
                }
                break;
            case C1090R.styleable.Theme_actionButtonStyle /*49*/:
                if (str.equals("1")) {
                    z = true;
                    break;
                }
                break;
            case TTSConst.TTSEVT_RESERVED /*50*/:
                if (str.equals("2")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                radioPhoneSpeaker.setChecked(true);
                this.mSeekBarText.setEnabled(false);
                this.mSeekBarHfpDelay.setEnabled(false);
            case TTSConst.TTSMULTILINE /*1*/:
                radioBluetooth.setChecked(true);
                this.mSeekBarText.setEnabled(false);
                this.mSeekBarHfpDelay.setEnabled(false);
            case TTSConst.TTSPARAGRAPH /*2*/:
                radioBluetoothHfp.setChecked(true);
                this.mSeekBarText.setEnabled(true);
                this.mSeekBarHfpDelay.setEnabled(true);
            default:
        }
    }

    protected void onPrepareDialogBuilder(Builder builder) {
        builder.setNeutralButton(this.mPreviewButtonString, null);
    }

    protected void showDialog(Bundle state) {
        super.showDialog(state);
        Dialog dialog = getDialog();
        if (dialog != null && (dialog instanceof AlertDialog)) {
            ((AlertDialog) dialog).getButton(-3).setOnClickListener(new C16652());
        }
    }

    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            String value = getValue();
            if (callChangeListener(value)) {
                setValue(value);
                persistInt(this.mKeyHfpDelay, this.mSeekBarHfpDelay.getProgress());
                return;
            }
            return;
        }
        persistString(this.mValue);
        persistInt(this.mKeyHfpDelay, this.mOriginalHfpDelay);
    }

    public CharSequence getSummary() {
        if ("2".equals(this.mValue)) {
            return this.mRadioTitleBluetoothHfp;
        }
        if ("1".equals(this.mValue)) {
            return this.mRadioTitleBluetooth;
        }
        if ("0".equals(this.mValue)) {
            return this.mRadioTitlePhoneSpeaker;
        }
        return super.getSummary();
    }

    private String getValue() {
        return getValue(this.mRadioGroup.getCheckedRadioButtonId());
    }

    private String getValue(int radioId) {
        if (radioId == 2131624126) {
            return "0";
        }
        if (radioId == 2131624127) {
            return "1";
        }
        if (radioId == 2131624128) {
            return "2";
        }
        return this.mValue;
    }

    protected boolean persistInt(String key, int value) {
        if (!shouldPersist()) {
            return false;
        }
        Editor editor = getEditor();
        editor.putInt(key, value);
        if (shouldCommit()) {
            editor.apply();
        }
        return true;
    }

    public void setValue(String value) {
        boolean changed = !TextUtils.equals(this.mValue, value);
        if (changed || !this.mValueSet) {
            this.mValue = value;
            this.mValueSet = true;
            persistString(value);
            if (changed) {
                notifyChanged();
            }
        }
    }

    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
            defaultValue = getPersistedString(this.mValue);
        } else {
            String defaultValue2 = (String) defaultValue;
        }
        setValue(defaultValue);
    }

    public void style(View view) {
    }

    protected int getPersistedInt(String key, int defaultReturnValue) {
        return !shouldPersist() ? defaultReturnValue : getPreferenceManager().getSharedPreferences().getInt(key, defaultReturnValue);
    }
}
