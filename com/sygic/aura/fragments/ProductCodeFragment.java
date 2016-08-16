package com.sygic.aura.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.Button;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.resources.ResourceManager;

public class ProductCodeFragment extends InputDialogFragment {

    /* renamed from: com.sygic.aura.fragments.ProductCodeFragment.1 */
    class C12501 implements InputFilter {
        C12501() {
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (TextUtils.isEmpty(source)) {
                if (ProductCodeFragment.this.getDialog() != null) {
                    ProductCodeFragment.this.getPositiveButton().setEnabled(false);
                }
                return null;
            }
            int length = dest.length() + source.length();
            if (ProductCodeFragment.this.getDialog() != null) {
                boolean z;
                Button access$000 = ProductCodeFragment.this.getPositiveButton();
                if (length >= 19) {
                    z = true;
                } else {
                    z = false;
                }
                access$000.setEnabled(z);
            }
            if (length > 19) {
                return "";
            }
            return (length % 5 != 0 || source.charAt(0) == '-') ? null : "-" + source;
        }
    }

    /* renamed from: com.sygic.aura.fragments.ProductCodeFragment.2 */
    class C12512 implements OnShowListener {
        C12512() {
        }

        public void onShow(DialogInterface dialog) {
            ((AlertDialog) dialog).getButton(-1).setEnabled(false);
        }
    }

    public static ProductCodeFragment newInstance(Context context, int titleResId, String preselectedName, DialogFragmentClickListener callback) {
        Bundle args = new Bundle();
        args.putString("InputText", preselectedName);
        String str = "DialogTitle";
        if (titleResId <= 0) {
            titleResId = 2131165491;
        }
        args.putString(str, ResourceManager.getCoreString(context, titleResId));
        ProductCodeFragment f = new ProductCodeFragment();
        f.setArguments(args);
        f.setCallback(callback);
        f.addProductCodeFilter();
        return f;
    }

    private void addProductCodeFilter() {
        addFilter(new C12501(), 524432);
        this.mHintId = 2131166190;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog result = super.onCreateDialog(savedInstanceState);
        result.setOnShowListener(new C12512());
        return result;
    }

    private Button getPositiveButton() {
        return ((AlertDialog) getDialog()).getButton(-1);
    }
}
