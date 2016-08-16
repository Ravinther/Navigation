package com.sygic.aura.travelbook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.time.TimeManager;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem;
import com.sygic.aura.views.font_specials.STextView;
import java.util.ArrayList;

public class TravelbookAdapter extends ArrayAdapter<TravelbookTrackLogItem> {
    private final long DAY;
    private final long MONTH;
    private final long NOW;
    private final long WEEK;
    private final Mode mMode;
    private int mTimeInfoId;

    public enum Mode {
        MODE_HISTORY,
        MODE_LIKED
    }

    private class ViewHolder {
        private final STextView mTxtDistance;
        private final STextView mTxtEndAddress;
        private final STextView mTxtHeaderView;
        private final STextView mTxtStartAddress;
        private final STextView mTxtStartTime;

        ViewHolder(View convertView) {
            this.mTxtHeaderView = (STextView) convertView.findViewById(2131624159);
            this.mTxtStartAddress = (STextView) convertView.findViewById(2131624630);
            this.mTxtEndAddress = (STextView) convertView.findViewById(2131624632);
            this.mTxtDistance = (STextView) convertView.findViewById(2131624629);
            this.mTxtStartTime = (STextView) convertView.findViewById(2131624633);
        }

        private void updateView(TravelbookTrackLogItem item) {
            this.mTxtHeaderView.setText(item.getStartAddress());
            this.mTxtStartAddress.setText(item.getStartAddress());
            this.mTxtEndAddress.setText(item.getEndAddress());
            this.mTxtDistance.setText(item.getDistance());
            this.mTxtStartTime.setText(item.getStartTime());
        }
    }

    public TravelbookAdapter(Context context, ArrayList<TravelbookTrackLogItem> items, int position) {
        super(context, 2130903293, 2131624159, items);
        this.DAY = 86400;
        this.WEEK = 604800;
        this.MONTH = 2419200;
        this.NOW = TimeManager.nativeTimeGetCurrentTime();
        this.mTimeInfoId = 0;
        this.mMode = Mode.values()[position];
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
            convertView.setTag(new ViewHolder(convertView));
        }
        TravelbookTrackLogItem item = (TravelbookTrackLogItem) getItem(position);
        ((ViewHolder) convertView.getTag()).updateView(item);
        if (this.mMode.equals(Mode.MODE_HISTORY)) {
            boolean bUseHeader;
            STextView headerView = (STextView) convertView.findViewById(2131624159);
            long timeDiff = getTimeDiff(item);
            if (timeDiff <= 86400) {
                bUseHeader = position == 0;
                this.mTimeInfoId = 2131165946;
            } else if (timeDiff <= 604800) {
                bUseHeader = position == 0 || getTimeDiff((TravelbookTrackLogItem) getItem(position - 1)) <= 86400;
                this.mTimeInfoId = 2131165944;
            } else if (timeDiff <= 2419200) {
                bUseHeader = position == 0 || getTimeDiff((TravelbookTrackLogItem) getItem(position - 1)) <= 604800;
                this.mTimeInfoId = 2131165943;
            } else {
                bUseHeader = position != 0 && getTimeDiff((TravelbookTrackLogItem) getItem(position - 1)) <= 2419200;
                this.mTimeInfoId = 2131165945;
            }
            if (bUseHeader) {
                headerView.setCoreText(this.mTimeInfoId);
                headerView.setVisibility(0);
            } else {
                headerView.setVisibility(8);
            }
        }
        return convertView;
    }

    public long getTimeDiff(TravelbookTrackLogItem item) {
        return this.NOW - ((long) item.getTimeStamp());
    }

    public void deleteAtPosition(int position) {
        remove(getItem(position));
    }
}
