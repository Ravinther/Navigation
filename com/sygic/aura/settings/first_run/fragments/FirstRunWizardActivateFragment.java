package com.sygic.aura.settings.first_run.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.fragments.ProductCodeFragment;
import com.sygic.aura.helper.EventReceivers.MarketPlaceEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.MarketPlaceListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.MarketPlaceManager;
import com.sygic.aura.store.data.ProductDetailEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.base.C1799R;
import java.util.ArrayList;

public class FirstRunWizardActivateFragment extends AbstractScreenFragment implements MarketPlaceListener {
    private boolean mSuccessMsg;

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardActivateFragment.1 */
    class C16281 implements OnClickListener {

        /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardActivateFragment.1.1 */
        class C16271 implements DialogFragmentClickListener {
            C16271() {
            }

            public void onPositiveButtonClicked(Editable text) {
                if (!MarketPlaceManager.nativeEnterProductCode(text.toString())) {
                    FirstRunWizardActivateFragment.this.showResult(1, 2131165464, ResourceManager.getCoreString(FirstRunWizardActivateFragment.this.getActivity(), 2131165437));
                }
            }
        }

        C16281() {
        }

        public void onClick(View view) {
            ProductCodeFragment.newInstance(FirstRunWizardActivateFragment.this.getActivity(), 2131165932, null, new C16271()).showDialog();
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardActivateFragment.2 */
    class C16292 implements OnClickListener {
        C16292() {
        }

        public void onClick(View view) {
            if (!MarketPlaceManager.nativeRestoreProducts()) {
                FirstRunWizardActivateFragment.this.showResult(1, 2131165464, ResourceManager.getCoreString(FirstRunWizardActivateFragment.this.getActivity(), 2131165463));
            }
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardActivateFragment.3 */
    class C16303 implements OnClickListener {
        C16303() {
        }

        public void onClick(View view) {
        }
    }

    public FirstRunWizardActivateFragment() {
        this.mSuccessMsg = false;
        setWantsNavigationData(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MarketPlaceEventsReceiver.registerEventsListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903103, container, false);
        view.findViewById(2131624196).setOnClickListener(new C16281());
        view.findViewById(2131624197).setOnClickListener(new C16292());
        view.findViewById(2131624198).setOnClickListener(new C16303());
        return view;
    }

    private void showResult(int type, int titleId, String strMessage) {
        if (!this.mSuccessMsg) {
            this.mSuccessMsg = type == 0;
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(getActivity(), titleId));
            bundle.putInt("type", type);
            bundle.putString("message", strMessage);
            Fragments.add(getActivity(), FirstRunWizardRestoreResultFragment.class, "fragment_restore_result_tag", bundle, true, this.mResultCallback);
        }
    }

    public void onProductDetail(ProductDetailEntry product) {
    }

    public void onShowError(Integer iErrorState, String strMessage) {
        showResult(1, 2131165464, strMessage);
    }

    public void onEnterActivationCode() {
    }

    public void onShowComponents(String strId) {
        showResult(0, 2131165465, ResourceManager.getCoreString(getActivity(), 2131165438).replace("%productmlm%", getString(C1799R.string.app_name)));
    }

    public void onListLoaded(ArrayList<StoreEntry> arrayList, Boolean isUpdateRequired) {
    }

    public void onStoreMessage(String strMessage) {
        showResult(0, 2131165465, strMessage);
    }

    public void onInstallRequired() {
    }

    public void onConnectionChanged(Boolean connected) {
    }
}
