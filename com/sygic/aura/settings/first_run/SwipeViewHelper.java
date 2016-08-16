package com.sygic.aura.settings.first_run;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.View;
import com.sygic.aura.settings.first_run.model.ImagePagerAdapter;
import com.sygic.aura.views.PageIndicator;
import java.util.ArrayList;
import java.util.List;

public class SwipeViewHelper {
    private ViewPager mPager;

    public SwipeViewHelper(Context context, FragmentManager fm, View view, int picturesID, int textsID) {
        this.mPager = (ViewPager) view.findViewById(2131624497);
        PageIndicator indicator = (PageIndicator) view.findViewById(2131624214);
        TypedArray imageArray = context.getResources().obtainTypedArray(picturesID);
        TypedArray textArray = context.getResources().obtainTypedArray(textsID);
        int count = imageArray.length();
        int textCount = textArray.length();
        if (count != textCount) {
            count = Math.min(count, textCount);
            Log.w("SwipeView", "enjoy arrays of different size");
        }
        List<Integer> images = new ArrayList(count);
        List<Integer> texts = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            images.add(Integer.valueOf(imageArray.getResourceId(i, 0)));
            texts.add(Integer.valueOf(textArray.getResourceId(i, 0)));
        }
        imageArray.recycle();
        textArray.recycle();
        this.mPager.setAdapter(new ImagePagerAdapter(fm, images, texts));
        indicator.setViewPager(this.mPager);
    }

    public void addOnPageChangeListener(SimpleOnPageChangeListener listener) {
        this.mPager.addOnPageChangeListener(listener);
    }

    public int getPagerCurrentItem() {
        return this.mPager.getCurrentItem();
    }
}
