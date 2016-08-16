package com.sygic.aura.dashboard;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DashboardScrollButtonsGridView extends DashboardGridView {
    private View mNextButton;
    private View mPrevButton;
    private int mScrollPosition;

    /* renamed from: com.sygic.aura.dashboard.DashboardScrollButtonsGridView.1 */
    class C11531 implements OnClickListener {
        C11531() {
        }

        public void onClick(View v) {
            if (DashboardScrollButtonsGridView.this.mScrollPosition > 0) {
                DashboardScrollButtonsGridView.this.mScrollPosition = DashboardScrollButtonsGridView.this.mScrollPosition - DashboardScrollButtonsGridView.this.getWidth();
                DashboardScrollButtonsGridView.this.onPage(DashboardScrollButtonsGridView.this.mScrollPosition);
            }
        }
    }

    /* renamed from: com.sygic.aura.dashboard.DashboardScrollButtonsGridView.2 */
    class C11542 implements OnClickListener {
        C11542() {
        }

        public void onClick(View v) {
            DashboardScrollButtonsGridView.this.mScrollPosition = DashboardScrollButtonsGridView.this.mScrollPosition + DashboardScrollButtonsGridView.this.getWidth();
            DashboardScrollButtonsGridView.this.onPage(DashboardScrollButtonsGridView.this.mScrollPosition);
        }
    }

    public DashboardScrollButtonsGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollButtons(View prev, View next) {
        this.mPrevButton = prev;
        this.mNextButton = next;
        if (this.mPrevButton != null) {
            this.mPrevButton.setOnClickListener(new C11531());
        }
        if (this.mNextButton != null) {
            this.mNextButton.setOnClickListener(new C11542());
        }
    }

    private void onPage(int x) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "scrollX", new int[]{x});
        animator.setDuration(200);
        animator.start();
    }

    protected int pointToPosition(int draggedChild, int x, int y) {
        return super.pointToPosition(draggedChild, this.mScrollPosition + x, y);
    }

    protected Rect layoutChildrens(int draggedChild, boolean animate) {
        boolean z;
        int pages = getChildCount() / ((this.mRowCount * this.mColCount) + 1);
        View view = this.mNextButton;
        if (pages > 0) {
            z = true;
        } else {
            z = false;
        }
        setButtonEnabled(view, z);
        setButtonEnabled(this.mPrevButton, false);
        return super.layoutChildrens(draggedChild, animate);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        boolean z;
        boolean z2 = true;
        super.onScrollChanged(l, t, oldl, oldt);
        int pages = getChildCount() / ((this.mRowCount * this.mColCount) + 1);
        int actualPage = l / getWidth();
        View view = this.mPrevButton;
        if (actualPage > 0) {
            z = true;
        } else {
            z = false;
        }
        setButtonEnabled(view, z);
        View view2 = this.mNextButton;
        if (actualPage == pages) {
            z2 = false;
        }
        setButtonEnabled(view2, z2);
    }

    private void setButtonEnabled(View view, boolean enabled) {
        if (view != null) {
            view.setEnabled(enabled);
            ((TextView) view).setTextColor(getResources().getColor(enabled ? 2131558794 : 2131558498));
        }
    }

    public void fullScroll(View container) {
        scrollTo(0, 0);
        this.mScrollPosition = 0;
    }
}
