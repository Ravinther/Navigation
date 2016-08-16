package com.facebook.share.model;

import android.os.Parcel;
import java.util.ArrayList;

public final class GameRequestContent implements ShareModel {
    private final ActionType actionType;
    private final String data;
    private final Filters filters;
    private final String message;
    private final String objectId;
    private final ArrayList<String> suggestions;
    private final String title;
    private final String to;

    public enum ActionType {
        SEND,
        ASKFOR,
        TURN
    }

    public enum Filters {
        APP_USERS,
        APP_NON_USERS
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public Filters getFilters() {
        return this.filters;
    }

    public ArrayList<String> getSuggestions() {
        return this.suggestions;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.message);
        out.writeString(this.to);
        out.writeString(this.title);
        out.writeString(this.data);
        out.writeString(getActionType().toString());
        out.writeString(getObjectId());
        out.writeString(getFilters().toString());
        out.writeStringList(getSuggestions());
    }
}
