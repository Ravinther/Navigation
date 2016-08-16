package loquendo.tts.engine;

import java.util.EventListener;

public interface TTSOutputListener extends EventListener {
    void outputEventOccurred(TTSOutputEvent tTSOutputEvent);
}
