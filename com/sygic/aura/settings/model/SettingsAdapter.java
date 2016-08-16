package com.sygic.aura.settings.model;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sygic.base.C1799R;
import java.util.Locale;

public abstract class SettingsAdapter<T> extends ArrayAdapter<T> {
    private int mResource;

    public class ViewHolder {
        protected final TextView title;

        protected ViewHolder(View rootView) {
            this.title = (TextView) rootView.findViewById(C1799R.id.title);
            SettingsAdapter.fixIssue74910(this.title, 16);
        }

        protected void update(T entry) {
            this.title.setText(SettingsAdapter.this.getItemName(entry));
        }
    }

    public abstract CharSequence getItemName(T t);

    public SettingsAdapter(Context context) {
        this(context, 2130903205);
    }

    public SettingsAdapter(Context context, int resource) {
        super(context, 0);
        this.mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.mResource, parent, false);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(getItem(position));
        return convertView;
    }

    protected com.sygic.aura.settings.model.SettingsAdapter$com.sygic.aura.settings.model.SettingsAdapter.ViewHolder createViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @SuppressLint({"RtlHardcoded"})
    @TargetApi(17)
    public static void fixIssue74910(TextView textView, int initialGravity) {
        if (VERSION.SDK_INT >= 17) {
            textView.setGravity((TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0 ? 3 : 5) | initialGravity);
        }
    }

    public static void fixIssue74910(TextView textView) {
        fixIssue74910(textView, 0);
    }
}
