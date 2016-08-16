package com.sygic.aura.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.resources.ResourceManager;

public class CustomDialogFragment extends DialogFragment {
    protected ListAdapter mAdapter;
    private int mCheckedIndex;
    private boolean mNavigationChanged;
    protected OnClickListener mOnAdapterClickListener;
    protected OnClickListener mOnNegativeBtnClickListener;
    protected OnClickListener mOnNeutralBtnClickListener;
    protected OnClickListener mOnPositiveBtnClickListener;
    protected View mView;

    /* renamed from: com.sygic.aura.helper.CustomDialogFragment.1 */
    static class C12641 implements OnClickListener {
        final /* synthetic */ Activity val$context;

        C12641(Activity activity) {
            this.val$context = activity;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.val$context.finish();
        }
    }

    public static class Builder {
        private ListAdapter mAdapter;
        private String mBody;
        private int mCheckedIndex;
        private final Context mContext;
        private OnClickListener mItemClickListener;
        private OnClickListener mNegativeButtonListener;
        private String mNegativeButtonText;
        private OnClickListener mNeutralButtonListener;
        private String mNeutralButtonText;
        private OnClickListener mPositiveButtonListener;
        private String mPositiveButtonText;
        private String mTitle;
        private View mView;

        public Builder(Context context) {
            this.mCheckedIndex = -1;
            this.mContext = context;
        }

        public Builder title(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder title(int titleRes) {
            return title(ResourceManager.getCoreString(this.mContext, titleRes));
        }

        public Builder body(String body) {
            this.mBody = body;
            return this;
        }

        public Builder body(int bodyRes) {
            return body(ResourceManager.getCoreString(this.mContext, bodyRes));
        }

        public Builder formattedBody(String format, Object... args) {
            this.mBody = String.format(format, args);
            return this;
        }

        public Builder formattedBody(int formatRes, Object... args) {
            return formattedBody(ResourceManager.getCoreString(this.mContext, formatRes), args);
        }

        public Builder view(View view) {
            this.mView = view;
            return this;
        }

        public Builder positiveButton(String positiveButtonText, OnClickListener positiveClickListener) {
            this.mPositiveButtonText = positiveButtonText;
            this.mPositiveButtonListener = positiveClickListener;
            return this;
        }

        public Builder positiveButton(int positiveButtonTextRes, OnClickListener positiveClickListener) {
            return positiveButton(ResourceManager.getCoreString(this.mContext, positiveButtonTextRes), positiveClickListener);
        }

        public Builder negativeButton(String negativeButtonText, OnClickListener negativeClickListener) {
            this.mNegativeButtonText = negativeButtonText;
            this.mNegativeButtonListener = negativeClickListener;
            return this;
        }

        public Builder negativeButton(int negativeButtonTextRes, OnClickListener negativeClickListener) {
            return negativeButton(ResourceManager.getCoreString(this.mContext, negativeButtonTextRes), negativeClickListener);
        }

        public Builder listAdapter(ListAdapter adapter, OnClickListener itemClickListener) {
            this.mAdapter = adapter;
            this.mItemClickListener = itemClickListener;
            return this;
        }

        public Builder checkedListAdapter(ListAdapter adapter, int checkedIndex, OnClickListener itemClickListener) {
            this.mCheckedIndex = checkedIndex;
            return listAdapter(adapter, itemClickListener);
        }

        public <T> Builder simpleListAdapter(T[] items, OnClickListener itemClickListener) {
            return listAdapter(new ArrayAdapter(this.mContext, 17367043, items), itemClickListener);
        }

        public <T> Builder simpleCheckedAdapter(T[] items, int checkedIndex, OnClickListener itemClickListener) {
            return checkedListAdapter(new ArrayAdapter(this.mContext, 17367055, items), checkedIndex, itemClickListener);
        }

        public CustomDialogFragment build() {
            CustomDialogFragment df = CustomDialogFragment.newInstance(this.mTitle, this.mBody, this.mNegativeButtonText, this.mPositiveButtonText, this.mNeutralButtonText);
            df.setOnPositiveBtnClick(this.mPositiveButtonListener);
            df.setOnNegativeBtnClick(this.mNegativeButtonListener);
            df.setOnNeutralBtnClick(this.mNeutralButtonListener);
            df.setAdapter(this.mAdapter, this.mItemClickListener);
            df.setCheckedAdapter(this.mAdapter, this.mCheckedIndex, this.mItemClickListener);
            df.setView(this.mView);
            return df;
        }
    }

