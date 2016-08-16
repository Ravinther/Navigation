package com.sygic.aura.store.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.interfaces.ComponentsFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.fragments.interfaces.MultiSelectComponentsResultCallback;
import com.sygic.aura.fragments.interfaces.UpdateMapFragmentResultCallback;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.ComponentEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.store.data.ButtonEntry;
import com.sygic.aura.store.data.ButtonEntry.EButtonAction;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.model.MultiChoiceComponentsAdapter;
import com.sygic.aura.store.model.StoreAdapter;
import com.sygic.aura.views.animation.VerticalExpandingAnimator;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import java.util.ArrayList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class MultiSelectComponentsFragment extends ComponentsFragment implements MultiSelectComponentsResultCallback, BackPressedListener {
    private static final String TAG;
    private MultiChoiceComponentsAdapter mAdapter;
    private MenuItem mItem;
    private STextView mUpdateBt;

    /* renamed from: com.sygic.aura.store.fragment.MultiSelectComponentsFragment.1 */
    class C17311 implements OnClickListener {

        /* renamed from: com.sygic.aura.store.fragment.MultiSelectComponentsFragment.1.1 */
        class C17301 implements DialogInterface.OnClickListener {
            final /* synthetic */ Object[][] val$items;

            C17301(Object[][] objArr) {
                this.val$items = objArr;
            }

            public void onClick(DialogInterface dialog, int which) {
                MultiSelectComponentsFragment.this.proceedInstall(this.val$items);
            }
        }

        C17311() {
        }

        public void onClick(View view) {
            Object[][] items;
            boolean hasSelectedItems = MultiSelectComponentsFragment.this.mAdapter.hasSelectedItems();
            if (hasSelectedItems) {
                items = MultiSelectComponentsFragment.this.mAdapter.getSelectedItems();
            } else {
                items = MultiSelectComponentsFragment.this.mAdapter.getAllItems();
            }
            if (!hasSelectedItems || MultiSelectComponentsFragment.this.mAdapter.areAllItemsSelected()) {
                MultiSelectComponentsFragment.this.proceedInstall(items);
            } else {
                new Builder(MultiSelectComponentsFragment.this.getActivity()).title(2131166003).body(2131166002).negativeButton(2131165351, null).positiveButton(2131165355, new C17301(items)).build().showAllowingStateLoss("warning_dialog");
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MultiSelectComponentsFragment.2 */
    class C17322 implements OnClickListener {
        C17322() {
        }

        public void onClick(View view) {
            if (MultiSelectComponentsFragment.this.mResultCallback != null && (MultiSelectComponentsFragment.this.mResultCallback instanceof UpdateMapFragmentResultCallback)) {
                ((UpdateMapFragmentResultCallback) MultiSelectComponentsFragment.this.mResultCallback).onIgnore();
                MultiSelectComponentsFragment.this.mResultCallback = null;
                MultiSelectComponentsFragment.this.performHomeAction();
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MultiSelectComponentsFragment.3 */
    class C17333 implements DialogInterface.OnClickListener {
        final /* synthetic */ Object[][] val$items;
        final /* synthetic */ long val$totalSize;

        C17333(Object[][] objArr, long j) {
            this.val$items = objArr;
            this.val$totalSize = j;
        }

        public void onClick(DialogInterface dialog, int which) {
            MultiSelectComponentsFragment.this.doProceed(this.val$items, this.val$totalSize);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MultiSelectComponentsFragment.4 */
    class C17344 implements Runnable {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ StoreEntry val$item;

        C17344(Activity activity, StoreEntry storeEntry) {
            this.val$activity = activity;
            this.val$item = storeEntry;
        }

        public void run() {
            MarketPlaceFragment.onButtonClick(this.val$activity, this.val$item);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MultiSelectComponentsFragment.5 */
    static /* synthetic */ class C17355 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType;

        static {
            $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType = new int[EViewType.values().length];
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_FOLDER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_COMPONENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_BUTTON.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static {
        TAG = MultiSelectComponentsFragment.class.getSimpleName();
    }

    protected StoreAdapter getAdapter(Context context) {
        StoreAdapter multiChoiceComponentsAdapter = new MultiChoiceComponentsAdapter(context, this);
        this.mAdapter = multiChoiceComponentsAdapter;
        return multiChoiceComponentsAdapter;
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        this.mItem = toolbar.getMenu().findItem(2131624673);
    }

    protected void initPendingLayout(View root) {
        this.mUpdateBt = (STextView) root.findViewById(2131624227);
        this.mUpdateBt.setOnClickListener(new C17311());
        root.findViewById(2131624204).setOnClickListener(new C17322());
    }

    private void proceedInstall(Object[][] items) {
        ComponentManager.nativeInstall(items, new Object[0][]);
        registerDownloadProgressNotification(items);
        if (this.mResultCallback != null && (this.mResultCallback instanceof UpdateMapFragmentResultCallback)) {
            List<String> list = new ArrayList(items.length);
            for (Object[] item : items) {
                list.add((String) item[0]);
            }
            ((UpdateMapFragmentResultCallback) this.mResultCallback).onUpdateMaps(list);
            this.mResultCallback = null;
        }
        SettingsManager.nativeResetUpdateNumber();
        performHomeAction();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624673) {
            return super.onOptionsItemSelected(item);
        }
        proceed();
        return true;
    }

    private void proceed() {
        Object[][] items = this.mAdapter.getSelectedItems();
        if (items.length > 0) {
            long totalSize = this.mAdapter.getTotalSize();
            if (totalSize > this.mFreeSpace) {
                showNoFreeSpaceWarningDialog(totalSize, this.mFreeSpace);
                return;
            }
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService("connectivity");
            boolean metered = VERSION.SDK_INT >= 16 && cm.isActiveNetworkMetered();
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            if (totalSize <= 0 || (!metered && (activeNetworkInfo == null || activeNetworkInfo.getType() != 0))) {
                doProceed(items, totalSize);
            } else {
                showMeteredNetworkWarningDialog(items, totalSize);
            }
        }
    }

    private void showNoFreeSpaceWarningDialog(long totalSize, long freespace) {
        new Builder(getActivity()).title(2131165451).body(ResourceManager.getCoreString(getActivity(), 2131165450).replace("%t_size%", ResourceManager.nativeFormatBytes(totalSize)).replace("%f_space%", ResourceManager.nativeFormatBytes(freespace))).negativeButton(2131165355, null).build().showAllowingStateLoss("space_warning");
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwModalNotEnoughSpace(totalSize, this.mAdapter.getSelectedIdsCopy());
    }

    private void showMeteredNetworkWarningDialog(Object[][] items, long totalSize) {
        new Builder(getActivity()).title(2131165448).body(ResourceManager.getCoreString(getActivity(), 2131165449).replace("%size%", ResourceManager.nativeFormatBytes(totalSize))).negativeButton(2131165351, null).positiveButton(2131165461, new C17333(items, totalSize)).build().showAllowingStateLoss("wifi_warning");
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwModalMobileNetworkDownload(totalSize, this.mAdapter.getSelectedIdsCopy());
    }

    private void doProceed(Object[][] items, long totalSize) {
        if (this.mResultCallback != null && (this.mResultCallback instanceof MultiSelectComponentsResultCallback)) {
            ((MultiSelectComponentsResultCallback) this.mResultCallback).setComponentsSelected(items, new Object[0][]);
            ((MultiSelectComponentsResultCallback) this.mResultCallback).setTotalComponentsSize(totalSize);
        }
        this.mChanged = true;
        Fragments.clearBackStack(getActivity(), false);
    }

    protected void processUpdate(ArrayList<StoreEntry> arrayList) {
        VerticalExpandingAnimator.animateView(this.mPending, true);
    }

    public void onDestroy() {
        super.onDestroy();
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
        if (this.mResultCallback != null && (this.mResultCallback instanceof UpdateMapFragmentResultCallback)) {
            ((UpdateMapFragmentResultCallback) this.mResultCallback).onDismiss();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StoreEntry item = (StoreEntry) parent.getItemAtPosition(position);
        if (item != null) {
            switch (C17355.$SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[item.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    onFolderClick(item, getClass());
                case TTSConst.TTSPARAGRAPH /*2*/:
                    ((MultiChoiceComponentsAdapter) parent.getAdapter()).onItemClick(item);
                case TTSConst.TTSUNICODE /*3*/:
                    Activity activity = getActivity();
                    performHomeAction();
                    if (item instanceof ButtonEntry) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AbstractFragment.ARG_TITLE, item.getTitle());
                        if (((ButtonEntry) item).getAction() == EButtonAction.BtnActivate) {
                            bundle.putString("product_id", "-2");
                            bundle.putBoolean("force_multiselect", true);
                            bundle.putString("source", TAG);
                        }
                        ComponentEventsReceiver.unregisterEventsListener(this);
                        Fragments.add(getActivity(), MarketPlaceFragment.class, item.getTitle(), bundle);
                        view.getHandler().post(new C17344(activity, item));
                    }
                default:
                    Log.w(TAG, "Unsupported call of type: " + item.getType());
            }
        }
    }

    public void setProceedEnabled(boolean enabled) {
        if (this.mItem != null) {
            this.mItem.setEnabled(enabled);
        }
        if (this.mUpdateBt != null) {
            this.mUpdateBt.setCoreText(enabled ? 2131165459 : 2131165502);
        }
    }

    public void setProceedVisible(boolean visible) {
        if (this.mItem != null) {
            this.mItem.setVisible(visible);
        }
    }

    public void setAdditionalDataSize(long size) {
    }

    protected FragmentResultCallback getCallback() {
        return this;
    }

    public boolean onBackPressed() {
        return true;
    }

    public void setComponentsSelected(Object[][] installItems, Object[][] uninstallItems) {
        if (this.mResultCallback != null) {
            ((MultiSelectComponentsResultCallback) this.mResultCallback).setComponentsSelected(installItems, uninstallItems);
        }
    }

    public void setTotalComponentsSize(long size) {
        if (this.mResultCallback != null) {
            ((MultiSelectComponentsResultCallback) this.mResultCallback).setTotalComponentsSize(size);
        }
    }

    public void onComponentsFragmentResult(boolean changed) {
        if (changed && this.mResultCallback != null) {
            ((ComponentsFragmentResultCallback) this.mResultCallback).onComponentsFragmentResult(changed);
        }
    }

    public void onDashboardFragmentFinished() {
        onComponentsFragmentResult(false);
    }

    public void onConnectionChanged(Boolean connected) {
        if (!connected.booleanValue()) {
            this.mAdapter.clear();
            setProceedEnabled(false);
            setProceedVisible(false);
        }
        super.onConnectionChanged(connected);
    }
}
