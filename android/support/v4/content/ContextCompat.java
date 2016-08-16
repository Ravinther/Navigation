package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.io.File;

public class ContextCompat {
    public static boolean startActivities(Context context, Intent[] intents, Bundle options) {
        int version = VERSION.SDK_INT;
        if (version >= 16) {
            ContextCompatJellybean.startActivities(context, intents, options);
            return true;
        } else if (version < 11) {
            return false;
        } else {
            ContextCompatHoneycomb.startActivities(context, intents);
            return true;
        }
    }

    public static File[] getExternalFilesDirs(Context context, String type) {
        int version = VERSION.SDK_INT;
        if (version >= 19) {
            return ContextCompatKitKat.getExternalFilesDirs(context, type);
        }
        File single;
        if (version >= 8) {
            single = ContextCompatFroyo.getExternalFilesDir(context, type);
        } else {
            single = buildPath(Environment.getExternalStorageDirectory(), "Android", PoiFragment.ARG_DATA, context.getPackageName(), "files", type);
        }
        return new File[]{single};
    }

    private static File buildPath(File base, String... segments) {
        File cur = base;
        String[] arr$ = segments;
        int len$ = arr$.length;
        int i$ = 0;
        File cur2 = cur;
        while (i$ < len$) {
            String segment = arr$[i$];
            if (cur2 == null) {
                cur = new File(segment);
            } else if (segment != null) {
                cur = new File(cur2, segment);
            } else {
                cur = cur2;
            }
            i$++;
            cur2 = cur;
        }
        return cur2;
    }

    public static final Drawable getDrawable(Context context, int id) {
        if (VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.getDrawable(context, id);
        }
        return context.getResources().getDrawable(id);
    }
}
