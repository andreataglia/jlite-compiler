package utils;

import concrete_nodes.MethodDecl;
import concrete_nodes.VarDecl;
import concrete_nodes.expressions.Atom;
import concrete_nodes.expressions.AtomGrd;

import java.util.ArrayList;
import java.util.List;

class SymbolTable {
    int indentLevel;
    List<ClassDescriptor> classDescriptors;
    private List<VarDecl> methodLocalVars;
    ClassNameType currentClass;
    MethodDecl currentMethod;

    SymbolTable() {
        this.indentLevel = 0;
        methodLocalVars = new ArrayList<>();
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
    }

    void pushLocalVar(VarDecl varDecl) {
        methodLocalVars.add(varDecl);
    }

    boolean isFieldOfClass(String field, String className) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.equals(className)) {
                if (c.getFieldType(field) != null) return true;
            }
        }
        return false;
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
        return null;
    }

    FunctionType lookupFunctionTypeInClass(ClassNameType classNameType, String id) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.equals(classNameType)){
                for (MethodSignature m : c.methodSignatures) {
                    if (m.name.equals(id)) return m.getFunctionType();
                }
            }
        }
        return null;
    }

    ClassNameType lookUpClass(String className) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.name.equals(className)) return c.className;
        }
        return null;
    }
}
