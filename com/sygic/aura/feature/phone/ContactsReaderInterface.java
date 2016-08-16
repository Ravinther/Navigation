package com.sygic.aura.feature.phone;

/* compiled from: ContactsReader */
abstract class ContactsReaderInterface {
    abstract String GetContact(String str);

    abstract int GetCount();

    abstract String ReadNext();

    abstract String ReadPhoto(int i);

    abstract boolean Reset();

    ContactsReaderInterface() {
    }

    protected static String delimiter() {
        return "\uffff";
    }
}
