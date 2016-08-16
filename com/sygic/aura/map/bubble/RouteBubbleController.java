package com.sygic.aura.map.bubble;

import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.SparseArray;
import android.view.View;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.RouteBubbleMoveListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.data.BubbleBaseInfo;
import com.sygic.aura.map.data.BubbleInfo;
import com.sygic.aura.map.view.BubbleView;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import java.util.ArrayList;

class RouteBubbleController extends BubbleController implements RouteBubbleMoveListener {
    private boolean mHighlightDistance;
    private final SparseArray<BubbleView> mRouteLabelBubbles;

    protected RouteBubbleController() {
        super(SygicHelper.getActivity());
        this.mRouteLabelBubbles = new SparseArray();
        checkHighlighting(SettingsManager.nativeGetSettings(ESettingsType.eCompute));
    }

    protected BubbleView createBubbleView(BubbleBaseInfo bubbleInfo) {
        BubbleView view = super.createBubbleView(bubbleInfo);
        CharSequence text = ((BubbleInfo) bubbleInfo).getLabel();
        view.setText(text);
        int separatorPos = text.toString().indexOf(47);
        if (separatorPos >= 0) {
            int start;
            int end;
            int flags;
            SpannableString spannable = new SpannableString(text);
            if (this.mHighlightDistance) {
                start = separatorPos;
                end = text.length();
                flags = 33;
            } else {
                start = 0;
                end = separatorPos;
                flags = 17;
            }
            spannable.setSpan(new StyleSpan(1), start, end, flags);
            view.setText(spannable);
        }
        view.registerRouteBubbleMoveListener(this);
        return view;
    }

    public boolean isVisible() {
        return this.mRouteLabelBubbles.size() > 0;
    }

    public void onClick(View view) {
        MapControlsManager.nativeOnRouteBubbleClick(getRouteIndex((BubbleInfo) view.getTag()));
    }

    public void removeBubble() {
        while (this.mRouteLabelBubbles.size() > 0) {
            removeBubble(this.mRouteLabelBubbles.keyAt(0));
        }
        this.mRouteLabelBubbles.clear();
    }

    private void removeBubble(int bubbleKey) {
        BubbleView bubble = (BubbleView) this.mRouteLabelBubbles.get(bubbleKey);
        if (bubble != null) {
            removeBubbleView(bubble);
        }
        this.mRouteLabelBubbles.remove(bubbleKey);
    }

    private void removeBubbleView(BubbleView bubble) {
        bubble.unregisterRouteBubbleMoveListener(this);
        this.mBubbleParent.removeView(bubble);
    }

    public void moveBubble() {
        for (int i = 0; i != this.mRouteLabelBubbles.size(); i++) {
            BubbleView bubbleView = (BubbleView) this.mRouteLabelBubbles.valueAt(i);
            if (bubbleView != null) {
                bubbleView.moveBubble();
            }
        }
        hideIntersectedBubbles();
    }

    public void createBubble(BubbleBaseInfo info) {
        int iCurrRouteIdx = RouteSummary.nativeGetCurrRouteIndex();
        BubbleView mapBubble = createBubbleView(info);
        if (iCurrRouteIdx != getRouteIndex(info)) {
            mapBubble.setBackgroundResource(2130837599);
            mapBubble.setTextColor(this.mContext.getResources().getColor(2131558726));
        }
        if (mapBubble != null) {
            if (((BubbleView) this.mRouteLabelBubbles.get(info.getKey())) != null) {
                if (getRouteIndex(info) != iCurrRouteIdx) {
                    removeBubbleView(mapBubble);
                    return;
                }
                removeBubble(info.getKey());
            }
            this.mRouteLabelBubbles.put(info.getKey(), mapBubble);
        }
        if (this.mRouteLabelBubbles.size() == RouteSummary.nativeGetRouteCount()) {
            hideIntersectedBubbles();
        }
    }

