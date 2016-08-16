package com.sygic.aura.helper;

import android.content.Context;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import com.sygic.aura.resources.Typefaces;

public class AssetTypefaceSpan extends MetricAffectingSpan implements ParcelableSpan {
    private final int mAssetPathId;
    private Context mContext;

    public AssetTypefaceSpan(Context context, int fontId) {
        this.mAssetPathId = fontId;
        this.mContext = context;
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public int getSpanTypeIdInternal() {
        return 13;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeInt(this.mAssetPathId);
    }

    public void updateDrawState(TextPaint ds) {
        apply(this.mContext, ds, this.mAssetPathId);
    }

    public void updateMeasureState(TextPaint paint) {
        apply(this.mContext, paint, this.mAssetPathId);
    }

    private static void apply(Context context, Paint paint, int fontId) {
        paint.setTypeface(Typefaces.getFont(context, fontId));
        paint.setFlags(paint.getFlags() | 128);
    }
}
