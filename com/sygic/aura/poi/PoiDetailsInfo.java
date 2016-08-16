package com.sygic.aura.poi;

import com.sygic.aura.SygicProject;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.OnlinePoiInfoEntry.EItemType;

public class PoiDetailsInfo {

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.10 */
    static class AnonymousClass10 implements Callback<String> {
        final /* synthetic */ long val$longPosition;

        AnonymousClass10(long j) {
            this.val$longPosition = j;
        }

        public String getMethod() {
            return PoiDetailsInfo.GetAddressFromLongposition(this.val$longPosition);
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.11 */
    static class AnonymousClass11 implements Callback<RupiData> {
        final /* synthetic */ MapSelection val$mapSelection;

        AnonymousClass11(MapSelection mapSelection) {
            this.val$mapSelection = mapSelection;
        }

        public RupiData getMethod() {
            return PoiDetailsInfo.GetDetailsFromRupi(this.val$mapSelection.getPosition().toNativeLong(), this.val$mapSelection.getNavSelType().getValue(), this.val$mapSelection.getData());
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.12 */
    static class AnonymousClass12 implements Callback<Boolean> {
        final /* synthetic */ EItemType val$filterType;
        final /* synthetic */ MapSelection val$mapSelection;

        AnonymousClass12(MapSelection mapSelection, EItemType eItemType) {
            this.val$mapSelection = mapSelection;
            this.val$filterType = eItemType;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PoiDetailsInfo.QueryOnlineInfo(this.val$mapSelection.getPosition().toNativeLong(), this.val$mapSelection.getNavSelType().getValue(), this.val$mapSelection.getData(), this.val$filterType.getValue()));
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.1 */
    static class C14191 implements Callback<String> {
        final /* synthetic */ long val$lPoiId;

        C14191(long j) {
            this.val$lPoiId = j;
        }

        public String getMethod() {
            return PoiDetailsInfo.GetPoiName(this.val$lPoiId);
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.2 */
    static class C14202 implements Callback<Long> {
        final /* synthetic */ long val$lPoiId;

        C14202(long j) {
            this.val$lPoiId = j;
        }

        public Long getMethod() {
            return Long.valueOf(PoiDetailsInfo.GetPoiDistance(this.val$lPoiId));
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.3 */
    static class C14213 implements Callback<Integer> {
        final /* synthetic */ long val$lPoiId;

        C14213(long j) {
            this.val$lPoiId = j;
        }

        public Integer getMethod() {
            return Integer.valueOf(PoiDetailsInfo.GetPoiRating(this.val$lPoiId));
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.4 */
    static class C14224 implements Callback<String> {
        final /* synthetic */ PoiAttributes val$iAttrId;

        C14224(PoiAttributes poiAttributes) {
            this.val$iAttrId = poiAttributes;
        }

        public String getMethod() {
            return PoiDetailsInfo.GetPoiAttrStrId(this.val$iAttrId.getValue());
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.5 */
    static class C14235 implements Callback<String> {
        final /* synthetic */ PoiAttributes val$iAttrId;

        C14235(PoiAttributes poiAttributes) {
            this.val$iAttrId = poiAttributes;
        }

        public String getMethod() {
            return PoiDetailsInfo.GetPoiAttrName(this.val$iAttrId.getValue());
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.6 */
    static class C14246 implements Callback<String> {
        final /* synthetic */ PoiAttributes val$iAttrId;
        final /* synthetic */ long val$lPoiId;

        C14246(long j, PoiAttributes poiAttributes) {
            this.val$lPoiId = j;
            this.val$iAttrId = poiAttributes;
        }

        public String getMethod() {
            return PoiDetailsInfo.GetPoiDetail(this.val$lPoiId, this.val$iAttrId.getValue());
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.7 */
    static class C14257 implements Callback<Integer> {
        final /* synthetic */ long val$lPoiId;

        C14257(long j) {
            this.val$lPoiId = j;
        }

        public Integer getMethod() {
            return Integer.valueOf(PoiDetailsInfo.GetPoiCategory(this.val$lPoiId));
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.8 */
    static class C14268 implements Callback<MapSelection> {
        final /* synthetic */ long val$lPoiId;

        C14268(long j) {
            this.val$lPoiId = j;
        }

        public MapSelection getMethod() {
            return PoiDetailsInfo.GetPoiSelection(this.val$lPoiId);
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiDetailsInfo.9 */
    static class C14279 implements Callback<MapSelection> {
        final /* synthetic */ long val$lPoiId;
        final /* synthetic */ LongPosition val$longPos;

        C14279(long j, LongPosition longPosition) {
            this.val$lPoiId = j;
            this.val$longPos = longPosition;
        }

        public MapSelection getMethod() {
            return PoiDetailsInfo.GetRupiPoiSelection(this.val$lPoiId, this.val$longPos.toNativeLong());
        }
    }

    public enum PoiAttributes {
        Id_Gen_Intro(2),
        Id_Gen_History(3),
        Id_Gen_WhereStay(4),
        Id_Gen_EatDrink(5),
        Id_Gen_Entertainment(6),
        Id_Gen_Tour(7),
        Id_Gen_GettingThere(8),
        Id_Gen_FunFacts(9),
        Id_Atr_Name(10),
        Id_Atr_AltName(11),
        Id_Atr_Address(12),
        Id_Atr_Phone(13),
        Id_Atr_Mail(14),
        Id_Atr_Url(15),
        Id_Atr_Fax(16),
        Id_Atr_Image(17),
        Id_Atr_ShortDesc(18),
        Id_Atr_LongDesc(19),
        Id_Atr_SubType(20),
        Id_Atr_OpenHours(21),
        Id_Atr_Costs(22),
        Id_Atr_BookAdvis(23),
        Id_Atr_CreditCards(24),
        Id_Atr_BrandNames(25),
        Id_Atr_NearTrain(26),
        Id_Atr_RoomCount(27),
        Id_Atr_Decor(28),
        Id_Atr_Breakfast(29),
        Id_Atr_Takeaways(30),
        Id_Atr_DisabledAccess(31),
        Id_Atr_HomeDelivery(32),
        Id_Atr_Conferences(33),
        Id_Atr_CheckInOut(34),
        Id_Atr_AccomodationType(35),
        Id_Atr_HotelServices(36),
        Id_Atr_SpecialFeatures(37),
        Id_Atr_SeasonDate(38),
        Id_Atr_RelevantPOIS(39),
        Id_Atr_Comments(40),
        Id_Atr_Rating(41),
        Id_Atr_NonhotelCosts(42),
        Id_Atr_Street(43),
        Id_Atr_Postal(44),
        Id_Atr_HouseNum(45),
        Id_Atr_City(46),
        Id_Atr_WikiDescription(47);
        
        final int value;

        private PoiAttributes(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class RupiData {
        public boolean mIsExtension;
        public String mStrAddress;
        public String mStrCmdParams;
        public String mStrCreditCards;
        public String mStrGuide;
        public String mStrIconPath;
        public String mStrMail;
        public String mStrPhone;
        public String mStrRatingPath;
        public String mStrReviews;
        public String mStrShortDesc;
        public String mStrTrain;
        public String mStrWeb;

        private RupiData(String strAddress, String strShortDesc, String strGuide, String strCreditCards, String strPhone, String strWeb, String strMail, String strTrain, boolean isExtension, String strIcon, String strRate, String strReview, String strCmdParam) {
            this.mStrAddress = strAddress;
            this.mStrShortDesc = strShortDesc;
            this.mStrGuide = strGuide;
            this.mStrCreditCards = strCreditCards;
            this.mStrPhone = strPhone;
            this.mStrWeb = strWeb;
            this.mStrMail = strMail;
            this.mStrTrain = strTrain;
            this.mIsExtension = isExtension;
            this.mStrIconPath = strIcon;
            this.mStrRatingPath = strRate;
            this.mStrReviews = strReview;
            this.mStrCmdParams = strCmdParam;
        }
    }

    private static native String GetAddressFromLongposition(long j);

    private static native RupiData GetDetailsFromRupi(long j, int i, long j2);

    private static native String GetPoiAttrName(int i);

    private static native String GetPoiAttrStrId(int i);

    private static native int GetPoiCategory(long j);

    private static native String GetPoiDetail(long j, int i);

    private static native long GetPoiDistance(long j);

    private static native String GetPoiName(long j);

    private static native int GetPoiRating(long j);

    private static native MapSelection GetPoiSelection(long j);

    private static native MapSelection GetRupiPoiSelection(long j, long j2);

    private static native boolean QueryOnlineInfo(long j, int i, long j2, int i2);

    public static String nativeGetPoiName(long lPoiId) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (String) new ObjectHandler(new C14191(lPoiId)).execute().get(null);
    }

    public static long nativeGetPoiDistance(long lPoiId) {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Long) new ObjectHandler(new C14202(lPoiId)).execute().get(Long.valueOf(0))).longValue();
    }

    public static int nativeGetPoiRating(long lPoiId) {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Integer) new ObjectHandler(new C14213(lPoiId)).execute().get(Integer.valueOf(0))).intValue();
    }

    public static String nativeGetPoiAttrStrId(PoiAttributes iAttrId) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (String) new ObjectHandler(new C14224(iAttrId)).execute().get(null);
    }

    public static String nativeGetPoiAttrName(PoiAttributes iAttrId) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (String) new ObjectHandler(new C14235(iAttrId)).execute().get(null);
    }

    public static String nativeGetPoiDetail(long lPoiId, PoiAttributes iAttrId) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (String) new ObjectHandler(new C14246(lPoiId, iAttrId)).execute().get(null);
    }

    public static int nativeGetPoiCategory(long lPoiId) {
        if (SygicProject.IS_PROTOTYPE) {
            return 23;
        }
        if (lPoiId != 0) {
            return ((Integer) new ObjectHandler(new C14257(lPoiId)).execute().get(Integer.valueOf(0))).intValue();
        }
        return 0;
    }

    public static MapSelection nativeGetPoiSelection(long lPoiId) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (MapSelection) new ObjectHandler(new C14268(lPoiId)).execute().get(MapSelection.Empty);
    }

    public static MapSelection nativeGetRupiPoiSelection(long lPoiId, LongPosition longPos) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (MapSelection) new ObjectHandler(new C14279(lPoiId, longPos)).execute().get(MapSelection.Empty);
    }

    public static String nativeGetAddressFromLongposition(long longPosition) {
        if (SygicProject.IS_PROTOTYPE) {
            return "Any Address";
        }
        return (String) new ObjectHandler(new AnonymousClass10(longPosition)).execute().get(null);
    }

    public static RupiData nativeGetDetailsFromRupi(MapSelection mapSelection) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (RupiData) new ObjectHandler(new AnonymousClass11(mapSelection)).execute().get(null);
    }

    public static boolean nativeQueryOnlineInfo(MapSelection mapSelection, EItemType filterType) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass12(mapSelection, filterType)).execute().get(Boolean.valueOf(false))).booleanValue();
    }
}
