package com.sygic.aura.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.sygic.aura.clazz.ImageInfo;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PictureData {
    public static void makePicture(String strImage, ImageInfo image) {
        if (image != null) {
            int nSideSize = image.getSideSize();
            if (nSideSize > 0) {
                Options options = new Options();
                Bitmap bm = BitmapFactory.decodeFile(strImage);
                if (bm != null) {
                    int nScale = Math.max(bm.getWidth(), bm.getHeight()) / nSideSize;
                    if (nScale != 1) {
                        options.inSampleSize = nScale;
                        bm = BitmapFactory.decodeFile(strImage, options);
                    }
                    savePicture(cropToSquare(bm), image.getPath());
                    ExifInfo ei = getExifInfo(strImage);
                    if (ei != null) {
                        image.setLatLong(ei.lLatitude, ei.lLongitude);
                    }
                }
            }
        }
    }

    public static void makePicture(byte[] data, ImageInfo image, ContentResolver cr) {
        if (data != null && image != null) {
            int nSideSize = image.getSideSize();
            Bitmap bm;
            if (nSideSize > 0) {
                Options options = new Options();
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                int nScale = Math.max(bm.getWidth(), bm.getHeight()) / nSideSize;
                if (nScale != 1) {
                    options.inSampleSize = nScale;
                    bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                }
                savePicture(cropToSquare(bm), image.getPath());
                return;
            }
            try {
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (bm != null) {
                    String strImage = Media.insertImage(cr, bm, null, null);
                    if (!TextUtils.isEmpty(strImage)) {
                        ContentResolver contentResolver = cr;
                        Cursor cur = contentResolver.query(Uri.parse(strImage), new String[]{"_data"}, null, null, null);
                        int nIndex = cur.getColumnIndexOrThrow("_data");
                        cur.moveToFirst();
                        strImage = cur.getString(nIndex);
                        image.setPath(strImage);
                        if (image.bHasPosition) {
                            try {
                                float fLat = ((float) image.getLatitude()) / 100000.0f;
                                float fLong = ((float) image.getLongitude()) / 100000.0f;
                                String strLatRef = "N";
                                String strLongRef = "E";
                                String[] strLat = new String[3];
                                String[] strLong = new String[3];
                                if (((double) fLat) < 0.0d) {
                                    strLatRef = "S";
                                    fLat = -fLat;
                                }
                                if (((double) fLong) < 0.0d) {
                                    strLongRef = "W";
                                    fLong = -fLong;
                                }
                                strLat[0] = Long.toString((long) fLat) + "/1";
                                strLong[0] = Long.toString((long) fLong) + "/1";
                                fLat = (float) (((double) (fLat - ((float) ((long) fLat)))) * 60.0d);
                                fLong = (float) (((double) (fLong - ((float) ((long) fLong)))) * 60.0d);
                                strLat[1] = Long.toString((long) fLat) + "/1";
                                strLong[1] = Long.toString((long) fLong) + "/1";
                                fLong = (float) (((double) (fLong - ((float) ((long) fLong)))) * 60.0d);
                                strLat[2] = Long.toString((long) (((double) ((float) (((double) (fLat - ((float) ((long) fLat)))) * 60.0d))) * 360.0d)) + "/360";
                                strLong[2] = Long.toString((long) (((double) fLong) * 360.0d)) + "/360";
                                ExifInterface ei = new ExifInterface(strImage);
                                if (ei != null) {
                                    ei.setAttribute("GPSLatitude", strLat[0] + "," + strLat[1] + "," + strLat[2]);
                                    ei.setAttribute("GPSLatitudeRef", strLatRef);
                                    ei.setAttribute("GPSLongitude", strLong[0] + "," + strLong[1] + "," + strLong[2]);
                                    ei.setAttribute("GPSLongitudeRef", strLongRef);
                                    ei.saveAttributes();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void makePicture(InputStream stream, ImageInfo image) {
        if (stream != null && image != null) {
            int nSideSize = image.getSideSize();
            if (nSideSize > 0) {
                Options options = new Options();
                Bitmap bm = BitmapFactory.decodeStream(stream);
                int nScale = Math.max(bm.getWidth(), bm.getHeight()) / nSideSize;
                if (nScale != 1) {
                    options.inSampleSize = nScale;
                    bm = BitmapFactory.decodeStream(stream, null, options);
                }
                savePicture(cropToSquare(bm), image.getPath());
            }
        }
    }

    private static void savePicture(Bitmap bm, String strFile) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bm.compress(CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
            bos.close();
            fos.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private static Bitmap cropToSquare(Bitmap bmp) {
        int x = 0;
        int y = 0;
        int nSide = Math.min(bmp.getWidth(), bmp.getHeight());
        if (bmp.getWidth() < bmp.getHeight()) {
            y = (bmp.getHeight() - bmp.getWidth()) / 2;
        } else {
            x = (bmp.getWidth() - bmp.getHeight()) / 2;
        }
        return Bitmap.createBitmap(bmp, x, y, nSide, nSide);
    }

    public static ExifInfo getExifInfo(String strImageFile) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(strImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        float[] output = new float[2];
        if (!exif.getLatLong(output)) {
            return null;
        }
        ExifInfo ei = new ExifInfo();
        ei.lLatitude = (long) (output[0] * 100000.0f);
        ei.lLongitude = (long) (output[1] * 100000.0f);
        return ei;
    }
}
