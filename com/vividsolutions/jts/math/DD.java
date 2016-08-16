package com.vividsolutions.jts.math;

import java.io.Serializable;

public final class DD implements Serializable, Cloneable, Comparable {
    public static final DD f1299E;
    public static final DD NaN;
    private static final DD ONE;
    public static final DD PI;
    public static final DD PI_2;
    private static final DD TEN;
    public static final DD TWO_PI;
    private double hi;
    private double lo;

    static {
        PI = new DD(3.141592653589793d, 1.2246467991473532E-16d);
        TWO_PI = new DD(6.283185307179586d, 2.4492935982947064E-16d);
        PI_2 = new DD(1.5707963267948966d, 6.123233995736766E-17d);
        f1299E = new DD(2.718281828459045d, 1.4456468917292502E-16d);
        NaN = new DD(Double.NaN, Double.NaN);
        TEN = valueOf(10.0d);
        ONE = valueOf(1.0d);
    }

    private static DD createNaN() {
        return new DD(Double.NaN, Double.NaN);
    }

    public static DD valueOf(double x) {
        return new DD(x);
    }

    public DD() {
        this.hi = 0.0d;
        this.lo = 0.0d;
        init(0.0d);
    }

    public DD(double x) {
        this.hi = 0.0d;
        this.lo = 0.0d;
        init(x);
    }

    public DD(double hi, double lo) {
        this.hi = 0.0d;
        this.lo = 0.0d;
        init(hi, lo);
    }

    public DD(DD dd) {
        this.hi = 0.0d;
        this.lo = 0.0d;
        init(dd);
    }

