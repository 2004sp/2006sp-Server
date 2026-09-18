package com.rs2.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimestampedPrintStream
extends PrintStream {
    private BufferedWriter logWriter;
    private DateFormat dateFormat = new SimpleDateFormat();

    public TimestampedPrintStream(OutputStream outputStream, String text2) throws IOException {
        super(outputStream);
        this.logWriter = new BufferedWriter(new FileWriter(text2, true));
    }

    public TimestampedPrintStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public final void println(String text2) {
        text2 = "[" + this.dateFormat.format(new Date()) + "]: " + text2;
        super.println(text2);
        try {
            if (this.logWriter == null) {
                return;
            }
            this.logWriter.write(text2);
            this.logWriter.newLine();
            this.logWriter.flush();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }
}

