package com.sygic.aura.incidents.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.incidents.IncidentItemsHelper;
import com.sygic.aura.incidents.IncidentItemsHelper.IncidentItemType;
import com.sygic.aura.incidents.IncidentItemsHelper.ReportResult;
import com.sygic.aura.incidents.interfaces.ReportIncidentCallback;
import com.sygic.aura.incidents.interfaces.ReportIncidentTimeOutCallback;
import com.sygic.aura.incidents.views.IncidentView;
import com.sygic.aura.map.MapControlsManager;

public class ReportIncidentFragment extends AbstractScreenFragment implements OnClickListener, ReportIncidentCallback {
    private final int DISMISS_TIMEOUT;
    private final Runnable mDismissRunnable;
    private Handler mHandler;

    /* renamed from: com.sygic.aura.incidents.fragment.ReportIncidentFragment.1 */
    class C12851 implements Runnable {
        C12851() {
        }

        public void run() {
            if (ReportIncidentFragment.this.isAdded()) {
                ReportIncidentFragment.this.dismissFragment();
            }
        }
    }

    public ReportIncidentFragment() {
        this.DISMISS_TIMEOUT = 6000;
        this.mDismissRunnable = new C12851();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IncidentView mIncidentWrapper = (IncidentView) inflater.inflate(2130903145, container, false);
        mIncidentWrapper.setIncidentCallback(this);
        mIncidentWrapper.setOnClickListener(this);
        return mIncidentWrapper;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(this.mDismissRunnable, 6000);
    }

    public void dismissFragment() {
        performHomeAction();
    }

    public void reportIncident(IncidentItemType type) {
        ReportResult result = IncidentItemsHelper.canReportType(type);
        if (result.canReport()) {
            MapControlsManager.nativeReportIncident(type, (LongPosition) getArguments().getParcelable("position"));
            SToast.makeText(getActivity(), IncidentItemsHelper.getReportMessage(type), 0).show();
            dismissFragment();
            return;
        }
        SToast.makeText(getActivity(), result.reason(), 0).show();
    }

    public void onClick(View v) {
        dismissFragment();
    }

    public void onStop() {
        super.onStop();
        if (!isRemoving()) {
            getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        }
        this.mHandler.removeCallbacks(this.mDismissRunnable);
        ReportIncidentTimeOutCallback timeOutCallBack = this.mResultCallback;
        if (timeOutCallBack != null) {
            timeOutCallBack.timeoutFinished();
        }
    }
}
