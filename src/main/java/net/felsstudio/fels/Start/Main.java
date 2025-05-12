package net.felsstudio.fels.Start;

import net.felsstudio.fels.lib.Information;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //fels run/help
        if(args[0].equalsIgnoreCase("run")) {
            run(args);
        }else if(args[0].equalsIgnoreCase("help")) {
            help();
        }else if(args[0].equalsIgnoreCase("version")) {
            version();
        }else if(args[0].equalsIgnoreCase("proj")) {

        }
    }

    private static void run(@NotNull String[] args) throws IOException {
        RunData run = new RunData();
        //fels run
        for(int i = 1;i < args.length-1;i++){
            switch(args[i]){
                case "-dsv":
                    run.doShowVars = true;
                    break;
                case "-dst":
                    run.doShowTokens = true;
                    break;
                case "-dsmt":
                    run.doShowMT = true;
                    break;
                case "-dsast":
                    run.doShowAST = true;
                    break;
                case "-file":
                    run.startFile = args[++i];
                    break;
                case "-dp":
                    run.doPreprocess = true;
                    break;
                case "-da":
                    run.doEnableAnnotations = true;
                    break;
            }
        }

        Starter.start(run.doShowVars,run.doShowTokens,run.doShowMT,run.doShowAST,run.doPreprocess,run.doEnableAnnotations,run.startFile);
    }

    private static void help(){
        System.out.println("====FELS PROGRAMMING LANGUAGE====\n"+
                "FELS VERSION>>> "+ Information.FELS_VERSION+
                "\nFELS AUTHOR>>> "+Information.FELS_AUTHOR+
                "\nFELS LOADER VERSION>>> "+Information.LOADER_VERSION+
                "\nDATE>>> "+Information.DATE);
    }

    private static void version(){
        System.out.println("FELS VERSION>>> "+Information.FELS_VERSION);
    }
}