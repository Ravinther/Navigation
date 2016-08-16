package com.sygic.widget.places.data;

import android.text.TextUtils;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceEntry {
    protected long mPosition;
    protected String mStrName;
    protected EMemoType mType;
    protected int mX;
    protected int mY;

    public PlaceEntry(String strName, long position, int type) {
        this(strName, position, EMemoType.createFromInt(type));
    }

    public PlaceEntry(String strName, long position, EMemoType type) {
        this.mStrName = strName;
        this.mPosition = position;
        this.mX = (int) (position >> 32);
        this.mY = (int) position;
        this.mType = type;
    }

    public PlaceEntry(PlaceEntry entry) {
        this(entry.mStrName, entry.mPosition, entry.mType);
    }

    public String getName() {
        return this.mStrName;
    }

    public float getLat() {
        return ((float) this.mY) / 100000.0f;
    }

    public float getLon() {
        return ((float) this.mX) / 100000.0f;
    }

    public EMemoType getType() {
        return this.mType;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public void setName(String name) {
        this.mStrName = name;
    }

    public void setPosition(long position) {
        this.mPosition = position;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceEntry)) {
            return false;
        }
        PlaceEntry item = (PlaceEntry) obj;
        if (TextUtils.equals(item.mStrName, this.mStrName) && item.mPosition == this.mPosition && item.mType == this.mType) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.mStrName.hashCode() + ((int) (this.mPosition ^ (this.mPosition >>> 32)))) + (this.mType.getValue() * 31);
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", this.mStrName);
            jsonObject.put("posX", this.mX);
            jsonObject.put("posY", this.mY);
            jsonObject.put("type", this.mType.getValue());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static PlaceEntry fromJSON(String strJSON) {
        try {
            JSONObject jsonObject = new JSONObject(strJSON);
            return new PlaceEntry(jsonObject.getString("name"), (((long) jsonObject.getInt("posY")) & 4294967295L) | (((long) jsonObject.getInt("posX")) << 32), EMemoType.createFromInt(jsonObject.optInt("type", -1)));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
