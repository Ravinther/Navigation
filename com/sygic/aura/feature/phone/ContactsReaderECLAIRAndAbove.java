package com.sygic.aura.feature.phone;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Contacts;
import com.sygic.aura.clazz.ImageInfo;
import com.sygic.aura.utils.PictureData;
import java.io.File;
import java.io.InputStream;

/* compiled from: ContactsReader */
class ContactsReaderECLAIRAndAbove extends ContactsReaderInterface {
    private static final String[] CONTACTS_PROJECTION;
    private static final String[] CONTACTS_PROJECTION_FALLBACK;
    private Context mCtx;
    private Cursor m_curAddr;
    private Cursor m_curEmails;
    private Cursor m_curPhones;
    private Cursor m_curPhoto;
    private Cursor m_cursor;
    private String m_strAuraPhotoDir;

    static {
        CONTACTS_PROJECTION = new String[]{"contact_id", "display_name", "has_phone_number", "photo_id", "display_name_alt", "data2", "data7", "data10", "data9", "data4", "_id"};
        CONTACTS_PROJECTION_FALLBACK = new String[]{"contact_id", "display_name", "photo_id", "data2", "data7", "data10", "data9", "data4", "_id"};
    }

    public ContactsReaderECLAIRAndAbove(Context ctx) {
        this.m_strAuraPhotoDir = "aura_photo";
        this.mCtx = ctx;
    }

    public int GetCount() {
        if (this.m_cursor != null) {
            return this.m_cursor.getCount();
        }
        return 0;
    }

    public boolean Reset() {
        int i = 0;
        if (this.mCtx == null) {
            return false;
        }
        boolean ret = false;
        if (this.m_cursor == null) {
            String strSelect = "((display_name NOTNULL) AND (display_name != '' ) AND (data1 NOTNULL) and (data1 != '' ))";
            try {
                this.m_cursor = this.mCtx.getContentResolver().query(StructuredPostal.CONTENT_URI, CONTACTS_PROJECTION, strSelect, null, "display_name COLLATE NOCASE");
            } catch (IllegalArgumentException e) {
                this.m_cursor = this.mCtx.getContentResolver().query(StructuredPostal.CONTENT_URI, CONTACTS_PROJECTION_FALLBACK, strSelect, null, "display_name COLLATE NOCASE");
            }
        }
        if (this.m_cursor != null) {
            ret = this.m_cursor.moveToFirst();
        }
        if (this.m_curPhones != null) {
            this.m_curPhones.close();
            this.m_curPhones = null;
        }
        if (this.m_curEmails != null) {
            this.m_curEmails.close();
            this.m_curEmails = null;
        }
        if (this.m_curAddr != null) {
            this.m_curAddr.close();
            this.m_curAddr = null;
        }
        if (this.m_curPhoto != null) {
            this.m_curPhoto.close();
            this.m_curPhoto = null;
        }
        if (this.mCtx.getFilesDir() == null) {
            return ret;
        }
        File[] dir = new File(this.mCtx.getFilesDir().getAbsolutePath() + "/" + this.m_strAuraPhotoDir).listFiles();
        if (dir == null) {
            return ret;
        }
        int length = dir.length;
        while (i < length) {
            dir[i].delete();
            i++;
        }
        return ret;
    }

    public String ReadNext() {
        if (this.m_cursor == null || this.m_cursor.isAfterLast() || this.m_cursor.isBeforeFirst()) {
            return null;
        }
        String strAltName;
        String strRet = "";
        String strContactId = this.m_cursor.getString(this.m_cursor.getColumnIndex("contact_id"));
        String strName = this.m_cursor.getString(this.m_cursor.getColumnIndex("display_name"));
        String strPhotoId = this.m_cursor.getString(this.m_cursor.getColumnIndex("photo_id"));
        int index = this.m_cursor.getColumnIndex("has_phone_number");
        String strHasPhone = index >= 0 ? this.m_cursor.getString(index) : "1";
        index = this.m_cursor.getColumnIndex("display_name_alt");
        if (index >= 0) {
            strAltName = this.m_cursor.getString(index);
        } else {
            strAltName = strName;
        }
        if (strContactId == null) {
            return strRet;
        }
        strRet = (strRet + ContactsReaderInterface.delimiter() + 99 + ContactsReaderInterface.delimiter() + strContactId) + getAddressFromCursor(this.m_cursor);
        while (this.m_cursor.moveToNext() && this.m_cursor.getString(0).equals(strContactId)) {
            strRet = strRet + getAddressFromCursor(this.m_cursor);
        }
        if (strName != null) {
            strRet = strRet + ContactsReaderInterface.delimiter() + 1 + ContactsReaderInterface.delimiter() + strName;
        }
        String strEmail = getEmail(strContactId);
        if (strEmail != null) {
            strRet = strRet + ContactsReaderInterface.delimiter() + 2 + ContactsReaderInterface.delimiter() + strEmail;
        }
        if (strAltName != null) {
            strRet = strRet + ContactsReaderInterface.delimiter() + 5 + ContactsReaderInterface.delimiter() + strAltName;
        }
        if ("1".equals(strHasPhone)) {
            strRet = strRet + getPhones(strContactId);
        }
        if (strPhotoId != null) {
            strRet = strRet + ContactsReaderInterface.delimiter() + 4 + ContactsReaderInterface.delimiter() + strPhotoId;
        }
        return strRet.replace("\n", " ");
    }

