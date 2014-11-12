/*
* Copyright (C) 2007 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Adapted to be a simpler data bundle which only holds primitive data values.
* Konrad Markus <konker@luxvelocitas.com>
*/

package fi.hiit.util;

/*[XXX: might this be useful in the future?] import java.io.Serializable;*/
import java.util.HashMap;
import java.util.Set;

/**
 * A mapping from String values to various types.
 * In effect, a glorified HashMap.
 *
 */
public final class DataBundle implements Cloneable {
    static final boolean DEBUG = false;

    private HashMap<String, Object> mMap = null;

    /**
     * Constructs a new, empty DataBundle.
     */
    public DataBundle() {
        mMap = new HashMap<String, Object>();
    }

    /**
     * Constructs a new, empty DataBundle sized to hold the given number of
     * elements. The DataBundle will grow as needed.
     *
     * @param capacity the initial capacity of the DataBundle
     */
    public DataBundle(int capacity) {
        mMap = new HashMap<String, Object>(capacity);
    }

    /**
     * Constructs a DataBundle containing a copy of the mappings from the given
     * DataBundle.
     *
     * @param b a DataBundle to be copied.
     */
    public DataBundle(DataBundle b) {
        if (b.mMap != null) {
            mMap = new HashMap<String, Object>(b.mMap);
        } else {
            mMap = null;
        }
    }

    /**
     * Clones the current DataBundle. The internal map is cloned, but the keys and
     * values to which it refers are copied by reference.
     */
    @Override
    public Object clone() {
        return new DataBundle(this);
    }

    /**
     * Returns the number of mappings contained in this DataBundle.
     *
     * @return the number of mappings as an int.
     */
    public int size() {
        return mMap.size();
    }

    /**
     * Returns true if the mapping of this DataBundle is empty, false otherwise.
     */
    public boolean isEmpty() {
        return mMap.isEmpty();
    }

    /**
     * Removes all elements from the mapping of this DataBundle.
     */
    public void clear() {
        mMap.clear();
    }

    /**
     * Returns true if the given key is contained in the mapping
     * of this DataBundle.
     *
     * @param key a String key
     * @return true if the key is part of the mapping, false otherwise
     */
    public boolean containsKey(String key) {
        return mMap.containsKey(key);
    }

    /**
     * Returns the entry with the given key as an object.
     *
     * @param key a String key
     * @return an Object, or null
     */
    public Object get(String key) {
        return mMap.get(key);
    }

    /**
     * Removes any entry with the given key from the mapping of this DataBundle.
     *
     * @param key a String key
     */
    public void remove(String key) {
        mMap.remove(key);
    }

    /**
     * Inserts all mappings from the given DataBundle into this DataBundle.
     *
     * @param map a DataBundle
     */
    public void putAll(DataBundle dataBundle) {
        mMap.putAll(dataBundle.mMap);
    }

    /**
     * Returns a Set containing the Strings used as keys in this DataBundle.
     *
     * @return a Set of String keys
     */
    public Set<String> keySet() {
        return mMap.keySet();
    }

