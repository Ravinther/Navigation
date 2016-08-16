package com.sygic.aura.helper.interfaces;

import com.sygic.aura.store.data.ProductDetailEntry;

public interface MarketPlaceListener extends StoreBaseListener {
    void onEnterActivationCode();

    void onInstallRequired();

    void onProductDetail(ProductDetailEntry productDetailEntry);

    void onShowComponents(String str);

    void onShowError(Integer num, String str);
}
