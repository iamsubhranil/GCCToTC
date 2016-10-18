package com.personal.iamsubhranil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Author : Nil
 * Date : 10/18/2016 at 8:39 PM.
 * Project : GCCToTC
 */
public class AnalyzeFileAndConvert {

    public static void main(String[] args) {
        main2(new String[]{"E:\\myprogs\\p7_basic_arithmetic\\basic_arithmetics.c"});
    }

    private static void main2(String[] args) {
        if (args.length > 0) {
            File f = new File(args[0]);
            if (!f.exists()) {
                System.err.println("File does not exist. Please check the path and try again.");
                System.exit(1);
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                ArrayList<String> lines = new ArrayList<>();
                String cache;
                while ((cache = bufferedReader.readLine()) != null) {
                    lines.add(cache);
                }
                String dateTime = Files.readAttributes(f.toPath(), BasicFileAttributes.class).creationTime().toString();
                lines = manipulateLines(lines, dateTime);
                System.out.println("Modified source : ");
                File newFile = new File(f.getAbsolutePath().replace(".c", "_tc.c"));
                PrintStream printStream = new PrintStream(new FileOutputStream(newFile, false));
                lines.forEach(System.out::println);
                lines.forEach(printStream::println);
                printStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usage : java AnalyzeAndConvert.java [file_name]");
        }
    }

    private static ArrayList<String> manipulateLines(ArrayList<String> lines, String dateTime) throws IOException {
        if (!(lines.get(0).contains("#include"))) {
            System.out.println("Either this file is not an c source file or it has already been modified. " +
                    "If you still want to continue with the process, press F : ");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String ch = bufferedReader.readLine();
            if (!(ch.equals("f") || ch.equals("F"))) {
                System.exit(1);
            }
        }
        lines.ensureCapacity(lines.size() + 8);
        lines.add(0, "/* Author : Subhranil Mukherjee");
        lines.add(1, "   Created on : " + dateTime);
        lines.add(2, "   Environment : gcc");
        lines.add(3, "   Editor : vim */");
        final boolean[] declarationFinished = {false};
        final boolean[] hasConio = {false};
        final int[] counter = {0, 0, 0};
        lines.forEach(line -> {
            if (line.equals("#include<conio.h>")) {
                hasConio[0] = true;
            } else if (line.equals("void main(){")) {
                counter[2] = counter[0];
            } else if (line.contains("printf(") && !declarationFinished[0]) {
                declarationFinished[0] = true;
                counter[1] = counter[0];
            }
            counter[0]++;
        });
        lines.set(counter[2], lines.get(counter[2]).replace("void", "int"));
        lines.add(counter[1], "\tclrscr();");
        lines.add(lines.size() - 1, "\tgetch();");
        lines.add(lines.size() - 1, "\treturn 0;");
        if (!hasConio[0]) {
            lines.add((counter[2] - 1), "#include<conio.h>");
        }
        return lines;
    }

}
