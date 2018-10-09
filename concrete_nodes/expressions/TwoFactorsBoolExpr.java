package concrete_nodes.expressions;

import utils.BoolOperand;
import utils.Visitor;

public class BooleanExpr extends Expr {
    public Expr term1;
    public BoolOperand boolOperand;
    public Expr term2;

    public BooleanExpr(Expr term1, BoolOperand boolOperand, Expr term2) {
        this.term1 = term1;
        this.boolOperand = boolOperand;
        this.term2 = term2;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return term1 + " " + boolOperand + " " + term2;
    }
}
