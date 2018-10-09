package utils;

import java.util.List;
import java.util.Stack;

public class SymbolTable {
    public int indentLevel;
    List<ClassDescriptor> classDescriptors;
    Stack<VarDecl> enviromentVars;

    public SymbolTable() {
        this.indentLevel = 0;
    }

    public SymbolTable(List<ClassDescriptor> classDescriptors) {
        super();
        this.classDescriptors = classDescriptors;
    }
}
