package net.felsstudio.fels.utils;

import net.felsstudio.fels.lib.ValueUtils;
import net.felsstudio.fels.lib.Variables;
import net.felsstudio.fpm.FelsPackageManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Preprocessor {
    // Хранит определения макросов
    private final Map<String, String> macros = new HashMap<>();
    private boolean isRegion = false;

    public String process(String source) {
        // Разделите исходный код на строки для обработки
        String[] lines = source.split("\n");
        StringBuilder processedCode = new StringBuilder();

        for (String line : lines) {
            // Обработка макросов
            if (line.trim().startsWith("#define")) {
                handleDefine(line);
            }else if(line.trim().startsWith("#comment")){

            }else if(line.trim().startsWith("#undef")){
                String[] parts = line.split("\\s+", 3);
                macros.remove(parts[1]);
            }else if(line.trim().startsWith("#defvar")){
                String[] parts = line.split("\\s+", 3);
                Variables.set(parts[1], ValueUtils.toValue(parts[2]));
            }else if(line.trim().startsWith("#cmdc")){
                String[] parts = line.split("\\s+");
                parts[0] = "";
                StringBuilder sb = new StringBuilder();
                for (String part : parts) {
                    sb.append(part+" ");
                }
                CmdExecuter.execute(sb.toString());
            }else if(line.trim().startsWith("#error")){
                String[] parts = line.split("\\s+");
                parts[0] = "";
                StringBuilder sb = new StringBuilder();
                for (String part : parts) {
                    sb.append(part);
                }
                throw new RuntimeException(sb.toString().trim().substring(0, sb.toString().length()-1));
            }else if(line.trim().startsWith("#region")){
                isRegion = true;
            }else if(line.trim().startsWith("#endregion")){
                isRegion = false;
            }else if(line.trim().startsWith("#linklude")){
                String[] parts = line.trim().split("\\s+", 2);
                new File(parts[1]).mkdirs();
                System.out.println(parts[0]+","+parts[1]);
                FelsPackageManager.loadExtLibrary(parts[1]);
                processedCode.append("import ").append("\"").append(parts[1], 1, parts[1].length()-1).append("\"\n");
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

        if(isRegion) throw new RuntimeException("region not close");

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