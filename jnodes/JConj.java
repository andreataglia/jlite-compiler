package jnodes;


import concrete_nodes.expressions.BoolExpr;
import concrete_nodes.expressions.BoolGrdExpr;
import concrete_nodes.expressions.TwoFactorsBoolExpr;
import utils.BoolOperand;

// conj ::= conj AND rExp
//          | rExp
//          | conj AND functionId
//          | functionId AND functionId
//          | functionId AND rExp
public class JConj extends JNode {
    //left side
    public JConj conj1;
    public JRExp rexp;
    public JAtom atom1;

    //right side
    public JRExp rexp2;
    public JAtom atom2;

    private String print;

    private BoolExpr boolExpr;

    public JConj(JAtom atom1, JRExp rexp2) {
        this.atom1 = atom1;
        this.rexp2 = rexp2;
        print = atom1 + " AND " + rexp2;

        boolExpr = new TwoFactorsBoolExpr(new BoolGrdExpr(atom1.getConcreteNode()), BoolOperand.AND, rexp2.getConcreteExpr());
    }

    public JConj(JAtom atom1, JAtom atom2) {
        this.atom1 = atom1;
        this.atom2 = atom2;
        print = atom1 + " AND " + atom2;

        boolExpr = new TwoFactorsBoolExpr(new BoolGrdExpr(atom1.getConcreteNode()), BoolOperand.AND, new BoolGrdExpr(atom2.getConcreteNode()));
    }

    public JConj(JRExp rexp) {
        this.rexp = rexp;
        print = rexp.toString();

        boolExpr = rexp.getConcreteExpr();
    }

    public JConj(JConj conj1, JAtom atom2) {
        this.conj1 = conj1;
        this.atom2 = atom2;
        print = conj1 + " AND " + atom2;

        boolExpr = new TwoFactorsBoolExpr(conj1.getConcreteExpr(), BoolOperand.AND, new BoolGrdExpr(atom2.getConcreteNode()));
    }

    public JConj(JConj conj1, JRExp rexp2) {
        this.conj1 = conj1;
        this.rexp2 = rexp2;
        print = conj1 + " AND " + rexp2;

        boolExpr = new TwoFactorsBoolExpr(conj1.getConcreteExpr(), BoolOperand.AND, rexp2.getConcreteExpr());
    }

    @Override
    public String toString() {
        return print;
    }

    BoolExpr getConcreteExpr(){
        return boolExpr;
    }

}