    /**
     * Inserts a Boolean value into the mapping of this DataBundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key a String, or null
     * @param value a Boolean, or null
     */
    public void putBoolean(String key, boolean value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a byte value into the mapping of this DataBundle, replacing
     * any existing value for the given key.
     *
     * @param key a String, or null
     * @param value a byte
     */
    public void putByte(String key, byte value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a char value into the mapping of this DataBundle, replacing
     * any existing value for the given key.
     *
     * @param key a String, or null
     * @param value a char, or null
     */
    public void putChar(String key, char value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a short value into the mapping of this DataBundle, replacing
     * any existing value for the given key.
     *
     * @param key a String, or null
     * @param value a short
     */
    public void putShort(String key, short value) {
        mMap.put(key, value);
    }

    /**
     * Inserts an int value into the mapping of this DataBundle, replacing
     * any existing value for the given key.
     *
     * @param key a String, or null
     * @param value an int, or null
     */
    public void putInt(String key, int value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a long value into the mapping of this DataBundle, replacing
     * any existing value for the given key.
     *
     * @param key a String, or null
     * @param value a long
     */
    public void putLong(String key, long value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a float value into the mapping of this DataBundle, replacing
     * any existing value for the given key.
     *
     * @param key a String, or null
     * @param value a float
     */
    public void putFloat(String key, float value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a double value into the mapping of this DataBundle, replacing
     * any existing value for the given key.
     *
     * @param key a String, or null
     * @param value a double
     */
    public void putDouble(String key, double value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a String value into the mapping of this DataBundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key a String, or null
     * @param value a String, or null
     */
    public void putString(String key, String value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a CharSequence value into the mapping of this DataBundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key a String, or null
     * @param value a CharSequence, or null
     */
    public void putCharSequence(String key, CharSequence value) {
        mMap.put(key, value);
    }

    /**
     * Inserts a DataBundle value into the mapping of this DataBundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key a String, or null
     * @param value a DataBundle object, or null
     */
    public void putDataBundle(String key, DataBundle value) {
        mMap.put(key, value);
    }

    /**
     * Returns the value associated with the given key, or false if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a boolean value
     */
    public boolean getBoolean(String key) {
        if (DEBUG) System.err.println("Getting boolean in "
                + Integer.toHexString(System.identityHashCode(this)));
        return getBoolean(key, false);
    }

    // Log a message if the value was non-null but not of the expected type
    private void typeWarning(String key, Object value, String className,
        Object defaultValue, ClassCastException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("Key ");
        sb.append(key);
        sb.append(" expected ");
        sb.append(className);
        sb.append(" but value was a ");
        sb.append(value.getClass().getName());
        sb.append(".  The default value ");
        sb.append(defaultValue);
        sb.append(" was returned.");

        /*[FIXME: logging]*/
        System.err.println(sb.toString());
        System.err.println("Attempt to cast generated internal exception:" + e);
    }

    private void typeWarning(String key, Object value, String className,
        ClassCastException e) {
        typeWarning(key, value, className, "<null>", e);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return a boolean value
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Boolean) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Boolean", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or (byte) 0 if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a byte value
     */
    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return a byte value
     */
    public Byte getByte(String key, byte defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Byte) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Byte", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or (char) 0 if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a char value
     */
    public char getChar(String key) {
        return getChar(key, (char) 0);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return a char value
     */
    public char getChar(String key, char defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Character) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Character", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or (short) 0 if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a short value
     */
    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return a short value
     */
    public short getShort(String key, short defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Short) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Short", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or 0 if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return an int value
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return an int value
     */
    public int getInt(String key, int defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Integer) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Integer", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or 0L if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a long value
     */
    public long getLong(String key) {
        return getLong(key, 0L);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return a long value
     */
    public long getLong(String key, long defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Long) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Long", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or 0.0f if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a float value
     */
    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return a float value
     */
    public float getFloat(String key, float defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Float) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Float", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or 0.0 if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a double value
     */
    public double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @param defaultValue Value to return if key does not exist
     * @return a double value
     */
    public double getDouble(String key, double defaultValue) {
        Object o = mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Double) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Double", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a String value, or null
     */
    public String getString(String key) {
        final Object o = mMap.get(key);
        try {
            return (String) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "String", e);
            return null;
        }
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String, or null
     * @param defaultValue Value to return if key does not exist
     * @return the String value associated with the given key, or defaultValue
     *     if no valid String object is currently mapped to that key.
     */
    public String getString(String key, String defaultValue) {
        final String s = getString(key);
        return (s == null) ? defaultValue : s;
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a CharSequence value, or null
     */
    public CharSequence getCharSequence(String key) {
        final Object o = mMap.get(key);
        try {
            return (CharSequence) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "CharSequence", e);
            return null;
        }
    }

    /**
     * Returns the value associated with the given key, or defaultValue if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String, or null
     * @param defaultValue Value to return if key does not exist
     * @return the CharSequence value associated with the given key, or defaultValue
     *     if no valid CharSequence object is currently mapped to that key.
     */
    public CharSequence getCharSequence(String key, CharSequence defaultValue) {
        final CharSequence cs = getCharSequence(key);
        return (cs == null) ? defaultValue : cs;
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a DataBundle value, or null
     */
    public DataBundle getBundle(String key) {
        Object o = mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (DataBundle) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Bundle", e);
            return null;
        }
    }


    @Override
    public synchronized String toString() {
        return "DataBundle[" + mMap.toString() + "]";
    }
}

