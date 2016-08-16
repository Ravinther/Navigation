package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import org.json.JSONObject;

public class WebDialogParameters {
    public static Bundle create(ShareLinkContent shareLinkContent) {
        Bundle params = new Bundle();
        Utility.putUri(params, "href", shareLinkContent.getContentUrl());
        return params;
    }

    public static Bundle create(ShareOpenGraphContent shareOpenGraphContent) {
        Bundle params = new Bundle();
        Utility.putNonEmptyString(params, "action_type", shareOpenGraphContent.getAction().getActionType());
        try {
            JSONObject ogJSON = ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForWeb(shareOpenGraphContent), false);
            if (ogJSON != null) {
                Utility.putNonEmptyString(params, "action_properties", ogJSON.toString());
            }
            return params;
        } catch (Throwable e) {
            throw new FacebookException("Unable to serialize the ShareOpenGraphContent to JSON", e);
        }
    }

    public static Bundle createForFeed(ShareLinkContent shareLinkContent) {
        Bundle webParams = new Bundle();
        Utility.putNonEmptyString(webParams, "name", shareLinkContent.getContentTitle());
        Utility.putNonEmptyString(webParams, "description", shareLinkContent.getContentDescription());
        Utility.putNonEmptyString(webParams, "link", Utility.getUriString(shareLinkContent.getContentUrl()));
        Utility.putNonEmptyString(webParams, "picture", Utility.getUriString(shareLinkContent.getImageUrl()));
        return webParams;
    }

    public static Bundle createForFeed(ShareFeedContent shareFeedContent) {
        Bundle webParams = new Bundle();
        Utility.putNonEmptyString(webParams, "to", shareFeedContent.getToId());
        Utility.putNonEmptyString(webParams, "link", shareFeedContent.getLink());
        Utility.putNonEmptyString(webParams, "picture", shareFeedContent.getPicture());
        Utility.putNonEmptyString(webParams, "source", shareFeedContent.getMediaSource());
        Utility.putNonEmptyString(webParams, "name", shareFeedContent.getLinkName());
        Utility.putNonEmptyString(webParams, "caption", shareFeedContent.getLinkCaption());
        Utility.putNonEmptyString(webParams, "description", shareFeedContent.getLinkDescription());
        return webParams;
    }
}
