package com.google.android.gms.analytics.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzx;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class zzn extends zzd {
    private volatile String zzLd;
    private Future<String> zzMG;

    /* renamed from: com.google.android.gms.analytics.internal.zzn.1 */
    class C06301 implements Callable<String> {
        final /* synthetic */ zzn zzMH;

        C06301(zzn com_google_android_gms_analytics_internal_zzn) {
            this.zzMH = com_google_android_gms_analytics_internal_zzn;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzji();
        }

        public String zzji() throws Exception {
            return this.zzMH.zzjf();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzn.2 */
    class C06312 implements Callable<String> {
        final /* synthetic */ zzn zzMH;

        C06312(zzn com_google_android_gms_analytics_internal_zzn) {
            this.zzMH = com_google_android_gms_analytics_internal_zzn;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzji();
        }

        public String zzji() throws Exception {
            return this.zzMH.zzjg();
        }
    }

    protected zzn(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
    }

    private boolean zzg(Context context, String str) {
        boolean z = false;
        zzx.zzcs(str);
        zzx.zzci("ClientId should be saved from worker thread");
        FileOutputStream fileOutputStream = null;
        try {
            zza("Storing clientId", str);
            fileOutputStream = context.openFileOutput("gaClientId", 0);
            fileOutputStream.write(str.getBytes());
            z = true;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    zze("Failed to close clientId writing stream", e);
                }
            }
        } catch (FileNotFoundException e2) {
            zze("Error creating clientId file", e2);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e3) {
                    zze("Failed to close clientId writing stream", e3);
                }
            }
        } catch (IOException e32) {
            zze("Error writing to clientId file", e32);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e322) {
                    zze("Failed to close clientId writing stream", e322);
                }
            }
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e3222) {
                    zze("Failed to close clientId writing stream", e3222);
                }
            }
        }
        return z;
    }

    private String zzjg() {
        String zzjh = zzjh();
        try {
            return !zzg(zzig().getContext(), zzjh) ? "0" : zzjh;
        } catch (Exception e) {
            zze("Error saving clientId file", e);
            return "0";
        }
    }

    protected String zzZ(Context context) {
        FileInputStream fileInputStream;
        Object e;
        Throwable th;
        zzx.zzci("ClientId should be loaded from worker thread");
        FileInputStream openFileInput;
        try {
            openFileInput = context.openFileInput("gaClientId");
            try {
                byte[] bArr = new byte[36];
                int read = openFileInput.read(bArr, 0, bArr.length);
                if (openFileInput.available() > 0) {
                    zzbb("clientId file seems corrupted, deleting it.");
                    openFileInput.close();
                    context.deleteFile("gaClientId");
                    if (openFileInput == null) {
                        return null;
                    }
                    try {
                        openFileInput.close();
                        return null;
                    } catch (IOException e2) {
                        zze("Failed to close client id reading stream", e2);
                        return null;
                    }
                } else if (read < 14) {
                    zzbb("clientId file is empty, deleting it.");
                    openFileInput.close();
                    context.deleteFile("gaClientId");
                    if (openFileInput == null) {
                        return null;
                    }
                    try {
                        openFileInput.close();
                        return null;
                    } catch (IOException e22) {
                        zze("Failed to close client id reading stream", e22);
                        return null;
                    }
                } else {
                    openFileInput.close();
                    String str = new String(bArr, 0, read);
                    zza("Read client id from disk", str);
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                        } catch (IOException e3) {
                            zze("Failed to close client id reading stream", e3);
                        }
                    }
                    return str;
                }
            } catch (FileNotFoundException e4) {
                fileInputStream = openFileInput;
                if (fileInputStream != null) {
                    return null;
                }
                try {
                    fileInputStream.close();
                    return null;
                } catch (IOException e222) {
                    zze("Failed to close client id reading stream", e222);
                    return null;
                }
            } catch (IOException e5) {
                e = e5;
                try {
                    zze("Error reading client id file, deleting it", e);
                    context.deleteFile("gaClientId");
                    if (openFileInput != null) {
                        return null;
                    }
                    try {
                        openFileInput.close();
                        return null;
                    } catch (IOException e2222) {
                        zze("Failed to close client id reading stream", e2222);
                        return null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                        } catch (IOException e22222) {
                            zze("Failed to close client id reading stream", e22222);
                        }
                    }
                    throw th;
                }
            }
        } catch (FileNotFoundException e6) {
            fileInputStream = null;
            if (fileInputStream != null) {
                return null;
            }
            fileInputStream.close();
            return null;
        } catch (IOException e7) {
            e = e7;
            openFileInput = null;
            zze("Error reading client id file, deleting it", e);
            context.deleteFile("gaClientId");
            if (openFileInput != null) {
                return null;
            }
            openFileInput.close();
            return null;
        } catch (Throwable th3) {
            openFileInput = null;
            th = th3;
            if (openFileInput != null) {
                openFileInput.close();
            }
            throw th;
        }
    }

    protected void zzhB() {
    }

    public String zzjd() {
        String str;
        zzio();
        synchronized (this) {
            if (this.zzLd == null) {
                this.zzMG = zzig().zzb(new C06301(this));
            }
            if (this.zzMG != null) {
                try {
                    this.zzLd = (String) this.zzMG.get();
                } catch (InterruptedException e) {
                    zzd("ClientId loading or generation was interrupted", e);
                    this.zzLd = "0";
                } catch (ExecutionException e2) {
                    zze("Failed to load or generate client id", e2);
                    this.zzLd = "0";
                }
                if (this.zzLd == null) {
                    this.zzLd = "0";
                }
                zza("Loaded clientId", this.zzLd);
                this.zzMG = null;
            }
            str = this.zzLd;
        }
        return str;
    }

    String zzje() {
        synchronized (this) {
            this.zzLd = null;
            this.zzMG = zzig().zzb(new C06312(this));
        }
        return zzjd();
    }

    String zzjf() {
        String zzZ = zzZ(zzig().getContext());
        return zzZ == null ? zzjg() : zzZ;
    }

    protected String zzjh() {
        return UUID.randomUUID().toString().toLowerCase();
    }
}
