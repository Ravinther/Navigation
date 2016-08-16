package com.mobeta.android.dslv;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import com.mobeta.android.dslv.DragSortListView.FloatViewManager;

public class SimpleFloatViewManager implements FloatViewManager {
    private int mFloatBGResource;
    private Bitmap mFloatBitmap;
    private ImageView mImageView;
    private ListView mListView;

    public SimpleFloatViewManager(ListView lv) {
        this.mFloatBGResource = 0;
        this.mListView = lv;
    }

    public void setBackgroundColor(int color) {
        this.mFloatBGResource = color;
    }

    public View onCreateFloatView(int position) {
        View v = this.mListView.getChildAt((this.mListView.getHeaderViewsCount() + position) - this.mListView.getFirstVisiblePosition());
        if (v == null) {
            return null;
        }
        v.setPressed(false);
        v.setDrawingCacheEnabled(true);
        this.mFloatBitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        if (this.mImageView == null) {
            this.mImageView = new ImageView(this.mListView.getContext());
        }
        this.mImageView.setBackgroundResource(this.mFloatBGResource);
        this.mImageView.setPadding(0, 0, 0, 0);
        this.mImageView.setImageBitmap(this.mFloatBitmap);
        this.mImageView.setLayoutParams(new LayoutParams(v.getWidth(), v.getHeight()));
        return this.mImageView;
    }

    public void onDragFloatView(View floatView, Point position, Point touch) {
    }

    public void onDestroyFloatView(View floatView) {
        ((ImageView) floatView).setImageDrawable(null);
        this.mFloatBitmap.recycle();
        this.mFloatBitmap = null;
    }
}
