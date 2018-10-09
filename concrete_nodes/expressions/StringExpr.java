package concrete_nodes.expressions;

import utils.Visitor;

public class StringExpr extends Expr {
    String s;

    public StringExpr(String s) {
        this.s = s;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
