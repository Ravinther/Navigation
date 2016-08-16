package com.sygic.aura.search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.views.font_specials.STextView;
import loquendo.tts.engine.TTSConst;

public class SearchBubble extends STextView {
    private final Animation mAddBubbleAnim;
    private final Animation mDelBubbleAnim;
    private int mIntToRemove;
    private final int mSearchBarTextGreyColor;

    /* renamed from: com.sygic.aura.search.view.SearchBubble.1 */
    class C15971 implements OnClickListener {
        final /* synthetic */ SearchBar val$searchBar;

        C15971(SearchBar searchBar) {
            this.val$searchBar = searchBar;
        }

        public void onClick(View view) {
            this.val$searchBar.requestFocus();
            this.val$searchBar.scrollRow();
            NaviNativeActivity.showKeyboard(view);
        }
    }

    public SearchBubble(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIntToRemove = 0;
        this.mAddBubbleAnim = AnimationUtils.loadAnimation(context, 2130968586);
        this.mDelBubbleAnim = AnimationUtils.loadAnimation(context, 2130968587);
        this.mSearchBarTextGreyColor = getResources().getColor(2131558684);
    }

    public void assignSearchBubble(SearchBar searchBar) {
        setOnClickListener(new C15971(searchBar));
        this.mAddBubbleAnim.setAnimationListener(searchBar);
        this.mDelBubbleAnim.setAnimationListener(searchBar);
    }

    public void makeBubbleVisible(String bubbleTxt, boolean toBeShown, boolean withAnimation) {
        if (toBeShown) {
            setText(bubbleTxt);
        }
        int visibility = toBeShown ? 0 : 8;
        if (visibility != getVisibility()) {
            setVisibility(visibility);
            if (withAnimation) {
                startAnimation(toBeShown ? this.mAddBubbleAnim : this.mDelBubbleAnim);
            } else {
                clearAnimation();
            }
        }
    }

    public void setBubbleViewStyle(int asSelected) {
        if (this.mIntToRemove != asSelected) {
            this.mIntToRemove = asSelected;
            switch (asSelected) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    setBackgroundResource(2130837595);
                    setTextColor(this.mSearchBarTextGreyColor);
                case TTSConst.TTSMULTILINE /*1*/:
                    setBackgroundResource(2130837597);
                    setTextColor(-1);
                case TTSConst.TTSPARAGRAPH /*2*/:
                    setBackgroundResource(2130837596);
                    setTextColor(-1);
                default:
            }
        }
    }
}
