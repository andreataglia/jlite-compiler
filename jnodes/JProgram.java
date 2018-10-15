package jnodes;

import concrete_nodes.Program;

public class JProgram extends JNode {
    public JMainClass mainClass;
    public JClassDeclList classDeclList;

    public JProgram(JMainClass mainClass, JClassDeclList classDeclList) {
        this.mainClass = mainClass;
        this.classDeclList = classDeclList;
    }

    @Override
    public String toString() {
        String temp = "";
        try {
            temp = "\n------------------------\n--JLite Parsed program----\n" + mainClass + classDeclList;
        } catch (NullPointerException e) {
            System.out.println("Something went wrong while printing: " + e);
        }
        return temp;
    }

    public Program genConcreteTree() {
        return new Program(mainClass.genConcreteNode(), classDeclList.getClassList());
    }
}
