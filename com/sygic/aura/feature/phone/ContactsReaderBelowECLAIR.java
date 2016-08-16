package com.sygic.aura.feature.phone;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts.ContactMethods;
import android.provider.Contacts.People;
import com.sygic.aura.poi.fragment.PoiFragment;

/* compiled from: ContactsReader */
class ContactsReaderBelowECLAIR extends ContactsReaderInterface {
    private Activity m_activity;
    private Cursor m_cur;

    public ContactsReaderBelowECLAIR(Activity activity) {
        this.m_cur = null;
        this.m_activity = null;
        this.m_activity = activity;
        if (this.m_activity != null) {
            this.m_cur = this.m_activity.getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        }
        if (this.m_cur != null) {
            this.m_activity.startManagingCursor(this.m_cur);
            this.m_cur.moveToFirst();
        }
    }

    public int GetCount() {
        if (this.m_cur != null) {
            return this.m_cur.getCount();
        }
        return 0;
    }

    public boolean Reset() {
        if (this.m_cur != null) {
            return this.m_cur.moveToFirst();
        }
        return false;
    }

    public String ReadNext() {
        String strContact = "";
        if (this.m_cur != null) {
            if (this.m_cur.isAfterLast()) {
                return null;
            }
            strContact = ((strContact + AddContact(this.m_cur, "name", 1)) + AddContact(this.m_cur, "number", 10)) + AddPostalContact(this.m_cur, ContactMethods.CONTENT_EMAIL_URI, 1, 2);
            String strAddress = AddPostalContact(this.m_cur, ContactMethods.CONTENT_URI, 2, 0);
            if (strAddress.length() > 3) {
                strAddress = strAddress.substring(3);
            }
            strContact = strContact + GetAddress(strAddress);
        }
        this.m_cur.moveToNext();
        return strContact;
    }

    String ReadPhoto(int nPhotoId) {
        return null;
    }

    String GetContact(String strContactId) {
        return null;
    }

    private String AddContact(Cursor cur, String strId, int nContactId) {
        String str = cur.getString(cur.getColumnIndex(strId));
        if (str.equals("-1")) {
            return "";
        }
        return ContactsReaderInterface.delimiter() + nContactId + ContactsReaderInterface.delimiter() + str;
    }

    private String AddPostalContact(Cursor cur, Uri uri, int nKind, int nContactId) {
        String str = "";
        Cursor curAddr = this.m_activity.getContentResolver().query(uri, null, "person==" + cur.getString(cur.getColumnIndex("_id")) + " AND " + "kind" + "==" + nKind, null, null);
        int nPostal = curAddr.getColumnIndex(PoiFragment.ARG_DATA);
        if (curAddr.moveToFirst()) {
            str = curAddr.getString(nPostal);
        }
        curAddr.close();
        if (str.equals("-1") || str.equals("")) {
            return "";
        }
        return ContactsReaderInterface.delimiter() + nContactId + ContactsReaderInterface.delimiter() + str;
    }

    public static String GetAddress(String strAddr) {
        String strRet = "";
        String[] strArr = strAddr.split("[,\n]");
        int nCount = strArr.length;
        if (nCount < 3) {
            return ContactsReaderInterface.delimiter() + 20 + ContactsReaderInterface.delimiter() + strAddr;
        }
        if (HasDigit(strArr[0])) {
            strRet = (strRet + ContactsReaderInterface.delimiter() + 20 + ContactsReaderInterface.delimiter() + GetStreet(strArr[0].trim()).trim()) + ContactsReaderInterface.delimiter() + 21 + ContactsReaderInterface.delimiter() + GetNum(strArr[0].trim());
        } else {
            strRet = strRet + ContactsReaderInterface.delimiter() + 20 + ContactsReaderInterface.delimiter() + strArr[0].trim();
        }
        strRet = strRet + ContactsReaderInterface.delimiter() + 23 + ContactsReaderInterface.delimiter() + strArr[nCount - 1].trim();
        boolean bCity = false;
        boolean bZip = false;
        boolean bState = false;
        for (int i = 1; i < nCount - 1; i++) {
            String string = strArr[i].trim();
            if (HasDigit(string)) {
                if (string.length() > 7) {
                    if (!bCity) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 22 + ContactsReaderInterface.delimiter() + GetCity(string).trim();
                        bCity = true;
                    }
                    if (!bZip) {
                        strRet = strRet + ContactsReaderInterface.delimiter() + 25 + ContactsReaderInterface.delimiter() + GetZip(string);
                        bZip = true;
                    }
                } else if (!bZip) {
                    strRet = strRet + ContactsReaderInterface.delimiter() + 25 + ContactsReaderInterface.delimiter() + string;
                    bZip = true;
                }
            } else if (!bCity) {
                strRet = strRet + ContactsReaderInterface.delimiter() + 22 + ContactsReaderInterface.delimiter() + string;
                bCity = true;
            } else if (!bState) {
                strRet = strRet + ContactsReaderInterface.delimiter() + 24 + ContactsReaderInterface.delimiter() + string;
                bState = true;
            }
        }
        return strRet;
    }

    private static boolean HasDigit(String string) {
        for (int j = 0; j < string.length(); j++) {
            if (Character.isDigit(string.charAt(j))) {
                return true;
            }
        }
        return false;
    }

    private static String GetCity(String string) {
        String strRet = "";
        for (String str : string.split(" ")) {
            if (!HasDigit(str)) {
                strRet = strRet + str + " ";
            }
        }
        return strRet;
    }

    private static String GetStreet(String string) {
        return string.replace(GetNum(string), "");
    }

    private static String GetZip(String string) {
        for (String str : string.split(" ")) {
            if (HasDigit(str)) {
                return str;
            }
        }
        return "";
    }

    private static String GetNum(String string) {
        String[] strArr = string.split(" ");
        int nLen = strArr.length;
        if (nLen > 0) {
            if (HasDigit(strArr[0])) {
                return strArr[0];
            }
            if (HasDigit(strArr[nLen - 1])) {
                return strArr[nLen - 1];
            }
        }
        return "";
    }
}
