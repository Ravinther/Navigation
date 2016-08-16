package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import java.io.IOException;
import loquendo.tts.engine.TTSConst;

class FileBitmapHunter extends ContentStreamBitmapHunter {
    FileBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(context, picasso, dispatcher, cache, stats, action);
    }

    Bitmap decode(Request data) throws IOException {
        setExifRotation(getFileExifRotation(data.uri));
        return super.decode(data);
    }

    static int getFileExifRotation(Uri uri) throws IOException {
        switch (new ExifInterface(uri.getPath()).getAttributeInt("Orientation", 1)) {
            case TTSConst.TTSUNICODE /*3*/:
                return 180;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return 90;
            case TTSConst.TTSEVT_TAG /*8*/:
                return 270;
            default:
                return 0;
        }
    }
}
