package com.sygic.aura.fcd;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.sygic.aura.search.model.data.PoiListItem;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

public final class Utils {
    private static final Map<String, String> _countryCodes;

    static {
        _countryCodes = new HashMap();
        _countryCodes.put("AF", "AFG");
        _countryCodes.put("AL", "ALB");
        _countryCodes.put("DZ", "DZA");
        _countryCodes.put("AS", "ASM");
        _countryCodes.put("AD", "AND");
        _countryCodes.put("AO", "AGO");
        _countryCodes.put("AI", "AIA");
        _countryCodes.put("AQ", "ATA");
        _countryCodes.put("AG", "ATG");
        _countryCodes.put("AR", "ARG");
        _countryCodes.put("AM", "ARM");
        _countryCodes.put("AW", "ABW");
        _countryCodes.put("AU", "AUS");
        _countryCodes.put("AT", "AUT");
        _countryCodes.put("AZ", "AZE");
        _countryCodes.put("BS", "BHS");
        _countryCodes.put("BH", "BHR");
        _countryCodes.put("BD", "BGD");
        _countryCodes.put("BB", "BRB");
        _countryCodes.put("BY", "BLR");
        _countryCodes.put("BE", "BEL");
        _countryCodes.put("BZ", "BLZ");
        _countryCodes.put("BJ", "BEN");
        _countryCodes.put("BM", "BMU");
        _countryCodes.put("BT", "BTN");
        _countryCodes.put("BO", "BOL");
        _countryCodes.put("BA", "BIH");
        _countryCodes.put("BW", "BWA");
        _countryCodes.put("BV", "BVT");
        _countryCodes.put("BR", "BRA");
        _countryCodes.put("IO", "IOT");
        _countryCodes.put("VG", "VGB");
        _countryCodes.put("BN", "BRN");
        _countryCodes.put("BG", "BGR");
        _countryCodes.put("BF", "BFA");
        _countryCodes.put("MM", "MMR");
        _countryCodes.put("BI", "BDI");
        _countryCodes.put("CV", "CPV");
        _countryCodes.put("KH", "KHM");
        _countryCodes.put("CM", "CMR");
        _countryCodes.put("CA", "CAN");
        _countryCodes.put("KY", "CYM");
        _countryCodes.put("CF", "CAF");
        _countryCodes.put("TD", "TCD");
        _countryCodes.put("CL", "CHL");
        _countryCodes.put("CN", "CHN");
        _countryCodes.put("CX", "CXR");
        _countryCodes.put("CC", "CCK");
        _countryCodes.put("CO", "COL");
        _countryCodes.put("KM", "COM");
        _countryCodes.put("CD", "COD");
        _countryCodes.put("CG", "COG");
        _countryCodes.put("CK", "COK");
        _countryCodes.put("CR", "CRI");
        _countryCodes.put("CI", "CIV");
        _countryCodes.put("HR", "HRV");
        _countryCodes.put("CU", "CUB");
        _countryCodes.put("CW", "CUW");
        _countryCodes.put("CY", "CYP");
        _countryCodes.put("CZ", "CZE");
        _countryCodes.put("DK", "DNK");
        _countryCodes.put("DJ", "DJI");
        _countryCodes.put("DM", "DMA");
        _countryCodes.put("DO", "DOM");
        _countryCodes.put("EC", "ECU");
        _countryCodes.put("EG", "EGY");
        _countryCodes.put("SV", "SLV");
        _countryCodes.put("GQ", "GNQ");
        _countryCodes.put("ER", "ERI");
        _countryCodes.put("EE", "EST");
        _countryCodes.put("ET", "ETH");
        _countryCodes.put("FK", "FLK");
        _countryCodes.put("FO", "FRO");
        _countryCodes.put("FJ", "FJI");
        _countryCodes.put("FI", "FIN");
        _countryCodes.put("FR", "FRA");
        _countryCodes.put("GF", "GUF");
        _countryCodes.put("PF", "PYF");
        _countryCodes.put("TF", "ATF");
        _countryCodes.put("GA", "GAB");
        _countryCodes.put("GM", "GMB");
        _countryCodes.put("GE", "GEO");
        _countryCodes.put("DE", "DEU");
        _countryCodes.put("GH", "GHA");
        _countryCodes.put("GI", "GIB");
        _countryCodes.put("GR", "GRC");
        _countryCodes.put("GL", "GRL");
        _countryCodes.put("GD", "GRD");
        _countryCodes.put("GP", "GLP");
        _countryCodes.put("GU", "GUM");
        _countryCodes.put("GT", "GTM");
        _countryCodes.put("GG", "GGY");
        _countryCodes.put("GN", "GIN");
        _countryCodes.put("GW", "GNB");
        _countryCodes.put("GY", "GUY");
        _countryCodes.put("HT", "HTI");
        _countryCodes.put("HM", "HMD");
        _countryCodes.put("VA", "VAT");
        _countryCodes.put("HN", "HND");
        _countryCodes.put("HK", "HKG");
        _countryCodes.put("HU", "HUN");
        _countryCodes.put("IS", "ISL");
        _countryCodes.put("IN", "IND");
        _countryCodes.put("ID", "IDN");
        _countryCodes.put("IR", "IRN");
        _countryCodes.put("IQ", "IRQ");
        _countryCodes.put("IE", "IRL");
        _countryCodes.put("IM", "IMN");
        _countryCodes.put("IL", "ISR");
        _countryCodes.put("IT", "ITA");
        _countryCodes.put("JM", "JAM");
        _countryCodes.put("JP", "JPN");
        _countryCodes.put("JE", "JEY");
        _countryCodes.put("JO", "JOR");
        _countryCodes.put("KZ", "KAZ");
        _countryCodes.put("KE", "KEN");
        _countryCodes.put("KI", "KIR");
        _countryCodes.put("KP", "PRK");
        _countryCodes.put("KR", "KOR");
        _countryCodes.put("XK", "XKS");
        _countryCodes.put("KW", "KWT");
        _countryCodes.put("KG", "KGZ");
        _countryCodes.put("LA", "LAO");
        _countryCodes.put("LV", "LVA");
        _countryCodes.put("LB", "LBN");
        _countryCodes.put("LS", "LSO");
        _countryCodes.put("LR", "LBR");
        _countryCodes.put("LY", "LBY");
        _countryCodes.put("LI", "LIE");
        _countryCodes.put("LT", "LTU");
        _countryCodes.put("LU", "LUX");
        _countryCodes.put("MO", "MAC");
        _countryCodes.put("MK", "MKD");
        _countryCodes.put("MG", "MDG");
        _countryCodes.put("MW", "MWI");
        _countryCodes.put("MY", "MYS");
        _countryCodes.put("MV", "MDV");
        _countryCodes.put("ML", "MLI");
        _countryCodes.put("MT", "MLT");
        _countryCodes.put("MH", "MHL");
        _countryCodes.put("MQ", "MTQ");
        _countryCodes.put("MR", "MRT");
        _countryCodes.put("MU", "MUS");
        _countryCodes.put("YT", "MYT");
        _countryCodes.put("MX", "MEX");
        _countryCodes.put("FM", "FSM");
        _countryCodes.put("MD", "MDA");
        _countryCodes.put("MC", "MCO");
        _countryCodes.put("MN", "MNG");
        _countryCodes.put("ME", "MNE");
        _countryCodes.put("MS", "MSR");
        _countryCodes.put("MA", "MAR");
        _countryCodes.put("MZ", "MOZ");
        _countryCodes.put("NA", "NAM");
        _countryCodes.put("NR", "NRU");
        _countryCodes.put("NP", "NPL");
        _countryCodes.put("NL", "NLD");
        _countryCodes.put("NC", "NCL");
        _countryCodes.put("NZ", "NZL");
        _countryCodes.put("NI", "NIC");
        _countryCodes.put("NE", "NER");
        _countryCodes.put("NG", "NGA");
        _countryCodes.put("NU", "NIU");
        _countryCodes.put("NF", "NFK");
        _countryCodes.put("MP", "MNP");
        _countryCodes.put("NO", "NOR");
        _countryCodes.put("OM", "OMN");
        _countryCodes.put("PK", "PAK");
        _countryCodes.put("PW", "PLW");
        _countryCodes.put("PA", "PAN");
        _countryCodes.put("PG", "PNG");
        _countryCodes.put("PY", "PRY");
        _countryCodes.put("PE", "PER");
        _countryCodes.put("PH", "PHL");
        _countryCodes.put("PN", "PCN");
        _countryCodes.put("PL", "POL");
        _countryCodes.put("PT", "PRT");
        _countryCodes.put("PR", "PRI");
        _countryCodes.put("QA", "QAT");
        _countryCodes.put("RE", "REU");
        _countryCodes.put("RO", "ROU");
        _countryCodes.put("RU", "RUS");
        _countryCodes.put("RW", "RWA");
        _countryCodes.put("BL", "BLM");
        _countryCodes.put("SH", "SHN");
        _countryCodes.put("KN", "KNA");
        _countryCodes.put("LC", "LCA");
        _countryCodes.put("MF", "MAF");
        _countryCodes.put("PM", "SPM");
        _countryCodes.put("VC", "VCT");
        _countryCodes.put("WS", "WSM");
        _countryCodes.put("SM", "SMR");
        _countryCodes.put("ST", "STP");
        _countryCodes.put("SA", "SAU");
        _countryCodes.put("SN", "SEN");
        _countryCodes.put("RS", "SRB");
        _countryCodes.put("SC", "SYC");
        _countryCodes.put("SL", "SLE");
        _countryCodes.put("SG", "SGP");
        _countryCodes.put("SX", "SXM");
        _countryCodes.put("SK", "SVK");
        _countryCodes.put("SI", "SVN");
        _countryCodes.put("SB", "SLB");
        _countryCodes.put("SO", "SOM");
        _countryCodes.put("ZA", "ZAF");
        _countryCodes.put("GS", "SGS");
        _countryCodes.put("SS", "SSD");
        _countryCodes.put("ES", "ESP");
        _countryCodes.put("LK", "LKA");
        _countryCodes.put("SD", "SDN");
        _countryCodes.put("SR", "SUR");
        _countryCodes.put("SJ", "SJM");
        _countryCodes.put("SZ", "SWZ");
        _countryCodes.put("SE", "SWE");
        _countryCodes.put("CH", "CHE");
        _countryCodes.put("SY", "SYR");
        _countryCodes.put("TW", "TWN");
        _countryCodes.put("TJ", "TJK");
        _countryCodes.put("TZ", "TZA");
        _countryCodes.put("TH", "THA");
        _countryCodes.put("TL", "TLS");
        _countryCodes.put("TG", "TGO");
        _countryCodes.put("TK", "TKL");
        _countryCodes.put("TO", "TON");
        _countryCodes.put("TT", "TTO");
        _countryCodes.put("TN", "TUN");
        _countryCodes.put("TR", "TUR");
        _countryCodes.put("TM", "TKM");
        _countryCodes.put("TC", "TCA");
        _countryCodes.put("TV", "TUV");
        _countryCodes.put("UG", "UGA");
        _countryCodes.put("UA", "UKR");
        _countryCodes.put("AE", "ARE");
        _countryCodes.put("GB", "GBR");
        _countryCodes.put("US", "USA");
        _countryCodes.put("UM", "UMI");
        _countryCodes.put("UY", "URY");
        _countryCodes.put("UZ", "UZB");
        _countryCodes.put("VU", "VUT");
        _countryCodes.put("VE", "VEN");
        _countryCodes.put("VN", "VNM");
        _countryCodes.put("VI", "VIR");
        _countryCodes.put("WF", "WLF");
        _countryCodes.put("EH", "ESH");
        _countryCodes.put("YE", "YEM");
        _countryCodes.put("ZM", "ZMB");
        _countryCodes.put("ZW", "ZWE");
        _countryCodes.put("AX", "ALA");
        _countryCodes.put("BQ", "BES");
        _countryCodes.put("PS", "PSE");
    }

