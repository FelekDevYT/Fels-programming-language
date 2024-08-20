package main.java.net.felsstudio.fels.Start;

import main.java.net.felsstudio.fels.lib.Information;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;



public class Main {
    public static void main(String[] args){

        System.out.println("=========================================================================================");
        System.out.println("                    WELCOME IN FELS                       ");
        System.out.println("setAS <DOSHOWVARS> <DOSHOWTOKENS> <DOSHOWME> <SOSHOWAST> - set auto start setting");
        System.out.println("debugger <PROJECT_NAME> - open debug setting of <PROJECT_NAME> project");
        System.out.println("run <NAME> - run you project");
        System.out.println("new <NAME> <VERSION> <AUTHOR> - create new project");
        System.out.println("setArgs <DOSHOWVARS> <DOSHOWTOKENS> <DOSHOWME> <SOSHOWAST> - set args");
        System.out.println("help - about of FELS");
        System.out.println("version - version of FELS");
        System.out.println("cls - clear console");
        System.out.println("exit - exit from program");
        System.out.println("=========================================================================================");

        var scan = new Scanner(System.in);

        var v = new vals();

        if(Files.exists(Paths.get("as.fsf"))){
            try(BufferedReader br = Files.newBufferedReader(Paths.get("as.fsf"))) {
                v.doShowVars = Boolean.parseBoolean(br.readLine().toString());
                v.doShowTokens = Boolean.parseBoolean(br.readLine().toString());
                v.doShowMe = Boolean.parseBoolean(br.readLine().toString());
                v.doShowAst = Boolean.parseBoolean(br.readLine().toString());
            }catch(IOException e){
                e.printStackTrace();
            }
            System.out.println("Settings up automatically from as.fsf file");
        }

        while (true) {
            System.out.print("FELS v"+ Information.FELS_VERSION+">>>");
            var line = scan.nextLine().split(" ");

            switch (line[0]) {
                case "debugger" ->{
                    System.out.println("This feature in development");
                    //TODO("CREATE THIS DEBUGGER!!!")
                }

                case "setAS" -> {
                    try {
                        new File("as.fsf");

                        try(BufferedWriter bw = new BufferedWriter(new FileWriter("as.fsf"))){
                            bw.write(line[1] + "\n");
                            bw.write(line[2] + "\n");
                            bw.write(line[3] + "\n");
                            bw.write(line[4] + "\n");
                        }catch(IOException exc){
                            exc.printStackTrace();
                        }
                    } catch (Exception exc) {
                        System.err.println("FELS [ERROR] " + exc.getMessage());
                    }
                    System.out.println("FELS [ SUCCESSFUL ] setting up auto setting");
                }

                case "run" ->{
                    try {
                        Starter.start(v.doShowVars, v.doShowTokens, v.doShowMe, v.doShowAst, line[1]+"\\src\\main.fels");
                    } catch (Exception exc) {
                        System.err.println("FELS [ERROR] " + exc.getMessage());
                    }
                }

                case "setArgs" -> {
                    if (line[1].equals(":f")) {
                        try {
                            var lines = new Saver().read();
                            v.doShowVars = Boolean.parseBoolean(lines[0]);
                            v.doShowTokens = Boolean.parseBoolean(lines[1]);
                            v.doShowMe = Boolean.parseBoolean(lines[2]);
                            v.doShowAst = Boolean.parseBoolean(lines[3]);
                            System.out.println("FELS [ SUCCESSFUL ] setting up setting");
                            continue;
                        } catch (IOException exc) {
                            System.err.println("FELS [ERROR] " + exc.getMessage());
                        }
                    }
                    try {
                        var lines = new Saver().read();
                        v.doShowVars = Boolean.parseBoolean(lines[0]);
                        v.doShowTokens = Boolean.parseBoolean(lines[1]);
                        v.doShowMe = Boolean.parseBoolean(lines[2]);
                        v.doShowAst = Boolean.parseBoolean(lines[3]);
                    } catch (Exception exc) {
                        System.err.println("FELS [ERROR] " + exc.getMessage());
                    }
                    System.out.println("FELS [ SUCCESSFUL ] setting up setting");
                }

                case "help" -> {
                    System.out.println("FELS programming language V."+Information.FELS_VERSION);
                    System.out.println("Copyright $"+Information.FELS_AUTHOR);
                    System.out.println("FELS console V.0.1");
                }

                case "version" -> System.out.println("FELS programming language V."+Information.FELS_VERSION);
                case "exit" -> {
                    System.out.println("FELS [ SUCCESSFUL ] exiting from console");
                    System.exit(0);
                }

                case "save" -> {
                    var s = new Saver();
                    s.save(v);
                    System.out.println("FELS [ SUCCESSFUL ] run setting save to saver.fsf");
                }

                case "cls" -> {
                    System.out.println("\u001b[H\u001b[2J");
                    System.out.flush();
                }

                case "new" ->{
                    var p = new Project(line[1],line[2],line[3]);
                    p.createProject();
                    System.out.println("FELS [ SUCCESSFUL ] setting up project");
                }
            }
        }
    }
}
