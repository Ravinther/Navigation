package loquendo.tts.engine;

import java.util.Vector;

public class TTSConfiguration {
    static {
        System.loadLibrary("ttsloquendoengine");
        resolveTTSSymbols(System.getProperty("user.dir"));
    }

    private static native Vector _call(String str, Object[] objArr) throws TTSException;

    private static native void _delete(String str) throws TTSException;

    private static native String _getMp3LibraryName() throws TTSException;

    private static native boolean _isMp3SupportInstalled() throws TTSException;

    private static native String _loadParam(String str, String str2) throws TTSException;

    private static native void _saveParam(String str, String str2, String str3) throws TTSException;

    public static Vector call(String str, Object[] objArr) throws TTSException {
        return _call(str, objArr);
    }

    public static void delete(String str) throws TTSException {
        _delete(str);
    }

    public static String getMp3LibraryName() throws TTSException {
        return _getMp3LibraryName();
    }

    public static native String getVersionInfo() throws TTSException;

    public static boolean isMp3SupportInstalled() throws TTSException {
        return _isMp3SupportInstalled();
    }

    public static String loadParam(String str, String str2) throws TTSException {
        return _loadParam(str, str2);
    }

    protected static native void resolveTTSSymbols(String str);

    public static void saveParam(String str, String str2, String str3) throws TTSException {
        _saveParam(str, str2, str3);
    }
}
