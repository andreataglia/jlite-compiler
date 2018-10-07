package utils;

import jnodes.*;

public interface JVisitor {
    void visit(JNode node);
    void visit(JProgram program);
    void visit(JMainClass mainClass);
    DataType visit(JAtom atom, SymbolTable symbolTable);
}
