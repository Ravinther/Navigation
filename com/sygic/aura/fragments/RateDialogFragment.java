package com.sygic.aura.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import com.sygic.aura.views.RatingStarsView;

public class RateDialogFragment extends AbstractDialogFragment {

    /* renamed from: com.sygic.aura.fragments.RateDialogFragment.1 */
    class C12521 implements OnClickListener {
        C12521() {
        }

        public void onClick(View view) {
            RateDialogFragment.this.setupTeaser(true, false);
            RateDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.fragments.RateDialogFragment.2 */
    class C12532 implements OnClickListener {
        C12532() {
        }

        public void onClick(View view) {
            SettingsManager.rateApp(RateDialogFragment.this.getActivity());
            RateDialogFragment.this.setupTeaser(true, false);
            RateDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.fragments.RateDialogFragment.3 */
    class C12543 implements OnClickListener {
        C12543() {
        }

        public void onClick(View view) {
            SendFeedbackDialogFragment.newInstance().show(RateDialogFragment.this.getFragmentManager(), "SendFeedbackDialogFragment");
            RateDialogFragment.this.setupTeaser(false, false);
            RateDialogFragment.this.dismiss();
        }
    }

    public static RateDialogFragment newInstance() {
        RateDialogFragment df = new RateDialogFragment();
        df.setStyle(1, 0);
        return df;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(2130903102, container, false);
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        handleNotification();
        setupTeaser(true, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RatingStarsView starsView = (RatingStarsView) view.findViewById(2131624189);
        starsView.setFilledCount(5);
        starsView.setEnabled(false);
        ((ImageButton) view.findViewById(2131624188)).setOnClickListener(new C12521());
        ((FrameLayout) view.findViewById(2131624194)).setOnClickListener(new C12532());
        ((Button) view.findViewById(2131624195)).setOnClickListener(new C12543());
    }

    private void setupTeaser(boolean bHide, boolean bSession) {
        SettingsManager.nativeSetSettings(ESettingsType.eHideTeaser, bHide);
        SettingsManager.nativeSetSettings(ESettingsType.eSessionTeaser, bSession);
        SettingsManager.nativeFlushSettings();
    }

    private void handleNotification() {
        Context context = getActivity();
        ((AlarmManager) context.getSystemService("alarm")).set(0, System.currentTimeMillis() + 7200000, PendingIntent.getBroadcast(context, 0, new Intent("com.sygic.aura.ACTION_RATE_NOTIFICATION"), 134217728));
    }
}
