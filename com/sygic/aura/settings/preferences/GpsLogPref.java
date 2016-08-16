package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.sygic.aura.C1090R;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.route.RouteManager;
import com.sygic.base.C1799R;
import loquendo.tts.engine.TTSConst;

public class GpsLogPref extends CoreEditTextPreference {
    private GpsType mType;

    /* renamed from: com.sygic.aura.settings.preferences.GpsLogPref.1 */
    class C16671 implements OnClickListener {
        C16671() {
        }

        public void onClick(View view) {
            GpsLogPref.this.onClick();
        }
    }

    /* renamed from: com.sygic.aura.settings.preferences.GpsLogPref.2 */
    static /* synthetic */ class C16682 {
        static final /* synthetic */ int[] f1287x5cb4e5b5;

        static {
            f1287x5cb4e5b5 = new int[GpsType.values().length];
            try {
                f1287x5cb4e5b5[GpsType.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1287x5cb4e5b5[GpsType.RECORD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private enum GpsType {
        PLAY(0),
        RECORD(1);
        
        int mValue;

        private GpsType(int val) {
            this.mValue = val;
        }

        public static GpsType fromInteger(int x) {
            switch (x) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    return PLAY;
                case TTSConst.TTSMULTILINE /*1*/:
                    return RECORD;
                default:
                    Log.d("GpsLogPref", "Invalid enum value");
                    return PLAY;
            }
        }
    }

    public GpsLogPref(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public GpsLogPref(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.GpsInputPreference, 0, 0);
        this.mType = GpsType.fromInteger(a.getInt(0, -1));
        a.recycle();
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        Button button = (Button) view.findViewById(C1799R.id.button);
        button.setText(getTitle());
        button.setOnClickListener(new C16671());
    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            String strFileName = getEditText().getText().toString();
            switch (C16682.f1287x5cb4e5b5[this.mType.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    playLog(strFileName);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    recordLog(strFileName);
                    break;
                default:
                    Log.d("GpsLogPref", "Invalid operation");
                    break;
            }
        }
        super.onClick(dialog, which);
    }

    private void playLog(String strFileName) {
        if (MapControlsManager.nativeEmulatorStart(strFileName)) {
            MapOverlayFragment.setMode(getContext(), RouteManager.nativeExistValidRoute() ? Mode.NAVIGATE_INFO_BAR : Mode.FREEDRIVE_INFO_BAR);
        } else {
            SToast.makeText(getContext(), 2131165773, 1).show();
        }
    }

    private void recordLog(String strFileName) {
        MapControlsManager.nativeEmulatorRecord(strFileName);
    }
}
