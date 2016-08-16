package com.facebook.share.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.NativeAppCallAttachmentStore.Attachment;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.Mapper;
import com.facebook.share.internal.OpenGraphJSONUtility.PhotoJSONProcessor;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareInternalUtility {

    /* renamed from: com.facebook.share.internal.ShareInternalUtility.2 */
    static class C03882 implements Callback {
        final /* synthetic */ int val$requestCode;

        C03882(int i) {
            this.val$requestCode = i;
        }
    }

    /* renamed from: com.facebook.share.internal.ShareInternalUtility.4 */
    static class C03894 implements Mapper<SharePhoto, Attachment> {
        final /* synthetic */ UUID val$appCallId;

        C03894(UUID uuid) {
            this.val$appCallId = uuid;
        }

        public Attachment apply(SharePhoto item) {
            return ShareInternalUtility.getAttachment(this.val$appCallId, item);
        }
    }

    /* renamed from: com.facebook.share.internal.ShareInternalUtility.5 */
    static class C03905 implements Mapper<Attachment, String> {
        C03905() {
        }

        public String apply(Attachment item) {
            return item.getAttachmentUrl();
        }
    }

    /* renamed from: com.facebook.share.internal.ShareInternalUtility.6 */
    static class C03916 implements PhotoJSONProcessor {
        final /* synthetic */ ArrayList val$attachments;
        final /* synthetic */ UUID val$callId;

        C03916(UUID uuid, ArrayList arrayList) {
            this.val$callId = uuid;
            this.val$attachments = arrayList;
        }

        public JSONObject toJSONObject(SharePhoto photo) {
            Attachment attachment = ShareInternalUtility.getAttachment(this.val$callId, photo);
            if (attachment == null) {
                return null;
            }
            this.val$attachments.add(attachment);
            JSONObject photoJSONObject = new JSONObject();
            try {
                photoJSONObject.put("url", attachment.getAttachmentUrl());
                if (!photo.getUserGenerated()) {
                    return photoJSONObject;
                }
                photoJSONObject.put("user_generated", true);
                return photoJSONObject;
            } catch (Throwable e) {
                throw new FacebookException("Unable to attach images", e);
            }
        }
    }

    /* renamed from: com.facebook.share.internal.ShareInternalUtility.7 */
    static class C03927 implements PhotoJSONProcessor {
        C03927() {
        }

        public JSONObject toJSONObject(SharePhoto photo) {
            Uri photoUri = photo.getImageUrl();
            JSONObject photoJSONObject = new JSONObject();
            try {
                photoJSONObject.put("url", photoUri.toString());
                return photoJSONObject;
            } catch (Throwable e) {
                throw new FacebookException("Unable to attach images", e);
            }
        }
    }

    public static void registerStaticShareCallback(int requestCode) {
        CallbackManagerImpl.registerStaticCallback(requestCode, new C03882(requestCode));
    }

    public static List<String> getPhotoUrls(SharePhotoContent photoContent, UUID appCallId) {
        if (photoContent != null) {
            List<SharePhoto> photos = photoContent.getPhotos();
            if (photos != null) {
                List<Attachment> attachments = Utility.map(photos, new C03894(appCallId));
                List<String> attachmentUrls = Utility.map(attachments, new C03905());
                NativeAppCallAttachmentStore.addAttachments(attachments);
                return attachmentUrls;
            }
        }
        return null;
    }

    public static String getVideoUrl(ShareVideoContent videoContent, UUID appCallId) {
        if (videoContent == null || videoContent.getVideo() == null) {
            return null;
        }
        Attachment attachment = NativeAppCallAttachmentStore.createAttachment(appCallId, videoContent.getVideo().getLocalUrl());
        ArrayList<Attachment> attachments = new ArrayList(1);
        attachments.add(attachment);
        NativeAppCallAttachmentStore.addAttachments(attachments);
        return attachment.getAttachmentUrl();
    }

    public static JSONObject toJSONObjectForCall(UUID callId, ShareOpenGraphContent content) throws JSONException {
        ShareOpenGraphAction action = content.getAction();
        ArrayList<Attachment> attachments = new ArrayList();
        JSONObject actionJSON = OpenGraphJSONUtility.toJSONObject(action, new C03916(callId, attachments));
        NativeAppCallAttachmentStore.addAttachments(attachments);
        if (content.getPlaceId() != null && Utility.isNullOrEmpty(actionJSON.optString("place"))) {
            actionJSON.put("place", content.getPlaceId());
        }
        if (content.getPeopleIds() != null) {
            Set<String> peopleIdSet;
            JSONArray peopleTags = actionJSON.optJSONArray("tags");
            if (peopleTags == null) {
                peopleIdSet = new HashSet();
            } else {
                peopleIdSet = Utility.jsonArrayToSet(peopleTags);
            }
            for (String peopleId : content.getPeopleIds()) {
                peopleIdSet.add(peopleId);
            }
            actionJSON.put("tags", new ArrayList(peopleIdSet));
        }
        return actionJSON;
    }

    public static JSONObject toJSONObjectForWeb(ShareOpenGraphContent shareOpenGraphContent) throws JSONException {
        return OpenGraphJSONUtility.toJSONObject(shareOpenGraphContent.getAction(), new C03927());
    }

    public static JSONArray removeNamespacesFromOGJsonArray(JSONArray jsonArray, boolean requireNamespace) throws JSONException {
        JSONArray newArray = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONArray) {
                value = removeNamespacesFromOGJsonArray((JSONArray) value, requireNamespace);
            } else if (value instanceof JSONObject) {
                value = removeNamespacesFromOGJsonObject((JSONObject) value, requireNamespace);
            }
            newArray.put(value);
        }
        return newArray;
    }

    public static JSONObject removeNamespacesFromOGJsonObject(JSONObject jsonObject, boolean requireNamespace) {
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONObject newJsonObject = new JSONObject();
            JSONObject data = new JSONObject();
            JSONArray names = jsonObject.names();
            for (int i = 0; i < names.length(); i++) {
                String key = names.getString(i);
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    value = removeNamespacesFromOGJsonObject((JSONObject) value, true);
                } else if (value instanceof JSONArray) {
                    value = removeNamespacesFromOGJsonArray((JSONArray) value, true);
                }
                Pair<String, String> fieldNameAndNamespace = getFieldNameAndNamespaceFromFullName(key);
                String namespace = fieldNameAndNamespace.first;
                String fieldName = fieldNameAndNamespace.second;
                if (requireNamespace) {
                    if (namespace != null && namespace.equals("fbsdk")) {
                        newJsonObject.put(key, value);
                    } else if (namespace == null || namespace.equals("og")) {
                        newJsonObject.put(fieldName, value);
                    } else {
                        data.put(fieldName, value);
                    }
                } else if (namespace == null || !namespace.equals("fb")) {
                    newJsonObject.put(fieldName, value);
                } else {
                    newJsonObject.put(key, value);
                }
            }
            if (data.length() <= 0) {
                return newJsonObject;
            }
            newJsonObject.put(PoiFragment.ARG_DATA, data);
            return newJsonObject;
        } catch (JSONException e) {
            throw new FacebookException("Failed to create json object from share content");
        }
    }

    public static Pair<String, String> getFieldNameAndNamespaceFromFullName(String fullName) {
        String fieldName;
        String namespace = null;
        int index = fullName.indexOf(58);
        if (index == -1 || fullName.length() <= index + 1) {
            fieldName = fullName;
        } else {
            namespace = fullName.substring(0, index);
            fieldName = fullName.substring(index + 1);
        }
        return new Pair(namespace, fieldName);
    }

    private static Attachment getAttachment(UUID callId, SharePhoto photo) {
        Bitmap bitmap = photo.getBitmap();
        Uri photoUri = photo.getImageUrl();
        if (bitmap != null) {
            return NativeAppCallAttachmentStore.createAttachment(callId, bitmap);
        }
        if (photoUri != null) {
            return NativeAppCallAttachmentStore.createAttachment(callId, photoUri);
        }
        return null;
    }
}
