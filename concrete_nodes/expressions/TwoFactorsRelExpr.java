package concrete_nodes.expressions;

import utils.RelBoolOperand;
import utils.Visitor;

public class TwoFactorsRelExpr extends BoolExpr {
    public ArithExpr leftSide;
    public RelBoolOperand operator;
    public ArithExpr rightSide;

    public TwoFactorsRelExpr(ArithExpr leftSide, RelBoolOperand operator, ArithExpr rightSide) {
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return leftSide + " " + operator + " " + rightSide;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
