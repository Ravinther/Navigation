package com.sygic.aura.poi.detail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.appcompat.C0101R;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnDismissListener;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.InputDialogFragment;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.fragments.WebViewFragment;
import com.sygic.aura.fragments.WebViewFragment.Mode;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.TransitionManagerCompat;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.helper.interfaces.PoiDetailInfoListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.poi.OnlinePoiInfoEntry.EItemType;
import com.sygic.aura.poi.PoiDetailsInfo;
import com.sygic.aura.poi.PoiDetailsInfo.PoiAttributes;
import com.sygic.aura.poi.PoiDetailsInfo.RupiData;
import com.sygic.aura.poi.ShareUtils;
import com.sygic.aura.poi.detail.badge.ExtensionsBadge;
import com.sygic.aura.poi.detail.badge.PoiBadge;
import com.sygic.aura.poi.detail.badge.PoiBadgeHelper;
import com.sygic.aura.poi.detail.badge.PoiBadgeHelper.Type;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.Typefaces;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.views.FloatingActionButton;
import com.sygic.aura.views.PoiView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.aura.views.font_specials.SToolbar.OnInvalidatedMenuListener;
import com.sygic.base.C1799R;
import com.sygic.widget.TrafficWidgetProvider;
import com.sygic.widget.WidgetDataProvider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class PoiDetailFragment extends AbstractScreenFragment implements OnScrollChangedListener, PoiDetailInfoListener {
    public static final int FADE_ANIM_DURATION = 750;
    public static final int MAX_STREET_VIEW_IMAGE_WIDTH = 640;
    public static final int MIN_STREET_VIEW_BYTES = 10000;
    private static final long NO_POI_ID = 0;
    public static final String POI_ID = "poi_id";
    public static final String POI_MENU = "poi_menu";
    public static final String POI_NO_NAVIGATE_OPTIONS = "poi_only_show";
    public static final String POI_ONLINE_TYPE = "online_type";
    public static final String POI_SEL = "poi_sel";
    public static final String POI_TITLE = "poi_title";
    public static final int SWITCHER_MAP = 0;
    public static final int SWITCHER_STREET_VIEW = 1;
    public static final String TAG = "po_detail_fragment";
    private static final int ZOOM_DISTANCE = 1000;
    private static final AccelerateInterpolator sInterpolator;
    OnClickListener emailOnClickListener;
    OnClickListener exploreNearByOnClickListener;
    OnClickListener fabOnClickListener;
    OnClickListener getDirectionsOnClickListener;
    private boolean mActionBarChanged;
    private int mActionBarHeight;
    private View mContent;
    private View mExternalPanoramaButton;
    private ViewGroup mFragmentStack;
    private boolean mFromSelectView;
    private int mImgHeight;
    private LayoutInflater mInflater;
    private boolean mIsMapPlaced;
    private boolean mIsStreetviewLoaded;
    private MapSelection mLastPinSelection;
    private int mLastScroll;
    private float mLastTilt;
    private EMapView mLastViewMode;
    private int mLocationOffset;
    private LongPosition mLongPosition;
    private ViewSwitcher mMapSwitcher;
    private int mMenuId;
    private boolean mNavigationChanged;
    private EItemType mOnlineType;
    private View mPlaceholderView;
    private View mPoiDetailNavigateToSavedPos;
    private MapSelection mPoiSelection;
    private PoiView mPoiView;
    private RatingBar mRbPoiRating;
    private ScrollView mScrollView;
    private ShareActionProvider mShareActionProvider;
    private boolean mShouldHideKeyboard;
    private boolean mShouldShowStreetView;
    private boolean mShouldUnlock;
    private String mStrBubbleText;
    private ImageView mStreetView;
    private TextView mTvDistance;
    private TextView mTvPoiCoordinates;
    private TextView mTvPoiEmail;
    private TextView mTvPoiName;
    private TextView mTvPoiPhone;
    private TextView mTvPoiUrl;
    private TextView mViewAddress;
    private View mViewContacts;
    private View mViewEmail;
    private LinearLayout mViewOtherDetails;
    private View mViewPhone;
    private View mViewUrl;
    private boolean mWasMapIn3D;
    private long mlPoiID;
    OnClickListener navigateToSavedPosOnClickListener;
    OnClickListener passByOnClickListener;
    OnClickListener phoneOnClickListener;
    OnClickListener saveCurrentPosOnClickListener;
    OnClickListener urlOnClickListener;

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.12 */
    class AnonymousClass12 implements OnGlobalLayoutListener {
        final /* synthetic */ View val$view;

        /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.12.1 */
        class C14381 implements Runnable {
            C14381() {
            }

            public void run() {
                MapControlsManager.nativeSetViewDistance(1000.0f);
                MapControlsManager.nativeSetViewRotation(0.0f);
                PoiDetailFragment.this.saveMapState();
                PoiDetailFragment.this.mLastViewMode = MapControlsManager.nativeGetMapViewMode();
                MapControlsManager.nativeSetMapViewMode(EMapView.MVPoiDetail);
                MapControlsManager.nativeShowPin(PoiDetailFragment.this.mPoiSelection);
                PoiDetailFragment.this.mWasMapIn3D = !MapControlsManager.nativeIsMapView2D();
                if (PoiDetailFragment.this.mWasMapIn3D) {
                    MapControlsManager.nativeSetMapView2D(false);
                }
                PoiDetailFragment.this.placeMap(PoiDetailFragment.SWITCHER_MAP, false);
                PoiDetailFragment.this.animateMapTransparency();
                PoiDetailFragment.this.loadStreetView();
            }
        }

        AnonymousClass12(View view) {
            this.val$view = view;
        }

        public void onGlobalLayout() {
            PoiDetailFragment.removeOnGlobalLayoutListener(this.val$view, this);
            if (PoiDetailFragment.this.isAdded()) {
                PoiDetailFragment.this.mContent = this.val$view.findViewById(2131624250);
                PoiDetailFragment.this.addPaddingIfNecessary();
                new Handler().postDelayed(new C14381(), 300);
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.14 */
    class AnonymousClass14 implements OnClickListener {
        final /* synthetic */ PoiBadge val$badge;

        AnonymousClass14(PoiBadge poiBadge) {
            this.val$badge = poiBadge;
        }

        public void onClick(View v) {
            this.val$badge.performClick(PoiDetailFragment.this.getActivity());
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.17 */
    class AnonymousClass17 implements DialogFragmentClickListener {
        final /* synthetic */ int val$itemId;

        AnonymousClass17(int i) {
            this.val$itemId = i;
        }

        public void onPositiveButtonClicked(Editable text) {
            MemoManager.nativeAddPlugin(PoiDetailFragment.this.getActivity(), PoiDetailFragment.this.mLongPosition, text.toString(), this.val$itemId == 2131624683 ? EMemoType.memoHome : EMemoType.memoWork);
            PoiDetailFragment.this.mToolbar.invalidateMenu();
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.18 */
    class AnonymousClass18 implements DialogInterface.OnClickListener {
        final /* synthetic */ boolean val$bIsItemSet;
        final /* synthetic */ MemoItem val$item;
        final /* synthetic */ int val$itemId;

        AnonymousClass18(int i, MemoItem memoItem, boolean z) {
            this.val$itemId = i;
            this.val$item = memoItem;
            this.val$bIsItemSet = z;
        }

        public void onClick(DialogInterface dialog, int which) {
            String strKey = "";
            if (this.val$itemId == 2131624684) {
                strKey = TrafficWidgetProvider.PREFERENCE_WORK_KEY;
            } else if (this.val$itemId == 2131624683) {
                strKey = TrafficWidgetProvider.PREFERENCE_HOME_KEY;
            }
            PoiDetailFragment.this.getActivity().getContentResolver().call(WidgetDataProvider.getContentUri(PoiDetailFragment.this.getActivity()), "clearItem", strKey, null);
            PoiDetailFragment.this.removeItem((int) this.val$item.getId());
            if (this.val$bIsItemSet) {
                PoiDetailFragment.this.mToolbar.invalidateMenu();
            } else {
                PoiDetailFragment.this.showHomeWorkDialog(this.val$itemId);
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.1 */
    class C14391 implements OnClickListener {
        C14391() {
        }

        public void onClick(View v) {
            String strPhoneNum = PoiDetailFragment.this.mTvPoiPhone.getText().toString();
            if (!TextUtils.isEmpty(strPhoneNum)) {
                SygicHelper.makeCall(strPhoneNum, false);
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.20 */
    class AnonymousClass20 implements OnClickListener {
        final /* synthetic */ PoiBadge val$badge;

        AnonymousClass20(PoiBadge poiBadge) {
            this.val$badge = poiBadge;
        }

        public void onClick(View v) {
            this.val$badge.performClick(PoiDetailFragment.this.getActivity());
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.21 */
    class AnonymousClass21 extends AsyncTask<Void, Void, Integer> {
        final /* synthetic */ String val$url;

        /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.21.1 */
        class C14401 implements Callback {
            C14401() {
            }

            public void onSuccess() {
                PoiDetailFragment.this.mIsStreetviewLoaded = true;
            }

            public void onError() {
            }
        }

        /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.21.2 */
        class C14412 implements OnClickListener {
            final /* synthetic */ double val$latitude;
            final /* synthetic */ double val$longitude;

            C14412(double d, double d2) {
                this.val$latitude = d;
                this.val$longitude = d2;
            }

            public void onClick(View v) {
                String localUrl = "file:///android_asset/streetView.html?lat=" + this.val$latitude + "&lng=" + this.val$longitude + "&head=" + PoiDetailFragment.this.getStreetViewHeading();
                Bundle args = new Bundle();
                args.putString("uri", localUrl);
                args.putParcelable("mode", Mode.PANORAMA);
                Fragments.add(PoiDetailFragment.this.getActivity(), WebViewFragment.class, "fragment_webview", args);
            }
        }

        AnonymousClass21(String str) {
            this.val$url = str;
        }

        protected Integer doInBackground(Void... params) {
            int contentLength = PoiDetailFragment.SWITCHER_MAP;
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) new URL(this.val$url).openConnection();
                urlConnection.setRequestMethod("HEAD");
                contentLength = Integer.parseInt(urlConnection.getHeaderField("content-length"));
                urlConnection.getInputStream().close();
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                CrashlyticsHelper.logError("STREET_VIEW", e.getMessage());
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (NumberFormatException e2) {
                CrashlyticsHelper.logError("STREET_VIEW", e2.getMessage());
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (Throwable th) {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return Integer.valueOf(contentLength);
        }

        protected void onPostExecute(Integer contentLength) {
            Activity activity = PoiDetailFragment.this.getActivity();
            if (contentLength.intValue() > PoiDetailFragment.MIN_STREET_VIEW_BYTES && activity != null) {
                Picasso.with(activity).load(this.val$url).into(PoiDetailFragment.this.mStreetView, new C14401());
                PoiDetailFragment.this.mExternalPanoramaButton.setOnClickListener(new C14412(PoiDetailFragment.this.mLongPosition.getDoubleY(), PoiDetailFragment.this.mLongPosition.getDoubleX()));
                PoiDetailFragment.this.mExternalPanoramaButton.setVisibility(PoiDetailFragment.SWITCHER_MAP);
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.22 */
    class AnonymousClass22 implements Runnable {
        final /* synthetic */ View val$view;

        AnonymousClass22(View view) {
            this.val$view = view;
        }

        public void run() {
            NaviNativeActivity.hideKeyboard(this.val$view.getWindowToken());
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.2 */
    class C14422 implements OnClickListener {
        C14422() {
        }

        public void onClick(View v) {
            String strEmail = PoiDetailFragment.this.mTvPoiEmail.getText().toString();
            if (!TextUtils.isEmpty(strEmail)) {
                Intent mailIntent = new Intent("android.intent.action.SEND");
                mailIntent.setType("text/plain");
                String[] strArr = new String[PoiDetailFragment.SWITCHER_STREET_VIEW];
                strArr[PoiDetailFragment.SWITCHER_MAP] = Uri.parse(strEmail).toString();
                mailIntent.putExtra("android.intent.extra.EMAIL", strArr);
                v.getContext().startActivity(mailIntent);
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.3 */
    class C14433 implements OnClickListener {
        C14433() {
        }

        public void onClick(View v) {
            String strUrl = PoiDetailFragment.this.mTvPoiUrl.getText().toString();
            if (!TextUtils.isEmpty(strUrl)) {
                if (!(strUrl.startsWith("http://") || strUrl.startsWith("https://"))) {
                    strUrl = "http://" + strUrl;
                }
                v.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(strUrl)));
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.4 */
    class C14444 implements OnClickListener {
        C14444() {
        }

        public void onClick(View v) {
            if (PoiDetailFragment.this.hasRouteAndIsNavselValid()) {
                PoiDetailFragment.this.finish(PoiDetailActions.ACTION_TRAVEL_VIA, PoiDetailFragment.this.mPoiSelection);
            } else {
                PoiDetailFragment.this.finish(PoiDetailActions.ACTION_DRIVE_TO, PoiDetailFragment.this.mPoiSelection);
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.5 */
    class C14455 implements OnClickListener {
        C14455() {
        }

        public void onClick(View v) {
            PoiDetailFragment.this.finish(PoiDetailActions.ACTION_PASS_BY, PoiDetailFragment.this.mPoiSelection);
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.6 */
    class C14466 implements OnClickListener {
        C14466() {
        }

        public void onClick(View v) {
            PoiDetailFragment.this.finish(PoiDetailActions.ACTION_DRIVE_TO, PoiDetailFragment.this.mPoiSelection);
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.7 */
    class C14477 implements OnClickListener {
        C14477() {
        }

        public void onClick(View v) {
            PoiDetailFragment.this.finish(PoiDetailFragment.this.mFromSelectView ? PoiDetailActions.ACTION_EXPLORE_NEARBY_SELECT : PoiDetailActions.ACTION_EXPLORE_NEARBY, PoiDetailFragment.this.mPoiSelection);
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.8 */
    class C14488 implements OnClickListener {
        C14488() {
        }

        public void onClick(View v) {
            if (SettingsManager.nativeSetSavedCarPosition(PoiDetailFragment.this.mLongPosition)) {
                SToast.makeText(PoiDetailFragment.this.getActivity(), 2131165546, PoiDetailFragment.SWITCHER_MAP).show();
                PoiDetailFragment.this.mPoiDetailNavigateToSavedPos.setEnabled(true);
            }
        }
    }

    /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.9 */
    class C14499 implements OnClickListener {
        C14499() {
        }

        public void onClick(View v) {
            LongPosition position = SettingsManager.nativeGetSavedCarPosition();
            if (position.isValid()) {
                PoiDetailFragment.this.finish(PoiDetailActions.ACTION_DRIVE_TO, new MapSelection(position.toNativeLong(), PoiDetailFragment.SWITCHER_MAP, PoiDetailFragment.NO_POI_ID));
            }
        }
    }

    private class CopyLongClickListener implements OnLongClickListener {
        private final TextView mTextView;

        /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.CopyLongClickListener.1 */
        class C14501 implements OnMenuItemClickListener {
            final /* synthetic */ BackgroundColorSpan val$bgHighlighter;
            final /* synthetic */ ForegroundColorSpan val$fgHighlighter;
            final /* synthetic */ Spannable val$highlightedText;
            final /* synthetic */ CharSequence val$text;

            C14501(Spannable spannable, BackgroundColorSpan backgroundColorSpan, ForegroundColorSpan foregroundColorSpan, CharSequence charSequence) {
                this.val$highlightedText = spannable;
                this.val$bgHighlighter = backgroundColorSpan;
                this.val$fgHighlighter = foregroundColorSpan;
                this.val$text = charSequence;
            }

            public boolean onMenuItemClick(MenuItem menuItem) {
                this.val$highlightedText.removeSpan(this.val$bgHighlighter);
                this.val$highlightedText.removeSpan(this.val$fgHighlighter);
                CopyLongClickListener.this.mTextView.setText(this.val$highlightedText);
                if (VERSION.SDK_INT >= 11) {
                    ((ClipboardManager) PoiDetailFragment.this.getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, this.val$text));
                } else {
                    ((android.text.ClipboardManager) PoiDetailFragment.this.getActivity().getSystemService("clipboard")).setText(this.val$text);
                }
                return true;
            }
        }

        /* renamed from: com.sygic.aura.poi.detail.PoiDetailFragment.CopyLongClickListener.2 */
        class C14512 implements OnDismissListener {
            final /* synthetic */ BackgroundColorSpan val$bgHighlighter;
            final /* synthetic */ ForegroundColorSpan val$fgHighlighter;
            final /* synthetic */ Spannable val$highlightedText;

            C14512(Spannable spannable, BackgroundColorSpan backgroundColorSpan, ForegroundColorSpan foregroundColorSpan) {
                this.val$highlightedText = spannable;
                this.val$bgHighlighter = backgroundColorSpan;
                this.val$fgHighlighter = foregroundColorSpan;
            }

            public void onDismiss(PopupMenu popupMenu) {
                this.val$highlightedText.removeSpan(this.val$bgHighlighter);
                this.val$highlightedText.removeSpan(this.val$fgHighlighter);
                CopyLongClickListener.this.mTextView.setText(this.val$highlightedText);
            }
        }

        public CopyLongClickListener(TextView textView) {
            this.mTextView = textView;
        }

        public boolean onLongClick(View v) {
            CharSequence text = this.mTextView.getText();
            Spannable highlightedText = new SpannableString(text);
            BackgroundColorSpan bgHighlighter = new BackgroundColorSpan(PoiDetailFragment.this.getResources().getColor(C1799R.color.azure_radiance));
            ForegroundColorSpan fgHighlighter = new ForegroundColorSpan(PoiDetailFragment.this.getResources().getColor(2131558794));
            highlightedText.setSpan(bgHighlighter, PoiDetailFragment.SWITCHER_MAP, text.length(), 33);
            highlightedText.setSpan(fgHighlighter, PoiDetailFragment.SWITCHER_MAP, text.length(), 33);
            this.mTextView.setText(highlightedText);
            PopupMenu popup = new PopupMenu(PoiDetailFragment.this.getActivity(), v);
            popup.setOnMenuItemClickListener(new C14501(highlightedText, bgHighlighter, fgHighlighter, text));
            popup.setOnDismissListener(new C14512(highlightedText, bgHighlighter, fgHighlighter));
            popup.inflate(2131755020);
            popup.show();
            return true;
        }
    }

    public PoiDetailFragment() {
        this.phoneOnClickListener = new C14391();
        this.emailOnClickListener = new C14422();
        this.urlOnClickListener = new C14433();
        this.mActionBarChanged = false;
        this.mNavigationChanged = false;
        this.fabOnClickListener = new C14444();
        this.passByOnClickListener = new C14455();
        this.getDirectionsOnClickListener = new C14466();
        this.exploreNearByOnClickListener = new C14477();
        this.saveCurrentPosOnClickListener = new C14488();
        this.navigateToSavedPosOnClickListener = new C14499();
        this.mIsMapPlaced = false;
        this.mIsStreetviewLoaded = false;
        this.mShouldShowStreetView = true;
        this.mLocationOffset = SWITCHER_MAP;
        this.mFromSelectView = false;
        this.mWasMapIn3D = false;
        this.mShouldUnlock = false;
        this.mLastScroll = SWITCHER_MAP;
        this.mShouldHideKeyboard = false;
    }

    static {
        sInterpolator = new AccelerateInterpolator();
    }

    private static void setTextOrHide(View wrapper, TextView txtView, String text) {
        boolean isEmpty = TextUtils.isEmpty(text);
        wrapper.setVisibility(isEmpty ? 8 : SWITCHER_MAP);
        if (!isEmpty) {
            txtView.setText(text);
        }
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165317);
        toolbar.setOnMenuInvalidateListener(new OnInvalidatedMenuListener() {
            public void onMenuInvalidated(Menu menu) {
                PoiDetailFragment.this.onPrepareOptionsMenu(menu);
            }
        });
        toolbar.inflateMenu(this.mMenuId);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                return PoiDetailFragment.this.onOptionsItemSelected(menuItem);
            }
        });
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        setActionIcons(menu);
    }

    private void setActionIcons(Menu menu) {
        if (this.mMenuId == 2131755021) {
            menu.findItem(2131624683).setTitle(MemoManager.nativeHasItemType(this.mLongPosition, EMemoType.memoHome) ? 2131165407 : 2131165555);
            menu.findItem(2131624684).setTitle(MemoManager.nativeHasItemType(this.mLongPosition, EMemoType.memoWork) ? 2131165408 : 2131165556);
            ResourceManager.setActionButtonIcon(getActivity(), menu.findItem(2131624681), MemoManager.nativeIsItemFavorite(this.mLongPosition) ? 2131166084 : 2131166083);
            this.mToolbar.invalidateMenuStrings();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        LongPosition longPosition;
        super.onCreate(savedInstanceState);
        loadNavigationData();
        Bundle arguments = getArguments();
        this.mlPoiID = arguments.getLong(POI_ID);
        this.mPoiSelection = (MapSelection) arguments.getParcelable(POI_SEL);
        this.mStrBubbleText = arguments.getString(POI_TITLE);
        if (this.mPoiSelection == null) {
            longPosition = LongPosition.Invalid;
        } else {
            longPosition = this.mPoiSelection.getPosition();
        }
        this.mLongPosition = longPosition;
        this.mFromSelectView = arguments.getBoolean(POI_NO_NAVIGATE_OPTIONS, false);
        this.mMenuId = arguments.getInt(POI_MENU, 2131755021);
        this.mOnlineType = (EItemType) arguments.getSerializable(POI_ONLINE_TYPE);
        if (this.mOnlineType == null) {
            this.mOnlineType = EItemType.None;
        }
        Activity activity = getActivity();
        BubbleManager.getInstance().setVisible(false);
        this.mFragmentStack = (ViewGroup) activity.findViewById(2131624520);
        if (this.mFragmentStack != null) {
            int pos = this.mFragmentStack.getChildCount();
            if (pos > SWITCHER_STREET_VIEW) {
                ViewGroup child;
                while (true) {
                    child = (ViewGroup) this.mFragmentStack.getChildAt(pos - 1);
                    if (child.getChildCount() > 0) {
                        break;
                    }
                    child.setTag(2131623944, null);
                    pos--;
                }
                for (int i = SWITCHER_MAP; i < pos; i += SWITCHER_STREET_VIEW) {
                    child = (ViewGroup) this.mFragmentStack.getChildAt(i);
                    child.setTag(2131623944, Integer.valueOf(child.getVisibility()));
                    child.setVisibility(8);
                }
            }
        }
        setHeightsForScroll();
        PoiDetailsInfo.nativeQueryOnlineInfo(this.mPoiSelection, this.mOnlineType);
    }

    private void placeMap(int scrollY) {
        placeMap(scrollY, true);
    }

    private void placeMap(int scrollY, boolean animate) {
        if (getActivity() != null && scrollY <= this.mActionBarHeight + this.mImgHeight) {
            LongPosition offsettedPosition = new LongPosition(this.mLongPosition.getX(), (this.mLongPosition.getY() - this.mLocationOffset) - (scrollY / 4));
            this.mLastScroll = scrollY;
            if (animate) {
                MapControlsManager.nativeSetWantedPosition(offsettedPosition);
            } else {
                MapControlsManager.nativeSetViewPosition(offsettedPosition);
            }
        }
    }

    private void animateMapTransparency() {
        if (VERSION.SDK_INT >= 12) {
            this.mPlaceholderView.animate().setInterpolator(sInterpolator).setStartDelay(250).setDuration(750).alpha(0.0f);
        } else {
            ViewPropertyAnimator.animate(this.mPlaceholderView).setInterpolator(sInterpolator).setStartDelay(250).setDuration(750).alpha(0.0f);
        }
    }

    private void setHeightsForScroll() {
        if (getActivity() != null) {
            int screenHeight = SygicHelper.getSurface().getHeight();
            this.mImgHeight = getResources().getDimensionPixelSize(2131230975);
            this.mActionBarHeight = getResources().getDimensionPixelSize(C0101R.dimen.abc_action_bar_default_height_material);
            this.mLocationOffset = (int) (1000.0f / (((float) screenHeight) / ((((float) screenHeight) / 2.0f) - ((float) ((this.mImgHeight / 2) + this.mActionBarHeight)))));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        if (NaviNativeActivity.isNavigationBarHidden(activity)) {
            this.mNavigationChanged = true;
            NaviNativeActivity.showNavigationBar(activity, false);
        }
        NaviNativeActivity.hideKeyboard(container.getWindowToken());
        WndEventsReceiver.registerAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        MapEventsReceiver.registerPoiDetailInfoListener(this);
        this.mInflater = inflater;
        return inflater.inflate(2130903119, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mScrollView = (ScrollView) view.findViewById(2131624236);
        this.mContent = view.findViewById(2131624250);
        this.mPlaceholderView = view.findViewById(2131624241);
        this.mViewAddress = (TextView) view.findViewById(2131624245);
        this.mTvPoiCoordinates = (TextView) view.findViewById(2131624269);
        this.mViewContacts = view.findViewById(2131624262);
        this.mViewPhone = this.mViewContacts.findViewById(2131624263);
        this.mViewEmail = this.mViewContacts.findViewById(2131624265);
        this.mViewUrl = this.mViewContacts.findViewById(2131624267);
        this.mViewOtherDetails = (LinearLayout) view.findViewById(2131624270);
        this.mTvPoiName = (TextView) view.findViewById(2131624244);
        this.mRbPoiRating = (RatingBar) view.findViewById(2131624246);
        this.mTvDistance = (TextView) view.findViewById(2131624249);
        this.mTvPoiPhone = (TextView) view.findViewById(2131624264);
        this.mTvPoiUrl = (TextView) view.findViewById(2131624268);
        this.mTvPoiEmail = (TextView) view.findViewById(2131624266);
        this.mTvPoiName.setOnLongClickListener(new CopyLongClickListener(this.mTvPoiName));
        this.mViewAddress.setOnLongClickListener(new CopyLongClickListener(this.mViewAddress));
        this.mTvPoiCoordinates.setOnLongClickListener(new CopyLongClickListener(this.mTvPoiCoordinates));
        this.mViewPhone.setOnLongClickListener(new CopyLongClickListener(this.mTvPoiPhone));
        this.mViewEmail.setOnLongClickListener(new CopyLongClickListener(this.mTvPoiEmail));
        this.mViewUrl.setOnLongClickListener(new CopyLongClickListener(this.mTvPoiUrl));
        View fabContainer = view.findViewById(2131624242);
        FloatingActionButton fab = (FloatingActionButton) fabContainer.findViewById(2131624247);
        STextView fabTextView = (STextView) fabContainer.findViewById(2131624248);
        View poiDetailGetDirections = view.findViewById(2131624253);
        View poiDetailPassBy = view.findViewById(2131624255);
        View poiDetailExploreNearBy = view.findViewById(2131624256);
        View poiDetailSaveCurrentPos = view.findViewById(2131624258);
        this.mPoiDetailNavigateToSavedPos = view.findViewById(2131624260);
        fabContainer.setOnClickListener(this.fabOnClickListener);
        fab.setOnClickListener(this.fabOnClickListener);
        poiDetailGetDirections.setOnClickListener(this.getDirectionsOnClickListener);
        poiDetailPassBy.setOnClickListener(this.passByOnClickListener);
        poiDetailExploreNearBy.setOnClickListener(this.exploreNearByOnClickListener);
        poiDetailSaveCurrentPos.setOnClickListener(this.saveCurrentPosOnClickListener);
        this.mPoiDetailNavigateToSavedPos.setOnClickListener(this.navigateToSavedPosOnClickListener);
        this.mPoiDetailNavigateToSavedPos.setEnabled(SettingsManager.nativeGetSavedCarPosition().isValid());
        this.mViewUrl.setOnClickListener(this.urlOnClickListener);
        this.mViewEmail.setOnClickListener(this.emailOnClickListener);
        this.mViewPhone.setOnClickListener(this.phoneOnClickListener);
        this.mPoiView = (PoiView) view.findViewById(2131624251);
        int i = (RouteManager.nativeExistValidRoute() && PositionInfo.nativeIsCityCenter(this.mPoiSelection)) ? SWITCHER_MAP : 8;
        poiDetailPassBy.setVisibility(i);
        if (hasRouteAndIsNavselValid()) {
            fab.setFontDrawableResource(2131034175);
            poiDetailGetDirections.setVisibility(SWITCHER_MAP);
            fabTextView.setCoreText(2131165569);
        } else {
            fab.setFontDrawableResource(2131034171);
            poiDetailGetDirections.setVisibility(8);
            fabTextView.setCoreText(2131165547);
        }
        if (this.mPoiSelection.isVehicle()) {
            poiDetailSaveCurrentPos.setVisibility(SWITCHER_MAP);
            this.mPoiDetailNavigateToSavedPos.setVisibility(SWITCHER_MAP);
            fabContainer.setVisibility(8);
            poiDetailGetDirections.setVisibility(8);
        }
        if (this.mFromSelectView) {
            poiDetailGetDirections.setVisibility(8);
            poiDetailPassBy.setVisibility(8);
            poiDetailExploreNearBy.setVisibility(8);
        }
        updateInfo();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass12(view));
        this.mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        this.mMapSwitcher = (ViewSwitcher) view.findViewById(2131624238);
        this.mMapSwitcher.setInAnimation(view.getContext(), 2130968594);
        this.mMapSwitcher.setOutAnimation(view.getContext(), 2130968595);
        this.mMapSwitcher.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (PoiDetailFragment.this.mIsStreetviewLoaded) {
                    PoiDetailFragment.this.mMapSwitcher.showNext();
                    PoiDetailFragment.this.mShouldShowStreetView = false;
                }
            }
        });
        this.mStreetView = (ImageView) view.findViewById(2131624232);
        this.mExternalPanoramaButton = view.findViewById(2131624240);
    }

    private boolean hasRouteAndIsNavselValid() {
        return RouteManager.nativeExistValidRoute() && PositionInfo.nativeHasNavSel(this.mLongPosition);
    }

    private void handleOnlineInfo(OnlinePoiInfoEntry entry) {
        if (this.mOnlineType == EItemType.None) {
            if (this.mPoiView.isEmpty()) {
                TransitionManagerCompat.beginDelayedTransition((ViewGroup) getView());
                this.mPoiView.setVisibility(SWITCHER_MAP);
            }
            this.mPoiView.addSmallListBadge(entry);
            return;
        }
        TransitionManagerCompat.beginDelayedTransition((ViewGroup) getView());
        this.mPoiView.setVisibility(SWITCHER_MAP);
        this.mPoiView.setBigBadge(entry);
    }

    private void initBadges(View view, Type type, String title, String url, int rating, int ratingMax, String bitmapPath) {
        FrameLayout badgeContainer = (FrameLayout) view.findViewById(2131624252);
        PoiBadge badge = PoiBadgeHelper.create(type, title, url, rating, ratingMax, bitmapPath);
        if (badge == null) {
            badgeContainer.setVisibility(8);
            return;
        }
        badge.injectView(LayoutInflater.from(getActivity()), badgeContainer);
        badgeContainer.setVisibility(SWITCHER_MAP);
        badgeContainer.setOnClickListener(new AnonymousClass14(badge));
    }

    private void addPaddingIfNecessary() {
        int scrollHeight = this.mScrollView.getHeight();
        int imgHeight = getResources().getDimensionPixelSize(2131230975);
        int contentHeight = this.mContent.getHeight();
        if (contentHeight + imgHeight > scrollHeight && contentHeight < scrollHeight) {
            this.mContent.setPadding(SWITCHER_MAP, SWITCHER_MAP, SWITCHER_MAP, scrollHeight - contentHeight);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        boolean z = true;
        super.onConfigurationChanged(newConfig);
        this.mIsStreetviewLoaded = false;
        if (this.mMapSwitcher.getDisplayedChild() != SWITCHER_STREET_VIEW) {
            z = false;
        }
        this.mShouldShowStreetView = z;
        displayChild(SWITCHER_MAP);
        this.mPlaceholderView.setAlpha(1.0f);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                PoiDetailFragment.this.setHeightsForScroll();
                PoiDetailFragment.this.placeMap(PoiDetailFragment.this.mScrollView.getScrollY());
                PoiDetailFragment.this.animateMapTransparency();
                PoiDetailFragment.this.loadStreetView();
            }
        }, 300);
        this.mContent.setPadding(SWITCHER_MAP, SWITCHER_MAP, SWITCHER_MAP, SWITCHER_MAP);
        this.mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                PoiDetailFragment.removeOnGlobalLayoutListener(PoiDetailFragment.this.mScrollView, this);
                PoiDetailFragment.this.addPaddingIfNecessary();
            }
        });
    }

    @TargetApi(16)
    private static void removeOnGlobalLayoutListener(View view, OnGlobalLayoutListener victim) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        if (!vto.isAlive()) {
            return;
        }
        if (VERSION.SDK_INT < 16) {
            vto.removeGlobalOnLayoutListener(victim);
        } else {
            vto.removeOnGlobalLayoutListener(victim);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 2131624681) {
            handleActionFavourite();
            return true;
        } else if (itemId == 2131624682) {
            handleShare();
            return true;
        } else if (itemId == 2131624683 || itemId == 2131624684) {
            handleAction(itemId);
            return true;
        } else if (itemId != 2131624697) {
            return super.onOptionsItemSelected(item);
        } else {
            getFragmentManager().popBackStackImmediate();
            FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
            if (mngr == null) {
                return true;
            }
            this.mRouteNavigateData.changeStart(this.mPoiSelection);
            mngr.replaceFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
            return true;
        }
    }

    private void handleShare() {
        ShareUtils.sharePoi(getActivity(), getPoiName(), this.mLongPosition);
    }

    private void showHomeWorkDialog(int itemId) {
        InputDialogFragment.newInstance(getActivity(), itemId == 2131624683 ? 2131165410 : 2131165413, this.mStrBubbleText, new AnonymousClass17(itemId)).showDialog();
    }

    private MemoItem getItemById(int itemId) {
        if (itemId == 2131624683) {
            return MemoManager.nativeGetHome();
        }
        if (itemId == 2131624684) {
            return MemoManager.nativeGetWork();
        }
        return null;
    }

    private boolean isItemSet(int itemId) {
        if (itemId == 2131624683) {
            return MemoManager.nativeHasItemType(this.mLongPosition, EMemoType.memoHome);
        }
        if (itemId == 2131624684) {
            return MemoManager.nativeHasItemType(this.mLongPosition, EMemoType.memoWork);
        }
        return false;
    }

    private void handleAction(int itemId) {
        MemoItem item = getItemById(itemId);
        if (item == null) {
            showHomeWorkDialog(itemId);
            return;
        }
        int nTitleResId;
        int nMsgBodyResId;
        String strMsgBodyInsert;
        int nNegativeButton;
        int nPositiveButton;
        boolean bIsItemSet = isItemSet(itemId);
        if (bIsItemSet) {
            if (itemId == 2131624683) {
                nTitleResId = 2131165407;
                nMsgBodyResId = 2131165416;
            } else if (itemId == 2131624684) {
                nTitleResId = 2131165408;
                nMsgBodyResId = 2131165417;
            } else {
                CrashlyticsHelper.logWarning(TAG, "trying to show dialog for non existing item");
                return;
            }
            strMsgBodyInsert = "";
            nNegativeButton = 2131165354;
            nPositiveButton = 2131165364;
        } else {
            nTitleResId = itemId == 2131624683 ? 2131165414 : 2131165415;
            nMsgBodyResId = itemId == 2131624683 ? 2131165405 : 2131165418;
            strMsgBodyInsert = item.getStrData();
            nNegativeButton = 2131165406;
            nPositiveButton = 2131165409;
        }
        Builder title = new Builder(getActivity()).title(nTitleResId);
        Object[] objArr = new Object[SWITCHER_STREET_VIEW];
        objArr[SWITCHER_MAP] = strMsgBodyInsert;
        title.formattedBody(nMsgBodyResId, objArr).negativeButton(nNegativeButton, null).positiveButton(nPositiveButton, new AnonymousClass18(itemId, item, bIsItemSet)).build().showAllowingStateLoss("remove_home/work_dialog");
    }

    private void removeItem(int itemId) {
        MemoManager.nativeRemoveItem(getActivity(), (long) itemId);
        MapEventsReceiver.onMemoRemoved(MemoManager.nativeGetWidgetIDFromMemoID(itemId));
        this.mToolbar.invalidateMenu();
    }

    private void handleActionFavourite() {
        if (MemoManager.nativeIsItemFavorite(this.mLongPosition)) {
            MemoManager.nativeRemoveMemoFavorite(getActivity(), this.mLongPosition);
            this.mToolbar.invalidateMenu();
            return;
        }
        InputDialogFragment.newInstance(getActivity(), 2131165400, this.mStrBubbleText, new DialogFragmentClickListener() {
            public void onPositiveButtonClicked(Editable text) {
                String name = text.toString();
                if (!SettingsManager.nativeEnableDebugMode(name)) {
                    MemoManager.nativeAddFavorite(PoiDetailFragment.this.getActivity(), PoiDetailFragment.this.mLongPosition, name, PoiDetailsInfo.nativeGetPoiCategory(PoiDetailFragment.this.mlPoiID));
                    PoiDetailFragment.this.mToolbar.invalidateMenu();
                }
            }
        }).showDialog();
    }

    public void clearInfo() {
        this.mTvPoiName.setText("");
        this.mTvDistance.setText("");
        this.mRbPoiRating.setRating(0.0f);
        this.mViewContacts.setVisibility(8);
        this.mViewAddress.setVisibility(8);
        this.mViewPhone.setVisibility(8);
        this.mViewUrl.setVisibility(8);
        this.mViewEmail.setVisibility(8);
    }

    public void updateInfo() {
        long lDistance;
        String strAddress;
        String strPhone = "";
        String strUrl = "";
        String strEmail = "";
        String strPoiName = this.mStrBubbleText;
        clearInfo();
        if (this.mlPoiID == NO_POI_ID) {
            this.mRbPoiRating.setVisibility(8);
            lDistance = MapControlsManager.nativeGetPointsDistance(this.mLongPosition);
            RupiData rupiData = PoiDetailsInfo.nativeGetDetailsFromRupi(this.mPoiSelection);
            if (rupiData == null) {
                strAddress = PoiDetailsInfo.nativeGetAddressFromLongposition(this.mLongPosition.toNativeLong());
            } else {
                strAddress = rupiData.mStrAddress;
                fillRupiInfo(rupiData);
                strEmail = rupiData.mStrMail;
                strPhone = rupiData.mStrPhone;
                strUrl = rupiData.mStrWeb;
                if (rupiData.mIsExtension) {
                    FrameLayout badgeContainer = (FrameLayout) getView().findViewById(2131624252);
                    PoiBadge badge = PoiBadgeHelper.create(Type.EXTENSIONS, strPoiName, rupiData.mStrCmdParams, SWITCHER_MAP, SWITCHER_MAP, null);
                    if (badge == null || !(badge instanceof ExtensionsBadge)) {
                        badgeContainer.setVisibility(8);
                    } else {
                        ((ExtensionsBadge) badge).setIconPath(rupiData.mStrIconPath).setRatingPath(rupiData.mStrRatingPath).setReviews(rupiData.mStrReviews);
                        badge.injectView(LayoutInflater.from(getActivity()), badgeContainer);
                        badgeContainer.setVisibility(SWITCHER_MAP);
                        badgeContainer.setOnClickListener(new AnonymousClass20(badge));
                    }
                }
            }
        } else {
            String strName = PoiDetailsInfo.nativeGetPoiName(this.mlPoiID);
            if (strName != null) {
                strPoiName = strName;
            }
            lDistance = PoiDetailsInfo.nativeGetPoiDistance(this.mlPoiID);
            int iRating = PoiDetailsInfo.nativeGetPoiRating(this.mlPoiID);
            boolean hasRating = iRating > 0;
            this.mRbPoiRating.setVisibility(hasRating ? SWITCHER_MAP : 8);
            if (hasRating) {
                this.mRbPoiRating.setRating((float) iRating);
            }
            strAddress = PoiDetailsInfo.nativeGetPoiDetail(this.mlPoiID, PoiAttributes.Id_Atr_Address);
            strPhone = PoiDetailsInfo.nativeGetPoiDetail(this.mlPoiID, PoiAttributes.Id_Atr_Phone);
            strUrl = PoiDetailsInfo.nativeGetPoiDetail(this.mlPoiID, PoiAttributes.Id_Atr_Url);
            strEmail = PoiDetailsInfo.nativeGetPoiDetail(this.mlPoiID, PoiAttributes.Id_Atr_Mail);
        }
        if (!TextUtils.isEmpty(strAddress)) {
            int iEndIdx = strAddress.indexOf(7);
            if (iEndIdx != -1) {
                String strStreet = strAddress.charAt(SWITCHER_MAP) == '\b' ? "" : strAddress.substring(SWITCHER_MAP, iEndIdx);
                int iStartIdx = iEndIdx + SWITCHER_STREET_VIEW;
                iEndIdx = strAddress.indexOf(7, iStartIdx);
                String strStreetNum = strAddress.charAt(iStartIdx) == '\b' ? "" : strAddress.substring(iStartIdx, iEndIdx);
                iStartIdx = iEndIdx + SWITCHER_STREET_VIEW;
                iEndIdx = strAddress.indexOf(7, iStartIdx);
                String strCity = strAddress.charAt(iStartIdx) == '\b' ? "" : strAddress.substring(iStartIdx, iEndIdx);
                String strPostal = strAddress.charAt(iStartIdx) == '\b' ? "" : strAddress.substring(iEndIdx + SWITCHER_STREET_VIEW);
                strAddress = strStreet;
                if (!TextUtils.isEmpty(strStreetNum)) {
                    strAddress = strAddress + (!TextUtils.isEmpty(strAddress) ? " " : "") + strStreetNum;
                }
                if (!TextUtils.isEmpty(strCity)) {
                    strAddress = strAddress + (!TextUtils.isEmpty(strAddress) ? "\n" : "") + strCity;
                }
                if (!(TextUtils.isEmpty(strPostal) || "\b".equalsIgnoreCase(strPostal))) {
                    strAddress = strAddress + (!TextUtils.isEmpty(strAddress) ? "\n" : "") + strPostal;
                }
            } else {
                strAddress = strAddress.replace("  ", "\n");
            }
        }
        this.mTvPoiName.setText(strPoiName);
        this.mTvDistance.setText(ResourceManager.nativeFormatDistance(lDistance));
        this.mTvDistance.setVisibility(this.mPoiSelection.isVehicle() ? 8 : SWITCHER_MAP);
        boolean isEmpty = TextUtils.isEmpty(strAddress);
        this.mViewAddress.setVisibility(isEmpty ? 8 : SWITCHER_MAP);
        if (!isEmpty) {
            this.mViewAddress.setText(strAddress);
        }
        if (!(TextUtils.isEmpty(strPhone) && TextUtils.isEmpty(strUrl) && TextUtils.isEmpty(strEmail))) {
            this.mViewContacts.setVisibility(SWITCHER_MAP);
        }
        setTextOrHide(this.mViewPhone, this.mTvPoiPhone, strPhone);
        String decodedUrl = null;
        if (!TextUtils.isEmpty(strUrl)) {
            try {
                decodedUrl = URLDecoder.decode(strUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                decodedUrl = strUrl;
            }
        }
        setTextOrHide(this.mViewUrl, this.mTvPoiUrl, decodedUrl);
        setTextOrHide(this.mViewEmail, this.mTvPoiEmail, strEmail);
        if (this.mlPoiID != NO_POI_ID) {
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_AltName);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_ShortDesc);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_LongDesc);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_SubType);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_OpenHours);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_Costs);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_BookAdvis);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_CreditCards);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_BrandNames);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_NearTrain);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_RoomCount);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_Decor);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_Breakfast);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_Takeaways);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_DisabledAccess);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_HomeDelivery);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_Conferences);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_CheckInOut);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_AccomodationType);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_HotelServices);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_SpecialFeatures);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_SeasonDate);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_Comments);
            addDetailItem(this.mlPoiID, PoiAttributes.Id_Atr_NonhotelCosts);
        }
        this.mTvPoiCoordinates.setText(ResourceManager.nativeFormatPosition(this.mLongPosition.getX(), this.mLongPosition.getY()));
    }

    public void addDetailItem(long lPoiID, PoiAttributes eAttrId) {
        int index = SWITCHER_MAP;
        String strAttrDetail = PoiDetailsInfo.nativeGetPoiDetail(lPoiID, eAttrId);
        if (!TextUtils.isEmpty(strAttrDetail)) {
            String strDetName = PoiDetailsInfo.nativeGetPoiAttrName(eAttrId);
            View detailRow = this.mInflater.inflate(2130903253, this.mViewOtherDetails, false);
            TextView vOtherLabel = (TextView) detailRow.findViewById(2131624547);
            TextView vOtherText = (TextView) detailRow.findViewById(2131624546);
            vOtherText.setOnLongClickListener(new CopyLongClickListener(vOtherText));
            vOtherLabel.setText(strDetName);
            vOtherText.setText(strAttrDetail);
            if (this.mViewOtherDetails.getVisibility() != 0) {
                this.mViewOtherDetails.setVisibility(SWITCHER_MAP);
            }
            if (this.mViewOtherDetails.getChildCount() > 0) {
                index = this.mViewOtherDetails.getChildCount() - 1;
            }
            this.mViewOtherDetails.addView(detailRow, index);
        }
    }

    private void fillRupiInfo(RupiData rupiData) {
        boolean show = false;
        Activity activity = getActivity();
        View rupiMainContainer = getView().findViewById(2131624271);
        ViewGroup rupiContainer = (ViewGroup) rupiMainContainer.findViewById(2131624272);
        rupiContainer.removeAllViews();
        if (!TextUtils.isEmpty(rupiData.mStrShortDesc)) {
            STextView tv = createRupiTextView(activity);
            tv.setText(rupiData.mStrShortDesc);
            rupiContainer.addView(tv);
            show = true;
        }
        if (!TextUtils.isEmpty(rupiData.mStrGuide)) {
            tv = createRupiTextView(activity);
            tv.setText(rupiData.mStrGuide);
            rupiContainer.addView(tv);
            show = true;
        }
        if (!TextUtils.isEmpty(rupiData.mStrCreditCards)) {
            tv = createRupiTextView(activity);
            tv.setText(rupiData.mStrCreditCards);
            rupiContainer.addView(tv);
            show = true;
        }
        if (!TextUtils.isEmpty(rupiData.mStrTrain)) {
            tv = createRupiTextView(activity);
            tv.setText(rupiData.mStrTrain);
            rupiContainer.addView(tv);
            show = true;
        }
        rupiMainContainer.setVisibility(show ? SWITCHER_MAP : 8);
    }

    private STextView createRupiTextView(Context context) {
        STextView tv = new STextView(context, null);
        tv.setTypeface(Typefaces.getFont(context, 2131166098));
        tv.setTextColor(getResources().getColor(2131558736));
        tv.setTextSize(SWITCHER_MAP, getResources().getDimension(2131230978));
        return tv;
    }

    public void finish(PoiDetailActions actionId, MapSelection mapSel) {
        boolean z = (actionId == PoiDetailActions.ACTION_TRAVEL_VIA || actionId == PoiDetailActions.ACTION_EXPLORE_NEARBY) ? false : true;
        this.mShouldUnlock = z;
        performHomeAction();
        if (this.mResultCallback != null) {
            ((PoiDetailFragmentResultCallback) this.mResultCallback).onPoiDetailFragmentResult(actionId, mapSel);
            this.mResultCallback = null;
        }
    }

    public void onDestroyView() {
        this.mScrollView.getViewTreeObserver().removeOnScrollChangedListener(this);
        super.onDestroyView();
        if (this.mNavigationChanged) {
            NaviNativeActivity.hideNavigationBar(getActivity());
        }
        WndEventsReceiver.unregisterAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        MapEventsReceiver.unregisterPoiDetailInfoListener(this);
        if (this.mLastViewMode != null) {
            MapControlsManager.nativeSetMapViewMode(this.mLastViewMode);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        BubbleManager.getInstance().setVisible(true);
        if (this.mFragmentStack != null) {
            for (int i = SWITCHER_MAP; i < this.mFragmentStack.getChildCount() - 1; i += SWITCHER_STREET_VIEW) {
                View child = this.mFragmentStack.getChildAt(i);
                Integer tag = (Integer) child.getTag(2131623944);
                if (tag != null) {
                    child.setVisibility(tag.intValue());
                    child.setTag(2131623944, null);
                }
            }
        }
        loadMapState();
        if (this.mWasMapIn3D) {
            MapControlsManager.nativeSetMapView3D();
        }
        if (this.mShouldUnlock) {
            MapControlsManager.nativeUnlockVehicle();
        }
    }

    public void onScrollChanged() {
        int scrollY = this.mScrollView.getScrollY();
        if (this.mLastScroll != scrollY) {
            placeMap(scrollY);
        }
    }

    private void saveMapState() {
        this.mLastTilt = MapControlsManager.nativeGetViewTilt();
        this.mLastPinSelection = MapControlsManager.nativeGetPinPosition();
        MapControlsManager.nativeSaveMapState(this.mLongPosition);
    }

    private void loadMapState() {
        MapControlsManager.nativeRestoreMapState();
        MapControlsManager.nativeSetWantedTilt(this.mLastTilt);
        MapControlsManager.nativeShowPin(this.mLastPinSelection);
    }

    private void loadStreetView() {
        AsyncTaskHelper.execute(new AnonymousClass21(getStreetViewUrl()));
    }

    private int getStreetViewHeading() {
        return PositionInfo.nativeGetStreetViewHeading(this.mPoiSelection);
    }

    private String getStreetViewUrl() {
        int width = this.mMapSwitcher.getWidth();
        int height = this.mMapSwitcher.getHeight();
        double latitude = this.mLongPosition.getDoubleY();
        double longitude = this.mLongPosition.getDoubleX();
        int heading = getStreetViewHeading();
        if (width > MAX_STREET_VIEW_IMAGE_WIDTH) {
            double ratio = 640.0d / ((double) width);
            width = (int) (((double) width) * ratio);
            height = (int) (((double) height) * ratio);
        }
        return "https://maps.googleapis.com/maps/api/streetview?size=" + width + "x" + height + "&location=" + latitude + "," + longitude + "&heading=" + heading;
    }

    private void displayChild(int which) {
        if (this.mMapSwitcher.getDisplayedChild() != which) {
            this.mMapSwitcher.setDisplayedChild(which);
        }
    }

    private String getPoiName() {
        String poiName = this.mStrBubbleText;
        if (this.mlPoiID == NO_POI_ID) {
            return poiName;
        }
        String name = PoiDetailsInfo.nativeGetPoiName(this.mlPoiID);
        if (name != null) {
            return name;
        }
        return poiName;
    }

    public void onResume() {
        super.onResume();
        if (this.mShouldHideKeyboard) {
            this.mShouldHideKeyboard = false;
            View view = getView();
            if (view != null) {
                view.post(new AnonymousClass22(view));
            }
        }
    }

    public void onUpdateOnlineInfo(OnlinePoiInfoEntry entry) {
        handleOnlineInfo(entry);
    }
}
