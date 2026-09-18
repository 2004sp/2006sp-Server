package com.rs2.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class FileUtil {
    public static final byte[] readBytes(String text2) {
        return FileUtil.readBytes(text2, true);
    }

    public static final byte[] readBytes(String text2, boolean enabled2) {
        try {
            File file = new File(text2);
            int value = (int)file.length();
            byte[] byteValues = new byte[value];
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(text2)));
            dataInputStream.readFully(byteValues, 0, value);
            dataInputStream.close();
            return byteValues;
        }
        catch (Exception exception) {
            if (enabled2) {
                System.out.println("Read Error: " + text2);
            }
            return null;
        }
    }

    public static final void writeBytes(String text2, byte[] byteValues2) {
        try {
            new File(new File(text2).getParent()).mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(text2);
            fileOutputStream.write(byteValues2, 0, byteValues2.length);
            fileOutputStream.close();
            return;
        }
        catch (Throwable throwable) {
            System.out.println("Write Error: " + text2);
            return;
        }
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }
}
