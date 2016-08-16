package com.squareup.picasso;

import android.graphics.Bitmap;

public interface Cache {
    public static final Cache NONE;

    /* renamed from: com.squareup.picasso.Cache.1 */
    static class C10801 implements Cache {
        C10801() {
        }

        public Bitmap get(String key) {
            return null;
        }

        public void set(String key, Bitmap bitmap) {
        }

        public int size() {
            return 0;
        }

        public int maxSize() {
            return 0;
        }
    }

    Bitmap get(String str);

    int maxSize();

    void set(String str, Bitmap bitmap);

    int size();

    static {
        NONE = new C10801();
    }
}
