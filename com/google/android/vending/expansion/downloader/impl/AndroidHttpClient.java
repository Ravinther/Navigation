package com.google.android.vending.expansion.downloader.impl;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.os.Looper;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

public final class AndroidHttpClient implements HttpClient {
    public static long DEFAULT_SYNC_MIN_GZIP_BYTES;
    static Class<?> sSslSessionCacheClass;
    private static final HttpRequestInterceptor sThreadCheckInterceptor;
    private volatile LoggingConfiguration curlConfiguration;
    private final HttpClient delegate;
    private RuntimeException mLeakedException;

    /* renamed from: com.google.android.vending.expansion.downloader.impl.AndroidHttpClient.1 */
    static class C10341 implements HttpRequestInterceptor {
        C10341() {
        }

        public void process(HttpRequest request, HttpContext context) {
            if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("This thread forbids HTTP requests");
            }
        }
    }

    /* renamed from: com.google.android.vending.expansion.downloader.impl.AndroidHttpClient.2 */
    class C10352 extends DefaultHttpClient {
        C10352(ClientConnectionManager x0, HttpParams x1) {
            super(x0, x1);
        }

        protected BasicHttpProcessor createHttpProcessor() {
            BasicHttpProcessor processor = super.createHttpProcessor();
            processor.addRequestInterceptor(AndroidHttpClient.sThreadCheckInterceptor);
            processor.addRequestInterceptor(new CurlLogger(null));
            return processor;
        }

        protected HttpContext createHttpContext() {
            HttpContext context = new BasicHttpContext();
            context.setAttribute("http.authscheme-registry", getAuthSchemes());
            context.setAttribute("http.cookiespec-registry", getCookieSpecs());
            context.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
            return context;
        }
    }

    private class CurlLogger implements HttpRequestInterceptor {
        private CurlLogger() {
        }

        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            LoggingConfiguration configuration = AndroidHttpClient.this.curlConfiguration;
            if (configuration != null && configuration.isLoggable() && (request instanceof HttpUriRequest)) {
                configuration.println(AndroidHttpClient.toCurl((HttpUriRequest) request, false));
            }
        }
    }

    private static class LoggingConfiguration {
        private final int level;
        private final String tag;

        private boolean isLoggable() {
            return Log.isLoggable(this.tag, this.level);
        }

        private void println(String message) {
            Log.println(this.level, this.tag, message);
        }
    }

    static {
        try {
            sSslSessionCacheClass = Class.forName("android.net.SSLSessionCache");
        } catch (Exception e) {
        }
        DEFAULT_SYNC_MIN_GZIP_BYTES = 256;
        sThreadCheckInterceptor = new C10341();
    }

    public static AndroidHttpClient newInstance(String userAgent, Context context) {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setConnectionTimeout(params, 60000);
        HttpConnectionParams.setSoTimeout(params, 60000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpClientParams.setRedirecting(params, false);
        Object sessionCache = null;
        if (!(context == null || sSslSessionCacheClass == null)) {
            try {
                sessionCache = sSslSessionCacheClass.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (InstantiationException e4) {
                e4.printStackTrace();
            } catch (IllegalAccessException e5) {
                e5.printStackTrace();
            } catch (InvocationTargetException e6) {
                e6.printStackTrace();
            }
        }
        HttpProtocolParams.setUserAgent(params, userAgent);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        SocketFactory sslCertificateSocketFactory = null;
        if (sessionCache != null) {
            try {
                sslCertificateSocketFactory = (SocketFactory) SSLCertificateSocketFactory.class.getDeclaredMethod("getHttpSocketFactory", new Class[]{Integer.TYPE, sSslSessionCacheClass}).invoke(null, new Object[]{Integer.valueOf(60000), sessionCache});
            } catch (SecurityException e7) {
                e7.printStackTrace();
            } catch (NoSuchMethodException e22) {
                e22.printStackTrace();
            } catch (IllegalArgumentException e32) {
                e32.printStackTrace();
            } catch (IllegalAccessException e52) {
                e52.printStackTrace();
            } catch (InvocationTargetException e62) {
                e62.printStackTrace();
            }
        }
        if (sslCertificateSocketFactory == null) {
            sslCertificateSocketFactory = SSLSocketFactory.getSocketFactory();
        }
        schemeRegistry.register(new Scheme("https", sslCertificateSocketFactory, 443));
        return new AndroidHttpClient(new ThreadSafeClientConnManager(params, schemeRegistry), params);
    }

    private AndroidHttpClient(ClientConnectionManager ccm, HttpParams params) {
        this.mLeakedException = new IllegalStateException("AndroidHttpClient created and never closed");
        this.delegate = new C10352(ccm, params);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.mLeakedException != null) {
            Log.e("AndroidHttpClient", "Leak found", this.mLeakedException);
            this.mLeakedException = null;
        }
    }

    public void close() {
        if (this.mLeakedException != null) {
            getConnectionManager().shutdown();
            this.mLeakedException = null;
        }
    }

    public HttpParams getParams() {
        return this.delegate.getParams();
    }

    public ClientConnectionManager getConnectionManager() {
        return this.delegate.getConnectionManager();
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return this.delegate.execute(request);
    }

    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
        return this.delegate.execute(request, context);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
        return this.delegate.execute(target, request);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        return this.delegate.execute(target, request, context);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.delegate.execute(request, responseHandler);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return this.delegate.execute(request, responseHandler, context);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.delegate.execute(target, request, responseHandler);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return this.delegate.execute(target, request, responseHandler, context);
    }

    private static String toCurl(HttpUriRequest request, boolean logAuthToken) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("curl ");
        for (Header header : request.getAllHeaders()) {
            if (logAuthToken || !(header.getName().equals("Authorization") || header.getName().equals("Cookie"))) {
                builder.append("--header \"");
                builder.append(header.toString().trim());
                builder.append("\" ");
            }
        }
        URI uri = request.getURI();
        if (request instanceof RequestWrapper) {
            HttpRequest original = ((RequestWrapper) request).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) original).getURI();
            }
        }
        builder.append("\"");
        builder.append(uri);
        builder.append("\"");
        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            if (entity != null && entity.isRepeatable()) {
                if (entity.getContentLength() < 1024) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    entity.writeTo(stream);
                    builder.append(" --data-ascii \"").append(stream.toString()).append("\"");
                } else {
                    builder.append(" [TOO MUCH DATA TO INCLUDE]");
                }
            }
        }
        return builder.toString();
    }
}
