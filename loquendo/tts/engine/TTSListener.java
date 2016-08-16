package loquendo.tts.engine;

import java.util.EventListener;

public interface TTSListener extends EventListener {
    void eventOccurred(TTSEvent tTSEvent);
}
