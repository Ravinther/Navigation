package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Downloader.ResponseException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttpDownloader implements Downloader {
    private final OkUrlFactory urlFactory;

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpDownloader(File cacheDir) {
        this(cacheDir, Utils.calculateDiskCacheSize(cacheDir));
    }

    public OkHttpDownloader(File cacheDir, long maxSize) {
        this(new OkHttpClient());
        try {
            this.urlFactory.client().setCache(new Cache(cacheDir, maxSize));
        } catch (IOException e) {
        }
    }

    public OkHttpDownloader(OkHttpClient client) {
        this.urlFactory = new OkUrlFactory(client);
    }

    protected HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection connection = this.urlFactory.open(new URL(uri.toString()));
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(20000);
        return connection;
    }

    public Response load(Uri uri, boolean localCacheOnly) throws IOException {
        HttpURLConnection connection = openConnection(uri);
        connection.setUseCaches(true);
        if (localCacheOnly) {
            connection.setRequestProperty("Cache-Control", "only-if-cached,max-age=2147483647");
        }
        int responseCode = connection.getResponseCode();
        if (responseCode >= 300) {
            connection.disconnect();
            throw new ResponseException(responseCode + " " + connection.getResponseMessage());
        }
        String responseSource = connection.getHeaderField("OkHttp-Response-Source");
        if (responseSource == null) {
            responseSource = connection.getHeaderField("X-Android-Response-Source");
        }
        long contentLength = (long) connection.getHeaderFieldInt("Content-Length", -1);
        return new Response(connection.getInputStream(), Utils.parseResponseSourceHeader(responseSource), contentLength);
    }
}
