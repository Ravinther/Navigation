package com.sygic.aura.route;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.network.ConnectionManager;
import com.sygic.aura.route.data.SpeedCamItem;
import java.util.ArrayList;

public class SpeedCamsAdapter extends ArrayAdapter<SpeedCamItem> implements Filterable {
    private boolean mHasFinishedLoading;

    private class ViewHolder {
        private final TextView mSpeedCamIcon;
        private final TextView mTxtSpeedCamDescription;
        private final TextView mTxtSpeedCamSpeed;

        ViewHolder(View convertView) {
            this.mSpeedCamIcon = (TextView) convertView.findViewById(2131624602);
            this.mTxtSpeedCamDescription = (TextView) convertView.findViewById(2131624603);
            this.mTxtSpeedCamSpeed = (TextView) convertView.findViewById(2131624604);
        }

        private void setCameraItem(SpeedCamItem item, int position) {
            this.mSpeedCamIcon.setTextColor(SpeedCamsAdapter.this.getContext().getResources().getColor(2131558435));
            this.mSpeedCamIcon.setText(SpeedCamItem.getIconFromSubType(item.getCamType().ordinal()));
            this.mTxtSpeedCamDescription.setText(item.getCamDescription());
            this.mTxtSpeedCamSpeed.setText(item.getCamSpeed());
        }
    }

    public SpeedCamsAdapter(Context context) {
        super(context, 2130903286, 2131624603);
        this.mHasFinishedLoading = false;
    }

    public boolean isEnabled(int position) {
        return false;
    }

    public void loadCams(ArrayList<SpeedCamItem> items) {
        this.mHasFinishedLoading = false;
        clear();
        addAll(items);
        setFinished();
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, null, parent);
            convertView.setTag(new ViewHolder(convertView));
        }
        ((ViewHolder) convertView.getTag()).setCameraItem((SpeedCamItem) getItem(position), position);
        return convertView;
    }

    public boolean hasLicence() {
        return LicenseInfo.nativeHasSpeedcamLicense();
    }

    public void setFinished() {
        this.mHasFinishedLoading = true;
    }

    public boolean isFinished() {
        return this.mHasFinishedLoading;
    }

    public int getImageResId() {
        if (ConnectionManager.nativeIsConnected()) {
            return hasLicence() ? 2131034306 : 2130838120;
        } else {
            return 2131034305;
        }
    }

    public int getTextResId() {
        if (ConnectionManager.nativeIsConnected()) {
            return hasLicence() ? 2131165634 : 2131165635;
        } else {
            return 2131165646;
        }
    }
}
