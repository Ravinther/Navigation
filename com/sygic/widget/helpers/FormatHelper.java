package com.sygic.widget.helpers;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import java.util.concurrent.TimeUnit;

public class FormatHelper {
    public static CharSequence formatNumber(long number, String unit) {
        String strNumber = Long.toString(number);
        int nNumLength = strNumber.length();
        SpannableString spannable = new SpannableString(strNumber + unit);
        spannable.setSpan(new StyleSpan(1), 0, nNumLength, 0);
        return spannable;
    }

    public static CharSequence formatTime(long totalSeconds, boolean showZeroHours) {
        if (totalSeconds < 0) {
            return "";
        }
        long days = TimeUnit.SECONDS.toDays(totalSeconds);
        long hours = TimeUnit.SECONDS.toHours(totalSeconds) - TimeUnit.DAYS.toHours(days);
        long minutes = (TimeUnit.SECONDS.toMinutes(totalSeconds) - TimeUnit.DAYS.toMinutes(days)) - TimeUnit.HOURS.toMinutes(hours);
        CharSequence time = "";
        if (days > 0) {
            time = formatNumber(days, "d");
        }
        if (time.length() > 0) {
            time = TextUtils.concat(new CharSequence[]{time, " "});
        }
        if (hours > 0 || showZeroHours) {
            time = TextUtils.concat(new CharSequence[]{time, formatNumber(hours, "h")});
        }
        if (time.length() > 0) {
            time = TextUtils.concat(new CharSequence[]{time, " "});
        }
        return TextUtils.concat(new CharSequence[]{time, formatNumber(minutes, "m")});
    }

    public static int getTrafficColor(int totalDelaySeconds, int totalFreeSeconds) {
        float trafficLoad = ((float) totalDelaySeconds) / ((float) totalFreeSeconds);
        if (trafficLoad < 0.3f) {
            return 2131558769;
        }
        if (trafficLoad < 0.7f) {
            return 2131558773;
        }
        return 2131558771;
    }
}
