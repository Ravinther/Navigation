package com.sygic.aura.events;

import android.net.Uri;

public interface CoreEventsListener {
    String onPhotoChosen(Uri uri);

    boolean onWebViewOpenUri(String str, String str2);
}
