package com.sygic.aura.helper.imageMetadataExtractor.metadata;

public class Tag {
    private final Directory _directory;
    private final int _tagType;

    public Tag(int tagType, Directory directory) {
        this._tagType = tagType;
        this._directory = directory;
    }

    public int getTagType() {
        return this._tagType;
    }

    public String getDescription() {
        return this._directory.getDescription(this._tagType);
    }

    public String getTagName() {
        return this._directory.getTagName(this._tagType);
    }

    public String toString() {
        String description = getDescription();
        if (description == null) {
            description = this._directory.getString(getTagType()) + " (unable to formulate description)";
        }
        return "[" + this._directory.getName() + "] " + getTagName() + " - " + description;
    }
}
