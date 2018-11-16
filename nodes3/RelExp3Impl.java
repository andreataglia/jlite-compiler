package nodes3;

import asm.ASMGeneratorVisitor;
import utils.RelBoolOperand;

//⟨idc3⟩ ⟨Relop3⟩ ⟨idc3⟩ | ⟨idc3⟩
public class RelExp3Impl extends Exp3 {
    public Idc3 rightSide;
    public RelBoolOperand relOp3;
    public Idc3 leftSide;

    public RelExp3Impl(Type3 type, Idc3 leftSide, RelBoolOperand relOp3, Idc3 rightSide) {
        super(type);
        this.leftSide = leftSide;
        this.relOp3 = relOp3;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return leftSide + " " + relOp3 + " " + rightSide;
    }

    public int accept(ASMGeneratorVisitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
