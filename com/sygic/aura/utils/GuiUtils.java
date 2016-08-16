package com.sygic.aura.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.text.Html;
import com.sygic.aura.SygicMain;
import com.sygic.aura.c2dm.NotifImageDownloaderService;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.base.C1799R;

public class GuiUtils {

    private static class BuildLocalDialog implements Runnable {
        private final Activity mActivity;
        private final boolean mDontFinish;
        private AlertDialog mLocalDialog;
        private final String mStrText;
        private final boolean mToCreateNew;

        /* renamed from: com.sygic.aura.utils.GuiUtils.BuildLocalDialog.1 */
        class C17731 implements OnClickListener {
            C17731() {
            }

            public void onClick(DialogInterface dummy1, int dummy2) {
                if (!BuildLocalDialog.this.mDontFinish) {
                    BuildLocalDialog.this.mActivity.finish();
                }
            }
        }

        public BuildLocalDialog(Activity activity, String strText, boolean bDontFinish) {
            this.mActivity = activity;
            this.mStrText = strText;
            this.mDontFinish = bDontFinish;
            this.mToCreateNew = true;
        }

        public BuildLocalDialog(Activity activity, AlertDialog dialog) {
            this.mActivity = activity;
            this.mStrText = "";
            this.mDontFinish = true;
            this.mLocalDialog = dialog;
            this.mToCreateNew = false;
        }

        public AlertDialog getAlertDialog() {
            return this.mLocalDialog;
        }

        public void run() {
            if (!this.mActivity.isFinishing()) {
                if (this.mToCreateNew || this.mLocalDialog == null) {
                    this.mLocalDialog = new Builder(this.mActivity).setTitle(this.mActivity.getResources().getString(C1799R.string.app_name)).setMessage(this.mStrText).setPositiveButton(this.mActivity.getResources().getString(C1799R.string.button_cancel), new C17731()).create();
                }
                this.mLocalDialog.show();
            }
        }
    }

    public static class CustomDialogFragment extends DialogFragment {
        private OnCancelListener mOnCancelListener;
        private OnClickListener mOnNegativeBtnClickListener;
        private OnClickListener mOnPositiveBtnClickListener;

        /* renamed from: com.sygic.aura.utils.GuiUtils.CustomDialogFragment.1 */
        class C17741 implements Runnable {
            final /* synthetic */ FragmentActivity val$activity;
            final /* synthetic */ String val$tag;

            C17741(FragmentActivity fragmentActivity, String str) {
                this.val$activity = fragmentActivity;
                this.val$tag = str;
            }

            public void run() {
                try {
                    CustomDialogFragment.this.show(this.val$activity.getSupportFragmentManager().beginTransaction(), this.val$tag);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }

        public static CustomDialogFragment newInstance(Context context, int titleResId, int msgBodyResId, int negativeButtonResId, int positiveButtonResId) {
            return newInstance(context.getString(titleResId), context.getString(msgBodyResId), context.getString(negativeButtonResId), context.getString(positiveButtonResId));
        }

        public static CustomDialogFragment newInstance(Context context, String strMsgBody, String strButtonText) {
            return newInstance(context, strMsgBody, "", strButtonText);
        }

        public static CustomDialogFragment newInstance(Context context, String strMsgBody, String strNegativeButtonText, String strPositiveButtonText) {
            return newInstance(context.getString(C1799R.string.app_name), strMsgBody, strNegativeButtonText, strPositiveButtonText);
        }

        public static CustomDialogFragment newInstance(String strTitle, String strMsgBody, String strNegativeButtonText, String strPositiveButtonText) {
            CustomDialogFragment fragment = new CustomDialogFragment();
            Bundle args = new Bundle();
            args.putString(AbstractFragment.ARG_TITLE, strTitle);
            args.putString("msg", strMsgBody);
            args.putString("btn_negative", strNegativeButtonText);
            args.putString("btn_positive", strPositiveButtonText);
            fragment.setArguments(args);
            return fragment;
        }

        public void setOnNegativeBtnClick(OnClickListener onNegativeBtnClickListener) {
            this.mOnNegativeBtnClickListener = onNegativeBtnClickListener;
        }

        public void setOnPositiveBtnClick(OnClickListener onPositiveBtnClickListener) {
            this.mOnPositiveBtnClickListener = onPositiveBtnClickListener;
        }

        public void setOnCancelListener(OnCancelListener onCancelListener) {
            this.mOnCancelListener = onCancelListener;
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Builder builder = new Builder(getActivity()).setTitle(getArguments().getString(AbstractFragment.ARG_TITLE)).setMessage(getArguments().getString("msg"));
            if (this.mOnNegativeBtnClickListener != null) {
                builder.setNegativeButton(getArguments().getString("btn_negative"), this.mOnNegativeBtnClickListener);
            }
            if (this.mOnPositiveBtnClickListener != null) {
                builder.setPositiveButton(getArguments().getString("btn_positive"), this.mOnPositiveBtnClickListener);
            }
            return builder.create();
        }

        public void onCancel(DialogInterface dialog) {
            if (this.mOnCancelListener != null) {
                this.mOnCancelListener.onCancel(dialog);
            } else if (this.mOnNegativeBtnClickListener != null) {
                this.mOnNegativeBtnClickListener.onClick(dialog, 0);
            } else if (this.mOnPositiveBtnClickListener != null) {
                this.mOnPositiveBtnClickListener.onClick(dialog, 0);
            }
        }

        public void showDialog(String tag) {
            showDialog((FragmentActivity) SygicMain.getActivity(), tag);
        }

        public void showDialog(FragmentActivity activity, String tag) {
            activity.runOnUiThread(new C17741(activity, tag));
        }
    }

