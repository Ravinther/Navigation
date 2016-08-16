package com.google.android.vending.licensing;

import android.content.Context;
import android.util.Log;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class APKExpansionPolicy implements Policy {
    private Vector<String> mExpansionFileNames;
    private Vector<Long> mExpansionFileSizes;
    private Vector<String> mExpansionURLs;
    private int mLastResponse;
    private long mLastResponseTime;
    private long mMaxRetries;
    private PreferenceObfuscator mPreferences;
    private long mRetryCount;
    private long mRetryUntil;
    private long mValidityTimestamp;

    public APKExpansionPolicy(Context context, Obfuscator obfuscator) {
        this.mLastResponseTime = 0;
        this.mExpansionURLs = new Vector();
        this.mExpansionFileNames = new Vector();
        this.mExpansionFileSizes = new Vector();
        this.mPreferences = new PreferenceObfuscator(context.getSharedPreferences("com.android.vending.licensing.APKExpansionPolicy", 0), obfuscator);
        this.mLastResponse = Integer.parseInt(this.mPreferences.getString("lastResponse", Integer.toString(291)));
        this.mValidityTimestamp = Long.parseLong(this.mPreferences.getString("validityTimestamp", "0"));
        this.mRetryUntil = Long.parseLong(this.mPreferences.getString("retryUntil", "0"));
        this.mMaxRetries = Long.parseLong(this.mPreferences.getString("maxRetries", "0"));
        this.mRetryCount = Long.parseLong(this.mPreferences.getString("retryCount", "0"));
    }

    public void resetPolicy() {
        this.mPreferences.putString("lastResponse", Integer.toString(291));
        setRetryUntil("0");
        setMaxRetries("0");
        setRetryCount(Long.parseLong("0"));
        setValidityTimestamp("0");
        this.mPreferences.commit();
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
            setValidityTimestamp(Long.toString(System.currentTimeMillis() + 60000));
            for (String key : extras.keySet()) {
                if (key.equals("VT")) {
                    setValidityTimestamp((String) extras.get(key));
                } else if (key.equals("GT")) {
                    setRetryUntil((String) extras.get(key));
                } else if (key.equals("GR")) {
                    setMaxRetries((String) extras.get(key));
                } else if (key.startsWith("FILE_URL")) {
                    setExpansionURL(Integer.parseInt(key.substring("FILE_URL".length())) - 1, (String) extras.get(key));
                } else if (key.startsWith("FILE_NAME")) {
                    setExpansionFileName(Integer.parseInt(key.substring("FILE_NAME".length())) - 1, (String) extras.get(key));
                } else if (key.startsWith("FILE_SIZE")) {
                    setExpansionFileSize(Integer.parseInt(key.substring("FILE_SIZE".length())) - 1, Long.parseLong((String) extras.get(key)));
                }
            }
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
            Log.w("APKExpansionPolicy", "License validity timestamp (VT) missing, caching for a minute");
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
            Log.w("APKExpansionPolicy", "License retry timestamp (GT) missing, grace period disabled");
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
            Log.w("APKExpansionPolicy", "Licence retry count (GR) missing, grace period disabled");
            maxRetries = "0";
            lMaxRetries = Long.valueOf(0);
        }
        this.mMaxRetries = lMaxRetries.longValue();
        this.mPreferences.putString("maxRetries", maxRetries);
    }

    public int getExpansionURLCount() {
        return this.mExpansionURLs.size();
    }

    public String getExpansionURL(int index) {
        if (index < this.mExpansionURLs.size()) {
            return (String) this.mExpansionURLs.elementAt(index);
        }
        return null;
    }

    public void setExpansionURL(int index, String URL) {
        if (index >= this.mExpansionURLs.size()) {
            this.mExpansionURLs.setSize(index + 1);
        }
        this.mExpansionURLs.set(index, URL);
    }

    public String getExpansionFileName(int index) {
        if (index < this.mExpansionFileNames.size()) {
            return (String) this.mExpansionFileNames.elementAt(index);
        }
        return null;
    }

    public void setExpansionFileName(int index, String name) {
        if (index >= this.mExpansionFileNames.size()) {
            this.mExpansionFileNames.setSize(index + 1);
        }
        this.mExpansionFileNames.set(index, name);
    }

    public long getExpansionFileSize(int index) {
        if (index < this.mExpansionFileSizes.size()) {
            return ((Long) this.mExpansionFileSizes.elementAt(index)).longValue();
        }
        return -1;
    }

    public void setExpansionFileSize(int index, long size) {
        if (index >= this.mExpansionFileSizes.size()) {
            this.mExpansionFileSizes.setSize(index + 1);
        }
        this.mExpansionFileSizes.set(index, Long.valueOf(size));
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
                String name = item.getName();
                int i = 0;
                while (results.containsKey(name)) {
                    i++;
                    name = item.getName() + i;
                }
                results.put(name, item.getValue());
            }
        } catch (URISyntaxException e) {
            Log.w("APKExpansionPolicy", "Invalid syntax error while decoding extras data from server.");
        }
        return results;
    }
}
