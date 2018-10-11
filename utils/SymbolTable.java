package utils;

import concrete_nodes.ClassDecl;
import concrete_nodes.MethodDecl;
import concrete_nodes.VarDecl;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SymbolTable {
    int indentLevel;
    List<ClassDescriptor> classDescriptors;
    HashMap<Integer, Stack<VarDecl>> enviromentVars;
    ClassNameType currentClass;
    String currentMethod;

    SymbolTable() {
        this.indentLevel = 0;
    }

    public SymbolTable(List<ClassDescriptor> classDescriptors) {
        super();
        this.classDescriptors = classDescriptors;
    }

    void setCurrentClass(ClassDecl classDecl) {
        this.currentClass = classDecl.className;
    }

    void increaseIndentLevel() {
        indentLevel++;
        enviromentVars.put(indentLevel, new Stack<>());
    }

    void decreaseIndentLevel() {
        indentLevel--;
        enviromentVars.remove(indentLevel);
    }

    void increaseIndentLevel(MethodDecl methodDecl) {
        increaseIndentLevel();
        currentMethod = methodDecl.name;
        for (VarDecl v: methodDecl.params
             ) {

        }
    }

    void increaseIndentLevel(ClassNameType classNameType) {
        increaseIndentLevel();
        currentClass = classNameType;
    }

    void pushLocalVar(VarDecl varDecl) {
        enviromentVars.get(indentLevel).push(varDecl);
    }

    void popLocalVar() {
        enviromentVars.get(indentLevel).pop();
    }

    BasicType getClassFieldType(String id) {
        BasicType type;
        for (ClassDescriptor c : classDescriptors) {
            type = c.getFieldType(id);
            if (type != null) return type;
        }
        return null;
    }

    boolean fieldIsInClass(String field, String className) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.equals(className)) {
                if (c.getFieldType(field) != null) return true;
            }
        }
        return false;
    }

    BasicType getLocalVarType(String id){
        for (VarDecl var: enviromentVars.get(indentLevel)) {
            if (var.id.equals(id)) return var.type;
        }
        return null;
    }
}
