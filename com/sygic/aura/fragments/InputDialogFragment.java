package com.sygic.aura.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.view.ExtendedEditText;
import java.util.HashSet;
import java.util.Set;

public class InputDialogFragment extends DialogFragment {
    protected DialogFragmentClickListener mCallback;
    protected OnCancelListener mCancelCallback;
    protected ExtendedEditText mEditText;
    protected final Set<InputFilter> mFilters;
    protected int mHintId;
    protected InputMethodManager mInputManager;
    protected int mInputType;
    protected TextWatcher mWatcher;

    public interface DialogFragmentClickListener {
        void onPositiveButtonClicked(Editable editable);
    }

    /* renamed from: com.sygic.aura.fragments.InputDialogFragment.1 */
    class C12471 implements OnClickListener {
        C12471() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            InputDialogFragment.this.mInputManager.hideSoftInputFromWindow(InputDialogFragment.this.mEditText.getWindowToken(), 0);
            if (InputDialogFragment.this.mCallback != null) {
                InputDialogFragment.this.mCallback.onPositiveButtonClicked(InputDialogFragment.this.mEditText.getText());
            }
        }
    }

    /* renamed from: com.sygic.aura.fragments.InputDialogFragment.2 */
    class C12482 implements OnClickListener {
        C12482() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (InputDialogFragment.this.mCancelCallback != null) {
                InputDialogFragment.this.mCancelCallback.onCancel(dialogInterface);
            }
            InputDialogFragment.this.mInputManager.hideSoftInputFromWindow(InputDialogFragment.this.mEditText.getWindowToken(), 0);
            dialogInterface.dismiss();
        }
    }

    public InputDialogFragment() {
        this.mHintId = 0;
        this.mInputType = -1;
        this.mFilters = new HashSet();
        this.mWatcher = null;
    }

    public static InputDialogFragment newInstance(Context context, int titleResId, String preselectedName, DialogFragmentClickListener callback) {
        return newInstance(context, titleResId, preselectedName, null, callback);
    }

    public static InputDialogFragment newInstance(Context context, int titleResId, String preselectedName, String hintText, DialogFragmentClickListener callback) {
        Bundle args = new Bundle();
        args.putString("InputText", preselectedName);
        args.putString("HintText", hintText);
        String str = "DialogTitle";
        if (titleResId <= 0) {
            titleResId = 2131165491;
        }
        args.putString(str, ResourceManager.getCoreString(context, titleResId));
        InputDialogFragment f = new InputDialogFragment();
        f.setArguments(args);
        f.setCallback(callback);
        return f;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.mInputManager = (InputMethodManager) getActivity().getSystemService("input_method");
        Builder builder = new Builder(getActivity());
        builder.setTitle(getArguments().getString("DialogTitle")).setNegativeButton(ResourceManager.getCoreString(getActivity(), 2131165351), new C12482()).setPositiveButton(ResourceManager.getCoreString(getActivity(), 2131165355), new C12471());
        if (this.mCancelCallback != null) {
            builder.setCancelable(true);
            builder.setOnCancelListener(this.mCancelCallback);
        }
        this.mEditText = new ExtendedEditText(getActivity());
        this.mEditText.setImeOptions(268435456);
        if (this.mInputType >= 0) {
            this.mEditText.setInputType(this.mInputType);
        }
        String initText = getArguments().getString("InputText");
        if (!TextUtils.isEmpty(initText)) {
            this.mEditText.setText(initText);
            this.mEditText.setSelection(initText.length());
        }
        String hintText = getArguments().getString("HintText");
        if (!TextUtils.isEmpty(hintText)) {
            this.mEditText.setHint(hintText);
        }
        if (this.mHintId > 0) {
            this.mEditText.setHint(this.mHintId);
        }
        if (!this.mFilters.isEmpty()) {
            this.mEditText.setFilters((InputFilter[]) this.mFilters.toArray(new InputFilter[this.mFilters.size()]));
        }
        if (this.mWatcher != null) {
            this.mEditText.addTextChangedListener(this.mWatcher);
        }
        Dialog dialog = builder.setView(this.mEditText).create();
        dialog.getWindow().setSoftInputMode(4);
        return dialog;
    }

    public void setCallback(DialogFragmentClickListener callback) {
        this.mCallback = callback;
    }

    public InputDialogFragment setCancelCallback(OnCancelListener cancelCallback) {
        this.mCancelCallback = cancelCallback;
        return this;
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (this.mCancelCallback != null) {
            this.mCancelCallback.onCancel(dialog);
        }
    }

    public void showDialog() {
        show(SygicHelper.getFragmentManager().beginTransaction(), "input_dialog");
    }

    public void setText(CharSequence text) {
        if (this.mEditText != null) {
            this.mEditText.setText(text);
        }
    }

    public void addTextChangedListener(TextWatcher watcher) {
        this.mWatcher = watcher;
        if (this.mEditText != null) {
            this.mEditText.addTextChangedListener(watcher);
        }
    }

    public boolean setButtonEnabled(int which, boolean enabled) {
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog == null || dialog.getButton(which) == null) {
            return false;
        }
        dialog.getButton(which).setEnabled(enabled);
        return true;
    }

    public void addFilter(InputFilter filter) {
        this.mFilters.add(filter);
    }

    public void addFilter(InputFilter filter, int inputType) {
        addFilter(filter);
        this.mInputType = inputType;
    }
}
