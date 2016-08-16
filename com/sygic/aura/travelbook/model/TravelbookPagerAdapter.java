package com.sygic.aura.travelbook.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem;
import com.sygic.aura.travelbook.fragment.TripDetailFragment.TripDetailCallback;
import java.util.ArrayList;

public class TravelbookPagerAdapter extends FragmentPagerAdapter {
    private PagerTravelbookFragment mAllLogsFragment;
    private ArrayList<TravelbookTrackLogItem> mArrItems;
    private final TripDetailCallback mCallback;
    private PagerTravelbookFragment mLikedLogsFragment;

    public TravelbookPagerAdapter(FragmentManager fm, ArrayList<TravelbookTrackLogItem> arrItems, TripDetailCallback callback) {
        super(fm);
        this.mCallback = callback;
        this.mArrItems = arrItems;
    }

    public Fragment getItem(int position) {
        PagerTravelbookFragment f = PagerTravelbookFragment.newInstance(position, this.mArrItems, this.mCallback);
        if (position == 1) {
            this.mLikedLogsFragment = f;
        } else if (position == 0) {
            this.mAllLogsFragment = f;
        }
        return f;
    }

    public int getCount() {
        return 2;
    }

    public PagerTravelbookFragment getLikedLogsFragment() {
        return this.mLikedLogsFragment;
    }

    public PagerTravelbookFragment getAllLogsFragment() {
        return this.mAllLogsFragment;
    }
}
