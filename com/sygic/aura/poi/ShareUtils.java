package com.sygic.aura.poi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.resources.ResourceManager;

public class ShareUtils {
    public static void sharePoi(Activity activity, String poiName, LongPosition longPosition) {
        if (poiName == null || longPosition == null || !longPosition.isValid()) {
            SToast.makeText(activity, 2131165571, 0).show();
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", makeSharedPoiText(activity, poiName, longPosition));
        Intent chooser = Intent.createChooser(intent, ResourceManager.getCoreString((Context) activity, 2131165557));
        if (isIntentAvailable(activity, intent)) {
            activity.startActivity(chooser);
        } else {
            Toast.makeText(activity, 2131165570, 0).show();
        }
    }

    private static String makeSharedPoiText(Context context, String poiName, LongPosition poiPosition) {
        return poiName + "\n" + ResourceManager.nativeFormatPosition(poiPosition.getX(), poiPosition.getY()) + "\n\n" + "________________________________" + "\n" + ResourceManager.getCoreString(context, 2131165565) + "\n" + getGooglePlayUrl(context);
    }

    private static boolean isIntentAvailable(Context context, Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public static String getGooglePlayUrl(Context context) {
        return "https://play.google.com/store/apps/details?id=" + context.getPackageName();
    }
}
