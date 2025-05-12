package net.felsstudio.fels.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdExecuter {
    public static void execute(String cmd){
        // Команда, которую вы хотите выполнить
        String command = cmd; // Замените на вашу команду

        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command}); // Для Windows используйте "cmd.exe", "/c"

            // Чтение вывода команды
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Ждем завершения процесса
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
