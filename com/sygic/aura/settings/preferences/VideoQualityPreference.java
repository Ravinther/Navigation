package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.media.CamcorderProfile;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import com.sygic.aura.blackbox.fragment.BlackBoxFragment;
import com.sygic.aura.utils.Pair;
import java.util.ArrayList;
import java.util.List;

public class VideoQualityPreference extends SummaryListPreference {
    public VideoQualityPreference(Context context) {
        super(context);
        init();
    }

    public VideoQualityPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        List<String> entries = new ArrayList();
        List<String> values = new ArrayList();
        for (Pair<Integer, String> quality : getQualitySettings()) {
            if (CamcorderProfile.hasProfile(((Integer) quality.getFirst()).intValue())) {
                entries.add(getProfileDescription((String) quality.getSecond(), CamcorderProfile.get(((Integer) quality.getFirst()).intValue())));
                values.add(String.valueOf(quality.getFirst()));
            }
        }
        setEntries((CharSequence[]) entries.toArray(new String[entries.size()]));
        setEntryValues((CharSequence[]) values.toArray(new String[values.size()]));
        if (getValue() == null) {
            setValue(String.valueOf(BlackBoxFragment.getQuality(BlackBoxFragment.getCameraId(), -1)));
        }
    }

    private List<Pair<Integer, String>> getQualitySettings() {
        List<Pair<Integer, String>> result = new ArrayList();
        result.add(new Pair(Integer.valueOf(2), "QCIF"));
        if (VERSION.SDK_INT >= 15) {
            result.add(new Pair(Integer.valueOf(7), "QVGA"));
        }
        result.add(new Pair(Integer.valueOf(4), "SD"));
        result.add(new Pair(Integer.valueOf(5), "HD"));
        result.add(new Pair(Integer.valueOf(6), "Full HD"));
        if (VERSION.SDK_INT >= 21) {
            result.add(new Pair(Integer.valueOf(8), "UltraHD"));
        }
        return result;
    }

    protected boolean persistString(String value) {
        return value != null && persistInt(Integer.valueOf(value).intValue());
    }

    protected String getPersistedString(String defaultReturnValue) {
        if (getSharedPreferences().contains(getKey())) {
            return String.valueOf(getPersistedInt(0));
        }
        return defaultReturnValue;
    }

    private String getProfileDescription(String name, CamcorderProfile profile) {
        return String.format("%s (%dx%d)", new Object[]{name, Integer.valueOf(profile.videoFrameWidth), Integer.valueOf(profile.videoFrameHeight)});
    }
}
