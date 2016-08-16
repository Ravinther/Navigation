package com.sygic.aura.store.payment;

import android.content.Context;
import com.sygic.aura.store.payment.PaymentMethodHelper.MethodCode;

public class PaymentMethod {
    private MethodCode mCode;
    private String mName;

    public PaymentMethod(Context context, String code) {
        this.mCode = MethodCode.fromString(code);
        this.mName = PaymentMethodHelper.getName(context, this.mCode);
    }

    public String getName() {
        return this.mName;
    }

    public MethodCode getCode() {
        return this.mCode;
    }

    public int getImageRes() {
        return PaymentMethodHelper.getImage(this.mCode);
    }
}
