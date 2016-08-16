package com.sygic.aura;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.sygic.base.C1799R;

public class CameraActivity extends Activity {
    private CameraPreview mPreview;
    private ImageButton m_btnShutter;

    /* renamed from: com.sygic.aura.CameraActivity.1 */
    class C10891 implements OnClickListener {
        C10891() {
        }

        public void onClick(View v) {
            CameraActivity.this.mPreview.onKeyDown(27, new KeyEvent(0, 27));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(C1799R.layout.camera);
        this.mPreview = new CameraPreview(this);
        DrawRectangle drawRect = new DrawRectangle(this);
        ((LinearLayout) findViewById(C1799R.id.preview)).addView(this.mPreview);
        addContentView(drawRect, new LayoutParams(-2, -2));
        this.m_btnShutter = (ImageButton) findViewById(C1799R.id.shutter);
        this.m_btnShutter.setOnClickListener(new C10891());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() != 0) {
            return super.onKeyDown(keyCode, event);
        }
        if (keyCode != 4) {
            return this.mPreview.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getAction() == 0 && keyCode == 4) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
