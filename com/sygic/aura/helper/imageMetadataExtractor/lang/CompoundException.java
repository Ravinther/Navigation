package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

public class CompoundException extends Exception {
    private final Throwable _innerException;

    public CompoundException(String msg) {
        this(msg, null);
    }

    public CompoundException(String msg, Throwable innerException) {
        super(msg);
        this._innerException = innerException;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(super.toString());
        if (this._innerException != null) {
            string.append("\n");
            string.append("--- inner exception ---");
            string.append("\n");
            string.append(this._innerException.toString());
        }
        return string.toString();
    }

    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        if (this._innerException != null) {
            s.println("--- inner exception ---");
            this._innerException.printStackTrace(s);
        }
    }

    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        if (this._innerException != null) {
            s.println("--- inner exception ---");
            this._innerException.printStackTrace(s);
        }
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (this._innerException != null) {
            System.err.println("--- inner exception ---");
            this._innerException.printStackTrace();
        }
    }
}
