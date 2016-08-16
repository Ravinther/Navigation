package com.sygic.aura.blackbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FFmpegHelper {
    public static File getIntermediateFile(File input) {
        return new File(input.getParentFile(), input.getName().replace('.', '_') + "_inter.ts");
    }

    private static List<String> readFiles(String[] infoPaths) {
        List<String> result = new ArrayList();
        for (String infoFile : infoPaths) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(infoFile));
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    result.add(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void createSubtitlesFile(String[] infoPaths, File output) {
        List<String> infoLines = readFiles(infoPaths);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(output));
            for (int i = 0; i < infoLines.size(); i++) {
                if (infoLines.get(i) != null) {
                    String[] infos = ((String) infoLines.get(i)).split("\\|", -1);
                    if (infos != null) {
                        pw.println(i + 1);
                        pw.printf("00:%02d:%02d,000 --> 00:%02d:%02d,000\n", new Object[]{Integer.valueOf(i / 60), Integer.valueOf(i % 60), Integer.valueOf((i + 1) / 60), Integer.valueOf((i + 1) % 60)});
                        if (infos.length >= 1) {
                            pw.print(infos[0]);
                        }
                        String str = (infos.length < 2 || infos[2].isEmpty()) ? "" : ",  " + infos[2];
                        pw.println(str);
                        if (infos.length >= 2 && !infos[1].isEmpty()) {
                            pw.println(infos[1]);
                        }
                        pw.println("made by Sygic");
                        pw.println();
                    } else {
                        continue;
                    }
                }
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
