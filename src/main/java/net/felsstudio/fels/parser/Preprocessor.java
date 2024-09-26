package main.java.net.felsstudio.fels.parser;

import jdk.jshell.spi.SPIResolutionException;

import java.util.HashMap;
import java.util.Map;

public class Preprocessor {
    // Хранит определения макросов
    private Map<String, String> macros = new HashMap<>();

    public String process(String source) {
        // Разделите исходный код на строки для обработки
        String[] lines = source.split("\n");
        StringBuilder processedCode = new StringBuilder();

        for (String line : lines) {
            // Обработка макросов
            if (line.startsWith("#define")) {
                handleDefine(line);
            }else if (line.trim().startsWith("#include")) {
                String[] parts = line.trim().split("\\s+", 2);
                if (parts.length == 2) {
                    processedCode.append("import ").append(parts[1]).append("\n");
                }
            }else {
                // Замените макросы в текущей строке
                for (Map.Entry<String, String> entry : macros.entrySet()) {
                    line = line.replace(entry.getKey(), entry.getValue());
                }
                processedCode.append(line).append("\n");
            }
        }

        return processedCode.toString();
    }

    private void handleDefine(String line) {
        // Удаляем #define и лишние пробелы
        String[] parts = line.split("\\s+", 3);
        if (parts.length == 3) {
            String macroName = parts[1];
            String macroValue = parts[2];

            // Сохраняем макрос в мапу
            macros.put(macroName, macroValue);
        }
    }
}