    String ReadPhoto(int nPhotoId) {
        InputStream stream = Contacts.openContactPhotoInputStream(this.mCtx.getContentResolver(), ContentUris.withAppendedId(Contacts.CONTENT_URI, (long) nPhotoId));
        if (stream == null) {
            return null;
        }
        String strPathDir = this.mCtx.getFilesDir().getAbsolutePath() + "/" + this.m_strAuraPhotoDir;
        new File(strPathDir).mkdir();
        String strPath = strPathDir + "/" + System.currentTimeMillis() + ".jpg";
        ImageInfo image = new ImageInfo();
        image.setImage(strPath, 64);
        PictureData.makePicture(stream, image);
        return strPath;
    }

    public String GetContact(String strContactId) {
        return getAddr(strContactId, -1);
    }

    protected String getAddr(String strContactId, int nAddrId) {
        if (this.m_curAddr == null) {
            this.m_curAddr = this.mCtx.getContentResolver().query(StructuredPostal.CONTENT_URI, new String[]{"contact_id", "display_name", "data2", "data7", "data10", "data9", "data4", "_id"}, null, null, null);
        }
        String strRet = "";
        if (this.m_curAddr != null && this.m_curAddr.moveToFirst()) {
            do {
                String strId;
                if (nAddrId != -1) {
                    strContactId = String.valueOf(nAddrId);
                    strId = this.m_curAddr.getString(this.m_curAddr.getColumnIndex("_id"));
                } else {
                    strId = this.m_curAddr.getString(this.m_curAddr.getColumnIndex("contact_id"));
                }
                if (strId.compareTo(strContactId) == 0) {
                    strRet = strRet + getAddressFromCursor(this.m_curAddr);
                    if (nAddrId != -1) {
                        break;
                    }
                }
            } while (this.m_curAddr.moveToNext());
            this.m_curAddr.moveToFirst();
        }
        return strRet;
    }

    private String getAddressFromCursor(Cursor cursor) {
        String result = "";
        String strName = cursor.getString(cursor.getColumnIndex("display_name"));
        String strCity = cursor.getString(cursor.getColumnIndex("data7"));
        String strCountry = cursor.getString(cursor.getColumnIndex("data10"));
        String strPostCode = cursor.getString(cursor.getColumnIndex("data9"));
        String strStreet = cursor.getString(cursor.getColumnIndex("data4"));
        int id = 0;
        if (cursor.getInt(cursor.getColumnIndex("data2")) != 1) {
            id = 10;
        }
        if (strName != null && strName.length() > 0) {
            result = result + ContactsReaderInterface.delimiter() + 1 + ContactsReaderInterface.delimiter() + strName;
        }
        if (strCity != null && strCity.length() > 0) {
            result = result + ContactsReaderInterface.delimiter() + (id + 22) + ContactsReaderInterface.delimiter() + strCity;
        }
        if (strCountry != null && strCountry.length() > 0) {
            result = result + ContactsReaderInterface.delimiter() + (id + 23) + ContactsReaderInterface.delimiter() + strCountry;
        }
        if (strPostCode != null && strPostCode.length() > 0) {
            result = result + ContactsReaderInterface.delimiter() + (id + 25) + ContactsReaderInterface.delimiter() + strPostCode;
        }
        if (strStreet != null && strStreet.length() > 0) {
            result = result + ContactsReaderInterface.delimiter() + (id + 20) + ContactsReaderInterface.delimiter() + strStreet;
        }
        return result + ContactsReaderInterface.delimiter() + 97;
    }

    protected String getEmail(String strContactId) {
        if (this.m_curEmails == null) {
            this.m_curEmails = this.mCtx.getContentResolver().query(Email.CONTENT_URI, new String[]{"contact_id", "data1"}, null, null, null);
        }
        if (!this.m_curEmails.moveToFirst()) {
            return null;
        }
        while (!this.m_curEmails.getString(this.m_curEmails.getColumnIndex("contact_id")).equals(strContactId)) {
            if (!this.m_curEmails.moveToNext()) {
                this.m_curEmails.moveToFirst();
                return null;
            }
        }
        String emailAddress = this.m_curEmails.getString(this.m_curEmails.getColumnIndex("data1"));
        this.m_curEmails.moveToFirst();
        return emailAddress;
    }

    protected String getPhones(String strContactId) {
        String strRet = "";
        if (this.m_curPhones == null) {
            this.m_curPhones = this.mCtx.getContentResolver().query(Phone.CONTENT_URI, new String[]{"contact_id", "data1", "data2"}, null, null, null);
        }
        if (this.m_curPhones.moveToFirst()) {
            do {
                if (this.m_curPhones.getString(this.m_curPhones.getColumnIndex("contact_id")).equals(strContactId)) {
                    String phoneNumber = this.m_curPhones.getString(this.m_curPhones.getColumnIndex("data1"));
                    int type = Integer.parseInt(this.m_curPhones.getString(this.m_curPhones.getColumnIndex("data2")));
                    if (type == 2) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 10 + ContactsReaderInterface.delimiter() + phoneNumber;
                    } else if (type == 1) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 11 + ContactsReaderInterface.delimiter() + phoneNumber;
                    } else if (type == 9) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 14 + ContactsReaderInterface.delimiter() + phoneNumber;
                    } else if (type == 12) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 13 + ContactsReaderInterface.delimiter() + phoneNumber;
                    } else if (type == 3) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 12 + ContactsReaderInterface.delimiter() + phoneNumber;
                    } else if (type == 17) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 16 + ContactsReaderInterface.delimiter() + phoneNumber;
                    } else if (type == 0) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 15 + ContactsReaderInterface.delimiter() + phoneNumber;
                    } else {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 10 + ContactsReaderInterface.delimiter() + phoneNumber;
                    }
                }
            } while (this.m_curPhones.moveToNext());
            this.m_curPhones.moveToFirst();
        }
        return strRet;
    }
}
