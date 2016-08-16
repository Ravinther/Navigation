package com.sygic.aura.route.overview;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.WrapperListAdapter;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.InputDialogFragment;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.MarketPlaceEventsReceiver;
import com.sygic.aura.helper.EventReceivers.NetworkEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.FlurryHelper;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.TransitionManagerCompat;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.helper.interfaces.ConnectionChangeListener;
import com.sygic.aura.helper.interfaces.MarketPlaceListener;
import com.sygic.aura.helper.interfaces.SpeedCamsReadyListener;
import com.sygic.aura.helper.interfaces.TrafficChangeListener;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.network.ConnectionManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.Typefaces;
import com.sygic.aura.route.Filterable;
import com.sygic.aura.route.RouteInstructionsAdapter;
import com.sygic.aura.route.RouteInstructionsAdapter.InstructionOverflowCallback;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.SpeedCamsAdapter;
import com.sygic.aura.route.TrafficAdapter;
import com.sygic.aura.route.data.SpeedCamItem;
import com.sygic.aura.route.data.TrafficItem;
import com.sygic.aura.route.data.TrafficItem.TrafficLevel;
import com.sygic.aura.route.fragment.RouteComputeProgressFragment;
import com.sygic.aura.route.view.RouteFilterLayout;
import com.sygic.aura.route.view.RouteFilterLayout.RouteFilterInterface;
import com.sygic.aura.store.MarketPlaceManager;
import com.sygic.aura.store.data.ProductDetailEntry;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.fragment.MarketPlaceFragment;
import com.sygic.aura.store.fragment.ProductDetailFragment;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import java.util.ArrayList;
import java.util.HashMap;
import loquendo.tts.engine.TTSConst;

public class RouteOverviewFragment extends RouteComputeProgressFragment implements OnScrollListener, ConnectionChangeListener, MarketPlaceListener, SpeedCamsReadyListener, TrafficChangeListener, RouteFilterInterface {
    private AsyncTask<Void, Integer, Integer> mAsyncTask;
    private RouteFilterLayout mFiltersFrame;
    private int mIncidentsCount;
    private View mListHeader;
    private boolean mNavigationChanged;
    private RouteInstructionsAdapter mRouteInstructionsAdapter;
    private ListView mRouteInstructionsList;
    private SmartProgressBar mSmartProgressBar;
    private SpeedCamsAdapter mSpeedCamsAdapter;
    private DataSetObserver mSpeedCamsObserver;
    private View mStoreButton;
    private String mStrEtaFormat;
    private String mStrFromClass;
    private String mStrIncidentsLabel;
    private String mStrRouteName;
    private TrafficItem mTraffic;
    private TrafficAdapter mTrafficAdapter;
    private DataSetObserver mTrafficObserver;
    private TextView mTvIncidents;
    private TextView mTvRouteEnd;
    private TextView mTvRouteInfoDistance;
    private TextView mTvRouteInfoETA;
    private TextView mTvRouteStart;
    private ViewGroup mWaypointsContainer;
    private ViewGroup mWaypointsList;
    private View mWaypointsPlaceholder;
    private WrapperAdapter mWrapperAdapter;

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.11 */
    static /* synthetic */ class AnonymousClass11 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel;
        static final /* synthetic */ int[] f1272x7fba8005;

