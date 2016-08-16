package com.sygic.aura.search.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.views.font_specials.SEditText;
import com.sygic.aura.views.font_specials.STextView;
import loquendo.tts.engine.TTSConst;

public class GpsInputFragment extends AbstractScreenFragment {
    private EditText mLat;
    private EditText mLong;
    private PageType mType;

    /* renamed from: com.sygic.aura.search.model.GpsInputFragment.1 */
    static /* synthetic */ class C15711 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$search$model$GpsInputFragment$PageType;

        static {
            $SwitchMap$com$sygic$aura$search$model$GpsInputFragment$PageType = new int[PageType.values().length];
            try {
                $SwitchMap$com$sygic$aura$search$model$GpsInputFragment$PageType[PageType.DEGREES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$search$model$GpsInputFragment$PageType[PageType.MINUTES.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$search$model$GpsInputFragment$PageType[PageType.SECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private enum PageType {
        DEGREES,
        MINUTES,
        SECONDS
    }

    public static GpsInputFragment newInstance(int type) {
        GpsInputFragment fragment = new GpsInputFragment();
        Bundle args = new Bundle();
        args.putInt("TYPE", type);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903142, container, false);
        switch (getArguments().getInt("TYPE")) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mType = PageType.DEGREES;
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                this.mType = PageType.MINUTES;
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mType = PageType.SECONDS;
                break;
            default:
                throw new RuntimeException("Index out of range");
        }
        ((STextView) view.findViewById(2131624352)).setCoreText(getTitleText());
        this.mLat = (SEditText) view.findViewById(2131624353);
        this.mLat.setHint(getHintText());
        this.mLong = (SEditText) view.findViewById(2131624354);
        this.mLong.setHint(getHintText());
        return view;
    }

    public String getLatitude() {
        if (this.mLat != null) {
            return this.mLat.getText().toString();
        }
        return null;
    }

    public String getLongitude() {
        if (this.mLong != null) {
            return this.mLong.getText().toString();
        }
        return null;
    }

    private int getTitleText() {
        switch (C15711.$SwitchMap$com$sygic$aura$search$model$GpsInputFragment$PageType[this.mType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131165663;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131165668;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131165670;
            default:
                throw new RuntimeException("Invalid item type");
        }
    }

    private String getHintText() {
        switch (C15711.$SwitchMap$com$sygic$aura$search$model$GpsInputFragment$PageType[this.mType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return "000.00000\u00b0";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "000\u00b000.000'";
            case TTSConst.TTSUNICODE /*3*/:
                return "000\u00b000'00.0\"";
            default:
                throw new RuntimeException("Invalid item type");
        }
    }
}
