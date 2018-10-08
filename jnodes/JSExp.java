package jnodes;

import concrete_nodes.expressions.Expr;

public class JSExp extends JExp {
    public String s;

    public JSExp(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }

    @Override
    Expr getConcreteExpr() {
        return null; //TODO impl
    }
}
