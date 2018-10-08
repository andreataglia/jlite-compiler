package utils;

import concrete_nodes.*;

import java.util.Map;

public class PrettyPrintVisitor implements Visitor {

    private SymbolTable symbolTable;

    public PrettyPrintVisitor() {
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(Node node) {
        return null;
    }

    @Override
    public Object visit(Program program) {
        System.out.println(getIndentation() + "Program");
        symbolTable.indentLevel++;
        program.mainClass.accept(this);
        for (ClassDecl c : program.classDeclList) {
            c.accept(this);
        }
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MainClass mainClass) {
        System.out.println(getIndentation() + "MainClass-" + mainClass.className.name);
        symbolTable.indentLevel++;
        printClass(mainClass, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) {
        System.out.println(getIndentation() + "ClassDecl-" + classDecl.className.name);
        symbolTable.indentLevel++;
        printClass(classDecl, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MethodDecl methodDecl) {
        System.out.println(getIndentation() + "MethodDecl-" + methodDecl.name);
        return null;
    }

    //////////////////////// Helper Methods ///////////////////////////////////////

    private String getIndentation() {
        String ret = "";
        for (int i = 0; i < symbolTable.indentLevel; i++) {
            ret += "    ";
        }
        return ret;
    }

    private void printClass(ClassDecl classDecl, String spaces){
        if (!classDecl.varDeclList.isEmpty()){
            for (Map.Entry<String, BasicType> entry : classDecl.varDeclList.entrySet()) {
                System.out.println(spaces + "VarDecl-" + entry.getValue() + " " + entry.getKey());
            }
        }
        if (!classDecl.methodDeclList.isEmpty()){
            for (MethodDecl m : classDecl.methodDeclList) {
                m.accept(this);
            }
        }
    }
}
