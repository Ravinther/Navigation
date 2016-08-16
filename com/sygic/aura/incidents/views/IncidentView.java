package com.sygic.aura.incidents.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.sygic.aura.incidents.IncidentItemsHelper.IncidentItemType;
import com.sygic.aura.incidents.IncidentsAdapter;
import com.sygic.aura.incidents.interfaces.ReportIncidentCallback;

public class IncidentView extends LinearLayout implements OnItemClickListener {
    private final int ITEMS_PER_LINE;
    private final int LANDSCAPE_RIGHT_MARGIN_DP;
    private GridView mGridView;
    private ReportIncidentCallback mIncidentCallback;

    public IncidentView(Context context) {
        super(context);
        this.ITEMS_PER_LINE = 3;
        this.LANDSCAPE_RIGHT_MARGIN_DP = 172;
    }

    public IncidentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ITEMS_PER_LINE = 3;
        this.LANDSCAPE_RIGHT_MARGIN_DP = 172;
    }

    public IncidentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ITEMS_PER_LINE = 3;
        this.LANDSCAPE_RIGHT_MARGIN_DP = 172;
    }

    @TargetApi(21)
    public IncidentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.ITEMS_PER_LINE = 3;
        this.LANDSCAPE_RIGHT_MARGIN_DP = 172;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mGridView = (GridView) findViewById(2131624360);
        init();
    }

    private void init() {
        this.mGridView.setAdapter(new IncidentsAdapter(getContext(), IncidentItemType.values()));
        this.mGridView.setNumColumns(3);
        this.mGridView.setOnItemClickListener(this);
        setWidth(getResources().getConfiguration());
    }

    public void setIncidentCallback(ReportIncidentCallback callback) {
        this.mIncidentCallback = callback;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        IncidentItemType type = ((IncidentViewHolder) view.getTag()).getIncidentType();
        if (this.mIncidentCallback != null) {
            this.mIncidentCallback.reportIncident(type);
        }
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setWidth(newConfig);
    }

    private void setWidth(Configuration newConfig) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = newConfig.orientation == 1 ? -1 : (int) (((float) metrics.widthPixels) - (172.0f * density));
        setLayoutParams(layoutParams);
    }
}