    public void setVisible(boolean visible) {
        int routeLabelsCount = this.mRouteLabelBubbles.size();
        for (int ind = 0; ind < routeLabelsCount; ind++) {
            makeBubbleVisible((View) this.mRouteLabelBubbles.valueAt(ind), visible);
        }
    }

    public void onRouteBubbleMoved() {
        hideIntersectedBubbles();
    }

    protected void hideIntersectedBubbles() {
        int iCurrRouteIdx = RouteSummary.nativeGetCurrRouteIndex();
        int iRouteBubblesCount = this.mRouteLabelBubbles.size();
        SparseArray<Rect> arrRect = new SparseArray(iRouteBubblesCount);
        ArrayList<Integer> arrBubblesToHide = new ArrayList();
        if (iRouteBubblesCount > 1) {
            int i;
            for (i = 0; i < iRouteBubblesCount; i++) {
                BubbleView bubbleView = (BubbleView) this.mRouteLabelBubbles.valueAt(i);
                BubbleInfo bubbleInfo = (BubbleInfo) bubbleView.getTag();
                Rect rect = new Rect();
                bubbleView.getGlobalVisibleRect(rect);
                arrRect.append(getRouteIndex(bubbleInfo), rect);
            }
            for (i = 0; i < arrRect.size() - 1; i++) {
                for (int j = i + 1; j < arrRect.size(); j++) {
                    if (Rect.intersects((Rect) arrRect.valueAt(i), (Rect) arrRect.valueAt(j))) {
                        int iBubbleToHide = getRouteBubbleIndexToHide(iCurrRouteIdx, arrRect.keyAt(i), arrRect.keyAt(j));
                        if (!arrBubblesToHide.contains(Integer.valueOf(iBubbleToHide))) {
                            arrBubblesToHide.add(Integer.valueOf(iBubbleToHide));
                        }
                    }
                }
            }
            showHideBubbles(iRouteBubblesCount, arrBubblesToHide);
        }
    }

    protected int getRouteBubbleIndexToHide(int iCurrRouteIdx, int iBubbleIndexFirst, int iBubbleIndexSecond) {
        return iCurrRouteIdx == iBubbleIndexSecond ? iBubbleIndexFirst : iBubbleIndexSecond;
    }

    protected void showHideBubbles(int iRouteBubblesCount, ArrayList<Integer> arr) {
        for (int i = 0; i != iRouteBubblesCount; i++) {
            BubbleView bubbleView = (BubbleView) this.mRouteLabelBubbles.valueAt(i);
            BubbleInfo bubbleInfo = (BubbleInfo) bubbleView.getTag();
            if (bubbleInfo != null) {
                bubbleView.setVisibility(arr.contains(Integer.valueOf(getRouteIndex(bubbleInfo))) ? 8 : 0);
            }
        }
    }

    public void checkHighlighting(int mode) {
        this.mHighlightDistance = mode > 0;
    }

    private int getRouteIndex(BubbleBaseInfo info) {
        long id = info.getId();
        if (id < -2147483648L || id > 2147483647L) {
            CrashlyticsHelper.logError("ROUTE BUBBLE ANIMATOR", "Route index is long object?!");
        }
        return (int) id;
    }

    public void onAlternativeSelected() {
        int iCurrRouteIdx = RouteSummary.nativeGetCurrRouteIndex();
        for (int i = 0; i != this.mRouteLabelBubbles.size(); i++) {
            BubbleView bubbleView = (BubbleView) this.mRouteLabelBubbles.valueAt(i);
            if (iCurrRouteIdx == getRouteIndex((BubbleInfo) bubbleView.getTag())) {
                bubbleView.setBackgroundResource(2130837598);
                bubbleView.setTextColor(this.mContext.getResources().getColor(2131558730));
            } else {
                bubbleView.setBackgroundResource(2130837599);
                bubbleView.setTextColor(this.mContext.getResources().getColor(2131558726));
            }
        }
    }
}
