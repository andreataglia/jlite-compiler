package concrete_nodes.expressions;

import utils.RelBoolOperand;
import utils.Visitor;

public class TwoFactorsRelExpr extends BoolExpr {
    public ArithExpr leftSide;
    public RelBoolOperand operand;
    public ArithExpr rightSide;

    public TwoFactorsRelExpr(ArithExpr leftSide, RelBoolOperand operand, ArithExpr rightSide) {
        this.leftSide = leftSide;
        this.operand = operand;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return leftSide + " " + operand + " " + rightSide;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
