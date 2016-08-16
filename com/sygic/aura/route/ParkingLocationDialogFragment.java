package com.sygic.aura.route;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.resources.ResourceManager;

public class ParkingLocationDialogFragment extends DialogFragment {
    private ParkingDialogListener mParkingDialogListener;

    public interface ParkingDialogListener {
        void onNavigateToCar();

        void onSaveLocation();
    }

    /* renamed from: com.sygic.aura.route.ParkingLocationDialogFragment.1 */
    class C14911 implements OnClickListener {
        C14911() {
        }

        public void onClick(View v) {
            ParkingLocationDialogFragment.this.mParkingDialogListener.onSaveLocation();
            ParkingLocationDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.route.ParkingLocationDialogFragment.2 */
    class C14922 implements OnClickListener {
        C14922() {
        }

        public void onClick(View v) {
            ParkingLocationDialogFragment.this.mParkingDialogListener.onNavigateToCar();
            ParkingLocationDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.route.ParkingLocationDialogFragment.3 */
    class C14933 implements DialogInterface.OnClickListener {
        C14933() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ParkingLocationDialogFragment.this.dismiss();
        }
    }

    public static ParkingLocationDialogFragment getInstance(ParkingDialogListener parkingDialogListener) {
        ParkingLocationDialogFragment fragment = new ParkingLocationDialogFragment();
        fragment.mParkingDialogListener = parkingDialogListener;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setCustomTitle(inflater.inflate(2130903252, null));
        View content = inflater.inflate(2130903251, null);
        content.findViewById(2131624543).setOnClickListener(new C14911());
        View navigateToCar = content.findViewById(2131624544);
        MemoItem parkingMemo = MemoManager.nativeGetParking();
        if (parkingMemo != null) {
            String noteText = parkingMemo.getStrData();
            String addressText = parkingMemo.getStrOrigText();
            String note = ResourceManager.getCoreString(getActivity(), 2131165517).toUpperCase() + ": ";
            TextView textView = (TextView) content.findViewById(2131624545);
            StringBuilder append = new StringBuilder().append(note);
            if (!TextUtils.isEmpty(noteText)) {
                addressText = noteText;
            }
            textView.setText(append.append(addressText).toString());
            navigateToCar.setOnClickListener(new C14922());
        } else {
            navigateToCar.setVisibility(8);
        }
        builder.setView(content).setNegativeButton(17039360, new C14933());
        return builder.create();
    }

    public void show(FragmentManager manager, String tag) {
        if (manager != null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        }
    }
}
