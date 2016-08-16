package com.sygic.aura.store.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.ComponentsFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.fragments.interfaces.UpdateMapFragmentResultCallback;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.ComponentEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.ComponentsListener;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.store.data.ComponentEntry;
import com.sygic.aura.store.data.ComponentEntry.InstallStatus;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.data.SystemTTSEntry;
import com.sygic.aura.store.model.SingleChoiceComponentsAdapter;
import com.sygic.aura.store.model.StoreAdapter;
import com.sygic.aura.store.model.holder.ViewHolderComponent;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.SmartProgressBar.OnRetryCallback;
import com.sygic.aura.views.animation.VerticalExpandingAnimator;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

public class ComponentsFragment extends AbstractScreenFragment implements OnItemClickListener, ComponentsListener {
    protected StoreAdapter mAdapter;
    protected boolean mChanged;
    protected ComponentEntry mCurrentDownload;
    protected long mFreeSpace;
    private View mFreeSpaceView;
    private boolean mFromFrwContinents;
    private boolean mFromFrwStart;
    private boolean mHasUpdate;
    protected ListView mList;
    private String mListId;
    protected boolean mLoading;
    private int mMenuId;
    protected boolean mNavigationBarChanged;
    protected View mPending;
    protected Set<String> mPendingItems;
    protected SmartProgressBar mSmartProgressBar;
    private String mTitle;

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.10 */
    static /* synthetic */ class AnonymousClass10 {
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

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.1 */
    class C17091 implements OnMenuItemClickListener {
        C17091() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return ComponentsFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.2 */
    class C17102 implements OnRetryCallback {
        C17102() {
        }

        public void onRetry() {
            if (!ComponentsFragment.this.mLoading) {
                ComponentsFragment.this.loadList(true);
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.3 */
    class C17113 implements Runnable {
        int mCounter;

        C17113() {
            this.mCounter = 0;
        }

        public void run() {
            boolean success;
            if (ComponentsFragment.this.mListId == null) {
                success = ComponentManager.nativeInvokeManageMaps();
            } else {
                success = ComponentManager.nativeRequestList(ComponentsFragment.this.mListId);
            }
            if (!success) {
                int i = this.mCounter + 1;
                this.mCounter = i;
                if (i < 3) {
                    ComponentsFragment.this.mSmartProgressBar.postDelayed(this, 1200);
                    return;
                }
                ComponentsFragment.this.mLoading = false;
                ComponentsFragment.this.mSmartProgressBar.stopAndShowError();
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.4 */
    class C17124 extends AsyncTask<StoreEntry, StoreEntry, ArrayList<StoreEntry>> {
        private StoreAdapter mAdapter;
        private int mPosition;
        private boolean mUpdate;
        final /* synthetic */ boolean val$isUpdateRequired;

        C17124(boolean z) {
            this.val$isUpdateRequired = z;
        }

        protected void onPreExecute() {
            boolean z = false;
            this.mAdapter = ComponentsFragment.this.mAdapter;
            this.mAdapter.clear();
            this.mPosition = 0;
            if (this.val$isUpdateRequired && ComponentsFragment.this.mHasUpdate) {
                z = true;
            }
            this.mUpdate = z;
        }

        protected ArrayList<StoreEntry> doInBackground(StoreEntry... storeEntries) {
            ArrayList<StoreEntry> entries = new ArrayList();
            for (StoreEntry entry : storeEntries) {
                if (!this.mUpdate || ((entry instanceof ComponentEntry) && (((ComponentEntry) entry).isUpdateAvailable() || ((ComponentEntry) entry).isMandatory()))) {
                    addEntry(entries, entry);
                    if ((entry instanceof ComponentEntry) && ((ComponentEntry) entry).isUpdateAvailable()) {
                        ComponentsFragment.this.mPendingItems.add(entry.getId());
                    }
                }
            }
            return entries;
        }

        private void addEntry(ArrayList<StoreEntry> entries, StoreEntry entry) {
            int i = this.mPosition;
            this.mPosition = i + 1;
            entry.setPosition(i);
            entries.add(entry);
        }

        protected void onPostExecute(ArrayList<StoreEntry> entries) {
            ComponentsFragment.this.mSmartProgressBar.stopAndCrossfadeWith(ComponentsFragment.this.mList);
            if (this.mUpdate) {
                ComponentsFragment.this.processUpdate(entries);
            }
            this.mAdapter.setNotifyOnChange(false);
            Iterator it = entries.iterator();
            while (it.hasNext()) {
                this.mAdapter.add((StoreEntry) it.next());
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.5 */
    class C17135 implements UpdateMapFragmentResultCallback {
        C17135() {
        }

        public void onUpdateMaps(Collection<String> updatedMaps) {
            VerticalExpandingAnimator.animateView(ComponentsFragment.this.mPending, false);
            if (updatedMaps != null) {
                ComponentsFragment.this.mPendingItems.retainAll(updatedMaps);
                StoreAdapter adapter = (StoreAdapter) ComponentsFragment.this.mList.getAdapter();
                adapter.retainAll(updatedMaps);
                for (int i = 0; i < adapter.getCount(); i++) {
                    ((ComponentEntry) adapter.getItem(i)).setDownloading(null);
                }
            }
        }

        public void onIgnore() {
            ((StoreAdapter) ComponentsFragment.this.mList.getAdapter()).clear();
            if ("10680".compareTo(ComponentsFragment.this.mListId) == 0) {
                ComponentsFragment.this.mListId = null;
            }
            ComponentsFragment.this.loadList(false);
        }

        public void onDismiss() {
            ComponentsFragment.this.performHomeAction();
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.6 */
    class C17146 implements Runnable {
        final /* synthetic */ StoreEntry val$item;

        C17146(StoreEntry storeEntry) {
            this.val$item = storeEntry;
        }

        public void run() {
            MarketPlaceFragment.onButtonClick(ComponentsFragment.this.getActivity(), this.val$item);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.7 */
    class C17157 implements OnClickListener {
        final /* synthetic */ ComponentEntry val$entry;
        final /* synthetic */ View val$view;

        C17157(ComponentEntry componentEntry, View view) {
            this.val$entry = componentEntry;
            this.val$view = view;
        }

        public void onClick(DialogInterface dialog, int which) {
            ComponentsFragment.this.uninstall(this.val$entry, this.val$view);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.8 */
    class C17168 implements Runnable {
        final /* synthetic */ ViewHolderComponent val$tag;
        final /* synthetic */ View val$view;

        C17168(View view, ViewHolderComponent viewHolderComponent) {
            this.val$view = view;
            this.val$tag = viewHolderComponent;
        }

        public void run() {
            if (ComponentsFragment.this.mCurrentDownload == null) {
                return;
            }
            if (ComponentsFragment.this.mCurrentDownload.install()) {
                ComponentsFragment.this.onInstallSuccess();
            } else {
                ComponentsFragment.this.onInstallFailed(this.val$view, this.val$tag);
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ComponentsFragment.9 */
    class C17179 extends AsyncTask<Void, Void, Long> {
        C17179() {
        }

        protected Long doInBackground(Void... voids) {
            return Long.valueOf(SygicHelper.getFreeDiskSpaceInBytes(ResourceManager.nativeGetMapPath()));
        }

        protected void onPostExecute(Long freeSpace) {
            ComponentsFragment.this.mFreeSpace = freeSpace.longValue();
            ((TextView) ComponentsFragment.this.mFreeSpaceView.findViewById(C1799R.id.text)).setText(ResourceManager.nativeFormatBytes(freeSpace.longValue()));
        }
    }

    public ComponentsFragment() {
        this.mChanged = false;
        this.mLoading = false;
        this.mNavigationBarChanged = false;
        this.mMenuId = 0;
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(this.mTitle);
        if (this.mMenuId > 0) {
            toolbar.inflateMenu(this.mMenuId);
            toolbar.setOnMenuItemClickListener(new C17091());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mTitle = bundle.getString(AbstractFragment.ARG_TITLE);
            this.mMenuId = bundle.getInt(SettingsFragment.ARG_MENU);
            this.mFromFrwStart = bundle.getBoolean("from_frw_start");
            this.mFromFrwContinents = bundle.getBoolean("from_frw_continents");
        }
        this.mPendingItems = new HashSet();
        ComponentEventsReceiver.registerEventsListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        ComponentEventsReceiver.unregisterEventsListener(this);
        if (this.mResultCallback != null && (this.mResultCallback instanceof ComponentsFragmentResultCallback)) {
            ((ComponentsFragmentResultCallback) this.mResultCallback).onComponentsFragmentResult(this.mChanged);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (NaviNativeActivity.isNavigationBarHidden(activity)) {
            NaviNativeActivity.showNavigationBar(activity, true);
            this.mNavigationBarChanged = true;
        }
    }

    public void onDetach() {
        if (this.mNavigationBarChanged) {
            NaviNativeActivity.hideNavigationBar(getActivity());
            this.mNavigationBarChanged = false;
        }
        super.onDetach();
    }

    protected StoreAdapter getAdapter(Context context) {
        return new SingleChoiceComponentsAdapter(context);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903111, container, false);
        this.mList = (ListView) view.findViewById(2131624221);
        addHeaderView(inflater, this.mList);
        this.mAdapter = getAdapter(inflater.getContext());
        this.mList.setAdapter(this.mAdapter);
        this.mList.setOnItemClickListener(this);
        Bundle arguments = getArguments();
        this.mHasUpdate = arguments.getBoolean("fragment_has_update", false);
        this.mListId = arguments.getString("map_list_id", this.mHasUpdate ? "10680" : null);
        ArrayList<StoreEntry> dataList = arguments.getParcelableArrayList("map_data");
        this.mPending = view.findViewById(2131624225);
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        this.mSmartProgressBar.setRetryCallback(new C17102());
        initPendingLayout(this.mPending);
        this.mFreeSpaceView = view.findViewById(2131624230);
        if (this.mListId == null && dataList == null) {
            this.mFreeSpaceView.setVisibility(8);
        } else {
            calculateFreeSpace();
        }
        if (dataList == null) {
            loadList(false);
        } else {
            onListLoaded(dataList, Boolean.valueOf(this.mHasUpdate));
        }
        if (this.mFromFrwStart) {
            InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwChooseContinent();
        }
        if (this.mFromFrwContinents) {
            InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwChooseCountry(this.mListId);
        }
        return view;
    }

    private void loadList(boolean reload) {
        Runnable r = new C17113();
        this.mLoading = true;
        if (reload) {
            this.mSmartProgressBar.startWithCrossfade();
        } else {
            this.mSmartProgressBar.startWithFadeIn();
        }
        this.mSmartProgressBar.post(r);
    }

    protected void initPendingLayout(View root) {
    }

    protected void registerDownloadProgressNotification(Object[][] installItems) {
        if (!(this instanceof TTSVoiceComponentsFragment)) {
            String[] installIds = new String[installItems.length];
            for (int i = 0; i < installItems.length; i++) {
                installIds[i] = (String) installItems[i][0];
            }
            ((NaviNativeActivity) getActivity()).registerDownloadProgressNotification(installIds);
        }
    }

    protected AsyncTask getProcessListTask(boolean isUpdateRequired) {
        return new C17124(isUpdateRequired);
    }

    protected void processUpdate(ArrayList<StoreEntry> entries) {
        Bundle args = new Bundle();
        args.putString(AbstractFragment.ARG_TITLE, this.mTitle);
        args.putParcelableArrayList("map_data", entries);
        args.putInt(SettingsFragment.ARG_MENU, this.mMenuId);
        args.putBoolean("fragment_has_update", this.mHasUpdate);
        Fragments.add(getActivity(), MultiSelectComponentsFragment.class, this.mTitle, args, true, new C17135());
    }

    public void onListLoaded(ArrayList<StoreEntry> entries, Boolean isUpdateRequired) {
        this.mLoading = false;
        AsyncTaskHelper.execute(getProcessListTask(isUpdateRequired.booleanValue()), entries.toArray(new StoreEntry[entries.size()]));
    }

    public void onConnectionChanged(Boolean connected) {
        if (!connected.booleanValue()) {
            if (this.mList.getAdapter().getCount() <= 0) {
                this.mSmartProgressBar.stopAndShowError();
            }
            this.mLoading = false;
        } else if (!this.mLoading) {
            loadList(true);
        }
    }

    public void onInstallWaiting(String id) {
        if (this.mCurrentDownload == null || !this.mCurrentDownload.getId().equals(id)) {
            this.mCurrentDownload = this.mAdapter.findItem(id);
            if (this.mCurrentDownload != null) {
                this.mCurrentDownload.showInstallNotification(getViewHolder(this.mCurrentDownload), true);
            }
        } else if (getViewHolder(this.mCurrentDownload) != null) {
            this.mCurrentDownload.showInstallNotification(getViewHolder(this.mCurrentDownload), true);
        }
    }

    public void onInstallFinished(String id) {
        if (this.mCurrentDownload == null || !this.mCurrentDownload.getId().equals(id)) {
            ComponentEntry item = this.mAdapter.findItem(id);
            if (item != null) {
                item.setProgress(null, ComponentEntry.PROGRESS_MAX);
                item.setFinished(getViewHolder(item));
            }
        } else {
            this.mCurrentDownload.setFinished(getViewHolder(this.mCurrentDownload));
            this.mCurrentDownload = null;
        }
        if (!this.mPendingItems.isEmpty()) {
            this.mPendingItems.remove(id);
            if (this.mPendingItems.isEmpty()) {
                this.mAdapter.clear();
                VerticalExpandingAnimator.animateView(this.mPending, false);
                if ("10680".compareTo(this.mListId) == 0) {
                    this.mListId = null;
                }
                loadList(false);
            }
        }
        this.mChanged = true;
        calculateFreeSpace();
    }

    public void onDownloadProgressUpdated(String id, Short progress, Long total, Long downloaded) {
        if (this.mCurrentDownload == null || !this.mCurrentDownload.getId().equals(id)) {
            this.mCurrentDownload = this.mAdapter.findItem(id);
            if (this.mCurrentDownload != null) {
                this.mCurrentDownload.setProgress(null, progress.shortValue());
                this.mCurrentDownload.setDownloading(getViewHolder(this.mCurrentDownload));
                return;
            }
            return;
        }
        ViewHolderComponent viewHolder = getViewHolder(this.mCurrentDownload);
        if (viewHolder != null) {
            this.mCurrentDownload.setProgress(viewHolder.getProgressView(), progress.shortValue());
        } else {
            this.mCurrentDownload = null;
        }
    }

    public void onUninstallFinished(String id) {
        this.mChanged = true;
        if (this.mCurrentDownload == null || !this.mCurrentDownload.getId().equals(id)) {
            ComponentEntry item = this.mAdapter.findItem(id);
            if (item != null) {
                item.showInstallNotification(getViewHolder(item), false);
            }
        } else {
            this.mCurrentDownload.showInstallNotification(getViewHolder(this.mCurrentDownload), false);
            this.mCurrentDownload = null;
        }
        calculateFreeSpace();
        if (!this.mPendingItems.isEmpty()) {
            this.mPendingItems.remove(id);
            if (this.mPendingItems.isEmpty()) {
                this.mAdapter.clear();
                VerticalExpandingAnimator.animateView(this.mPending, false);
                loadList(false);
            }
        }
    }

    private ViewHolderComponent getViewHolder(ComponentEntry item) {
        View itemView = this.mList.getChildAt(item.getPosition() - this.mList.getFirstVisiblePosition());
        if (itemView != null) {
            return (ViewHolderComponent) itemView.getTag();
        }
        return null;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StoreEntry item = (StoreEntry) parent.getItemAtPosition(position);
        if (item != null) {
            switch (AnonymousClass10.$SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[item.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    onFolderClick(item, getClass());
                case TTSConst.TTSPARAGRAPH /*2*/:
                    onComponentClick(item, view);
                case TTSConst.TTSUNICODE /*3*/:
                    performHomeAction();
                    Bundle bundle = new Bundle();
                    bundle.putString(AbstractFragment.ARG_TITLE, item.getTitle());
                    ComponentEventsReceiver.unregisterEventsListener(this);
                    bundle.putString("source", "ComponentsFragment");
                    Fragments.add(getActivity(), MarketPlaceFragment.class, item.getTitle(), bundle);
                    new Handler().post(new C17146(item));
                default:
                    Log.w("ComponentsFragment", "Unsupported call of type: " + item.getType());
            }
        }
    }

    protected FragmentResultCallback getCallback() {
        return this.mResultCallback;
    }

    protected void onFolderClick(StoreEntry item, Class<? extends ComponentsFragment> cls) {
        Bundle bundle = new Bundle();
        bundle.putString(AbstractFragment.ARG_TITLE, item.getTitle());
        bundle.putString("map_list_id", item.getId());
        bundle.putInt(SettingsFragment.ARG_MENU, this.mMenuId);
        if (this.mFromFrwStart) {
            bundle.putBoolean("from_frw_continents", true);
        }
        ComponentEventsReceiver.unregisterEventsListener(this);
        Fragments.add(getActivity(), cls, item.getTitle(), bundle, true, getCallback());
    }

    protected void onComponentClick(StoreEntry item, View view) {
        if (item instanceof ComponentEntry) {
            ComponentEntry entry = (ComponentEntry) item;
            String format;
            if (!entry.isInstalled() && !entry.isDownloading() && !entry.isUpdateAvailable()) {
                if (this.mFreeSpace >= ComponentManager.nativeGetComponentSize(item.getId(), item.getIndex())) {
                    this.mCurrentDownload = entry;
                    if (!(this instanceof TTSVoiceComponentsFragment)) {
                        ((NaviNativeActivity) getActivity()).registerDownloadProgressNotification(new String[]{entry.getId()});
                    }
                    onInstall(this.mCurrentDownload.install(), view);
                    return;
                }
                format = ResourceManager.getCoreString(getActivity(), 2131165398).replace("%size%", "%s");
                new Builder(getActivity()).title(2131166003).formattedBody(format, ResourceManager.nativeFormatBytes(spaceNeeded)).negativeButton(2131165355, null).build().showAllowingStateLoss("not_enough_space_dialog");
                return;
            } else if (!(entry instanceof SystemTTSEntry)) {
                if (entry.isInstalled() || entry.isUpdateAvailable()) {
                    format = ResourceManager.getCoreString(getActivity(), 2131165493).replace("%item%", "%s");
                    new Builder(getActivity()).title(2131166003).formattedBody(format, entry.getTitle()).negativeButton(2131165354, null).positiveButton(2131165364, new C17157(entry, view)).build().showAllowingStateLoss("map_delete_confirmation");
                    return;
                } else if (entry.isDownloading()) {
                    uninstall(entry, view);
                    return;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        Log.w("ComponentsFragment", "Invalid StoreEntry type. Expected 'TYPE_COMPONENT', current type: " + item.getType());
    }

    private void onInstall(boolean success, View view) {
        ViewHolderComponent tag = (ViewHolderComponent) view.getTag();
        this.mCurrentDownload.setDownloading(tag);
        if (success) {
            onInstallSuccess();
        } else {
            view.postDelayed(new C17168(view, tag), 3000);
        }
    }

    protected void onInstallSuccess() {
    }

    protected void onInstallFailed(View view, ViewHolderComponent tag) {
        SToast.makeText(view.getContext(), 2131165397, 1).show();
        this.mCurrentDownload.stopDownloading(tag);
        this.mCurrentDownload.showInstallNotification(tag, false);
        this.mCurrentDownload.setInstalled(InstallStatus.IsUninstalled);
    }

    private void uninstall(ComponentEntry item, View view) {
        if (ComponentManager.nativeUninstall(item.getId(), item.getIndex())) {
            item.stopDownloading((ViewHolderComponent) view.getTag());
            item.setInstalled(InstallStatus.IsUninstalled);
        }
    }

    public void onStoreMessage(String message) {
        if (this.mPendingItems.isEmpty()) {
            new Builder(getActivity()).title(2131166003).body(message).positiveButton(2131165355, null).build().showAllowingStateLoss("store_message");
        }
    }

    private void calculateFreeSpace() {
        AsyncTaskHelper.execute(new C17179());
    }

    protected void addHeaderView(LayoutInflater inflater, ListView listView) {
    }
}