    public static String convertCountryCodeAlpha2ToAlpha3(String countryCode) {
        if (TextUtils.isEmpty(countryCode)) {
            return "";
        }
        return _countryCodes.containsKey(countryCode) ? (String) _countryCodes.get(countryCode) : countryCode;
    }

    public static long convertSygicTimeSecondsToUnixTimeMillis(long sygicTimeSeconds) {
        return 978307200000L + (1000 * sygicTimeSeconds);
    }

    public static long convertUnixTimeMillisToSygicTimeSeconds(long unixTimeMillis) {
        return ((unixTimeMillis - 978307200000L) + 500) / 1000;
    }

    public static long normalizeUnixTimeMillis(long unixTimeMillis) {
        if (unixTimeMillis < 978307200000L || unixTimeMillis > 2147483647000L) {
            return 978307200000L;
        }
        return unixTimeMillis;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.toLowerCase(Locale.ENGLISH).equals("htc")) {
            manufacturer = manufacturer.toUpperCase(Locale.ENGLISH);
        }
        return capitalize(manufacturer) + " " + model;
    }

    public static String getDeviceId(Context context) {
        String id = getImei(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        id = getWifiMacAddress(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        id = getAndroidId(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        id = getUuid(context);
        if (TextUtils.isEmpty(id)) {
            return "";
        }
        return id;
    }

    public static byte convertNetworkType(int networkType) {
        switch (networkType) {
            case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                return (byte) 0;
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return (byte) 1;
            case TTSConst.TTSMULTILINE /*1*/:
                return (byte) 2;
            default:
                return (byte) 3;
        }
    }

    private static String getImei(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
            if (tm == null) {
                return imei;
            }
            imei = tm.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                return "";
            }
            return imei;
        } catch (Exception e) {
            return imei;
        }
    }

    private static String getWifiMacAddress(Context context) {
        String wifiMacAddress = "";
        WifiManager wm = (WifiManager) context.getSystemService("wifi");
        if (wm != null) {
            String mac;
            WifiInfo wi = wm.getConnectionInfo();
            if (wi != null) {
                mac = wi.getMacAddress();
                if (mac != null) {
                    wifiMacAddress = mac.replace(":", "");
                }
            }
            if (TextUtils.isEmpty(wifiMacAddress) && wm.getWifiState() == 1) {
                wm.setWifiEnabled(true);
                int i = 0;
                while (i < 10) {
                    if (wm.getWifiState() == 3) {
                        wi = wm.getConnectionInfo();
                        if (wi != null) {
                            mac = wi.getMacAddress();
                            if (mac != null) {
                                wifiMacAddress = mac.replace(":", "");
                            }
                        }
                        wm.setWifiEnabled(false);
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                        i++;
                    }
                }
                wm.setWifiEnabled(false);
            }
        }
        return wifiMacAddress;
    }

    private static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), "android_id");
        return TextUtils.isEmpty(androidId) ? "" : androidId;
    }

    private static String getUuid(Context context) {
        return getSystemProperties(context, "tcc.hwinfo.uuid");
    }

    private static String getSystemProperties(Context context, String key) {
        String result = "";
        try {
            Class<?> SystemProperties = context.getClassLoader().loadClass("android.os.SystemProperties");
            result = (String) SystemProperties.getMethod("get", new Class[]{String.class}).invoke(SystemProperties, new Object[]{new String(key)});
            if (TextUtils.isEmpty(result)) {
                return "";
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : str.toCharArray()) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase = phrase + Character.toUpperCase(c);
                capitalizeNext = false;
            } else {
                capitalizeNext |= Character.isWhitespace(c);
                phrase = phrase + c;
            }
        }
        return phrase;
    }
}
