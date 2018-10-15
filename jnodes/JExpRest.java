package jnodes;

import concrete_nodes.expressions.Expr;

// expRest ::= COMMA exp
public class JExpRest extends JExp{
    public JExp exp;

    public JExpRest(JExp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return ", " + exp;
    }

    @Override
    Expr getConcreteExpr() {
        return exp.getConcreteExpr();
    }
}
