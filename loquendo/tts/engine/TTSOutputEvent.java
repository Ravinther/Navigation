package loquendo.tts.engine;

import java.util.EventObject;

public class TTSOutputEvent extends EventObject {
    private static final long serialVersionUID = -5609840453413909052L;
    private Object _data;
    private String _reason;

    public TTSOutputEvent(TTSReader tTSReader, String str, Object obj) {
        super(tTSReader);
        this._data = null;
        this._reason = null;
        this._data = obj;
        this._reason = str;
    }

    public Object getData() {
        return this._data;
    }

    public Object getReason() {
        return this._data;
    }

    public String getStringData() {
        return this._data.toString();
    }
}
