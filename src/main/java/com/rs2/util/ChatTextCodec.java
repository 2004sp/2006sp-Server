package com.rs2.util;

public final class ChatTextCodec {
    private static char[] CHAR_TABLE = new char[]{' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\u00a3', '$', '%', '\"', '[', ']'};

    public static String decode(byte[] byteValues3, int value3) {
        byte[] byteValues2 = new byte[4096];
        int index = 0;
        int initialValue = -1;
        int index2 = 0;
        while (index2 < value3 << 1) {
            int value2 = byteValues3[index2 / 2] >> 4 - 4 * (index2 % 2) & 0xF;
            if (initialValue == -1) {
                if (value2 < 13) {
                    byteValues2[index++] = (byte)CHAR_TABLE[value2];
                } else {
                    initialValue = value2;
                }
            } else {
                byteValues2[index++] = (byte)CHAR_TABLE[(initialValue << 4) + value2 - 195];
                initialValue = -1;
            }
            ++index2;
        }
        return new String(byteValues2, 0, index);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int encode(String text2, byte[] byteValues2) {
        if (text2.length() > 80) {
            text2 = text2.substring(0, 80);
        }
        text2 = text2.toLowerCase();
        int initialValue = -1;
        int index = 0;
        int index2 = 0;
        while (index2 < text2.length()) {
            char character = text2.charAt(index2);
            int index3 = 0;
            int index4 = 0;
            while (index4 < 61) {
                if (character == CHAR_TABLE[index4]) {
                    index3 = index4;
                    break;
                }
                ++index4;
            }
            if (index3 > 12) {
                index3 += 195;
            }
            if (initialValue == -1) {
                if (index3 < 13) {
                    initialValue = index3;
                } else {
                    byteValues2[index++] = (byte)index3;
                }
            } else if (index3 < 13) {
                byteValues2[index++] = (byte)((initialValue << 4) + index3);
                initialValue = -1;
            } else {
                byteValues2[index++] = (byte)((initialValue << 4) + (index3 >> 4));
                initialValue = index3 & 0xF;
            }
            ++index2;
        }
        if (initialValue != -1) {
            byteValues2[index++] = (byte)(initialValue << 4);
        }
        return index;
    }
}

