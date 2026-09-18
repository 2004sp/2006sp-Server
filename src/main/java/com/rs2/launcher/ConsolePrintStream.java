package com.rs2.launcher;

import java.io.PrintStream;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public final class ConsolePrintStream
extends PrintStream {
    private JTextArea outputTextArea;

    public ConsolePrintStream(JTextArea jTextArea, JScrollPane jScrollPane) {
        super(System.out);
        this.outputTextArea = jTextArea;
    }

    @Override
    public final void println(Object value2) {
        this.appendLine(value2);
    }

    private void appendLine(Object value2) {
        this.outputTextArea.append(value2.toString() + "\n");
    }

    @Override
    public final void println(String text2) {
        this.appendLine(text2);
    }

    @Override
    public final void println() {
        this.println("println\n");
    }

    @Override
    public final void print(Object value2) {
        this.appendText(value2);
    }

    private void appendText(Object value2) {
        this.outputTextArea.append(value2.toString());
    }

    @Override
    public final void print(String text2) {
        this.appendText(text2);
    }
}

