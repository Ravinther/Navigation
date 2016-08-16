package com.sygic.aura.navigate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.sygic.aura.map.data.SignpostItem;
import com.sygic.aura.map.data.SignpostItem.ESignPostsType;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import loquendo.tts.engine.TTSConst;

public class SignPostHolder {
    private final LayoutInflater mInflater;
    private TextView mLabelTextView;
    private int mOrientation;
    private final FontDrawable mPictogramDrawable;
    private ImageView mPictogramView;
    private ViewGroup mRouteSignsView;
    private ViewGroup mSignpost;
    private SignpostItem[] mSignpostItems;
    private String mStrBuilder;

    /* renamed from: com.sygic.aura.navigate.SignPostHolder.1 */
    static /* synthetic */ class C13861 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$data$SignpostItem$ESignPostsType;

        static {
            $SwitchMap$com$sygic$aura$map$data$SignpostItem$ESignPostsType = new int[ESignPostsType.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$data$SignpostItem$ESignPostsType[ESignPostsType.SIGNPOST_TYPE_PLATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$SignpostItem$ESignPostsType[ESignPostsType.SIGNPOST_TYPE_LABEL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$SignpostItem$ESignPostsType[ESignPostsType.SIGNPOST_TYPE_ROAD_SIGN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$SignpostItem$ESignPostsType[ESignPostsType.SIGNPOST_TYPE_PICTOGRAM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public SignPostHolder(ViewGroup signpost, Context context) {
        this.mStrBuilder = "";
        this.mSignpost = signpost;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mPictogramDrawable = (FontDrawable) FontDrawable.inflate(this.mSignpost.getResources(), 2131034222);
        switch (((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mOrientation = 1;
                break;
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSUNICODE /*3*/:
                break;
        }
        this.mOrientation = 2;
        this.mOrientation = 0;
        findViews();
        updateSignPosts();
    }

    public void onConfigurationChanged(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            ViewGroup parent = (ViewGroup) this.mSignpost.getParent();
            int index = parent.indexOfChild(this.mSignpost);
            parent.removeView(this.mSignpost);
            this.mSignpost = (ViewGroup) this.mInflater.inflate(2130903207, parent, false);
            if (this.mSignpost != null) {
                parent.addView(this.mSignpost, index);
            }
            findViews();
            updateSignPosts();
        }
    }

    private void findViews() {
        this.mLabelTextView = (TextView) this.mSignpost.findViewById(2131624484);
        this.mPictogramView = (ImageView) this.mSignpost.findViewById(2131624482);
        this.mRouteSignsView = (ViewGroup) this.mSignpost.findViewById(2131624485);
    }

    private void updateSignPosts() {
        updateSignPostItems(this.mSignpostItems, true);
    }

    public void updateSignPostItems(SignpostItem[] signpostItems) {
        updateSignPostItems(signpostItems, false);
    }

    public void setSignpostPlateVisible(boolean visible) {
        ResourceManager.makeControlVisible(this.mSignpost, visible, true);
    }

    private void updateSignPostItems(SignpostItem[] signpostItems, boolean forceRedraw) {
        ResourceManager.makeControlVisible(this.mPictogramView, false, true);
        ResourceManager.makeControlVisible(this.mRouteSignsView, false, true);
        this.mRouteSignsView.removeAllViews();
        this.mStrBuilder = "";
        if (this.mSignpostItems != null && (signpostItems == null || signpostItems.length != this.mSignpostItems.length)) {
            this.mSignpostItems = null;
        }
        updateSignPosts(signpostItems, forceRedraw);
        this.mSignpostItems = signpostItems;
    }

    private void updateSignPosts(SignpostItem[] signpostItems, boolean forceRedraw) {
        boolean z = false;
        if (signpostItems != null) {
            if (SettingsManager.nativeGetSettings(ESettingsType.eSignposts) == 0) {
                ResourceManager.makeControlVisible(this.mSignpost, false, true);
                return;
            }
            int signpostCount = signpostItems.length;
            View view = this.mSignpost;
            if (signpostCount > 0) {
                z = true;
            }
            ResourceManager.makeControlVisible(view, z, true);
            for (int i = 0; i < signpostCount; i++) {
                if (signpostItems[i] != null) {
                    updateSignPost(signpostItems[i], i, forceRedraw);
                }
            }
        }
    }

    private void updateSignPost(SignpostItem signpostItem, int index, boolean forceRedraw) {
        switch (C13861.$SwitchMap$com$sygic$aura$map$data$SignpostItem$ESignPostsType[signpostItem.getType().ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                if (forceRedraw || this.mSignpostItems == null || signpostItem.getBackgroundColor() != this.mSignpostItems[index].getBackgroundColor()) {
                    ((GradientDrawable) this.mSignpost.getBackground()).setColor(ABGRtoARGB(signpostItem.getBackgroundColor()));
                }
            case TTSConst.TTSPARAGRAPH /*2*/:
                if (forceRedraw || this.mSignpostItems == null || !signpostItem.getText().equals(this.mSignpostItems[index].getText()) || signpostItem.getColor() != this.mSignpostItems[index].getColor()) {
                    if (this.mStrBuilder.indexOf(signpostItem.getText()) < 0) {
                        this.mStrBuilder = this.mStrBuilder.concat(signpostItem.getText());
                    }
                    this.mLabelTextView.setText(this.mStrBuilder.toString());
                    this.mLabelTextView.setTextColor(signpostItem.getColor());
                }
            case TTSConst.TTSUNICODE /*3*/:
                ResourceManager.makeControlVisible(this.mRouteSignsView, true);
                TextView sign = (TextView) this.mInflater.inflate(2130903270, this.mRouteSignsView, false);
                int symbol = signpostItem.getSymbol();
                if (symbol != 0) {
                    FontDrawable drawable = (FontDrawable) FontDrawable.inflate(this.mRouteSignsView.getResources(), 2131034271, Character.toString((char) symbol));
                    drawable.setColor(ABGRtoARGB(signpostItem.getBackgroundColor()));
                    if (VERSION.SDK_INT >= 16) {
                        sign.setBackground(drawable);
                    } else {
                        sign.setBackgroundDrawable(drawable);
                    }
                } else {
                    sign.setBackgroundResource(2130838164);
                    ((GradientDrawable) sign.getBackground()).setColor(ABGRtoARGB(signpostItem.getBackgroundColor()));
                }
                sign.setText(signpostItem.getText());
                sign.setTextColor(ABGRtoARGB(signpostItem.getColor()));
                this.mRouteSignsView.addView(sign);
            case TTSConst.TTSXML /*4*/:
                this.mPictogramDrawable.setText(signpostItem.getText());
                this.mPictogramDrawable.setColor(signpostItem.getColor());
                this.mPictogramView.setImageDrawable(this.mPictogramDrawable);
                ResourceManager.makeControlVisible(this.mPictogramView, true);
            default:
        }
    }

    private static int ABGRtoARGB(int abgr) {
        return Color.argb(Color.alpha(abgr), Color.blue(abgr), Color.green(abgr), Color.red(abgr));
    }
}
