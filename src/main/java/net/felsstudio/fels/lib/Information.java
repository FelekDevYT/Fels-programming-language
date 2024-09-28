package main.java.net.felsstudio.fels.lib;

import main.java.net.felsstudio.fels.annotations.INFO;

import java.util.Date;

public class Information {
    @INFO
    public static final String FELS_VERSION = "6 PRE-RELEASE";

    @INFO
    public static final String FELS_AUTHOR = "FelekDevYT,FelsStudio";

    @INFO
    public static final String DATE = new Date().toString();

    @INFO
    public static final String LOADER_VERSION = "1.4";
}
