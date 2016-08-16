package com.sygic.aura.store.model.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.data.ProductEntry;
import com.sygic.aura.store.data.ProductEntry.ELicenseStatus;
import com.sygic.aura.store.data.ProductEntry.EPurchase;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ViewHolderProduct extends StoreHolder {
    private final Context mContext;
    private final TextView mDiscountView;
    private final Drawable mDrawable;
    private final TextView mPriceView;
    private final TextView mStrikedView;
    private final ViewSwitcher mSwitcher;

    public ViewHolderProduct(View view, Drawable icon) {
        super(view);
        this.mDrawable = icon;
        this.mSwitcher = (ViewSwitcher) view.findViewById(2131624493);
        this.mDiscountView = (TextView) view.findViewById(2131624496);
        this.mPriceView = (TextView) this.mSwitcher.findViewById(2131624494);
        this.mStrikedView = (TextView) this.mSwitcher.findViewById(2131624495);
        this.mContext = view.getContext();
    }

    public void updateContent(StoreEntry item) {
        this.mTitle.setText(item.getTitle());
        this.mDescription.setVisibility(8);
        loadIcon(item, true);
        ProductEntry product = (ProductEntry) item;
        if (product.getLicenseStatus() == ELicenseStatus.LsNone) {
            String strPrice;
            int i;
            if (product.getPurchase() == EPurchase.PFree) {
                strPrice = ResourceManager.getCoreString(this.mContext, 2131165933);
            } else {
                strPrice = product.getPrice();
            }
            this.mDiscountView.setText(String.format("-%d%%", new Object[]{Integer.valueOf(product.getDiscount())}));
            TextView textView = this.mDiscountView;
            if (product.getDiscount() > 0) {
                i = 0;
            } else {
                i = 8;
            }
            textView.setVisibility(i);
            String strikedPrice = product.getStrikedPrice();
            if (TextUtils.isEmpty(strikedPrice)) {
                this.mStrikedView.setVisibility(8);
            } else {
                this.mStrikedView.setText(strikedPrice);
                this.mStrikedView.setVisibility(0);
                this.mStrikedView.setPaintFlags(this.mStrikedView.getPaintFlags() | 16);
            }
            this.mSwitcher.setDisplayedChild(0);
            this.mPriceView.setText(strPrice);
        } else {
            this.mSwitcher.setDisplayedChild(1);
            this.mStatusIcon.setImageDrawable(this.mDrawable);
        }
        String desc = product.getDescription();
        if (!TextUtils.isEmpty(desc)) {
            this.mDescription.setVisibility(0);
            this.mDescription.setText(desc);
        }
    }

    public EViewType getType() {
        return EViewType.TYPE_PRODUCT;
    }
}
