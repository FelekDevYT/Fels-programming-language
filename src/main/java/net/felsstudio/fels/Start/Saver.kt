package main.java.net.felsstudio.fels.Start;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Saver {

    public void save(vals v){
        new File("saver.fsf");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("saver.fsf"))){
            bw.write(String.valueOf(v.doShowVars)+"\n");
            bw.write(String.valueOf(v.doShowTokens)+"\n");
            bw.write(String.valueOf(v.doShowMe)+"\n");
            bw.write(String.valueOf(v.doShowAst)+"\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String[] read() throws IOException {
        String[] s = Files.readAllLines(Paths.get("saver.fsf")).toArray(new String[0]);
        return s;
    }
}
