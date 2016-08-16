package com.squareup.picasso;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Contacts;
import com.squareup.picasso.Picasso.LoadedFrom;
import java.io.IOException;
import java.io.InputStream;
import loquendo.tts.engine.TTSConst;

class ContactsPhotoBitmapHunter extends BitmapHunter {
    private static final UriMatcher matcher;
    final Context context;

    @TargetApi(14)
    private static class ContactPhotoStreamIcs {
        static InputStream get(ContentResolver contentResolver, Uri uri) {
            return Contacts.openContactPhotoInputStream(contentResolver, uri, true);
        }
    }

    static {
        matcher = new UriMatcher(-1);
        matcher.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
        matcher.addURI("com.android.contacts", "contacts/lookup/*", 1);
        matcher.addURI("com.android.contacts", "contacts/#/photo", 2);
        matcher.addURI("com.android.contacts", "contacts/#", 3);
        matcher.addURI("com.android.contacts", "display_photo/#", 4);
    }

    ContactsPhotoBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.context = context;
    }

    Bitmap decode(Request data) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = getInputStream();
            Bitmap decodeStream = decodeStream(inputStream, data);
            return decodeStream;
        } finally {
            Utils.closeQuietly(inputStream);
        }
    }

    LoadedFrom getLoadedFrom() {
        return LoadedFrom.DISK;
    }

    private InputStream getInputStream() throws IOException {
        ContentResolver contentResolver = this.context.getContentResolver();
        Uri uri = getData().uri;
        switch (matcher.match(uri)) {
            case TTSConst.TTSMULTILINE /*1*/:
                uri = Contacts.lookupContact(contentResolver, uri);
                if (uri == null) {
                    return null;
                }
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSXML /*4*/:
                return contentResolver.openInputStream(uri);
            case TTSConst.TTSUNICODE /*3*/:
                break;
            default:
                throw new IllegalStateException("Invalid uri: " + uri);
        }
        if (VERSION.SDK_INT < 14) {
            return Contacts.openContactPhotoInputStream(contentResolver, uri);
        }
        return ContactPhotoStreamIcs.get(contentResolver, uri);
    }

    private Bitmap decodeStream(InputStream stream, Request data) throws IOException {
        if (stream == null) {
            return null;
        }
        Options options = BitmapHunter.createBitmapOptions(data);
        if (BitmapHunter.requiresInSampleSize(options)) {
            InputStream is = getInputStream();
            try {
                BitmapFactory.decodeStream(is, null, options);
                BitmapHunter.calculateInSampleSize(data.targetWidth, data.targetHeight, options);
            } finally {
                Utils.closeQuietly(is);
            }
        }
        return BitmapFactory.decodeStream(stream, null, options);
    }
}
