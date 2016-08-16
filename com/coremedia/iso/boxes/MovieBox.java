package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractContainerBox;

public class MovieBox extends AbstractContainerBox {
    public MovieBox() {
        super("moov");
    }

    public MovieHeaderBox getMovieHeaderBox() {
        for (Box box : getBoxes()) {
            if (box instanceof MovieHeaderBox) {
                return (MovieHeaderBox) box;
            }
        }
        return null;
    }
}
