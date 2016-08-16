package com.sygic.aura.poi.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.feature.common.TextWatcherDummy;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.TransitionManagerCompat;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.keyboard.FocusableKeyboardView;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.navigate.ActionControlFragment;
import com.sygic.aura.navigate.ActionControlFragment.Mode;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.PoiManager;
import com.sygic.aura.poi.adapter.PoiAdapter;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.search.model.data.SearchLocationData;
import com.sygic.aura.views.ButtonScrollListView;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.SmartProgressBar.OnRetryCallback;
import com.sygic.aura.views.font_specials.SEditText;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.aura.views.font_specials.SToolbar.OnInvalidatedMenuListener;
import com.sygic.base.C1799R;

public abstract class PoiPagerFragment extends Fragment implements OnItemClickListener, BackPressedListener {
    protected static final int MAX_MAP_ITEMS = 20;
    protected PoiAdapter mAdapter;
    protected PoiFragmentInterface mCallback;
    protected Parcelable mData;
    private ViewGroup mFragmentStack;
    private ButtonScrollListView mListView;
    protected LoadingStateListener mListener;
    protected LocationQuery mLocationQuery;
    protected boolean mMapShown;
    protected View mSearchBar;
    protected long mSearchRef;
    private EditText mSearchText;
    private SmartProgressBar mSmartProgressBar;
    private TextWatcher mTextWatcher;

