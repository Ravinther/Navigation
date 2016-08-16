package com.sygic.aura.store.fragment;

import android.animation.TimeInterpolator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.text.Editable;
import android.text.Html;
import android.text.Html.TagHandler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.clazz.TimeInfo;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.fragments.ProductCodeFragment;
import com.sygic.aura.fragments.interfaces.ProductDetailFragmentResultCallback;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.MarketPlaceEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.MarketPlaceListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.store.MarketPlaceManager;
import com.sygic.aura.store.data.ProductDetailEntry;
import com.sygic.aura.store.data.ProductDetailEntry.ActionButtonType;
import com.sygic.aura.store.data.ProductEntry.EInstallStatus;
import com.sygic.aura.store.data.ProductEntry.ELicenseStatus;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.payment.PaymentMethodHelper;
import com.sygic.aura.utils.CollectionUtils;
import com.sygic.aura.views.FloatingActionButton;
import com.sygic.aura.views.PageIndicator;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.SmartProgressBar.OnRetryCallback;
import com.sygic.aura.views.WndManager;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.aura.views.font_specials.SToolbar.OnInvalidatedMenuListener;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import loquendo.tts.engine.TTSConst;
import org.xml.sax.XMLReader;

public class ProductDetailFragment extends AbstractScreenFragment implements MarketPlaceListener {
    private static final String TAG;
    private static TimeInterpolator sInterpolator;
    private ViewGroup mAttributesContainer;
    private TextView mDescTextView;
    private TextView mDiscountView;
    private FloatingActionButton mFab;
    private final OnClickListener mFabClickListener;
    private View mFabContainer;
    private STextView mFabTextView;
    private ImageView mIconImageView;
    private boolean mInstallInvoked;
    private int mPreviewID;
    private ProductDetailEntry mProduct;
    private View mProductDetailLayout;
    private final BroadcastReceiver mReceiver;
    private boolean mRefreshOnExit;
    private final OnClickListener mScreenshotClickListener;
    private ScreenshotsPagerAdapter mScreenshotsAdapter;
    private SmartProgressBar mSmartProgressBar;
    private String mStrPreviewSoundFile;
    private String mStrProductId;
    private String mStrProductIdParent;
    private STextView mStrikedPriceTextView;
    private TextView mSubtitleTextView;
    private TextView mTitleTextView;

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ String val$strId;

        AnonymousClass10(String str) {
            this.val$strId = str;
        }

