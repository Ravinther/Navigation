package com.sygic.aura.store.payment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.MarketPlaceManager;
import com.sygic.aura.store.data.ProductDetailEntry;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import loquendo.tts.engine.TTSConst;

public class PaymentMethodHelper {

    /* renamed from: com.sygic.aura.store.payment.PaymentMethodHelper.1 */
    static class C17471 implements OnClickListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ PaymentMethodAdapter val$paymentMethodsAdapter;
        final /* synthetic */ ProductDetailEntry val$product;

        C17471(Context context, PaymentMethodAdapter paymentMethodAdapter, ProductDetailEntry productDetailEntry) {
            this.val$context = context;
            this.val$paymentMethodsAdapter = paymentMethodAdapter;
            this.val$product = productDetailEntry;
        }

        public void onClick(DialogInterface dialog, int which) {
            PaymentMethodHelper.click(this.val$context, ((PaymentMethod) this.val$paymentMethodsAdapter.getItem(which)).getCode(), this.val$product);
            dialog.cancel();
        }
    }

    /* renamed from: com.sygic.aura.store.payment.PaymentMethodHelper.2 */
    static class C17482 implements OnClickListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ ArrayList val$methodList;
        final /* synthetic */ ProductDetailEntry val$product;

        C17482(Context context, ArrayList arrayList, ProductDetailEntry productDetailEntry) {
            this.val$context = context;
            this.val$methodList = arrayList;
            this.val$product = productDetailEntry;
        }

        public void onClick(DialogInterface dialog, int which) {
            PaymentMethodHelper.click(this.val$context, ((PaymentMethod) this.val$methodList.get(0)).getCode(), this.val$product);
        }
    }

    /* renamed from: com.sygic.aura.store.payment.PaymentMethodHelper.3 */
    static class C17493 implements OnClickListener {
        C17493() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    /* renamed from: com.sygic.aura.store.payment.PaymentMethodHelper.4 */
    static /* synthetic */ class C17504 {
        static final /* synthetic */ int[] f1292xdc650f3;

        static {
            f1292xdc650f3 = new int[MethodCode.values().length];
            try {
                f1292xdc650f3[MethodCode.PAYPAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1292xdc650f3[MethodCode.GOOGLE_PLAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1292xdc650f3[MethodCode.CREDIT_CARD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum MethodCode {
        PAYPAL("eshop-paypal"),
        GOOGLE_PLAY("google-play"),
        CREDIT_CARD("eshop-creditcard"),
        UNKNOWN("");
        
        private final String id;

        private MethodCode(String id) {
            this.id = id;
        }

        public String getValue() {
            return this.id;
        }

        public static MethodCode fromString(String code) {
            MethodCode[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].getValue().equals(code)) {
                    return values[i];
                }
            }
            return UNKNOWN;
        }
    }

    public static String getName(Context context, MethodCode code) {
        switch (C17504.f1292xdc650f3[code.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return ResourceManager.getCoreString(context, 2131165526);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return ResourceManager.getCoreString(context, 2131165524);
            case TTSConst.TTSUNICODE /*3*/:
                return ResourceManager.getCoreString(context, 2131165523);
            default:
                CrashlyticsHelper.logWarning("Payment_Helper", "No name for payment method");
                return "";
        }
    }

    public static int getImage(MethodCode code) {
        switch (C17504.f1292xdc650f3[code.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2130838123;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2130838128;
            case TTSConst.TTSUNICODE /*3*/:
                return 2130837628;
            default:
                return 2130838125;
        }
    }

    public static void click(Context context, MethodCode code, ProductDetailEntry product) {
        switch (C17504.f1292xdc650f3[code.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
                MarketPlaceManager.nativeBuyProduct(product.getTitle(), product.getId(), product.getStoreId(), code.getValue());
                SygicAnalyticsLogger.getAnalyticsEvent(context, EventType.STORE_PAYMENT).setName("payment_type").setValue("payment_type", code.toString()).logAndRecycle();
            default:
                CrashlyticsHelper.logWarning("Payment_Helper", "Calling not implemented payment method");
        }
    }

    public static void showPaymentDialog(Context context, ProductDetailEntry product) {
        String[] paymentMethods = product.getPaymentMethods();
        if (paymentMethods == null) {
            SToast.makeText(context, 2131165525, 0).show();
            return;
        }
        ArrayList<PaymentMethod> methodList = new ArrayList(paymentMethods.length);
        for (String code : paymentMethods) {
            PaymentMethod paymentMethod = new PaymentMethod(context, code);
            if (paymentMethod.getCode() != MethodCode.UNKNOWN) {
                methodList.add(paymentMethod);
            }
        }
        if (methodList.size() == 1) {
            click(context, ((PaymentMethod) methodList.get(0)).getCode(), product);
            return;
        }
        PaymentMethodAdapter paymentMethodsAdapter = new PaymentMethodAdapter(context, methodList);
        new Builder(context).title(ResourceManager.getCoreString(context, 2131165527)).listAdapter(paymentMethodsAdapter, new C17471(context, paymentMethodsAdapter, product)).negativeButton((int) C1799R.string.button_cancel, new C17493()).positiveButton(2131165441, new C17482(context, methodList, product)).build().showAllowingStateLoss("");
    }
}
