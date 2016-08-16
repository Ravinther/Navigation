package com.sygic.aura.events;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

public interface ActivityEventListener {
    void onConfigChanged(Configuration configuration);

    void onContentViewSet(ViewGroup viewGroup);

    void onCreate(Bundle bundle);

    void onDestroy();

    void onNewIntent(Intent intent);

    boolean onOptionsItemSelected(MenuItem menuItem);

    void onPause();

    void onResume();
}
