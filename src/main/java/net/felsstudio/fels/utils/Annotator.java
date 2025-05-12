package net.felsstudio.fels.utils;

public class Annotator {

    static String[] annotations = new String[]{"class","param","comment","function","doc","imports","preprocess","noUse","NULL","FPM","returns","see","version","science","creator"};
    public static String parseAnotation(String source){
        String[] lines = source.split("\n");

        boolean isEnd = false;

        int use = 0;

        for(String line : lines){
            String[] parts = line.split("\\s+");
            if(line.trim().startsWith("@")) {
                for (String annotation : annotations) {
                    if (line.trim().startsWith("@" + annotation)) {
                        lines[use] = "";
                        isEnd = true;
                        break;
                    }
                }
                if (!isEnd) throw new RuntimeException("Unknown annotation: " + line);
            }
            use++;
        }
        StringBuilder out = new StringBuilder();
        for (String l : lines){
            out.append(l+"\n");
        }
        return out.toString();
    }
}
