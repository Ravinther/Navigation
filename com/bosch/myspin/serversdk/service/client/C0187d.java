package com.bosch.myspin.serversdk.service.client;

import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardExtension;

/* renamed from: com.bosch.myspin.serversdk.service.client.d */
public final class C0187d {
    public static KeyboardExtension m175a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("keyboardId must be not null");
        } else if (str.equals("com.bosch.myspin.keyboard.en")) {
            return new C0192i("com.bosch.myspin.keyboard.en", new String[]{"en"});
        } else if (str.equals("com.bosch.myspin.keyboard.de")) {
            return new C0192i("com.bosch.myspin.keyboard.de", new String[]{"de"});
        } else if (str.equals("com.bosch.myspin.keyboard.es")) {
            return new C0192i("com.bosch.myspin.keyboard.es", new String[]{"es"});
        } else if (str.equals("com.bosch.myspin.keyboard.fr")) {
            return new C0192i("com.bosch.myspin.keyboard.fr", new String[]{"fr"});
        } else if (str.equals("com.bosch.myspin.keyboard.nl")) {
            return new C0192i("com.bosch.myspin.keyboard.nl", new String[]{"nl"});
        } else if (str.equals("com.bosch.myspin.keyboard.ru")) {
            return new C0192i("com.bosch.myspin.keyboard.ru", new String[]{"ru"});
        } else if (!str.equals("com.bosch.myspin.keyboard.pt")) {
            return null;
        } else {
            return new C0192i("com.bosch.myspin.keyboard.pt", new String[]{"pt"});
        }
    }
}
