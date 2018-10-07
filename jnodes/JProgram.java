package jnodes;
import utils.JVisitor;

import java.util.ArrayList;

public class JProgram extends JNode {
    public JMainClass mainClass;
    public ArrayList<JClassDecl> classDeclList;

    public JProgram(JMainClass mainClass, ArrayList<JClassDecl> classDeclList) {
        this.mainClass = mainClass;
        this.classDeclList = classDeclList;
    }

    @Override
    public String toString() {
        String temp = "";
        try{
            temp = "\n------------------------\n--JLite Parsed program----\n" + mainClass + classDeclList;
        }catch (NullPointerException e) {
            System.out.println("Something went wrong while printing: " + e);
        }
        return temp;
    }

    @Override
    public void accept(JVisitor visitor){
        visitor.visit(this);
    }


}
