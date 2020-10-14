package com.wnana.uuid;

import java.net.InetAddress;
import java.util.Map;

/**
 * 
 * @author  智凡
 * @email  hnhnwang_3250@126.com
 * 
 */
public class SequenceUUID {

    private static final int IP;

    private static short counter = (short) 0;

    static {
        int ip2Int;
        try {
            ip2Int = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ip2Int = 0;
        }
        IP = ip2Int;
    }

    public static String generate() {
        return format(getIP()) +
                format(getJVM()) +
                format(getHiTime()) +
                format(getLoTime()) +
                format(getCount());
    }

    private static String format(int intValue) {
        String formatted = Integer.toHexString(intValue);
        StringBuilder buf = new StringBuilder("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    private static String format(short shortValue) {
        String formatted = Integer.toHexString(shortValue);
        StringBuilder buf = new StringBuilder("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    /**
     * Unique across JVMs on this machine (unless they load this class
     * in the same quater second - very unlikely)
     */
    private static int getJVM() {
        return (int) (System.currentTimeMillis() >>> 8);
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     */

    private static short getCount() {
        synchronized (SequenceUUID.class) {
            if (counter < 0) counter = 0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    private static int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    private static short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    private static int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    /**
     * Get the config value as a {@link String}
     *
     * @param name   The config setting name.
     * @param values The map of config values
     * @return The value, or null if not found
     */
    private String getString(String name, Map values) {
        Object value = values.get(name);
        if (value == null) {
            return null;
        }
        if (String.class.isInstance(value)) {
            return (String) value;
        }
        return value.toString();
    }

    /**
     * Custom algorithm used to generate an int from a series of bytes.
     * <p/>
     * NOTE : this is different than interpreting the incoming bytes as an int value!
     *
     * @param bytes The bytes to use in generating the int.
     * @return The generated int.
     */
    private static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

}
