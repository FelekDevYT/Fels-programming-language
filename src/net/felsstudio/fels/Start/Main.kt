package net.felsstudio.fels.Start;

import java.io.IOException;

/**
 * @author felek
 *
 */
public final class Main {

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            Starter.start(false,true,true,false,"prog.fels");
        }else if (args.length == 1) {
            Starter.start(false,false,true,false,args[0]);
        }else if(args.length == 2) {
            Starter.start(Boolean.parseBoolean(args[0]),false,true,false,args[1]);
        }else if (args.length == 3) {
            Starter.start(Boolean.parseBoolean(args[0]),Boolean.parseBoolean(args[1]),true,false,args[2]);
        }else if (args.length == 4) {
            Starter.start(Boolean.parseBoolean(args[0]),Boolean.parseBoolean(args[1]),Boolean.parseBoolean(args[2]),false,args[3]);
        }else if (args.length == 5) {
            Starter.start(Boolean.parseBoolean(args[0]),Boolean.parseBoolean(args[1]),Boolean.parseBoolean(args[2]),false,args[4]);
        }else if (args.length == 6) {
            Starter.start(Boolean.parseBoolean(args[0]),Boolean.parseBoolean(args[1]),Boolean.parseBoolean(args[2]),Boolean.parseBoolean(args[4]),args[5]);
        }
    }
}
