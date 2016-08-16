package com.sygic.aura.settings.first_run.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ViewSwitcher;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.RestoreFragmentResultCallback;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CustomDialogFragment;
import com.sygic.aura.helper.EventReceivers.ComponentEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.helper.interfaces.ComponentsListener;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.VoiceEntry;
import com.sygic.aura.settings.first_run.SwipeViewHelper;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.tracker.TrackerUtils;
import com.sygic.aura.views.font_specials.SEditText;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;
import java.util.ArrayList;

public class FirstRunWizardDownloadFragment extends AbstractScreenFragment implements BackPressedListener, ComponentsListener {
    private String mDownloadState;
    private long mDownloadTimestampStart;
    private String mEmail;
    private final SimpleArrayMap<String, Long> mInstallIds;
    private String[] mInstallIdsArray;
    private int mInstalled;
    private int mLogValue;
    private ProgressBar mProgress;
    private Button mProgressText;
    private View mRestoreView;
    private SwipeViewHelper mSwipeViewHelper;
    private ViewSwitcher mSwitcher;
    private long mTotalSize;

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.1 */
    class C16311 extends AsyncTask<Context, Void, Void> {
        C16311() {
        }

        protected Void doInBackground(Context... params) {
            Context context = params[0];
            VoiceEntry voiceEntry = SettingsManager.nativeGetSelectedVoice();
            Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            if (voiceEntry != null) {
                editor.putString(context.getString(2131166728), voiceEntry.getLanguage());
                editor.putInt(context.getString(2131166729), voiceEntry.hashCode());
            }
            String lang = SettingsManager.nativeGetSelectedLanguage();
            if (lang != null) {
                editor.putString(context.getString(2131166722), lang);
                editor.putString(context.getString(2131166723), SettingsManager.nativeGetSelectedLanguageName(lang));
            }
            editor.apply();
            SettingsManager.nativeSetFirstRun(false);
            SettingsManager.nativeFlushSettings();
            return null;
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.2 */
    class C16322 implements OnEditorActionListener {
        final /* synthetic */ SEditText val$email;
        final /* synthetic */ Button val$next;

        C16322(SEditText sEditText, Button button) {
            this.val$email = sEditText;
            this.val$next = button;
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6 || !FirstRunWizardDownloadFragment.this.checkEmail(this.val$email)) {
                return false;
            }
            NaviNativeActivity.hideKeyboard(v.getWindowToken());
            this.val$next.performClick();
            return true;
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.3 */
    class C16333 implements OnClickListener {
        final /* synthetic */ SEditText val$email;

        C16333(SEditText sEditText) {
            this.val$email = sEditText;
        }

        public void onClick(View view) {
            if (FirstRunWizardDownloadFragment.this.checkEmail(this.val$email)) {
                if (!TextUtils.isEmpty(FirstRunWizardDownloadFragment.this.mEmail)) {
                    SettingsManager.nativeSetUserMail(FirstRunWizardDownloadFragment.this.mEmail);
                }
                FirstRunWizardDownloadFragment.this.showNext(true);
            }
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.4 */
    class C16344 implements OnClickListener {
        C16344() {
        }

        public void onClick(View view) {
            FirstRunWizardDownloadFragment.this.showNext(false);
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.5 */
    class C16355 implements OnClickListener {
        C16355() {
        }

        public void onClick(View v) {
            Bundle logParams = new Bundle();
            logParams.putString("screenName", "FRW - Done (exit to map)");
            SygicAnalyticsLogger.logEvent(FirstRunWizardDownloadFragment.this.getActivity(), EventType.FRW, logParams);
            InfinarioAnalyticsLogger.getInstance(FirstRunWizardDownloadFragment.this.getActivity()).trackMapView();
            FirstRunWizardDownloadFragment.this.finish();
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.6 */
    class C16376 implements OnClickListener {

        /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.6.1 */
        class C16361 implements RestoreFragmentResultCallback {
            C16361() {
            }

            public void onRestoreFragmentFinished() {
                FirstRunWizardDownloadFragment.this.finish();
            }
        }

        C16376() {
        }

        public void onClick(View view) {
            Bundle logParams = new Bundle();
            logParams.putString("screenName", "FRW - Done (exit to restore)");
            SygicAnalyticsLogger.logEvent(FirstRunWizardDownloadFragment.this.getActivity(), EventType.FRW, logParams);
            Bundle bundle = new Bundle();
            String title = ResourceManager.getCoreString(FirstRunWizardDownloadFragment.this.getActivity(), 2131165436);
            bundle.putString(AbstractFragment.ARG_TITLE, title);
            Fragments.add(FirstRunWizardDownloadFragment.this.getActivity(), FirstRunWizardActivateFragment.class, title, bundle, true, new C16361());
        }
    }

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardDownloadFragment.7 */
    class C16387 extends SimpleOnPageChangeListener {
        C16387() {
        }

        public void onPageSelected(int position) {
            InfinarioAnalyticsLogger.getInstance(FirstRunWizardDownloadFragment.this.getActivity()).trackDownloadingScreen(FirstRunWizardDownloadFragment.this.mTotalSize, FirstRunWizardDownloadFragment.this.mInstallIdsArray, FirstRunWizardDownloadFragment.this.mProgress.getProgress(), position);
        }
    }

    public FirstRunWizardDownloadFragment() {
        this.mInstallIds = new SimpleArrayMap();
        this.mInstalled = 0;
        this.mLogValue = 0;
        this.mTotalSize = 0;
        setWantsNavigationData(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle logParams = new Bundle();
        logParams.putString("screenName", "FRW - Email");
        SygicAnalyticsLogger.logEvent(getActivity(), EventType.FRW, logParams);
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwEmailShown();
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mTotalSize = bundle.getLong("total_size");
            this.mInstallIdsArray = bundle.getStringArray("ids_install");
            if (this.mInstallIdsArray != null) {
                if (this.mInstallIdsArray.length >= 0) {
                    this.mDownloadTimestampStart = System.currentTimeMillis();
                    InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwMapDownloadStarted(this.mInstallIdsArray, this.mTotalSize);
                }
                for (String id : this.mInstallIdsArray) {
                    long progressSize = 0;
                    long[] sizes = ComponentManager.nativeGetDownloadItemStatus(id);
                    if (sizes != null) {
                        progressSize = sizes[0];
                    }
                    this.mInstallIds.put(id, Long.valueOf(progressSize));
                }
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ComponentEventsReceiver.unregisterEventsListener(this);
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        AsyncTaskHelper.execute(new C16311(), getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mSwitcher = (ViewSwitcher) inflater.inflate(2130903104, container, false);
        View screen1 = this.mSwitcher.getChildAt(0);
        STextView skip = (STextView) screen1.findViewById(2131624204);
        Button next = (Button) screen1.findViewById(2131624203);
        SEditText email = (SEditText) screen1.findViewById(2131624202);
        email.requestFocus();
        email.setOnEditorActionListener(new C16322(email, next));
        next.setOnClickListener(new C16333(email));
        skip.setOnClickListener(new C16344());
        View screen2 = this.mSwitcher.getChildAt(1);
        this.mProgress = (ProgressBar) screen2.findViewById(C1799R.id.progress);
        this.mProgressText = (Button) screen2.findViewById(2131624206);
        this.mRestoreView = screen2.findViewById(2131624207);
        this.mProgressText.setOnClickListener(new C16355());
        this.mRestoreView.setOnClickListener(new C16376());
        this.mSwipeViewHelper = new SwipeViewHelper(getActivity(), getChildFragmentManager(), screen2.findViewById(2131624205), 2131492867, 2131492868);
        this.mSwipeViewHelper.addOnPageChangeListener(new C16387());
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        ComponentEventsReceiver.registerEventsListener(this);
        if (this.mInstallIds.isEmpty()) {
            setDone();
            this.mDownloadState = "skipped";
        } else {
            this.mDownloadState = "in progress";
        }
        return this.mSwitcher;
    }

    public void onDetach() {
        super.onDetach();
        getActivity().setRequestedOrientation(-1);
    }

    private void showNext(boolean emailEntered) {
        this.mSwitcher.showNext();
        Bundle logParams = new Bundle();
        logParams.putString("screenName", "FRW - Promotion");
        SygicAnalyticsLogger.logEvent(getActivity(), EventType.FRW, logParams);
        InfinarioAnalyticsLogger infinarioLogger = InfinarioAnalyticsLogger.getInstance(getActivity());
        infinarioLogger.trackFrwEmailAction(emailEntered);
        if (emailEntered) {
            infinarioLogger.update(this.mEmail);
        }
        infinarioLogger.trackDownloadingScreen(this.mTotalSize, this.mInstallIdsArray, this.mProgress.getProgress(), this.mSwipeViewHelper.getPagerCurrentItem());
    }

    private void showPrevious() {
        this.mSwitcher.showPrevious();
        Bundle logParams = new Bundle();
        logParams.putString("screenName", "FRW - Email");
        SygicAnalyticsLogger.logEvent(getActivity(), EventType.FRW, logParams);
    }

    private boolean checkEmail(SEditText email) {
        String strEmail = email.getText().toString();
        if (strEmail == null) {
            return false;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            this.mEmail = strEmail;
            return true;
        }
        email.setError(ResourceManager.getCoreString(getActivity(), 2131165456));
        this.mEmail = null;
        return false;
    }

    private void finish() {
        FragmentActivity activity = getActivity();
        getFragmentManager().beginTransaction().remove(this).commit();
        SettingsManager.nativeSetFirstRun(false);
        SettingsManager.nativeCheckLicence();
        SettingsManager.nativeFlushSettings();
        ((NaviNativeActivity) activity).checkGPSEnabled();
        LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent("com.sygic.aura.ACTION_FRW_FINISHED"));
    }

    public boolean onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            return false;
        }
        if (this.mSwitcher.getDisplayedChild() > 0) {
            showPrevious();
            return true;
        }
        CustomDialogFragment.showExitDialog(getActivity());
        return true;
    }

    public void onInstallFinished(String id) {
        int i = this.mInstalled + 1;
        this.mInstalled = i;
        if (i == this.mInstallIds.size()) {
            setDone();
            this.mDownloadState = "done";
            InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwMapDownloadFinished(this.mInstallIdsArray, this.mTotalSize, this.mDownloadTimestampStart);
        }
    }

    private void setDone() {
        this.mProgress.setProgress(100);
        this.mProgressText.setText(ResourceManager.getCoreString(getActivity(), 2131165447));
        this.mProgressText.setEnabled(true);
        this.mRestoreView.setVisibility(0);
    }

    public void onUninstallFinished(String id) {
        Log.w("FirstRunWizardDownloadFragment", "Uninstalling in FRW?!");
    }

    public void onDownloadProgressUpdated(String id, Short progress, Long total, Long downloaded) {
        if (!downloaded.equals(this.mInstallIds.put(id, downloaded))) {
            double actualProgressSize = 0.0d;
            for (int i = 0; i < this.mInstallIds.size(); i++) {
                actualProgressSize += (double) ((Long) this.mInstallIds.valueAt(i)).longValue();
            }
            int totalProgress = (int) Math.round((actualProgressSize / ((double) this.mTotalSize)) * 100.0d);
            this.mProgress.setProgress(totalProgress);
            int logValue = totalProgress / 10;
            if (this.mLogValue < logValue) {
                this.mLogValue = logValue;
                Bundle logParams = new Bundle();
                logParams.putString("category", "FRW - Downloading map");
                logParams.putString("eventName", Integer.toString(Math.min(this.mLogValue, 20) * 10) + " %");
                logParams.putString("label", TrackerUtils.toSizeBucket(this.mTotalSize));
                SygicAnalyticsLogger.logEvent(getActivity(), EventType.FRW, logParams);
            }
        }
    }

    public void onInstallWaiting(String id) {
    }

    public void onListLoaded(ArrayList<StoreEntry> arrayList, Boolean isUpdateRequired) {
    }

    public void onStoreMessage(String message) {
    }

    public void onConnectionChanged(Boolean connected) {
    }
}
