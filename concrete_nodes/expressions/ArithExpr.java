package concrete_nodes.expressions;

import utils.Visitor;

public abstract class ArithExpr extends Expr {
    @Override
    public Object accept(Visitor visitor) {
        super.accept(visitor);
        return visitor.visit(this);
    }
}
