package com.sygic.widget.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class NaviHelper {
    public static void navigateSygic(Context context, float latitude, float longitude, String mode) {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("com.sygic.aura://coordinate|" + longitude + "|" + latitude + "|" + mode));
        mapIntent.addFlags(268435456);
        context.startActivity(mapIntent);
    }
}
