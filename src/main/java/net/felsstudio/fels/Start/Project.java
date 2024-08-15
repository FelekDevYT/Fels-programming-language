package main.java.net.felsstudio.fels.Start;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;

public class Project {
    private String name = "example";
    private String version = "1.0.0";
    private String creator = "FelsStudio";

    public Project(String name, String version, String creator) {
        this.name = name;
        this.version = version;
        this.creator = creator;
    }

    public boolean isExists(String name) {
        File f = new File(name);
        return f.exists();
    }

    public void createFile(String name){
        new File(this.name+"\\src\\"+name);
    }

    public void createDir(String name){
        new File(this.name+"\\src\\"+name).mkdirs();
    }

    public boolean saveConf(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.name+"\\run.fsf"))){
                /*
                --NAME--
                --VERSION--
                --AUTHOR--
                 */
            bw.write(this.name+"\n"+
                    this.version+"\n"+
                    this.creator);
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public boolean createProject(){
        if(isExists(this.name)){
            return false;
        }else{
            new File(this.name).mkdirs();
            new File(this.name+"\\src").mkdirs();
            new File(this.name+"\\run.fsf");
            new File(this.name+"\\src\\main.fels");
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.name+"\\run.fsf"))){
                /*
                --NAME--
                --VERSION--
                --AUTHOR--
                 */
                bw.write(this.name+"\n"+
                            this.version+"\n"+
                            this.creator);
            }catch(IOException e){
                return false;
            }

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.name+"\\src\\main.fels"))){
                bw.write("using [\"sfm\"]");
                bw.write("\n\n");
                bw.write("println(\"Hello,World!\")");
                bw.write("\n");
            } catch (IOException e) {
                return false;
            }

        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
