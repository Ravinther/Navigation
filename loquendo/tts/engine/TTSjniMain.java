package loquendo.tts.engine;

public class TTSjniMain {
    public static void main(String[] strArr) {
        if (strArr.length <= 0) {
            return;
        }
        if (strArr[0].compareTo("--version") == 0 || strArr[0].compareTo("--ver") == 0 || strArr[0].compareTo("-version") == 0 || strArr[0].compareTo("-ver") == 0) {
            System.out.println("LoquendoTTS jni wrapper - Version 7.20.0");
        }
    }
}
