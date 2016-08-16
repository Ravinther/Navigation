package com.sygic.aura.blackbox;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.MediaStore.Video.Media;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.Builder;
import com.sygic.base.C1799R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BlackBoxService extends IntentService {
    private String mNotificationSaved;
    private String mNotificationSaving;
    private String mNotificationShare;

    /* renamed from: com.sygic.aura.blackbox.BlackBoxService.1 */
    class C11421 implements OnScanCompletedListener {
        C11421() {
        }

        public void onScanCompleted(String path, Uri uri) {
            synchronized (BlackBoxService.this) {
                BlackBoxService.this.notify();
            }
        }
    }

    public BlackBoxService() {
        super("BlackBoxService");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    protected void onHandleIntent(Intent intent) {
        String[] filePaths;
        String[] infoPaths;
        if (intent.getAction().equals("blackbox_move_to_public")) {
            this.mNotificationSaving = intent.getStringExtra("blackbox_extra_notif_saving");
            this.mNotificationSaved = intent.getStringExtra("blackbox_extra_notif_saved");
            this.mNotificationShare = intent.getStringExtra("blackbox_extra_notif_share");
            filePaths = intent.getStringArrayExtra("blackbox_extra_files_paths");
            infoPaths = intent.getStringArrayExtra("blackbox_extra_info_files_paths");
            int bitRate = intent.getIntExtra("blackbox_extra_bitrate", 0);
            int frameRate = intent.getIntExtra("blackbox_extra_framerate", 0);
            if (filePaths.length != 0 && allFilesExist(filePaths) && allFilesExist(infoPaths)) {
                showNotification();
                processVideoToPublic(filePaths, infoPaths, frameRate, bitRate);
            }
            cleanUpFiles(filePaths);
            cleanUpFiles(infoPaths);
        }
        if (intent.getAction().equals("blackbox_delete_files")) {
            filePaths = intent.getStringArrayExtra("blackbox_extra_files_paths");
            infoPaths = intent.getStringArrayExtra("blackbox_extra_info_files_paths");
            cleanUpFiles(filePaths);
            cleanUpFiles(infoPaths);
        }
    }

    private boolean allFilesExist(String[] filePaths) {
        for (String fileName : filePaths) {
            if (!new File(fileName).exists()) {
                return false;
            }
        }
        return true;
    }

    private void showNotification() {
        Builder builder = new Builder(this);
        builder.setContentTitle(this.mNotificationSaving);
        builder.setProgress(0, 0, true);
        builder.setSmallIcon(C1799R.drawable.sygic_bar);
        ((NotificationManager) getSystemService("notification")).notify(3, builder.build());
    }

    private void updateNotification(String videoFile) {
        Uri fileUri = getContentUri(videoFile);
        if (fileUri == null) {
            hideNotification();
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(fileUri);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 134217728);
        Builder builder = new Builder(this);
        builder.setContentTitle(this.mNotificationSaved);
        builder.setSmallIcon(C1799R.drawable.sygic_bar);
        builder.setContentIntent(pendingIntent);
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoFile, 1);
        builder.setLargeIcon(thumb);
        builder.setAutoCancel(true);
        if (VERSION.SDK_INT >= 16) {
            BigPictureStyle bigStyle = new BigPictureStyle();
            bigStyle.bigPicture(thumb);
            bigStyle.bigLargeIcon(null);
            Intent shareIntent = new Intent("android.intent.action.SEND");
            shareIntent.setType("video/*");
            shareIntent.putExtra("android.intent.extra.STREAM", fileUri);
            builder.addAction(17301586, this.mNotificationShare, PendingIntent.getActivity(this, 0, shareIntent, 134217728));
            builder.setStyle(bigStyle);
        }
        ((NotificationManager) getSystemService("notification")).notify(3, builder.build());
    }

    private void hideNotification() {
        ((NotificationManager) getSystemService("notification")).cancel(3);
    }

    private Uri getContentUri(String videoFile) {
        Cursor res = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{videoFile}, null);
        if (res == null) {
            return null;
        }
        if (res.moveToNext()) {
            int id = res.getInt(0);
            res.close();
            return Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
        }
        res.close();
        return null;
    }

    private void processVideoToPublic(String[] filePaths, String[] infoPaths, int frameRate, int bitRate) {
        File firstInput = new File(filePaths[0]);
        File intermediateFile = FFmpegHelper.getIntermediateFile(firstInput);
        Mp4parserHelper.concatenateVideoFiles(filePaths, intermediateFile.getAbsolutePath());
        Mp4parserHelper.addSubtitleTrackToVideo(intermediateFile.getAbsolutePath(), infoPaths);
        intermediateFile.renameTo(firstInput);
        moveTempFileToPublicStorage(firstInput);
        FFmpegHelper.createSubtitlesFile(infoPaths, new File(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sygic"), firstInput.getName().substring(0, firstInput.getName().indexOf(".")) + ".srt"));
    }

    private void cleanUpFiles(String[] filePaths) {
        for (String s : filePaths) {
            File f = new File(s);
            if (f.exists()) {
                f.delete();
            }
        }
    }

    private void moveTempFileToPublicStorage(File file) {
        File sygicDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sygic");
        if (!sygicDir.exists()) {
            sygicDir.mkdirs();
        }
        File dst = new File(sygicDir, file.getName());
        try {
            copy(file, dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            file.delete();
        }
        addToGallery(dst);
    }

    public void addToGallery(File file) {
        MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, new C11421());
        synchronized (this) {
            try {
                wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateNotification(file.getAbsolutePath());
        }
    }

    private void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }
}
