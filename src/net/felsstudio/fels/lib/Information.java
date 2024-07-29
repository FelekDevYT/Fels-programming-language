package net.felsstudio.fels.lib;

import net.felsstudio.fels.annotations.INFO;

import java.util.Date;

public class Information {
    @INFO
    public static final String FELS_VERSION = "0.1";

    @INFO
    public static final String FELS_AUTHOR = "Felek_,FelsStudio";

    @INFO
    public static final String DATE = new Date().toString();
}
