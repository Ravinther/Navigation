package loquendo.tts.engine;

public abstract class TTSObject {
    protected static final String FINALIZEPARAMNAME = "IgnoreThreadBoundaryCrossing";
    protected static final String FINALIZEPARAMVALUE = "yes";

    static {
        System.loadLibrary("ttsloquendoengine");
        TTSConfiguration.resolveTTSSymbols(System.getProperty("user.dir"));
    }

    public abstract String getName();

    public abstract String getParam(String str) throws TTSException;

    public abstract Long getReference() throws TTSException;

    public abstract void setParam(String str, String str2) throws TTSException;
}
