package com.sygic.aura.c2dm;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.utils.GuiUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotifImageDownloaderService extends IntentService {
    public NotifImageDownloaderService() {
        super(NotifImageDownloaderService.class.getName());
    }

    protected void onHandleIntent(Intent intent) {
        IOException e;
        Throwable th;
        if (intent != null && "com.sygic.aura.c2dm.ACTION_DOWNLOAD_IMAGE_AND_SHOW_NOTIF".equals(intent.getAction())) {
            HttpURLConnection httpURLConnection = null;
            Bitmap bitmap = null;
            BufferedInputStream bis = null;
            String imageUrl = intent.getStringExtra("image_url");
            if (!TextUtils.isEmpty(imageUrl)) {
                try {
                    httpURLConnection = (HttpURLConnection) new URL(intent.getStringExtra("image_url")).openConnection();
                    BufferedInputStream bis2 = new BufferedInputStream(httpURLConnection.getInputStream());
                    try {
                        bis2.mark(1048576);
                        Options options = new Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeStream(bis2, null, options);
                        DisplayMetrics metrics = getResources().getDisplayMetrics();
                        options.inSampleSize = calculateInSampleSize(options, (int) TypedValue.applyDimension(1, 360.0f, metrics), (int) TypedValue.applyDimension(1, 192.0f, metrics));
                        options.inJustDecodeBounds = false;
                        bis2.reset();
                        bitmap = BitmapFactory.decodeStream(bis2, null, options);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        if (bis2 != null) {
                            try {
                                bis2.close();
                                bis = bis2;
                            } catch (IOException e2) {
                                bis = bis2;
                            }
                        }
                    } catch (IOException e3) {
                        e = e3;
                        bis = bis2;
                        try {
                            e.printStackTrace();
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            if (bis != null) {
                                try {
                                    bis.close();
                                } catch (IOException e4) {
                                }
                            }
                            GuiUtils.showPromoNotification(this, intent.getStringExtra(AbstractFragment.ARG_TITLE), intent.getStringExtra("text"), intent.getStringExtra("icon"), intent.getStringExtra("action"), intent.getStringExtra("action_arg"), imageUrl, bitmap);
                        } catch (Throwable th2) {
                            th = th2;
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            if (bis != null) {
                                try {
                                    bis.close();
                                } catch (IOException e5) {
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bis = bis2;
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        if (bis != null) {
                            bis.close();
                        }
                        throw th;
                    }
                } catch (IOException e6) {
                    e = e6;
                    e.printStackTrace();
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    GuiUtils.showPromoNotification(this, intent.getStringExtra(AbstractFragment.ARG_TITLE), intent.getStringExtra("text"), intent.getStringExtra("icon"), intent.getStringExtra("action"), intent.getStringExtra("action_arg"), imageUrl, bitmap);
                }
            }
            GuiUtils.showPromoNotification(this, intent.getStringExtra(AbstractFragment.ARG_TITLE), intent.getStringExtra("text"), intent.getStringExtra("icon"), intent.getStringExtra("action"), intent.getStringExtra("action_arg"), imageUrl, bitmap);
        }
    }

    private int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
