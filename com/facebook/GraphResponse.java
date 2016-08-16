package com.facebook;

import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GraphResponse {
    private final HttpURLConnection connection;
    private final FacebookRequestError error;
    private final JSONObject graphObject;
    private final JSONArray graphObjectArray;
    private final String rawResponse;
    private final GraphRequest request;

    GraphResponse(GraphRequest request, HttpURLConnection connection, String rawResponse, JSONObject graphObject) {
        this(request, connection, rawResponse, graphObject, null, null);
    }

    GraphResponse(GraphRequest request, HttpURLConnection connection, String rawResponse, JSONArray graphObjects) {
        this(request, connection, rawResponse, null, graphObjects, null);
    }

    GraphResponse(GraphRequest request, HttpURLConnection connection, FacebookRequestError error) {
        this(request, connection, null, null, null, error);
    }

    GraphResponse(GraphRequest request, HttpURLConnection connection, String rawResponse, JSONObject graphObject, JSONArray graphObjects, FacebookRequestError error) {
        this.request = request;
        this.connection = connection;
        this.rawResponse = rawResponse;
        this.graphObject = graphObject;
        this.graphObjectArray = graphObjects;
        this.error = error;
    }

    public final FacebookRequestError getError() {
        return this.error;
    }

    public final JSONObject getJSONObject() {
        return this.graphObject;
    }

    public String toString() {
        String responseCode;
        try {
            Locale locale = Locale.US;
            String str = "%d";
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.connection != null ? this.connection.getResponseCode() : 200);
            responseCode = String.format(locale, str, objArr);
        } catch (IOException e) {
            responseCode = "unknown";
        }
        return "{Response: " + " responseCode: " + responseCode + ", graphObject: " + this.graphObject + ", error: " + this.error + "}";
    }

    static List<GraphResponse> fromHttpConnection(HttpURLConnection connection, GraphRequestBatch requests) {
        List<GraphResponse> createResponsesFromStream;
        InputStream stream = null;
        try {
            if (connection.getResponseCode() >= 400) {
                stream = connection.getErrorStream();
            } else {
                stream = connection.getInputStream();
            }
            createResponsesFromStream = createResponsesFromStream(stream, connection, requests);
        } catch (FacebookException facebookException) {
            Logger.log(LoggingBehavior.REQUESTS, "Response", "Response <Error>: %s", facebookException);
            createResponsesFromStream = constructErrorResponses(requests, connection, facebookException);
        } catch (Throwable exception) {
            Logger.log(LoggingBehavior.REQUESTS, "Response", "Response <Error>: %s", exception);
            createResponsesFromStream = constructErrorResponses(requests, connection, new FacebookException(exception));
        } catch (Throwable exception2) {
            Logger.log(LoggingBehavior.REQUESTS, "Response", "Response <Error>: %s", exception2);
            createResponsesFromStream = constructErrorResponses(requests, connection, new FacebookException(exception2));
        } catch (Throwable exception22) {
            Logger.log(LoggingBehavior.REQUESTS, "Response", "Response <Error>: %s", exception22);
            createResponsesFromStream = constructErrorResponses(requests, connection, new FacebookException(exception22));
        } finally {
            Utility.closeQuietly(stream);
        }
        return createResponsesFromStream;
    }

    static List<GraphResponse> createResponsesFromStream(InputStream stream, HttpURLConnection connection, GraphRequestBatch requests) throws FacebookException, JSONException, IOException {
        Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, "Response", "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf(Utility.readStreamToString(stream).length()), responseString);
        return createResponsesFromString(Utility.readStreamToString(stream), connection, requests);
    }

    static List<GraphResponse> createResponsesFromString(String responseString, HttpURLConnection connection, GraphRequestBatch requests) throws FacebookException, JSONException, IOException {
        Logger.log(LoggingBehavior.REQUESTS, "Response", "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", requests.getId(), Integer.valueOf(responseString.length()), createResponsesFromObject(connection, requests, new JSONTokener(responseString).nextValue()));
        return createResponsesFromObject(connection, requests, new JSONTokener(responseString).nextValue());
    }

    private static List<GraphResponse> createResponsesFromObject(HttpURLConnection connection, List<GraphRequest> requests, Object object) throws FacebookException, JSONException {
        GraphRequest request;
        int numRequests = requests.size();
        List<GraphResponse> responses = new ArrayList(numRequests);
        Object originalResult = object;
        if (numRequests == 1) {
            request = (GraphRequest) requests.get(0);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("body", object);
                jsonObject.put("code", connection != null ? connection.getResponseCode() : 200);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);
                object = jsonArray;
            } catch (JSONException e) {
                responses.add(new GraphResponse(request, connection, new FacebookRequestError(connection, e)));
            } catch (IOException e2) {
                responses.add(new GraphResponse(request, connection, new FacebookRequestError(connection, e2)));
            }
        }
        if ((object instanceof JSONArray) && ((JSONArray) object).length() == numRequests) {
            jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.length(); i++) {
                request = (GraphRequest) requests.get(i);
                try {
                    responses.add(createResponseFromObject(request, connection, jsonArray.get(i), originalResult));
                } catch (JSONException e3) {
                    responses.add(new GraphResponse(request, connection, new FacebookRequestError(connection, e3)));
                } catch (FacebookException e4) {
                    responses.add(new GraphResponse(request, connection, new FacebookRequestError(connection, e4)));
                }
            }
            return responses;
        }
        throw new FacebookException("Unexpected number of results");
    }

    private static GraphResponse createResponseFromObject(GraphRequest request, HttpURLConnection connection, Object object, Object originalResult) throws JSONException {
        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            FacebookRequestError error = FacebookRequestError.checkResponseAndCreateError(jsonObject, originalResult, connection);
            if (error != null) {
                if (error.getErrorCode() == 190 && Utility.isCurrentAccessToken(request.getAccessToken())) {
                    AccessToken.setCurrentAccessToken(null);
                }
                return new GraphResponse(request, connection, error);
            }
            Object body = Utility.getStringPropertyAsJSON(jsonObject, "body", "FACEBOOK_NON_JSON_RESULT");
            if (body instanceof JSONObject) {
                return new GraphResponse(request, connection, body.toString(), (JSONObject) body);
            }
            if (body instanceof JSONArray) {
                return new GraphResponse(request, connection, body.toString(), (JSONArray) body);
            }
            object = JSONObject.NULL;
        }
        if (object == JSONObject.NULL) {
            return new GraphResponse(request, connection, object.toString(), (JSONObject) null);
        }
        throw new FacebookException("Got unexpected object type in response, class: " + object.getClass().getSimpleName());
    }

    static List<GraphResponse> constructErrorResponses(List<GraphRequest> requests, HttpURLConnection connection, FacebookException error) {
        int count = requests.size();
        List<GraphResponse> responses = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            responses.add(new GraphResponse((GraphRequest) requests.get(i), connection, new FacebookRequestError(connection, error)));
        }
        return responses;
    }
}
