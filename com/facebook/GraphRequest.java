package com.facebook;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Logger;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphRequest {
    public static final String TAG;
    private static String defaultBatchApplicationId;
    private static volatile String userAgent;
    private static Pattern versionPattern;
    private AccessToken accessToken;
    private String batchEntryDependsOn;
    private String batchEntryName;
    private boolean batchEntryOmitResultOnSuccess;
    private Callback callback;
    private JSONObject graphObject;
    private String graphPath;
    private HttpMethod httpMethod;
    private String overriddenURL;
    private Bundle parameters;
    private boolean skipClientToken;
    private Object tag;
    private String version;

    public interface Callback {
        void onCompleted(GraphResponse graphResponse);
    }

    /* renamed from: com.facebook.GraphRequest.1 */
    static class C03141 implements Callback {
        final /* synthetic */ GraphJSONObjectCallback val$callback;

        public void onCompleted(GraphResponse response) {
            if (this.val$callback != null) {
                this.val$callback.onCompleted(response.getJSONObject(), response);
            }
        }
    }

    /* renamed from: com.facebook.GraphRequest.4 */
    class C03154 implements Callback {
        final /* synthetic */ Callback val$callback;

        C03154(Callback callback) {
            this.val$callback = callback;
        }

        public void onCompleted(GraphResponse response) {
            JSONObject debug;
            JSONArray debugMessages;
            JSONObject responseObject = response.getJSONObject();
            if (responseObject != null) {
                debug = responseObject.optJSONObject("__debug__");
            } else {
                debug = null;
            }
            if (debug != null) {
                debugMessages = debug.optJSONArray("messages");
            } else {
                debugMessages = null;
            }
            if (debugMessages != null) {
                for (int i = 0; i < debugMessages.length(); i++) {
                    String debugMessage;
                    String debugMessageType;
                    String debugMessageLink;
                    JSONObject debugMessageObject = debugMessages.optJSONObject(i);
                    if (debugMessageObject != null) {
                        debugMessage = debugMessageObject.optString("message");
                    } else {
                        debugMessage = null;
                    }
                    if (debugMessageObject != null) {
                        debugMessageType = debugMessageObject.optString("type");
                    } else {
                        debugMessageType = null;
                    }
                    if (debugMessageObject != null) {
                        debugMessageLink = debugMessageObject.optString("link");
                    } else {
                        debugMessageLink = null;
                    }
                    if (!(debugMessage == null || debugMessageType == null)) {
                        LoggingBehavior behavior = LoggingBehavior.GRAPH_API_DEBUG_INFO;
                        if (debugMessageType.equals("warning")) {
                            behavior = LoggingBehavior.GRAPH_API_DEBUG_WARNING;
                        }
                        if (!Utility.isNullOrEmpty(debugMessageLink)) {
                            debugMessage = debugMessage + " Link: " + debugMessageLink;
                        }
                        Logger.log(behavior, GraphRequest.TAG, debugMessage);
                    }
                }
            }
            if (this.val$callback != null) {
                this.val$callback.onCompleted(response);
            }
        }
    }

    /* renamed from: com.facebook.GraphRequest.5 */
    static class C03165 implements Runnable {
        final /* synthetic */ ArrayList val$callbacks;
        final /* synthetic */ GraphRequestBatch val$requests;

        C03165(ArrayList arrayList, GraphRequestBatch graphRequestBatch) {
            this.val$callbacks = arrayList;
            this.val$requests = graphRequestBatch;
        }

        public void run() {
            Iterator it = this.val$callbacks.iterator();
            while (it.hasNext()) {
                Pair<Callback, GraphResponse> pair = (Pair) it.next();
                ((Callback) pair.first).onCompleted((GraphResponse) pair.second);
            }
            for (com.facebook.GraphRequestBatch.Callback batchCallback : this.val$requests.getCallbacks()) {
                batchCallback.onBatchCompleted(this.val$requests);
            }
        }
    }

    private interface KeyValueSerializer {
        void writeString(String str, String str2) throws IOException;
    }

    /* renamed from: com.facebook.GraphRequest.6 */
    class C03176 implements KeyValueSerializer {
        final /* synthetic */ ArrayList val$keysAndValues;

        C03176(ArrayList arrayList) {
            this.val$keysAndValues = arrayList;
        }

        public void writeString(String key, String value) throws IOException {
            this.val$keysAndValues.add(String.format(Locale.US, "%s=%s", new Object[]{key, URLEncoder.encode(value, "UTF-8")}));
        }
    }

    private static class Attachment {
        private final GraphRequest request;
        private final Object value;

        public Attachment(GraphRequest request, Object value) {
            this.request = request;
            this.value = value;
        }

        public GraphRequest getRequest() {
            return this.request;
        }

        public Object getValue() {
            return this.value;
        }
    }

    public interface GraphJSONObjectCallback {
        void onCompleted(JSONObject jSONObject, GraphResponse graphResponse);
    }

    public interface OnProgressCallback extends Callback {
        void onProgress(long j, long j2);
    }

    public static class ParcelableResourceWithMimeType<RESOURCE extends Parcelable> implements Parcelable {
        public static final Creator<ParcelableResourceWithMimeType> CREATOR;
        private final String mimeType;
        private final RESOURCE resource;

        /* renamed from: com.facebook.GraphRequest.ParcelableResourceWithMimeType.1 */
        static class C03181 implements Creator<ParcelableResourceWithMimeType> {
            C03181() {
            }

            public ParcelableResourceWithMimeType createFromParcel(Parcel in) {
                return new ParcelableResourceWithMimeType(null);
            }

            public ParcelableResourceWithMimeType[] newArray(int size) {
                return new ParcelableResourceWithMimeType[size];
            }
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public RESOURCE getResource() {
            return this.resource;
        }

        public int describeContents() {
            return 1;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.mimeType);
            out.writeParcelable(this.resource, flags);
        }

        static {
            CREATOR = new C03181();
        }

        private ParcelableResourceWithMimeType(Parcel in) {
            this.mimeType = in.readString();
            this.resource = in.readParcelable(FacebookSdk.getApplicationContext().getClassLoader());
        }
    }

    private static class Serializer implements KeyValueSerializer {
        private boolean firstWrite;
        private final Logger logger;
        private final OutputStream outputStream;
        private boolean useUrlEncode;

        public Serializer(OutputStream outputStream, Logger logger, boolean useUrlEncode) {
            this.firstWrite = true;
            this.useUrlEncode = false;
            this.outputStream = outputStream;
            this.logger = logger;
            this.useUrlEncode = useUrlEncode;
        }

        public void writeObject(String key, Object value, GraphRequest request) throws IOException {
            if (this.outputStream instanceof RequestOutputStream) {
                ((RequestOutputStream) this.outputStream).setCurrentRequest(request);
            }
            if (GraphRequest.isSupportedParameterType(value)) {
                writeString(key, GraphRequest.parameterToString(value));
            } else if (value instanceof Bitmap) {
                writeBitmap(key, (Bitmap) value);
            } else if (value instanceof byte[]) {
                writeBytes(key, (byte[]) value);
            } else if (value instanceof Uri) {
                writeContentUri(key, (Uri) value, null);
            } else if (value instanceof ParcelFileDescriptor) {
                writeFile(key, (ParcelFileDescriptor) value, null);
            } else if (value instanceof ParcelableResourceWithMimeType) {
                ParcelableResourceWithMimeType resourceWithMimeType = (ParcelableResourceWithMimeType) value;
                Parcelable resource = resourceWithMimeType.getResource();
                String mimeType = resourceWithMimeType.getMimeType();
                if (resource instanceof ParcelFileDescriptor) {
                    writeFile(key, (ParcelFileDescriptor) resource, mimeType);
                } else if (resource instanceof Uri) {
                    writeContentUri(key, (Uri) resource, mimeType);
                } else {
                    throw getInvalidTypeError();
                }
            } else {
                throw getInvalidTypeError();
            }
        }

        private RuntimeException getInvalidTypeError() {
            return new IllegalArgumentException("value is not a supported type.");
        }

        public void writeRequestsAsJson(String key, JSONArray requestJsonArray, Collection<GraphRequest> requests) throws IOException, JSONException {
            if (this.outputStream instanceof RequestOutputStream) {
                RequestOutputStream requestOutputStream = this.outputStream;
                writeContentDisposition(key, null, null);
                write("[", new Object[0]);
                int i = 0;
                for (GraphRequest request : requests) {
                    JSONObject requestJson = requestJsonArray.getJSONObject(i);
                    requestOutputStream.setCurrentRequest(request);
                    if (i > 0) {
                        write(",%s", requestJson.toString());
                    } else {
                        write("%s", requestJson.toString());
                    }
                    i++;
                }
                write("]", new Object[0]);
                if (this.logger != null) {
                    this.logger.appendKeyValue("    " + key, requestJsonArray.toString());
                    return;
                }
                return;
            }
            writeString(key, requestJsonArray.toString());
        }

        public void writeString(String key, String value) throws IOException {
            writeContentDisposition(key, null, null);
            writeLine("%s", value);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, value);
            }
        }

        public void writeBitmap(String key, Bitmap bitmap) throws IOException {
            writeContentDisposition(key, key, "image/png");
            bitmap.compress(CompressFormat.PNG, 100, this.outputStream);
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, "<Image>");
            }
        }

        public void writeBytes(String key, byte[] bytes) throws IOException {
            writeContentDisposition(key, key, "content/unknown");
            this.outputStream.write(bytes);
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(bytes.length)}));
            }
        }

        public void writeContentUri(String key, Uri contentUri, String mimeType) throws IOException {
            if (mimeType == null) {
                mimeType = "content/unknown";
            }
            writeContentDisposition(key, key, mimeType);
            InputStream inputStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(contentUri);
            int totalBytes = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                ((ProgressNoopOutputStream) this.outputStream).addProgress(Utility.getContentSize(contentUri));
            } else {
                totalBytes = 0 + Utility.copyAndCloseInputStream(inputStream, this.outputStream);
            }
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(totalBytes)}));
            }
        }

        public void writeFile(String key, ParcelFileDescriptor descriptor, String mimeType) throws IOException {
            if (mimeType == null) {
                mimeType = "content/unknown";
            }
            writeContentDisposition(key, key, mimeType);
            int totalBytes = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                ((ProgressNoopOutputStream) this.outputStream).addProgress(descriptor.getStatSize());
            } else {
                totalBytes = 0 + Utility.copyAndCloseInputStream(new AutoCloseInputStream(descriptor), this.outputStream);
            }
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(totalBytes)}));
            }
        }

        public void writeRecordBoundary() throws IOException {
            if (this.useUrlEncode) {
                this.outputStream.write("&".getBytes());
                return;
            }
            writeLine("--%s", "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
        }

        public void writeContentDisposition(String name, String filename, String contentType) throws IOException {
            if (this.useUrlEncode) {
                this.outputStream.write(String.format("%s=", new Object[]{name}).getBytes());
                return;
            }
            write("Content-Disposition: form-data; name=\"%s\"", name);
            if (filename != null) {
                write("; filename=\"%s\"", filename);
            }
            writeLine("", new Object[0]);
            if (contentType != null) {
                writeLine("%s: %s", "Content-Type", contentType);
            }
            writeLine("", new Object[0]);
        }

        public void write(String format, Object... args) throws IOException {
            if (this.useUrlEncode) {
                this.outputStream.write(URLEncoder.encode(String.format(Locale.US, format, args), "UTF-8").getBytes());
                return;
            }
            if (this.firstWrite) {
                this.outputStream.write("--".getBytes());
                this.outputStream.write("3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f".getBytes());
                this.outputStream.write("\r\n".getBytes());
                this.firstWrite = false;
            }
            this.outputStream.write(String.format(format, args).getBytes());
        }

        public void writeLine(String format, Object... args) throws IOException {
            write(format, args);
            if (!this.useUrlEncode) {
                write("\r\n", new Object[0]);
            }
        }
    }

    static {
        TAG = GraphRequest.class.getSimpleName();
        versionPattern = Pattern.compile("^/?v\\d+\\.\\d+/(.*)");
    }

    public GraphRequest() {
        this(null, null, null, null, null);
    }

    public GraphRequest(AccessToken accessToken, String graphPath, Bundle parameters, HttpMethod httpMethod, Callback callback) {
        this(accessToken, graphPath, parameters, httpMethod, callback, null);
    }

    public GraphRequest(AccessToken accessToken, String graphPath, Bundle parameters, HttpMethod httpMethod, Callback callback, String version) {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.accessToken = accessToken;
        this.graphPath = graphPath;
        this.version = version;
        setCallback(callback);
        setHttpMethod(httpMethod);
        if (parameters != null) {
            this.parameters = new Bundle(parameters);
        } else {
            this.parameters = new Bundle();
        }
        if (this.version == null) {
            this.version = ServerProtocol.getAPIVersion();
        }
    }

    public static GraphRequest newPostRequest(AccessToken accessToken, String graphPath, JSONObject graphObject, Callback callback) {
        GraphRequest request = new GraphRequest(accessToken, graphPath, null, HttpMethod.POST, callback);
        request.setGraphObject(graphObject);
        return request;
    }

    public static GraphRequest newGraphPathRequest(AccessToken accessToken, String graphPath, Callback callback) {
        return new GraphRequest(accessToken, graphPath, null, null, callback);
    }

    public final JSONObject getGraphObject() {
        return this.graphObject;
    }

    public final void setGraphObject(JSONObject graphObject) {
        this.graphObject = graphObject;
    }

    public final String getGraphPath() {
        return this.graphPath;
    }

    public final HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public final void setHttpMethod(HttpMethod httpMethod) {
        if (this.overriddenURL == null || httpMethod == HttpMethod.GET) {
            if (httpMethod == null) {
                httpMethod = HttpMethod.GET;
            }
            this.httpMethod = httpMethod;
            return;
        }
        throw new FacebookException("Can't change HTTP method on request with overridden URL.");
    }

    public final String getVersion() {
        return this.version;
    }

    public final void setSkipClientToken(boolean skipClientToken) {
        this.skipClientToken = skipClientToken;
    }

    public final Bundle getParameters() {
        return this.parameters;
    }

    public final void setParameters(Bundle parameters) {
        this.parameters = parameters;
    }

    public final AccessToken getAccessToken() {
        return this.accessToken;
    }

    public final Callback getCallback() {
        return this.callback;
    }

    public final void setCallback(Callback callback) {
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO) || FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.callback = new C03154(callback);
        } else {
            this.callback = callback;
        }
    }

    public final void setTag(Object tag) {
        this.tag = tag;
    }

    public final Object getTag() {
        return this.tag;
    }

    public final GraphResponse executeAndWait() {
        return executeAndWait(this);
    }

    public final GraphRequestAsyncTask executeAsync() {
        return executeBatchAsync(this);
    }

    public static HttpURLConnection toHttpConnection(GraphRequestBatch requests) {
        validateFieldsParamForGetRequests(requests);
        try {
            URL url;
            if (requests.size() == 1) {
                url = new URL(requests.get(0).getUrlForSingleRequest());
            } else {
                url = new URL(ServerProtocol.getGraphUrlBase());
            }
            try {
                HttpURLConnection connection = createConnection(url);
                serializeToUrlConnection(requests, connection);
                return connection;
            } catch (Throwable e) {
                throw new FacebookException("could not construct request body", e);
            } catch (Throwable e2) {
                throw new FacebookException("could not construct request body", e2);
            }
        } catch (Throwable e22) {
            throw new FacebookException("could not construct URL for request", e22);
        }
    }

    public static GraphResponse executeAndWait(GraphRequest request) {
        List<GraphResponse> responses = executeBatchAndWait(request);
        if (responses != null && responses.size() == 1) {
            return (GraphResponse) responses.get(0);
        }
        throw new FacebookException("invalid state: expected a single response");
    }

    public static List<GraphResponse> executeBatchAndWait(GraphRequest... requests) {
        Validate.notNull(requests, "requests");
        return executeBatchAndWait(Arrays.asList(requests));
    }

    public static List<GraphResponse> executeBatchAndWait(Collection<GraphRequest> requests) {
        return executeBatchAndWait(new GraphRequestBatch((Collection) requests));
    }

    public static List<GraphResponse> executeBatchAndWait(GraphRequestBatch requests) {
        Validate.notEmptyAndContainsNoNulls(requests, "requests");
        try {
            return executeConnectionAndWait(toHttpConnection(requests), requests);
        } catch (Throwable ex) {
            List<GraphResponse> responses = GraphResponse.constructErrorResponses(requests.getRequests(), null, new FacebookException(ex));
            runCallbacks(requests, responses);
            return responses;
        }
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequest... requests) {
        Validate.notNull(requests, "requests");
        return executeBatchAsync(Arrays.asList(requests));
    }

    public static GraphRequestAsyncTask executeBatchAsync(Collection<GraphRequest> requests) {
        return executeBatchAsync(new GraphRequestBatch((Collection) requests));
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequestBatch requests) {
        Validate.notEmptyAndContainsNoNulls(requests, "requests");
        GraphRequestAsyncTask asyncTask = new GraphRequestAsyncTask(requests);
        asyncTask.executeOnSettingsExecutor();
        return asyncTask;
    }

    public static List<GraphResponse> executeConnectionAndWait(HttpURLConnection connection, GraphRequestBatch requests) {
        List<GraphResponse> responses = GraphResponse.fromHttpConnection(connection, requests);
        Utility.disconnectQuietly(connection);
        if (requests.size() != responses.size()) {
            throw new FacebookException(String.format(Locale.US, "Received %d responses while expecting %d", new Object[]{Integer.valueOf(responses.size()), Integer.valueOf(requests.size())}));
        }
        runCallbacks(requests, responses);
        AccessTokenManager.getInstance().extendAccessTokenIfNeeded();
        return responses;
    }

    public String toString() {
        return "{Request: " + " accessToken: " + (this.accessToken == null ? "null" : this.accessToken) + ", graphPath: " + this.graphPath + ", graphObject: " + this.graphObject + ", httpMethod: " + this.httpMethod + ", parameters: " + this.parameters + "}";
    }

    static void runCallbacks(GraphRequestBatch requests, List<GraphResponse> responses) {
        int numRequests = requests.size();
        ArrayList<Pair<Callback, GraphResponse>> callbacks = new ArrayList();
        for (int i = 0; i < numRequests; i++) {
            GraphRequest request = requests.get(i);
            if (request.callback != null) {
                callbacks.add(new Pair(request.callback, responses.get(i)));
            }
        }
        if (callbacks.size() > 0) {
            Runnable runnable = new C03165(callbacks, requests);
            Handler callbackHandler = requests.getCallbackHandler();
            if (callbackHandler == null) {
                runnable.run();
            } else {
                callbackHandler.post(runnable);
            }
        }
    }

    private static HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", getUserAgent());
        connection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
        connection.setChunkedStreamingMode(0);
        return connection;
    }

    private void addCommonParameters() {
        if (this.accessToken != null) {
            if (!this.parameters.containsKey("access_token")) {
                String token = this.accessToken.getToken();
                Logger.registerAccessToken(token);
                this.parameters.putString("access_token", token);
            }
        } else if (!(this.skipClientToken || this.parameters.containsKey("access_token"))) {
            String appID = FacebookSdk.getApplicationId();
            String clientToken = FacebookSdk.getClientToken();
            if (Utility.isNullOrEmpty(appID) || Utility.isNullOrEmpty(clientToken)) {
                Log.d(TAG, "Warning: Request without access token missing application ID or client token.");
            } else {
                this.parameters.putString("access_token", appID + "|" + clientToken);
            }
        }
        this.parameters.putString("sdk", "android");
        this.parameters.putString("format", "json");
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO)) {
            this.parameters.putString("debug", "info");
        } else if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.parameters.putString("debug", "warning");
        }
    }

    private String appendParametersToBaseUrl(String baseUrl) {
        Builder uriBuilder = new Builder().encodedPath(baseUrl);
        for (String key : this.parameters.keySet()) {
            Object value = this.parameters.get(key);
            if (value == null) {
                value = "";
            }
            if (isSupportedParameterType(value)) {
                uriBuilder.appendQueryParameter(key, parameterToString(value).toString());
            } else if (this.httpMethod == HttpMethod.GET) {
                throw new IllegalArgumentException(String.format(Locale.US, "Unsupported parameter type for GET request: %s", new Object[]{value.getClass().getSimpleName()}));
            }
        }
        return uriBuilder.toString();
    }

    final String getUrlForBatchedRequest() {
        if (this.overriddenURL != null) {
            throw new FacebookException("Can't override URL for a batch request");
        }
        String baseUrl = getGraphPathWithVersion();
        addCommonParameters();
        return appendParametersToBaseUrl(baseUrl);
    }

    final String getUrlForSingleRequest() {
        if (this.overriddenURL != null) {
            return this.overriddenURL.toString();
        }
        String graphBaseUrlBase;
        if (getHttpMethod() == HttpMethod.POST && this.graphPath != null && this.graphPath.endsWith("/videos")) {
            graphBaseUrlBase = ServerProtocol.getGraphVideoUrlBase();
        } else {
            graphBaseUrlBase = ServerProtocol.getGraphUrlBase();
        }
        String baseUrl = String.format("%s/%s", new Object[]{graphBaseUrlBase, getGraphPathWithVersion()});
        addCommonParameters();
        return appendParametersToBaseUrl(baseUrl);
    }

    private String getGraphPathWithVersion() {
        if (versionPattern.matcher(this.graphPath).matches()) {
            return this.graphPath;
        }
        return String.format("%s/%s", new Object[]{this.version, this.graphPath});
    }

    private void serializeToBatch(JSONArray batch, Map<String, Attachment> attachments) throws JSONException, IOException {
        JSONObject batchEntry = new JSONObject();
        if (this.batchEntryName != null) {
            batchEntry.put("name", this.batchEntryName);
            batchEntry.put("omit_response_on_success", this.batchEntryOmitResultOnSuccess);
        }
        if (this.batchEntryDependsOn != null) {
            batchEntry.put("depends_on", this.batchEntryDependsOn);
        }
        String relativeURL = getUrlForBatchedRequest();
        batchEntry.put("relative_url", relativeURL);
        batchEntry.put("method", this.httpMethod);
        if (this.accessToken != null) {
            Logger.registerAccessToken(this.accessToken.getToken());
        }
        ArrayList<String> attachmentNames = new ArrayList();
        for (String key : this.parameters.keySet()) {
            Object value = this.parameters.get(key);
            if (isSupportedAttachmentType(value)) {
                String name = String.format(Locale.ROOT, "%s%d", new Object[]{"file", Integer.valueOf(attachments.size())});
                attachmentNames.add(name);
                attachments.put(name, new Attachment(this, value));
            }
        }
        if (!attachmentNames.isEmpty()) {
            batchEntry.put("attached_files", TextUtils.join(",", attachmentNames));
        }
        if (this.graphObject != null) {
            ArrayList<String> keysAndValues = new ArrayList();
            processGraphObject(this.graphObject, relativeURL, new C03176(keysAndValues));
            batchEntry.put("body", TextUtils.join("&", keysAndValues));
        }
        batch.put(batchEntry);
    }

    private static boolean hasOnProgressCallbacks(GraphRequestBatch requests) {
        for (com.facebook.GraphRequestBatch.Callback callback : requests.getCallbacks()) {
            if (callback instanceof com.facebook.GraphRequestBatch.OnProgressCallback) {
                return true;
            }
        }
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            if (((GraphRequest) it.next()).getCallback() instanceof OnProgressCallback) {
                return true;
            }
        }
        return false;
    }

    private static void setConnectionContentType(HttpURLConnection connection, boolean shouldUseGzip) {
        if (shouldUseGzip) {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Encoding", "gzip");
            return;
        }
        connection.setRequestProperty("Content-Type", getMimeContentType());
    }

    private static boolean isGzipCompressible(GraphRequestBatch requests) {
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            GraphRequest request = (GraphRequest) it.next();
            for (String key : request.parameters.keySet()) {
                if (isSupportedAttachmentType(request.parameters.get(key))) {
                    return false;
                }
            }
        }
        return true;
    }

    static final boolean shouldWarnOnMissingFieldsParam(GraphRequest request) {
        boolean z = false;
        String version = request.getVersion();
        if (Utility.isNullOrEmpty(version)) {
            return true;
        }
        if (version.startsWith("v")) {
            version = version.substring(1);
        }
        String[] versionParts = version.split("\\.");
        if ((versionParts.length >= 2 && Integer.parseInt(versionParts[0]) > 2) || (Integer.parseInt(versionParts[0]) >= 2 && Integer.parseInt(versionParts[1]) >= 4)) {
            z = true;
        }
        return z;
    }

    static final void validateFieldsParamForGetRequests(GraphRequestBatch requests) {
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            GraphRequest request = (GraphRequest) it.next();
            if (HttpMethod.GET.equals(request.getHttpMethod()) && shouldWarnOnMissingFieldsParam(request)) {
                Bundle params = request.getParameters();
                if (!params.containsKey("fields") || Utility.isNullOrEmpty(params.getString("fields"))) {
                    Logger.log(LoggingBehavior.DEVELOPER_ERRORS, 5, "Request", "starting with Graph API v2.4, GET requests for /%s should contain an explicit \"fields\" parameter.", request.getGraphPath());
                }
            }
        }
    }

    static final void serializeToUrlConnection(GraphRequestBatch requests, HttpURLConnection connection) throws IOException, JSONException {
        Throwable th;
        Logger logger = new Logger(LoggingBehavior.REQUESTS, "Request");
        int numRequests = requests.size();
        boolean shouldUseGzip = isGzipCompressible(requests);
        HttpMethod connectionHttpMethod = numRequests == 1 ? requests.get(0).httpMethod : HttpMethod.POST;
        connection.setRequestMethod(connectionHttpMethod.name());
        setConnectionContentType(connection, shouldUseGzip);
        URL url = connection.getURL();
        logger.append("Request:\n");
        logger.appendKeyValue("Id", requests.getId());
        logger.appendKeyValue("URL", url);
        logger.appendKeyValue("Method", connection.getRequestMethod());
        logger.appendKeyValue("User-Agent", connection.getRequestProperty("User-Agent"));
        logger.appendKeyValue("Content-Type", connection.getRequestProperty("Content-Type"));
        connection.setConnectTimeout(requests.getTimeout());
        connection.setReadTimeout(requests.getTimeout());
        if (connectionHttpMethod == HttpMethod.POST) {
            connection.setDoOutput(true);
            OutputStream outputStream = null;
            try {
                OutputStream outputStream2 = new BufferedOutputStream(connection.getOutputStream());
                if (shouldUseGzip) {
                    try {
                        outputStream2 = new GZIPOutputStream(outputStream2);
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = outputStream2;
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        throw th;
                    }
                }
                if (hasOnProgressCallbacks(requests)) {
                    ProgressNoopOutputStream countingStream = new ProgressNoopOutputStream(requests.getCallbackHandler());
                    processRequest(requests, null, numRequests, url, countingStream, shouldUseGzip);
                    outputStream = new ProgressOutputStream(outputStream2, requests, countingStream.getProgressMap(), (long) countingStream.getMaxProgress());
                } else {
                    outputStream = outputStream2;
                }
                processRequest(requests, logger, numRequests, url, outputStream, shouldUseGzip);
                if (outputStream != null) {
                    outputStream.close();
                }
                logger.log();
                return;
            } catch (Throwable th3) {
                th = th3;
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th;
            }
        }
        logger.log();
    }

    private static void processRequest(GraphRequestBatch requests, Logger logger, int numRequests, URL url, OutputStream outputStream, boolean shouldUseGzip) throws IOException, JSONException {
        Serializer serializer = new Serializer(outputStream, logger, shouldUseGzip);
        Map<String, Attachment> attachments;
        if (numRequests == 1) {
            GraphRequest request = requests.get(0);
            attachments = new HashMap();
            for (String key : request.parameters.keySet()) {
                Object value = request.parameters.get(key);
                if (isSupportedAttachmentType(value)) {
                    attachments.put(key, new Attachment(request, value));
                }
            }
            if (logger != null) {
                logger.append("  Parameters:\n");
            }
            serializeParameters(request.parameters, serializer, request);
            if (logger != null) {
                logger.append("  Attachments:\n");
            }
            serializeAttachments(attachments, serializer);
            if (request.graphObject != null) {
                processGraphObject(request.graphObject, url.getPath(), serializer);
                return;
            }
            return;
        }
        String batchAppID = getBatchAppId(requests);
        if (Utility.isNullOrEmpty(batchAppID)) {
            throw new FacebookException("App ID was not specified at the request or Settings.");
        }
        serializer.writeString("batch_app_id", batchAppID);
        attachments = new HashMap();
        serializeRequestsAsJSON(serializer, requests, attachments);
        if (logger != null) {
            logger.append("  Attachments:\n");
        }
        serializeAttachments(attachments, serializer);
    }

    private static boolean isMeRequest(String path) {
        Matcher matcher = versionPattern.matcher(path);
        if (matcher.matches()) {
            path = matcher.group(1);
        }
        if (path.startsWith("me/") || path.startsWith("/me/")) {
            return true;
        }
        return false;
    }

    private static void processGraphObject(JSONObject graphObject, String path, KeyValueSerializer serializer) throws IOException {
        boolean isOGAction = false;
        if (isMeRequest(path)) {
            int colonLocation = path.indexOf(":");
            int questionMarkLocation = path.indexOf("?");
            if (colonLocation <= 3 || (questionMarkLocation != -1 && colonLocation >= questionMarkLocation)) {
                isOGAction = false;
            } else {
                isOGAction = true;
            }
        }
        Iterator<String> keyIterator = graphObject.keys();
        while (keyIterator.hasNext()) {
            boolean passByValue;
            String key = (String) keyIterator.next();
            Object value = graphObject.opt(key);
            if (isOGAction && key.equalsIgnoreCase("image")) {
                passByValue = true;
            } else {
                passByValue = false;
            }
            processGraphObjectProperty(key, value, serializer, passByValue);
        }
    }

    private static void processGraphObjectProperty(String key, Object value, KeyValueSerializer serializer, boolean passByValue) throws IOException {
        Class<?> valueClass = value.getClass();
        if (JSONObject.class.isAssignableFrom(valueClass)) {
            JSONObject jsonObject = (JSONObject) value;
            if (passByValue) {
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    Object[] objArr = new Object[]{key, (String) keys.next()};
                    processGraphObjectProperty(String.format("%s[%s]", objArr), jsonObject.opt((String) keys.next()), serializer, passByValue);
                }
            } else if (jsonObject.has("id")) {
                processGraphObjectProperty(key, jsonObject.optString("id"), serializer, passByValue);
            } else if (jsonObject.has("url")) {
                processGraphObjectProperty(key, jsonObject.optString("url"), serializer, passByValue);
            } else if (jsonObject.has("fbsdk:create_object")) {
                processGraphObjectProperty(key, jsonObject.toString(), serializer, passByValue);
            }
        } else if (JSONArray.class.isAssignableFrom(valueClass)) {
            JSONArray jsonArray = (JSONArray) value;
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                processGraphObjectProperty(String.format(Locale.ROOT, "%s[%d]", new Object[]{key, Integer.valueOf(i)}), jsonArray.opt(i), serializer, passByValue);
            }
        } else if (String.class.isAssignableFrom(valueClass) || Number.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)) {
            serializer.writeString(key, value.toString());
        } else if (Date.class.isAssignableFrom(valueClass)) {
            KeyValueSerializer keyValueSerializer = serializer;
            String str = key;
            keyValueSerializer.writeString(str, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format((Date) value));
        }
    }

    private static void serializeParameters(Bundle bundle, Serializer serializer, GraphRequest request) throws IOException {
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (isSupportedParameterType(value)) {
                serializer.writeObject(key, value, request);
            }
        }
    }

    private static void serializeAttachments(Map<String, Attachment> attachments, Serializer serializer) throws IOException {
        for (String key : attachments.keySet()) {
            Attachment attachment = (Attachment) attachments.get(key);
            if (isSupportedAttachmentType(attachment.getValue())) {
                serializer.writeObject(key, attachment.getValue(), attachment.getRequest());
            }
        }
    }

    private static void serializeRequestsAsJSON(Serializer serializer, Collection<GraphRequest> requests, Map<String, Attachment> attachments) throws JSONException, IOException {
        JSONArray batch = new JSONArray();
        for (GraphRequest request : requests) {
            request.serializeToBatch(batch, attachments);
        }
        serializer.writeRequestsAsJson("batch", batch, requests);
    }

    private static String getMimeContentType() {
        return String.format("multipart/form-data; boundary=%s", new Object[]{"3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f"});
    }

    private static String getUserAgent() {
        if (userAgent == null) {
            userAgent = String.format("%s.%s", new Object[]{"FBAndroidSDK", "4.5.0"});
            if (!Utility.isNullOrEmpty(InternalSettings.getCustomUserAgent())) {
                userAgent = String.format(Locale.ROOT, "%s/%s", new Object[]{userAgent, InternalSettings.getCustomUserAgent()});
            }
        }
        return userAgent;
    }

    private static String getBatchAppId(GraphRequestBatch batch) {
        if (!Utility.isNullOrEmpty(batch.getBatchApplicationId())) {
            return batch.getBatchApplicationId();
        }
        Iterator it = batch.iterator();
        while (it.hasNext()) {
            AccessToken accessToken = ((GraphRequest) it.next()).accessToken;
            if (accessToken != null) {
                String applicationId = accessToken.getApplicationId();
                if (applicationId != null) {
                    return applicationId;
                }
            }
        }
        if (Utility.isNullOrEmpty(defaultBatchApplicationId)) {
            return FacebookSdk.getApplicationId();
        }
        return defaultBatchApplicationId;
    }

    private static boolean isSupportedAttachmentType(Object value) {
        return (value instanceof Bitmap) || (value instanceof byte[]) || (value instanceof Uri) || (value instanceof ParcelFileDescriptor) || (value instanceof ParcelableResourceWithMimeType);
    }

    private static boolean isSupportedParameterType(Object value) {
        return (value instanceof String) || (value instanceof Boolean) || (value instanceof Number) || (value instanceof Date);
    }

    private static String parameterToString(Object value) {
        if (value instanceof String) {
            return (String) value;
        }
        if ((value instanceof Boolean) || (value instanceof Number)) {
            return value.toString();
        }
        if (value instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format(value);
        }
        throw new IllegalArgumentException("Unsupported parameter type.");
    }
}
