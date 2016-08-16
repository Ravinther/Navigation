package com.sygic.aura.helper;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import com.sygic.aura.resources.ResourceManager;
import java.util.Locale;

public class GuiUtils {
    public static void setupTabsWithViewPager(TabLayout tabLayout, ViewPager viewPager, Context context, int... titlesRes) {
        for (int titleRes : titlesRes) {
            tabLayout.addTab(tabLayout.newTab().setText(makeSemiboldFontText(context, titleRes, true)));
        }
        tabLayout.setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
    }

    public static void setupIconTabsWithViewPager(TabLayout tabLayout, ViewPager viewPager, int... drawablesRes) {
        for (int drawableRes : drawablesRes) {
            tabLayout.addTab(tabLayout.newTab().setIcon(drawableRes));
        }
        tabLayout.setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
    }

    public static CharSequence makeSemiboldFontText(Context context, int stringRes, boolean capitalize) {
        return makeCustomFontText(context, ResourceManager.getCoreString(context, stringRes), 2131166101, capitalize);
    }

    public static CharSequence makeSemiboldFontText(Context context, String string, boolean capitalize) {
        return makeCustomFontText(context, string, 2131166101, capitalize);
    }

    public static CharSequence makeCustomFontText(Context context, CharSequence cs, int fontRes, boolean capitalize) {
        if (context == null || cs == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 18 || !isLG()) {
            CharSequence toUpperCase;
            if (capitalize) {
                toUpperCase = cs.toString().toUpperCase(Locale.getDefault());
            } else {
                toUpperCase = cs;
            }
            CharSequence s = new SpannableString(toUpperCase);
            s.setSpan(new AssetTypefaceSpan(context, fontRes), 0, cs.length(), 33);
            return s;
        } else if (capitalize) {
            return cs.toString().toUpperCase(Locale.getDefault());
        } else {
            return cs;
        }
    }

    private static boolean isLG() {
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lg");
    }
}
