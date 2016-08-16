package com.google.android.gms.ads.internal.formats;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.internal.zzx;

class zzb extends RelativeLayout {
    private static final float[] zzvH;
    private final RelativeLayout zzvI;

    static {
        zzvH = new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
    }

    public zzb(Context context, zza com_google_android_gms_ads_internal_formats_zza) {
        super(context);
        zzx.zzv(com_google_android_gms_ads_internal_formats_zza);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(11);
        Drawable shapeDrawable = new ShapeDrawable(new RoundRectShape(zzvH, null, null));
        shapeDrawable.getPaint().setColor(com_google_android_gms_ads_internal_formats_zza.getBackgroundColor());
        this.zzvI = new RelativeLayout(context);
        this.zzvI.setLayoutParams(layoutParams);
        zzp.zzbz().zza(this.zzvI, shapeDrawable);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        View textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setId(1195835393);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setText(com_google_android_gms_ads_internal_formats_zza.getText());
        textView.setTextColor(com_google_android_gms_ads_internal_formats_zza.getTextColor());
        textView.setTextSize((float) com_google_android_gms_ads_internal_formats_zza.getTextSize());
        textView.setPadding(zzk.zzcE().zzb(context, 4), 0, zzk.zzcE().zzb(context, 4), 0);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(1, textView.getId());
        View imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setId(1195835394);
        imageView.setImageDrawable(com_google_android_gms_ads_internal_formats_zza.getIcon());
        this.zzvI.addView(textView);
        this.zzvI.addView(imageView);
        addView(this.zzvI);
    }

    public ViewGroup zzdq() {
        return this.zzvI;
    }
}
