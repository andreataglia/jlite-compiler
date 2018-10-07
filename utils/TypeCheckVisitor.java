package utils;

import jnodes.*;

public class TypeCheckVisitor implements JVisitor {
    @Override
    public void visit(JNode node) {
        System.out.println("visitiing node");
    }

    @Override
    public void visit(JProgram program) {
        System.out.println("visitiing program");
    }

    @Override
    public void visit(JMainClass mainClass) {

    }
    @Override
    public DataType visit(JAtom atom, SymbolTable symbolTable) {
        return null;
    }
}
