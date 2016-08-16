package com.sygic.aura.helper.resources;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class ExtendedDrawable {
    public static Drawable createFromXml(Resources r, XmlPullParser parser, Theme theme) throws XmlPullParserException, IOException {
        AttributeSet attrs = Xml.asAttributeSet(parser);
        int type;
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        Drawable drawable = createFromXmlInner(r, parser, attrs, theme);
        if (drawable != null) {
            return drawable;
        }
        throw new RuntimeException("Unknown initial tag: " + parser.getName());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.drawable.Drawable createFromXmlInner(android.content.res.Resources r5, org.xmlpull.v1.XmlPullParser r6, android.util.AttributeSet r7, android.content.res.Resources.Theme r8) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
        r4 = 21;
        r1 = r6.getName();
        r2 = -1;
        r3 = r1.hashCode();
        switch(r3) {
            case -1586386121: goto L_0x001a;
            case 1191572447: goto L_0x0025;
            default: goto L_0x000e;
        };
    L_0x000e:
        switch(r2) {
            case 0: goto L_0x0030;
            case 1: goto L_0x003d;
            default: goto L_0x0011;
        };
    L_0x0011:
        r2 = android.os.Build.VERSION.SDK_INT;
        if (r2 < r4) goto L_0x0047;
    L_0x0015:
        r0 = android.graphics.drawable.Drawable.createFromXmlInner(r5, r6, r7, r8);
    L_0x0019:
        return r0;
    L_0x001a:
        r3 = "font-icon";
        r3 = r1.equals(r3);
        if (r3 == 0) goto L_0x000e;
    L_0x0023:
        r2 = 0;
        goto L_0x000e;
    L_0x0025:
        r3 = "selector";
        r3 = r1.equals(r3);
        if (r3 == 0) goto L_0x000e;
    L_0x002e:
        r2 = 1;
        goto L_0x000e;
    L_0x0030:
        r0 = new com.sygic.aura.resources.FontDrawable;
        r0.<init>();
    L_0x0035:
        r2 = android.os.Build.VERSION.SDK_INT;
        if (r2 < r4) goto L_0x004c;
    L_0x0039:
        r0.inflate(r5, r6, r7, r8);
        goto L_0x0019;
    L_0x003d:
        r2 = android.os.Build.VERSION.SDK_INT;
        if (r2 < r4) goto L_0x0011;
    L_0x0041:
        r0 = new android.graphics.drawable.ExtendedStateListDrawable;
        r0.<init>();
        goto L_0x0035;
    L_0x0047:
        r0 = android.graphics.drawable.Drawable.createFromXmlInner(r5, r6, r7);
        goto L_0x0019;
    L_0x004c:
        r0.inflate(r5, r6, r7);
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.helper.resources.ExtendedDrawable.createFromXmlInner(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.graphics.drawable.Drawable");
    }
}