    @Deprecated
    public static AlertDialog showMessage(Activity activity, String strText) {
        return showMessage(activity, strText, false);
    }

    public static AlertDialog showMessage(Activity activity, String strText, boolean bDontFinish) {
        if (activity == null) {
            return null;
        }
        return showOnUi(activity, new BuildLocalDialog(activity, strText, bDontFinish));
    }

    public static AlertDialog showMessage(Activity activity, AlertDialog dialog) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }
        return showOnUi(activity, new BuildLocalDialog(activity, dialog));
    }

    private static AlertDialog showOnUi(Activity activity, BuildLocalDialog localDialog) {
        activity.runOnUiThread(localDialog);
        return localDialog.getAlertDialog();
    }

    public static void showStandartNotification(Context context, String strTitle, String strText) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            int iIconId = C1799R.drawable.sygic_bar;
            PendingIntent contentIntent = getPackageLaunchPendingIntentForNotification(context, 1, null, null, null);
            if (contentIntent != null) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(iIconId);
                builder.setColor(context.getResources().getColor(C1799R.color.azure_radiance));
                builder.setContentTitle(strTitle);
                builder.setContentText(Html.fromHtml(strText));
                builder.setContentIntent(contentIntent);
                builder.setWhen(System.currentTimeMillis());
                builder.setAutoCancel(true);
                notificationManager.notify(1, builder.build());
            }
        }
    }

    public static void showRateNotification(Context context, String strTitle, String strText, String strAction, String strActionArg) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            int iIconId = C1799R.drawable.sygic_bar;
            PendingIntent contentIntent = getPackageLaunchPendingIntentForNotification(context, 1, strAction, strActionArg, null);
            if (contentIntent != null) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(iIconId);
                builder.setColor(context.getResources().getColor(C1799R.color.azure_radiance));
                builder.setContentTitle(strTitle);
                if (strText != null) {
                    strText = Html.fromHtml(strText);
                }
                builder.setContentText(strText);
                builder.setContentIntent(contentIntent);
                builder.setWhen(System.currentTimeMillis());
                builder.setAutoCancel(true);
                notificationManager.notify(1, builder.build());
            }
        }
    }

    public static void showRetentionNotification(Context context, String text, String openBtn) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            PendingIntent contentIntent = getPackageLaunchPendingIntentForNotification(context, 3, null, null, null);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(C1799R.drawable.sygic_bar);
            builder.setColor(context.getResources().getColor(C1799R.color.azure_radiance));
            builder.setContentTitle(context.getString(C1799R.string.app_name));
            builder.setContentText(text);
            builder.setContentIntent(contentIntent);
            builder.setWhen(System.currentTimeMillis());
            builder.setAutoCancel(true);
            builder.setDeleteIntent(PendingIntent.getBroadcast(context, 1, getIntent("com.sygic.aura.ACTION_DISMISSED_RETENTION_NOTIFICATION", 3), 134217728));
            builder.addAction(0, openBtn, contentIntent);
            notificationManager.notify(3, builder.build());
        }
    }

    private static Intent getIntent(String name, int notificationId) {
        Intent intent = new Intent(name);
        Bundle extras = new Bundle();
        extras.putInt("pending_notif_id", notificationId);
        intent.putExtras(extras);
        return intent;
    }

    public static void showPromoNotification(Context context, String strTitle, String strText, String strIcon, String strAction, String strActionArg) {
        showPromoNotification(context, strTitle, strText, strIcon, strAction, strActionArg, null, null);
    }

    public static void showPromoNotification(Context context, String strTitle, String strText, String strIcon, String strAction, String strActionArg, String bitmapUrl, Bitmap bitmap) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            int iIconId = C1799R.drawable.sygic_bar;
            if (strIcon != null) {
                int id = context.getResources().getIdentifier(strIcon, "drawable", context.getPackageName());
                if (id != 0) {
                    iIconId = id;
                }
            }
            PendingIntent contentIntent = getPackageLaunchPendingIntentForNotification(context, 2, strAction, strActionArg, bitmap);
            if (contentIntent != null) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(iIconId);
                builder.setColor(context.getResources().getColor(C1799R.color.azure_radiance));
                builder.setContentTitle(strTitle);
                builder.setContentText(Html.fromHtml(strText));
                builder.setContentIntent(contentIntent);
                builder.setWhen(System.currentTimeMillis());
                builder.setAutoCancel(true);
                if (bitmap != null) {
                    builder.setStyle(new BigPictureStyle().bigPicture(bitmap).setBigContentTitle(strTitle).setSummaryText(Html.fromHtml(strText)));
                    builder.addAction(0, context.getString(C1799R.string.notif_learn_more), contentIntent);
                }
                Intent alarmIntent = new Intent("com.sygic.aura.ACTION_POSTPONE_NOTIFICATION");
                alarmIntent.putExtra("com.sygic.aura.BUNDLEKEY_TITLE", strTitle);
                alarmIntent.putExtra("com.sygic.aura.BUNDLEKEY_TEXT", strText);
                alarmIntent.putExtra("com.sygic.aura.BUNDLEKEY_ICON", strIcon);
                alarmIntent.putExtra("com.sygic.aura.BUNDLEKEY_ACTION", strAction);
                alarmIntent.putExtra("com.sygic.aura.BUNDLEKEY_ACTION_ARG", strActionArg);
                alarmIntent.putExtra("com.sygic.aura.BUNDLEKEY_BITMAP_URL", bitmapUrl);
                builder.addAction(0, context.getString(C1799R.string.notif_remind_later), PendingIntent.getBroadcast(context, 7898, alarmIntent, 134217728));
                builder.setDeleteIntent(PendingIntent.getBroadcast(context, 8899, getIntent("com.sygic.aura.ACTION_DISMISSED_PROMO_NOTIFICATION", 2), 134217728));
                notificationManager.notify(2, builder.build());
            }
        }
    }

    public static void showPromoNotification(Context context, Intent intent) {
        String strTitle = intent.getStringExtra("com.sygic.aura.BUNDLEKEY_TITLE");
        String strText = intent.getStringExtra("com.sygic.aura.BUNDLEKEY_TEXT");
        String strIcon = intent.getStringExtra("com.sygic.aura.BUNDLEKEY_ICON");
        String strAction = intent.getStringExtra("com.sygic.aura.BUNDLEKEY_ACTION");
        String strActionArg = intent.getStringExtra("com.sygic.aura.BUNDLEKEY_ACTION_ARG");
        String strBitmapUrl = intent.getStringExtra("com.sygic.aura.BUNDLEKEY_BITMAP_URL");
        if (strBitmapUrl == null || VERSION.SDK_INT < 16) {
            showPromoNotification(context, strTitle, strText, strIcon, strAction, strActionArg);
        } else {
            context.startService(new Intent(context, NotifImageDownloaderService.class).setAction("com.sygic.aura.c2dm.ACTION_DOWNLOAD_IMAGE_AND_SHOW_NOTIF").putExtra(AbstractFragment.ARG_TITLE, strTitle).putExtra("text", strText).putExtra("icon", strIcon).putExtra("action", strAction).putExtra("action_arg", strActionArg).putExtra("image_url", strBitmapUrl));
        }
    }

    private static Intent getNotificationIntent(Context context, int iNotificationId, String strAction, String strActionArg, boolean isBitmap) {
        Class<?> clazz = null;
        Intent resultIntent = null;
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            try {
                clazz = Class.forName(intent.getComponent().getClassName());
            } catch (ClassNotFoundException e) {
                clazz = null;
            }
        }
        if (clazz != null) {
            resultIntent = new Intent(context, clazz);
            if (strAction != null) {
                if (strAction.equals("list")) {
                    resultIntent.putExtra("MY_SYGIC", strActionArg);
                }
                if (strAction.equals("detail")) {
                    resultIntent.putExtra("MY_SYGIC_DETAIL", strActionArg);
                } else if (strAction.equals("url")) {
                    resultIntent = new Intent("android.intent.action.VIEW", Uri.parse(strActionArg));
                }
            }
            if (iNotificationId == 3) {
                resultIntent.putExtra("pending_notif_id", iNotificationId);
            }
            if (isBitmap || iNotificationId == 2) {
                resultIntent.putExtra("pending_notif_id", iNotificationId);
            }
        }
        return resultIntent;
    }

    public static PendingIntent getPackageLaunchPendingIntentForNotification(Context context, int iNotificationId, String strAction, String strActionArg, Bitmap bitmap) {
        boolean z;
        if (bitmap != null) {
            z = true;
        } else {
            z = false;
        }
        Intent notifIntent = getNotificationIntent(context, iNotificationId, strAction, strActionArg, z);
        if (notifIntent != null) {
            return PendingIntent.getActivity(context, 0, notifIntent, 134217728);
        }
        return null;
    }

    public static void cancelNotification(Context context, int iNotificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager == null) {
            return;
        }
        if (iNotificationId == -1) {
            notificationManager.cancelAll();
        } else {
            notificationManager.cancel(iNotificationId);
        }
    }
}
