package nodes3;

import utils.RelBoolOperand;

//⟨idc3⟩ ⟨Relop3⟩ ⟨idc3⟩ | ⟨idc3⟩
public class RelExp3 {
    public Idc3 leftSide;
    public RelBoolOperand relOp3;
    public Idc3 rightSide;

    public RelExp3(Idc3 leftSide, RelBoolOperand relOp3, Idc3 rightSide) {
        this.leftSide = leftSide;
        this.relOp3 = relOp3;
        this.rightSide = rightSide;
    }

    public RelExp3(Idc3 leftSide) {
        this.leftSide = leftSide;
    }
}
