package jnodes;

import concrete_nodes.expressions.BoolExpr;
import concrete_nodes.expressions.BoolGrdExpr;
import concrete_nodes.expressions.TwoFactorsRelExpr;
import utils.RelBoolOperand;

// rExp ::= aExp bOp aExp | bGrd | NOT atom
public class JRExp extends JNode{
    public JAExp aexp1;
    public JAExp aexp2;
    public String bOp;
    public JAtom atom;
    public JBGrd bGrd;

    private String print;
    private BoolExpr boolExpr;

    public JRExp(JAExp aexp1, String bOp , JAExp aexp2) {
        this.aexp1 = aexp1;
        this.bOp = bOp;
        this.aexp2 = aexp2;

        print = aexp1 + bOp + aexp2;
        boolExpr = new TwoFactorsRelExpr(aexp1.getConcreteExpr(), RelBoolOperand.fromString(bOp), aexp2.getConcreteExpr());
    }

    public JRExp(JBGrd bGrd) {
        this.bGrd = bGrd;
        print = bGrd.toString();

        boolExpr = bGrd.getConcreteNode();
    }

    public JRExp(JAtom atom) {
        this.atom = atom;
        print = "!" + atom;
        boolExpr = new BoolGrdExpr(atom.getConcreteNode());
    }

    @Override
    public String toString() {
        return print;
    }

    BoolExpr getConcreteExpr() {
        return boolExpr;
    }
}
