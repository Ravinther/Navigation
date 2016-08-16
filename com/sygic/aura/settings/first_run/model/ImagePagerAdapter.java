package com.sygic.aura.settings.first_run.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.sygic.aura.views.font_specials.STextView;
import java.util.List;

public class ImagePagerAdapter extends FragmentPagerAdapter {
    private final List<Integer> mImages;
    private final List<Integer> mTexts;

    public static class ImageFragment extends Fragment {
        public static Fragment newInstance(int imageId, int textId) {
            Fragment f = new ImageFragment();
            Bundle arguments = new Bundle(1);
            arguments.putInt("image_id", imageId);
            arguments.putInt("text_id", textId);
            f.setArguments(arguments);
            return f;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(2130903105, container, false);
            Bundle arguments = getArguments();
            ((ImageView) view.findViewById(2131624208)).setImageResource(arguments.getInt("image_id"));
            ((STextView) view.findViewById(2131624209)).setCoreText(arguments.getInt("text_id"));
            return view;
        }
    }

    public ImagePagerAdapter(FragmentManager fm, List<Integer> images, List<Integer> texts) {
        super(fm);
        this.mImages = images;
        this.mTexts = texts;
    }

    public Fragment getItem(int position) {
        return ImageFragment.newInstance(((Integer) this.mImages.get(position)).intValue(), ((Integer) this.mTexts.get(position)).intValue());
    }

    public int getCount() {
        return this.mImages.size();
    }
}
