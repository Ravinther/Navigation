package com.sygic.aura.route;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.network.ConnectionManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.ResourceManager.TrafficType;
import com.sygic.aura.route.RouteInstructionsAdapter.InstructionOverflowCallback;
import com.sygic.aura.route.data.InstructionsItem;
import com.sygic.aura.route.data.InstructionsItem.ERouteInstructionType;
import com.sygic.aura.views.font_specials.SImageView;
import loquendo.tts.engine.TTSConst;

public class TrafficAdapter extends ArrayAdapter<InstructionsItem> implements Filterable {
    private AsyncTask<Void, InstructionsItem, Integer> mAsynctask;
    private InstructionOverflowCallback mCallback;
    public boolean mHasFinishedLoading;

    /* renamed from: com.sygic.aura.route.TrafficAdapter.1 */
    class C15161 extends AsyncTask<Void, InstructionsItem, Integer> {
        private int mIntLastCount;

        C15161() {
            this.mIntLastCount = 0;
        }

        protected Integer doInBackground(Void... params) {
            while (!isCancelled()) {
                int count = RouteSummary.nativeGetRouteSegmentsCount();
                InstructionsItem[] items = new InstructionsItem[(count - this.mIntLastCount)];
                if (count > 0) {
                    if (count <= this.mIntLastCount) {
                        break;
                    }
                    for (int ind = this.mIntLastCount; ind < count; ind++) {
                        items[ind - this.mIntLastCount] = RouteSummary.nativeGetInstructionAt(ind);
                    }
                    publishProgress(items);
                    this.mIntLastCount = count;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onProgressUpdate(InstructionsItem... values) {
            int i = 0;
            TrafficAdapter.this.setNotifyOnChange(false);
            if (values != null) {
                int length = values.length;
                while (i < length) {
                    InstructionsItem item = values[i];
                    if (item != null && (item.getType() == ERouteInstructionType.TYPE_SINGLE_TRAFFIC || item.getType() == ERouteInstructionType.TYPE_SINGLE_DYNA_CHANGE || item.getType() == ERouteInstructionType.TYPE_SINGLE_TRAFFIC_USER)) {
                        TrafficAdapter.this.add(item);
                    }
                    i++;
                }
            }
            TrafficAdapter.this.notifyDataSetChanged();
        }

        protected void onPostExecute(Integer integer) {
            TrafficAdapter.this.setFinished();
            TrafficAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: com.sygic.aura.route.TrafficAdapter.2 */
    static /* synthetic */ class C15172 {
        static final /* synthetic */ int[] f1270x43567c2;

        static {
            f1270x43567c2 = new int[ERouteInstructionType.values().length];
            try {
                f1270x43567c2[ERouteInstructionType.TYPE_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1270x43567c2[ERouteInstructionType.TYPE_END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1270x43567c2[ERouteInstructionType.TYPE_SINGLE_TRAFFIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1270x43567c2[ERouteInstructionType.TYPE_SINGLE_DYNA_CHANGE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1270x43567c2[ERouteInstructionType.TYPE_SINGLE_TRAFFIC_USER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1270x43567c2[ERouteInstructionType.TYPE_WAYPOINT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private class ViewHolder {
        private final SImageView mOverflow;
        private final View mRouteInstructionSymbol;
        private final TextView[] mRouteInstructionSymbolControls;
        private final TextView mTxtInstructionDistance;
        private final TextView mTxtInstructionTraffic;
        private final TextView mTxtRouteInstruction;

        /* renamed from: com.sygic.aura.route.TrafficAdapter.ViewHolder.1 */
        class C15191 implements OnClickListener {
            final /* synthetic */ InstructionsItem val$instruction;

            /* renamed from: com.sygic.aura.route.TrafficAdapter.ViewHolder.1.1 */
            class C15181 implements OnMenuItemClickListener {
                C15181() {
                }

                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() != 2131624689) {
                        return false;
                    }
                    if (TrafficAdapter.this.mCallback != null) {
                        TrafficAdapter.this.mCallback.onAvoidInstruction(C15191.this.val$instruction.getIndex());
                    }
                    return true;
                }
            }

            C15191(InstructionsItem instructionsItem) {
                this.val$instruction = instructionsItem;
            }

            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TrafficAdapter.this.getContext(), v);
                popup.inflate(2131755027);
                MenuItem ignoreItem = popup.getMenu().findItem(2131624689);
                ignoreItem.setTitle(ResourceManager.getCoreString(ignoreItem.getTitle().toString()));
                popup.setOnMenuItemClickListener(new C15181());
                popup.show();
            }
        }

        ViewHolder(View convertView) {
            this.mRouteInstructionSymbol = convertView.findViewById(2131624587);
            this.mRouteInstructionSymbolControls = new TextView[]{(TextView) this.mRouteInstructionSymbol.findViewById(2131624590), (TextView) this.mRouteInstructionSymbol.findViewById(2131624589), (TextView) this.mRouteInstructionSymbol.findViewById(2131624588)};
            this.mTxtRouteInstruction = (TextView) convertView.findViewById(2131624591);
            this.mTxtInstructionDistance = (TextView) convertView.findViewById(2131624592);
            this.mTxtInstructionTraffic = (TextView) convertView.findViewById(2131624594);
            this.mOverflow = (SImageView) convertView.findViewById(2131624593);
        }

        private void setInstructionItem(InstructionsItem instruction, int position) {
            this.mRouteInstructionSymbolControls[0].setTextColor(TrafficAdapter.this.getContext().getResources().getColor(2131558435));
            this.mTxtInstructionTraffic.setBackgroundColor(TrafficAdapter.this.getContext().getResources().getColor(2131558432));
            for (TextView mRouteInstructionSymbolControl : this.mRouteInstructionSymbolControls) {
                mRouteInstructionSymbolControl.setText("");
            }
            switch (C15172.f1270x43567c2[instruction.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    this.mRouteInstructionSymbolControls[0].setText(TrafficAdapter.this.getContext().getString(2131166234));
                    this.mRouteInstructionSymbolControls[0].setTextColor(TrafficAdapter.this.getContext().getResources().getColor(2131558739));
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    this.mRouteInstructionSymbolControls[0].setText(TrafficAdapter.this.getContext().getString(2131166234));
                    this.mRouteInstructionSymbolControls[0].setTextColor(TrafficAdapter.this.getContext().getResources().getColor(2131558735));
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                case TTSConst.TTSXML /*4*/:
                case TTSConst.TTSEVT_TEXT /*5*/:
                    TrafficType trafficType = instruction.getTrafficType();
                    this.mRouteInstructionSymbolControls[0].setVisibility(0);
                    this.mRouteInstructionSymbolControls[0].setText(TrafficAdapter.this.getContext().getString(2131166154));
                    this.mRouteInstructionSymbolControls[0].setTextColor(ResourceManager.getTrafficColor(TrafficAdapter.this.getContext(), trafficType));
                    this.mTxtInstructionTraffic.setBackgroundColor(ResourceManager.getTrafficColor(TrafficAdapter.this.getContext(), trafficType));
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    this.mRouteInstructionSymbolControls[0].setText(TrafficAdapter.this.getContext().getString(2131166263));
                    break;
                default:
                    setRouteInstructionIcon(instruction);
                    break;
            }
            this.mTxtRouteInstruction.setText(instruction.getText());
            this.mTxtInstructionDistance.setText(instruction.getDistance());
            if (instruction.isAvoidable()) {
                this.mOverflow.setVisibility(0);
                this.mOverflow.setOnClickListener(new C15191(instruction));
                return;
            }
            this.mOverflow.setVisibility(8);
        }

        private void setRouteInstructionIcon(InstructionsItem instruction) {
            ResourceManager.drawMultiFontIcon(TrafficAdapter.this.getContext(), this.mRouteInstructionSymbolControls, instruction.getIconChars(), null);
        }
    }

    public TrafficAdapter(Context context) {
        super(context, 2130903269, 2131624591);
        this.mHasFinishedLoading = false;
    }

    public void loadTraffic() {
        if (hasLicence()) {
            clear();
            RouteSummary.nativeCleanResults();
            if (this.mAsynctask != null) {
                this.mAsynctask.cancel(true);
            }
            setNotifyOnChange(false);
            this.mHasFinishedLoading = false;
            this.mAsynctask = new C15161();
            AsyncTaskHelper.execute(this.mAsynctask);
        }
    }

    public boolean isEnabled(int position) {
        return false;
    }

    public void setOverflowCallback(InstructionOverflowCallback callback) {
        this.mCallback = callback;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, null, parent);
            convertView.setTag(new ViewHolder(convertView));
        }
        ((ViewHolder) convertView.getTag()).setInstructionItem((InstructionsItem) getItem(position), position);
        return convertView;
    }

    public boolean hasLicence() {
        return LicenseInfo.nativeHasTrafficLicense();
    }

    public int getImageResId() {
        if (ConnectionManager.nativeIsConnected()) {
            return hasLicence() ? 2131034307 : 2130838121;
        } else {
            return 2131034305;
        }
    }

    public int getTextResId() {
        if (ConnectionManager.nativeIsConnected()) {
            return hasLicence() ? 2131165636 : 2131165637;
        } else {
            return 2131165646;
        }
    }

    public void setFinished() {
        this.mHasFinishedLoading = true;
    }

    public boolean isFinished() {
        return this.mHasFinishedLoading;
    }
}
