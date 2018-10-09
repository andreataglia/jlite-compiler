package concrete_nodes.expressions;

import utils.ArithOperand;
import utils.Visitor;

public class TwoFactorsArithExpr extends ArithExpr {
    public ArithExpr leftSide;
    public ArithOperand operator;
    public ArithExpr rightSide;

    public TwoFactorsArithExpr(ArithExpr leftSide, ArithOperand operator, ArithExpr rightSide) {
        this.operator = operator;
        this.leftSide = leftSide;
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
