package com.sygic.aura.store.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;
import java.util.List;

public class PaymentMethodAdapter extends ArrayAdapter<PaymentMethod> {
    private LayoutInflater inflator;

    private static class ViewHolder {
        private SImageView mLogo;
        private STextView mName;
        private RadioButton mRadio;

        public ViewHolder(View view) {
            this.mName = (STextView) view.findViewById(C1799R.id.text);
            this.mLogo = (SImageView) view.findViewById(C1799R.id.image);
            this.mRadio = (RadioButton) view.findViewById(C1799R.id.radio);
        }

        public void updateContent(Context context, PaymentMethod payment, boolean checked) {
            this.mName.setText(payment.getName());
            this.mLogo.setImageDrawable(context.getResources().getDrawable(payment.getImageRes()));
            this.mRadio.setChecked(checked);
        }
    }

    public PaymentMethodAdapter(Context context, List<PaymentMethod> list) {
        super(context, 2130903195, list);
        this.inflator = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        boolean z = false;
        if (convertView == null) {
            view = this.inflator.inflate(2130903195, parent, false);
            view.setTag(new ViewHolder(view));
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Context context = getContext();
        PaymentMethod paymentMethod = (PaymentMethod) getItem(position);
        if (position == 0) {
            z = true;
        }
        holder.updateContent(context, paymentMethod, z);
        return view;
    }
}
