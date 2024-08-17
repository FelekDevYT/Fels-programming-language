package main.java.net.felsstudio.fels.parser.Console;

import java.io.File;

public sealed interface OutputSettings permits ConsoleOutputSettings, StringOutputSettings {

    String newline();

    void print(String value);

    void print(Object value);

    void println();

    void println(String value);

    void println(Object value);

    String getText();

    void error(Throwable throwable);

    void error(CharSequence value);

    File fileInstance(String path);

}