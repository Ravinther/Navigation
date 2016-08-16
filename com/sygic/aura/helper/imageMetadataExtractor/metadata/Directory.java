package com.sygic.aura.helper.imageMetadataExtractor.metadata;

import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Directory {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected final Collection<Tag> _definedTagList;
    protected TagDescriptor _descriptor;
    private final Collection<String> _errorList;
    protected final Map<Integer, Object> _tagMap;

    public abstract String getName();

    protected abstract HashMap<Integer, String> getTagNameMap();

    static {
        $assertionsDisabled = !Directory.class.desiredAssertionStatus();
    }

    protected Directory() {
        this._tagMap = new HashMap();
        this._definedTagList = new ArrayList();
        this._errorList = new ArrayList(4);
    }

    public boolean containsTag(int tagType) {
        return this._tagMap.containsKey(Integer.valueOf(tagType));
    }

    public void setDescriptor(TagDescriptor descriptor) {
        if (descriptor == null) {
            throw new NullPointerException("cannot set a null descriptor");
        }
        this._descriptor = descriptor;
    }

    public void addError(String message) {
        this._errorList.add(message);
    }

    public void setInt(int tagType, int value) {
        setObject(tagType, Integer.valueOf(value));
    }

    public void setIntArray(int tagType, int[] ints) {
        setObjectArray(tagType, ints);
    }

    public void setFloat(int tagType, float value) {
        setObject(tagType, Float.valueOf(value));
    }

    public void setFloatArray(int tagType, float[] floats) {
        setObjectArray(tagType, floats);
    }

    public void setDouble(int tagType, double value) {
        setObject(tagType, Double.valueOf(value));
    }

    public void setDoubleArray(int tagType, double[] doubles) {
        setObjectArray(tagType, doubles);
    }

    public void setString(int tagType, String value) {
        if (value == null) {
            throw new NullPointerException("cannot set a null String");
        }
        setObject(tagType, value);
    }

    public void setStringArray(int tagType, String[] strings) {
        setObjectArray(tagType, strings);
    }

    public void setLong(int tagType, long value) {
        setObject(tagType, Long.valueOf(value));
    }

    public void setDate(int tagType, Date value) {
        setObject(tagType, value);
    }

    public void setRational(int tagType, Rational rational) {
        setObject(tagType, rational);
    }

    public void setRationalArray(int tagType, Rational[] rationals) {
        setObjectArray(tagType, rationals);
    }

    public void setByteArray(int tagType, byte[] bytes) {
        setObjectArray(tagType, bytes);
    }

    public void setObject(int tagType, Object value) {
        if (value == null) {
            throw new NullPointerException("cannot set a null object");
        }
        if (!this._tagMap.containsKey(Integer.valueOf(tagType))) {
            this._definedTagList.add(new Tag(tagType, this));
        }
        this._tagMap.put(Integer.valueOf(tagType), value);
    }

    public void setObjectArray(int tagType, Object array) {
        setObject(tagType, array);
    }

    public Integer getInteger(int tagType) {
        String o = getObject(tagType);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return Integer.valueOf(((Number) o).intValue());
        }
        if (o instanceof String) {
            try {
                return Integer.valueOf(Integer.parseInt(o));
            } catch (NumberFormatException e) {
                long val = 0;
                for (byte aByte : o.getBytes()) {
                    val = (val << 8) + ((long) (aByte & 255));
                }
                return Integer.valueOf((int) val);
            }
        } else if (o instanceof Rational[]) {
            Rational[] rationals = (Rational[]) o;
            if (rationals.length == 1) {
                return Integer.valueOf(rationals[0].intValue());
            }
            return null;
        } else if (o instanceof byte[]) {
            byte[] bytes = (byte[]) o;
            if (bytes.length == 1) {
                return Integer.valueOf(bytes[0]);
            }
            return null;
        } else if (!(o instanceof int[])) {
            return null;
        } else {
            int[] ints = (int[]) o;
            if (ints.length == 1) {
                return Integer.valueOf(ints[0]);
            }
            return null;
        }
    }

    public String[] getStringArray(int tagType) {
        Object o = getObject(tagType);
        if (o == null) {
            return null;
        }
        if (o instanceof String[]) {
            return (String[]) o;
        }
        if (o instanceof String) {
            return new String[]{(String) o};
        } else if (o instanceof int[]) {
            int[] ints = (int[]) o;
            strings = new String[ints.length];
            for (i = 0; i < strings.length; i++) {
                strings[i] = Integer.toString(ints[i]);
            }
            return strings;
        } else if (o instanceof byte[]) {
            byte[] bytes = (byte[]) o;
            strings = new String[bytes.length];
            for (i = 0; i < strings.length; i++) {
                strings[i] = Byte.toString(bytes[i]);
            }
            return strings;
        } else if (!(o instanceof Rational[])) {
            return null;
        } else {
            Rational[] rationals = (Rational[]) o;
            strings = new String[rationals.length];
            for (i = 0; i < strings.length; i++) {
                strings[i] = rationals[i].toSimpleString(false);
            }
            return strings;
        }
    }

    public int[] getIntArray(int tagType) {
        CharSequence o = getObject(tagType);
        if (o == null) {
            return null;
        }
        if (o instanceof int[]) {
            return (int[]) o;
        }
        int[] ints;
        int i;
        if (o instanceof Rational[]) {
            Rational[] rationals = (Rational[]) o;
            ints = new int[rationals.length];
            for (i = 0; i < ints.length; i++) {
                ints[i] = rationals[i].intValue();
            }
            return ints;
        } else if (o instanceof short[]) {
            short[] shorts = (short[]) o;
            ints = new int[shorts.length];
            for (i = 0; i < shorts.length; i++) {
                ints[i] = shorts[i];
            }
            return ints;
        } else if (o instanceof byte[]) {
            byte[] bytes = (byte[]) o;
            ints = new int[bytes.length];
            for (i = 0; i < bytes.length; i++) {
                ints[i] = bytes[i];
            }
            return ints;
        } else if (o instanceof CharSequence) {
            CharSequence str = o;
            ints = new int[str.length()];
            for (i = 0; i < str.length(); i++) {
                ints[i] = str.charAt(i);
            }
            return ints;
        } else if (!(o instanceof Integer)) {
            return null;
        } else {
            return new int[]{((Integer) o).intValue()};
        }
    }

    public byte[] getByteArray(int tagType) {
        CharSequence o = getObject(tagType);
        if (o == null) {
            return null;
        }
        byte[] bytes;
        int i;
        if (o instanceof Rational[]) {
            Rational[] rationals = (Rational[]) o;
            bytes = new byte[rationals.length];
            for (i = 0; i < bytes.length; i++) {
                bytes[i] = rationals[i].byteValue();
            }
            return bytes;
        } else if (o instanceof byte[]) {
            return (byte[]) o;
        } else {
            if (o instanceof int[]) {
                int[] ints = (int[]) o;
                bytes = new byte[ints.length];
                for (i = 0; i < ints.length; i++) {
                    bytes[i] = (byte) ints[i];
                }
                return bytes;
            } else if (o instanceof short[]) {
                short[] shorts = (short[]) o;
                bytes = new byte[shorts.length];
                for (i = 0; i < shorts.length; i++) {
                    bytes[i] = (byte) shorts[i];
                }
                return bytes;
            } else if (o instanceof CharSequence) {
                CharSequence str = o;
                bytes = new byte[str.length()];
                for (i = 0; i < str.length(); i++) {
                    bytes[i] = (byte) str.charAt(i);
                }
                return bytes;
            } else if (!(o instanceof Integer)) {
                return null;
            } else {
                return new byte[]{((Integer) o).byteValue()};
            }
        }
    }

    public Double getDoubleObject(int tagType) {
        Double d = null;
        Object o = getObject(tagType);
        if (o == null) {
            return d;
        }
        if (o instanceof String) {
            try {
                return Double.valueOf(Double.parseDouble((String) o));
            } catch (NumberFormatException e) {
                return d;
            }
        } else if (o instanceof Number) {
            return Double.valueOf(((Number) o).doubleValue());
        } else {
            return d;
        }
    }

    public Float getFloatObject(int tagType) {
        Float f = null;
        Object o = getObject(tagType);
        if (o == null) {
            return f;
        }
        if (o instanceof String) {
            try {
                return Float.valueOf(Float.parseFloat((String) o));
            } catch (NumberFormatException e) {
                return f;
            }
        } else if (o instanceof Number) {
            return Float.valueOf(((Number) o).floatValue());
        } else {
            return f;
        }
    }

    public Rational getRational(int tagType) {
        Object o = getObject(tagType);
        if (o == null) {
            return null;
        }
        if (o instanceof Rational) {
            return (Rational) o;
        }
        if (o instanceof Integer) {
            return new Rational((long) ((Integer) o).intValue(), 1);
        }
        return o instanceof Long ? new Rational(((Long) o).longValue(), 1) : null;
    }

    public Rational[] getRationalArray(int tagType) {
        Object o = getObject(tagType);
        if (o == null) {
            return null;
        }
        return o instanceof Rational[] ? (Rational[]) o : null;
    }

    public String getString(int tagType) {
        Object o = getObject(tagType);
        if (o == null) {
            return null;
        }
        if (o instanceof Rational) {
            return ((Rational) o).toSimpleString(true);
        }
        if (!o.getClass().isArray()) {
            return o.toString();
        }
        int arrayLength = Array.getLength(o);
        Class<?> componentType = o.getClass().getComponentType();
        boolean isObjectArray = Object.class.isAssignableFrom(componentType);
        boolean isFloatArray = componentType.getName().equals("float");
        boolean isDoubleArray = componentType.getName().equals("double");
        boolean isIntArray = componentType.getName().equals("int");
        boolean isLongArray = componentType.getName().equals("long");
        boolean isByteArray = componentType.getName().equals("byte");
        boolean isShortArray = componentType.getName().equals("short");
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < arrayLength; i++) {
            if (i != 0) {
                string.append(' ');
            }
            if (isObjectArray) {
                string.append(Array.get(o, i).toString());
            } else if (isIntArray) {
                string.append(Array.getInt(o, i));
            } else if (isShortArray) {
                string.append(Array.getShort(o, i));
            } else if (isLongArray) {
                string.append(Array.getLong(o, i));
            } else if (isFloatArray) {
                string.append(Array.getFloat(o, i));
            } else if (isDoubleArray) {
                string.append(Array.getDouble(o, i));
            } else if (isByteArray) {
                string.append(Array.getByte(o, i));
            } else {
                addError("Unexpected array component type: " + componentType.getName());
            }
        }
        return string.toString();
    }

    public Object getObject(int tagType) {
        return this._tagMap.get(Integer.valueOf(tagType));
    }

    public String getTagName(int tagType) {
        HashMap<Integer, String> nameMap = getTagNameMap();
        if (nameMap.containsKey(Integer.valueOf(tagType))) {
            return (String) nameMap.get(Integer.valueOf(tagType));
        }
        String hex = Integer.toHexString(tagType);
        while (hex.length() < 4) {
            hex = "0" + hex;
        }
        return "Unknown tag (0x" + hex + ")";
    }

    public String getDescription(int tagType) {
        if ($assertionsDisabled || this._descriptor != null) {
            return this._descriptor.getDescription(tagType);
        }
        throw new AssertionError();
    }
}
