package com.sygic.aura.settings;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.sygic.aura.resources.Typefaces;
import java.lang.reflect.Constructor;

public class StyleablePreferenceGroupAdapter implements ListAdapter {
    private BaseAdapter mAdapter;
    private final Context mContext;
    private final int mIconPadding;
    private final int mPadding;
    private final Resources mResources;
    private final float mSubtitleTextSize;
    private final Typeface mSubtitleTypeface;
    private final float mTitleTextSize;
    private final Typeface mTitleTypeface;

    public StyleablePreferenceGroupAdapter(Context context, PreferenceGroup preferenceGroup) {
        this.mContext = context;
        try {
            Constructor<?> c = Class.forName("android.preference.PreferenceGroupAdapter").getDeclaredConstructor(new Class[]{PreferenceGroup.class});
            c.setAccessible(true);
            this.mAdapter = (BaseAdapter) c.newInstance(new Object[]{preferenceGroup});
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mResources = context.getResources();
        this.mTitleTypeface = Typefaces.getFont(this.mContext, 2131166101);
        this.mTitleTextSize = (float) this.mResources.getDimensionPixelSize(2131230917);
        this.mSubtitleTextSize = (float) this.mResources.getDimensionPixelSize(2131230915);
        this.mSubtitleTypeface = Typefaces.getFont(this.mContext, 2131166098);
        this.mPadding = (int) this.mResources.getDimension(2131231022);
        this.mIconPadding = (int) this.mResources.getDimension(2131231020);
    }

    public boolean areAllItemsEnabled() {
        return this.mAdapter.areAllItemsEnabled();
    }

    public boolean isEnabled(int position) {
        return this.mAdapter.isEnabled(position);
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        this.mAdapter.registerDataSetObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.mAdapter.unregisterDataSetObserver(observer);
    }

    public int getCount() {
        return this.mAdapter.getCount();
    }

    public Preference getItem(int position) {
        return (Preference) this.mAdapter.getItem(position);
    }

    public long getItemId(int position) {
        return this.mAdapter.getItemId(position);
    }

    public boolean hasStableIds() {
        return this.mAdapter.hasStableIds();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.mAdapter.getView(position, convertView, parent);
        Preference preference = getItem(position);
        if (preference instanceof StyleablePreference) {
            TextView title = (TextView) view.findViewById(16908310);
            TextView summary = (TextView) view.findViewById(16908304);
            ImageView icon = (ImageView) view.findViewById(16908294);
            if (icon == null || icon.getVisibility() != 0) {
                view.setPadding(this.mPadding, 0, this.mPadding, 0);
            } else if (VERSION.SDK_INT >= 21) {
                icon.setPadding(this.mIconPadding, 0, 0, 0);
            } else {
                view.setPadding(this.mIconPadding, 0, 0, 0);
            }
            if (title != null) {
                title.setTextSize(0, this.mTitleTextSize);
                title.setTextColor(this.mResources.getColorStateList(2131558691));
                title.setTypeface(this.mTitleTypeface);
                title.setPadding(0, 0, 0, 0);
            }
            if (summary != null) {
                summary.setTextSize(0, this.mSubtitleTextSize);
                summary.setTextColor(this.mResources.getColor(2131558690));
                summary.setTypeface(this.mSubtitleTypeface);
            }
            ((StyleablePreference) preference).style(view);
        }
        return view;
    }

    public int getItemViewType(int position) {
        return this.mAdapter.getItemViewType(position);
    }

    public int getViewTypeCount() {
        return this.mAdapter.getViewTypeCount();
    }

    public boolean isEmpty() {
        return this.mAdapter.isEmpty();
    }
}
