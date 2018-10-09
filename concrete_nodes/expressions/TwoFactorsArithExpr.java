package concrete_nodes.expressions;

import utils.ArithOperand;
import utils.Visitor;

public class TwoFactorsArithExpr extends ArithExpr {
    public ArithExpr leftSide;
    public ArithOperand operand;
    public ArithExpr rightSide;

    public TwoFactorsArithExpr(ArithExpr leftSide, ArithOperand operand, ArithExpr rightSide) {
        this.operand = operand;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
