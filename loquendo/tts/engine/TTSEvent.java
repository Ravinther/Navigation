package loquendo.tts.engine;

import java.util.EventObject;

public class TTSEvent extends EventObject {
    private static final long serialVersionUID = 3455572305522851220L;
    private Object _data;
    private Integer _reason;

    public TTSEvent(TTSReader tTSReader, int i, Object obj) {
        super(tTSReader);
        this._reason = null;
        this._data = null;
        this._reason = new Integer(i);
        this._data = obj;
    }

    public Object getData() {
        return this._data;
    }

    public Integer getReason() {
        return this._reason;
    }

    public String getStringData() {
        return this._data.toString();
    }
}