    public CustomDialogFragment() {
        this.mNavigationChanged = false;
        this.mCheckedIndex = -1;
    }

    protected static CustomDialogFragment newInstance(String title, String msgBody, String negativeButtonText, String positiveButtonText, String neutralButtonText) {
        CustomDialogFragment frag = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString(AbstractFragment.ARG_TITLE, title);
        args.putString("msg", msgBody);
        args.putString("btn_negative", negativeButtonText);
        args.putString("btn_positive", positiveButtonText);
        args.putString("btn_neutral", neutralButtonText);
        frag.setArguments(args);
        return frag;
    }

    public static void showExitDialog(Activity context) {
        new Builder(context).body(2131165399).negativeButton(2131165354, null).positiveButton(2131165364, new C12641(context)).build().showAllowingStateLoss("exit_dialog_fragment");
    }

    public void showAllowingStateLoss(String tag) {
        FragmentTransaction ft = SygicHelper.getFragmentManager().beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (NaviNativeActivity.isNavigationBarHidden(getActivity())) {
            this.mNavigationChanged = true;
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        if (getArguments().getString(AbstractFragment.ARG_TITLE) != null) {
            builder.setTitle(getArguments().getString(AbstractFragment.ARG_TITLE));
        }
        if (getArguments().getString("msg") != null) {
            builder.setMessage(getArguments().getString("msg"));
        }
        String buttonTitle = getArguments().getString("btn_negative");
        if (buttonTitle != null) {
            builder.setNegativeButton(buttonTitle, this.mOnNegativeBtnClickListener);
        }
        if (this.mView != null) {
            builder.setView(this.mView);
        }
        buttonTitle = getArguments().getString("btn_positive");
        if (buttonTitle != null) {
            builder.setPositiveButton(buttonTitle, this.mOnPositiveBtnClickListener);
        }
        buttonTitle = getArguments().getString("btn_neutral");
        if (buttonTitle != null) {
            builder.setNeutralButton(buttonTitle, this.mOnNeutralBtnClickListener);
        }
        if (this.mCheckedIndex != -1 && this.mAdapter != null && this.mOnAdapterClickListener != null) {
            builder.setSingleChoiceItems(this.mAdapter, this.mCheckedIndex, this.mOnAdapterClickListener);
        } else if (!(this.mAdapter == null || this.mOnAdapterClickListener == null)) {
            builder.setAdapter(this.mAdapter, this.mOnAdapterClickListener);
        }
        WndEventsReceiver.registerAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        return builder.create();
    }

    public void onDestroyView() {
        WndEventsReceiver.unregisterAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        super.onDestroyView();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.mNavigationChanged) {
            NaviNativeActivity.hideNavigationBar(getActivity());
        }
    }

    protected void setOnNegativeBtnClick(OnClickListener onNegativeBtnClickListener) {
        this.mOnNegativeBtnClickListener = onNegativeBtnClickListener;
    }

    protected void setOnPositiveBtnClick(OnClickListener onPositiveBtnClickListener) {
        this.mOnPositiveBtnClickListener = onPositiveBtnClickListener;
    }

    protected void setOnNeutralBtnClick(OnClickListener onNeutralBtnClickListener) {
        this.mOnNeutralBtnClickListener = onNeutralBtnClickListener;
    }

    protected void setAdapter(ListAdapter adapter, OnClickListener onAdapterClickListener) {
        this.mAdapter = adapter;
        this.mOnAdapterClickListener = onAdapterClickListener;
    }

    protected void setView(View view) {
        this.mView = view;
    }

    protected void setCheckedAdapter(ListAdapter adapter, int checkedIndex, OnClickListener onAdapterClickListener) {
        setAdapter(adapter, onAdapterClickListener);
        this.mCheckedIndex = checkedIndex;
    }
}
