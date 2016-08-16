package loquendo.tts.engine;

public class TTSException extends Exception {
    private static final long serialVersionUID = -7146541941388878386L;
    public Integer failure;

    public TTSException(String str) {
        super(str);
        this.failure = null;
    }

    public TTSException(String str, int i) {
        super(str);
        this.failure = null;
        this.failure = new Integer(i);
    }
}
