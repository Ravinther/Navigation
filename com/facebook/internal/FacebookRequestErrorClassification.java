package com.facebook.internal;

import com.facebook.FacebookRequestError.Category;
import com.sygic.aura.C1090R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import loquendo.tts.engine.TTSConst;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FacebookRequestErrorClassification {
    private static FacebookRequestErrorClassification defaultInstance;
    private final Map<Integer, Set<Integer>> loginRecoverableErrors;
    private final String loginRecoverableRecoveryMessage;
    private final Map<Integer, Set<Integer>> otherErrors;
    private final String otherRecoveryMessage;
    private final Map<Integer, Set<Integer>> transientErrors;
    private final String transientRecoveryMessage;

    /* renamed from: com.facebook.internal.FacebookRequestErrorClassification.1 */
    static class C03431 extends HashMap<Integer, Set<Integer>> {
        C03431() {
            put(Integer.valueOf(2), null);
            put(Integer.valueOf(4), null);
            put(Integer.valueOf(9), null);
            put(Integer.valueOf(17), null);
            put(Integer.valueOf(341), null);
        }
    }

    /* renamed from: com.facebook.internal.FacebookRequestErrorClassification.2 */
    static class C03442 extends HashMap<Integer, Set<Integer>> {
        C03442() {
            put(Integer.valueOf(C1090R.styleable.Theme_editTextStyle), null);
            put(Integer.valueOf(190), null);
        }
    }

    /* renamed from: com.facebook.internal.FacebookRequestErrorClassification.3 */
    static /* synthetic */ class C03453 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$FacebookRequestError$Category;

        static {
            $SwitchMap$com$facebook$FacebookRequestError$Category = new int[Category.values().length];
            try {
                $SwitchMap$com$facebook$FacebookRequestError$Category[Category.OTHER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$facebook$FacebookRequestError$Category[Category.LOGIN_RECOVERABLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$facebook$FacebookRequestError$Category[Category.TRANSIENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    FacebookRequestErrorClassification(Map<Integer, Set<Integer>> otherErrors, Map<Integer, Set<Integer>> transientErrors, Map<Integer, Set<Integer>> loginRecoverableErrors, String otherRecoveryMessage, String transientRecoveryMessage, String loginRecoverableRecoveryMessage) {
        this.otherErrors = otherErrors;
        this.transientErrors = transientErrors;
        this.loginRecoverableErrors = loginRecoverableErrors;
        this.otherRecoveryMessage = otherRecoveryMessage;
        this.transientRecoveryMessage = transientRecoveryMessage;
        this.loginRecoverableRecoveryMessage = loginRecoverableRecoveryMessage;
    }

    public String getRecoveryMessage(Category category) {
        switch (C03453.$SwitchMap$com$facebook$FacebookRequestError$Category[category.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return this.otherRecoveryMessage;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return this.loginRecoverableRecoveryMessage;
            case TTSConst.TTSUNICODE /*3*/:
                return this.transientRecoveryMessage;
            default:
                return null;
        }
    }

    public Category classify(int errorCode, int errorSubCode, boolean isTransient) {
        if (isTransient) {
            return Category.TRANSIENT;
        }
        Set<Integer> subCodes;
        if (this.otherErrors != null && this.otherErrors.containsKey(Integer.valueOf(errorCode))) {
            subCodes = (Set) this.otherErrors.get(Integer.valueOf(errorCode));
            if (subCodes == null || subCodes.contains(Integer.valueOf(errorSubCode))) {
                return Category.OTHER;
            }
        }
        if (this.loginRecoverableErrors != null && this.loginRecoverableErrors.containsKey(Integer.valueOf(errorCode))) {
            subCodes = (Set) this.loginRecoverableErrors.get(Integer.valueOf(errorCode));
            if (subCodes == null || subCodes.contains(Integer.valueOf(errorSubCode))) {
                return Category.LOGIN_RECOVERABLE;
            }
        }
        if (this.transientErrors != null && this.transientErrors.containsKey(Integer.valueOf(errorCode))) {
            subCodes = (Set) this.transientErrors.get(Integer.valueOf(errorCode));
            if (subCodes == null || subCodes.contains(Integer.valueOf(errorSubCode))) {
                return Category.TRANSIENT;
            }
        }
        return Category.OTHER;
    }

    public static synchronized FacebookRequestErrorClassification getDefaultErrorClassification() {
        FacebookRequestErrorClassification facebookRequestErrorClassification;
        synchronized (FacebookRequestErrorClassification.class) {
            if (defaultInstance == null) {
                defaultInstance = getDefaultErrorClassificationImpl();
            }
            facebookRequestErrorClassification = defaultInstance;
        }
        return facebookRequestErrorClassification;
    }

    private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl() {
        return new FacebookRequestErrorClassification(null, new C03431(), new C03442(), null, null, null);
    }

    private static Map<Integer, Set<Integer>> parseJSONDefinition(JSONObject definition) {
        JSONArray itemsArray = definition.optJSONArray("items");
        if (itemsArray.length() == 0) {
            return null;
        }
        Map<Integer, Set<Integer>> items = new HashMap();
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject item = itemsArray.optJSONObject(i);
            if (item != null) {
                int code = item.optInt("code");
                if (code != 0) {
                    Set<Integer> subcodes = null;
                    JSONArray subcodesArray = item.optJSONArray("subcodes");
                    if (subcodesArray != null && subcodesArray.length() > 0) {
                        subcodes = new HashSet();
                        for (int j = 0; j < subcodesArray.length(); j++) {
                            int subCode = subcodesArray.optInt(j);
                            if (subCode != 0) {
                                subcodes.add(Integer.valueOf(subCode));
                            }
                        }
                    }
                    items.put(Integer.valueOf(code), subcodes);
                }
            }
        }
        return items;
    }

    public static FacebookRequestErrorClassification createFromJSON(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        Map<Integer, Set<Integer>> otherErrors = null;
        Map<Integer, Set<Integer>> transientErrors = null;
        Map<Integer, Set<Integer>> loginRecoverableErrors = null;
        String otherRecoveryMessage = null;
        String transientRecoveryMessage = null;
        String loginRecoverableRecoveryMessage = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject definition = jsonArray.optJSONObject(i);
            if (definition != null) {
                String name = definition.optString("name");
                if (name != null) {
                    if (name.equalsIgnoreCase("other")) {
                        otherRecoveryMessage = definition.optString("recovery_message", null);
                        otherErrors = parseJSONDefinition(definition);
                    } else if (name.equalsIgnoreCase("transient")) {
                        transientRecoveryMessage = definition.optString("recovery_message", null);
                        transientErrors = parseJSONDefinition(definition);
                    } else if (name.equalsIgnoreCase("login_recoverable")) {
                        loginRecoverableRecoveryMessage = definition.optString("recovery_message", null);
                        loginRecoverableErrors = parseJSONDefinition(definition);
                    }
                }
            }
        }
        return new FacebookRequestErrorClassification(otherErrors, transientErrors, loginRecoverableErrors, otherRecoveryMessage, transientRecoveryMessage, loginRecoverableRecoveryMessage);
    }
}
