package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractContainerBox;

public class MediaInformationBox extends AbstractContainerBox {
    public MediaInformationBox() {
        super("minf");
    }

    public SampleTableBox getSampleTableBox() {
        for (Box box : getBoxes()) {
            if (box instanceof SampleTableBox) {
                return (SampleTableBox) box;
            }
        }
        return null;
    }
}
