package concrete_nodes.expressions;

import utils.Visitor;

public class AtomParenthesizedExpr extends Atom{
    public Expr expr;

    public AtomParenthesizedExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "("+expr+")";
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
