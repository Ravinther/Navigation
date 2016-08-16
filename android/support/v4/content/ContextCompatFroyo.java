package android.support.v4.content;

import android.content.Context;
import java.io.File;

class ContextCompatFroyo {
    public static File getExternalFilesDir(Context context, String type) {
        return context.getExternalFilesDir(type);
    }
}