        public void run() {
            Bundle bundle = new Bundle();
            String title = ResourceManager.getCoreString(ProductDetailFragment.this.getActivity(), 2131165799);
            bundle.putString(AbstractFragment.ARG_TITLE, title);
            bundle.putString("map_list_id", this.val$strId);
            Fragments.add(ProductDetailFragment.this.getActivity(), ComponentsFragment.class, title, bundle);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.14 */
    static /* synthetic */ class AnonymousClass14 {
        static final /* synthetic */ int[] f1290xf1bff792;
        static final /* synthetic */ int[] f1291x8d477afe;

        static {
            f1290xf1bff792 = new int[ActionButtonType.values().length];
            try {
                f1290xf1bff792[ActionButtonType.BUY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1290xf1bff792[ActionButtonType.INSTALL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1290xf1bff792[ActionButtonType.MANAGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1290xf1bff792[ActionButtonType.INSTALLED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            f1291x8d477afe = new int[EErrorState.values().length];
            try {
                f1291x8d477afe[EErrorState.ESBuyProduct.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1291x8d477afe[EErrorState.ESNone.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.1 */
    class C17361 implements OnInvalidatedMenuListener {
        C17361() {
        }

        public void onMenuInvalidated(Menu menu) {
            ProductDetailFragment.this.onPrepareOptionsMenu(menu);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.2 */
    class C17372 implements OnMenuItemClickListener {
        C17372() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return ProductDetailFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.3 */
    class C17383 implements OnRetryCallback {
        C17383() {
        }

        public void onRetry() {
            ProductDetailFragment.this.loadProduct(true);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.4 */
    class C17394 implements Runnable {
        int mCounter;

        C17394() {
            this.mCounter = 0;
        }

        public void run() {
            if (!MarketPlaceManager.nativeRequestProductDetail(ProductDetailFragment.this.mStrProductId)) {
                int i = this.mCounter + 1;
                this.mCounter = i;
                if (i >= 3 || ProductDetailFragment.this.getView() == null) {
                    ProductDetailFragment.this.mSmartProgressBar.stopAndShowError();
                } else {
                    ProductDetailFragment.this.getView().postDelayed(this, 1200);
                }
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.5 */
    class C17405 implements DialogInterface.OnClickListener {
        C17405() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ProductDetailFragment.this.loadProduct(false);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.6 */
    class C17416 implements DialogInterface.OnClickListener {
        C17416() {
        }

        public void onClick(DialogInterface dialog, int which) {
            MarketPlaceManager.nativeContinueProcess();
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.7 */
    class C17427 implements TagHandler {
        final String liTag;

        C17427() {
            this.liTag = "li";
        }

        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.equals("li")) {
                output.append(opening ? "\t\u2022 " : "\n\n");
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.8 */
    class C17438 implements Runnable {
        C17438() {
        }

        public void run() {
            ProductDetailFragment.this.mFab.setVisibility(0);
            ProductDetailFragment.this.mFab.setScaleX(0.0f);
            ProductDetailFragment.this.mFab.setScaleY(0.0f);
            ProductDetailFragment.this.mFab.setRotation(-30.0f);
            ProductDetailFragment.this.mFab.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).rotation(0.0f).setDuration(175).setInterpolator(ProductDetailFragment.sInterpolator);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.ProductDetailFragment.9 */
    class C17449 implements DialogFragmentClickListener {
        C17449() {
        }

        public void onPositiveButtonClicked(Editable text) {
            MarketPlaceManager.nativeEnterProductCode(text.toString());
        }
    }

    private enum EErrorState {
        ESNone,
        ESBuyProduct
    }

    private class ScreenshotsPagerAdapter extends PagerAdapter {
        private LayoutInflater mInflater;
        private String[] mUrls;

        public ScreenshotsPagerAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public void setUrls(String[] urls) {
            this.mUrls = urls;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout imageContainer = (FrameLayout) this.mInflater.inflate(2130903154, container, false);
            imageContainer.setOnClickListener(ProductDetailFragment.this.mScreenshotClickListener);
            imageContainer.setTag(Integer.valueOf(position));
            Picasso.with(container.getContext()).load(this.mUrls[position]).into((ImageView) imageContainer.findViewById(2131624208));
            container.addView(imageContainer);
            return imageContainer;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public int getCount() {
            return this.mUrls != null ? this.mUrls.length : 0;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    static {
        TAG = ProductCodeFragment.class.getSimpleName();
        sInterpolator = new DecelerateInterpolator();
    }

    public ProductDetailFragment() {
        this.mPreviewID = 0;
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "com.sygic.aura.ACTION_PURCHASE_DONE".equals(intent.getAction())) {
                    ProductDetailFragment.this.mProductDetailLayout.setVisibility(4);
                    ProductDetailFragment.this.loadProduct(false);
                    ProductDetailFragment.this.mRefreshOnExit = true;
                }
            }
        };
        this.mScreenshotClickListener = new OnClickListener() {
            public void onClick(View v) {
                int position = ((Integer) v.getTag()).intValue();
                Rect rect = new Rect();
                v.getGlobalVisibleRect(rect);
                Bundle args = new Bundle();
                args.putInt("position", position);
                args.putStringArray("urls", ProductDetailFragment.this.mProduct.getScreenshots());
                args.putParcelable("thumb_rect", rect);
                args.putInt("thumb_width", v.getWidth());
                args.putInt("thumb_height", v.getHeight());
                Fragments.add(ProductDetailFragment.this.getActivity(), GalleryFragment.class, "fragment_gallery", args);
            }
        };
        this.mFabClickListener = new OnClickListener() {
            public void onClick(View v) {
                ProductDetailFragment.this.performFabAction();
            }
        };
        setWantsNavigationData(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mStrProductId = bundle.getString("product_id");
            this.mStrProductIdParent = bundle.getString("product_id_parent");
            this.mProduct = (ProductDetailEntry) bundle.getParcelable("product");
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.mReceiver, new IntentFilter("com.sygic.aura.ACTION_PURCHASE_DONE"));
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165319);
        toolbar.setOnMenuInvalidateListener(new C17361());
        toolbar.inflateMenu(2131755023);
        toolbar.setOnMenuItemClickListener(new C17372());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903122, container, false);
        this.mProductDetailLayout = view.findViewById(2131624111);
        this.mAttributesContainer = (ViewGroup) view.findViewById(2131624281);
        this.mIconImageView = (ImageView) view.findViewById(2131624277);
        this.mTitleTextView = (TextView) view.findViewById(2131624113);
        this.mSubtitleTextView = (TextView) view.findViewById(2131624117);
        this.mDiscountView = (TextView) view.findViewById(2131624278);
        this.mFabContainer = view.findViewById(2131624242);
        this.mFabContainer.setOnClickListener(this.mFabClickListener);
        this.mFab = (FloatingActionButton) this.mFabContainer.findViewById(2131624247);
        this.mFab.setOnClickListener(this.mFabClickListener);
        this.mFabTextView = (STextView) this.mFabContainer.findViewById(2131624248);
        this.mStrikedPriceTextView = (STextView) this.mFabContainer.findViewById(2131624279);
        this.mDescTextView = (TextView) view.findViewById(2131624280);
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        this.mSmartProgressBar.setRetryCallback(new C17383());
        this.mScreenshotsAdapter = new ScreenshotsPagerAdapter(getActivity());
        ViewPager viewPager = (ViewPager) view.findViewById(2131624213);
        viewPager.setPageMargin((int) getResources().getDimension(2131230896));
        viewPager.setAdapter(this.mScreenshotsAdapter);
        ((PageIndicator) view.findViewById(2131624214)).setViewPager(viewPager);
        MarketPlaceEventsReceiver.registerEventsListener(this);
        return view;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(2131624686);
        if (menuItem != null) {
            menuItem.setVisible(hasFunnyVoicePreview());
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624686) {
            return super.onOptionsItemSelected(item);
        }
        playPreview();
        return true;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.mProduct == null) {
            loadProduct(false);
        } else {
            onProductDetail(this.mProduct);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mReceiver);
    }

    public void onDestroyView() {
        super.onDestroyView();
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
        if (this.mResultCallback != null && (this.mResultCallback instanceof ProductDetailFragmentResultCallback)) {
            ((ProductDetailFragmentResultCallback) this.mResultCallback).onProductDetailFragmentResult(this.mRefreshOnExit);
        }
    }

    private void loadProduct(boolean reload) {
        Runnable r = new C17394();
        if (reload) {
            this.mSmartProgressBar.startWithCrossfade();
        } else {
            this.mSmartProgressBar.startWithFadeIn();
        }
        this.mSmartProgressBar.post(r);
    }

    public void onListLoaded(ArrayList<StoreEntry> arrayList, Boolean isUpdateRequired) {
    }

    public void onStoreMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            if (this.mInstallInvoked) {
                new Builder(getActivity()).title((int) C1799R.string.app_name).body(message).positiveButton(2131165355, new C17405()).build().showAllowingStateLoss("show_message");
                if (this.mSmartProgressBar != null) {
                    this.mSmartProgressBar.stopWithFadeOut();
                }
                this.mInstallInvoked = false;
                return;
            }
            new Builder(getActivity()).title((int) C1799R.string.app_name).body(message).negativeButton(2131165351, null).positiveButton(2131165355, new C17416()).build().showAllowingStateLoss("show_message");
        }
    }

    public void onConnectionChanged(Boolean connected) {
        if (connected.booleanValue()) {
            loadProduct(true);
        } else if (this.mProduct == null) {
            this.mSmartProgressBar.stopAndShowError();
        }
    }

    public void onProductDetail(ProductDetailEntry product) {
        if (product != null) {
            ActionButtonType buttonType = getActionButtonType(product);
            Picasso.with(getActivity()).load(product.getIconUrl()).placeholder(C1799R.drawable.sygic).error((int) C1799R.drawable.sygic).into(this.mIconImageView);
            this.mTitleTextView.setText(product.getTitle());
            this.mSubtitleTextView.setText(getProductStatusTitle(product));
            String productText = product.getText();
            if (productText.contains("html")) {
                this.mDescTextView.setText(Html.fromHtml(product.getText(), null, new C17427()));
            } else {
                this.mDescTextView.setText(productText);
            }
            boolean actionButtonEnabled = getActionButtonEnabled(buttonType);
            this.mFabContainer.setEnabled(actionButtonEnabled);
            this.mFab.setEnabled(actionButtonEnabled);
            this.mFab.setFontDrawableResource(getActionButtonIcon(product, buttonType));
            this.mFabTextView.setText(getActionButtonTitle(product, buttonType));
            String[] screenshotUrls = product.getScreenshots();
            if (!CollectionUtils.isEmpty(screenshotUrls)) {
                this.mScreenshotsAdapter.setUrls(screenshotUrls);
                this.mScreenshotsAdapter.notifyDataSetChanged();
            }
            if (product.getDiscount() > 0) {
                this.mDiscountView.setText(String.format("-%d%%", new Object[]{Integer.valueOf(product.getDiscount())}));
                this.mDiscountView.setVisibility(0);
            } else {
                this.mDiscountView.setVisibility(8);
            }
            String strikedPrice = product.getStrikedPrice();
            if (TextUtils.isEmpty(strikedPrice) || buttonType != ActionButtonType.BUY) {
                this.mStrikedPriceTextView.setVisibility(8);
            } else {
                this.mStrikedPriceTextView.setText(strikedPrice);
                this.mStrikedPriceTextView.setVisibility(0);
                this.mStrikedPriceTextView.setPaintFlags(this.mStrikedPriceTextView.getPaintFlags() | 16);
            }
            addAttributes(product, this.mAttributesContainer);
            if (buttonType == ActionButtonType.BUY) {
                logAddToWishlist(product);
            }
            this.mProduct = product;
            if (product.getPreviews() != null) {
                this.mStrPreviewSoundFile = MarketPlaceManager.nativeDownloadSample(product.getPreviews()[this.mPreviewID], product.getId(), this.mPreviewID);
                this.mToolbar.invalidateMenu();
            }
            runEnterAnimation();
            InfinarioAnalyticsLogger.getInstance(getActivity()).trackStoreViewProductDetail(product.getId());
        }
    }

    private void runEnterAnimation() {
        if (VERSION.SDK_INT < 16) {
            this.mFab.setVisibility(0);
            this.mSmartProgressBar.stopAndCrossfadeWith(this.mProductDetailLayout);
            return;
        }
        this.mSmartProgressBar.stopAndCrossfadeWith(this.mProductDetailLayout, new C17438());
    }

    private void playPreview() {
        if (hasFunnyVoicePreview()) {
            int i;
            SettingsManager.nativePlaySoundFile(this.mStrPreviewSoundFile);
            if (this.mPreviewID < this.mProduct.getPreviews().length - 1) {
                i = this.mPreviewID + 1;
                this.mPreviewID = i;
            } else {
                i = 0;
            }
            this.mPreviewID = i;
            this.mStrPreviewSoundFile = MarketPlaceManager.nativeDownloadSample(this.mProduct.getPreviews()[this.mPreviewID], this.mProduct.getId(), this.mPreviewID);
        }
    }

    private void performFabAction() {
        if (getActivity() != null && this.mProduct != null) {
            if (this.mProduct.getLicenseStatus() != ELicenseStatus.LsActivated || this.mProduct.getExpires() != 0) {
                PaymentMethodHelper.showPaymentDialog(getActivity(), this.mProduct);
                logAddToCart(this.mProduct);
                InfinarioAnalyticsLogger.getInstance(getActivity()).trackStoreClickBuyButton(this.mProduct.getId());
            } else if (this.mProduct.isInstallable()) {
                onShowComponents(this.mProduct.getId());
            }
        }
    }

    private void logAddToCart(ProductDetailEntry product) {
        logProduct("fb_mobile_add_to_cart", EventType.EVENT_NAME_ADDED_TO_CART, product);
    }

    private void logAddToWishlist(ProductDetailEntry product) {
        logProduct("fb_mobile_add_to_wishlist", EventType.EVENT_NAME_ADDED_TO_WISHLIST, product);
    }

    private void logProduct(String eventName, EventType eventType, ProductDetailEntry product) {
        logAnalytics(eventName, eventType, (("productId:" + product.getTitle()) + ":price:" + product.getPrice()) + ":" + WndManager.nativeGetLogParams(), null);
    }

    private void logAnalytics(String eventName, EventType evenType, String params, HashMap<String, Object> attributes) {
        Bundle logParams = new Bundle();
        logParams.putString("eventName", eventName);
        logParams.putString("fbParams", params);
        logParams.putSerializable("attributes", attributes);
        SygicAnalyticsLogger.logEvent(getActivity(), evenType, logParams);
    }

    private boolean hasFunnyVoicePreview() {
        return (this.mProduct == null || this.mStrProductIdParent == null || this.mStrPreviewSoundFile == null || (!this.mStrProductIdParent.equals("10102") && !this.mStrProductIdParent.equalsIgnoreCase("funny-voices"))) ? false : true;
    }

    public void onShowError(Integer iErrorState, String strMessage) {
        EErrorState eState = EErrorState.values()[iErrorState.intValue()];
        if (eState.ordinal() != iErrorState.intValue()) {
            throw new IllegalStateException("Enum value mismatch");
        }
        String strText = strMessage;
        switch (AnonymousClass14.f1291x8d477afe[eState.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                strText = ResourceManager.getCoreString(getActivity(), 2131165934);
                break;
        }
        if (!TextUtils.isEmpty(strText)) {
            new Builder(getActivity()).title((int) C1799R.string.title_warning).body(ResourceManager.getCoreString(strText)).negativeButton(2131165351, null).build().showAllowingStateLoss("show_error");
        }
    }

    public void onEnterActivationCode() {
        ProductCodeFragment.newInstance(getActivity(), 2131165932, null, new C17449()).showDialog();
    }

    public void onShowComponents(String strId) {
        new Handler().postDelayed(new AnonymousClass10(strId), 500);
    }

    public void onInstallRequired() {
        if (this.mSmartProgressBar != null) {
            this.mSmartProgressBar.startWithFadeIn();
        }
        this.mInstallInvoked = true;
    }

    private ActionButtonType getActionButtonType(ProductDetailEntry product) {
        ELicenseStatus licenseStatus = product.getLicenseStatus();
        EInstallStatus localInstallStatus = product.getLocalInstallStatus();
        boolean ownsProduct = (licenseStatus == ELicenseStatus.LsNone || licenseStatus == ELicenseStatus.LsExpired || licenseStatus == ELicenseStatus.LsUnsubscribed) ? false : true;
        ActionButtonType buttonType = ActionButtonType.NONE;
        if (!ownsProduct) {
            return ActionButtonType.BUY;
        }
        if (licenseStatus == ELicenseStatus.LsSubscribed) {
            return ActionButtonType.UNSUBSCRIBE;
        }
        if (localInstallStatus == EInstallStatus.IsUninstalled || localInstallStatus == EInstallStatus.IsUninstalling || localInstallStatus == EInstallStatus.IsCorrupted || localInstallStatus == EInstallStatus.IsNotInstalled) {
            if (licenseStatus == ELicenseStatus.LsActivated) {
                return ActionButtonType.MANAGE;
            }
            return ActionButtonType.INSTALL;
        } else if (licenseStatus != ELicenseStatus.LsActivated) {
            return buttonType;
        } else {
            if (product.showList() || product.hasFiles()) {
                return ActionButtonType.MANAGE;
            }
            return ActionButtonType.INSTALLED;
        }
    }

    private int getActionButtonIcon(ProductDetailEntry product, ActionButtonType buttonType) {
        switch (AnonymousClass14.f1290xf1bff792[buttonType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                if (TextUtils.isEmpty(product.getPrice())) {
                    return 2131034172;
                }
                return 2131034170;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131034172;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131034173;
            case TTSConst.TTSXML /*4*/:
                return 2131034174;
            default:
                return 2131034170;
        }
    }

    private String getActionButtonTitle(ProductDetailEntry product, ActionButtonType buttonType) {
        switch (AnonymousClass14.f1290xf1bff792[buttonType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                String price = product.getPrice();
                if (TextUtils.isEmpty(price)) {
                    return ResourceManager.getCoreString(getActivity(), 2131165935);
                }
                return price;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return ResourceManager.getCoreString(getActivity(), 2131165935);
            case TTSConst.TTSUNICODE /*3*/:
                return ResourceManager.getCoreString(getActivity(), 2131165936);
            default:
                return "";
        }
    }

    private boolean getActionButtonEnabled(ActionButtonType buttonType) {
        switch (AnonymousClass14.f1290xf1bff792[buttonType.ordinal()]) {
            case TTSConst.TTSXML /*4*/:
                return false;
            default:
                return true;
        }
    }

    private String getProductStatusTitle(ProductDetailEntry product) {
        if (product.getLicenseStatus() != ELicenseStatus.LsActivated) {
            return product.getDescription();
        }
        if (product.getExpires() == 0) {
            return ResourceManager.getCoreString(getActivity(), 2131165938);
        }
        String strValidUntil = ResourceManager.getCoreString(getActivity(), 2131165579);
        TimeInfo timeInfo = (TimeInfo) SygicHelper.getTime(product.getExpires());
        String phonesLanguage = Locale.getDefault().toString();
        List<String> countriesYMD = Arrays.asList(new String[]{"hu_HU", "zh_CN", "zh_TW", "fa_IR", "ja_JP", "lt_LT", "ko_KR", "mn_MN"});
        List<String> countriesMDY = Arrays.asList(new String[]{"en_US"});
        if (countriesYMD.contains(phonesLanguage)) {
            return strValidUntil.replace("%date%", timeInfo.getYMD());
        }
        if (countriesMDY.contains(phonesLanguage)) {
            return strValidUntil.replace("%date%", timeInfo.getMDY());
        }
        return strValidUntil.replace("%date%", timeInfo.getDMY());
    }

    private void addAttributes(ProductDetailEntry product, ViewGroup view) {
        String[] attributes = product.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.length; i++) {
                if (!TextUtils.isEmpty(attributes[i])) {
                    LinearLayout layout = (LinearLayout) LayoutInflater.from(view.getContext()).inflate(2130903157, view, false);
                    ((STextView) layout.findViewById(C1799R.id.right)).setCoreText(attributes[i]);
                    int id = 0;
                    switch (i) {
                        case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                            id = 2131165567;
                            break;
                        case TTSConst.TTSMULTILINE /*1*/:
                            id = 2131165568;
                            break;
                        case TTSConst.TTSPARAGRAPH /*2*/:
                            id = 2131165573;
                            break;
                        case TTSConst.TTSUNICODE /*3*/:
                            id = 2131165566;
                            break;
                        case TTSConst.TTSXML /*4*/:
                            id = 2131165549;
                            break;
                        default:
                            Log.d(TAG, "Unsupported product attribute");
                            break;
                    }
                    if (id != 0) {
                        ((STextView) layout.findViewById(C1799R.id.left)).setCoreText(id);
                    }
                    view.addView(layout);
                }
            }
        }
    }
}
