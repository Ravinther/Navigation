package com.sygic.aura.settings.fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.BaseDashboardFragmentResultCallback;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.LocalizedStringComparator;
import com.sygic.aura.settings.data.PoiCategoryEntry;
import com.sygic.aura.settings.data.PoiEntry;
import com.sygic.aura.settings.data.PoiGroupEntry;
import com.sygic.aura.settings.data.PoiSettingsManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.model.PoisAdapter;
import com.sygic.aura.settings.model.PoisAdapter.AdapterMode;
import com.sygic.aura.settings.model.PoisAdapter.OnPoiItemClickedListener;
import com.sygic.aura.settings.model.SettingsAdapter;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public abstract class PoiBaseSettingsFragment extends AbstractScreenFragment implements OnItemClickListener, BaseDashboardFragmentResultCallback, OnPoiItemClickedListener {
    protected static final String ARG_GROUP_ID = "poi_group_id";
    protected static final String ARG_TITLE_RES = "poi_group_title_res";
    protected CompoundButton mCompoundEnable;
    protected int mGroupID;
    protected List<PoiEntry> mItems;
    protected ListView mList;
    protected PoisAdapter mListAdapter;
    protected int mMenuId;
    protected String mTitle;
    protected TextView mTitleEnable;
    protected String mTitleRes;

    /* renamed from: com.sygic.aura.settings.fragments.PoiBaseSettingsFragment.1 */
    class C16451 implements OnMenuItemClickListener {
        C16451() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return PoiBaseSettingsFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.PoiBaseSettingsFragment.2 */
    class C16462 implements OnCheckedChangeListener {
        final /* synthetic */ AdapterMode val$adapterMode;

        C16462(AdapterMode adapterMode) {
            this.val$adapterMode = adapterMode;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Object tag = buttonView.getTag();
            if (tag == null || ((tag instanceof Boolean) && ((Boolean) tag).booleanValue())) {
                PoiBaseSettingsFragment.this.handleClick(this.val$adapterMode, isChecked);
                ((PoisAdapter) PoiBaseSettingsFragment.this.mList.getAdapter()).notifyDataSetChanged(PoiBaseSettingsFragment.this.mGroupID);
            }
            SygicAnalyticsLogger.getAnalyticsEvent(PoiBaseSettingsFragment.this.getActivity(), EventType.SETTINGS_POI).setName(PoiBaseSettingsFragment.this.createAnalyticsName()).setValue(PoiBaseSettingsFragment.this.mTitleRes.replace('.', '_'), Boolean.valueOf(isChecked)).logAndRecycle();
            buttonView.setTag(null);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.PoiBaseSettingsFragment.3 */
    class C16473 implements OnClickListener {
        C16473() {
        }

        public void onClick(View v) {
            PoiBaseSettingsFragment.this.mCompoundEnable.performClick();
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.PoiBaseSettingsFragment.4 */
    static /* synthetic */ class C16484 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$settings$model$PoisAdapter$AdapterMode;

        static {
            $SwitchMap$com$sygic$aura$settings$model$PoisAdapter$AdapterMode = new int[AdapterMode.values().length];
            try {
                $SwitchMap$com$sygic$aura$settings$model$PoisAdapter$AdapterMode[AdapterMode.CATEGORY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$settings$model$PoisAdapter$AdapterMode[AdapterMode.GROUP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    protected abstract Class<? extends AbstractScreenFragment> getChildClass();

    protected abstract String getPoiTag();

    protected abstract boolean getShowStatusAll();

    protected abstract boolean getShowStatusGroup(int i);

    protected abstract PoisAdapter initListAdapter();

    protected abstract void setShowStatusAll(boolean z);

    protected abstract void setShowStatusGroup(int i, boolean z);

    protected PoiBaseSettingsFragment() {
        this.mMenuId = 0;
        this.mGroupID = 0;
        setWantsNavigationData(false);
    }

    protected PoiCategoryEntry[] getPoiCategoriesByGroup(int groupID) {
        return PoiSettingsManager.nativeGetPoiCategoriesByGroup(groupID, this.mListAdapter.getPoiType());
    }

    protected PoiGroupEntry[] getPoiGroups() {
        return PoiSettingsManager.nativeGetPoiGroups(this.mListAdapter.getPoiType());
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(this.mTitle);
        if (this.mMenuId > 0) {
            toolbar.inflateMenu(this.mMenuId);
            toolbar.setOnMenuItemClickListener(new C16451());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mTitle = bundle.getString(AbstractFragment.ARG_TITLE);
            this.mTitleRes = bundle.getString(ARG_TITLE_RES, getPoiTag() + "_main");
            this.mMenuId = bundle.getInt(SettingsFragment.ARG_MENU);
            this.mGroupID = bundle.getInt(ARG_GROUP_ID);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mListAdapter.setAdapterMode(AdapterMode.GROUP);
        finish();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903121, container, false);
        this.mItems = new ArrayList();
        this.mListAdapter = initListAdapter();
        AdapterMode adapterMode = this.mListAdapter.getAdapterMode();
        PoiEntry[] arrPoiEntry = adapterMode == AdapterMode.CATEGORY ? getPoiCategoriesByGroup(this.mGroupID) : getPoiGroups();
        if (arrPoiEntry != null) {
            this.mItems.addAll(Arrays.asList(arrPoiEntry));
            Collections.sort(this.mItems, PoiEntry.getComparator(LocalizedStringComparator.getInstance(SettingsManager.nativeGetSelectedLanguage())));
        }
        this.mList = (ListView) view.findViewById(2131624221);
        this.mList.setAdapter(this.mListAdapter);
        this.mList.setOnItemClickListener(this);
        View parent = view.findViewById(2131624274);
        this.mTitleEnable = (TextView) parent.findViewById(C1799R.id.title);
        this.mCompoundEnable = (CompoundButton) parent.findViewById(2131624275);
        if (adapterMode == AdapterMode.CATEGORY) {
            this.mCompoundEnable.setChecked(getShowStatusGroup(this.mGroupID));
        } else {
            this.mCompoundEnable.setChecked(getShowStatusAll());
        }
        this.mCompoundEnable.setOnCheckedChangeListener(new C16462(adapterMode));
        parent.setOnClickListener(new C16473());
        SettingsAdapter.fixIssue74910(this.mTitleEnable);
        return view;
    }

    private void handleClick(AdapterMode mode, boolean bIsEnabled) {
        switch (C16484.$SwitchMap$com$sygic$aura$settings$model$PoisAdapter$AdapterMode[mode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                setShowStatusGroup(this.mGroupID, bIsEnabled);
            case TTSConst.TTSPARAGRAPH /*2*/:
                setShowStatusAll(bIsEnabled);
            default:
        }
    }

    private boolean getShowStatus(AdapterMode mode) {
        switch (C16484.$SwitchMap$com$sygic$aura$settings$model$PoisAdapter$AdapterMode[mode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return getShowStatusGroup(this.mGroupID);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return getShowStatusAll();
            default:
                return false;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PoiEntry item = (PoiEntry) parent.getItemAtPosition(position);
        if (item != null && (item instanceof PoiGroupEntry)) {
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, item.getName());
            bundle.putInt(ARG_GROUP_ID, item.getID());
            bundle.putString(ARG_TITLE_RES, item.getResName());
            this.mListAdapter.setAdapterMode(AdapterMode.CATEGORY);
            Fragments.add(getActivity(), getChildClass(), item.getName(), bundle, true, this);
        }
    }

    public void onPoiItemClicked(boolean isChecked) {
        boolean showStatusAll = getShowStatus(this.mListAdapter.getAdapterMode());
        this.mCompoundEnable.setTag(Boolean.valueOf(this.mCompoundEnable.isChecked() == showStatusAll));
        this.mCompoundEnable.setChecked(showStatusAll);
    }

    private void finish() {
        if (this.mResultCallback != null) {
            ((BaseDashboardFragmentResultCallback) this.mResultCallback).onDashboardFragmentFinished();
            this.mResultCallback = null;
        }
    }

    public void onDashboardFragmentFinished() {
        boolean showStatusAll = getShowStatusAll();
        this.mCompoundEnable.setTag(Boolean.valueOf(this.mCompoundEnable.isChecked() == showStatusAll));
        this.mCompoundEnable.setChecked(showStatusAll);
        ((PoisAdapter) this.mList.getAdapter()).notifyDataSetChanged(this.mGroupID);
    }

    protected String createAnalyticsName() {
        return "Settings poi-" + getPoiTag();
    }
}
