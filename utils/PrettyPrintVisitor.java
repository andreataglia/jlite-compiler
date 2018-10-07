package utils;

import jnodes.*;

public class PrettyPrintVisitor implements JVisitor {
    @Override
    public void visit(JNode node) {
        System.out.println("generic node");
    }

    @Override
    public void visit(JProgram program) {
        program.mainClass.accept(this);
    }

    @Override
    public void visit(JMainClass node) {
        System.out.println("visit mainClass");
    }


    @Override
    public DataType visit(JAtom atom, SymbolTable symbolTable) {
        return null;
    }
}