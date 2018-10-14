package nodes3;

import utils.RelBoolOperand;

//⟨idc3⟩ ⟨Relop3⟩ ⟨idc3⟩ | ⟨idc3⟩
public class RelExp3Impl extends Exp3 {
    public Idc3 rightSide;
    public RelBoolOperand relOp3;
    public Idc3 leftSide;

    public RelExp3Impl(Idc3 rightSide, RelBoolOperand relOp3, Idc3 leftSide) {
        this.rightSide = rightSide;
        this.relOp3 = relOp3;
        this.leftSide = leftSide;
    }

    @Override
    public String toString() {
        return " " + rightSide + " " + relOp3 + " " + rightSide;
    }
}
