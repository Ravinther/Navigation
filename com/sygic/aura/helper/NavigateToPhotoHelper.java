package com.sygic.aura.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.sygic.aura.helper.imageMetadataExtractor.imaging.ImageMetadataReader;
import com.sygic.aura.helper.imageMetadataExtractor.imaging.ImageProcessingException;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.exif.GpsDirectory;
import com.sygic.aura.resources.ResourceManager;
import java.io.IOException;
import java.util.Locale;

public class NavigateToPhotoHelper {
    public static void choosePhoto(Fragment f) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("image/*");
        FragmentActivity activity = f.getActivity();
        if (isIntentAvailable(activity, intent)) {
            activity.startActivityForResult(Intent.createChooser(intent, ResourceManager.getCoreString(f.getResources(), 2131165506)), 215);
        } else {
            SToast.makeText(activity, 2131165507, 0).show();
        }
    }

    public static String handlePhotoChosen(Context context, Uri uri) {
        if (uri == null) {
            Toast.makeText(context, 2131165509, 1).show();
            return null;
        }
        String gpsCord = getStringPosFromExif(context, uri);
        if (gpsCord != null) {
            return gpsCord;
        }
        Toast.makeText(context, 2131165508, 1).show();
        return gpsCord;
    }

    private static String getStringPosFromExif(Context context, Uri uri) {
        String str = null;
        try {
            GpsDirectory directory = (GpsDirectory) ImageMetadataReader.readMetadata(context.getContentResolver().openInputStream(uri)).getDirectory(GpsDirectory.class);
            if (!(directory == null || directory.getGeoLocation() == null)) {
                str = String.format(Locale.US, "%f|%f", new Object[]{Double.valueOf(directory.getGeoLocation().getLongitude()), Double.valueOf(directory.getGeoLocation().getLatitude())});
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return str;
    }

    private static boolean isIntentAvailable(Context context, Intent intent) {
        return (intent == null || intent.resolveActivity(context.getPackageManager()) == null) ? false : true;
    }
}
