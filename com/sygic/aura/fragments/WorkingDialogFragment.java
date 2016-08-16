package com.sygic.aura.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.resources.ResourceManager;

public class WorkingDialogFragment extends DialogFragment {
    private DialogFragmentClickListener mCallback;
    private TextView mMessage;
    protected View mPositiveButtonParent;
    protected View mProgress;

    /* renamed from: com.sygic.aura.fragments.WorkingDialogFragment.1 */
    class C12631 implements OnClickListener {
        C12631() {
        }

        public void onClick(DialogInterface dialogInterface, int which) {
            dialogInterface.dismiss();
            if (WorkingDialogFragment.this.mCallback != null && ((Boolean) WorkingDialogFragment.this.mPositiveButtonParent.getTag()).booleanValue()) {
                WorkingDialogFragment.this.mCallback.onPositiveButtonClicked(null);
            }
        }
    }

    public static WorkingDialogFragment newInstance(DialogFragmentClickListener callback) {
        WorkingDialogFragment f = new WorkingDialogFragment();
        f.setCallback(callback);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(2130903250, null);
        this.mProgress = view.findViewById(2131624110);
        this.mMessage = (TextView) view.findViewById(2131624542);
        builder.setView(view);
        builder.setPositiveButton(ResourceManager.getCoreString(getActivity(), 2131165355), new C12631());
        return builder.create();
    }

    private void setCallback(DialogFragmentClickListener callback) {
        this.mCallback = callback;
    }

    public void onStart() {
        super.onStart();
        this.mPositiveButtonParent = (View) getDialog().getWindow().findViewById(16908313).getParent().getParent();
        this.mPositiveButtonParent.setVisibility(8);
    }

    public void setMessage(boolean success, String strText) {
        this.mPositiveButtonParent.setVisibility(0);
        this.mPositiveButtonParent.setTag(Boolean.valueOf(success));
        this.mProgress.setVisibility(4);
        this.mMessage.setText(strText);
    }
}
