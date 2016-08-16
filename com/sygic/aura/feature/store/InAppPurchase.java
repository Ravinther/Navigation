package com.sygic.aura.feature.store;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.sygic.aura.SygicMain;
import com.sygic.aura.utils.FileUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public abstract class InAppPurchase {
    protected Context mContext;
    protected Handler mHandler;
    protected volatile boolean m_bResumed;

    public enum EPaymentResult {
        PaymentNone(0),
        PaymentCompleted(1),
        PaymentAlreadyPaid(2),
        PaymentCancelled(-1),
        PaymentFailed(-2),
        PaymentClientInvalid(-3),
        PaymentInvalid(-4),
        PaymentNotAllowed(-5),
        PaymentProductNA(-6),
        PaymentInvalidData(-7);
        
        private final int id;

        private EPaymentResult(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }
    }

    public enum EResponseType {
        RtProductInfo(1),
        RtPayment(2),
        RtRestore(3),
        RtRefund(4);
        
        private final int id;

        private EResponseType(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }
    }

    public abstract boolean buyProduct(String str);

    public abstract void checkQueuedTransactions();

    public abstract boolean handleActivityResult(int i, int i2, Intent intent);

    public abstract boolean isBillingSupported();

    public abstract void onPause();

    public abstract void onResume();

    public abstract boolean restorePurchase();

    protected abstract boolean startService();

    public abstract void stopService();

    public static InAppPurchase createInstance(Context context, Handler handler) {
        if ("com.sygic.amazon".equals(context.getPackageName())) {
            return new AmazonInAppPurchase(context, handler);
        }
        return new GoogleInAppPurchase(context, handler);
    }

    protected InAppPurchase(Context context, Handler handler) {
        this.m_bResumed = false;
        this.mContext = context;
        this.mHandler = handler;
        startService();
    }

    protected void checkQueuedTrans() {
        try {
            if (this.mContext.getFileStreamPath("QueuedTransactions").exists()) {
                FileInputStream fis = this.mContext.openFileInput("QueuedTransactions");
                if (fis != null) {
                    int nResponse = 0;
                    String strFisName = this.mContext.getFileStreamPath("QueuedTransactions").getAbsolutePath();
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    String strLine = br.readLine();
                    if (strLine != null) {
                        String[] arr = strLine.split(";", 3);
                        if (arr.length > 2) {
                            int nType = Integer.valueOf(arr[0]).intValue();
                            int nResult = Integer.valueOf(arr[1]).intValue();
                            String strData = arr[2];
                            nResponse = SygicMain.getInstance().ProcessStoreResponse(strData, strData.length(), nType, nResult);
                        }
                    }
                    br.close();
                    fis.close();
                    if (nResponse == 0) {
                        fis = this.mContext.openFileInput("QueuedTransactions");
                        br = new BufferedReader(new InputStreamReader(fis));
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.mContext.openFileOutput("tmpQueueFile", 0)));
                        while (true) {
                            String str = br.readLine();
                            if (str == null) {
                                bw.close();
                                br.close();
                                fis.close();
                                FileUtils.copyFile(this.mContext.getFileStreamPath("tmpQueueFile").getAbsolutePath(), strFisName);
                                this.mContext.deleteFile("tmpQueueFile");
                                return;
                            } else if (!str.trim().equals(strLine.trim())) {
                                bw.write(str);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void saveQueuedTransaction(Context context, String strData, int iType, int iResult) {
        if (strData != null) {
            try {
                FileOutputStream fos = context.openFileOutput("QueuedTransactions", 32768);
                fos.write((iType + ";" + iResult + ";" + strData + "\n").getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
