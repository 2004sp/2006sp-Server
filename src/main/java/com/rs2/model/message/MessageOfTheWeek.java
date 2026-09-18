package com.rs2.model.message;

import com.rs2.Server;
import com.rs2.model.GameplayHelper;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.CountingDataOutputStream;
import com.rs2.util.FileUtil;
import com.rs2.util.GameUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import org.joda.time.DateTime;

public final class MessageOfTheWeek {
    private static int antivirusMessageInterfaceId = 5993;
    private static int holidayMessageInterfaceId = 15767;
    private static int passwordMessageInterfaceId = 15774;
    private static int christmasMessageInterfaceId = 15819;
    private int interfaceId;
    private String[] lines;
    private String title;
    private static MessageOfTheWeek[] normalMessages = new MessageOfTheWeek[]{new MessageOfTheWeek(passwordMessageInterfaceId, false, "Your password is only as safe as your computer.", "Install anti-virus software!"), new MessageOfTheWeek(antivirusMessageInterfaceId, false, "Out of date anti-virus software is useless.", "Update it often and run regular scans!")};
    private static MessageOfTheWeek halloweenMessage = new MessageOfTheWeek(holidayMessageInterfaceId, false, "Halloween has arrived to RuneScape!", "");
    private static MessageOfTheWeek christmasMessage = new MessageOfTheWeek(christmasMessageInterfaceId, false, "JaGeX wishes you a Merry Christmas", "and a Happy New Year!");
    private static MessageOfTheWeek easterMessage = new MessageOfTheWeek(holidayMessageInterfaceId, false, "Easter has arrived to RuneScape!", "");

    private MessageOfTheWeek(int interfaceId, boolean enabled2, String ... lines) {
        this.interfaceId = interfaceId;
        this.lines = lines;
        this.title = "Message of the week";
    }

    public static MessageOfTheWeek getMessageForIndex(int index) {
        if (Server.halloweenEventActive) {
            return halloweenMessage;
        }
        if (Server.christmasEventActive) {
            return christmasMessage;
        }
        if (Server.easterEventActive) {
            return easterMessage;
        }
        return normalMessages[index];
    }

    private static void saveCurrentMessageIndex() {
        Object value = new File("./data/messageOfTheWeek.dat");
        ((File)value).delete();
        try {
            value = new CountingDataOutputStream(new FileOutputStream("./data/messageOfTheWeek.dat"));
            ((CountingDataOutputStream)value).writeLong(System.currentTimeMillis());
            ((CountingDataOutputStream)value).writeUnsignedByte(Server.messageOfTheWeekIndex);
            ((FilterOutputStream)value).close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void loadAndRotateMessage() {
        long value;
        DateTime dateTime;
        long value2 = 0L;
        long value3 = System.currentTimeMillis();
        if (FileUtil.exists("./data/messageOfTheWeek.dat")) {
            try {
                Object value4 = FileUtil.readBytes("./data/messageOfTheWeek.dat");
                ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])value4);
                value4 = byteArrayReader;
                value2 = byteArrayReader.readLong();
                Server.messageOfTheWeekIndex = ((ByteArrayReader)value4).readUnsignedByte();
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        if (Server.halloweenEventActive || Server.christmasEventActive || Server.easterEventActive) {
            Server.messageOfTheWeekIndex = 250;
            MessageOfTheWeek.saveCurrentMessageIndex();
            return;
        }
        if (GameplayHelper.getDaysBetweenMidnights(value2, value3) > 0 && (dateTime = new DateTime(value = value3)).dayOfWeek().get() == 1 || Server.messageOfTheWeekIndex == 250 || value2 == 0L) {
            Server.messageOfTheWeekIndex = GameUtil.randomInt(normalMessages.length);
            MessageOfTheWeek.saveCurrentMessageIndex();
        }
    }

    public final int getInterfaceId() {
        return this.interfaceId;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String[] getLines() {
        return this.lines;
    }
}

