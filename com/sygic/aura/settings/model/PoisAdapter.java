package com.sygic.aura.settings.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.helper.LocalizedStringComparator;
import com.sygic.aura.settings.data.PoiCategoryEntry;
import com.sygic.aura.settings.data.PoiEntry;
import com.sygic.aura.settings.data.PoiSettingsManager;
import com.sygic.aura.settings.data.PoiSettingsManager.EPoiType;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.base.C1799R;
import java.util.Arrays;
import java.util.List;

public class PoisAdapter extends ArrayAdapter<PoiEntry> {
    public static AdapterMode mAdapterMode;
    private String mAnalyticsName;
    final OnPoiItemClickedListener mCallback;
    final EPoiType mPoiType;

    public interface OnPoiItemClickedListener {
        void onPoiItemClicked(boolean z);
    }

    public enum AdapterMode {
        GROUP,
        CATEGORY
    }

    public class ViewHolder {
        private final CompoundButton enabled;
        private PoiEntry entry;
        final OnCheckedChangeListener mListener;
        private final TextView title;

        /* renamed from: com.sygic.aura.settings.model.PoisAdapter.ViewHolder.1 */
        class C16591 implements OnCheckedChangeListener {
            C16591() {
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ViewHolder.this.entry instanceof PoiCategoryEntry) {
                    PoiSettingsManager.nativeSetShowStatus(ViewHolder.this.entry.getID(), PoisAdapter.this.mPoiType, isChecked, ((PoiCategoryEntry) ViewHolder.this.entry).isCustom());
                } else {
                    PoiSettingsManager.nativeSetShowStatusGroup(ViewHolder.this.entry.getID(), PoisAdapter.this.mPoiType, isChecked);
                }
                PoisAdapter.this.mCallback.onPoiItemClicked(isChecked);
                ViewHolder.this.entry.setShowStatus(isChecked);
                SygicAnalyticsLogger.getAnalyticsEvent(PoisAdapter.this.getContext(), EventType.SETTINGS_POI).setName(PoisAdapter.this.mAnalyticsName).setValue(ViewHolder.this.entry.getResName().replace('.', '_'), Boolean.valueOf(isChecked)).logAndRecycle();
            }
        }

        public ViewHolder(View view) {
            this.mListener = new C16591();
            this.title = (TextView) view.findViewById(C1799R.id.title);
            this.enabled = (CompoundButton) view.findViewById(2131624463);
            SettingsAdapter.fixIssue74910(this.title);
        }

        public void process(PoiEntry entry) {
            this.entry = entry;
            this.title.setText(entry.getName());
            this.enabled.setChecked(entry.getShowStatus());
            this.enabled.setOnCheckedChangeListener(this.mListener);
        }
    }

    static {
        mAdapterMode = AdapterMode.GROUP;
    }

    public PoisAdapter(Context context, OnPoiItemClickedListener callback, List<PoiEntry> list, EPoiType poiType, String analyticsName) {
        super(context, 2130903196, C1799R.id.title, list);
        this.mPoiType = poiType;
        this.mAnalyticsName = analyticsName;
        this.mCallback = callback;
        setNotifyOnChange(false);
    }

    public EPoiType getPoiType() {
        return this.mPoiType;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public AdapterMode getAdapterMode() {
        return mAdapterMode;
    }

    public void setAdapterMode(AdapterMode type) {
        mAdapterMode = type;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, null, parent);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        holder.process((PoiEntry) getItem(position));
        return view;
    }

    public void notifyDataSetChanged(int nGroupID) {
        super.notifyDataSetChanged();
        PoiEntry[] arrPoiEntry = mAdapterMode == AdapterMode.CATEGORY ? PoiSettingsManager.nativeGetPoiCategoriesByGroup(nGroupID, this.mPoiType) : PoiSettingsManager.nativeGetPoiGroups(this.mPoiType);
        if (arrPoiEntry != null) {
            clear();
            addAll(Arrays.asList(arrPoiEntry));
            sort(PoiEntry.getComparator(LocalizedStringComparator.getInstance(SettingsManager.nativeGetSelectedLanguage())));
        }
    }
}
