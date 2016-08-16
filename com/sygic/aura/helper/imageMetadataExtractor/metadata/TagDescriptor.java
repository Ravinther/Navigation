package com.sygic.aura.helper.imageMetadataExtractor.metadata;

import java.lang.reflect.Array;

public class TagDescriptor<T extends Directory> {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected final T _directory;

    static {
        $assertionsDisabled = !TagDescriptor.class.desiredAssertionStatus();
    }

    public TagDescriptor(T directory) {
        this._directory = directory;
    }

    public String getDescription(int tagType) {
        Object object = this._directory.getObject(tagType);
        if (object == null) {
            return null;
        }
        if (object.getClass().isArray()) {
            int length = Array.getLength(object);
            if (length > 16) {
                String componentTypeName = object.getClass().getComponentType().getName();
                String str = "[%d %s%s]";
                Object[] objArr = new Object[3];
                objArr[0] = Integer.valueOf(length);
                objArr[1] = componentTypeName;
                objArr[2] = length == 1 ? "" : "s";
                return String.format(str, objArr);
            }
        }
        return this._directory.getString(tagType);
    }

    public static String convertBytesToVersionString(int[] components, int majorDigits) {
        if (components == null) {
            return null;
        }
        StringBuilder version = new StringBuilder();
        int i = 0;
        while (i < 4 && i < components.length) {
            if (i == majorDigits) {
                version.append('.');
            }
            char c = (char) components[i];
            if (c < '0') {
                c = (char) (c + 48);
            }
            if (i != 0 || c != '0') {
                version.append(c);
            }
            i++;
        }
        return version.toString();
    }

    protected String getVersionBytesDescription(int tagType, int majorDigits) {
        int[] values = this._directory.getIntArray(tagType);
        return values == null ? null : convertBytesToVersionString(values, majorDigits);
    }

    protected String getIndexedDescription(int tagType, String... descriptions) {
        return getIndexedDescription(tagType, 0, descriptions);
    }

    protected String getIndexedDescription(int tagType, int baseIndex, String... descriptions) {
        Integer index = this._directory.getInteger(tagType);
        if (index == null) {
            return null;
        }
        int arrayIndex = index.intValue() - baseIndex;
        if (arrayIndex >= 0 && arrayIndex < descriptions.length) {
            String description = descriptions[arrayIndex];
            if (description != null) {
                return description;
            }
        }
        return "Unknown (" + index + ")";
    }
}
