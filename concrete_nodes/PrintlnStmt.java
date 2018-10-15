package concrete_nodes;

import concrete_nodes.expressions.Expr;
import utils.Visitor;

public class PrintlnStmt extends Stmt{
    public Expr expr;

    public PrintlnStmt(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }
}
