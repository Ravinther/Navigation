package com.sygic.aura.search.model.holder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.QuickSearchItem;
import com.sygic.aura.search.model.data.QuickSearchItem.IconType;
import com.sygic.aura.search.model.data.QuickSearchItem.ItemType;
import com.sygic.aura.search.model.data.SearchItem;
import java.util.Locale;
import loquendo.tts.engine.TTSConst;

public class ViewHolderQuick extends ViewHolder {
    private final TextView mDistance;

    /* renamed from: com.sygic.aura.search.model.holder.ViewHolderQuick.1 */
    static /* synthetic */ class C15841 {
        static final /* synthetic */ int[] f1280xd28805a7;
        static final /* synthetic */ int[] f1281x44b36741;

        static {
            f1280xd28805a7 = new int[IconType.values().length];
            try {
                f1280xd28805a7[IconType.ICON_COUNTRY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1280xd28805a7[IconType.ICON_CITY_CAPITAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1280xd28805a7[IconType.ICON_CITY_BIG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1280xd28805a7[IconType.ICON_CITY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1280xd28805a7[IconType.ICON_MAP_STREET.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1280xd28805a7[IconType.ICON_STREET_CLOSED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1280xd28805a7[IconType.ICON_STREET_CITY_CENTER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1280xd28805a7[IconType.ICON_STREET.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1280xd28805a7[IconType.ICON_NEARBY_POI.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f1280xd28805a7[IconType.ICON_CROSSING.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f1280xd28805a7[IconType.ICON_GOOGLE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f1280xd28805a7[IconType.ICON_4SQUARE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                f1280xd28805a7[IconType.ICON_YELP.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                f1280xd28805a7[IconType.ICON_VIATOR.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                f1280xd28805a7[IconType.ICON_FAVORITES.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                f1280xd28805a7[IconType.ICON_GPS_COORDINATES.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            f1281x44b36741 = new int[ItemType.values().length];
            try {
                f1281x44b36741[ItemType.ITEM_GOOGLE_SEARCH.ordinal()] = 1;
            } catch (NoSuchFieldError e17) {
            }
            try {
                f1281x44b36741[ItemType.ITEM_4SQUARE_SEARCH.ordinal()] = 2;
            } catch (NoSuchFieldError e18) {
            }
            try {
                f1281x44b36741[ItemType.ITEM_YELP_SEARCH.ordinal()] = 3;
            } catch (NoSuchFieldError e19) {
            }
            try {
                f1281x44b36741[ItemType.ITEM_VIATOR_SEARCH.ordinal()] = 4;
            } catch (NoSuchFieldError e20) {
            }
        }
    }

    public ViewHolderQuick(View view) {
        super(view);
        this.mDistance = (TextView) view.findViewById(2131624119);
    }

    public void updateContent(SearchItem item, int position) {
        Drawable drawable = null;
        QuickSearchItem searchItem = (QuickSearchItem) item;
        if (searchItem != null) {
            switch (C15841.f1281x44b36741[searchItem.getQuickItemType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    if (searchItem.isSubmenuItem()) {
                        searchItem.setDisplayName(ResourceManager.getCoreString(this.mContext, 2131165662));
                        break;
                    }
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (searchItem.isSubmenuItem()) {
                        searchItem.setDisplayName(ResourceManager.getCoreString(this.mContext, 2131165651));
                        break;
                    }
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    if (searchItem.isSubmenuItem()) {
                        searchItem.setDisplayName(ResourceManager.getCoreString(this.mContext, 2131165689));
                        break;
                    }
                    break;
                case TTSConst.TTSXML /*4*/:
                    if (searchItem.isSubmenuItem()) {
                        searchItem.setDisplayName(ResourceManager.getCoreString(this.mContext, 2131165688));
                        break;
                    }
                    break;
            }
            this.mTextView.setText(searchItem.getDisplayName());
            if (searchItem.hasExtName()) {
                this.mExtTextView.setText(searchItem.getExtName());
                this.mExtTextView.setVisibility(0);
            } else {
                this.mExtTextView.setVisibility(8);
            }
            switch (C15841.f1280xd28805a7[searchItem.getIcon().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    String strIso = searchItem.getIso();
                    if (!TextUtils.isEmpty(strIso)) {
                        Resources resources = this.mContext.getResources();
                        int iconId = resources.getIdentifier("flg_" + strIso.toLowerCase(Locale.ENGLISH), "drawable", this.mContext.getPackageName());
                        ImageView imageView = this.mIconView;
                        if (iconId != 0) {
                            drawable = resources.getDrawable(iconId);
                        }
                        imageView.setImageDrawable(drawable);
                        break;
                    }
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034279));
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                case TTSConst.TTSXML /*4*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034278));
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                case TTSConst.TTSEVT_TAG /*8*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034285));
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    FontDrawable fontDrawable;
                    String strIcon = searchItem.getPoiIcon();
                    if (TextUtils.isEmpty(strIcon)) {
                        fontDrawable = (FontDrawable) ViewHolder.getIconDrawable(this.mContext, 2131034283);
                    } else {
                        fontDrawable = (FontDrawable) FontDrawable.inflate(this.mContext.getResources(), 2131034283, strIcon);
                    }
                    this.mIconView.setImageDrawable(fontDrawable);
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034280));
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034282));
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034277));
                    break;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034289));
                    break;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    this.mIconView.setImageDrawable(ViewHolder.getIconDrawable(this.mContext, 2131034288));
                    break;
                default:
                    this.mIconView.setImageDrawable(null);
                    break;
            }
            setupDistance(searchItem.getDistance());
            this.mIconView.setVisibility(0);
        }
    }

    private void setupDistance(long lDistance) {
        if (lDistance > 0) {
            this.mDistance.setVisibility(0);
            this.mDistance.setText(ResourceManager.nativeFormatDistance(lDistance));
            return;
        }
        this.mDistance.setVisibility(8);
    }
}
