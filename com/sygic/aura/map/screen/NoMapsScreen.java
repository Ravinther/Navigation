package com.sygic.aura.map.screen;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.fragment.ComponentsFragment;

public class NoMapsScreen extends OverlayScreen {

    /* renamed from: com.sygic.aura.map.screen.NoMapsScreen.1 */
    class C13461 implements OnClickListener {
        C13461() {
        }

        public void onClick(View view) {
            Bundle bundle = new Bundle();
            String title = ResourceManager.getCoreString(NoMapsScreen.this.mContext, 2131165799);
            bundle.putString(AbstractFragment.ARG_TITLE, title);
            Fragments.add(NoMapsScreen.this.mFragment.getActivity(), ComponentsFragment.class, title, bundle);
        }
    }

    protected void setupChildScreen(View rootView) {
        rootView.findViewById(2131624444).setOnClickListener(new C13461());
    }

    protected void onScreenEntered() {
    }

    protected void onscreenLeft() {
    }
}
