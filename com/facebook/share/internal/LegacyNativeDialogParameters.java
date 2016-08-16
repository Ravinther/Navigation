package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class LegacyNativeDialogParameters {
    public static Bundle create(UUID callId, ShareContent shareContent, boolean shouldFailOnDataError) {
        Validate.notNull(shareContent, "shareContent");
        Validate.notNull(callId, "callId");
        if (shareContent instanceof ShareLinkContent) {
            return create((ShareLinkContent) shareContent, shouldFailOnDataError);
        }
        if (shareContent instanceof SharePhotoContent) {
            SharePhotoContent photoContent = (SharePhotoContent) shareContent;
            return create(photoContent, ShareInternalUtility.getPhotoUrls(photoContent, callId), shouldFailOnDataError);
        } else if (shareContent instanceof ShareVideoContent) {
            return create((ShareVideoContent) shareContent, shouldFailOnDataError);
        } else {
            if (!(shareContent instanceof ShareOpenGraphContent)) {
                return null;
            }
            ShareOpenGraphContent openGraphContent = (ShareOpenGraphContent) shareContent;
            try {
                return create(openGraphContent, ShareInternalUtility.toJSONObjectForCall(callId, openGraphContent), shouldFailOnDataError);
            } catch (JSONException e) {
                throw new FacebookException("Unable to create a JSON Object from the provided ShareOpenGraphContent: " + e.getMessage());
            }
        }
    }

    private static Bundle create(ShareLinkContent linkContent, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(linkContent, dataErrorsFatal);
        Utility.putNonEmptyString(params, "com.facebook.platform.extra.TITLE", linkContent.getContentTitle());
        Utility.putNonEmptyString(params, "com.facebook.platform.extra.DESCRIPTION", linkContent.getContentDescription());
        Utility.putUri(params, "com.facebook.platform.extra.IMAGE", linkContent.getImageUrl());
        return params;
    }

    private static Bundle create(SharePhotoContent photoContent, List<String> imageUrls, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(photoContent, dataErrorsFatal);
        params.putStringArrayList("com.facebook.platform.extra.PHOTOS", new ArrayList(imageUrls));
        return params;
    }

    private static Bundle create(ShareVideoContent videoContent, boolean dataErrorsFatal) {
        return null;
    }

    private static Bundle create(ShareOpenGraphContent openGraphContent, JSONObject openGraphActionJSON, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(openGraphContent, dataErrorsFatal);
        Utility.putNonEmptyString(params, "com.facebook.platform.extra.PREVIEW_PROPERTY_NAME", openGraphContent.getPreviewPropertyName());
        Utility.putNonEmptyString(params, "com.facebook.platform.extra.ACTION_TYPE", openGraphContent.getAction().getActionType());
        Utility.putNonEmptyString(params, "com.facebook.platform.extra.ACTION", openGraphActionJSON.toString());
        return params;
    }

    private static Bundle createBaseParameters(ShareContent content, boolean dataErrorsFatal) {
        Bundle params = new Bundle();
        Utility.putUri(params, "com.facebook.platform.extra.LINK", content.getContentUrl());
        Utility.putNonEmptyString(params, "com.facebook.platform.extra.PLACE", content.getPlaceId());
        Utility.putNonEmptyString(params, "com.facebook.platform.extra.REF", content.getRef());
        params.putBoolean("com.facebook.platform.extra.DATA_FAILURES_FATAL", dataErrorsFatal);
        Collection peopleIds = content.getPeopleIds();
        if (!Utility.isNullOrEmpty(peopleIds)) {
            params.putStringArrayList("com.facebook.platform.extra.FRIENDS", new ArrayList(peopleIds));
        }
        return params;
    }
}
