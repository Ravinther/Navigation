package com.sygic.aura.route.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.network.ConnectionManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.overview.RouteOverviewFragment.Filter;
import com.sygic.aura.views.font_specials.SImageView;
import loquendo.tts.engine.TTSConst;

public class RouteFilterLayout extends LinearLayout implements OnClickListener {
    private static final String[] ANALYTICS_CATEGORIES;
    public static final int FILTERS_COUNT;
    private static final int[] ROUTE_FILTERS_IDS;
    private final View[] mBtnFilters;
    private RouteFilterInterface mCallback;
    private int mSelectedPage;
    private SImageView mSpeedCamsLock;
    private TextView mSpeedCamsText;
    private SImageView mTrafficLock;
    private TextView mTrafficText;

    public interface RouteFilterInterface {
        void onFilterChanged(Filter filter);
    }

    /* renamed from: com.sygic.aura.route.view.RouteFilterLayout.1 */
    static /* synthetic */ class C15471 {
        static final /* synthetic */ int[] f1273x7fba8005;

        static {
            f1273x7fba8005 = new int[Filter.values().length];
            try {
                f1273x7fba8005[Filter.SPEEDCAMS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1273x7fba8005[Filter.TRAFFIC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static {
        ROUTE_FILTERS_IDS = new int[]{2131624580, 2131624582, 2131624584};
        ANALYTICS_CATEGORIES = new String[]{"traffic", "speedcams", "all"};
        FILTERS_COUNT = ROUTE_FILTERS_IDS.length;
    }

    public RouteFilterLayout(Context context) {
        super(context);
        this.mBtnFilters = new View[FILTERS_COUNT];
    }

    public RouteFilterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBtnFilters = new View[FILTERS_COUNT];
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mSelectedPage = Filter.ALL.ordinal();
        int ind = 0;
        while (ind < FILTERS_COUNT) {
            this.mBtnFilters[ind] = findViewById(ROUTE_FILTERS_IDS[ind]);
            this.mBtnFilters[ind].setTag(Integer.valueOf(ind));
            this.mBtnFilters[ind].setOnClickListener(this);
            this.mBtnFilters[ind].setSelected(ind == this.mSelectedPage);
            ind++;
        }
        this.mTrafficText = (TextView) this.mBtnFilters[Filter.TRAFFIC.ordinal()].findViewById(2131624581);
        this.mTrafficLock = (SImageView) this.mBtnFilters[Filter.TRAFFIC.ordinal()].findViewById(2131624454);
        this.mSpeedCamsText = (TextView) this.mBtnFilters[Filter.SPEEDCAMS.ordinal()].findViewById(2131624583);
        this.mSpeedCamsLock = (SImageView) this.mBtnFilters[Filter.SPEEDCAMS.ordinal()].findViewById(2131624459);
    }

    public void setMode(Filter filter) {
        if (filter != null) {
            setSelectedPage(filter.ordinal());
        }
    }

    public int getSelectedPage() {
        return this.mSelectedPage;
    }

    public void setFilterInterFace(RouteFilterInterface callback) {
        this.mCallback = callback;
    }

    public void setFilterText(Filter filter, boolean hasLicence, String message) {
        boolean z;
        boolean z2 = false;
        TextView textView = null;
        SImageView lockView = null;
        switch (C15471.f1273x7fba8005[filter.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                textView = this.mSpeedCamsText;
                lockView = this.mSpeedCamsLock;
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                textView = this.mTrafficText;
                lockView = this.mTrafficLock;
                break;
        }
        if (hasLicence && ConnectionManager.nativeIsConnected()) {
            z = true;
        } else {
            z = false;
        }
        ResourceManager.makeControlVisible(textView, z, true);
        if (!hasLicence) {
            z2 = true;
        }
        ResourceManager.makeControlVisible(lockView, z2, true);
        if (textView != null) {
            textView.setText(message);
        }
    }

    public void onClick(View view) {
        int clickedPage = ((Integer) view.getTag()).intValue();
        if (clickedPage != this.mSelectedPage) {
            setSelectedPage(clickedPage);
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "Route overview - filter");
            logParams.putString("category", ANALYTICS_CATEGORIES[this.mSelectedPage]);
            SygicAnalyticsLogger.logEvent(getContext(), EventType.CLICK, logParams);
        }
    }

    private void setSelectedPage(int page) {
        this.mSelectedPage = page;
        if (!this.mBtnFilters[this.mSelectedPage].isSelected()) {
            if (this.mSelectedPage >= 0 && this.mSelectedPage < FILTERS_COUNT) {
                int i = 0;
                while (i < FILTERS_COUNT) {
                    this.mBtnFilters[i].setSelected(i == this.mSelectedPage);
                    i++;
                }
            }
            if (this.mCallback != null) {
                this.mCallback.onFilterChanged(Filter.fromInt(this.mSelectedPage));
            }
        }
    }
}
