package concrete_nodes.expressions;

import utils.ArithOperand;
import utils.Visitor;

public class TwoFactorsArithExpr extends ArithExpr {
    public ArithOperand operand;
    public ArithExpr leftSide;
    public ArithExpr rightSide;

    public TwoFactorsArithExpr(ArithOperand operand, ArithExpr leftSide, ArithExpr rightSide) {
        this.operand = operand;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public Object accept(Visitor visitor) {
        super.accept(visitor);
        return visitor.visit(this);
    }
}
