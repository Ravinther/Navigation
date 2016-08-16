package com.sygic.aura.sos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.sos.data.EmergencyNumbers.Holder;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import java.util.List;

public class EmergencyNumbersAdapter extends ArrayAdapter<Holder> {
    private final LayoutInflater mLayoutInflater;

    private final class EmergencyNumberViewHolder {
        private SImageView icon;
        private STextView label;
        private STextView number;

        private EmergencyNumberViewHolder() {
        }

        public void init(View view) {
            this.label = (STextView) view.findViewById(2131624157);
            this.number = (STextView) view.findViewById(2131624158);
            this.icon = (SImageView) view.findViewById(2131624156);
        }

        public void setContent(Holder emergencyNumber) {
            this.label.setCoreText(emergencyNumber.getTextId());
            this.number.setText(emergencyNumber.getPhoneNumber());
            this.icon.setFontDrawableResource(emergencyNumber.getIconId());
            ((FontDrawable) this.icon.getDrawable()).setColor(this.label.getCurrentTextColor());
        }
    }

    public EmergencyNumbersAdapter(Context context, List<Holder> objects) {
        super(context, 0, objects);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        EmergencyNumberViewHolder holder;
        if (convertView == null) {
            convertView = this.mLayoutInflater.inflate(2130903093, parent, false);
            holder = new EmergencyNumberViewHolder();
            holder.init(convertView);
            convertView.setTag(holder);
        } else {
            holder = (EmergencyNumberViewHolder) convertView.getTag();
        }
        holder.setContent((Holder) getItem(position));
        return convertView;
    }
}
