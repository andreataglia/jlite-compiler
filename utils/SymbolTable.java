package utils;

import concrete_nodes.MethodDecl;
import concrete_nodes.VarDecl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SymbolTable {
    int indentLevel;
    List<ClassDescriptor> classDescriptors;
    private List<VarDecl> classLocalVars;
    private List<VarDecl> methodLocalVars;
    ClassNameType currentClass;
    MethodDecl currentMethod;

    public SymbolTable() {
        this.indentLevel = 0;
        methodLocalVars = new ArrayList<>();
        classLocalVars = new ArrayList<>();
        classDescriptors = new ArrayList<>();
    }

    void increaseIndentLevel() {
        indentLevel++;
    }

    void decreaseIndentLevel() {
        indentLevel--;
    }

    void increaseIndentLevel(MethodDecl methodDecl) {
        increaseIndentLevel();
        currentMethod = methodDecl;
        methodLocalVars = new ArrayList<>();
    }

    void increaseIndentLevel(ClassNameType classNameType) {
        increaseIndentLevel();
        currentClass = classNameType;
        classLocalVars = new ArrayList<>();
    }

    void pushMethodVar(VarDecl varDecl) {
        methodLocalVars.add(varDecl);
    }

    void pushClassVar(VarDecl varDecl) {
        classLocalVars.add(varDecl);
    }

    void printState() {
        System.out.println("\n<<<<< Symbol Table State >>>>>>");
        for (VarDecl v : classLocalVars) {
            System.out.println(v);
        }
        System.out.println("<<<<< ------------------- >>>>>>");
    }

    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////////LookUps///////////////////////////////////////////


    BasicType lookupClassFieldType(ClassNameType classNameType, String id) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.name.equals(classNameType.name)) {
                return c.getFieldType(id);
            }
        }
        return null;
    }

    BasicType lookupVarType(String id) {
        ListIterator li = methodLocalVars.listIterator(methodLocalVars.size());
        // Iterate in reverse to first catch the last declared var
        while (li.hasPrevious()) {
            VarDecl var = (VarDecl) li.previous();
            if (var.id.equals(id)) return var.type;
        }
        li = classLocalVars.listIterator(classLocalVars.size());
        while (li.hasPrevious()) {
            VarDecl var = (VarDecl) li.previous();
            if (var.id.equals(id)) return var.type;
        }
        return null;
    }

    List<FunctionType> lookupFunctionTypeInClass(ClassNameType classNameType, String id) {
        List<FunctionType> list = new ArrayList<>();
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.equals(classNameType)) {
                for (MethodSignature m : c.methodSignatures) {
                    if (m.name.equals(id)) list.add(m.getFunctionType());
                }
            }
        }
        return list;
    }

    ClassNameType lookUpClass(String className) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.name.equals(className)) return c.className;
        }
        return null;
    }
}
