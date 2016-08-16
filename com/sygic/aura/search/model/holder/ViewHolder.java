package com.sygic.aura.search.model.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.search.model.data.SearchItem;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

public abstract class ViewHolder {
    private static final SparseArray<Drawable> mIconCache;
    protected final Context mContext;
    protected final STextView mExtTextView;
    protected final ImageView mIconView;
    protected final TextView mTextView;

    public abstract void updateContent(SearchItem searchItem, int i);

    static {
        mIconCache = new SparseArray(7);
    }

    public ViewHolder(View view) {
        this.mTextView = (TextView) view.findViewById(2131624363);
        this.mExtTextView = (STextView) view.findViewById(C1799R.id.text2);
        this.mIconView = (ImageView) view.findViewById(C1799R.id.image);
        this.mContext = view.getContext();
    }

    public static Drawable getIconDrawable(Context context, int resId) {
        Drawable drawable = (Drawable) mIconCache.get(resId);
        if (drawable != null) {
            return drawable;
        }
        drawable = FontDrawable.inflate(context.getResources(), resId);
        mIconCache.put(resId, drawable);
        return drawable;
    }
}
