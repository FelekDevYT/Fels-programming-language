package main.java.net.felsstudio.fpm;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FelsPackageManager {

    public static void loadExtLibrary(String name){
        String path = "https://raw.githubusercontent.com/FelekDevYT/FelsPackageManager/refs/heads/main/"+(name.substring(1,name.length()-1));
        try {
            // Создаем URL объект
            URL url = new URL(path);
            // Открываем соединение
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            // Проверяем код ответа
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Открываем InputStream
                InputStream in = new BufferedInputStream(httpConn.getInputStream());
                // Создаем FileOutputStream для сохранения файла
                FileOutputStream out = new FileOutputStream((name.substring(1,name.length()-1)));

                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                // Читаем данные и записываем их в файл
                while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                    out.write(dataBuffer, 0, bytesRead);
                }

                // Закрываем потоки
                out.close();
                in.close();
            } else {
                System.out.println("Error: " + httpConn.getResponseCode());
            }

            // Закрываем соединение
            httpConn.disconnect();
        } catch (IOException e) {
            System.out.println("Error on load: " + e.getMessage());
        }
    }
}