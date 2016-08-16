package com.sygic.aura.travelbook.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.GuiUtils;
import com.sygic.aura.travelbook.TravelBookManager;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem;
import com.sygic.aura.travelbook.fragment.TripDetailFragment.TripDetailCallback;
import com.sygic.aura.travelbook.model.PagerTravelbookFragment;
import com.sygic.aura.travelbook.model.TravelbookPagerAdapter;
import com.sygic.aura.views.font_specials.SToolbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TravelbookFragment extends AbstractScreenFragment {
    private TravelbookPagerAdapter mTravelbookPagerAdapter;
    private TripDetailCallback mTripDetailCallback;

    /* renamed from: com.sygic.aura.travelbook.fragment.TravelbookFragment.1 */
    class C17631 extends SimpleOnPageChangeListener {
        final /* synthetic */ View val$view;

        C17631(View view) {
            this.val$view = view;
        }

        public void onPageSelected(int position) {
            NaviNativeActivity.hideKeyboard(this.val$view.getWindowToken());
        }
    }

    /* renamed from: com.sygic.aura.travelbook.fragment.TravelbookFragment.2 */
    class C17652 implements TripDetailCallback {

        /* renamed from: com.sygic.aura.travelbook.fragment.TravelbookFragment.2.1 */
        class C17641 implements Comparator<TravelbookTrackLogItem> {
            C17641() {
            }

            public int compare(TravelbookTrackLogItem lLogItem, TravelbookTrackLogItem rLogItem) {
                return rLogItem.getTimeStamp() - lLogItem.getTimeStamp();
            }
        }

        C17652() {
        }

        public void onTripLogFavouriteChanged() {
            TravelbookTrackLogItem[] logs = TravelBookManager.nativeGetTracksLogs();
            ArrayList<TravelbookTrackLogItem> allLogsList = new ArrayList();
            ArrayList<TravelbookTrackLogItem> likedLogsList = new ArrayList();
            for (TravelbookTrackLogItem log : logs) {
                if (log.isFavourite()) {
                    likedLogsList.add(log);
                }
                allLogsList.add(log);
            }
            Collections.sort(allLogsList, new C17641());
            PagerTravelbookFragment allFragment = TravelbookFragment.this.mTravelbookPagerAdapter.getAllLogsFragment();
            if (allFragment != null) {
                allFragment.setLogs(allLogsList);
            }
            PagerTravelbookFragment likedFragment = TravelbookFragment.this.mTravelbookPagerAdapter.getLikedLogsFragment();
            if (likedFragment != null) {
                likedFragment.setLogs(likedLogsList);
            }
        }
    }

    public TravelbookFragment() {
        this.mTripDetailCallback = new C17652();
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        int titleRes = getArguments().getInt("action_title_mode_id");
        if (titleRes == 0) {
            titleRes = 2131165324;
        }
        toolbar.setTitle(titleRes);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mTravelbookPagerAdapter = new TravelbookPagerAdapter(getChildFragmentManager(), new ArrayList(Arrays.asList(TravelBookManager.nativeGetTracksLogs())), this.mTripDetailCallback);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903136, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(2131624309);
        viewPager.setAdapter(this.mTravelbookPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new C17631(view));
        GuiUtils.setupTabsWithViewPager((TabLayout) view.findViewById(2131624308), viewPager, getActivity(), 2131165969, 2131165970);
        return view;
    }
}
