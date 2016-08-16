package com.sygic.aura.dashboard;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sygic.aura.dashboard.SpanVariableGridView.CalculateChildrenPosition;
import com.sygic.aura.dashboard.SpanVariableGridView.LayoutParams;
import com.sygic.aura.views.font_specials.SImageView;
import java.util.HashSet;
import java.util.List;

public class DashboardPluginAdapter extends ArrayAdapter<DashboardPlugin> implements CalculateChildrenPosition {
    private boolean mEditMode;
    private LayoutInflater mLayoutInflater;
    private final Resources mResources;
    private HashSet<Integer> mSelected;

    public final class PluginViewHolder {
        public SImageView image;
        public SImageView imageSecondary;
        public TextView label;
        private Resources mResources;
        public View protection;

        private void init(View convertView) {
            this.mResources = convertView.getResources();
            this.label = (TextView) convertView.findViewById(2131624150);
            this.image = (SImageView) convertView.findViewById(2131624149);
            this.imageSecondary = (SImageView) convertView.findViewById(2131624152);
            this.protection = convertView.findViewById(2131624151);
        }

        public void showPluginAsLocked(boolean locked) {
            int color = locked ? 2131558567 : 2131558568;
            int visibility = locked ? 0 : 8;
            this.label.setTextColor(this.mResources.getColor(color));
            this.image.setFontDrawableColor(this.mResources.getColor(color));
            this.imageSecondary.setVisibility(visibility);
            if (this.protection != null) {
                this.protection.setVisibility(visibility);
            }
        }
    }

    public DashboardPluginAdapter(Context context, List<DashboardPlugin> plugins) {
        super(context, 0, plugins);
        this.mEditMode = false;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mSelected = new HashSet(plugins.size());
        this.mResources = context.getResources();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PluginViewHolder holder;
        if (convertView == null) {
            convertView = this.mLayoutInflater.inflate(2130903085, parent, false);
            holder = new PluginViewHolder();
            holder.init(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PluginViewHolder) convertView.getTag();
        }
        DashboardPlugin dashboardPlugin = (DashboardPlugin) getItem(position);
        dashboardPlugin.updateView(this.mResources, holder);
        int spans = dashboardPlugin.getPluginSpans();
        LayoutParams lp = new LayoutParams(convertView.getLayoutParams());
        lp.span = spans;
        convertView.setLayoutParams(lp);
        convertView.clearAnimation();
        convertView.setSelected(this.mSelected.contains(Integer.valueOf(position)));
        convertView.setEnabled(parent.isEnabled());
        return convertView;
    }

    public void onCalculatePosition(View view, int position, int row, int column) {
        ((DashboardPlugin) getItem(position)).setWidgetRowAndColumn(row, column);
    }

    public boolean getEditMode() {
        return this.mEditMode;
    }

    public void setEditMode(boolean editMode) {
        if (this.mEditMode != editMode) {
            if (!editMode) {
                this.mSelected.clear();
            }
            this.mEditMode = editMode;
            notifyDataSetChanged();
        }
    }

    public boolean hasSelected() {
        return !this.mSelected.isEmpty();
    }

    public void setSelected(int position, boolean selected) {
        if (selected) {
            this.mSelected.add(Integer.valueOf(position));
        } else {
            this.mSelected.remove(Integer.valueOf(position));
        }
    }

    public boolean isSelected(int position) {
        return this.mSelected.contains(Integer.valueOf(position));
    }

    public void insertPlugin(DashboardPlugin plugin, int where) {
        if (where >= 0 && where <= getCount() - 1) {
            insert(plugin, where);
        }
    }

    public <T extends DashboardPlugin> T getPluginByType(Class<T> clazz) {
        for (int i = 0; i < getCount(); i++) {
            DashboardPlugin item = (DashboardPlugin) getItem(i);
            if (item.getClass().isAssignableFrom(clazz)) {
                return item;
            }
        }
        return null;
    }
}
