package main.java.net.felsstudio.fels.Start;

import com.raylib.Jaylib;
import main.java.net.felsstudio.fels.lib.Information;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //fels run/help
        if(args[0].equalsIgnoreCase("run")) {
            run(args);
        }else if(args[0].equalsIgnoreCase("help")) {
            help();
        }else if(args[0].equalsIgnoreCase("version")) {
            version();
        };
    }

    private static void run(String[] args) throws IOException {
        RunData run = new RunData();
        //fels run
        for(int i = 1;i < args.length-1;i++){
            switch(args[i]){
                case "-dsv":
                    run.doShowVars = true;
                case "-dst":
                    run.doShowTokens = true;
                case "-dsmt":
                    run.doShowMT = true;
                case "-dsast":
                    run.doShowAST = true;
                case "-file":
                    run.startFile = args[++i];
            }
        }

        Starter.start(run.doShowVars,run.doShowTokens,run.doShowMT,run.doShowAST,run.startFile);
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
