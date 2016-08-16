package loquendo.tts.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class TTSSession extends TTSObject {
    private String sessionName;
    private Long sessionReference;

    public TTSSession() throws TTSException {
        this(null);
    }

    public TTSSession(String str) throws TTSException {
        this.sessionName = null;
        this.sessionReference = null;
        this.sessionReference = new Long(_newSession(str));
        if (this.sessionReference == null) {
            TTSException tTSException = new TTSException("Error in TTSSession object creation");
        }
        setParam("IgnoreThreadBoundaryCrossing", "yes");
        if (str == null) {
            this.sessionName = "default";
        } else {
            this.sessionName = str;
        }
    }

    public TTSSession(TTSSession tTSSession, String str) throws TTSException {
        this.sessionName = null;
        this.sessionReference = null;
        this.sessionReference = tTSSession.sessionReference;
        try {
            this.sessionReference = new Long(_clone(this.sessionReference.longValue(), str));
            this.sessionName = str;
        } catch (TTSException e) {
            throw new TTSException(e.getMessage());
        }
    }

    private static native Vector _call(long j, String str, Object[] objArr) throws TTSException;

    private native long _clone(long j, String str) throws TTSException;

    private native void _delete(long j) throws TTSException;

    private native void _deleteCF(long j, String str, String str2);

    private native String _getParam(long j, String str) throws TTSException;

    private native Vector _getRobotsList(long j);

    private native HashMap _getSAMPAPhonemeMap() throws TTSException;

    private native long _newSession(String str) throws TTSException;

    private native Vector _query(long j, String str, String str2, int i, boolean z, boolean z2, String str3);

    private native Vector _query(long j, String str, String str2, int i, boolean z, boolean z2, boolean z3);

    private native Vector _queryAttribute(long j, String str, String str2, int i, String str3);

    private native Vector _queryAttribute(long j, String str, String str2, int i, boolean z);

    private native void _setParam(long j, String str, String str2) throws TTSException;

    private native boolean _testPersona(long j, String str, String str2, String str3);

    public Vector call(String str, Object[] objArr) throws TTSException {
        return _call(this.sessionReference.longValue(), str, objArr);
    }

    public void delete() throws TTSException {
        _delete(this.sessionReference.longValue());
        this.sessionReference = null;
    }

    public void deleteCF(String str, String str2) throws TTSException {
        if (str.equalsIgnoreCase("session")) {
            _deleteCF(this.sessionReference.longValue(), "session", str2);
        } else if (str.equalsIgnoreCase("voice")) {
            _deleteCF(this.sessionReference.longValue(), "voice", str2);
        } else if (str.equalsIgnoreCase("language")) {
            _deleteCF(this.sessionReference.longValue(), "language", str2);
        } else if (str.equalsIgnoreCase("style")) {
            _deleteCF(this.sessionReference.longValue(), "style", str2);
        }
    }

    public String getName() {
        return null;
    }

    public String getParam(String str) throws TTSException {
        return _getParam(this.sessionReference.longValue(), str);
    }

    public Long getReference() throws TTSException {
        return this.sessionReference;
    }

    public Iterator getRobotsList() throws TTSException {
        Vector _getRobotsList = _getRobotsList(this.sessionReference.longValue());
        return _getRobotsList == null ? null : _getRobotsList.iterator();
    }

    public Map getSAMPAPhonemeMap() throws TTSException {
        return _getSAMPAPhonemeMap();
    }

    public Iterator query(String str, String str2, int i, boolean z, boolean z2, String str3) {
        Vector _query = _query(this.sessionReference.longValue(), str, str2, i, z, z2, str3);
        return _query == null ? null : _query.iterator();
    }

    public Iterator query(String str, String str2, int i, boolean z, boolean z2, boolean z3) {
        Vector _query = _query(this.sessionReference.longValue(), str, str2, i, z, z2, z3);
        return _query == null ? null : _query.iterator();
    }

    public Iterator queryAttribute(String str, String str2, int i, String str3) {
        Vector _queryAttribute = _queryAttribute(this.sessionReference.longValue(), str, str2, i, str3);
        return _queryAttribute == null ? null : _queryAttribute.iterator();
    }

    public Iterator queryAttribute(String str, String str2, int i, boolean z) {
        Vector _queryAttribute = _queryAttribute(this.sessionReference.longValue(), str, str2, i, z);
        return _queryAttribute == null ? null : _queryAttribute.iterator();
    }

    public void setParam(String str, String str2) throws TTSException {
        _setParam(this.sessionReference.longValue(), str, str2);
    }

    public boolean testPersona(String str, String str2, String str3) throws TTSException {
        return _testPersona(this.sessionReference.longValue(), str, str2, str3);
    }
}
