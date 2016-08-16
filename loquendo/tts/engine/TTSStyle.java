package loquendo.tts.engine;

public class TTSStyle extends TTSObject {
    protected Long sessionReference;
    protected String styleName;
    protected Long styleReference;

    public TTSStyle(String str, String str2) throws TTSException {
        this(new TTSSession(str), str2);
    }

    public TTSStyle(TTSSession tTSSession, String str) throws TTSException {
        this.styleReference = null;
        this.sessionReference = null;
        this.styleName = null;
        if (tTSSession == null || tTSSession.getReference() == null) {
            this.sessionReference = new Long(0);
        } else {
            this.sessionReference = tTSSession.getReference();
        }
        try {
            this.styleReference = new Long(_newStyle(this.sessionReference.longValue(), str));
            this.styleName = str;
        } catch (TTSException e) {
            throw new TTSException(e.getMessage());
        }
    }

    public TTSStyle(TTSStyle tTSStyle, String str) throws TTSException {
        this.styleReference = null;
        this.sessionReference = null;
        this.styleName = null;
        this.sessionReference = tTSStyle.sessionReference;
        try {
            this.styleReference = new Long(_clone(this.sessionReference.longValue(), str));
            this.styleName = str;
        } catch (TTSException e) {
            throw new TTSException(e.getMessage());
        }
    }

    private native long _clone(long j, String str) throws TTSException;

    private native void _delete(long j) throws TTSException;

    private native String _getParam(long j, String str) throws TTSException;

    private native long _newStyle(long j, String str) throws TTSException;

    private native void _saveCF(long j, String str) throws TTSException;

    private native void _setParam(long j, String str, String str2) throws TTSException;

    public void delete() throws TTSException {
        _delete(this.styleReference.longValue());
        this.styleReference = null;
    }

    public String getName() {
        return this.styleName;
    }

    public String getParam(String str) throws TTSException {
        return _getParam(this.styleReference.longValue(), str);
    }

    public Long getReference() throws TTSException {
        return this.styleReference;
    }

    public void setParam(String str, String str2) throws TTSException {
        _setParam(this.styleReference.longValue(), str, str2);
    }
}
