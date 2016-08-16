package com.sygic.aura.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.sygic.aura.fragments.WebViewFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.poi.OnlinePoiInfoEntry.EItemType;
import com.sygic.aura.poi.adapter.FsqPoiAdapter.FsqViewHolder;
import com.sygic.aura.poi.adapter.OnlinePoiAdapter.ViewHolder;
import com.sygic.aura.poi.adapter.TripAdvisorPoiAdapter.TripAdvisorViewHolder;
import com.sygic.aura.poi.adapter.YelpPoiAdapter.YelpViewHolder;
import java.util.ArrayList;
import loquendo.tts.engine.TTSConst;

public class PoiView extends FrameLayout {
    private LayoutInflater mInflater;
    private LinearLayout mInternalLayout;
    private ArrayList<OnlinePoiInfoEntry> mItems;

    /* renamed from: com.sygic.aura.views.PoiView.1 */
    class C17811 implements OnClickListener {
        final /* synthetic */ OnlinePoiInfoEntry val$entry;

        C17811(OnlinePoiInfoEntry onlinePoiInfoEntry) {
            this.val$entry = onlinePoiInfoEntry;
        }

        public void onClick(View v) {
            PoiView.this.goToWebView(this.val$entry);
        }
    }

    /* renamed from: com.sygic.aura.views.PoiView.2 */
    class C17822 implements OnClickListener {
        final /* synthetic */ OnlinePoiInfoEntry val$entry;

        C17822(OnlinePoiInfoEntry onlinePoiInfoEntry) {
            this.val$entry = onlinePoiInfoEntry;
        }

        public void onClick(View v) {
            PoiView.this.goToWebView(this.val$entry);
        }
    }

    /* renamed from: com.sygic.aura.views.PoiView.3 */
    static /* synthetic */ class C17833 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$poi$OnlinePoiInfoEntry$EItemType;

        static {
            $SwitchMap$com$sygic$aura$poi$OnlinePoiInfoEntry$EItemType = new int[EItemType.values().length];
            try {
                $SwitchMap$com$sygic$aura$poi$OnlinePoiInfoEntry$EItemType[EItemType.Foursquare.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$OnlinePoiInfoEntry$EItemType[EItemType.TripAdvisor.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$OnlinePoiInfoEntry$EItemType[EItemType.Viator.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$OnlinePoiInfoEntry$EItemType[EItemType.Yelp.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public PoiView(Context context) {
        super(context);
        this.mItems = new ArrayList();
        init();
    }

    public PoiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mItems = new ArrayList();
        init();
    }

    public PoiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mItems = new ArrayList();
        init();
    }

    @TargetApi(21)
    public PoiView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mItems = new ArrayList();
        init();
    }

    private void init() {
        Context context = getContext();
        this.mInflater = LayoutInflater.from(context);
        this.mInternalLayout = new LinearLayout(context);
        this.mInternalLayout.setOrientation(0);
        this.mInternalLayout.setLayoutParams(new LayoutParams(-1, -2));
        this.mInternalLayout.setPadding((int) getResources().getDimension(2131230979), 0, 0, 0);
        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        scrollView.setLayoutParams(new LayoutParams(-1, -2));
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.addView(this.mInternalLayout);
        addView(scrollView);
    }

    public void addSmallListBadge(OnlinePoiInfoEntry entry) {
        if (entry != null && !this.mItems.contains(entry)) {
            ViewHolder holder = newHolder(entry);
            if (holder != null) {
                View view = holder.inflateAndUpdateSmallBadge(this.mInflater, this.mInternalLayout, entry);
                view.setOnClickListener(new C17811(entry));
                this.mItems.add(entry);
                this.mInternalLayout.addView(view);
            }
        }
    }

    private void goToWebView(OnlinePoiInfoEntry entry) {
        Bundle args = new Bundle();
        args.putString("uri", entry.getHomepage());
        Fragments.add((Activity) getContext(), WebViewFragment.class, "fragment_promo_webview", args);
    }

    public boolean isEmpty() {
        return this.mItems.isEmpty();
    }

    private ViewHolder newHolder(OnlinePoiInfoEntry entry) {
        switch (C17833.$SwitchMap$com$sygic$aura$poi$OnlinePoiInfoEntry$EItemType[entry.getType().ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return new FsqViewHolder(getResources());
            case TTSConst.TTSPARAGRAPH /*2*/:
                return new TripAdvisorViewHolder(getResources());
            case TTSConst.TTSUNICODE /*3*/:
                return new FsqViewHolder(getResources());
            case TTSConst.TTSXML /*4*/:
                return new YelpViewHolder(getResources());
            default:
                return null;
        }
    }

    public void setBigBadge(OnlinePoiInfoEntry entry) {
        ViewHolder holder = newHolder(entry);
        if (holder != null) {
            View view = holder.inflateAndUpdateBigBadge(this.mInflater, this, entry);
            setOnClickListener(new C17822(entry));
            Theme theme = getContext().getTheme();
            TypedValue outValue = new TypedValue();
            theme.resolveAttribute(16843534, outValue, true);
            if (VERSION.SDK_INT >= 21) {
                setForeground(getResources().getDrawable(outValue.resourceId, theme));
            } else {
                setForeground(getResources().getDrawable(outValue.resourceId));
            }
            if (getChildCount() > 0) {
                removeAllViews();
            }
            addView(view);
        }
    }
}