    public static DD copy(DD dd) {
        return new DD(dd);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    private final void init(double x) {
        this.hi = x;
        this.lo = 0.0d;
    }

    private final void init(double hi, double lo) {
        this.hi = hi;
        this.lo = lo;
    }

    private final void init(DD dd) {
        this.hi = dd.hi;
        this.lo = dd.lo;
    }

    public final DD add(DD y) {
        return copy(this).selfAdd(y);
    }

    public final DD selfAdd(DD y) {
        return selfAdd(y.hi, y.lo);
    }

    public final DD selfAdd(double y) {
        double S = this.hi + y;
        double e = S - this.hi;
        double s = S - e;
        double f = ((y - e) + (this.hi - s)) + this.lo;
        double H = S + f;
        double h = f + (S - H);
        this.hi = H + h;
        this.lo = (H - this.hi) + h;
        return this;
    }

    private final DD selfAdd(double yhi, double ylo) {
        double S = this.hi + yhi;
        double T = this.lo + ylo;
        double e = S - this.hi;
        double f = T - this.lo;
        double s = S - e;
        double t = T - f;
        s = (yhi - e) + (this.hi - s);
        e = s + T;
        double H = S + e;
        e = ((ylo - f) + (this.lo - t)) + (e + (S - H));
        double zhi = H + e;
        double zlo = e + (H - zhi);
        this.hi = zhi;
        this.lo = zlo;
        return this;
    }

    public final DD subtract(DD y) {
        return add(y.negate());
    }

    public final DD selfSubtract(DD y) {
        return isNaN() ? this : selfAdd(-y.hi, -y.lo);
    }

    public final DD negate() {
        return isNaN() ? this : new DD(-this.hi, -this.lo);
    }

    public final DD multiply(DD y) {
        if (y.isNaN()) {
            return createNaN();
        }
        return copy(this).selfMultiply(y);
    }

    public final DD selfMultiply(DD y) {
        return selfMultiply(y.hi, y.lo);
    }

    private final DD selfMultiply(double yhi, double ylo) {
        double C = 1.34217729E8d * this.hi;
        double c = 1.34217729E8d * yhi;
        double hx = C - (C - this.hi);
        double tx = this.hi - hx;
        double hy = c - yhi;
        C = this.hi * yhi;
        hy = c - hy;
        double ty = yhi - hy;
        c = (((((hx * hy) - C) + (hx * ty)) + (tx * hy)) + (tx * ty)) + ((this.hi * ylo) + (this.lo * yhi));
        double zhi = C + c;
        double zlo = c + (C - zhi);
        this.hi = zhi;
        this.lo = zlo;
        return this;
    }

    public final DD divide(DD y) {
        double C = this.hi / y.hi;
        double c = 1.34217729E8d * C;
        double hc = c - C;
        double u = 1.34217729E8d * y.hi;
        hc = c - hc;
        double tc = C - hc;
        double hy = u - y.hi;
        double U = C * y.hi;
        hy = u - hy;
        double ty = y.hi - hy;
        u = ((((hc * hy) - U) + (hc * ty)) + (tc * hy)) + (tc * ty);
        double d = this.hi;
        double d2 = this.lo;
        d2 = y.lo;
        c = ((((r0 - U) - u) + r0) - (r0 * C)) / y.hi;
        u = C + c;
        return new DD(u, (C - u) + c);
    }

    public final DD reciprocal() {
        double C = 1.0d / this.hi;
        double c = 1.34217729E8d * C;
        double hc = c - C;
        double u = 1.34217729E8d * this.hi;
        hc = c - hc;
        double tc = C - hc;
        double hy = u - this.hi;
        double U = C * this.hi;
        hy = u - hy;
        double ty = this.hi - hy;
        u = ((((hc * hy) - U) + (hc * ty)) + (tc * hy)) + (tc * ty);
        double d = this.lo;
        c = (((1.0d - U) - u) - (r0 * C)) / this.hi;
        double zhi = C + c;
        return new DD(zhi, (C - zhi) + c);
    }

    public int signum() {
        if (this.hi > 0.0d) {
            return 1;
        }
        if (this.hi < 0.0d) {
            return -1;
        }
        if (this.lo > 0.0d) {
            return 1;
        }
        if (this.lo < 0.0d) {
            return -1;
        }
        return 0;
    }

    public DD abs() {
        if (isNaN()) {
            return NaN;
        }
        if (isNegative()) {
            return negate();
        }
        return new DD(this);
    }

    public DD sqr() {
        return multiply(this);
    }

    public DD pow(int exp) {
        if (((double) exp) == 0.0d) {
            return valueOf(1.0d);
        }
        DD r = new DD(this);
        DD s = valueOf(1.0d);
        int n = Math.abs(exp);
        if (n > 1) {
            while (n > 0) {
                if (n % 2 == 1) {
                    s.selfMultiply(r);
                }
                n /= 2;
                if (n > 0) {
                    r = r.sqr();
                }
            }
        } else {
            s = r;
        }
        if (exp < 0) {
            return s.reciprocal();
        }
        return s;
    }

    public boolean isZero() {
        return this.hi == 0.0d && this.lo == 0.0d;
    }

    public boolean isNegative() {
        return this.hi < 0.0d || (this.hi == 0.0d && this.lo < 0.0d);
    }

    public boolean isNaN() {
        return Double.isNaN(this.hi);
    }

    public boolean gt(DD y) {
        return this.hi > y.hi || (this.hi == y.hi && this.lo > y.lo);
    }

    public boolean lt(DD y) {
        return this.hi < y.hi || (this.hi == y.hi && this.lo < y.lo);
    }

    public int compareTo(Object o) {
        DD other = (DD) o;
        if (this.hi < other.hi) {
            return -1;
        }
        if (this.hi > other.hi) {
            return 1;
        }
        if (this.lo < other.lo) {
            return -1;
        }
        if (this.lo > other.lo) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        int mag = magnitude(this.hi);
        if (mag < -3 || mag > 20) {
            return toSciNotation();
        }
        return toStandardNotation();
    }

    public String toStandardNotation() {
        String specialStr = getSpecialNumberString();
        if (specialStr != null) {
            return specialStr;
        }
        int[] magnitude = new int[1];
        String sigDigits = extractSignificantDigits(true, magnitude);
        int decimalPointPos = magnitude[0] + 1;
        String num = sigDigits;
        if (sigDigits.charAt(0) == '.') {
            num = "0" + sigDigits;
        } else if (decimalPointPos < 0) {
            num = "0." + stringOfChar('0', -decimalPointPos) + sigDigits;
        } else if (sigDigits.indexOf(46) == -1) {
            num = sigDigits + stringOfChar('0', decimalPointPos - sigDigits.length()) + ".0";
        }
        if (isNegative()) {
            return "-" + num;
        }
        return num;
    }

    public String toSciNotation() {
        if (isZero()) {
            return "0.0E0";
        }
        String specialStr = getSpecialNumberString();
        if (specialStr != null) {
            return specialStr;
        }
        int[] magnitude = new int[1];
        String digits = extractSignificantDigits(false, magnitude);
        String expStr = "E" + magnitude[0];
        if (digits.charAt(0) == '0') {
            throw new IllegalStateException("Found leading zero: " + digits);
        }
        String trailingDigits = "";
        if (digits.length() > 1) {
            trailingDigits = digits.substring(1);
        }
        String digitsWithDecimal = digits.charAt(0) + "." + trailingDigits;
        if (isNegative()) {
            return "-" + digitsWithDecimal + expStr;
        }
        return digitsWithDecimal + expStr;
    }

    private String extractSignificantDigits(boolean insertDecimalPoint, int[] magnitude) {
        DD y = abs();
        int mag = magnitude(y.hi);
        y = y.divide(TEN.pow(mag));
        if (y.gt(TEN)) {
            y = y.divide(TEN);
            mag++;
        } else if (y.lt(ONE)) {
            y = y.multiply(TEN);
            mag--;
        }
        int decimalPointPos = mag + 1;
        StringBuffer buf = new StringBuffer();
        int i = 0;
        while (i <= 31) {
            if (insertDecimalPoint && i == decimalPointPos) {
                buf.append('.');
            }
            int digit = (int) y.hi;
            boolean rebiasBy10;
            char digitChar;
            boolean continueExtractingDigits;
            int remMag;
            if (digit >= 0 && digit <= 9) {
                if (digit >= 0) {
                    break;
                }
                rebiasBy10 = false;
                if (digit <= 9) {
                    digitChar = (char) (digit + 48);
                } else {
                    rebiasBy10 = true;
                    digitChar = '9';
                }
                buf.append(digitChar);
                y = y.subtract(valueOf((double) digit)).multiply(TEN);
                if (rebiasBy10) {
                    y.selfAdd(TEN);
                }
                continueExtractingDigits = true;
                remMag = magnitude(y.hi);
                continueExtractingDigits = false;
                if (continueExtractingDigits) {
                    break;
                }
                i++;
            } else if (digit >= 0) {
                rebiasBy10 = false;
                if (digit <= 9) {
                    rebiasBy10 = true;
                    digitChar = '9';
                } else {
                    digitChar = (char) (digit + 48);
                }
                buf.append(digitChar);
                y = y.subtract(valueOf((double) digit)).multiply(TEN);
                if (rebiasBy10) {
                    y.selfAdd(TEN);
                }
                continueExtractingDigits = true;
                remMag = magnitude(y.hi);
                if (remMag < 0 && Math.abs(remMag) >= 31 - i) {
                    continueExtractingDigits = false;
                }
                if (continueExtractingDigits) {
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        magnitude[0] = mag;
        return buf.toString();
    }

    private static String stringOfChar(char ch, int len) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buf.append(ch);
        }
        return buf.toString();
    }

    private String getSpecialNumberString() {
        if (isZero()) {
            return "0.0";
        }
        if (isNaN()) {
            return "NaN ";
        }
        return null;
    }

    private static int magnitude(double x) {
        double xAbs = Math.abs(x);
        int xMag = (int) Math.floor(Math.log(xAbs) / Math.log(10.0d));
        if (Math.pow(10.0d, (double) xMag) * 10.0d <= xAbs) {
            return xMag + 1;
        }
        return xMag;
    }
}
