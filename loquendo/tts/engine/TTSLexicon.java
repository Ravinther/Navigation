package loquendo.tts.engine;

import java.util.Iterator;
import java.util.Vector;

public class TTSLexicon extends TTSObject {
    private Long lexiconReference;
    protected boolean newAPIVersion;

    public TTSLexicon(TTSReader tTSReader, int i, boolean z, String str) throws TTSException {
        this.newAPIVersion = false;
        this.lexiconReference = null;
        if (tTSReader == null || tTSReader.getReference() == null) {
            throw new TTSException("Invalid TTSReader.");
        }
        this.lexiconReference = new Long(_new(tTSReader.getReference().longValue(), i, z, str == null ? "" : str, true));
    }

    public TTSLexicon(TTSReader tTSReader, int i, boolean z, String str, boolean z2) throws TTSException {
        this.newAPIVersion = false;
        this.lexiconReference = null;
        if (tTSReader == null || tTSReader.getReference() == null) {
            throw new TTSException("Invalid TTSReader.");
        }
        this.lexiconReference = new Long(_new(tTSReader.getReference().longValue(), i, z, str == null ? "" : str, z2));
    }

    public TTSLexicon(TTSSession tTSSession, String str) throws TTSException {
        this.newAPIVersion = false;
        this.lexiconReference = null;
        if (tTSSession == null || tTSSession.getReference() == null) {
            throw new TTSException("Invalid TTSSession");
        }
        this.lexiconReference = new Long(_newLexicon(tTSSession.getReference().longValue(), str));
        this.newAPIVersion = true;
    }

    private native void _activate(long j, boolean z);

    private native void _addEntry(long j, String str, String str2, String str3) throws TTSException;

    private native void _close(long j) throws TTSException;

    private native void _delete(long j) throws TTSException;

    private native Vector _enumLexiconEntries(long j, String str) throws TTSException;

    private native void _exportfile(long j, String str, String str2) throws TTSException;

    private native String _findEntry(long j, String str, String str2) throws TTSException;

    private native void _importfile(long j, String str);

    private native long _new(long j, int i, boolean z, String str, boolean z2) throws TTSException;

    private native long _newLexicon(long j, String str) throws TTSException;

    private native void _removeEntry(long j, String str, String str2) throws TTSException;

    public void activate(boolean z) throws TTSException {
        if (this.newAPIVersion) {
            throw new TTSException("Error: the object is incopatible with this deprecated method");
        }
        _activate(this.lexiconReference.longValue(), z);
    }

    public void addEntry(String str, String str2, String str3) throws TTSException {
        _addEntry(this.lexiconReference.longValue(), str, str2, str3);
    }

    public void delete() throws TTSException {
        if (this.newAPIVersion) {
            _delete(this.lexiconReference.longValue());
        } else {
            _close(this.lexiconReference.longValue());
        }
        this.lexiconReference = null;
    }

    public Iterator enumLexiconEntries(String str) throws TTSException {
        if (this.newAPIVersion) {
            return _enumLexiconEntries(this.lexiconReference.longValue(), str).iterator();
        }
        throw new TTSException("Error: the object is incopatible with this method; please use a not deprecated constructor");
    }

    public void exportfile(String str, String str2) throws TTSException {
        _exportfile(this.lexiconReference.longValue(), str, str2);
    }

    protected void finalize() throws Throwable {
        if (this.lexiconReference != null) {
            delete();
        }
        super.finalize();
    }

    public String findEntry(String str, String str2) throws TTSException {
        return _findEntry(this.lexiconReference.longValue(), str, str2);
    }

    public String getName() {
        return null;
    }

    public String getParam(String str) throws TTSException {
        return null;
    }

    public Long getReference() throws TTSException {
        return this.lexiconReference;
    }

    public void importfile(String str) throws TTSException {
        _importfile(this.lexiconReference.longValue(), str);
    }

    public void removeEntry(String str, String str2) throws TTSException {
        _removeEntry(this.lexiconReference.longValue(), str, str2);
    }

    public void setParam(String str, String str2) throws TTSException {
    }
}