    /* renamed from: com.sygic.aura.poi.fragment.PoiPagerFragment.1 */
    class C14551 extends TextWatcherDummy {
        C14551() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            PoiPagerFragment.this.mAdapter.query(s);
            PoiPagerFragment.this.mCallback.onQueryChanged(s.toString());
        }
    }

    /* renamed from: com.sygic.aura.poi.fragment.PoiPagerFragment.2 */
    class C14562 implements OnRetryCallback {
        C14562() {
        }

        public void onRetry() {
            PoiPagerFragment.this.mAdapter.resumeLoading();
        }
    }

    /* renamed from: com.sygic.aura.poi.fragment.PoiPagerFragment.3 */
    class C14573 implements OnInvalidatedMenuListener {
        C14573() {
        }

        public void onMenuInvalidated(Menu menu) {
            PoiPagerFragment.this.onPrepareOptionsMenu(menu);
        }
    }

    /* renamed from: com.sygic.aura.poi.fragment.PoiPagerFragment.4 */
    class C14584 implements OnMenuItemClickListener {
        C14584() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return PoiPagerFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.poi.fragment.PoiPagerFragment.5 */
    class C14595 implements LoadingStateListener {
        C14595() {
        }

        public void onLoadingStarted() {
            PoiPagerFragment.this.mSmartProgressBar.start();
        }

        public void onLoadingError() {
            PoiPagerFragment.this.mSmartProgressBar.stopAndShowError();
        }

        public void onFirstNonEmptyTick() {
            PoiPagerFragment.this.mSmartProgressBar.stopAndCrossfadeWith(PoiPagerFragment.this.mListView);
        }

        public void onLoadingFinished(boolean isEmpty) {
            if (PoiPagerFragment.this.getView() != null) {
                if (isEmpty) {
                    PoiPagerFragment.this.mSearchBar.setVisibility(8);
                    return;
                }
                TransitionManagerCompat.beginDelayedTransition((ViewGroup) PoiPagerFragment.this.getView());
                PoiPagerFragment.this.mSearchBar.setVisibility(0);
            }
        }
    }

    public abstract PoiAdapter getAdapter();

    public abstract int getSearchType();

    protected abstract boolean isOnline();

    public PoiPagerFragment() {
        this.mMapShown = false;
        this.mTextWatcher = new C14551();
        this.mListener = new C14595();
    }

    public static PoiPagerFragment newInstance(Class<? extends PoiPagerFragment> clazz, LocationQuery query, Parcelable data, int groupId, boolean startLoading, PoiFragmentInterface callback) {
        try {
            Bundle args = new Bundle();
            args.putParcelable(AbstractFragment.LOCATION_QUERY, query);
            args.putParcelable(PoiFragment.ARG_DATA, data);
            args.putInt(PoiFragment.ARG_GROUP_ID, groupId);
            args.putBoolean(PoiFragment.ARG_START_TASK, startLoading);
            PoiPagerFragment f = (PoiPagerFragment) clazz.newInstance();
            f.setArguments(args);
            f.setCallback(callback);
            return f;
        } catch (Exception e) {
            CrashlyticsHelper.logException("PoiPagerFragment", "error creating " + clazz.getName(), e);
            return null;
        }
    }

    protected void setCallback(PoiFragmentInterface callback) {
        this.mCallback = callback;
    }

    private long initSearch() {
        int searchType = getSearchType();
        if (this.mData instanceof SearchLocationData) {
            return this.mLocationQuery.initCoreSearch(searchType, ((SearchLocationData) this.mData).getSearchItem(1), ((SearchLocationData) this.mData).getSearchItem(2), getArguments().getInt(PoiFragment.ARG_GROUP_ID));
        }
        return this.mLocationQuery.initCoreSearch(searchType, this.mData, getArguments().getInt(PoiFragment.ARG_GROUP_ID));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        this.mData = arguments.getParcelable(PoiFragment.ARG_DATA);
        this.mLocationQuery = (LocationQuery) arguments.getParcelable(AbstractFragment.LOCATION_QUERY);
        this.mSearchRef = initSearch();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903156, container, false);
        this.mAdapter = getAdapter();
        this.mSearchBar = view.findViewById(C1799R.id.search_bar);
        this.mSearchText = (EditText) this.mSearchBar.findViewById(2131624470);
        this.mListView = (ButtonScrollListView) view.findViewById(2131624373);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(this);
        this.mListView.initButtonScroll(view, 2131624136, 2131624138);
        InCarConnection.registerKeyboard((FocusableKeyboardView) view.findViewById(2131624371), (SEditText) view.findViewById(2131624470));
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        this.mSmartProgressBar.setRetryCallback(new C14562());
        return view;
    }

    public void onSetupToolbar(SToolbar toolbar) {
        Menu menu = toolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        inflateMenu(toolbar);
        toolbar.setOnMenuInvalidateListener(new C14573());
        toolbar.setOnMenuItemClickListener(new C14584());
    }

    protected void inflateMenu(SToolbar toolbar) {
        toolbar.inflateMenu(2131755017);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624677) {
            return super.onOptionsItemSelected(item);
        }
        NaviNativeActivity.hideKeyboard(this.mSearchBar.getWindowToken());
        showPoisOnMap();
        return true;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        hidePoisOnMap();
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (getArguments().getBoolean(PoiFragment.ARG_START_TASK, false)) {
            getArguments().putBoolean(PoiFragment.ARG_START_TASK, false);
            onSetupToolbar(this.mCallback.getToolbar());
            startSearchTask(this.mSearchText.getText());
        }
    }

    public void onResume() {
        super.onResume();
        this.mSearchText.addTextChangedListener(this.mTextWatcher);
    }

    public void onPause() {
        this.mSearchText.removeTextChangedListener(this.mTextWatcher);
        super.onPause();
    }

    public void onDestroyView() {
        super.onDestroyView();
        cancelPoiLoading();
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mMapShown) {
            hidePoisOnMap();
        }
        this.mLocationQuery.destroySearchObjectRef(this.mSearchRef);
    }

    public void cancelPoiLoading() {
        this.mAdapter.cancelPoiLoading();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (this.mCallback != null) {
            this.mCallback.finish((PoiListItem) this.mAdapter.getItem(position));
        }
    }

    public void startSearchTask(CharSequence query) {
        if (!TextUtils.equals(this.mSearchText.getText(), query)) {
            this.mSearchText.removeTextChangedListener(this.mTextWatcher);
            this.mSearchText.setText(query);
            this.mSearchText.setSelection(query.length());
            this.mSearchText.addTextChangedListener(this.mTextWatcher);
        }
        this.mAdapter.query(query);
    }

    protected void showPoisOnMap() {
        if (this.mFragmentStack == null) {
            this.mFragmentStack = (ViewGroup) getActivity().findViewById(2131624520);
        }
        if (this.mFragmentStack != null) {
            this.mMapShown = true;
            int pos = this.mFragmentStack.getChildCount();
            if (pos > 1) {
                ViewGroup child;
                while (true) {
                    child = (ViewGroup) this.mFragmentStack.getChildAt(pos - 1);
                    if (child.getChildCount() > 0) {
                        break;
                    }
                    child.setTag(2131623943, null);
                    pos--;
                }
                for (int i = 0; i < pos - 1; i++) {
                    child = (ViewGroup) this.mFragmentStack.getChildAt(i);
                    child.setTag(2131623943, Integer.valueOf(child.getVisibility()));
                    child.setVisibility(8);
                }
            }
            PoiManager.nativeShowPoisOnMap(isOnline(), this.mAdapter.getShowOnMapData(Math.min(this.mAdapter.getCount(), MAX_MAP_ITEMS)));
        }
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null && this.mAdapter.getCount() > 0) {
            PoiListItem firstItem = (PoiListItem) this.mAdapter.getItem(0);
            MapSelection mapSel = firstItem.getMapSel();
            if (mapSel != null) {
                getArguments().putBoolean(PoiFragment.ARG_START_TASK, true);
                Bundle args = new Bundle();
                args.putParcelable("mapsel", mapSel);
                args.putString("icon_char", firstItem.getIcon());
                args.putString(AbstractFragment.ARG_TITLE, this.mCallback.getToolbar().getTitle().toString());
                args.putParcelable("mode", Mode.NEARBY_POI);
                manager.replaceFragment(ActionControlFragment.class, "action_control", true, args);
            }
        }
    }

    protected void hidePoisOnMap() {
        if (this.mMapShown && this.mFragmentStack != null) {
            for (int i = 0; i < this.mFragmentStack.getChildCount(); i++) {
                View child = this.mFragmentStack.getChildAt(i);
                Integer tag = (Integer) child.getTag(2131623943);
                if (tag != null) {
                    child.setVisibility(tag.intValue());
                    child.setTag(2131623943, null);
                }
            }
            PoiManager.nativeShowPoisOnMap(isOnline(), new long[0]);
            this.mMapShown = false;
        }
    }

    protected boolean isOnTopOfBackStack() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment == null) {
            return false;
        }
        FragmentManager fragmentManager = parentFragment.getFragmentManager();
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount == 0) {
            return false;
        }
        return fragmentManager.getBackStackEntryAt(backStackCount - 1).getName().equals(parentFragment.getTag());
    }

    public boolean onBackPressed() {
        if (!this.mMapShown || !isOnTopOfBackStack()) {
            return false;
        }
        hidePoisOnMap();
        return true;
    }
}
