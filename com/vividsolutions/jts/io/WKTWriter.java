package com.vividsolutions.jts.io;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.util.Assert;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class WKTWriter {
    private static int INDENT;
    private int coordsPerLine;
    private DecimalFormat formatter;
    private String indentTabStr;
    private boolean isFormatted;
    private int level;
    private int outputDimension;
    private boolean useFormatting;

    public static String toLineString(Coordinate p0, Coordinate p1) {
        return "LINESTRING ( " + p0.f1295x + " " + p0.f1296y + ", " + p1.f1295x + " " + p1.f1296y + " )";
    }

    static {
        INDENT = 2;
    }

    private static DecimalFormat createFormatter(PrecisionModel precisionModel) {
        int decimalPlaces = precisionModel.getMaximumSignificantDigits();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0" + (decimalPlaces > 0 ? "." : "") + stringOfChar('#', decimalPlaces), symbols);
    }

    public static String stringOfChar(char ch, int count) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < count; i++) {
            buf.append(ch);
        }
        return buf.toString();
    }

    public WKTWriter() {
        this.outputDimension = 2;
        this.isFormatted = false;
        this.useFormatting = false;
        this.level = 0;
        this.coordsPerLine = -1;
        this.indentTabStr = "  ";
    }

    public String write(Geometry geometry) {
        Writer sw = new StringWriter();
        try {
            writeFormatted(geometry, this.isFormatted, sw);
        } catch (IOException e) {
            Assert.shouldNeverReachHere();
        }
        return sw.toString();
    }

    private void writeFormatted(Geometry geometry, boolean useFormatting, Writer writer) throws IOException {
        this.useFormatting = useFormatting;
        this.formatter = createFormatter(geometry.getPrecisionModel());
        appendGeometryTaggedText(geometry, 0, writer);
    }

    private void appendGeometryTaggedText(Geometry geometry, int level, Writer writer) throws IOException {
        indent(level, writer);
        if (geometry instanceof Point) {
            Point point = (Point) geometry;
            appendPointTaggedText(point.getCoordinate(), level, writer, point.getPrecisionModel());
        } else if (geometry instanceof LinearRing) {
            appendLinearRingTaggedText((LinearRing) geometry, level, writer);
        } else if (geometry instanceof LineString) {
            appendLineStringTaggedText((LineString) geometry, level, writer);
        } else if (geometry instanceof Polygon) {
            appendPolygonTaggedText((Polygon) geometry, level, writer);
        } else if (geometry instanceof MultiPoint) {
            appendMultiPointTaggedText((MultiPoint) geometry, level, writer);
        } else if (geometry instanceof MultiLineString) {
            appendMultiLineStringTaggedText((MultiLineString) geometry, level, writer);
        } else if (geometry instanceof MultiPolygon) {
            appendMultiPolygonTaggedText((MultiPolygon) geometry, level, writer);
        } else if (geometry instanceof GeometryCollection) {
            appendGeometryCollectionTaggedText((GeometryCollection) geometry, level, writer);
        } else {
            Assert.shouldNeverReachHere("Unsupported Geometry implementation:" + geometry.getClass());
        }
    }

    private void appendPointTaggedText(Coordinate coordinate, int level, Writer writer, PrecisionModel precisionModel) throws IOException {
        writer.write("POINT ");
        appendPointText(coordinate, level, writer, precisionModel);
    }

    private void appendLineStringTaggedText(LineString lineString, int level, Writer writer) throws IOException {
        writer.write("LINESTRING ");
        appendLineStringText(lineString, level, false, writer);
    }

    private void appendLinearRingTaggedText(LinearRing linearRing, int level, Writer writer) throws IOException {
        writer.write("LINEARRING ");
        appendLineStringText(linearRing, level, false, writer);
    }

    private void appendPolygonTaggedText(Polygon polygon, int level, Writer writer) throws IOException {
        writer.write("POLYGON ");
        appendPolygonText(polygon, level, false, writer);
    }

    private void appendMultiPointTaggedText(MultiPoint multipoint, int level, Writer writer) throws IOException {
        writer.write("MULTIPOINT ");
        appendMultiPointText(multipoint, level, writer);
    }

    private void appendMultiLineStringTaggedText(MultiLineString multiLineString, int level, Writer writer) throws IOException {
        writer.write("MULTILINESTRING ");
        appendMultiLineStringText(multiLineString, level, false, writer);
    }

    private void appendMultiPolygonTaggedText(MultiPolygon multiPolygon, int level, Writer writer) throws IOException {
        writer.write("MULTIPOLYGON ");
        appendMultiPolygonText(multiPolygon, level, writer);
    }

    private void appendGeometryCollectionTaggedText(GeometryCollection geometryCollection, int level, Writer writer) throws IOException {
        writer.write("GEOMETRYCOLLECTION ");
        appendGeometryCollectionText(geometryCollection, level, writer);
    }

    private void appendPointText(Coordinate coordinate, int level, Writer writer, PrecisionModel precisionModel) throws IOException {
        if (coordinate == null) {
            writer.write("EMPTY");
            return;
        }
        writer.write("(");
        appendCoordinate(coordinate, writer);
        writer.write(")");
    }

    private void appendCoordinate(Coordinate coordinate, Writer writer) throws IOException {
        writer.write(writeNumber(coordinate.f1295x) + " " + writeNumber(coordinate.f1296y));
        if (this.outputDimension >= 3 && !Double.isNaN(coordinate.f1297z)) {
            writer.write(" ");
            writer.write(writeNumber(coordinate.f1297z));
        }
    }

    private String writeNumber(double d) {
        return this.formatter.format(d);
    }

    private void appendLineStringText(LineString lineString, int level, boolean doIndent, Writer writer) throws IOException {
        if (lineString.isEmpty()) {
            writer.write("EMPTY");
            return;
        }
        if (doIndent) {
            indent(level, writer);
        }
        writer.write("(");
        int i = 0;
        while (i < lineString.getNumPoints()) {
            if (i > 0) {
                writer.write(", ");
                if (this.coordsPerLine > 0 && i % this.coordsPerLine == 0) {
                    indent(level + 1, writer);
                }
            }
            appendCoordinate(lineString.getCoordinateN(i), writer);
            i++;
        }
        writer.write(")");
    }

    private void appendPolygonText(Polygon polygon, int level, boolean indentFirst, Writer writer) throws IOException {
        if (polygon.isEmpty()) {
            writer.write("EMPTY");
            return;
        }
        if (indentFirst) {
            indent(level, writer);
        }
        writer.write("(");
        appendLineStringText(polygon.getExteriorRing(), level, false, writer);
        for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
            writer.write(", ");
            appendLineStringText(polygon.getInteriorRingN(i), level + 1, true, writer);
        }
        writer.write(")");
    }

    private void appendMultiPointText(MultiPoint multiPoint, int level, Writer writer) throws IOException {
        if (multiPoint.isEmpty()) {
            writer.write("EMPTY");
            return;
        }
        writer.write("(");
        for (int i = 0; i < multiPoint.getNumGeometries(); i++) {
            if (i > 0) {
                writer.write(", ");
                indentCoords(i, level + 1, writer);
            }
            writer.write("(");
            appendCoordinate(((Point) multiPoint.getGeometryN(i)).getCoordinate(), writer);
            writer.write(")");
        }
        writer.write(")");
    }

    private void appendMultiLineStringText(MultiLineString multiLineString, int level, boolean indentFirst, Writer writer) throws IOException {
        if (multiLineString.isEmpty()) {
            writer.write("EMPTY");
            return;
        }
        int level2 = level;
        boolean doIndent = indentFirst;
        writer.write("(");
        for (int i = 0; i < multiLineString.getNumGeometries(); i++) {
            if (i > 0) {
                writer.write(", ");
                level2 = level + 1;
                doIndent = true;
            }
            appendLineStringText((LineString) multiLineString.getGeometryN(i), level2, doIndent, writer);
        }
        writer.write(")");
    }

    private void appendMultiPolygonText(MultiPolygon multiPolygon, int level, Writer writer) throws IOException {
        if (multiPolygon.isEmpty()) {
            writer.write("EMPTY");
            return;
        }
        int level2 = level;
        boolean doIndent = false;
        writer.write("(");
        for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
            if (i > 0) {
                writer.write(", ");
                level2 = level + 1;
                doIndent = true;
            }
            appendPolygonText((Polygon) multiPolygon.getGeometryN(i), level2, doIndent, writer);
        }
        writer.write(")");
    }

    private void appendGeometryCollectionText(GeometryCollection geometryCollection, int level, Writer writer) throws IOException {
        if (geometryCollection.isEmpty()) {
            writer.write("EMPTY");
            return;
        }
        int level2 = level;
        writer.write("(");
        for (int i = 0; i < geometryCollection.getNumGeometries(); i++) {
            if (i > 0) {
                writer.write(", ");
                level2 = level + 1;
            }
            appendGeometryTaggedText(geometryCollection.getGeometryN(i), level2, writer);
        }
        writer.write(")");
    }

    private void indentCoords(int coordIndex, int level, Writer writer) throws IOException {
        if (this.coordsPerLine > 0 && coordIndex % this.coordsPerLine == 0) {
            indent(level, writer);
        }
    }

    private void indent(int level, Writer writer) throws IOException {
        if (this.useFormatting && level > 0) {
            writer.write("\n");
            for (int i = 0; i < level; i++) {
                writer.write(this.indentTabStr);
            }
        }
    }
}