        static {
            $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel = new int[TrafficLevel.values().length];
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.LOW.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.MEDIUM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.HIGH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            f1272x7fba8005 = new int[Filter.values().length];
            try {
                f1272x7fba8005[Filter.TRAFFIC.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1272x7fba8005[Filter.SPEEDCAMS.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1272x7fba8005[Filter.ALL.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.1 */
    class C15381 extends DataSetObserver {
        C15381() {
        }

        public void onChanged() {
            if (RouteOverviewFragment.this.mFiltersFrame.getSelectedPage() == Filter.TRAFFIC.ordinal()) {
                RouteOverviewFragment.this.updateContent();
            }
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.2 */
    class C15392 extends DataSetObserver {
        C15392() {
        }

        public void onChanged() {
            if (RouteOverviewFragment.this.mFiltersFrame.getSelectedPage() == Filter.SPEEDCAMS.ordinal()) {
                RouteOverviewFragment.this.updateContent();
            }
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.4 */
    class C15404 implements DialogFragmentClickListener {
        C15404() {
        }

        public void onPositiveButtonClicked(Editable text) {
            RouteOverviewFragment.this.mStrRouteName = text.toString();
            MemoManager.nativeAddRoute(RouteOverviewFragment.this.mStrRouteName);
            RouteOverviewFragment.this.mToolbar.invalidateMenu();
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.5 */
    class C15415 implements OnClickListener {
        C15415() {
        }

        public void onClick(View v) {
            if (RouteOverviewFragment.this.mFiltersFrame.getSelectedPage() == Filter.TRAFFIC.ordinal()) {
                SygicAnalyticsLogger.logEvent(RouteOverviewFragment.this.getActivity(), EventType.LIVE_SERVICES, RouteOverviewFragment.this.prepareLogEventForStore("traffic"));
                RouteOverviewFragment.this.requestStoreListOrDetail("buy-traffic");
            }
            if (RouteOverviewFragment.this.mFiltersFrame.getSelectedPage() == Filter.SPEEDCAMS.ordinal()) {
                SygicAnalyticsLogger.logEvent(RouteOverviewFragment.this.getActivity(), EventType.LIVE_SERVICES, RouteOverviewFragment.this.prepareLogEventForStore("speedcams"));
                RouteOverviewFragment.this.requestStoreListOrDetail("SpeedCam");
            }
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.6 */
    class C15426 implements OnClickListener {
        C15426() {
        }

        public void onClick(View v) {
            FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
            if (manager != null) {
                manager.addFragment(AvoidsFragment.class, "fragment_route_avoids_tag", true, null, new Bundle());
            }
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.7 */
    class C15437 implements InstructionOverflowCallback {
        C15437() {
        }

        public void onAvoidInstruction(int index) {
            RouteSummary.nativeAvoidInstruction(index);
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.8 */
    class C15448 implements InstructionOverflowCallback {
        C15448() {
        }

        public void onAvoidInstruction(int index) {
            RouteSummary.nativeAvoidInstruction(index);
        }
    }

    /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.9 */
    class C15459 implements OnClickListener {
        C15459() {
        }

        public void onClick(View v) {
            boolean showWaypoints;
            int i = 0;
            if (RouteOverviewFragment.this.mWaypointsList.getVisibility() != 0) {
                showWaypoints = true;
            } else {
                showWaypoints = false;
            }
            RouteOverviewFragment.this.setupPlaceholder(v, showWaypoints);
            TransitionManagerCompat.beginDelayedTransition(RouteOverviewFragment.this.mWaypointsContainer);
            ViewGroup access$700 = RouteOverviewFragment.this.mWaypointsList;
            if (!showWaypoints) {
                i = 8;
            }
            access$700.setVisibility(i);
        }
    }

    public enum Filter {
        TRAFFIC,
        SPEEDCAMS,
        ALL;

        public static Filter fromInt(int filterIndex) {
            switch (filterIndex) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    return TRAFFIC;
                case TTSConst.TTSMULTILINE /*1*/:
                    return SPEEDCAMS;
                default:
                    return ALL;
            }
        }
    }

    public static class PinImageView extends SImageView {
        private TextPaint mPaint;
        private String mText;

        public PinImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public PinImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.mText = "A";
            this.mPaint = new TextPaint();
            this.mPaint.setColor(getResources().getColor(2131558794));
            this.mPaint.setTypeface(Typefaces.getFont(getContext(), 2131166099));
            this.mPaint.setTextSize(getResources().getDimension(2131230966));
            this.mPaint.setTextAlign(Align.CENTER);
        }

        public void setText(String text) {
            this.mText = text;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Rect bounds = new Rect();
            this.mPaint.getTextBounds(this.mText, 0, this.mText.length(), bounds);
            canvas.drawText(this.mText, (float) (canvas.getWidth() / 2), (float) ((canvas.getHeight() / 2) + (bounds.height() / 3)), this.mPaint);
        }
    }

    private class WrapperAdapter extends BaseAdapter implements WrapperListAdapter {
        private ArrayList<ListAdapter> mAdapters;
        private Filter mCurrent;

        /* renamed from: com.sygic.aura.route.overview.RouteOverviewFragment.WrapperAdapter.1 */
        class C15461 extends DataSetObserver {
            final /* synthetic */ ListAdapter val$adapter;

            C15461(ListAdapter listAdapter) {
                this.val$adapter = listAdapter;
            }

            public void onChanged() {
                if (WrapperAdapter.this.getWrappedAdapter() == this.val$adapter) {
                    WrapperAdapter.this.notifyDataSetChanged();
                }
            }

            public void onInvalidated() {
                if (WrapperAdapter.this.getWrappedAdapter() == this.val$adapter) {
                    WrapperAdapter.this.notifyDataSetInvalidated();
                }
            }
        }

        public WrapperAdapter(TrafficAdapter trafficAdapter, SpeedCamsAdapter speedCamsAdapter, RouteInstructionsAdapter instructionsAdapter) {
            this.mCurrent = Filter.ALL;
            this.mAdapters = new ArrayList(3);
            trafficAdapter.registerDataSetObserver(getObserver(trafficAdapter));
            speedCamsAdapter.registerDataSetObserver(getObserver(speedCamsAdapter));
            instructionsAdapter.registerDataSetObserver(getObserver(instructionsAdapter));
            this.mAdapters.add(trafficAdapter);
            this.mAdapters.add(speedCamsAdapter);
            this.mAdapters.add(instructionsAdapter);
        }

        public void setCurrent(Filter current) {
            this.mCurrent = current;
            notifyDataSetChanged();
        }

        public ListAdapter getWrappedAdapter() {
            return (ListAdapter) this.mAdapters.get(this.mCurrent.ordinal());
        }

        public int getCount() {
            return ((ListAdapter) this.mAdapters.get(this.mCurrent.ordinal())).getCount();
        }

        public Object getItem(int position) {
            return ((ListAdapter) this.mAdapters.get(this.mCurrent.ordinal())).getItem(position);
        }

        public long getItemId(int position) {
            return ((ListAdapter) this.mAdapters.get(this.mCurrent.ordinal())).getItemId(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return ((ListAdapter) this.mAdapters.get(this.mCurrent.ordinal())).getView(position, convertView, parent);
        }

        public int getItemViewType(int position) {
            return this.mCurrent.ordinal();
        }

        public int getViewTypeCount() {
            return this.mAdapters.size();
        }

        private DataSetObserver getObserver(ListAdapter adapter) {
            return new C15461(adapter);
        }
    }

    public RouteOverviewFragment() {
        this.mNavigationChanged = false;
        this.mStrRouteName = null;
        this.mIncidentsCount = 0;
        this.mTraffic = null;
        this.mTrafficObserver = new C15381();
        this.mSpeedCamsObserver = new C15392();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        setActionIcons(menu);
    }

    private void setActionIcons(Menu menu) {
        ResourceManager.setActionButtonIcon(getActivity(), menu.findItem(2131624681), this.mStrRouteName != null ? 2131166084 : 2131166083);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNavigationData();
        this.mStrFromClass = getArguments().getString("class_name");
        this.mTraffic = (TrafficItem) getArguments().getParcelable("traffic_delay");
        MarketPlaceEventsReceiver.registerEventsListener(this);
        MapEventsReceiver.registerTrafficChangeListener(this);
        WndEventsReceiver.registerAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        NetworkEventsReceiver.registerConnectionChangeListener(this);
    }

    protected int getToolbarTitle() {
        return 2131165321;
    }

    protected int getToolbarMenu() {
        return 2131755025;
    }

    public void onDestroy() {
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
        MapEventsReceiver.unregisterTrafficChangeListener(this);
        RouteEventsReceiver.unregisterSpeedCamsReadyListener(this);
        NetworkEventsReceiver.unregisterConnectionChangeListener(this);
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624681) {
            return super.onOptionsItemSelected(item);
        }
        if (this.mStrRouteName == null) {
            InputDialogFragment.newInstance(getActivity(), 2131165412, TextUtils.join(" - ", new String[]{this.mTvRouteStart.getText().toString(), this.mTvRouteEnd.getText().toString()}), new C15404()).showDialog();
            return true;
        }
        MemoManager.nativeRemoveRoute(this.mStrRouteName);
        this.mStrRouteName = null;
        this.mToolbar.invalidateMenu();
        return true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (NaviNativeActivity.isNavigationBarHidden(activity)) {
            this.mNavigationChanged = true;
            NaviNativeActivity.showNavigationBar(activity, false);
        }
        View view = inflater.inflate(2130903127, container, false);
        this.mRouteInstructionsList = (ListView) view.findViewById(2131624289);
        this.mListHeader = inflater.inflate(2130903265, this.mRouteInstructionsList, false);
        this.mSmartProgressBar = (SmartProgressBar) this.mListHeader.findViewById(2131624166);
        this.mSmartProgressBar.showEmpty();
        this.mStoreButton = this.mListHeader.findViewById(2131624579);
        this.mStoreButton.setOnClickListener(new C15415());
        this.mListHeader.findViewById(2131624574).setOnClickListener(new C15426());
        this.mFiltersFrame = (RouteFilterLayout) this.mListHeader.findViewById(2131624573);
        this.mFiltersFrame.setFilterInterFace(this);
        Context context = inflater.getContext();
        this.mRouteInstructionsAdapter = new RouteInstructionsAdapter(context);
        this.mRouteInstructionsAdapter.setOverflowCallback(new C15437());
        this.mTrafficAdapter = new TrafficAdapter(context);
        this.mTrafficAdapter.registerDataSetObserver(this.mTrafficObserver);
        this.mTrafficAdapter.setOverflowCallback(new C15448());
        this.mSpeedCamsAdapter = new SpeedCamsAdapter(context);
        this.mSpeedCamsAdapter.registerDataSetObserver(this.mSpeedCamsObserver);
        RouteEventsReceiver.registerSpeedCamsReadyListener(this);
        RouteManager.nativeComputeRouteCameras();
        setSpeedCamCountText(0);
        setTrafficDelayText();
        this.mWrapperAdapter = new WrapperAdapter(this.mTrafficAdapter, this.mSpeedCamsAdapter, this.mRouteInstructionsAdapter);
        this.mRouteInstructionsList.addHeaderView(this.mListHeader, null, false);
        this.mRouteInstructionsList.setAdapter(this.mWrapperAdapter);
        this.mStrEtaFormat = ResourceManager.getCoreString(context, 2131165642);
        this.mStrIncidentsLabel = ResourceManager.getCoreString(context, 2131165631);
        this.mTvRouteInfoETA = (TextView) this.mListHeader.findViewById(2131624576);
        this.mTvRouteInfoDistance = (TextView) this.mListHeader.findViewById(2131624577);
        this.mTvIncidents = (TextView) this.mListHeader.findViewById(2131624578);
        View routePoints = this.mListHeader.findViewById(2131624159);
        View startLayout = routePoints.findViewById(2131624567);
        this.mTvRouteStart = (TextView) startLayout.findViewById(2131624469);
        ((SImageView) startLayout.findViewById(2131624468)).setFontDrawableColor(getResources().getColor(2131558739));
        View endLayout = routePoints.findViewById(2131624572);
        this.mTvRouteEnd = (TextView) endLayout.findViewById(2131624469);
        ((PinImageView) endLayout.findViewById(2131624468)).setText(Character.toString((char) (RouteSummary.nativeGetRouteWayPointsCount() + 66)));
        this.mWaypointsContainer = (ViewGroup) routePoints.findViewById(2131624568);
        this.mWaypointsList = (ViewGroup) routePoints.findViewById(2131624292);
        this.mWaypointsPlaceholder = routePoints.findViewById(2131624569);
        setupPlaceholder(this.mWaypointsPlaceholder, false);
        this.mWaypointsPlaceholder.setOnClickListener(new C15459());
        if (RouteSummary.nativeIsPedestrian()) {
            ((View) this.mTvIncidents.getParent()).setVisibility(8);
        }
        updateAllRouteSummaryInfo(false);
        updateWaypoints();
        Filter filter = (Filter) getArguments().getSerializable("filter");
        if (filter == null) {
            filter = Filter.ALL;
        }
        this.mFiltersFrame.setMode(filter);
        return view;
    }

    private void setupPlaceholder(View v, boolean showWaypoints) {
        ((STextView) v.findViewById(2131624571)).setCoreText(showWaypoints ? 2131165648 : 2131165649);
        ((SImageView) v.findViewById(2131624570)).setFontDrawableResource(showWaypoints ? 2131034320 : 2131034321);
    }

    private void requestStoreListOrDetail(String productId) {
        MarketPlaceEventsReceiver.registerEventsListener(this);
        MarketPlaceManager.nativeRequestGet(productId);
    }

    private void updateAllRouteSummaryInfo(boolean loadTraffic) {
        RouteSummary.nativeInitRouteSummary();
        updateBaseInfo();
        loadRouteInstructions(loadTraffic);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mAsyncTask.cancel(true);
        if (this.mNavigationChanged) {
            NaviNativeActivity.hideNavigationBar(getActivity());
        }
        WndEventsReceiver.unregisterAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        this.mTrafficAdapter.unregisterDataSetObserver(this.mTrafficObserver);
        this.mSpeedCamsAdapter.unregisterDataSetObserver(this.mSpeedCamsObserver);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void updateBaseInfo() {
        updateEta();
        this.mTvRouteInfoDistance.setText(ResourceManager.nativeFormatDistance(RouteSummary.nativeGetRemainingDistance()));
        if (this.mAsyncTask != null) {
            this.mAsyncTask.cancel(true);
        }
        this.mAsyncTask = new AsyncTask<Void, Integer, Integer>() {
            protected Integer doInBackground(Void... params) {
                while (!isCancelled()) {
                    int incidentsCount = RouteSummary.nativeGetIncidentsCount();
                    if (incidentsCount > 0) {
                        return Integer.valueOf(incidentsCount);
                    }
                    publishProgress(new Integer[]{Integer.valueOf(incidentsCount)});
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            protected void onProgressUpdate(Integer... values) {
                if (RouteOverviewFragment.this.getActivity() != null) {
                    RouteOverviewFragment.this.mIncidentsCount = values[0].intValue();
                    if (RouteOverviewFragment.this.mIncidentsCount == -1) {
                        RouteOverviewFragment.this.mTvIncidents.setTextColor(RouteOverviewFragment.this.getResources().getColor(2131558793));
                        RouteOverviewFragment.this.mTvIncidents.setText(ResourceManager.getCoreString(RouteOverviewFragment.this.mTvIncidents.getContext(), 2131165638));
                    } else if (RouteOverviewFragment.this.mIncidentsCount == 0) {
                        ((View) RouteOverviewFragment.this.mTvIncidents.getParent()).setVisibility(8);
                        RouteOverviewFragment.this.mTrafficAdapter.setFinished();
                        if (RouteOverviewFragment.this.mFiltersFrame.getSelectedPage() == Filter.TRAFFIC.ordinal()) {
                            RouteOverviewFragment.this.updateContent();
                        }
                    }
                }
            }

            protected void onPostExecute(Integer result) {
                if (result != null && RouteOverviewFragment.this.getActivity() != null) {
                    RouteOverviewFragment.this.mIncidentsCount = result.intValue();
                    if (RouteOverviewFragment.this.mIncidentsCount > 0) {
                        RouteOverviewFragment.this.loadRouteInstructions(true);
                        Resources resources = RouteOverviewFragment.this.getResources();
                        RouteOverviewFragment.this.mTvIncidents.setTextColor(resources.getColor(2131558487));
                        String quantityString = ResourceManager.getQuantityString(resources, 2131689473, RouteOverviewFragment.this.mIncidentsCount);
                        RouteOverviewFragment.this.mTvIncidents.setText(String.format(ResourceManager.getCoreString(quantityString), new Object[]{Integer.valueOf(RouteOverviewFragment.this.mIncidentsCount)}));
                    }
                }
            }
        };
        AsyncTaskHelper.execute(this.mAsyncTask);
    }

    private void updateWaypoints() {
        String strStart = RouteSummary.nativeGetStartPointText();
        String strEnd = RouteSummary.nativeGetEndPointText();
        int wayPointsCount = RouteSummary.nativeGetRouteWayPointsCount();
        for (int i = 0; i < wayPointsCount; i++) {
            String wayPointText = RouteSummary.nativeGetWayPointText(i);
            View waypointView = getLayoutInflater(null).inflate(2130903198, this.mWaypointsList, false);
            ((TextView) waypointView.findViewById(2131624469)).setText(wayPointText);
            PinImageView waypointPin = (PinImageView) waypointView.findViewById(2131624468);
            waypointPin.setFontDrawableColor(getResources().getColor(2131558748));
            if (i < 24) {
                waypointPin.setText(Character.toString((char) (i + 66)));
            }
            this.mWaypointsList.addView(waypointView, i);
        }
        if (wayPointsCount <= 1) {
            this.mWaypointsList.setVisibility(0);
            this.mWaypointsPlaceholder.setVisibility(8);
        }
        Context activity = getActivity();
        TextView textView = this.mTvRouteStart;
        if (activity != null) {
            strStart = PreferenceManager.getDefaultSharedPreferences(activity).getString(getString(2131166238), strStart);
        }
        textView.setText(strStart);
        this.mTvRouteEnd.setText(strEnd);
    }

    private void updateEta() {
        long lDuration;
        long lRemainingTime = RouteSummary.nativeGetRemainingTime();
        if (lRemainingTime < 0) {
            lDuration = (long) RouteSummary.nativeGetTotalTime();
        } else {
            lDuration = lRemainingTime;
        }
        long totalTime = lDuration + ((long) (this.mTraffic != null ? this.mTraffic.getDelay() : 0));
        this.mTvRouteInfoETA.setText(this.mStrEtaFormat.replace("%ETA%", ResourceManager.nativeFormatETA(totalTime)).replace("%DURATION%", ResourceManager.nativeFormatTimeSpanToShortWords(totalTime)));
        if (this.mTraffic != null) {
            this.mTvRouteInfoETA.setTextColor(getResources().getColor(getTrafficLevelColor()));
        }
    }

    public void loadRouteInstructions(boolean loadTraffic) {
        if (this.mRouteInstructionsAdapter != null) {
            this.mRouteInstructionsAdapter.loadRouteInstructions();
        }
        if (this.mTrafficAdapter != null && loadTraffic) {
            this.mTrafficAdapter.loadTraffic();
        }
    }

    public void onRouteComputeFinishedAll() {
        super.onRouteComputeFinishedAll();
        updateAllRouteSummaryInfo(false);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void onFilterChanged(Filter mode) {
        String modeName;
        this.mWrapperAdapter.setCurrent(mode);
        switch (AnonymousClass11.f1272x7fba8005[mode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                updateContent();
                modeName = "traffic";
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                updateContent();
                modeName = "speedcams";
                break;
            case TTSConst.TTSUNICODE /*3*/:
                modeName = "all";
                ResourceManager.makeControlVisible(this.mSmartProgressBar, false, true);
                ResourceManager.makeControlVisible(this.mStoreButton, false, true);
                break;
            default:
                CrashlyticsHelper.logWarning("route_fragment_filter", "selected unknown filter");
                modeName = "unknown";
                break;
        }
        HashMap<String, Object> attributes = new HashMap();
        attributes.put("category", modeName);
        FlurryHelper.addDefaultParams(attributes);
        Bundle params = new Bundle();
        params.putString("eventName", "Route overview - filter");
        params.putSerializable("attributes", attributes);
        SygicAnalyticsLogger.logEvent(getActivity(), EventType.LIVE_SERVICES, params);
    }

    private void goToProductDetail(ProductDetailEntry productDetailEntry) {
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
        Bundle args = new Bundle();
        args.putParcelable("product", productDetailEntry);
        Fragments.add(getActivity(), ProductDetailFragment.class, "fragment_product_speedcam", args);
    }

    private void goToProductList(ArrayList<StoreEntry> entries) {
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
        Bundle args = new Bundle();
        args.putParcelableArrayList("entries", entries);
        args.putString("source", "route_fragment_filter");
        Fragments.add(getActivity(), MarketPlaceFragment.class, "fragment_sygic_store_tag", args);
    }

    public void onTrafficChange(TrafficItem trafficItem) {
        this.mTraffic = trafficItem;
        if (this.mTrafficAdapter != null) {
            setTrafficDelayText();
        }
        updateEta();
    }

    public void onSpeedCamsReady(ArrayList<SpeedCamItem> items) {
        setSpeedCamCountText(items.size());
        if (this.mSpeedCamsAdapter != null) {
            this.mSpeedCamsAdapter.loadCams(items);
            setFilterTitles();
            if (this.mFiltersFrame.getSelectedPage() == Filter.SPEEDCAMS.ordinal()) {
                updateContent();
            }
        }
    }

    private void setTrafficDelayText() {
        this.mFiltersFrame.setFilterText(Filter.TRAFFIC, this.mTrafficAdapter.hasLicence(), ResourceManager.nativeFormatTimeSpanToShortWords(this.mTraffic != null ? (long) this.mTraffic.getDelay() : 0));
    }

    private void setSpeedCamCountText(int cams) {
        this.mFiltersFrame.setFilterText(Filter.SPEEDCAMS, this.mSpeedCamsAdapter.hasLicence(), cams + "x");
    }

    public void onConnectionChanged(Boolean bConnection) {
        if (bConnection.booleanValue()) {
            this.mSmartProgressBar.stop();
            this.mSpeedCamsAdapter.setFinished();
            RouteManager.nativeComputeRouteCameras();
            this.mTrafficAdapter.loadTraffic();
        }
        setFilterTitles();
        updateContent();
    }

    private ArrayAdapter getAdapterForCurrentTab() {
        switch (AnonymousClass11.f1272x7fba8005[Filter.fromInt(this.mFiltersFrame.getSelectedPage()).ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return this.mTrafficAdapter;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return this.mSpeedCamsAdapter;
            default:
                CrashlyticsHelper.logWarning("route_fragment_filter", "Trying to choose invalid adapter for filter");
                return null;
        }
    }

    private void updateContent() {
        ArrayAdapter adapter = getAdapterForCurrentTab();
        if (adapter != null) {
            boolean hasConnection = ConnectionManager.nativeIsConnected();
            boolean hasLicence = ((Filterable) adapter).hasLicence();
            Filterable filterableAdapter = (Filterable) adapter;
            if (!(hasConnection && hasLicence)) {
                adapter.setNotifyOnChange(false);
                adapter.clear();
                filterableAdapter.setFinished();
                stopLoading();
            }
            if (filterableAdapter.isFinished()) {
                boolean z;
                boolean showStoreButton;
                this.mSmartProgressBar.setEmptyTextAndImageRes(filterableAdapter.getTextResId(), filterableAdapter.getImageResId());
                stopLoading();
                View view = this.mSmartProgressBar;
                if (!adapter.isEmpty() && hasConnection && hasLicence) {
                    z = false;
                } else {
                    z = true;
                }
                ResourceManager.makeControlVisible(view, z, true);
                if (hasLicence || !hasConnection) {
                    showStoreButton = false;
                } else {
                    showStoreButton = true;
                }
                ResourceManager.makeControlVisible(this.mStoreButton, showStoreButton, true);
                return;
            }
            ResourceManager.makeControlVisible(this.mSmartProgressBar, true, true);
            this.mSmartProgressBar.stop();
            if (!this.mSmartProgressBar.isRunning()) {
                this.mSmartProgressBar.startWithFadeIn();
            }
        }
    }

    private Bundle prepareLogEventForStore(String filter) {
        HashMap<String, Object> attributes = new HashMap();
        attributes.put("category", filter);
        Bundle params = new Bundle();
        params.putString("eventName", "Route overview - go to store");
        params.putSerializable("attributes", attributes);
        return params;
    }

    private void stopLoading() {
        this.mSmartProgressBar.stop();
        this.mSmartProgressBar.showEmpty();
    }

    private void setFilterTitles() {
        setSpeedCamCountText(this.mSpeedCamsAdapter.getCount());
        setTrafficDelayText();
    }

    public void onListLoaded(ArrayList<StoreEntry> entries, Boolean isUpdateRequired) {
        goToProductList(entries);
    }

    public void onProductDetail(ProductDetailEntry product) {
        goToProductDetail(product);
    }

    public void onShowError(Integer iErrorState, String strMessage) {
    }

    public void onEnterActivationCode() {
    }

    public void onShowComponents(String strId) {
    }

    public void onInstallRequired() {
    }

    public void onStoreMessage(String message) {
    }

    public int getTrafficLevelColor() {
        switch (AnonymousClass11.$SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[this.mTraffic.getLevel().ordinal()]) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131558616;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131558793;
            case TTSConst.TTSXML /*4*/:
                return 2131558515;
            default:
                return 2131558748;
        }
    }
}
