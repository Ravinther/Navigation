package android.support.v4.content;

import android.content.Context;
import java.io.File;

class ContextCompatKitKat {
    public static File[] getExternalFilesDirs(Context context, String type) {
        return context.getExternalFilesDirs(type);
    }
}
