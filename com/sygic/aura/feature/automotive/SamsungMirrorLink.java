package com.sygic.aura.feature.automotive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import com.sygic.aura.SygicMain;
import java.util.ArrayList;

public class SamsungMirrorLink {
    private final BroadcastReceiver mReceiver;

    /* renamed from: com.sygic.aura.feature.automotive.SamsungMirrorLink.1 */
    class C12241 extends BroadcastReceiver {
        C12241() {
        }

        public void onReceive(Context context, Intent intent) {
            int i = 0;
            if ("com.sygic.incar.action.AUDIO_CONTROL".equals(intent.getAction())) {
                boolean mute = intent.getIntExtra("tts_mute", 0) == 1;
                if (!mute) {
                    i = 1;
                }
                SygicMain.nativePostCommand(16898, i);
                if (mute) {
                    SygicMain.getInstance().getFeature().getTtsFeature().stop();
                    SygicMain.getInstance().getFeature().getSoundFeature().stop(true);
                }
            } else if ("com.sygic.incar.action.REQ_DEST".equals(intent.getAction())) {
                NaviDestInfo destInfo = SamsungMirrorLink.this.getDestInfo();
                Intent intentDest = new Intent("com.sec.android.automotive.drivelink.action.SEND_DEST");
                intentDest.putExtra("dest", destInfo);
                intentDest.setPackage("com.sec.android.automotive.drivelink");
                SygicMain.getActivity().sendBroadcast(intentDest);
            } else if ("com.sygic.incar.action.REQ_DEST_RECENT_LIST".equals(intent.getAction())) {
                ArrayList<NaviDestInfo> naviRecentDestList = SamsungMirrorLink.this.getRecentListInfo();
                Intent intentRecentList = new Intent("com.sec.android.automotive.drivelink.action.SEND_DEST_RECENT_LIST");
                intentRecentList.putParcelableArrayListExtra("dest_recent_list", naviRecentDestList);
                intentRecentList.setPackage("com.sec.android.automotive.drivelink");
                SygicMain.getActivity().sendBroadcast(intentRecentList);
            } else if ("com.sygic.incar.action.CANCEL_NAVIGATION".equals(intent.getAction())) {
                SygicMain.nativePostCommand(16899, (short) 0, (short) 0);
            } else if ("com.sygic.incar.action.EXIT_REQ".equals(intent.getAction())) {
                SygicMain.getInstance().Quit();
            }
        }
    }

    private class NaviDestInfo implements Parcelable {
        public String mDestAddr;
        public String mDestName;
        public double mLat;
        public double mLng;

        private NaviDestInfo(String item) {
            this.mDestName = null;
            this.mDestAddr = null;
            this.mLat = 0.0d;
            this.mLng = 0.0d;
            String[] entry = item.split("\\|");
            if (entry.length == 4) {
                this.mDestName = entry[0];
                this.mDestAddr = entry[1];
                this.mLat = Double.valueOf(entry[2]).doubleValue();
                this.mLng = Double.valueOf(entry[3]).doubleValue();
            }
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mDestName);
            dest.writeString(this.mDestAddr);
            dest.writeDouble(this.mLat);
            dest.writeDouble(this.mLng);
        }

        public int describeContents() {
            return 0;
        }
    }

    public SamsungMirrorLink() {
        this.mReceiver = new C12241();
    }

    private ArrayList<NaviDestInfo> getRecentListInfo() {
        ArrayList<NaviDestInfo> list = new ArrayList();
        String history = null;
        for (String item : history.split("\\|\\|")) {
            list.add(new NaviDestInfo(item, null));
        }
        return list;
    }

    private NaviDestInfo getDestInfo() {
        return null;
    }

    private IntentFilter getCarModeFilter(String action) {
        IntentFilter filter = new IntentFilter(action);
        filter.addCategory("android.intent.category.CAR_MODE");
        return filter;
    }

    public void start() {
        Context context = SygicMain.getActivity().getApplicationContext();
        if (context != null) {
            context.registerReceiver(this.mReceiver, getCarModeFilter("com.sygic.incar.action.AUDIO_CONTROL"));
            context.registerReceiver(this.mReceiver, getCarModeFilter("com.sygic.incar.action.REQ_DEST"));
            context.registerReceiver(this.mReceiver, getCarModeFilter("com.sygic.incar.action.REQ_DEST_RECENT_LIST"));
            context.registerReceiver(this.mReceiver, getCarModeFilter("com.sygic.incar.action.CANCEL_NAVIGATION"));
            context.registerReceiver(this.mReceiver, getCarModeFilter("com.sygic.incar.action.EXIT_REQ"));
        }
    }

    public void stop() {
        SygicMain.getActivity().getApplicationContext().unregisterReceiver(this.mReceiver);
    }

    public void onAudio(boolean play) {
        Intent intent = new Intent("com.sec.android.automotive.drivelink.AUDIO_CONTROL");
        intent.setPackage("com.sec.android.automotive.drivelink");
        intent.putExtra("tts_mute", play ? 1 : 0);
        SygicMain.getActivity().sendBroadcast(intent);
    }
}
