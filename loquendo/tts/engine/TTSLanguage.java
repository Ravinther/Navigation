package loquendo.tts.engine;

import java.util.HashMap;
import java.util.Map;

public class TTSLanguage extends TTSObject {
    private String languageName;
    private Long languageReference;
    private Long sessionReference;

    public TTSLanguage(String str, String str2) throws TTSException {
        this(new TTSSession(str), str2);
    }

    public TTSLanguage(TTSLanguage tTSLanguage, String str) throws TTSException {
        this.languageReference = null;
        this.sessionReference = null;
        this.languageName = null;
        this.sessionReference = tTSLanguage.sessionReference;
        try {
            this.languageReference = new Long(_clone(this.sessionReference.longValue(), str));
            this.languageName = str;
        } catch (TTSException e) {
            throw new TTSException(e.getMessage());
        }
    }

    public TTSLanguage(TTSSession tTSSession, String str) throws TTSException {
        this.languageReference = null;
        this.sessionReference = null;
        this.languageName = null;
        if (tTSSession == null || tTSSession.getReference() == null) {
            this.sessionReference = new Long(0);
        } else {
            this.sessionReference = tTSSession.getReference();
        }
        try {
            this.languageReference = new Long(_newLanguage(this.sessionReference.longValue(), str));
            this.languageName = str;
        } catch (TTSException e) {
            throw new TTSException(e.getMessage());
        }
    }

    private native void _activate(long j, long j2) throws TTSException;

    private native long _clone(long j, String str) throws TTSException;

    private native void _delete(long j) throws TTSException;

    private native String _getParam(long j, String str) throws TTSException;

    private native HashMap _getPhonemeMap(long j) throws TTSException;

    private native long _newLanguage(long j, String str) throws TTSException;

    private native void _saveCF(long j, String str) throws TTSException;

    private native void _setParam(long j, String str, String str2) throws TTSException;

    public void activate(TTSReader tTSReader) throws TTSException {
        _activate(tTSReader.getReference().longValue(), this.languageReference.longValue());
    }

    public void delete() throws TTSException {
        _delete(this.languageReference.longValue());
        this.languageReference = null;
    }

    public String getName() {
        return this.languageName;
    }

    public String getParam(String str) throws TTSException {
        return _getParam(this.languageReference.longValue(), str);
    }

    public Map getPhonemeMap() throws TTSException {
        return _getPhonemeMap(this.languageReference.longValue());
    }

    public Long getReference() throws TTSException {
        return this.languageReference;
    }

    public void saveCF(String str) throws TTSException {
        _saveCF(this.languageReference.longValue(), str);
    }

    public void setParam(String str, String str2) throws TTSException {
        _setParam(this.languageReference.longValue(), str, str2);
    }
}
