package com.sygic.aura.feature.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.util.Log;
import com.sygic.aura.SygicMain;
import com.sygic.aura.SygicNaviActivity;
import com.sygic.aura.WebActivity;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DropBoxLogin {
    private static final String[] DROPBOX_APP_SIGNATURES;
    public static String KEY_DELIMITER;
    private static String TAG;
    public static String mStrAppKey;
    private Context mCtx;

    static {
        TAG = DropBoxLogin.class.getName();
        KEY_DELIMITER = "\\|";
        mStrAppKey = null;
        DROPBOX_APP_SIGNATURES = new String[]{"308202223082018b02044bd207bd300d06092a864886f70d01010405003058310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f3110300e060355040a130744726f70626f783112301006035504031309546f6d204d65796572301e170d3130303432333230343930315a170d3430303431353230343930315a3058310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f3110300e060355040a130744726f70626f783112301006035504031309546f6d204d6579657230819f300d06092a864886f70d010101050003818d0030818902818100ac1595d0ab278a9577f0ca5a14144f96eccde75f5616f36172c562fab0e98c48ad7d64f1091c6cc11ce084a4313d522f899378d312e112a748827545146a779defa7c31d8c00c2ed73135802f6952f59798579859e0214d4e9c0554b53b26032a4d2dfc2f62540d776df2ea70e2a6152945fb53fef5bac5344251595b729d4810203010001300d06092a864886f70d01010405000381810055c425d94d036153203dc0bbeb3516f94563b102fff39c3d4ed91278db24fc4424a244c2e59f03bbfea59404512b8bf74662f2a32e37eafa2ac904c31f99cfc21c9ff375c977c432d3b6ec22776f28767d0f292144884538c3d5669b568e4254e4ed75d9054f75229ac9d4ccd0b7c3c74a34f07b7657083b2aa76225c0c56ffc", "308201e53082014ea00302010202044e17e115300d06092a864886f70d01010505003037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f6964204465627567301e170d3131303730393035303331375a170d3431303730313035303331375a3037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f696420446562756730819f300d06092a864886f70d010101050003818d003081890281810096759fe5abea6a0757039b92adc68d672efa84732c3f959408e12efa264545c61f23141026a6d01eceeeaa13ec7087087e5894a3363da8bf5c69ed93657a6890738a80998e4ca22dc94848f30e2d0e1890000ae2cddf543b20c0c3828deca6c7944b5ecd21a9d18c988b2b3e54517dafbc34b48e801bb1321e0fa49e4d575d7f0203010001300d06092a864886f70d0101050500038181002b6d4b65bcfa6ec7bac97ae6d878064d47b3f9f8da654995b8ef4c385bc4fbfbb7a987f60783ef0348760c0708acd4b7e63f0235c35a4fbcd5ec41b3b4cb295feaa7d5c27fa562a02562b7e1f4776b85147be3e295714986c4a9a07183f48ea09ae4d3ea31b88d0016c65b93526b9c45f2967c3d28dee1aff5a5b29b9c2c8639"};
    }

    public DropBoxLogin(Context context) {
        this.mCtx = context;
    }

    public void login(String arg) {
        String[] separated = arg.split(KEY_DELIMITER);
        if (separated.length != 3) {
            Log.e(TAG, "login invalid argument split return array.len = " + separated.length + " ");
            return;
        }
        String strUrl = separated[0];
        String strConsumerKey = separated[1];
        String strConsumerSecret = separated[2];
        mStrAppKey = strConsumerKey;
        Intent officialIntent = new Intent();
        officialIntent.setPackage("com.dropbox.android");
        officialIntent.setAction("com.dropbox.android.AUTHENTICATE_V2");
        officialIntent.putExtra("CONSUMER_KEY", strConsumerKey);
        officialIntent.putExtra("CONSUMER_SIG", getConsumerSig(strConsumerSecret));
        officialIntent.putExtra("CALLING_PACKAGE", this.mCtx.getPackageName());
        officialIntent.putExtra("CALLING_CLASS", SygicNaviActivity.class.getName());
        if (hasDropboxApp(officialIntent)) {
            this.mCtx.startActivity(officialIntent);
            return;
        }
        Intent intent = new Intent(this.mCtx, WebActivity.class);
        intent.setData(Uri.parse(strUrl));
        Activity act = SygicMain.getInstance().getFeature().getActivity();
        if (act != null) {
            act.startActivityForResult(intent, 225);
        } else {
            this.mCtx.startActivity(intent);
            throw new IllegalStateException("Activity doesn't exists in " + DropBoxLogin.class.getName() + ".login()");
        }
    }

    private boolean hasDropboxApp(Intent intent) {
        PackageManager packageManager = this.mCtx.getPackageManager();
        if (packageManager.queryIntentActivities(intent, 0).size() == 0) {
            return false;
        }
        ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);
        if (resolveInfo == null) {
            return false;
        }
        try {
            for (Signature signature : packageManager.getPackageInfo(resolveInfo.activityInfo.packageName, 64).signatures) {
                for (String dbSignature : DROPBOX_APP_SIGNATURES) {
                    if (dbSignature.equals(signature.toCharsString())) {
                        return true;
                    }
                }
            }
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private String getConsumerSig(String consumerSecret) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
        }
        m.update(consumerSecret.getBytes(), 0, consumerSecret.length());
        BigInteger i = new BigInteger(1, m.digest());
        return String.format("%1$040X", new Object[]{i}).substring(32);
    }
}
