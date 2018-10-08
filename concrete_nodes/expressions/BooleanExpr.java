package concrete_nodes.expressions;

import utils.BoolOperand;
import utils.Visitor;

public class BooleanExpr extends Expr {
    public Expr term1;
    public BoolOperand boolOperand;
    public Expr termi2;

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
