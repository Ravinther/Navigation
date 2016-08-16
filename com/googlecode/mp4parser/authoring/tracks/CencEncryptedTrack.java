package com.googlecode.mp4parser.authoring.tracks;

import com.googlecode.mp4parser.authoring.Track;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;
import java.util.List;

public interface CencEncryptedTrack extends Track {
    List<CencSampleAuxiliaryDataFormat> getSampleEncryptionEntries();

    boolean hasSubSampleEncryption();
}
