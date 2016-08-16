package com.googlecode.mp4parser.util;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import java.nio.ByteBuffer;

public class Matrix {
    public static final Matrix ROTATE_0;
    public static final Matrix ROTATE_180;
    public static final Matrix ROTATE_270;
    public static final Matrix ROTATE_90;
    double f1243a;
    double f1244b;
    double f1245c;
    double f1246d;
    double tx;
    double ty;
    double f1247u;
    double f1248v;
    double f1249w;

    public Matrix(double a, double b, double c, double d, double u, double v, double w, double tx, double ty) {
        this.f1247u = u;
        this.f1248v = v;
        this.f1249w = w;
        this.f1243a = a;
        this.f1244b = b;
        this.f1245c = c;
        this.f1246d = d;
        this.tx = tx;
        this.ty = ty;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Matrix matrix = (Matrix) o;
        if (Double.compare(matrix.f1243a, this.f1243a) != 0) {
            return false;
        }
        if (Double.compare(matrix.f1244b, this.f1244b) != 0) {
            return false;
        }
        if (Double.compare(matrix.f1245c, this.f1245c) != 0) {
            return false;
        }
        if (Double.compare(matrix.f1246d, this.f1246d) != 0) {
            return false;
        }
        if (Double.compare(matrix.tx, this.tx) != 0) {
            return false;
        }
        if (Double.compare(matrix.ty, this.ty) != 0) {
            return false;
        }
        if (Double.compare(matrix.f1247u, this.f1247u) != 0) {
            return false;
        }
        if (Double.compare(matrix.f1248v, this.f1248v) != 0) {
            return false;
        }
        if (Double.compare(matrix.f1249w, this.f1249w) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.f1247u);
        int result = (int) ((temp >>> 32) ^ temp);
        temp = Double.doubleToLongBits(this.f1248v);
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(this.f1249w);
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(this.f1243a);
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(this.f1244b);
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(this.f1245c);
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(this.f1246d);
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(this.tx);
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(this.ty);
        return (result * 31) + ((int) ((temp >>> 32) ^ temp));
    }

    public String toString() {
        if (equals(ROTATE_0)) {
            return "Rotate 0\u00b0";
        }
        if (equals(ROTATE_90)) {
            return "Rotate 90\u00b0";
        }
        if (equals(ROTATE_180)) {
            return "Rotate 180\u00b0";
        }
        if (equals(ROTATE_270)) {
            return "Rotate 270\u00b0";
        }
        return "Matrix{u=" + this.f1247u + ", v=" + this.f1248v + ", w=" + this.f1249w + ", a=" + this.f1243a + ", b=" + this.f1244b + ", c=" + this.f1245c + ", d=" + this.f1246d + ", tx=" + this.tx + ", ty=" + this.ty + '}';
    }

    static {
        ROTATE_0 = new Matrix(1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
        ROTATE_90 = new Matrix(0.0d, 1.0d, -1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
        ROTATE_180 = new Matrix(-1.0d, 0.0d, 0.0d, -1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
        ROTATE_270 = new Matrix(0.0d, -1.0d, 1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    }

    public static Matrix fromFileOrder(double a, double b, double u, double c, double d, double v, double tx, double ty, double w) {
        return new Matrix(a, b, c, d, u, v, w, tx, ty);
    }

    public static Matrix fromByteBuffer(ByteBuffer byteBuffer) {
        return fromFileOrder(IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer));
    }

    public void getContent(ByteBuffer byteBuffer) {
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1243a);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1244b);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f1247u);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1245c);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1246d);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f1248v);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.tx);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.ty);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f1249w);
    }
}
