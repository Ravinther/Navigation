package com.sygic.aura.downloader;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sygic.aura.SygicMain;
import com.sygic.base.C1799R;

public class ProgressBarDialog {
    private Activity mActivity;
    private Button mBtnCancel;
    public Dialog mDialog;
    public ProgressBar mPbDialog;
    public TextView mTvPercent;
    public TextView mTvProgress;
    private boolean mbShown;

    /* renamed from: com.sygic.aura.downloader.ProgressBarDialog.1 */
    class C11911 implements OnCancelListener {
        C11911() {
        }

        public void onCancel(DialogInterface dialog) {
            ProgressBarDialog.this.onCancelProgressDialog();
        }
    }

    /* renamed from: com.sygic.aura.downloader.ProgressBarDialog.2 */
    class C11922 implements OnClickListener {
        C11922() {
        }

        public void onClick(View v) {
            ProgressBarDialog.this.onCancelProgressDialog();
        }
    }

    private ProgressBarDialog() {
        this.mbShown = false;
    }

    public ProgressBarDialog(Activity activity) {
        this.mbShown = false;
        this.mActivity = activity;
    }

    public void createProgressDialog() {
        this.mDialog = new Dialog(this.mActivity);
        this.mDialog.setContentView(C1799R.layout.progressbar_dialog);
        LayoutParams params = this.mDialog.getWindow().getAttributes();
        params.width = -1;
        this.mDialog.getWindow().setAttributes(params);
        this.mTvPercent = (TextView) this.mDialog.findViewById(C1799R.id.tv_percent);
        this.mTvProgress = (TextView) this.mDialog.findViewById(C1799R.id.tv_progress);
        this.mPbDialog = (ProgressBar) this.mDialog.findViewById(C1799R.id.pb_downloader);
        this.mDialog.setTitle(C1799R.string.app_name);
        this.mDialog.setCancelable(false);
        this.mDialog.setOnCancelListener(new C11911());
        this.mBtnCancel = (Button) this.mDialog.findViewById(C1799R.id.btn_cancel);
        this.mBtnCancel.setOnClickListener(new C11922());
    }

    public void updateProgressBar(String title, int progressPercent, String progressRatio) {
        if (!this.mbShown) {
            this.mDialog.show();
            this.mbShown = true;
        }
        this.mDialog.setTitle(title);
        this.mTvPercent.setText(String.format("%d%%", new Object[]{Integer.valueOf(progressPercent)}));
        this.mTvProgress.setText(progressRatio);
        this.mPbDialog.setProgress(progressPercent);
    }

    public void dismiss() {
        this.mDialog.dismiss();
        this.mbShown = false;
    }

    private void onCancelProgressDialog() {
        try {
            SygicMain.getInstance().getFeature().getSystemFeature().logEvent("frw->cancel", null, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismiss();
        this.mActivity.finish();
    }

    public void disableCancelButton() {
        if (this.mBtnCancel != null) {
            this.mBtnCancel.setVisibility(8);
        }
    }
}
