package com.google.android.gms.ads;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzfd;
import com.google.android.gms.internal.zzfe;

public class AdActivity extends Activity {
    private zzfe zznJ;

    private void zzaE() {
        if (this.zznJ != null) {
            try {
                this.zznJ.zzaE();
            } catch (Throwable e) {
                zzb.zzd("Could not forward setContentViewSet to ad overlay:", e);
            }
        }
    }

    public void onBackPressed() {
        boolean z = true;
        try {
            if (this.zznJ != null) {
                z = this.zznJ.zzez();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onBackPressed to ad overlay:", e);
        }
        if (z) {
            super.onBackPressed();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.zznJ = zzfd.zzb(this);
        if (this.zznJ == null) {
            zzb.zzaE("Could not create ad overlay.");
            finish();
            return;
        }
        try {
            this.zznJ.onCreate(savedInstanceState);
        } catch (Throwable e) {
            zzb.zzd("Could not forward onCreate to ad overlay:", e);
            finish();
        }
    }

    protected void onDestroy() {
        try {
            if (this.zznJ != null) {
                this.zznJ.onDestroy();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onDestroy to ad overlay:", e);
        }
        super.onDestroy();
    }

    protected void onPause() {
        try {
            if (this.zznJ != null) {
                this.zznJ.onPause();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onPause to ad overlay:", e);
            finish();
        }
        super.onPause();
    }

    protected void onRestart() {
        super.onRestart();
        try {
            if (this.zznJ != null) {
                this.zznJ.onRestart();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onRestart to ad overlay:", e);
            finish();
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            if (this.zznJ != null) {
                this.zznJ.onResume();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onResume to ad overlay:", e);
            finish();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        try {
            if (this.zznJ != null) {
                this.zznJ.onSaveInstanceState(outState);
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onSaveInstanceState to ad overlay:", e);
            finish();
        }
        super.onSaveInstanceState(outState);
    }

    protected void onStart() {
        super.onStart();
        try {
            if (this.zznJ != null) {
                this.zznJ.onStart();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onStart to ad overlay:", e);
            finish();
        }
    }

    protected void onStop() {
        try {
            if (this.zznJ != null) {
                this.zznJ.onStop();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onStop to ad overlay:", e);
            finish();
        }
        super.onStop();
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        zzaE();
    }

    public void setContentView(View view) {
        super.setContentView(view);
        zzaE();
    }

    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        zzaE();
    }
}
