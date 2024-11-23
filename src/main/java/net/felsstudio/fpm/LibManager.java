package main.java.net.felsstudio.fpm;

import com.electronwill.nightconfig.core.file.FileConfig;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class LibManager {
    public static String loadLib(String name){
        String path = "https://raw.githubusercontent.com/FelekDevYT/FelsPackageManager/refs/heads/main/"+name+"/lib.toml";
        System.out.println(path);
        try{
            URL url = new URL(path);

            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(httpConn.getInputStream());
                FileOutputStream out = new FileOutputStream((name+".toml"));

                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                    out.write(dataBuffer, 0, bytesRead);
                }

                out.close();
                in.close();
            } else {
                System.out.println("Error: " + httpConn.getResponseCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String arr = "";

        try{
            arr = Files.readAllLines(Path.of(name+".toml")).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return arr;
    }

    public static String getLibName(String name){
        String path = name+".toml";
        FileConfig cfg = FileConfig.of(path);
        cfg.load();
        return cfg.get("lib-name");
    }

    public static String getLibDescription(String name){
        String path = name+".toml";
        FileConfig cfg = FileConfig.of(path);
        cfg.load();
        return cfg.get("lib-description");
    }

    public static String getLibAuthor(String name){
        String path = name+".toml";
        FileConfig cfg = FileConfig.of(path);
        cfg.load();
        return cfg.get("lib-author");
    }

    public static String getLibVersion(String name){
        String path = name+".toml";
        FileConfig cfg = FileConfig.of(path);
        cfg.load();
        return cfg.get("lib-version");
    }

    public static String[] getLibDeps(String name){
        String path = name+".toml";
        FileConfig cfg = FileConfig.of(path);
        cfg.load();
        return cfg.get("lib-deps");
    }
}
