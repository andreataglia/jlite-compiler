package asm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ASMCode {
    private List<String> dataSection;
    private List<String> textSection;

    public ASMCode() {
        dataSection = new ArrayList<>();
        textSection = new ArrayList<>();
    }

    void addToData(String s) {
        dataSection.add(s);
    }

    void addStringData(String s, int label) {
        addToData("L" + label + ":");
        addToData(".asciz " + s);
    }

    void addToText(String s) {
        textSection.add(s);
    }

    void printToFile(String filename) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(".data");
        for (String s : dataSection) {
            writer.println(s);
        }
        writer.println("\n.text");
        writer.println(".global main");
        for (String s : textSection) {
            writer.println(s);
        }
        writer.close();
    }
}
