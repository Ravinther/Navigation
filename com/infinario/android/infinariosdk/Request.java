package com.infinario.android.infinariosdk;

import org.json.JSONException;
import org.json.JSONObject;

public class Request {
    private JSONObject command;
    private int id;
    private int retries;

    public Request(int id, String command, int retries) throws JSONException {
        this.command = new JSONObject(command);
        this.id = id;
        this.retries = retries;
    }

    public JSONObject getCommand() {
        return this.command;
    }

    public int getId() {
        return this.id;
    }
}
