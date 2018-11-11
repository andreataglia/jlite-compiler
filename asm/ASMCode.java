package asm;

import java.io.*;
import java.util.List;

public class ASMCode {
    private List<String> dataSection;
    private List<String> textSection;

    void addToData(String s){
        dataSection.add(s);
    }

    void addToText(String s){
        textSection.add(s);
    }

    void printToFile(String filename){
        System.out.println("mbareeeeeee");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(".data");
        for (String s: dataSection) {
            writer.println(s);
        }
        writer.println("\n.text");
        writer.println(".global main");
        for (String s: textSection) {
            writer.println(s);
        }
        writer.close();
    }
}
