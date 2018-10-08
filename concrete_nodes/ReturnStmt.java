package concrete_nodes;

import concrete_nodes.expressions.Expr;
import utils.Visitor;

public class ReturnStmt extends Stmt {
    public Expr expr;

    public ReturnStmt(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor visitor) {
        super.accept(visitor);
        return visitor.visit(this);
    }
}
