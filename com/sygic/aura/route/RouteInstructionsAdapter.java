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
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.ResourceManager.TrafficType;
import com.sygic.aura.route.data.InstructionsItem;
import com.sygic.aura.route.data.InstructionsItem.ERouteInstructionType;
import com.sygic.aura.views.font_specials.SImageView;
import loquendo.tts.engine.TTSConst;

public class RouteInstructionsAdapter extends ArrayAdapter<InstructionsItem> {
    private AsyncTask<Void, InstructionsItem, Integer> mAsynctask;
    private InstructionOverflowCallback mCallback;

    /* renamed from: com.sygic.aura.route.RouteInstructionsAdapter.1 */
    class C14941 extends AsyncTask<Void, InstructionsItem, Integer> {
        private int mIntLastCount;

        C14941() {
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
            RouteInstructionsAdapter.this.setNotifyOnChange(false);
            if (values != null) {
                int length = values.length;
                while (i < length) {
                    InstructionsItem item = values[i];
                    if (item != null) {
                        RouteInstructionsAdapter.this.add(item);
                    }
                    i++;
                }
            }
            RouteInstructionsAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: com.sygic.aura.route.RouteInstructionsAdapter.2 */
    static /* synthetic */ class C14952 {
        static final /* synthetic */ int[] f1269x43567c2;

        static {
            f1269x43567c2 = new int[ERouteInstructionType.values().length];
            try {
                f1269x43567c2[ERouteInstructionType.TYPE_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1269x43567c2[ERouteInstructionType.TYPE_END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1269x43567c2[ERouteInstructionType.TYPE_SINGLE_TRAFFIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1269x43567c2[ERouteInstructionType.TYPE_SINGLE_DYNA_CHANGE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1269x43567c2[ERouteInstructionType.TYPE_SINGLE_TRAFFIC_USER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1269x43567c2[ERouteInstructionType.TYPE_WAYPOINT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public interface InstructionOverflowCallback {
        void onAvoidInstruction(int i);
    }

    private class ViewHolder {
        private final SImageView mOverflow;
        private final View mRouteInstructionSymbol;
        private final TextView[] mRouteInstructionSymbolControls;
        private final TextView mTxtInstructionDistance;
        private final TextView mTxtInstructionTraffic;
        private final TextView mTxtRouteInstruction;

        /* renamed from: com.sygic.aura.route.RouteInstructionsAdapter.ViewHolder.1 */
        class C14971 implements OnClickListener {
            final /* synthetic */ InstructionsItem val$instruction;

            /* renamed from: com.sygic.aura.route.RouteInstructionsAdapter.ViewHolder.1.1 */
            class C14961 implements OnMenuItemClickListener {
                C14961() {
                }

                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() != 2131624687) {
                        return false;
                    }
                    if (RouteInstructionsAdapter.this.mCallback != null) {
                        RouteInstructionsAdapter.this.mCallback.onAvoidInstruction(C14971.this.val$instruction.getIndex());
                    }
                    return true;
                }
            }

            C14971(InstructionsItem instructionsItem) {
                this.val$instruction = instructionsItem;
            }

            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(RouteInstructionsAdapter.this.getContext(), v);
                popup.inflate(2131755024);
                MenuItem ignoreItem = popup.getMenu().findItem(2131624687);
                ignoreItem.setTitle(ResourceManager.getCoreString(ignoreItem.getTitle().toString()));
                popup.setOnMenuItemClickListener(new C14961());
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
            this.mRouteInstructionSymbolControls[0].setTextColor(RouteInstructionsAdapter.this.getContext().getResources().getColor(2131558435));
            this.mTxtInstructionTraffic.setBackgroundColor(RouteInstructionsAdapter.this.getContext().getResources().getColor(2131558432));
            for (TextView text : this.mRouteInstructionSymbolControls) {
                text.setText("");
            }
            switch (C14952.f1269x43567c2[instruction.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    this.mRouteInstructionSymbolControls[0].setText(RouteInstructionsAdapter.this.getContext().getString(2131166234));
                    this.mRouteInstructionSymbolControls[0].setTextColor(RouteInstructionsAdapter.this.getContext().getResources().getColor(2131558739));
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    this.mRouteInstructionSymbolControls[0].setText(RouteInstructionsAdapter.this.getContext().getString(2131166234));
                    this.mRouteInstructionSymbolControls[0].setTextColor(RouteInstructionsAdapter.this.getContext().getResources().getColor(2131558735));
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                case TTSConst.TTSXML /*4*/:
                case TTSConst.TTSEVT_TEXT /*5*/:
                    TrafficType trafficType = instruction.getTrafficType();
                    this.mRouteInstructionSymbolControls[0].setVisibility(0);
                    this.mRouteInstructionSymbolControls[0].setText(RouteInstructionsAdapter.this.getContext().getString(2131166154));
                    this.mRouteInstructionSymbolControls[0].setTextColor(ResourceManager.getTrafficColor(RouteInstructionsAdapter.this.getContext(), trafficType));
                    this.mTxtInstructionTraffic.setBackgroundColor(ResourceManager.getTrafficColor(RouteInstructionsAdapter.this.getContext(), trafficType));
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    this.mRouteInstructionSymbolControls[0].setText(RouteInstructionsAdapter.this.getContext().getString(2131166263));
                    break;
                default:
                    setRouteInstructionIcon(instruction);
                    break;
            }
            this.mTxtRouteInstruction.setText(instruction.getText());
            this.mTxtInstructionDistance.setText(instruction.getDistance());
            if (instruction.isAvoidable()) {
                this.mOverflow.setVisibility(0);
                this.mOverflow.setOnClickListener(new C14971(instruction));
                return;
            }
            this.mOverflow.setVisibility(8);
        }

        private void setRouteInstructionIcon(InstructionsItem instruction) {
            ResourceManager.drawMultiFontIcon(RouteInstructionsAdapter.this.getContext(), this.mRouteInstructionSymbolControls, instruction.getIconChars(), null);
        }
    }

    public RouteInstructionsAdapter(Context context) {
        super(context, 2130903269, 2131624591);
    }

    public void loadRouteInstructions() {
        clear();
        RouteSummary.nativeCleanResults();
        if (this.mAsynctask != null) {
            this.mAsynctask.cancel(true);
        }
        this.mAsynctask = new C14941();
        AsyncTaskHelper.execute(this.mAsynctask);
    }

    public boolean isEnabled(int position) {
        return false;
    }

    public void setOverflowCallback(InstructionOverflowCallback callback) {
        this.mCallback = callback;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
            convertView.setTag(new ViewHolder(convertView));
        }
        ((ViewHolder) convertView.getTag()).setInstructionItem((InstructionsItem) getItem(position), position);
        return convertView;
    }
}
