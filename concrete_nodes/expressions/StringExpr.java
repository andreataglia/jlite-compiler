package concrete_nodes.expressions;

import utils.Visitor;

public class StringExpr extends Expr {
    public String s;

    public StringExpr(String s) {
        this.s = s;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return s;
    }
}
