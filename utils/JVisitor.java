package utils;

import jnodes.*;

public interface JVisitor {
    Object visit(JNode node);
    Object visit(JProgram program);
    Object visit(JMainClass mainClass);
}
