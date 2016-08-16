package com.sygic.sdk.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sygic.aura.C1090R;
import loquendo.tts.engine.TTSConst;

public interface IRemoteSygicService extends IInterface {

    public static abstract class Stub extends Binder implements IRemoteSygicService {
        public Stub() {
            attachInterface(this, "com.sygic.sdk.aidl.IRemoteSygicService");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _result;
            Bundle _result2;
            Bundle _arg0;
            Bundle[] _result3;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result = isRunning();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    registerCallback(com.sygic.sdk.aidl.IRemoteServiceCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    unregisterCallback(com.sygic.sdk.aidl.IRemoteServiceCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    startNavi(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result = isAppRunning();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = isApplicationRunning(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = endApplication(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_TAG /*8*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getUniqueDeviceId(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = isApplicationInForeground(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getMapVersion(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getApplicationVersion(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = changeApplicationOptions(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    if (_arg0 != null) {
                        reply.writeInt(1);
                        _arg0.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = setDefaultValues(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getActualGpsPosition(data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getRouteInfo(data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_ERROR /*16*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = startNavigation(_arg0, data.readInt(), data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_JUMP /*17*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = stopNavigation(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = navigateToAddress(data.readString(), data.readInt() != 0, data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = loadComputedRoute(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_STYLECHANGE /*20*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = saveComputedRoute(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_PERSONACHANGE /*21*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = getFavoritesList(data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case TTSConst.TTSEVT_SAYASCHANGE /*22*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = addStopOffPointsToFavorites(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionBarDivider /*23*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = removeFavoriteFromList(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionBarItemBackground /*24*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = addStopOffPointToHistory(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionMenuTextAppearance /*25*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = getHistoryList(data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_actionMenuTextColor /*26*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = addPoi(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeStyle /*27*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = addPoiCategory(data.readString(), data.readString(), data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeCloseButtonStyle /*28*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = deletePoiCategory(data.readString(), data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeBackground /*29*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = deletePoi(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeSplitBackground /*30*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = makeUserPoiVisible(data.readInt(), data.readString(), data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeCloseDrawable /*31*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = getPoiList(data.readString(), data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_actionModeCutDrawable /*32*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = findNearbyPoi(data.readInt(), data.readString(), data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeCopyDrawable /*33*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = findNearbyPoiList(data.readInt(), data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_actionModePasteDrawable /*34*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = getPoiCategoryList(data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_actionModeSelectAllDrawable /*35*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = setPoiWarning(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeShareDrawable /*36*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getLocationAddressInfo(data.createIntArray(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeFindDrawable /*37*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getLocationRoadInfo(data.createIntArray(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModeWebSearchDrawable /*38*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = locationFromAddress(data.readString(), data.readInt() != 0, data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionModePopupWindowStyle /*39*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = locationFromAddressEx(data.readString(), data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_textAppearanceLargePopupMenu /*40*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = locationFromAddressEx2(data.readString(), data.readInt() != 0, data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_textAppearanceSmallPopupMenu /*41*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = showHierarchyDialog(data.readString(), data.readString(), data.readString(), data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_dialogTheme /*42*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = getAddressList(data.readString(), data.readInt() != 0, data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_dialogPreferredPadding /*43*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getCoordinatesFromOffset(data.readString(), data.readLong(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_listDividerAlertDialog /*44*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = switchMap(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionDropDownStyle /*45*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = showCoordinatesOnMap(data.createIntArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_dropdownListPreferredItemHeight /*46*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = showRectangleOnMap(data.createIntArray(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_spinnerDropDownItemStyle /*47*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = addBitmapToMap(data.readString(), data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_homeAsUpIndicator /*48*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = removeBitmap(data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_actionButtonStyle /*49*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = showBitmap(data.readInt(), data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_RESERVED /*50*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = moveBitmap(data.readInt(), data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_RESERVED_1 /*51*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = loadGFFile(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_RESERVED_2 /*52*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = unloadGFFile(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_selectableItemBackgroundBorderless /*53*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = onMenuCommand(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_borderlessButtonStyle /*54*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = showMessage(data.readString(), data.readInt(), data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_dividerVertical /*55*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = flashMessage(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_dividerHorizontal /*56*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = errorReport(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_activityChooserViewStyle /*57*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = closeDialogs(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_toolbarStyle /*58*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = addItinerary((Bundle[]) data.createTypedArray(Bundle.CREATOR), data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_toolbarNavigationButtonStyle /*59*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = setRoute(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_popupMenuStyle /*60*/:
                    Bundle _arg1;
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    String _arg02 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    _result2 = addEntryToItinerary(_arg02, _arg1, data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_popupWindowStyle /*61*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = deleteEntryInItinerary(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_editTextColor /*62*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = deleteItinerary(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_editTextBackground /*63*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result3 = getItineraryList(data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case C1090R.styleable.Theme_textAppearanceSearchResultTitle /*64*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = skipNextWaypoint(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_textAppearanceSearchResultSubtitle /*65*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = highlightPoi(data.readString(), data.readString(), data.readString(), data.createIntArray(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_textColorSearchUrl /*66*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = enableExternalGpsInput(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_searchViewStyle /*67*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = disableExternalGpsInput(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_listPreferredItemHeight /*68*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    int _result4 = sendGpsData(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case C1090R.styleable.Theme_listPreferredItemHeightSmall /*69*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = gpsSwitchOn(data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_listPreferredItemHeightLarge /*70*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getCurrentSpeedLimit(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_listPreferredItemPaddingLeft /*71*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = getNextInstruction(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_listPreferredItemPaddingRight /*72*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = goOnline(data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_dropDownListViewStyle /*73*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = onlineServicesLogin(data.readString(), data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_listPopupWindowStyle /*74*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = addTMCEvent(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_textAppearanceListItem /*75*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = removeTMCEvent(data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_textAppearanceListItemSmall /*76*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = clearTMCTable(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_panelBackground /*77*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = onlineServicesSettings(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_panelMenuListWidth /*78*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = playSoundTTS(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_panelMenuListTheme /*79*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = tripStart(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_listChoiceBackgroundIndicator /*80*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = tripAddUserEvent(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_colorPrimary /*81*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = tripEnd(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_colorPrimaryDark /*82*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = loadExternalFile(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_colorAccent /*83*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = unloadExternalFile(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case C1090R.styleable.Theme_colorControlNormal /*84*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteSygicService");
                    _result2 = reloadExternalFiles(data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 1598968902:
                    reply.writeString("com.sygic.sdk.aidl.IRemoteSygicService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    Bundle addBitmapToMap(String str, int i, int i2, int i3) throws RemoteException;

    Bundle addEntryToItinerary(String str, Bundle bundle, int i, int i2) throws RemoteException;

    Bundle addItinerary(Bundle[] bundleArr, String str, int i) throws RemoteException;

    Bundle addPoi(Bundle bundle, int i) throws RemoteException;

    Bundle addPoiCategory(String str, String str2, String str3, int i) throws RemoteException;

    Bundle addStopOffPointToHistory(Bundle bundle, int i) throws RemoteException;

    Bundle addStopOffPointsToFavorites(Bundle bundle, int i) throws RemoteException;

    Bundle addTMCEvent(Bundle bundle, int i) throws RemoteException;

    Bundle changeApplicationOptions(Bundle bundle, int i) throws RemoteException;

    Bundle clearTMCTable(int i) throws RemoteException;

    Bundle closeDialogs(int i) throws RemoteException;

    Bundle deleteEntryInItinerary(String str, int i, int i2) throws RemoteException;

    Bundle deleteItinerary(String str, int i) throws RemoteException;

    Bundle deletePoi(Bundle bundle, int i) throws RemoteException;

    Bundle deletePoiCategory(String str, String str2, int i) throws RemoteException;

    Bundle disableExternalGpsInput(int i) throws RemoteException;

    Bundle enableExternalGpsInput(int i) throws RemoteException;

    Bundle endApplication(int i) throws RemoteException;

    Bundle errorReport(int i) throws RemoteException;

    Bundle findNearbyPoi(int i, String str, int i2, int i3, int i4) throws RemoteException;

    Bundle[] findNearbyPoiList(int i, String str, int i2, int i3, int i4, int i5) throws RemoteException;

    Bundle flashMessage(String str, int i) throws RemoteException;

    Bundle getActualGpsPosition(boolean z, int i) throws RemoteException;

    Bundle[] getAddressList(String str, boolean z, int i, int i2) throws RemoteException;

    Bundle getApplicationVersion(int i) throws RemoteException;

    Bundle getCoordinatesFromOffset(String str, long j, int i) throws RemoteException;

    Bundle getCurrentSpeedLimit(int i) throws RemoteException;

    Bundle[] getFavoritesList(boolean z, int i) throws RemoteException;

    Bundle[] getHistoryList(boolean z, int i) throws RemoteException;

    Bundle[] getItineraryList(String str, int i) throws RemoteException;

    Bundle getLocationAddressInfo(int[] iArr, int i) throws RemoteException;

    Bundle getLocationRoadInfo(int[] iArr, int i) throws RemoteException;

    Bundle getMapVersion(String str, int i) throws RemoteException;

    Bundle getNextInstruction(int i) throws RemoteException;

    Bundle[] getPoiCategoryList(int i) throws RemoteException;

    Bundle[] getPoiList(String str, boolean z, int i) throws RemoteException;

    Bundle getRouteInfo(boolean z, int i) throws RemoteException;

    Bundle getUniqueDeviceId(int i) throws RemoteException;

    Bundle goOnline(boolean z, int i) throws RemoteException;

    Bundle gpsSwitchOn(boolean z, int i) throws RemoteException;

    Bundle highlightPoi(String str, String str2, String str3, int[] iArr, int i) throws RemoteException;

    boolean isAppRunning() throws RemoteException;

    Bundle isApplicationInForeground(int i) throws RemoteException;

    Bundle isApplicationRunning(int i) throws RemoteException;

    boolean isRunning() throws RemoteException;

    Bundle loadComputedRoute(String str, int i, int i2) throws RemoteException;

    Bundle loadExternalFile(String str, int i, int i2) throws RemoteException;

    Bundle loadGFFile(String str, int i) throws RemoteException;

    Bundle locationFromAddress(String str, boolean z, boolean z2, int i) throws RemoteException;

    Bundle[] locationFromAddressEx(String str, boolean z, int i) throws RemoteException;

    Bundle[] locationFromAddressEx2(String str, boolean z, boolean z2, int i) throws RemoteException;

    Bundle makeUserPoiVisible(int i, String str, boolean z, int i2) throws RemoteException;

    Bundle moveBitmap(int i, int i2, int i3, int i4) throws RemoteException;

    Bundle navigateToAddress(String str, boolean z, int i, int i2) throws RemoteException;

    Bundle onMenuCommand(int i, int i2, int i3) throws RemoteException;

    Bundle onlineServicesLogin(String str, String str2, int i) throws RemoteException;

    Bundle onlineServicesSettings(int i) throws RemoteException;

    Bundle playSoundTTS(String str, int i) throws RemoteException;

    void registerCallback(IRemoteServiceCallback iRemoteServiceCallback) throws RemoteException;

    Bundle reloadExternalFiles(int i) throws RemoteException;

    Bundle removeBitmap(int i, int i2) throws RemoteException;

    Bundle removeFavoriteFromList(Bundle bundle, int i) throws RemoteException;

    Bundle removeTMCEvent(int i, int i2) throws RemoteException;

    Bundle saveComputedRoute(String str, int i) throws RemoteException;

    int sendGpsData(String str) throws RemoteException;

    Bundle setDefaultValues(int i) throws RemoteException;

    Bundle setPoiWarning(Bundle bundle, int i) throws RemoteException;

    Bundle setRoute(String str, int i, int i2) throws RemoteException;

    Bundle showBitmap(int i, boolean z, int i2) throws RemoteException;

    Bundle showCoordinatesOnMap(int[] iArr, int i, int i2) throws RemoteException;

    Bundle showHierarchyDialog(String str, String str2, String str3, boolean z, int i) throws RemoteException;

    Bundle showMessage(String str, int i, boolean z, int i2) throws RemoteException;

    Bundle showRectangleOnMap(int[] iArr, int i) throws RemoteException;

    Bundle skipNextWaypoint(int i) throws RemoteException;

    void startNavi(boolean z) throws RemoteException;

    Bundle startNavigation(Bundle bundle, int i, boolean z, int i2) throws RemoteException;

    Bundle stopNavigation(int i) throws RemoteException;

    Bundle switchMap(String str, int i) throws RemoteException;

    Bundle tripAddUserEvent(String str, int i, int i2) throws RemoteException;

    Bundle tripEnd(int i) throws RemoteException;

    Bundle tripStart(String str, int i, int i2) throws RemoteException;

    Bundle unloadExternalFile(String str, int i, int i2) throws RemoteException;

    Bundle unloadGFFile(String str, int i) throws RemoteException;

    void unregisterCallback(IRemoteServiceCallback iRemoteServiceCallback) throws RemoteException;
}
