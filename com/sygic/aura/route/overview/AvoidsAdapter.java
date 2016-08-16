package com.sygic.aura.route.overview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.data.RouteAvoidsData;
import com.sygic.aura.route.data.RouteNavigateData;

public class AvoidsAdapter extends BaseAdapter {
    private boolean mEnabled;
    private final LayoutInflater mInflater;
    private final RouteNavigateData mRouteNaviData;

    private static class ViewHolder implements OnClickListener, OnCheckedChangeListener {
        private static final int AVOIDS_COUNT;
        private static final int[] ROUTE_AVOIDS_IDS;
        private final AvoidsAdapter mAdapter;
        private final View[] mAvoids;
        private int mCountryIndex;
        private RouteNavigateData mRouteNavigateData;
        private final CompoundButton[] mSwitches;
        private final TextView mTxtName;

        static {
            ROUTE_AVOIDS_IDS = new int[]{2131624375, 2131624377, 2131624378, 2131624379};
            AVOIDS_COUNT = ROUTE_AVOIDS_IDS.length;
        }

        public ViewHolder(View view, RouteNavigateData routeNavigateData, AvoidsAdapter adapter) {
            this.mAvoids = new View[AVOIDS_COUNT];
            this.mSwitches = new CompoundButton[AVOIDS_COUNT];
            this.mAdapter = adapter;
            this.mRouteNavigateData = routeNavigateData;
            this.mTxtName = (TextView) view.findViewById(2131624374);
            for (int i = 0; i < AVOIDS_COUNT; i++) {
                this.mAvoids[i] = view.findViewById(ROUTE_AVOIDS_IDS[i]);
                this.mSwitches[i] = (CompoundButton) this.mAvoids[i].findViewById(2131624376);
                this.mAvoids[i].setTag(Integer.valueOf(i));
                this.mSwitches[i].setTag(Integer.valueOf(i));
            }
        }

        public void update(RouteAvoidsData data, int countryIndex) {
            this.mCountryIndex = countryIndex;
            this.mTxtName.setText(data.getCountryName());
            boolean[] avoidsArray = data.getAvoidsArray();
            for (int ind = 0; ind < AVOIDS_COUNT; ind++) {
                boolean isOnRouteOrAvoided;
                boolean z;
                boolean alreadyAvoided = avoidsArray[AVOIDS_COUNT + ind];
                if (avoidsArray[ind] || alreadyAvoided) {
                    isOnRouteOrAvoided = true;
                } else {
                    isOnRouteOrAvoided = false;
                }
                if (isOnRouteOrAvoided && this.mAdapter.mEnabled) {
                    z = true;
                } else {
                    z = false;
                }
                bulkSetEnabled(ind, z);
                if (this.mAdapter.mEnabled) {
                    this.mSwitches[ind].setOnCheckedChangeListener(null);
                    CompoundButton compoundButton = this.mSwitches[ind];
                    if (avoidsArray[ind] || !alreadyAvoided) {
                        z = true;
                    } else {
                        z = false;
                    }
                    compoundButton.setChecked(z);
                    ResourceManager.makeControlVisible(this.mSwitches[ind], isOnRouteOrAvoided, true);
                }
                this.mAvoids[ind].setOnClickListener(this);
                this.mSwitches[ind].setOnCheckedChangeListener(this);
            }
        }

        public void onClick(View view) {
            if (!RouteManager.nativeIsComputing()) {
                view.setOnClickListener(null);
                this.mSwitches[((Integer) view.getTag()).intValue()].toggle();
            }
        }

        public void onCheckedChanged(CompoundButton avoidSwitch, boolean isChecked) {
            if (!RouteManager.nativeIsComputing()) {
                this.mAdapter.mEnabled = false;
                this.mAdapter.notifyDataSetChanged();
                avoidSwitch.setOnCheckedChangeListener(null);
                onAvoidChanged(((Integer) avoidSwitch.getTag()).intValue());
            }
        }

        private void onAvoidChanged(int avoidIndex) {
            if (avoidIndex >= 0 && avoidIndex < AVOIDS_COUNT) {
                RouteAvoidsData avoidsData = this.mRouteNavigateData.getCountryAvoids(this.mCountryIndex);
                if (avoidsData != null) {
                    this.mRouteNavigateData.onAvoidChanged(this.mCountryIndex, avoidsData.getCountryIso(), avoidIndex, this.mSwitches[avoidIndex].isChecked());
                    RouteManager.nativeSetCountryRouteAvoids(this.mRouteNavigateData);
                }
            }
        }

        private void bulkSetEnabled(int index, boolean state) {
            this.mAvoids[index].setEnabled(state);
            ViewGroup viewGroup = this.mAvoids[index];
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                viewGroup.getChildAt(i).setEnabled(state);
            }
        }
    }

    public AvoidsAdapter(Context context, RouteNavigateData routeNavigateData) {
        this.mEnabled = true;
        this.mRouteNaviData = routeNavigateData;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.mRouteNaviData.getCountryAvoidsCount();
    }

    public Object getItem(int position) {
        return this.mRouteNaviData.getCountryAvoids(position);
    }

    public long getItemId(int position) {
        return (long) getItem(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = this.mInflater.inflate(2130903158, parent, false);
            holder = new ViewHolder(view, this.mRouteNaviData, this);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.update(this.mRouteNaviData.getCountryAvoids(position), position);
        return view;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }
}
