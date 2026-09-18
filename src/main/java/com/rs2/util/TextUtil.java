package com.rs2.util;

import java.nio.ByteBuffer;

public final class TextUtil {
    private static char[] NAME_HASH_CHAR_TABLE = new char[]{'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=', ':', ';', '.', '>', '<', ',', '\"', '[', ']', '|', '?', '/', '`'};

    public static String decodeNameHash(long value3) {
        int index = 0;
        char[] characterValues = new char[12];
        while (value3 != 0L) {
            long value2 = value3;
            characterValues[11 - index++] = NAME_HASH_CHAR_TABLE[(int)(value2 - (value3 /= 37L) * 37L)];
        }
        return new String(characterValues, 12 - index, index);
    }

    public static String formatCombatLevel(int level, int value22) {
        if ((level -= value22) < 0 && level > -10) {
            return "@gr2@" + value22;
        }
        if (level == 0) {
            return "@yel@" + value22;
        }
        if (level > 0 && level < 10) {
            return "@ora@" + value22;
        }
        if (level > 0) {
            return "@red@" + value22;
        }
        return "@gre@" + value22;
    }

    public static long encodeNameHash(String text2) {
        long value = 0L;
        int index = 0;
        while (index < text2.length() && index < 12) {
            char character = text2.charAt(index);
            value *= 37L;
            if (character >= 'A' && character <= 'Z') {
                value += character + '\u0001' - 65;
            } else if (character >= 'a' && character <= 'z') {
                value += character + '\u0001' - 97;
            } else if (character >= '0' && character <= '9') {
                value += character + 27 - 48;
            }
            ++index;
        }
        while (value % 37L == 0L && value != 0L) {
            value /= 37L;
        }
        return value;
    }

    public static String capitalizeFirst(String text2) {
        if ((text2 = text2.toLowerCase()).length() <= 1) {
            return text2.toUpperCase();
        }
        text2 = String.valueOf(text2.substring(0, 1).toUpperCase()) + text2.substring(1);
        return text2;
    }

    public static String formatDisplayName(String text2) {
        text2 = text2.replace(" ", "_");
        if (text2.length() > 0) {
            char[] chars = text2.toCharArray();
            int index = 0;
            while (index < chars.length) {
                if (chars[index] == '_') {
                    chars[index] = ' ';
                    if (index + 1 < chars.length && chars[index + 1] >= 'a' && chars[index + 1] <= 'z') {
                        chars[index + 1] = (char)(chars[index + 1] + 'A' - 'a');
                    }
                }
                ++index;
            }
            if (chars[0] >= 'a' && chars[0] <= 'z') {
                chars[0] = (char)(chars[0] + 'A' - 'a');
            }
            return new String(chars);
        }
        return text2;
    }

    public static String prependIndefiniteArticle(String text4) {
        String text2 = text4.toUpperCase();
        String text3 = "a";
        char character = text2.charAt(0);
        if (character == 'A' || character == 'E' || character == 'I' || character == 'O' || character == 'U') {
            text3 = "an";
        }
        text4 = String.valueOf(text3) + " " + text4;
        return text4;
    }

    public static String readLine(ByteBuffer byteBuffer) {
        byte value;
        StringBuilder stringBuilder = new StringBuilder();
        while (byteBuffer.hasRemaining() && (value = byteBuffer.get()) != 10) {
            stringBuilder.append((char)value);
        }
        return stringBuilder.toString();
    }
}
