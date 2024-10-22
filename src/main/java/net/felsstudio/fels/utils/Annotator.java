package main.java.net.felsstudio.fels.utils;

public class Annotator {

    static String[] annotations = new String[]{"class","comment","function","doc","import","preprocess","noUse","NULL","FPM","returns","see","version","science"};
    public static void parseAnotation(String source){
        String[] lines = source.split("\n");

        boolean isEnd = false;

        for(String line : lines){
            String[] parts = line.split("\\s+");
            if(line.trim().startsWith("@")) {
                for (String annotation : annotations) {
                    if (line.trim().startsWith("@" + annotation)) {
                        isEnd = true;
                    }
                }
                if(!isEnd) throw new RuntimeException("Unknown annotation: " + line);
            }
        }
    }
}
