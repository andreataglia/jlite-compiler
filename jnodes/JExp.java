package jnodes;

import concrete_nodes.expressions.Expr;

abstract public class JExp extends JNode {
    abstract Expr getConcreteExpr();
}
