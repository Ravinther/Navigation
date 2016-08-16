package com.sygic.aura.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.views.font_specials.SToolbar;
import loquendo.tts.engine.TTSConst;

public class WebViewFragment extends AbstractScreenFragment {
    private WebviewFragmentDelegate mDelegate;

    public enum Mode implements Parcelable {
        DEFAULT,
        ESHOP,
        PROMO,
        PANORAMA;
        
        public static final Creator<Mode> CREATOR;

        /* renamed from: com.sygic.aura.fragments.WebViewFragment.Mode.1 */
        static class C12581 implements Creator<Mode> {
            C12581() {
            }

            public Mode createFromParcel(Parcel source) {
                return Mode.values()[source.readInt()];
            }

            public Mode[] newArray(int size) {
                return new Mode[size];
            }
        }

        static {
            CREATOR = new C12581();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }
    }

    public WebViewFragment() {
        setWantsNavigationData(false);
    }

    public static Mode tagToMode(String tag) {
        Object obj = -1;
        switch (tag.hashCode()) {
            case 96805083:
                if (tag.equals("eshop")) {
                    obj = null;
                    break;
                }
                break;
            case 106940687:
                if (tag.equals("promo")) {
                    obj = 1;
                    break;
                }
                break;
            case 1069983349:
                if (tag.equals("panorama")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return Mode.ESHOP;
            case TTSConst.TTSMULTILINE /*1*/:
                return Mode.PROMO;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return Mode.PANORAMA;
            default:
                return Mode.DEFAULT;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDelegate.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mDelegate.onDestroy();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mDelegate = WebviewFragmentDelegate.fromMode(resolveMode(), this);
        this.mDelegate.onAttach(activity);
    }

    private Mode resolveMode() {
        Bundle args = getArguments();
        if (args == null) {
            return Mode.DEFAULT;
        }
        Mode mode = (Mode) args.getParcelable("mode");
        if (mode == null) {
            return Mode.DEFAULT;
        }
        return mode;
    }

    public void onDestroyView() {
        this.mDelegate.onDestroyView();
        super.onDestroyView();
    }

    public void onDetach() {
        super.onDetach();
        this.mDelegate.onDetach();
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        this.mDelegate.onSetupToolbar(toolbar);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.mDelegate.onCreateView(inflater, container);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mDelegate.onViewCreated(view);
    }
}
