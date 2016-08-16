package loquendo.tts.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class TTSReader extends TTSObject {
    private HashSet _listeners;
    private HashSet _outputlisteners;
    private long readID;
    private Long readerReference;
    private Long sessionReference;

    public TTSReader() throws TTSException {
        this(null, null);
    }

    public TTSReader(String str) throws TTSException {
        this(null, str);
    }

    public TTSReader(TTSSession tTSSession) throws TTSException {
        this(tTSSession, null);
    }

    public TTSReader(TTSSession tTSSession, String str) throws TTSException {
        this.readerReference = null;
        this.sessionReference = null;
        this.readID = 0;
        this._listeners = null;
        this._outputlisteners = null;
        if (tTSSession == null || tTSSession.getReference() == null) {
            this.sessionReference = new Long(0);
        } else {
            this.sessionReference = tTSSession.getReference();
        }
        this.readerReference = new Long(_new(this.sessionReference.longValue(), str));
        setParam("IgnoreThreadBoundaryCrossing", "yes");
    }

    private native boolean _audioFreeSpace(long j) throws TTSException;

    private native Vector _call(long j, String str, Object[] objArr) throws TTSException;

    private native void _checkPhoneticTranscription(long j, String str) throws TTSException;

    private native int _claimLicense(long j);

    private native void _delete(long j) throws TTSException;

    private native boolean _done(long j);

    private native void _enableEvent(long j, int i, boolean z);

    private void _fireTTSEvent(int i, Object obj) {
        Iterator it = this._listeners.iterator();
        while (it.hasNext()) {
            ((TTSListener) it.next()).eventOccurred(new TTSEvent(this, i, obj));
        }
    }

    private native void _freeInternalParameters(long j, String str, Object[] objArr) throws TTSException;

    private native int _getBalance(long j) throws TTSException;

    private native int _getDelay(long j) throws TTSException;

    private native int _getGain(long j) throws TTSException;

    private native float _getGraphicEQBand(long j, int i) throws TTSException;

    private native String[] _getGraphicEQPreset(long j);

    private native String _getParam(long j, String str) throws TTSException;

    private native int _getPitch(long j) throws TTSException;

    private native int _getSpeed(long j) throws TTSException;

    private native int _getTimbre(long j) throws TTSException;

    private native int _getVolume(long j) throws TTSException;

    private native String _languageGuess(long j, String str, int i, int i2, int i3) throws TTSException;

    private native void _loadPersona(long j, String str, String str2, String str3) throws TTSException;

    private native long _new(long j, String str) throws TTSException;

    private native void _pause(long j) throws TTSException;

    private native Vector _phoneticTranscription(long j, String str, int i) throws TTSException;

    private native int _read(long j, String str, boolean z, boolean z2) throws TTSException;

    private native void _registerCallback(long j, Object obj, long j2) throws TTSException;

    private native void _registerOutputCallback(long j, Object obj);

    private native void _resetGraphicEQ(long j) throws TTSException;

    private native void _resume(long j) throws TTSException;

    private native void _saveCF(long j, String str) throws TTSException;

    private native void _setAudio(long j, String str, String str2, int i, String str3, int i2) throws TTSException;

    private native void _setBalance(long j, int i) throws TTSException;

    private native void _setDefaultAttribute(long j) throws TTSException;

    private native void _setGraphicEQBand(long j, int i, float f) throws TTSException;

    private native void _setGraphicEQPreset(long j, String str) throws TTSException;

    private native void _setLanguage(long j, long j2) throws TTSException;

    private native void _setLexicon(long j, long j2, int i) throws TTSException;

    private native void _setMode(long j, String str) throws TTSException;

    private native void _setModularStructure(long j, String str, String str2, long j2);

    private native void _setParam(long j, String str, String str2) throws TTSException;

    private native void _setPitch(long j, int i) throws TTSException;

    private native void _setPitchRange(long j, int i, int i2, int i3) throws TTSException;

    private native void _setReverb(long j, int i, int i2) throws TTSException;

    private native void _setSpeed(long j, int i) throws TTSException;

    private native void _setSpeedRange(long j, int i, int i2, int i3) throws TTSException;

    private native void _setStyle(long j, long j2) throws TTSException;

    private native void _setTimbre(long j, int i) throws TTSException;

    private native void _setVoice(long j, long j2) throws TTSException;

    private native void _setVolume(long j, int i) throws TTSException;

    private native void _setVolumeRange(long j, int i, int i2, int i3) throws TTSException;

    private native String _ssmlConvert(long j, String str, boolean z) throws TTSException;

    private native void _stop(long j) throws TTSException;

    private native int _unclaimLicense(long j);

    private native void _validateXML(long j, String str, boolean z) throws TTSException;

    private native boolean _waitForEndOfSpeech(long j, int i);

    public static String[] getGraphicEQPreset() throws TTSException {
        return new String[]{"BassBoost", "LowFidelity", "NotchTwoOfThree", "ForbiddenPlanet", "HighPass", "LowPass", "VoicePresence"};
    }

    public void addTTSListener(TTSListener tTSListener) throws TTSException {
        if (this._listeners == null) {
            _registerCallback(this.readerReference.longValue(), this, 0);
            this._listeners = new HashSet();
        }
        this._listeners.add(tTSListener);
    }

    public boolean audioFreeSpace() throws TTSException {
        return _audioFreeSpace(this.readerReference.longValue());
    }

    public Vector call(String str, Object[] objArr) throws TTSException {
        return _call(this.readerReference.longValue(), str, objArr);
    }

    public void checkPhoneticTranscription(String str) throws TTSException {
        _checkPhoneticTranscription(this.readerReference.longValue(), str);
    }

    public int claimLicense() throws TTSException {
        return _claimLicense(this.readerReference.longValue());
    }

    public void delete() throws TTSException {
        removeAllListeners();
        _delete(this.readerReference.longValue());
        this.readerReference = null;
    }

    public boolean done() {
        return _done(this.readerReference.longValue());
    }

    public void enableEvent(int i, boolean z) throws TTSException {
        _enableEvent(this.readerReference.longValue(), i, z);
    }

    public void freeInternalParameters(String str, Object[] objArr) throws TTSException {
        _freeInternalParameters(this.readerReference.longValue(), str, objArr);
    }

    public int getBalance() throws TTSException {
        return _getBalance(this.readerReference.longValue());
    }

    public int getDelay() throws TTSException {
        return _getDelay(this.readerReference.longValue());
    }

    public int getGain() throws TTSException {
        return _getGain(this.readerReference.longValue());
    }

    public float getGraphicEQBand(int i) throws TTSException {
        return _getGraphicEQBand(this.readerReference.longValue(), i);
    }

    public String getName() {
        return null;
    }

    public String getParam(String str) throws TTSException {
        return _getParam(this.readerReference.longValue(), str);
    }

    public int getPitch() throws TTSException {
        return _getPitch(this.readerReference.longValue());
    }

    public Long getReference() throws TTSException {
        return this.readerReference;
    }

    public int getSpeed() throws TTSException {
        return _getSpeed(this.readerReference.longValue());
    }

    public Object[] getTTSListeners() throws TTSException {
        return this._listeners != null ? this._listeners.toArray() : null;
    }

    public int getTimbre() throws TTSException {
        return _getTimbre(this.readerReference.longValue());
    }

    public int getVolume() throws TTSException {
        return _getVolume(this.readerReference.longValue());
    }

    public void loadPersona(String str, String str2, String str3) throws TTSException {
        _loadPersona(this.readerReference.longValue(), str, str2, str3);
    }

    public void pause() throws TTSException {
        _pause(this.readerReference.longValue());
    }

    public Iterator phoneticTranscription(String str, int i) throws TTSException {
        Vector _phoneticTranscription = _phoneticTranscription(this.readerReference.longValue(), str, i);
        return _phoneticTranscription == null ? null : _phoneticTranscription.iterator();
    }

    public void read(String str, boolean z, boolean z2) throws TTSException {
        this.readID = (long) _read(this.readerReference.longValue(), str, z, z2);
    }

    public void removeAllListeners() throws TTSException {
        if (this._listeners != null) {
            _registerCallback(this.readerReference.longValue(), this, 1);
            this._listeners = null;
        }
    }

    public void removeTTSListener(TTSListener tTSListener) throws TTSException {
        this._listeners.remove(tTSListener);
        if (this._listeners.isEmpty()) {
            _registerCallback(this.readerReference.longValue(), this, 1);
            this._listeners = null;
        }
    }

    public void resetGraphicEQ() throws TTSException {
        _resetGraphicEQ(this.readerReference.longValue());
    }

    public void resume() throws TTSException {
        _resume(this.readerReference.longValue());
    }

    public void saveCF(String str) throws TTSException {
        _saveCF(this.readerReference.longValue(), str);
    }

    public void setAudio(String str, String str2, int i, String str3, int i2) throws TTSException {
        _setAudio(this.readerReference.longValue(), str, str2, i, str3, i2);
    }

    public void setBalance(int i) throws TTSException {
        _setBalance(this.readerReference.longValue(), i);
    }

    public void setDefaultAttribute() throws TTSException {
        _setDefaultAttribute(this.readerReference.longValue());
    }

    public void setDelay(int i) throws TTSException {
        _setReverb(this.readerReference.longValue(), _getDelay(this.readerReference.longValue()), i);
    }

    public void setGain(int i) throws TTSException {
        _setReverb(this.readerReference.longValue(), i, _getDelay(this.readerReference.longValue()));
    }

    public void setGraphicEQBand(int i, float f) throws TTSException {
        _setGraphicEQBand(this.readerReference.longValue(), i, f);
    }

    public void setGraphicEQPreset(String str) throws TTSException {
        _setGraphicEQPreset(this.readerReference.longValue(), str);
    }

    public void setLanguage(TTSLanguage tTSLanguage) throws TTSException {
        _setLanguage(this.readerReference.longValue(), tTSLanguage.getReference().longValue());
    }

    public void setLexicon(TTSLexicon tTSLexicon, int i) throws TTSException {
        if (tTSLexicon.newAPIVersion) {
            _setLexicon(tTSLexicon.getReference().longValue(), this.readerReference.longValue(), i);
            return;
        }
        throw new TTSException("Error: the object is incopatible with this method; please use a not deprecated constructor");
    }

    public void setMode(String str) throws TTSException {
        _setMode(this.readerReference.longValue(), str);
    }

    public void setModularStructure(String str, String str2, Long l) throws TTSException {
        _setModularStructure(this.readerReference.longValue(), str, str2, l.longValue());
    }

    public void setParam(String str, String str2) throws TTSException {
        _setParam(this.readerReference.longValue(), str, str2);
    }

    public void setPitch(int i) throws TTSException {
        _setPitch(this.readerReference.longValue(), i);
    }

    public void setPitchRange(int i, int i2, int i3) throws TTSException {
        _setPitchRange(this.readerReference.longValue(), i, i2, i3);
    }

    public void setReverb(int i, int i2) throws TTSException {
        _setReverb(this.readerReference.longValue(), i, i2);
    }

    public void setSpeed(int i) throws TTSException {
        _setSpeed(this.readerReference.longValue(), i);
    }

    public void setSpeedRange(int i, int i2, int i3) throws TTSException {
        _setSpeedRange(this.readerReference.longValue(), i, i2, i3);
    }

    public void setStyle(TTSStyle tTSStyle) throws TTSException {
        _setStyle(this.readerReference.longValue(), tTSStyle.styleReference.longValue());
    }

    public void setTimbre(int i) throws TTSException {
        _setTimbre(this.readerReference.longValue(), i);
    }

    public void setVoice(TTSVoice tTSVoice) throws TTSException {
        _setVoice(this.readerReference.longValue(), tTSVoice == null ? 0 : tTSVoice.getReference().longValue());
    }

    public void setVolume(int i) throws TTSException {
        _setVolume(this.readerReference.longValue(), i);
    }

    public void setVolumeRange(int i, int i2, int i3) throws TTSException {
        _setVolumeRange(this.readerReference.longValue(), i, i2, i3);
    }

    public String ssmlConvert(String str, boolean z) throws TTSException {
        return _ssmlConvert(this.readerReference.longValue(), str, z);
    }

    public void stop() throws TTSException {
        _stop(this.readerReference.longValue());
    }

    public int unclaimLicense() throws TTSException {
        return _unclaimLicense(this.readerReference.longValue());
    }

    public void validateXML(String str, boolean z) throws TTSException {
        _validateXML(this.readerReference.longValue(), str, z);
    }

    public boolean waitForEndOfSpeech(int i) throws TTSException {
        return _waitForEndOfSpeech(this.readerReference.longValue(), i);
    }
}
