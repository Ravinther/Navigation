package com.sygic.aura.settings.trial.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.EventReceivers.LicenseEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.LicenseListener;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.first_run.SwipeViewHelper;
import com.sygic.aura.store.fragment.MarketPlaceFragment;
import com.sygic.aura.views.font_specials.STextView;

public class PromotionFragment extends AbstractScreenFragment implements LicenseListener {
    private String mSource;

    /* renamed from: com.sygic.aura.settings.trial.fragments.PromotionFragment.1 */
    class C16751 implements OnClickListener {
        C16751() {
        }

        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(PromotionFragment.this.getActivity(), 2131165377));
            bundle.putString("source", "promotion fragment");
            Fragments.add(PromotionFragment.this.getActivity(), MarketPlaceFragment.class, "fragment_sygic_store_tag", bundle);
        }
    }

    /* renamed from: com.sygic.aura.settings.trial.fragments.PromotionFragment.2 */
    class C16762 implements OnClickListener {
        C16762() {
        }

        public void onClick(View v) {
            PromotionFragment.this.performHomeAction();
        }
    }

    /* renamed from: com.sygic.aura.settings.trial.fragments.PromotionFragment.3 */
    class C16773 extends SimpleOnPageChangeListener {
        C16773() {
        }

        public void onPageSelected(int position) {
            InfinarioAnalyticsLogger.getInstance(PromotionFragment.this.getActivity()).trackOfflinePromo(position, PromotionFragment.this.mSource);
        }
    }

    public PromotionFragment() {
        setWantsNavigationData(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mSource = bundle.getString("source");
        }
    }

    public void onAttach(Activity activity) {
        activity.setRequestedOrientation(1);
        super.onAttach(activity);
    }

    public void onDetach() {
        super.onDetach();
        getActivity().setRequestedOrientation(4);
        SettingsManager.setupRatingDlg((NaviNativeActivity) getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903123, container, false);
        ((Button) view.findViewById(2131624283)).setOnClickListener(new C16751());
        STextView notNow = (STextView) view.findViewById(2131624284);
        notNow.setOnClickListener(new C16762());
        SwipeViewHelper swipeHelper = new SwipeViewHelper(getActivity(), getChildFragmentManager(), view.findViewById(2131624205), 2131492906, 2131492907);
        swipeHelper.addOnPageChangeListener(new C16773());
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackOfflinePromo(swipeHelper.getPagerCurrentItem(), this.mSource);
        STextView title = (STextView) view.findViewById(2131624282);
        if (LicenseInfo.nativeIsTrialExpired()) {
            title.setCoreText(2131165981);
            notNow.setCoreText(2131165979);
        } else {
            title.setText(((String) title.getText()).replace("%days%", Integer.toString(LicenseInfo.nativeGetTrialDays())));
        }
        LicenseEventsReceiver.registerLicenseListener(this);
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        LicenseEventsReceiver.unregisterLicenseListener(this);
    }

    public void onLicenseUpdated() {
        if (!LicenseInfo.nativeIsTrial()) {
            performHomeAction();
        }
    }
}
