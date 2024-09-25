package main.java.net.felsstudio.fels.utils;

import main.java.net.felsstudio.fels.Start.Starter;
import main.java.net.felsstudio.fels.lib.Information;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Preprocessor {
    public static String preprocess(String code) {
        List<String> lines = Arrays.asList(code.split("\n"));
        Map<String, String> macros = new HashMap<>();
        macros.put("Fels", "\""+ Information.FELS_VERSION +"\"");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            List<String> parts = Arrays.asList(line.split(" "));
            if (parts.isEmpty()) continue;
            if (parts.get(0).equals("#define")) {
                String id = parts.get(1);
                String replacement = String.join(" ", parts.subList(2, parts.size()));
                if (id.contains("(")) {
                    code = "\n" + "func " + id + " return " + replacement + "\n" + code;
                    continue;
                }
                macros.put(id, replacement);
                continue;
            }
        }
        lines = Arrays.asList(code.split("\n"));
        String result = "";
        for (String line : lines) {
            String buffer = line;
            List<String> parts = Arrays.asList(line.split(" "));
            if (parts.get(0).equals("#undef")) {
                String id = parts.get(1);
                macros.remove(id);
                continue;
            }
            for (Map.Entry<String, String> entry : macros.entrySet()) {
                buffer = buffer.replaceAll(";", " ;");
                buffer = buffer.replaceAll(String.format("(?<!\")(%s)(?!\")", entry.getKey()), entry.getValue()) + "\n";
            }
            result += buffer;
        }
        result = result.replace("#define", "# PROCESSED DEFINE MACROS");
        result = result.replace("#undef", "# PROCESSED UNDEF MACRO");
        return result;
    }
}