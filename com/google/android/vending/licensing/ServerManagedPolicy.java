package com.google.android.vending.licensing;

import android.content.Context;
import android.util.Log;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class ServerManagedPolicy implements Policy {
    private int mLastResponse;
    private long mLastResponseTime;
    private long mMaxRetries;
    private PreferenceObfuscator mPreferences;
    private long mRetryCount;
    private long mRetryUntil;
    private long mValidityTimestamp;

    public ServerManagedPolicy(Context context, Obfuscator obfuscator) {
        this.mLastResponseTime = 0;
        this.mPreferences = new PreferenceObfuscator(context.getSharedPreferences("com.android.vending.licensing.ServerManagedPolicy", 0), obfuscator);
        this.mLastResponse = Integer.parseInt(this.mPreferences.getString("lastResponse", Integer.toString(291)));
        this.mValidityTimestamp = Long.parseLong(this.mPreferences.getString("validityTimestamp", "0"));
        this.mRetryUntil = Long.parseLong(this.mPreferences.getString("retryUntil", "0"));
        this.mMaxRetries = Long.parseLong(this.mPreferences.getString("maxRetries", "0"));
        this.mRetryCount = Long.parseLong(this.mPreferences.getString("retryCount", "0"));
    }

    public void processServerResponse(int response, ResponseData rawData) {
        if (response != 291) {
            setRetryCount(0);
        } else {
            setRetryCount(this.mRetryCount + 1);
        }
        if (response == 256) {
            Map<String, String> extras = decodeExtras(rawData.extra);
            this.mLastResponse = response;
            setValidityTimestamp((String) extras.get("VT"));
            setRetryUntil((String) extras.get("GT"));
            setMaxRetries((String) extras.get("GR"));
        } else if (response == 561) {
            setValidityTimestamp("0");
            setRetryUntil("0");
            setMaxRetries("0");
        }
        setLastResponse(response);
        this.mPreferences.commit();
    }

    private void setLastResponse(int l) {
        this.mLastResponseTime = System.currentTimeMillis();
        this.mLastResponse = l;
        this.mPreferences.putString("lastResponse", Integer.toString(l));
    }

    private void setRetryCount(long c) {
        this.mRetryCount = c;
        this.mPreferences.putString("retryCount", Long.toString(c));
    }

    private void setValidityTimestamp(String validityTimestamp) {
        Long lValidityTimestamp;
        try {
            lValidityTimestamp = Long.valueOf(Long.parseLong(validityTimestamp));
        } catch (NumberFormatException e) {
            Log.w("ServerManagedPolicy", "License validity timestamp (VT) missing, caching for a minute");
            lValidityTimestamp = Long.valueOf(System.currentTimeMillis() + 60000);
            validityTimestamp = Long.toString(lValidityTimestamp.longValue());
        }
        this.mValidityTimestamp = lValidityTimestamp.longValue();
        this.mPreferences.putString("validityTimestamp", validityTimestamp);
    }

    private void setRetryUntil(String retryUntil) {
        Long lRetryUntil;
        try {
            lRetryUntil = Long.valueOf(Long.parseLong(retryUntil));
        } catch (NumberFormatException e) {
            Log.w("ServerManagedPolicy", "License retry timestamp (GT) missing, grace period disabled");
            retryUntil = "0";
            lRetryUntil = Long.valueOf(0);
        }
        this.mRetryUntil = lRetryUntil.longValue();
        this.mPreferences.putString("retryUntil", retryUntil);
    }

    private void setMaxRetries(String maxRetries) {
        Long lMaxRetries;
        try {
            lMaxRetries = Long.valueOf(Long.parseLong(maxRetries));
        } catch (NumberFormatException e) {
            Log.w("ServerManagedPolicy", "Licence retry count (GR) missing, grace period disabled");
            maxRetries = "0";
            lMaxRetries = Long.valueOf(0);
        }
        this.mMaxRetries = lMaxRetries.longValue();
        this.mPreferences.putString("maxRetries", maxRetries);
    }

    public boolean allowAccess() {
        long ts = System.currentTimeMillis();
        if (this.mLastResponse == 256) {
            if (ts <= this.mValidityTimestamp) {
                return true;
            }
            return false;
        } else if (this.mLastResponse != 291 || ts >= this.mLastResponseTime + 60000) {
            return false;
        } else {
            if (ts <= this.mRetryUntil || this.mRetryCount <= this.mMaxRetries) {
                return true;
            }
            return false;
        }
    }

    private Map<String, String> decodeExtras(String extras) {
        Map<String, String> results = new HashMap();
        try {
            for (NameValuePair item : URLEncodedUtils.parse(new URI("?" + extras), "UTF-8")) {
                results.put(item.getName(), item.getValue());
            }
        } catch (URISyntaxException e) {
            Log.w("ServerManagedPolicy", "Invalid syntax error while decoding extras data from server.");
        }
        return results;
    }
}
