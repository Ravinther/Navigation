package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

class zzcv {

    /* renamed from: com.google.android.gms.tagmanager.zzcv.1 */
    static class C10221 implements Runnable {
        final /* synthetic */ Editor zzaSk;

        C10221(Editor editor) {
            this.zzaSk = editor;
        }

        public void run() {
            this.zzaSk.commit();
        }
    }

    static void zza(Editor editor) {
        if (VERSION.SDK_INT >= 9) {
            editor.apply();
        } else {
            new Thread(new C10221(editor)).start();
        }
    }

    static void zzb(Context context, String str, String str2, String str3) {
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        zza(edit);
    }
}
