package com.infinario.android.infinariosdk;

import com.sygic.aura.poi.detail.PoiDetailFragment;
import java.util.HashMap;
import java.util.Map;

public class HttpHelper {
    private Map<String, Object> header;
    private String target;
    private int timeout;
    private String userAgent;

    public HttpHelper(String target, String userAgent) {
        if (target == null) {
            this.target = "https://api.infinario.com";
        } else {
            this.target = target;
        }
        this.userAgent = userAgent;
        this.timeout = PoiDetailFragment.MIN_STREET_VIEW_BYTES;
        this.header = new HashMap();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.json.JSONObject post(java.lang.String r16, org.json.JSONObject r17) {
        /*
        r15 = this;
        r2 = 0;
        r10 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12.<init>();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r13 = r15.target;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r0 = r16;
        r12 = r12.append(r0);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r12.toString();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r10.<init>(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r10.openConnection();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r0 = r12;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r2 = r0;
        r12 = 1;
        r2.setDoOutput(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = 1;
        r2.setDoInput(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r15.timeout;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r2.setConnectTimeout(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r15.timeout;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r2.setReadTimeout(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = "Content-Type";
        r13 = "application/json";
        r2.setRequestProperty(r12, r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = "Accept";
        r13 = "application/json";
        r2.setRequestProperty(r12, r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r15.userAgent;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        if (r12 == 0) goto L_0x0053;
    L_0x004b:
        r12 = "User-Agent";
        r13 = r15.userAgent;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r2.setRequestProperty(r12, r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
    L_0x0053:
        r12 = r15.header;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r12.size();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        if (r12 <= 0) goto L_0x0095;
    L_0x005b:
        r12 = r15.header;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r12.entrySet();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r13 = r12.iterator();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
    L_0x0065:
        r12 = r13.hasNext();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        if (r12 == 0) goto L_0x0095;
    L_0x006b:
        r4 = r13.next();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r4 = (java.util.Map.Entry) r4;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r4.getKey();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = (java.lang.String) r12;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r14 = r4.getValue();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r14 = r14.toString();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r2.setRequestProperty(r12, r14);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        goto L_0x0065;
    L_0x0083:
        r3 = move-exception;
        r12 = "Infinario";
        r13 = r3.toString();	 Catch:{ all -> 0x012e }
        android.util.Log.e(r12, r13);	 Catch:{ all -> 0x012e }
        if (r2 == 0) goto L_0x0093;
    L_0x0090:
        r2.disconnect();
    L_0x0093:
        r12 = 0;
    L_0x0094:
        return r12;
    L_0x0095:
        r12 = "POST";
        r2.setRequestMethod(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r2.connect();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r7 = r2.getOutputStream();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r11 = new java.io.BufferedWriter;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = new java.io.OutputStreamWriter;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r13 = "UTF-8";
        r12.<init>(r7, r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r11.<init>(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = r17.toString();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r11.write(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r11.close();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r7.close();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r5 = 0;
        r5 = r2.getInputStream();	 Catch:{ IOException -> 0x00f7, MalformedURLException -> 0x0083, JSONException -> 0x011c }
    L_0x00c1:
        if (r5 == 0) goto L_0x010c;
    L_0x00c3:
        r9 = new java.io.BufferedReader;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r13 = "UTF-8";
        r12.<init>(r5, r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r9.<init>(r12);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r8 = new java.io.StringWriter;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r8.<init>();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r1 = new char[r12];	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r6 = 0;
    L_0x00da:
        r12 = -1;
        r6 = r9.read(r1);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        if (r12 == r6) goto L_0x00fd;
    L_0x00e1:
        r12 = 0;
        r8.write(r1, r12, r6);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        goto L_0x00da;
    L_0x00e6:
        r3 = move-exception;
        r12 = "Infinario";
        r13 = r3.toString();	 Catch:{ all -> 0x012e }
        android.util.Log.e(r12, r13);	 Catch:{ all -> 0x012e }
        if (r2 == 0) goto L_0x0093;
    L_0x00f3:
        r2.disconnect();
        goto L_0x0093;
    L_0x00f7:
        r3 = move-exception;
        r5 = r2.getErrorStream();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        goto L_0x00c1;
    L_0x00fd:
        r12 = new org.json.JSONObject;	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r13 = r8.toString();	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        r12.<init>(r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        if (r2 == 0) goto L_0x0094;
    L_0x0108:
        r2.disconnect();
        goto L_0x0094;
    L_0x010c:
        r12 = "Infinario";
        r13 = "Response is null";
        android.util.Log.e(r12, r13);	 Catch:{ MalformedURLException -> 0x0083, IOException -> 0x00e6, JSONException -> 0x011c }
        if (r2 == 0) goto L_0x0093;
    L_0x0117:
        r2.disconnect();
        goto L_0x0093;
    L_0x011c:
        r3 = move-exception;
        r12 = "Infinario";
        r13 = r3.toString();	 Catch:{ all -> 0x012e }
        android.util.Log.e(r12, r13);	 Catch:{ all -> 0x012e }
        if (r2 == 0) goto L_0x0093;
    L_0x0129:
        r2.disconnect();
        goto L_0x0093;
    L_0x012e:
        r12 = move-exception;
        if (r2 == 0) goto L_0x0134;
    L_0x0131:
        r2.disconnect();
    L_0x0134:
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.infinario.android.infinariosdk.HttpHelper.post(java.lang.String, org.json.JSONObject):org.json.JSONObject");
    }
}
