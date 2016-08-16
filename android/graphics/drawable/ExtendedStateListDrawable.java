package android.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ExtendedTypedArray;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.StateSet;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.resources.ExtendedDrawable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ExtendedStateListDrawable extends StateListDrawable {
    private static FakedR sStyleable;

    private static class FakedR {
        final Class rClass;

        public FakedR(Class cls) {
            this.rClass = cls;
        }

        private int[] getIntArray(String name) throws NoSuchFieldException, IllegalAccessException {
            Field field = this.rClass.getDeclaredField(name);
            field.setAccessible(true);
            return (int[]) field.get(null);
        }

        private int getInt(String name) throws NoSuchFieldException, IllegalAccessException {
            Field field = this.rClass.getDeclaredField(name);
            field.setAccessible(true);
            return field.getInt(null);
        }
    }

    private class RetValuePair {
        Drawable drawable;
        int[] states;

        private RetValuePair() {
        }
    }

    static {
        sStyleable = getR();
    }

    private static FakedR getR() {
        Class cls = null;
        try {
            cls = Class.forName("com.android.internal.R$styleable");
        } catch (ClassNotFoundException e) {
            CrashlyticsHelper.logException("ExtendedStateListDrawable", e.getMessage(), e, true);
        }
        return new FakedR(cls);
    }

    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Theme theme) throws XmlPullParserException, IOException {
        try {
            TypedArray a = obtainAttributes(r, theme, attrs, sStyleable.getIntArray("StateListDrawable"));
            Method method = Drawable.class.getDeclaredMethod("inflateWithAttributes", new Class[]{Resources.class, XmlPullParser.class, TypedArray.class, Integer.TYPE});
            method.setAccessible(true);
            method.invoke(this, new Object[]{r, parser, a, Integer.valueOf(sStyleable.getInt("StateListDrawable_visible"))});
            updateStateFromTypedArray(a);
            a.recycle();
            inflateChildElements(r, parser, attrs, theme);
            onStateChange(getState());
        } catch (Exception e) {
            CrashlyticsHelper.logError("ExtendedStateListDrawable", e.getMessage());
        }
    }

    private void inflateChildElements(Resources r, XmlPullParser parser, AttributeSet attrs, Theme theme) throws XmlPullParserException, IOException {
        Object obj;
        try {
            Field field = StateListDrawable.class.getDeclaredField("mStateListState");
            field.setAccessible(true);
            obj = field.get(this);
        } catch (Exception e) {
            obj = null;
            CrashlyticsHelper.logError("ExtendedStateListDrawable", e.getMessage());
        }
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int type = parser.next();
            if (type != 1) {
                int depth = parser.getDepth();
                if (depth < innerDepth && type == 3) {
                    return;
                }
                if (type == 2 && depth <= innerDepth && parser.getName().equals("item")) {
                    try {
                        RetValuePair retValuePair = extractStateSet(r, attrs, theme);
                        int[] states = retValuePair.states;
                        if (retValuePair.drawable == null) {
                            do {
                                type = parser.next();
                            } while (type == 4);
                            if (type != 2) {
                                break;
                            }
                            retValuePair.drawable = ExtendedDrawable.createFromXmlInner(r, parser, attrs, theme);
                        }
                        Method method = Class.forName(StateListDrawable.class.getName() + "$StateListState").getDeclaredMethod("addStateSet", new Class[]{int[].class, Drawable.class});
                        method.setAccessible(true);
                        method.invoke(obj, new Object[]{states, retValuePair.drawable});
                    } catch (Exception e2) {
                        CrashlyticsHelper.logError("ExtendedStateListDrawable", e2.getMessage());
                    }
                }
            } else {
                return;
            }
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or " + "child tag defining a drawable");
    }

    @TargetApi(21)
    private RetValuePair extractStateSet(Resources r, AttributeSet attrs, Theme theme) {
        ExtendedStateListDrawable extendedStateListDrawable = this;
        RetValuePair retValuePair = new RetValuePair();
        if (VERSION.SDK_INT >= 22) {
            try {
                TypedArray a = obtainAttributes(r, theme, attrs, sStyleable.getIntArray("StateListDrawableItem"));
                try {
                    retValuePair.drawable = a.getDrawable(sStyleable.getInt("StateListDrawableItem_drawable"));
                } catch (Exception e) {
                    retValuePair.drawable = null;
                    CrashlyticsHelper.logError("ExtendedStateListDrawable", e.getMessage());
                }
                a.recycle();
                Method method = StateListDrawable.class.getDeclaredMethod("extractStateSet", new Class[]{AttributeSet.class});
                method.setAccessible(true);
                retValuePair.states = (int[]) method.invoke(this, new Object[]{attrs});
            } catch (Exception e2) {
                CrashlyticsHelper.logWarning("ExtendedStateListDrawable", e2.getMessage() + " | model: " + Build.MODEL + " / API version: " + VERSION.SDK_INT);
            }
            return retValuePair;
        }
        int drawableRes = 0;
        int numAttrs = attrs.getAttributeCount();
        int[] states = new int[numAttrs];
        int i = 0;
        int j = 0;
        while (i < numAttrs) {
            int stateResId = attrs.getAttributeNameResource(i);
            if (stateResId == 0) {
                break;
            }
            int j2;
            if (stateResId == 16843161) {
                drawableRes = attrs.getAttributeResourceValue(i, 0);
                j2 = j;
            } else {
                j2 = j + 1;
                if (!attrs.getAttributeBooleanValue(i, false)) {
                    stateResId = -stateResId;
                }
                states[j] = stateResId;
            }
            i++;
            j = j2;
        }
        states = StateSet.trimStateSet(states, j);
        if (drawableRes != 0) {
            retValuePair.drawable = r.getDrawable(drawableRes, theme);
        }
        retValuePair.states = states;
        return retValuePair;
    }

    private void updateStateFromTypedArray(TypedArray a) {
        if (VERSION.SDK_INT >= 22) {
            try {
                Method method = StateListDrawable.class.getDeclaredMethod("updateStateFromTypedArray", new Class[]{TypedArray.class});
                method.setAccessible(true);
                method.invoke(this, new Object[]{a});
                return;
            } catch (Exception e) {
                CrashlyticsHelper.logWarning("ExtendedStateListDrawable", e.getMessage() + " | model: " + Build.MODEL + " / API version: " + VERSION.SDK_INT);
            }
        }
        try {
            Field field = StateListDrawable.class.getDeclaredField("mStateListState");
            field.setAccessible(true);
            DrawableContainerState state = (DrawableContainerState) field.get(this);
            state.setVariablePadding(a.getBoolean(sStyleable.getInt("StateListDrawable_variablePadding"), false));
            state.setConstantSize(a.getBoolean(sStyleable.getInt("StateListDrawable_constantSize"), false));
            state.setEnterFadeDuration(a.getInt(sStyleable.getInt("StateListDrawable_enterFadeDuration"), 0));
            state.setExitFadeDuration(a.getInt(sStyleable.getInt("StateListDrawable_exitFadeDuration"), 0));
            field = StateListDrawable.class.getDeclaredField("DEFAULT_DITHER");
            field.setAccessible(true);
            setDither(a.getBoolean(sStyleable.getInt("StateListDrawable_dither"), field.getBoolean(null)));
            setAutoMirrored(a.getBoolean(sStyleable.getInt("StateListDrawable_autoMirrored"), false));
        } catch (Exception e2) {
            CrashlyticsHelper.logError("ExtendedStateListDrawable", e2.getMessage());
        }
    }

    private TypedArray obtainAttributes(Resources r, Theme theme, AttributeSet set, int[] attrs) throws NoSuchFieldException, IllegalAccessException {
        if (theme == null) {
            return r.obtainAttributes(set, attrs);
        }
        return ExtendedTypedArray.copy(theme.obtainStyledAttributes(set, attrs, 0, 0));
    }
}
