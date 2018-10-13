package utils;

import concrete_nodes.MethodDecl;
import concrete_nodes.VarDecl;

import java.util.ArrayList;
import java.util.List;

class SymbolTable {
    int indentLevel;
    List<ClassDescriptor> classDescriptors;
    private List<VarDecl> classLocalVars;
    private List<VarDecl> methodLocalVars;
    ClassNameType currentClass;
    MethodDecl currentMethod;

    SymbolTable() {
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

    boolean isFieldOfClass(String field, String className) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.name.equals(className)) {
                if (c.getFieldType(field) != null) return true;
            }
        }
        return false;
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
            if (c.className.equals(classNameType)) {
                return c.getFieldType(id);
            }
        }
        return null;
    }

    BasicType lookupVarType(String id) {
        for (VarDecl var : methodLocalVars) {
            if (var.id.equals(id)) return var.type;
        }
        for (VarDecl var : classLocalVars) {
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
