package com.sygic.aura.route;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.map.PositionInfo;

public class NoGpsSignalDialogFragment extends DialogFragment {
    private NoGpsDialogListener mNoGpsDialogListener;

    /* renamed from: com.sygic.aura.route.NoGpsSignalDialogFragment.1 */
    class C14871 implements OnClickListener {
        C14871() {
        }

        public void onClick(View v) {
            NoGpsSignalDialogFragment.this.mNoGpsDialogListener.onButtonClick(NoGpsDialogOutput.BUTTON_FROM_MAP);
            NoGpsSignalDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.route.NoGpsSignalDialogFragment.2 */
    class C14882 implements OnClickListener {
        C14882() {
        }

        public void onClick(View v) {
            NoGpsSignalDialogFragment.this.mNoGpsDialogListener.onButtonClick(NoGpsDialogOutput.BUTTON_LAST_VALID);
            NoGpsSignalDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.route.NoGpsSignalDialogFragment.3 */
    class C14893 implements OnClickListener {
        C14893() {
        }

        public void onClick(View v) {
            NoGpsSignalDialogFragment.this.mNoGpsDialogListener.onButtonClick(NoGpsDialogOutput.BUTTON_CHANGE_START);
            NoGpsSignalDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.route.NoGpsSignalDialogFragment.4 */
    class C14904 implements DialogInterface.OnClickListener {
        C14904() {
        }

        public void onClick(DialogInterface dialog, int which) {
            NoGpsSignalDialogFragment.this.mNoGpsDialogListener.onButtonClick(NoGpsDialogOutput.BUTTON_CANCEL);
        }
    }

    public interface NoGpsDialogListener {
        void onButtonClick(NoGpsDialogOutput noGpsDialogOutput);
    }

    public enum NoGpsDialogOutput {
        BUTTON_LAST_VALID,
        BUTTON_FROM_MAP,
        BUTTON_CHANGE_START,
        BUTTON_CANCEL
    }

    public static NoGpsSignalDialogFragment getInstance(NoGpsDialogListener noGpsDialogListener) {
        NoGpsSignalDialogFragment fragment = new NoGpsSignalDialogFragment();
        fragment.mNoGpsDialogListener = noGpsDialogListener;
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        WndEventsReceiver.registerAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View title = inflater.inflate(2130903240, null);
        startIconAnimation(title);
        builder.setCustomTitle(title);
        View content = inflater.inflate(2130903239, null);
        content.findViewById(2131624523).setOnClickListener(new C14871());
        View routeFromLast = content.findViewById(2131624524);
        if (PositionInfo.nativeHasLastValidPosition()) {
            ((TextView) content.findViewById(2131624526)).setText(PositionInfo.nativeGetPositionString(inflater.getContext(), PositionInfo.nativeGetLastValidPosition()));
            routeFromLast.setOnClickListener(new C14882());
        } else {
            routeFromLast.setVisibility(8);
        }
        content.findViewById(2131624527).setOnClickListener(new C14893());
        builder.setView(content);
        builder.setNegativeButton(17039360, new C14904());
        return builder.create();
    }

    private void startIconAnimation(View title) {
        if (title != null) {
            ImageView imageView = (ImageView) title.findViewById(2131624528);
            if (imageView != null) {
                Drawable imageDrawable = imageView.getBackground();
                if (imageDrawable != null && (imageDrawable instanceof AnimationDrawable)) {
                    ((AnimationDrawable) imageDrawable).start();
                }
            }
        }
    }

    public void onDestroy() {
        WndEventsReceiver.unregisterAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        this.mNoGpsDialogListener = null;
        super.onDestroy();
    }

    public void onCancel(DialogInterface dialog) {
        this.mNoGpsDialogListener.onButtonClick(NoGpsDialogOutput.BUTTON_CANCEL);
        super.onCancel(dialog);
    }

    public void show(FragmentManager manager, String tag) {
        if (manager != null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        }
    }
}
