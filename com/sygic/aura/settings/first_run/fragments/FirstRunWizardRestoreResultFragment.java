package com.sygic.aura.settings.first_run.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sygic.aura.SygicMain;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.RestoreFragmentResultCallback;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.views.font_specials.STextView;
import loquendo.tts.engine.TTSConst;

public class FirstRunWizardRestoreResultFragment extends AbstractScreenFragment implements BackPressedListener {
    private String mMessage;
    private STextView mMessageView;
    private int mType;

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardRestoreResultFragment.1 */
    class C16391 implements OnClickListener {
        C16391() {
        }

        public void onClick(View view) {
            FirstRunWizardRestoreResultFragment.this.finish();
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardRestoreResultFragment.2 */
    class C16402 implements OnClickListener {
        C16402() {
        }

        public void onClick(View view) {
            SygicMain.getInstance().getFeature().getSystemFeature().browserOpenUri("http://help.sygic.com/", null, null);
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardRestoreResultFragment.3 */
    class C16413 implements OnClickListener {
        C16413() {
        }

        public void onClick(View view) {
            FirstRunWizardRestoreResultFragment.this.performHomeAction();
        }
    }

    public FirstRunWizardRestoreResultFragment() {
        setWantsNavigationData(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mType = bundle.getInt("type");
            this.mMessage = bundle.getString("message");
        }
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        switch (this.mType) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                view = inflater.inflate(2130903126, container, false);
                this.mMessageView = (STextView) view.findViewById(2131624288);
                if (!TextUtils.isEmpty(this.mMessage)) {
                    this.mMessageView.setText(this.mMessage);
                }
                view.findViewById(2131624203).setOnClickListener(new C16391());
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                view = inflater.inflate(2130903125, container, false);
                this.mMessageView = (STextView) view.findViewById(2131624285);
                if (!TextUtils.isEmpty(this.mMessage)) {
                    this.mMessageView.setText(ResourceManager.getCoreString(this.mMessage));
                    this.mMessageView.setVisibility(0);
                }
                view.findViewById(2131624287).setOnClickListener(new C16402());
                view.findViewById(2131624286).setOnClickListener(new C16413());
                break;
        }
        return view;
    }

    public void onDestroy() {
        super.onDestroy();
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
    }

    private void finish() {
        Fragments.clearBackStack(getActivity(), false);
        performHomeAction();
        if (this.mResultCallback != null) {
            ((RestoreFragmentResultCallback) this.mResultCallback).onRestoreFragmentFinished();
        }
    }

    public boolean onBackPressed() {
        if (this.mType != 0) {
            return false;
        }
        Fragments.clearBackStack(getActivity(), false);
        return true;
    }
}
