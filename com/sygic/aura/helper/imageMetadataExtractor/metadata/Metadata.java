package com.sygic.aura.helper.imageMetadataExtractor.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Metadata {
    private final Map<Class<? extends Directory>, Directory> _directoryByClass;
    private final Collection<Directory> _directoryList;

    public Metadata() {
        this._directoryByClass = new HashMap();
        this._directoryList = new ArrayList();
    }

    public <T extends Directory> T getOrCreateDirectory(Class<T> type) {
        if (this._directoryByClass.containsKey(type)) {
            return (Directory) this._directoryByClass.get(type);
        }
        try {
            T directory = (Directory) type.newInstance();
            this._directoryByClass.put(type, directory);
            this._directoryList.add(directory);
            return directory;
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate provided Directory type: " + type.toString());
        }
    }

    public <T extends Directory> T getDirectory(Class<T> type) {
        return (Directory) this._directoryByClass.get(type);
    }
}
