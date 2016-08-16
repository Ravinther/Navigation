package com.infinario.android.infinariosdk;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommandManager {
    boolean flushInProgress;
    boolean flushMayNeedRestart;
    AsyncTask<Void, Void, Void> flushTask;
    HttpHelper http;
    Object lockFlush;
    Preferences preferences;
    DbQueue queue;

    /* renamed from: com.infinario.android.infinariosdk.CommandManager.1 */
    class C10461 extends AsyncTask<Void, Void, Void> {
        final /* synthetic */ int val$count;

        C10461(int i) {
            this.val$count = i;
        }

        protected Void doInBackground(Void... params) {
            CommandManager.this.flushCommands(this.val$count);
            return null;
        }
    }

    public CommandManager(Context context, String target, String userAgent) {
        this.queue = new DbQueue(context);
        this.preferences = Preferences.get(context);
        this.http = new HttpHelper(target, userAgent);
        this.lockFlush = new Object();
        synchronized (this.lockFlush) {
            this.flushInProgress = false;
            this.flushMayNeedRestart = false;
        }
    }

    public boolean schedule(Command command) {
        return this.queue.schedule(command);
    }

    public boolean executeBatch() {
        Set<Integer> deleteRequests = new HashSet();
        Set<Integer> successfulRequests = new HashSet();
        JSONArray commands = new JSONArray();
        JSONObject payload = new JSONObject();
        List<Request> requests = this.queue.pop();
        if (requests.isEmpty()) {
            return true;
        }
        Log.i("Infinario", "sending ids " + ((Request) requests.get(0)).getId() + " - " + ((Request) requests.get(requests.size() - 1)).getId());
        for (Request r : requests) {
            commands.put(setCookieId(setAge(r.getCommand())));
        }
        try {
            payload.put("commands", commands);
        } catch (JSONException e) {
            Log.e("Infinario", e.getMessage().toString());
        }
        JSONObject data = this.http.post("/bulk", payload);
        StringBuilder logResult = new StringBuilder();
        logResult.append("Batch executed, ").append(requests.size()).append(" prepared, ");
        if (data != null) {
            JSONArray results;
            try {
                results = data.getJSONArray("results");
            } catch (JSONException e2) {
                results = null;
                Log.e("Infinario", e2.getMessage().toString());
            }
            if (results != null) {
                int i = 0;
                while (i < requests.size() && i < results.length()) {
                    try {
                        Request request = (Request) requests.get(i);
                        String status = results.getJSONObject(i).getString("status").toLowerCase();
                        if (status.equals("ok")) {
                            deleteRequests.add(Integer.valueOf(request.getId()));
                            successfulRequests.add(Integer.valueOf(request.getId()));
                        } else {
                            if (status.equals("error")) {
                                deleteRequests.add(Integer.valueOf(request.getId()));
                            }
                        }
                    } catch (JSONException e22) {
                        Log.e("Infinario", e22.getMessage().toString());
                    }
                    i++;
                }
                logResult.append(successfulRequests.size()).append(" succeeded, ").append(deleteRequests.size() - successfulRequests.size());
            } else {
                Log.e("Infinario", "Results are null");
                logResult.append("0 succeeded, ").append(requests.size());
            }
        } else {
            Log.e("Infinario", "Data is null");
            logResult.append("0 succeeded, ").append(requests.size());
        }
        logResult.append(" failed, rest was told to retry");
        Log.i("Infinario", logResult.toString());
        this.queue.clear(deleteRequests);
        if (requests.size() == deleteRequests.size()) {
            return true;
        }
        return false;
    }

    public boolean flush(int count) {
        synchronized (this.lockFlush) {
            this.flushMayNeedRestart = true;
            if (this.flushInProgress) {
                return false;
            }
            this.flushInProgress = true;
            this.flushTask = new C10461(count).execute(new Void[0]);
            return true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void flushCommands(int r7) {
        /*
        r6 = this;
    L_0x0000:
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = 0;
        r4 = r6.lockFlush;
        monitor-enter(r4);
        r3 = r6.flushMayNeedRestart;	 Catch:{ all -> 0x0031 }
        if (r3 != 0) goto L_0x000f;
    L_0x000a:
        r3 = 0;
        r6.flushInProgress = r3;	 Catch:{ all -> 0x0031 }
        monitor-exit(r4);	 Catch:{ all -> 0x0031 }
        return;
    L_0x000f:
        r3 = 0;
        r6.flushMayNeedRestart = r3;	 Catch:{ all -> 0x0031 }
        monitor-exit(r4);	 Catch:{ all -> 0x0031 }
    L_0x0013:
        r3 = r6.queue;	 Catch:{ Exception -> 0x0034 }
        r3 = r3.isEmpty();	 Catch:{ Exception -> 0x0034 }
        if (r3 != 0) goto L_0x0000;
    L_0x001b:
        if (r2 > r7) goto L_0x0000;
    L_0x001d:
        r3 = r6.executeBatch();	 Catch:{ Exception -> 0x0034 }
        if (r3 != 0) goto L_0x0013;
    L_0x0023:
        r3 = 1;
        if (r7 <= r3) goto L_0x002e;
    L_0x0026:
        r4 = (long) r0;	 Catch:{ Exception -> 0x0034 }
        java.lang.Thread.sleep(r4);	 Catch:{ Exception -> 0x0034 }
        r0 = r6.exponentialIncrease(r0);	 Catch:{ Exception -> 0x0034 }
    L_0x002e:
        r2 = r2 + 1;
        goto L_0x0013;
    L_0x0031:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0031 }
        throw r3;
    L_0x0034:
        r1 = move-exception;
        r3 = "Infinario";
        r4 = r1.getMessage();
        r4 = r4.toString();
        android.util.Log.e(r3, r4);
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.infinario.android.infinariosdk.CommandManager.flushCommands(int):void");
    }

    private int exponentialIncrease(int timeInMiliseconds) {
        int calculateDelay = timeInMiliseconds * 2;
        return Contract.FLUSH_MAX_INTERVAL < calculateDelay ? Contract.FLUSH_MAX_INTERVAL : calculateDelay;
    }

    private JSONObject setCookieId(JSONObject command) {
        try {
            JSONObject data = command.getJSONObject(PoiFragment.ARG_DATA);
            if (data.has("ids") && data.getJSONObject("ids").getString("cookie").isEmpty()) {
                data.getJSONObject("ids").put("cookie", this.preferences.getCookieId());
            }
            if (data.has("customer_ids") && data.getJSONObject("customer_ids").getString("cookie").isEmpty()) {
                data.getJSONObject("customer_ids").put("cookie", this.preferences.getCookieId());
            }
        } catch (JSONException e) {
        }
        return command;
    }

    private JSONObject setAge(JSONObject command) {
        try {
            command.getJSONObject(PoiFragment.ARG_DATA).put("age", (new Date().getTime() - command.getJSONObject(PoiFragment.ARG_DATA).getLong("age")) / 1000);
        } catch (JSONException e) {
        }
        return command;
    }
}
