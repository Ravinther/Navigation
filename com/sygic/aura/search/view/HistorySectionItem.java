package com.sygic.aura.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.RecentListItem;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

public class HistorySectionItem extends LinearLayout {
    private TextView mAddress;
    private STextView mDate;
    private ImageView mIcon;
    private TextView mName;

    /* renamed from: com.sygic.aura.search.view.HistorySectionItem.1 */
    class C15881 implements OnClickListener {
        final /* synthetic */ FavoritesFragmentResultCallback val$callback;
        final /* synthetic */ FavoritesItem val$item;

        C15881(FavoritesFragmentResultCallback favoritesFragmentResultCallback, FavoritesItem favoritesItem) {
            this.val$callback = favoritesFragmentResultCallback;
            this.val$item = favoritesItem;
        }

        public void onClick(View v) {
            if (this.val$callback != null) {
                this.val$callback.onFavoritesFragmentResult(this.val$item);
                SygicAnalyticsLogger.getAnalyticsEvent(HistorySectionItem.this.getContext(), EventType.SEARCH).setName("search").setValue("click", "recent_item_clicked").logAndRecycle();
            }
        }
    }

    public HistorySectionItem(Context context) {
        super(context);
    }

    public HistorySectionItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistorySectionItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public HistorySectionItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mName = (TextView) findViewById(2131624477);
        this.mAddress = (TextView) findViewById(2131624478);
        this.mIcon = (ImageView) findViewById(C1799R.id.image);
        this.mDate = (STextView) findViewById(2131624162);
    }

    public void setData(FavoritesItem item, FavoritesFragmentResultCallback callback) {
        this.mName.setText(item.getDisplayName());
        this.mAddress.setText(item.getAddressSummary());
        this.mDate.setText(((RecentListItem) item).getDate(getContext()));
        Drawable icon = item.getIcon(this.mIcon.getResources());
        ResourceManager.makeControlVisible(this.mIcon, icon != null);
        this.mIcon.setImageDrawable(icon);
        setOnClickListener(new C15881(callback, item));
    }
}
