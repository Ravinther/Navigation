package com.sygic.aura.helper.imageMetadataExtractor.imaging;

import com.sygic.aura.helper.imageMetadataExtractor.lang.CompoundException;

public class ImageProcessingException extends CompoundException {
    public ImageProcessingException(String message) {
        super(message);
    }
}
