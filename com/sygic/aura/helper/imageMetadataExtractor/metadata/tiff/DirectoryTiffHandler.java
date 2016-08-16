package com.sygic.aura.helper.imageMetadataExtractor.metadata.tiff;

import com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff.TiffHandler;
import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;
import java.util.Stack;

public abstract class DirectoryTiffHandler implements TiffHandler {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected Directory _currentDirectory;
    private final Stack<Directory> _directoryStack;
    protected Metadata _metadata;

    static {
        $assertionsDisabled = !DirectoryTiffHandler.class.desiredAssertionStatus();
    }

    protected DirectoryTiffHandler(Metadata metadata, Class<? extends Directory> initialDirectory) {
        this._directoryStack = new Stack();
        this._metadata = metadata;
        this._currentDirectory = this._metadata.getOrCreateDirectory(initialDirectory);
    }

    public void endingIFD() {
        this._currentDirectory = this._directoryStack.empty() ? null : (Directory) this._directoryStack.pop();
    }

    protected void pushDirectory(Class<? extends Directory> directoryClass) {
        if ($assertionsDisabled || directoryClass != this._currentDirectory.getClass()) {
            this._directoryStack.push(this._currentDirectory);
            this._currentDirectory = this._metadata.getOrCreateDirectory(directoryClass);
            return;
        }
        throw new AssertionError();
    }

    public void warn(String message) {
        this._currentDirectory.addError(message);
    }

    public void error(String message) {
        this._currentDirectory.addError(message);
    }

    public void setByteArray(int tagId, byte[] bytes) {
        this._currentDirectory.setByteArray(tagId, bytes);
    }

    public void setString(int tagId, String string) {
        this._currentDirectory.setString(tagId, string);
    }

    public void setRational(int tagId, Rational rational) {
        this._currentDirectory.setRational(tagId, rational);
    }

    public void setRationalArray(int tagId, Rational[] array) {
        this._currentDirectory.setRationalArray(tagId, array);
    }

    public void setFloat(int tagId, float float32) {
        this._currentDirectory.setFloat(tagId, float32);
    }

    public void setFloatArray(int tagId, float[] array) {
        this._currentDirectory.setFloatArray(tagId, array);
    }

    public void setDouble(int tagId, double double64) {
        this._currentDirectory.setDouble(tagId, double64);
    }

    public void setDoubleArray(int tagId, double[] array) {
        this._currentDirectory.setDoubleArray(tagId, array);
    }

    public void setInt8s(int tagId, byte int8s) {
        this._currentDirectory.setInt(tagId, int8s);
    }

    public void setInt8sArray(int tagId, byte[] array) {
        this._currentDirectory.setByteArray(tagId, array);
    }

    public void setInt8u(int tagId, short int8u) {
        this._currentDirectory.setInt(tagId, int8u);
    }

    public void setInt8uArray(int tagId, short[] array) {
        this._currentDirectory.setObjectArray(tagId, array);
    }

    public void setInt16s(int tagId, int int16s) {
        this._currentDirectory.setInt(tagId, int16s);
    }

    public void setInt16sArray(int tagId, short[] array) {
        this._currentDirectory.setObjectArray(tagId, array);
    }

    public void setInt16u(int tagId, int int16u) {
        this._currentDirectory.setInt(tagId, int16u);
    }

    public void setInt16uArray(int tagId, int[] array) {
        this._currentDirectory.setObjectArray(tagId, array);
    }

    public void setInt32s(int tagId, int int32s) {
        this._currentDirectory.setInt(tagId, int32s);
    }

    public void setInt32sArray(int tagId, int[] array) {
        this._currentDirectory.setIntArray(tagId, array);
    }

    public void setInt32u(int tagId, long int32u) {
        this._currentDirectory.setLong(tagId, int32u);
    }

    public void setInt32uArray(int tagId, long[] array) {
        this._currentDirectory.setObjectArray(tagId, array);
    }
}
