package com.sygic.sdk.api.exception;

public class GeneralException extends Exception {
    private int mCode;

    public int getCode() {
        return this.mCode;
    }
}
