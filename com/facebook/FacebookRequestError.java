package com.facebook;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookRequestError {
    static final Range HTTP_RANGE_SUCCESS;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final String errorMessage;
    private final String errorRecoveryMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final int subErrorCode;

    public enum Category {
        LOGIN_RECOVERABLE,
        OTHER,
        TRANSIENT
    }

    private static class Range {
        private final int end;
        private final int start;

        private Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        boolean contains(int value) {
            return this.start <= value && value <= this.end;
        }
    }

    static {
        HTTP_RANGE_SUCCESS = new Range(299, null);
    }

    private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, String errorUserTitle, String errorUserMessage, boolean errorIsTransient, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection, FacebookException exception) {
        Category category;
        this.requestStatusCode = requestStatusCode;
        this.errorCode = errorCode;
        this.subErrorCode = subErrorCode;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
        this.requestResultBody = requestResultBody;
        this.requestResult = requestResult;
        this.batchRequestResult = batchRequestResult;
        this.connection = connection;
        this.errorUserTitle = errorUserTitle;
        this.errorUserMessage = errorUserMessage;
        boolean isLocalException = false;
        if (exception != null) {
            this.exception = exception;
            isLocalException = true;
        } else {
            this.exception = new FacebookServiceException(this, errorMessage);
        }
        FacebookRequestErrorClassification errorClassification = getErrorClassification();
        if (isLocalException) {
            category = Category.OTHER;
        } else {
            category = errorClassification.classify(errorCode, subErrorCode, errorIsTransient);
        }
        this.category = category;
        this.errorRecoveryMessage = errorClassification.getRecoveryMessage(this.category);
    }

    FacebookRequestError(HttpURLConnection connection, Exception exception) {
        this(-1, -1, -1, null, null, null, null, false, null, null, null, connection, exception instanceof FacebookException ? (FacebookException) exception : new FacebookException((Throwable) exception));
    }

    public FacebookRequestError(int errorCode, String errorType, String errorMessage) {
        this(-1, errorCode, -1, errorType, errorMessage, null, null, false, null, null, null, null, null);
    }

    public int getRequestStatusCode() {
        return this.requestStatusCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public String getErrorMessage() {
        if (this.errorMessage != null) {
            return this.errorMessage;
        }
        return this.exception.getLocalizedMessage();
    }

    public FacebookException getException() {
        return this.exception;
    }

    public String toString() {
        return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", errorType: " + this.errorType + ", errorMessage: " + getErrorMessage() + "}";
    }

    static FacebookRequestError checkResponseAndCreateError(JSONObject singleResult, Object batchResult, HttpURLConnection connection) {
        try {
            if (singleResult.has("code")) {
                int responseCode = singleResult.getInt("code");
                Object body = Utility.getStringPropertyAsJSON(singleResult, "body", "FACEBOOK_NON_JSON_RESULT");
                if (body != null && (body instanceof JSONObject)) {
                    JSONObject jsonBody = (JSONObject) body;
                    String errorType = null;
                    String errorMessage = null;
                    String errorUserMessage = null;
                    String errorUserTitle = null;
                    boolean errorIsTransient = false;
                    int errorCode = -1;
                    int errorSubCode = -1;
                    boolean hasError = false;
                    if (jsonBody.has("error")) {
                        JSONObject error = (JSONObject) Utility.getStringPropertyAsJSON(jsonBody, "error", null);
                        errorType = error.optString("type", null);
                        errorMessage = error.optString("message", null);
                        errorCode = error.optInt("code", -1);
                        errorSubCode = error.optInt("error_subcode", -1);
                        errorUserMessage = error.optString("error_user_msg", null);
                        errorUserTitle = error.optString("error_user_title", null);
                        errorIsTransient = error.optBoolean("is_transient", false);
                        hasError = true;
                    } else if (jsonBody.has("error_code") || jsonBody.has("error_msg") || jsonBody.has("error_reason")) {
                        errorType = jsonBody.optString("error_reason", null);
                        errorMessage = jsonBody.optString("error_msg", null);
                        errorCode = jsonBody.optInt("error_code", -1);
                        errorSubCode = jsonBody.optInt("error_subcode", -1);
                        hasError = true;
                    }
                    if (hasError) {
                        return new FacebookRequestError(responseCode, errorCode, errorSubCode, errorType, errorMessage, errorUserTitle, errorUserMessage, errorIsTransient, jsonBody, singleResult, batchResult, connection, null);
                    }
                }
                if (!HTTP_RANGE_SUCCESS.contains(responseCode)) {
                    JSONObject jSONObject;
                    if (singleResult.has("body")) {
                        jSONObject = (JSONObject) Utility.getStringPropertyAsJSON(singleResult, "body", "FACEBOOK_NON_JSON_RESULT");
                    } else {
                        jSONObject = null;
                    }
                    return new FacebookRequestError(responseCode, -1, -1, null, null, null, null, false, jSONObject, singleResult, batchResult, connection, null);
                }
            }
        } catch (JSONException e) {
        }
        return null;
    }

    static synchronized FacebookRequestErrorClassification getErrorClassification() {
        FacebookRequestErrorClassification defaultErrorClassification;
        synchronized (FacebookRequestError.class) {
            FetchedAppSettings appSettings = Utility.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            if (appSettings == null) {
                defaultErrorClassification = FacebookRequestErrorClassification.getDefaultErrorClassification();
            } else {
                defaultErrorClassification = appSettings.getErrorClassification();
            }
        }
        return defaultErrorClassification;
    }
}
