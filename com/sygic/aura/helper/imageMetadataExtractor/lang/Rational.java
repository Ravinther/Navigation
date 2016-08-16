package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.io.Serializable;

public class Rational extends Number implements Serializable {
    private final long _denominator;
    private final long _numerator;

    public Rational(long numerator, long denominator) {
        this._numerator = numerator;
        this._denominator = denominator;
    }

    public double doubleValue() {
        return this._numerator == 0 ? 0.0d : ((double) this._numerator) / ((double) this._denominator);
    }

    public float floatValue() {
        return this._numerator == 0 ? 0.0f : ((float) this._numerator) / ((float) this._denominator);
    }

    public final byte byteValue() {
        return (byte) ((int) doubleValue());
    }

    public final int intValue() {
        return (int) doubleValue();
    }

    public final long longValue() {
        return (long) doubleValue();
    }

    public final short shortValue() {
        return (short) ((int) doubleValue());
    }

    public final long getNumerator() {
        return this._numerator;
    }

    public Rational getReciprocal() {
        return new Rational(this._denominator, this._numerator);
    }

    public boolean isInteger() {
        return this._denominator == 1 || ((this._denominator != 0 && this._numerator % this._denominator == 0) || (this._denominator == 0 && this._numerator == 0));
    }

    public String toString() {
        return this._numerator + "/" + this._denominator;
    }

    public String toSimpleString(boolean allowDecimal) {
        if (this._denominator == 0 && this._numerator != 0) {
            return toString();
        }
        if (isInteger()) {
            return Integer.toString(intValue());
        }
        if (this._numerator != 1 && this._denominator % this._numerator == 0) {
            return new Rational(1, this._denominator / this._numerator).toSimpleString(allowDecimal);
        }
        Rational simplifiedInstance = getSimplifiedInstance();
        if (allowDecimal) {
            String doubleString = Double.toString(simplifiedInstance.doubleValue());
            if (doubleString.length() < 5) {
                return doubleString;
            }
        }
        return simplifiedInstance.toString();
    }

    private boolean tooComplexForSimplification() {
        return (((double) (Math.min(this._denominator, this._numerator) - 1)) / 5.0d) + 2.0d > 1000.0d;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Rational)) {
            return false;
        }
        if (doubleValue() == ((Rational) obj).doubleValue()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((int) this._denominator) * 23) + ((int) this._numerator);
    }

    public Rational getSimplifiedInstance() {
        if (tooComplexForSimplification()) {
            return this;
        }
        int factor = 2;
        while (((long) factor) <= Math.min(this._denominator, this._numerator)) {
            if ((factor % 2 != 0 || factor <= 2) && ((factor % 5 != 0 || factor <= 5) && this._denominator % ((long) factor) == 0 && this._numerator % ((long) factor) == 0)) {
                return new Rational(this._numerator / ((long) factor), this._denominator / ((long) factor));
            }
            factor++;
        }
        return this;
    }
}
