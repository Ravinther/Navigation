package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.AboutEntry;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.TwoListItemData;
import com.sygic.aura.settings.model.TwoListAdapter;
import java.util.ArrayList;

public class AboutInfo extends Preference {
    private AboutEntry mAboutEntry;
    private int nClickCount;

    /* renamed from: com.sygic.aura.settings.preferences.AboutInfo.1 */
    class C16621 implements OnGlobalLayoutListener {
        final /* synthetic */ ListView val$listView;

        C16621(ListView listView) {
            this.val$listView = listView;
        }

        public void onGlobalLayout() {
            if (this.val$listView.getCount() > 0) {
                this.val$listView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                int itemsHeight = (this.val$listView.getCount() + 1) * this.val$listView.getMeasuredHeight();
                LayoutParams listParams = this.val$listView.getLayoutParams();
                listParams.height = itemsHeight;
                this.val$listView.setLayoutParams(listParams);
            }
        }
    }

    /* renamed from: com.sygic.aura.settings.preferences.AboutInfo.2 */
    class C16632 implements OnItemClickListener {
        C16632() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String strMsg;
            AboutInfo.access$004(AboutInfo.this);
            Context ctx = AboutInfo.this.getContext();
            if (AboutInfo.this.nClickCount % 3 == 0) {
                strMsg = ResourceManager.getCoreString(ctx, 2131165700).concat(AboutInfo.this.mAboutEntry.getHash());
            } else if (AboutInfo.this.nClickCount % 5 == 0) {
                strMsg = ResourceManager.getCoreString(ctx, 2131165698).concat(AboutInfo.this.mAboutEntry.getBranchName());
            } else if (AboutInfo.this.nClickCount % 8 == 0) {
                strMsg = ResourceManager.getCoreString(ctx, 2131165699).concat(AboutInfo.this.mAboutEntry.getDate());
            } else {
                return;
            }
            SToast.makeText(ctx, strMsg, 0).show();
        }
    }

    static /* synthetic */ int access$004(AboutInfo x0) {
        int i = x0.nClickCount + 1;
        x0.nClickCount = i;
        return i;
    }

    public AboutInfo(Context context) {
        super(context);
        this.nClickCount = 0;
    }

    public AboutInfo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.nClickCount = 0;
    }

    public AboutInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.nClickCount = 0;
    }

    public View onCreateView(ViewGroup parent) {
        Context ctx = getContext();
        ListView listView = (ListView) LayoutInflater.from(ctx).inflate(2130903065, parent, false);
        this.mAboutEntry = SettingsManager.nativeGetAboutInfo();
        ArrayList<TwoListItemData> objects = new ArrayList();
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165705), ResourceManager.getCoreString("%product%")));
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165710), this.mAboutEntry.getSwVersion()));
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165695), this.mAboutEntry.getBuildName()));
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165706), this.mAboutEntry.getFreeRam()));
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165704), this.mAboutEntry.getMapVersion()));
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165696), this.mAboutEntry.getDeviceID()));
        boolean isDebugEnabled = SettingsManager.nativeIsDebugEnabled();
        if (isDebugEnabled) {
            objects.add(new TwoListItemData("Model name", this.mAboutEntry.getModelName()));
            objects.add(new TwoListItemData("Compatibility", this.mAboutEntry.getCompatibility()));
        }
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165702), this.mAboutEntry.getGLVendor()));
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165701), this.mAboutEntry.getGLRenderer()));
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165703), this.mAboutEntry.getGLVersion()));
        if (isDebugEnabled) {
            objects.add(new TwoListItemData("Pixel format", this.mAboutEntry.getPixelFormat()));
            objects.add(new TwoListItemData("Depth format", this.mAboutEntry.getDepthFormat()));
        }
        String strTmcVendor = ResourceManager.getCoreString("%tmcVendor%");
        if (strTmcVendor.length() != 0) {
            objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165708), strTmcVendor));
        }
        objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165707), this.mAboutEntry.getResolution()));
        if (!TextUtils.isEmpty(this.mAboutEntry.getExpirationDate())) {
            objects.add(new TwoListItemData(ResourceManager.getCoreString(ctx, 2131165697), this.mAboutEntry.getExpirationDate()));
        }
        listView.setAdapter(new TwoListAdapter(ctx, objects));
        listView.getViewTreeObserver().addOnGlobalLayoutListener(new C16621(listView));
        listView.setOnItemClickListener(new C16632());
        return listView;
    }
}
