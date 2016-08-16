package com.sygic.aura.settings.preferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.StyleablePreference;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;
import java.lang.reflect.Method;
import loquendo.tts.engine.TTSConst;

public class ThreeSeekBarsPreference extends DialogPreference implements StyleablePreference {
    private final SparseArray<CharSequence[]> mCustomValues;
    private final EWarningFormatterType mFormatterFirst;
    private final EWarningFormatterType mFormatterSecond;
    private final EWarningFormatterType mFormatterThird;
    private final Drawable mIconFirst;
    private final Drawable mIconSecond;
    private final Drawable mIconThird;
    private final String mKeyFirst;
    private final String mKeySecond;
    private final String mKeyThird;
    private final String mMessage;
    private final int mRangeMaxFirst;
    private final int mRangeMaxSecond;
    private final int mRangeMaxThird;
    private SeekBar mSeekBarFirst;
    private SeekBar mSeekBarSecond;
    private SeekBar mSeekBarThird;
    private final String mTitleFirst;
    private final String mTitleSecond;
    private final String mTitleThird;

    /* renamed from: com.sygic.aura.settings.preferences.ThreeSeekBarsPreference.1 */
    static /* synthetic */ class C16741 {
        static final /* synthetic */ int[] f1288xa92f4b02;

        static {
            f1288xa92f4b02 = new int[EWarningFormatterType.values().length];
            try {
                f1288xa92f4b02[EWarningFormatterType.eDistance.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1288xa92f4b02[EWarningFormatterType.eCustom.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1288xa92f4b02[EWarningFormatterType.eSpeed.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1288xa92f4b02[EWarningFormatterType.eTime.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1288xa92f4b02[EWarningFormatterType.eAngle.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1288xa92f4b02[EWarningFormatterType.ePlain.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1288xa92f4b02[EWarningFormatterType.eNone.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public enum EWarningFormatterType {
        eDistance,
        eSpeed,
        eTime,
        eAngle,
        ePlain,
        eCustom,
        eNone;
        
        private int index;
    }

    protected class SeekBarChangeListener implements OnSeekBarChangeListener {
        private final EWarningFormatterType mFormater;
        private final String mSufix;
        private final STextView mValueTextView;

        public SeekBarChangeListener(View view, int viewId, EWarningFormatterType formater, String suffix) {
            this.mValueTextView = (STextView) view.findViewById(viewId);
            this.mFormater = formater;
            if (suffix == null) {
                suffix = "";
            }
            this.mSufix = suffix;
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (C16741.f1288xa92f4b02[this.mFormater.ordinal()]) {
                case TTSConst.TTSPARAGRAPH /*2*/:
                    CharSequence[] charSequences = (CharSequence[]) ThreeSeekBarsPreference.this.mCustomValues.get(this.mFormater.index);
                    STextView sTextView = this.mValueTextView;
                    CharSequence charSequence = (charSequences == null || progress >= charSequences.length) ? "" : charSequences[progress];
                    sTextView.setCoreText(ResourceManager.getCoreString(charSequence));
                case TTSConst.TTSUNICODE /*3*/:
                    this.mValueTextView.setText(this.mSufix.replace("%SPEED%", ResourceManager.nativeFormatSpeed((double) progress, false, true)));
                case TTSConst.TTSXML /*4*/:
                    this.mValueTextView.setText(Integer.toString(progress) + "sec");
                case TTSConst.TTSEVT_TEXT /*5*/:
                    this.mValueTextView.setText(this.mSufix.replace("%ANGLE%", Integer.toString((progress + 1) * 10)));
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    this.mValueTextView.setText(Integer.toString(progress));
                default:
                    this.mValueTextView.setText(ResourceManager.nativeFormatDistance((long) (progress * 100)));
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    public ThreeSeekBarsPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCustomValues = new SparseArray();
        setDialogLayoutResource(2130903291);
        setPositiveButtonText(ResourceManager.getCoreString(context, (int) C1799R.string.button_ok));
        setNegativeButtonText(ResourceManager.getCoreString(context, (int) C1799R.string.button_cancel));
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.SeekBarsPreference, 0, 0);
        this.mTitleFirst = ResourceManager.getCoreString(a.getString(2));
        this.mTitleSecond = ResourceManager.getCoreString(a.getString(3));
        this.mTitleThird = ResourceManager.getCoreString(a.getString(4));
        this.mMessage = ResourceManager.getCoreString(a.getString(10));
        this.mRangeMaxFirst = a.getInt(7, -1);
        this.mRangeMaxSecond = a.getInt(8, -1);
        this.mRangeMaxThird = a.getInt(9, -1);
        String strType = a.getString(11);
        this.mFormatterFirst = strType == null ? EWarningFormatterType.eNone : EWarningFormatterType.valueOf(strType);
        if (EWarningFormatterType.eCustom.equals(this.mFormatterFirst)) {
            this.mFormatterFirst.index = 0;
            this.mCustomValues.append(this.mFormatterFirst.index, a.getTextArray(14));
        }
        strType = a.getString(12);
        this.mFormatterSecond = strType == null ? EWarningFormatterType.eNone : EWarningFormatterType.valueOf(strType);
        if (EWarningFormatterType.eCustom.equals(this.mFormatterSecond)) {
            this.mFormatterSecond.index = 1;
            this.mCustomValues.append(this.mFormatterSecond.index, a.getTextArray(15));
        }
        strType = a.getString(13);
        this.mFormatterThird = strType == null ? EWarningFormatterType.eNone : EWarningFormatterType.valueOf(strType);
        if (EWarningFormatterType.eCustom.equals(this.mFormatterThird)) {
            this.mFormatterThird.index = 2;
            this.mCustomValues.append(this.mFormatterThird.index, a.getTextArray(16));
        }
        this.mKeyFirst = getKeyPrimary();
        this.mKeySecond = getKeySecondary(a);
        this.mKeyThird = getKeyTertiary(a);
        this.mIconFirst = getIconPrimary();
        this.mIconSecond = getIconSecondary(a);
        this.mIconThird = getIconTertiary(a);
        a.recycle();
        String title = ResourceManager.getCoreString(String.valueOf(getTitle()));
        setTitle(title);
        setDialogTitle(title);
    }

    private String getKeyPrimary() {
        return getKey();
    }

    private String getKeySecondary(TypedArray a) {
        return a.getString(0);
    }

    private String getKeyTertiary(TypedArray a) {
        return a.getString(1);
    }

    private Drawable getIconPrimary() {
        Drawable icon = getDialogIcon();
        setDialogIcon(null);
        return icon;
    }

    private Drawable getIconSecondary(TypedArray a) {
        return a.getDrawable(5);
    }

    private Drawable getIconTertiary(TypedArray a) {
        return a.getDrawable(6);
    }

    protected static SeekBar getSeekBar1(View dialogView) {
        return (SeekBar) dialogView.findViewById(2131624616);
    }

    protected static SeekBar getSeekBar2(View dialogView) {
        return (SeekBar) dialogView.findViewById(2131624621);
    }

    protected static SeekBar getSeekBar3(View dialogView) {
        return (SeekBar) dialogView.findViewById(2131624626);
    }

    protected int getPersistedInt(String key, int defaultReturnValue) {
        return !shouldPersist() ? defaultReturnValue : getPreferenceManager().getSharedPreferences().getInt(key, defaultReturnValue);
    }

    protected boolean persistInt(String key, int value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedInt(key, value ^ -1)) {
            return true;
        }
        try {
            Method m = PreferenceManager.class.getDeclaredMethod("getEditor", new Class[0]);
            m.setAccessible(true);
            Editor editor = (Editor) m.invoke(getPreferenceManager(), new Object[0]);
            editor.putInt(key, value);
            tryCommit(editor);
            return true;
        } catch (Exception e) {
            Log.w("SeekBarPreference", "Reflection exception - " + e.toString());
            return false;
        }
    }

    private void setPrefSummary() {
        String summary = "";
        switch (C16741.f1288xa92f4b02[this.mFormatterFirst.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                summary = ((this.mTitleFirst + " " + ResourceManager.nativeFormatDistance((long) (getPersistedInt(this.mKeyFirst, 0) * 100))) + (this.mKeySecond != null ? ", " + this.mTitleSecond + " " + ResourceManager.nativeFormatDistance((long) (getPersistedInt(this.mKeySecond, 0) * 100)) : "")) + (this.mKeyThird != null ? ", " + this.mTitleThird + " " + ResourceManager.nativeFormatDistance((long) (getPersistedInt(this.mKeySecond, 0) * 100)) : "");
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                CharSequence[] charSequences = (CharSequence[]) this.mCustomValues.get(this.mFormatterFirst.index);
                int mode = getPersistedInt(this.mKeyFirst, 0);
                CharSequence charSequence = (charSequences == null || mode >= charSequences.length) ? "" : charSequences[mode];
                summary = ResourceManager.getCoreString(charSequence);
                break;
            case TTSConst.TTSUNICODE /*3*/:
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_TEXT /*5*/:
            case TTSConst.TTSEVT_SENTENCE /*6*/:
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                Log.w("SeekBarPreference", "None format type");
                break;
            default:
                Log.w("SeekBarPreference", "Unknown format type");
                break;
        }
        setSummary(summary);
    }

    protected void onBindView(View view) {
        setPrefSummary();
        super.onBindView(view);
    }

    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        this.mSeekBarFirst = getSeekBar1(view);
        if (this.mRangeMaxFirst >= 0) {
            this.mSeekBarFirst.setMax(this.mRangeMaxFirst);
        }
        SeekBarChangeListener seekBarChangeListener = new SeekBarChangeListener(view, 2131624613, this.mFormatterFirst, getSufix(this.mFormatterFirst));
        this.mSeekBarFirst.setOnSeekBarChangeListener(seekBarChangeListener);
        this.mSeekBarFirst.setProgress(getPersistedInt(this.mKeyFirst, 0));
        seekBarChangeListener.onProgressChanged(this.mSeekBarFirst, this.mSeekBarFirst.getProgress(), false);
        this.mSeekBarSecond = getSeekBar2(view);
        if (TextUtils.isEmpty(this.mKeySecond)) {
            this.mSeekBarSecond.setVisibility(8);
        } else {
            if (this.mRangeMaxSecond >= 0) {
                this.mSeekBarSecond.setMax(this.mRangeMaxSecond);
            }
            seekBarChangeListener = new SeekBarChangeListener(view, 2131624618, this.mFormatterSecond, getSufix(this.mFormatterSecond));
            this.mSeekBarSecond.setOnSeekBarChangeListener(seekBarChangeListener);
            this.mSeekBarSecond.setProgress(getPersistedInt(this.mKeySecond, 0));
            seekBarChangeListener.onProgressChanged(this.mSeekBarSecond, this.mSeekBarSecond.getProgress(), false);
        }
        this.mSeekBarThird = getSeekBar3(view);
        if (TextUtils.isEmpty(this.mKeyThird)) {
            this.mSeekBarThird.setVisibility(8);
        } else {
            if (this.mRangeMaxThird >= 0) {
                this.mSeekBarThird.setMax(this.mRangeMaxThird);
            }
            seekBarChangeListener = new SeekBarChangeListener(view, 2131624623, this.mFormatterThird, getSufix(this.mFormatterThird));
            this.mSeekBarThird.setOnSeekBarChangeListener(seekBarChangeListener);
            this.mSeekBarThird.setProgress(getPersistedInt(this.mKeyThird, 0));
            seekBarChangeListener.onProgressChanged(this.mSeekBarThird, this.mSeekBarThird.getProgress(), false);
        }
        ImageView iconView = (ImageView) view.findViewById(2131624615);
        if (this.mIconFirst != null) {
            iconView.setImageDrawable(this.mIconFirst);
        } else {
            iconView.setVisibility(8);
        }
        iconView = (ImageView) view.findViewById(2131624620);
        if (this.mIconSecond != null) {
            iconView.setImageDrawable(this.mIconSecond);
        } else {
            iconView.setVisibility(8);
        }
        iconView = (ImageView) view.findViewById(2131624625);
        if (this.mIconThird != null) {
            iconView.setImageDrawable(this.mIconThird);
        } else {
            iconView.setVisibility(8);
        }
        TextView tv = (TextView) view.findViewById(2131624612);
        if (this.mTitleFirst != null) {
            tv.setText(this.mTitleFirst);
        } else {
            tv.setVisibility(8);
        }
        tv = (TextView) view.findViewById(2131624617);
        if (this.mTitleSecond != null) {
            tv.setText(this.mTitleSecond);
        } else {
            tv.setVisibility(8);
        }
        tv = (TextView) view.findViewById(2131624622);
        if (this.mTitleThird != null) {
            tv.setText(this.mTitleThird);
        } else {
            tv.setVisibility(8);
        }
        tv = (TextView) view.findViewById(2131624627);
        if (this.mMessage != null) {
            tv.setText(this.mMessage);
        } else {
            tv.setVisibility(8);
        }
    }

    private String getSufix(EWarningFormatterType formaterType) {
        switch (C16741.f1288xa92f4b02[formaterType.ordinal()]) {
            case TTSConst.TTSUNICODE /*3*/:
                return ResourceManager.getCoreString(getContext(), 2131165867);
            case TTSConst.TTSEVT_TEXT /*5*/:
                return ResourceManager.getCoreString(getContext(), 2131165849);
            default:
                return null;
        }
    }

    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            persistInt(this.mKeyFirst, this.mSeekBarFirst.getProgress());
            persistInt(this.mKeySecond, this.mSeekBarSecond.getProgress());
            persistInt(this.mKeyThird, this.mSeekBarThird.getProgress());
        }
        setPrefSummary();
    }

    @TargetApi(9)
    private void tryCommit(Editor editor) throws Exception {
        Method m = PreferenceManager.class.getDeclaredMethod("shouldCommit", new Class[0]);
        m.setAccessible(true);
        if (((Boolean) m.invoke(getPreferenceManager(), new Object[0])).booleanValue()) {
            try {
                editor.apply();
            } catch (AbstractMethodError e) {
                editor.commit();
            }
        }
    }

    public void style(View view) {
        ((TextView) view.findViewById(16908310)).setTextColor(getContext().getResources().getColorStateList(2131558824));
    }
}
