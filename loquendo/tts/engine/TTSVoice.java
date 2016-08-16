package loquendo.tts.engine;

import java.util.Vector;

public class TTSVoice extends TTSObject {
    private Long sessionReference;
    private String voiceName;
    private Long voiceReference;

    public TTSVoice(String str, String str2) throws TTSException {
        this(new TTSSession(str), str2);
    }

    public TTSVoice(TTSSession tTSSession, String str) throws TTSException {
        this.voiceReference = null;
        this.sessionReference = null;
        this.voiceName = null;
        if (tTSSession == null || tTSSession.getReference() == null) {
            this.sessionReference = new Long(0);
        } else {
            this.sessionReference = tTSSession.getReference();
        }
        try {
            this.voiceReference = new Long(_newVoice(this.sessionReference.longValue(), str));
            this.voiceName = str;
        } catch (TTSException e) {
            throw new TTSException(e.getMessage());
        }
    }

    public TTSVoice(TTSVoice tTSVoice, String str) throws TTSException {
        this.voiceReference = null;
        this.sessionReference = null;
        this.voiceName = null;
        this.sessionReference = tTSVoice.sessionReference;
        try {
            this.voiceReference = new Long(_clone(tTSVoice.getReference().longValue(), str));
            this.voiceName = str;
        } catch (TTSException e) {
            throw new TTSException(e.getMessage());
        }
    }

    private native void _activate(long j, long j2) throws TTSException;

    private native long _clone(long j, String str) throws TTSException;

    private native void _delete(long j) throws TTSException;

    private native String _getParam(long j, String str) throws TTSException;

    private native Vector _getSpeechAtom(long j, long j2);

    private native Vector _getVoiceFlavours(long j);

    private native long _locateSpeechAtomInventory(long j, String str) throws TTSException;

    private native long _newVoice(long j, String str) throws TTSException;

    private native void _saveCF(long j, String str) throws TTSException;

    private native void _setParam(long j, String str, String str2) throws TTSException;

    public void activate(TTSReader tTSReader) throws TTSException {
        _activate(tTSReader.getReference().longValue(), this.voiceReference.longValue());
    }

    public void delete() throws TTSException {
        _delete(this.voiceReference.longValue());
        this.voiceReference = null;
    }

    public String getName() {
        return this.voiceName;
    }

    public String getParam(String str) throws TTSException {
        return _getParam(this.voiceReference.longValue(), str);
    }

    public Long getReference() throws TTSException {
        return this.voiceReference;
    }

    public Vector getSpeechAtomChild(Long l) throws TTSException {
        return _getSpeechAtom(l == null ? 0 : l.longValue(), 0);
    }

    public Vector getSpeechAtomSibling(Long l) throws TTSException {
        return _getSpeechAtom(0, l == null ? 0 : l.longValue());
    }

    public Object[] getVoiceFlavours() throws TTSException {
        Vector _getVoiceFlavours = _getVoiceFlavours(this.voiceReference.longValue());
        return _getVoiceFlavours == null ? null : _getVoiceFlavours.toArray();
    }

    public String getVoiceName() {
        return this.voiceName;
    }

    public Long locateSpeechAtomInventory(String str) throws TTSException {
        return new Long(_locateSpeechAtomInventory(this.voiceReference.longValue(), str));
    }

    public void saveCF(String str) throws TTSException {
        _saveCF(this.voiceReference.longValue(), str);
    }

    public void setParam(String str, String str2) throws TTSException {
        _setParam(this.voiceReference.longValue(), str, str2);
    }
}
