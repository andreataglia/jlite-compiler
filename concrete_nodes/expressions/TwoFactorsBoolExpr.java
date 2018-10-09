package concrete_nodes.expressions;

import utils.BoolOperand;
import utils.Visitor;

public class TwoFactorsBoolExpr extends BoolExpr {
    public Expr leftSide;
    public BoolOperand operator;
    public Expr rightSide;

    public TwoFactorsBoolExpr(Expr leftSide, BoolOperand operator, Expr rightSide) {
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return leftSide + " " + operator + " " + rightSide;
    }
}
